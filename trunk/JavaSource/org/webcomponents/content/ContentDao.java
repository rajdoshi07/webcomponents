package org.webcomponents.content;

import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.List;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;


public interface ContentDao {

	String insert(Content content);
	
	Content get(String id);
	
	Content getMetadata(String id);
	
	boolean update(Content content);
	
	boolean updateStatus(String id, int status, Principal principal);

	List<Content> listMetadata(String username);

	List<Content> listMetadata(List<GrantedAuthority> authority);
	
	boolean remove(String id);
	
	void export(String id, Writer out) throws IOException;
	
	void addRelatedContent(String id, String relatedId);
	
	void removeRelatedContent(String id);

	boolean isOwner(String id, Authentication authentication);
	
	boolean isViewer(String id, Authentication authentication);

	boolean isEditor(String id, Authentication authentication);
	
	List<GrantedAuthority> getAuthorities(String id);
	
	int resetAuthorities(String id);
	
	void putAuthority(String id, GrantedAuthority authority);

}
