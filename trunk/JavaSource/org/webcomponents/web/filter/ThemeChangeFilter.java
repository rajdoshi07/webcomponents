package org.webcomponents.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

public class ThemeChangeFilter extends GenericFilterBean {
	
	private String paramName = ThemeChangeInterceptor.DEFAULT_PARAM_NAME;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String theme = request.getParameter(paramName);
		if(StringUtils.hasText(theme)) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			ThemeResolver resolver = RequestContextUtils.getThemeResolver(req);
			resolver.setThemeName(req, res, theme);
		}
		chain.doFilter(request, response);
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

}
