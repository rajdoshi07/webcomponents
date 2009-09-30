package org.webcomponents.web.servlet.theme;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ThemeResolver;

public class DomainThemeResolver implements ThemeResolver {
	
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
	public String resolveThemeName(HttpServletRequest request) {
		if(CollectionUtils.isEmpty(themes)) {
			return getDefaultThemeName();
		}
		String host = request.getServerName();
		String theme = themes.get(host);
		if(theme == null) {
			return getDefaultThemeName();
		}
		return theme;
	}

	@Override
	public void setThemeName(HttpServletRequest arg0, HttpServletResponse arg1, String themeName) {
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}

}
