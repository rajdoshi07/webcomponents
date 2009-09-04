package org.webcomponents.security.ui.josso;

import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.josso.gateway.Constants;
import org.springframework.util.StringUtils;

public class JOSSOUtils {

	private static final Logger logger = Logger.getLogger(JOSSOUtils.class);

	/**
	 * This method builds the back_to URL value pointing to the given URI.
	 * <p/>
	 * The determines the host used to build the back_to URL in the
	 * following order :
	 * <p/>
	 * First, checks the singlePointOfAccess agent's configuration property.
	 * Then checks the reverse-proxy-host HTTP header value from the
	 * request. Finally uses current host name.
	 */
	public static String buildBackToQueryString(HttpServletRequest request, String uri) {
		String backto = null;

		// Build the back to url.
		String contextPath = request.getContextPath();

		// This is the root context
		if (contextPath == null || "".equals(contextPath))
			contextPath = "/";

		// Using default host
		StringBuffer mySelf = request.getRequestURL();

		try {
			URL url = new URL(mySelf.toString());
			backto = url.getProtocol() + "://" + url.getHost() + ((url.getPort() > 0) ? ":" + url.getPort() : "");
		} catch (java.net.MalformedURLException e) {
			throw new RuntimeException(e);
		}

		backto += (contextPath.endsWith("/") ? contextPath.substring(0, contextPath.length() - 1) : contextPath) + uri;

		backto = "?josso_back_to=" + backto;
		logger.debug("Using josso_back_to : " + backto);
		return backto;
	}

	/**
	 * Locates the JOSSO_SESSION cookie in the request.
	 * 
	 * @param request
	 *                the submitted request which is to be authenticated
	 * @return the cookie value (if present), null otherwise.
	 */
	public static Cookie getJossoSessionCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if ((cookies == null)) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			if (Constants.JOSSO_SINGLE_SIGN_ON_COOKIE.equals(cookies[i].getName())) {
				return cookies[i];
			}
		}

		return null;
	}

	/**
	 * Sets a "cancel cookie" (with maxAge = 0) on the response to disable
	 * persistent logins.
	 * 
	 * @param request
	 * @param response
	 */
	public static void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(Constants.JOSSO_SINGLE_SIGN_ON_COOKIE, null);
		cookie.setMaxAge(0);
		String path = StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/";
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	/**
	 * Sets a new JOSSO Cookie for the given value.
	 * 
	 * @param path
	 *                the path associated with the cookie, normaly the
	 *                partner application context.
	 * @param value
	 *                the SSO Session ID
	 * @return
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String jossoSessionId) {
		Cookie cookie = new Cookie(Constants.JOSSO_SINGLE_SIGN_ON_COOKIE, jossoSessionId);
		cookie.setMaxAge(-1);
		String path = StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/";
		cookie.setPath(path);
		response.addCookie(cookie);
	}

}
