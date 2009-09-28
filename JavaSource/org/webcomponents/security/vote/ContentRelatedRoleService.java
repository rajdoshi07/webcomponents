package org.webcomponents.security.vote;

import java.security.Principal;

public interface ContentRelatedRoleService {
	
	boolean isContentOwner(Object id, Principal principal);
	
	boolean isContentEditor(Object id, Principal principal);

	boolean isContentViewer(Object id, Principal principal);

}
