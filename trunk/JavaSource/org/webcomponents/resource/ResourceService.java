package org.webcomponents.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import org.springframework.security.annotation.Secured;
import org.springframework.web.multipart.MultipartFile;

@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
public interface ResourceService {
	
	public ResourceMetaData add(Object entityId, MultipartFile resource) throws IOException, ResourceException;

	public List<Object> add(Object entityId, MultipartFile[] resources);

	public List<? extends ResourceMetaData> list(Object entityId, int offset, int size) throws IOException;
	
	public Long count(Object entityId);
	
	public void removeAll(Object entityId) throws IOException;
	
	public int getMaxResources(Object entityId);
	
	public URI getAccessUri(URI resource) throws IOException;
	
	public void export(URI resource, OutputStream out) throws IOException;

	public void remove(URI resource) throws IOException;

}
