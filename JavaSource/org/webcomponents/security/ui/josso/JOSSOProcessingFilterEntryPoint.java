package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationEntryPoint;

/**
 * This is an ACEGI Fileter Entry Point implementation to trigger JOSSO
 * authentication process.
 * 
 * @author Andrea Bandera
 */
public class JOSSOProcessingFilterEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = Logger.getLogger(JOSSOProcessingFilterEntryPoint.class);

	private String gatewayLoginUrl;
	
	private String authenticationProcessingUri;
	
	/**
	 * This redirects the user to the JOSSO Login entry point.
	 */
	public void commence(ServletRequest servletRequest, ServletResponse servletResponse, AuthenticationException authenticationException)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		String loginUrl = getGatewayLoginUrl() + JOSSOUtils.buildBackToQueryString(request, this.authenticationProcessingUri);
                logger.debug("Redirecting to login url '" + loginUrl + "'");

		response.sendRedirect(response.encodeRedirectURL(loginUrl));
	}

	public String getGatewayLoginUrl() {
		return gatewayLoginUrl;
	}

	public void setGatewayLoginUrl(String loginFormUrl) {
		this.gatewayLoginUrl = loginFormUrl;
	}

	public void setAuthenticationProcessingUri(String authenticationProcessingUri) {
		this.authenticationProcessingUri = authenticationProcessingUri;
	}
}
