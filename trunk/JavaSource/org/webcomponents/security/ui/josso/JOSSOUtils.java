package org.webcomponents.security.ui.josso;

import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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
	 * This creates a new JOSSO Cookie for the given path and value.
	 * 
	 * @param path
	 *                the path associated with the cookie, normaly the
	 *                partner application context.
	 * @param value
	 *                the SSO Session ID
	 * @return
	 */
	public static Cookie newJossoCookie(String path, String value) {

		// Some browsers don't like cookies without paths. This is
		// useful for partner applications configured in the root
		// context
		if (path == null || "".equals(path))
			path = "/";

		Cookie ssoCookie = new Cookie(org.josso.gateway.Constants.JOSSO_SINGLE_SIGN_ON_COOKIE, value);
		ssoCookie.setMaxAge(-1);
		ssoCookie.setPath(path);

		// TODO : Check domain / secure ?
		// ssoCookie.setDomain(cfg.getSessionTokenScope());
		// ssoCookie.setSecure(true);

		return ssoCookie;
	}

}
