package org.webcomponents.content;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.security.annotation.Secured;
import org.springframework.web.multipart.MultipartFile;
import org.webcomponents.net.URIWrapper;

public interface ResourceService {
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public ResourceMetaData addResource(String contentId, MultipartFile resource) throws ContentNotFoundException, IOException, ResourceException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public List<Object> addResources(String contentId, MultipartFile[] resources) throws ContentNotFoundException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public List<? extends ResourceMetaData> listResourcesMetadata(String contentId, int offset, int size) throws IOException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public List<? extends ResourceMetaData> listResourcesMetadata(String contentId, int offset, int size, String rel) throws IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public List<? extends ResourceMetaData> listResourcesMetadata(String contentId, int offset, String rel) throws IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public Long countResources(String contentId);
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void removeAllResources(String contentId) throws IOException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public URIWrapper getResourceAccessUri(URIWrapper resource) throws IOException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_VIEWER"})
	public void exportResource(URIWrapper resource, OutputStream out) throws IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void removeResource(URIWrapper resource) throws IOException;

}
