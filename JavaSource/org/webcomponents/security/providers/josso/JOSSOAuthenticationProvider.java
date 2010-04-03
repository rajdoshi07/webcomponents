package org.webcomponents.security.providers.josso;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.josso.gateway.GatewayServiceLocator;
import org.josso.gateway.identity.SSORole;
import org.josso.gateway.identity.SSOUser;
import org.josso.gateway.identity.exceptions.NoSuchUserException;
import org.josso.gateway.identity.exceptions.SSOIdentityException;
import org.josso.gateway.identity.service.SSOIdentityManagerService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * @author Andrea Bandera
 */
public class JOSSOAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(JOSSOAuthenticationProvider.class);

	private SSOIdentityManagerService im;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JOSSOAuthenticationToken auth = (JOSSOAuthenticationToken) authentication;
		String jossoSessionId = auth.getJossoSessionId();
		try {
			SSOUser ssoUser = im.findUserInSession(jossoSessionId);
			if (ssoUser == null) {
				logger.debug("No SSOUser found for JOSSO Session ID " + StringUtils.quote(jossoSessionId));
				return null;
			}
			Principal principal = createPrincipal(ssoUser);
			if (principal == null) {
				logger.debug("No principal created from SSOUser " + ObjectUtils.getDisplayString(ssoUser));
				return null;
			}
			logger.debug("Principal found for JOSSO Session ID " + StringUtils.quote(jossoSessionId) + ": " + principal);
			UserDetails details = retrieveUser(jossoSessionId, principal.getName(), auth);
			logger.debug("Roles of principal " + principal + ":" + StringUtils.arrayToCommaDelimitedString(details.getAuthorities()));
			
			Authentication rv = createSuccessAuthentication(principal, auth, details);
			return rv;
		} catch (NoSuchUserException e) {
			throw new UsernameNotFoundException("Unable to find user in session " + jossoSessionId, e);
		} catch (SSOIdentityException e) {
			throw new AuthenticationServiceException("Unable to get user in session " + jossoSessionId, e);
		}
	}

	@Required
	public void setGatewayServiceLocator(GatewayServiceLocator gsl) throws Exception {
		this.im = gsl.getSSOIdentityManager();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class authentication) {
		return JOSSOAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	protected Principal createPrincipal(SSOUser user) {
		return user;
	}
	
	protected UserDetails retrieveUser(String jossoSessionId, String username, JOSSOAuthenticationToken authentication) throws NoSuchUserException, SSOIdentityException {
		SSORole[] roles = im.findRolesBySSOSessionId(jossoSessionId);
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if(roles != null) {
			for(int i = 0; i < roles.length; i++) {
				authorities.add(new GrantedAuthorityImpl("ROLE_" + roles[i].getName().toUpperCase()));
			}
		}
		if(authentication.getAuthorities().length > 0) {
			for(int i = 0; i < authentication.getAuthorities().length; i++) {
				authorities.add(authentication.getAuthorities()[i]);
			}
		}
		User rv = new User(username, "", true, true, true, true, authorities.toArray(new GrantedAuthority[0]));
		return rv;
	}
	
	private Authentication createSuccessAuthentication(Object principal, JOSSOAuthenticationToken auth, UserDetails details) {
		JOSSOAuthenticationToken rv = new JOSSOAuthenticationToken(auth.getJossoSessionId(), details.getAuthorities());
		rv.setDetails(details);
		rv.setAuthenticated(true);
		return rv;
	}

}
