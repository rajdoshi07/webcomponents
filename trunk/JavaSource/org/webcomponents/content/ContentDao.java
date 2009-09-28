package org.webcomponents.content;

import java.security.Principal;
import java.util.List;


public interface ContentDao {

	String insert(Content content);
	
	Content get(String id);
	
	Content getMetadata(String id);
	
	boolean update(Content content);
	
	boolean updateStatus(String id, int status, Principal principal);

	List<Content> listMetadata(String username);

	boolean remove(String id);
	
	void addRelatedContent(String id, String relatedId);
	
	void removeRelatedContent(String id);

	boolean isContentOwner(String id, Principal principal);
	
	boolean isContentViewer(String id, Principal principal);

	boolean isContentEditor(String id, Principal principal);

}
