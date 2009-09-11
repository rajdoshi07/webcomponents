package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.josso.gateway.GatewayServiceLocator;
import org.josso.gateway.assertion.exceptions.AssertionNotValidException;
import org.josso.gateway.identity.exceptions.IdentityProvisioningException;
import org.josso.gateway.identity.service.SSOIdentityProviderService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationCredentialsNotFoundException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.util.StringUtils;
import org.webcomponents.security.providers.josso.JOSSOAuthenticationToken;

public class JOSSOAuthenticationProcessingFilter extends AbstractProcessingFilter {

	private static final Logger logger = Logger.getLogger(JOSSOAuthenticationProcessingFilter.class);

	private static final String JOSSO_SECURITY_CHECK_URI = "/josso_security_check";

	private SSOIdentityProviderService ip;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
		String assertionId = request.getParameter("josso_assertion_id");
		if(!StringUtils.hasText(assertionId)) {
			throw new AuthenticationCredentialsNotFoundException("HTTP parameter josso_assertion_id is missing or empty");
		}
		logger.debug("josso_security_check received for uri " + StringUtils.quote(request.getRequestURI()) + " assertion id "
				+ StringUtils.quote(assertionId));
		
		try {
			String jossoSessionId = ip.resolveAuthenticationAssertion(assertionId);
			JOSSOAuthenticationToken authRequest = new JOSSOAuthenticationToken(jossoSessionId, new GrantedAuthority[0]);
			Authentication rv = this.getAuthenticationManager().authenticate(authRequest);
			return rv;
		} catch (AssertionNotValidException e) {
			throw new AuthenticationServiceException("Unable to authenticate user with assertionId " + assertionId, e);
		} catch (IdentityProvisioningException e) {
			throw new AuthenticationServiceException("Unable to authenticate user with assertionId " + assertionId, e);
		}
	}

	@Override
	public String getDefaultFilterProcessesUrl() {
		return JOSSO_SECURITY_CHECK_URI;
	}

	@Override
	public int getOrder() {
	        return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER;
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		super.onSuccessfulAuthentication(request, response, authResult);

		JOSSOAuthenticationToken authentication = (JOSSOAuthenticationToken) authResult;
		JOSSOUtils.setCookie(request, response, authentication.getJossoSessionId());
	}

	@Required
	public void setGatewayServiceLocator(GatewayServiceLocator gsl) throws Exception {
		this.ip = gsl.getSSOIdentityProvider();
	}

}
