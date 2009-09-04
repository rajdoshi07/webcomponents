package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.josso.gateway.GatewayServiceLocator;
import org.josso.gateway.session.exceptions.NoSuchSessionException;
import org.josso.gateway.session.exceptions.SSOSessionException;
import org.josso.gateway.session.service.SSOSessionManagerService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.webcomponents.security.providers.josso.JOSSOAuthenticationToken;

public class JOSSOSessionPingFilter extends SpringSecurityFilter {

	public static final long DEFAULT_SESSION_ACCESS_MIN_INTERVAL = 1000;

	private long sessionAccessMinInterval = DEFAULT_SESSION_ACCESS_MIN_INTERVAL;

	private SSOSessionManagerService sm;

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication instanceof JOSSOAuthenticationToken) {
			JOSSOAuthenticationToken token = (JOSSOAuthenticationToken) authentication;
			long timestamp = JOSSOUtils.getTimestamp(request, token);
			long now = System.currentTimeMillis();
			if((now - timestamp) > this.sessionAccessMinInterval) {
				try {
					sm.accessSession(token.getJossoSessionId());
					JOSSOUtils.setTimestamp(request, token);
				} catch (NoSuchSessionException e) {
					logger.debug("Unable to ping JOSSO session " + token.getJossoSessionId() + ". " + e.getMessage());
					JOSSOUtils.invalidateSession(request, response);
				} catch (SSOSessionException e) {
					logger.warn("Unable to ping JOSSO session " + token.getJossoSessionId() + ". " + e.getMessage());
					JOSSOUtils.invalidateSession(request, response);
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public int getOrder() {
		return FilterChainOrder.PRE_AUTH_FILTER;
	}

	public void setSessionAccessMinInterval(long sessionAccessMinInterval) {
		this.sessionAccessMinInterval = sessionAccessMinInterval;
	}

	@Required
	public void setGatewayServiceLocator(GatewayServiceLocator gsl) throws Exception {
		this.sm = gsl.getSSOSessionManager();
	}

}
