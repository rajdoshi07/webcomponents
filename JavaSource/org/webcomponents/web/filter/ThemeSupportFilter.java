package org.webcomponents.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.ui.context.ThemeSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ThemeResolver;

public class ThemeSupportFilter extends GenericFilterBean {
	
	private static final String THEME_SOURCE_DEFAULT_BEAN = "themeSource";
	
	private static final String THEME_RESOLVER_DEFAULT_BEAN = "themeResolver";
	
	private String themeSourceBean = THEME_SOURCE_DEFAULT_BEAN;
	
	private String themeResolverBean = THEME_RESOLVER_DEFAULT_BEAN;
	
	private ThemeSource themeSource;

	private ThemeResolver themeResolver;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(themeSource != null) {
			request.setAttribute(DispatcherServlet.THEME_SOURCE_ATTRIBUTE, this.themeSource);
		}
		if(this.themeResolver != null) {
			request.setAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
		}
		chain.doFilter(request, response);
	}

	@Override
	protected void initFilterBean() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.themeSource = (ThemeSource) context.getBean(this.themeSourceBean);
		this.themeResolver = (ThemeResolver) context.getBean(this.themeResolverBean);
	}

	public void setThemeSourceBean(String themeSourceBean) {
		this.themeSourceBean = themeSourceBean;
	}

	public void setThemeResolverBean(String themeResolverBean) {
		this.themeResolverBean = themeResolverBean;
	}

}
