package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.josso.gateway.GatewayServiceLocator;
import org.josso.gateway.session.exceptions.NoSuchSessionException;
import org.josso.gateway.session.exceptions.SSOSessionException;
import org.josso.gateway.session.service.SSOSessionManagerService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.webcomponents.security.providers.josso.JOSSOAuthenticationToken;

public class JOSSOSessionPingFilter extends SpringSecurityFilter {
	
	private static final Logger logger = Logger.getLogger(JOSSOSessionPingFilter.class);

	private static final String TIMESTAMP_PREFIX = "org.webcomponents.security.ui.josso.";
	
	private static final long DEFAULT_SESSION_ACCESS_MIN_INTERVAL = 5000;
	
	private long sessionAccessMinInterval = DEFAULT_SESSION_ACCESS_MIN_INTERVAL;

	private SSOSessionManagerService sm;

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
	
	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication instanceof JOSSOAuthenticationToken) {
			JOSSOAuthenticationToken token = (JOSSOAuthenticationToken) authentication;
			long now = System.currentTimeMillis();
			Long timestamp = getTimestamp(request, token.getJossoSessionId());
			if(timestamp == null ||(now - timestamp) > this.sessionAccessMinInterval) {
				try {
					sm.accessSession(token.getJossoSessionId());
					logger.debug("JOSSO session " + StringUtils.quote(token.getJossoSessionId()) + " pinged at " + now);
					setTimestamp(request, token.getJossoSessionId(), now);
				} catch (NoSuchSessionException e) {
					logger.debug("Unable to ping JOSSO session " + token.getJossoSessionId() + ". " + e.getMessage());
					SecurityContextHolder.getContext().setAuthentication(null);
					return;
				} catch (SSOSessionException e) {
					logger.warn("Unable to ping JOSSO session " + token.getJossoSessionId() + ". " + e.getMessage());
					SecurityContextHolder.getContext().setAuthentication(null);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	private Long getTimestamp(HttpServletRequest request, String jossoSessionId) {
		Assert.hasText(jossoSessionId);
		HttpSession session = request.getSession();
		return (Long) session.getAttribute(TIMESTAMP_PREFIX + jossoSessionId);
	}

	private void setTimestamp(HttpServletRequest request, String jossoSessionId, Long timestamp) {
		Assert.hasText(jossoSessionId);
		Assert.notNull(timestamp);
		HttpSession session = request.getSession();
		session.setAttribute(JOSSOSessionPingFilter.TIMESTAMP_PREFIX + jossoSessionId, timestamp);
	}

}
