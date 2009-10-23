package org.webcomponents.web.servlet.theme;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DomainThemeChangeInterceptor extends HandlerInterceptorAdapter {
	
	public final static String ORIGINAL_DEFAULT_THEME_NAME = "theme";

	private String defaultThemeName = ORIGINAL_DEFAULT_THEME_NAME;

	private Map<String, String> themes;

	/**
	 * Set the name of the default theme.
	 */
	public void setDefaultThemeName(String defaultThemeName) {
		this.defaultThemeName = defaultThemeName;
	}

	/**
	 * Return the name of the default theme.
	 */
	public String getDefaultThemeName() {
		return defaultThemeName;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ThemeResolver resolver = (ThemeResolver) request.getAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE);
		if(CollectionUtils.isEmpty(themes)) {
			resolver.setThemeName(request, response, getDefaultThemeName());
		} else {
			String host = request.getServerName();
			String theme = themes.get(host);
			if(theme == null) {
				resolver.setThemeName(request, response, getDefaultThemeName());
			} else {
				resolver.setThemeName(request, response, theme);
			}
		}
		return true;
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}

}
