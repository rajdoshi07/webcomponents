package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationCredentialsNotFoundException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.util.StringUtils;
import org.webcomponents.security.providers.josso.JOSSOAuthenticationToken;

public class JOSSOAuthenticationProcessingFilter extends AbstractProcessingFilter {

	private static final Logger logger = Logger.getLogger(JOSSOAuthenticationProcessingFilter.class);

	private static final String JOSSO_SECURITY_CHECK_URI = "/josso_security_check";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
		String assertionId = request.getParameter("josso_assertion_id");
		if(!StringUtils.hasText(assertionId)) {
			throw new AuthenticationCredentialsNotFoundException("HTTP parameter josso_assertion_id is missing or empty");
		}
		logger.debug("josso_security_check received for uri " + StringUtils.quote(request.getRequestURI()) + " assertion id "
				+ StringUtils.quote(assertionId));
		JOSSOAuthenticationToken authRequest = new JOSSOAuthenticationToken(assertionId, new GrantedAuthority[0]);
		Authentication rv = this.getAuthenticationManager().authenticate(authRequest);
		return rv;
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
		// The cookie is valid to for the partner application
		// only ... in the future each partner app may
		// store a different auth. token (SSO SESSION) value
		JOSSOAuthenticationToken authentication = (JOSSOAuthenticationToken) authResult;
		Cookie cookie = JOSSOUtils.newJossoCookie(request.getContextPath(), authentication.getAssertionId());
		response.addCookie(cookie);
	}
}
