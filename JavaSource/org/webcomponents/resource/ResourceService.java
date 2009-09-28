package org.webcomponents.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import org.springframework.security.annotation.Secured;
import org.springframework.web.multipart.MultipartFile;
import org.webcomponents.content.ContentNotFoundException;

public interface ResourceService {
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public ResourceMetaData add(Object contentId, MultipartFile resource) throws ContentNotFoundException, IOException, ResourceException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public List<Object> add(Object contentId, MultipartFile[] resources) throws ContentNotFoundException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public List<? extends ResourceMetaData> list(Object contentId, int offset, int size) throws IOException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public List<? extends ResourceMetaData> list(Object contentId, int offset, int size, String rel) throws IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public Long count(Object contentId);
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void removeAll(Object contentId) throws IOException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public URI getAccessUri(URI resource) throws IOException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public void export(URI resource, OutputStream out) throws IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void remove(URI resource) throws IOException;

}
