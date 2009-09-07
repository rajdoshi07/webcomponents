package org.webcomponents.security.ui.josso;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.josso.gateway.Constants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.webcomponents.security.providers.josso.JOSSOAuthenticationToken;

public class JOSSOSessionCookieProcessingFilter extends SpringSecurityFilter implements InitializingBean {
	
	private static final Logger logger = Logger.getLogger(JOSSOSessionCookieProcessingFilter.class);

	private ApplicationEventPublisher eventPublisher;
	private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			Cookie sessionCookie = JOSSOUtils.getJossoSessionCookie(request);
			if (sessionCookie != null) {
				String sessionId = sessionCookie.getValue();
				logger.debug("Authentication empty, JOSSO session cookie found " + StringUtils.quote(sessionId));
				JOSSOAuthenticationToken authRequest = new JOSSOAuthenticationToken(sessionId, new GrantedAuthority[0]);
				try {
					Authentication auth = this.authenticationManager.authenticate(authRequest);
					if (auth == null) {
						onUnsuccessfulAuthentication(request, response, null);
					} else {
						SecurityContextHolder.getContext().setAuthentication(auth);
						onSuccessfulAuthentication(request, response, auth);
						// Fire event
						if (this.eventPublisher != null) {
							eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(SecurityContextHolder
									.getContext().getAuthentication(), this.getClass()));
						}
					}
				} catch (AuthenticationServiceException e) {
					logger.debug("Unable to autenticate using " + Constants.JOSSO_SINGLE_SIGN_ON_COOKIE + " cookie value "
							+ StringUtils.quote(sessionId) + ". " + e.getMessage());
					onUnsuccessfulAuthentication(request, response, null);
				}
			}
		}
		chain.doFilter(request, response);
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException object) {
		JOSSOUtils.cancelCookie(request, response);
		logger.debug("Unsuccessful authentication." + (object == null ? "" : " " + object.getMessage()) + " JOSSO session id cookie removed.");
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		logger.debug("Successful authentication for " + auth.getName());
	}

	@Override
	public int getOrder() {
		return FilterChainOrder.REMEMBER_ME_FILTER;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(authenticationManager, "authenticationManager must be specified");
	}

}
