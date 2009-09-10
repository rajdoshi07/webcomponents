package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.util.StringUtils;
import org.webcomponents.security.providers.josso.JOSSOAuthenticationToken;

public class JOSSOSessionPingFilter extends SpringSecurityFilter {
	
	private static final Logger logger = Logger.getLogger(JOSSOSessionPingFilter.class);

	private static final long DEFAULT_SESSION_ACCESS_MIN_INTERVAL = 1000;

	private long sessionAccessMinInterval = DEFAULT_SESSION_ACCESS_MIN_INTERVAL;

	private SSOSessionManagerService sm;

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication instanceof JOSSOAuthenticationToken) {
			JOSSOAuthenticationToken token = (JOSSOAuthenticationToken) authentication;
			Long timestamp = JOSSOUtils.getTimestamp(request, token);
			if(timestamp != null) {
				long now = System.currentTimeMillis();
				logger.trace("Now " + now + ". Last JOSSO session ping " + timestamp + ".");
				if((now - timestamp) > this.sessionAccessMinInterval) {
					logger.trace("Now " + now + ". Last JOSSO session ping " + timestamp + ". Ping required");
					try {
						sm.accessSession(token.getJossoSessionId());
						logger.debug("JOSSO session " + StringUtils.quote(token.getJossoSessionId()) + " pinged at " + timestamp);
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
