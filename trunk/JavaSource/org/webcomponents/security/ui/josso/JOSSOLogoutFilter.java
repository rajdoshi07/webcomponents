package org.webcomponents.security.ui.josso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.ui.logout.LogoutHandler;

public class JOSSOLogoutFilter extends org.springframework.security.ui.logout.LogoutFilter {

	private static final Logger logger = Logger.getLogger(JOSSOLogoutFilter.class);

	private String gatewayLogoutUrl;

	public JOSSOLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
		super(logoutSuccessUrl, handlers);
	}

	/**
	 * This method builds a logout URL based on a HttpServletRequest. The
	 * url contains all necessary parameters required by the front-channel
	 * part of the SSO protocol.
	 * 
	 * @return
	 */
	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("josso_logout request received for uri '" + request.getRequestURI() + "'");

		String logoutUrl = this.gatewayLogoutUrl + JOSSOUtils.buildBackToQueryString(request, getLogoutSuccessUrl());
		logger.debug("Redirecting to logout url '" + logoutUrl + "'");

		return logoutUrl;
	}

	public void setGatewayLogoutUrl(String gatewayLogoutUrl) {
		this.gatewayLogoutUrl = gatewayLogoutUrl;
	}

}
