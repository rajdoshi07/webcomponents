package org.webcomponents.content;

import java.io.Writer;
import java.util.List;

import org.springframework.security.annotation.Secured;

public interface ContentService {

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Content clone(String parentId) throws ContentNotFoundException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_EDITOR"})
	public Content edit(Content publishing) throws ContentNotFoundException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_EDITOR","CONTENT_VIEWER"})
	public Content retrieve(String id);
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_EDITOR","CONTENT_VIEWER"})
	public Content retrieveMetadata(String id);

	@Secured("ROLE_ADMIN")
	public void export(String id, Writer out) throws ContentNotFoundException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void remove(String id) throws ContentNotFoundException, IllegalStateException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void publish(String id) throws ContentNotFoundException;
	
	public List<Content> listMetadata(String username);

}
