package org.webcomponents.security.providers.josso;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.josso.gateway.GatewayServiceLocator;
import org.josso.gateway.identity.SSORole;
import org.josso.gateway.identity.exceptions.NoSuchUserException;
import org.josso.gateway.identity.exceptions.SSOIdentityException;
import org.josso.gateway.identity.service.SSOIdentityManagerService;
import org.josso.gateway.session.exceptions.NoSuchSessionException;
import org.josso.gateway.session.exceptions.SSOSessionException;
import org.josso.gateway.session.service.SSOSessionManagerService;
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

/**
 * 
 * @author Andrea Bandera
 */
public class JOSSOAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(JOSSOAuthenticationProvider.class);

	private SSOIdentityManagerService im;

	private SSOSessionManagerService sm;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JOSSOAuthenticationToken auth = (JOSSOAuthenticationToken) authentication;
		String jossoSessionId = auth.getJossoSessionId();
		try {
			Principal principal = im.findUserInSession(jossoSessionId);
			if (principal == null) {
				return null;
			}
			logger.debug("Principal checked for SSO Session '" + jossoSessionId + "' : " + principal);
			UserDetails details = retrieveUser(jossoSessionId, principal.getName(), auth);
			logger.debug("There is no associated principal for SSO Session '" + jossoSessionId + "'");
			
			Authentication rv = createSuccessAuthentication(principal, auth, details);

			/**
			 * Access sso session related with given the given SSO session
			 * identifier. In case the session is invalid or cannot be
			 * asserted an SSOException is thrown.
			 */
			sm.accessSession(jossoSessionId);
			
			// if (entry != null)
			// propagateSecurityContext(request, entry.principal);
			//
			return rv;
		} catch (NoSuchUserException e) {
			throw new UsernameNotFoundException("Unable to find user in session " + jossoSessionId, e);
		} catch (NoSuchSessionException e) {
			throw new AuthenticationServiceException("Unable to get user in session " + jossoSessionId, e);
		} catch (SSOIdentityException e) {
			throw new AuthenticationServiceException("Unable to get user in session " + jossoSessionId, e);
		} catch (SSOSessionException e) {
			throw new AuthenticationServiceException("Unable to get user in session " + jossoSessionId, e);
		}
	}

	private Authentication createSuccessAuthentication(Object principal, JOSSOAuthenticationToken auth, UserDetails details) {
		JOSSOAuthenticationToken rv = new JOSSOAuthenticationToken(auth.getJossoSessionId(), details.getAuthorities());
		rv.setDetails(details);
		rv.setAuthenticated(true);
		return rv;
	}

	protected UserDetails retrieveUser(String jossoSessionId, String username, JOSSOAuthenticationToken authentication) throws NoSuchUserException, SSOIdentityException {
		SSORole[] roles = im.findRolesBySSOSessionId(jossoSessionId);
		GrantedAuthority[] authorities = null;
		if(roles != null) {
			authorities = new GrantedAuthorityImpl[roles.length];
			for(int i = 0; i < roles.length; i++) {
				authorities[i] = new GrantedAuthorityImpl("ROLE_" + roles[i].getName().toUpperCase());
			}
		} else {
			authorities = new GrantedAuthority[0];
		}
		User rv = new User(username, "", true, true, true, true, authorities);
		return rv;
	}

	@Required
	public void setGatewayServiceLocator(GatewayServiceLocator gsl) throws Exception {
		this.im = gsl.getSSOIdentityManager();
		this.sm = gsl.getSSOSessionManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class authentication) {
		return JOSSOAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
