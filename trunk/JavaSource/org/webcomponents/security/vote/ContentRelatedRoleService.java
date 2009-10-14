package org.webcomponents.security.vote;


import org.springframework.security.Authentication;

public interface ContentRelatedRoleService {
	
	boolean isContentOwner(Object id, Authentication authentication);
	
	boolean isContentEditor(Object id, Authentication authentication);

	boolean isContentViewer(Object id, Authentication authentication);

}
