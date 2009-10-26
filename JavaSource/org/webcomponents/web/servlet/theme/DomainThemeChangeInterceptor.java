package org.webcomponents.web.servlet.theme;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

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
		ThemeResolver resolver = RequestContextUtils.getThemeResolver(request);
		String theme = getDefaultThemeName();
		if(!CollectionUtils.isEmpty(themes)) {
			String host = request.getServerName();
			theme = themes.get(host);
			if(theme == null) {
				theme = getDefaultThemeName();
			}
		}
		resolver.setThemeName(request, response, theme);
		return true;
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}

}
