package org.webcomponents.resource.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jets3t.service.S3ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.webcomponents.resource.ResourceDao;

@Controller
public class ResourceAccessController implements ServletContextAware {
	
	private static final Logger logger = Logger.getLogger(ResourceAccessController.class);
	
	private ResourceDao resourceDao;

	private int offset = 0;
	
	@RequestMapping(method = RequestMethod.GET)
	public void grantResourceAccess(HttpServletRequest request, HttpServletResponse response) throws S3ServiceException, IOException {
		String path = request.getRequestURI();
		if(path != null) {
			path = path.substring(offset);
			String url = resourceDao.getAccessUrl(path);
			logger.debug("Redirecting request path: " + path + " to " + url);
			response.sendRedirect(url);
		}
	}
	
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	@Override
	public void setServletContext(ServletContext application) {
		String contextPath = application.getContextPath();
		if(!StringUtils.hasText(contextPath)) {
			return;
		}
		offset = contextPath.length();
		if(contextPath.endsWith("/")) {
			offset--;
		}
		
	}

}
