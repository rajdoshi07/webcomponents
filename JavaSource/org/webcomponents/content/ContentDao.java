package org.webcomponents.content;

import java.security.Principal;
import java.util.List;

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
	
	void addRelatedContent(String id, String relatedId);
	
	void removeRelatedContent(String id);

	boolean isOwner(String id, Principal principal);
	
	boolean isViewer(String id, Principal principal);

	boolean isEditor(String id, Principal principal);
	
	List<GrantedAuthority> getAuthorities(String id);
	
	int resetAuthorities(String id);
	
	void putAuthority(String id, GrantedAuthority authority);

}
