package org.webcomponents.content;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.annotation.Secured;
import org.webcomponents.security.vote.ContentRelatedRoleService;

public interface ContentService extends ContentRelatedRoleService {

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Content clone(String parentId) throws ContentNotFoundException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_EDITOR"})
	public Content edit(Content publishing) throws ContentNotFoundException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_EDITOR","CONTENT_VIEWER"})
	public Content retrieve(String id);
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER","CONTENT_EDITOR","CONTENT_VIEWER"})
	public Content retrieveMetadata(String id);

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void remove(String id) throws ContentNotFoundException, IllegalStateException;
	
	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void publish(String id) throws ContentNotFoundException;
	
	public List<Content> listOwnedContentMetadata();
	
	public List<Content> listVisibleContentMetadata();

	@Secured("ROLE_ADMIN")
	public List<Content> listOwnedContentMetadata(String username);
	
	@Secured("ROLE_ADMIN")
	public List<Content> listVisibleContentMetadata(List<GrantedAuthority> authority);

	@Secured("ROLE_ADMIN")
	public void export(String id, Writer out) throws ContentNotFoundException, IOException;
	
	@Secured("ROLE_ADMIN")
	public void putAuthorities(String id, List<GrantedAuthority> authorities);
	
	@Secured("ROLE_ADMIN")
	public List<GrantedAuthority> listAuthorities(String id);

}
