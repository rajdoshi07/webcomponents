package org.webcomponents.security.ui.josso;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.josso.gateway.signon.Constants;
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
		// Build the back to url.
		String contextPath = request.getContextPath();

		// This is the root context
		if (!StringUtils.hasText(contextPath)) {
			contextPath = "/";
		}

		// Using default host
		StringBuffer mySelf = request.getRequestURL();

		try {
			URL url = new URL(mySelf.toString());
			String rv = url.getProtocol() + "://" + url.getHost() + ((url.getPort() > 0) ? ":" + url.getPort() : "");
			rv += (contextPath.endsWith("/") ? contextPath.substring(0, contextPath.length() - 1) : contextPath) + uri;
			
			rv = "?josso_back_to=" + rv;
			rv = rv + "&" + Constants.PARAM_JOSSO_PARTNERAPP_CONTEXT + "=" + contextPath;
			rv = rv + "&" + Constants.PARAM_JOSSO_PARTNERAPP_HOST + "=" + request.getServerName();
			logger.debug("Using josso_back_to : " + rv);
			return rv;
		} catch (java.net.MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
