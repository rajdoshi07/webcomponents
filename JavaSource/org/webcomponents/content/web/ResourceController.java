package org.webcomponents.content.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.webcomponents.content.Content;
import org.webcomponents.content.ContentNotFoundException;
import org.webcomponents.content.ContentService;
import org.webcomponents.content.ResourceException;
import org.webcomponents.content.ResourceMetaData;
import org.webcomponents.content.ResourceService;
import org.webcomponents.net.URIWrapper;

@Controller
public class ResourceController {
	
	private ResourceService resourceService;
	private ContentService contentService;
	private long maxUploadSize;
	private int pageSize;
	
	@RequestMapping(method=RequestMethod.GET)
	public Map<String, Object> list(@RequestParam("id")String id, @RequestParam(value="offset", required=false)Integer offset, @RequestParam(value="size", required=false)Integer size) throws IOException {
		int o = offset == null || offset < 0 ? 0 : offset;
		int s = size == null || size < 0 ? pageSize : size;
		List<? extends ResourceMetaData> resources = resourceService.listResourcesMetadata(id, o, s);
		Long count = resourceService.countResources(id);
		
		Map<String, Object> model = new HashMap<String, Object>(3);
		model.put("resources", resources);
		model.put("count", count);
		model.put("offset", o);
		model.put("pageSize", s);
		
		Content content = this.contentService.retrieveMetadata(id);
		if(content != null) {
			model.put("content", content);
		}
		
		model.put("maxUploadSize", maxUploadSize);
		return model;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void add(@RequestParam("id")String id, @RequestParam("file")MultipartFile file, HttpServletResponse response) throws ContentNotFoundException, IOException, ResourceException  {
		resourceService.addResource(id, file);
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		// Mac bug
		PrintWriter out = response.getWriter();
		out.print("ok");
		out.flush();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void remove(@RequestParam("resource")URIWrapper resource, HttpServletResponse response) throws IOException {
		this.resourceService.removeResource(resource);
		response.setStatus(HttpServletResponse.SC_OK);
		// Mac bug
		PrintWriter out = response.getWriter();
		out.print("ok");
		out.flush();
	}
		
	public void setMaxUploadSize(long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
	
}

