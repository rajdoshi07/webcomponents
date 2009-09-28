package org.webcomponents.security.vote;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.vote.AccessDecisionVoter;

public class ContentRelatedRoleVoter implements AccessDecisionVoter {
	
	private static final String PREFIX = "CONTENT_";
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		if(attribute.getAttribute().startsWith(PREFIX)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return MethodInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
		int rv = ACCESS_ABSTAIN;
		for(Object element: config.getConfigAttributes()) {
			ConfigAttribute attribute = (ConfigAttribute) element;
			if(this.supports(attribute)) {
				rv = ACCESS_DENIED;
				
				String role = attribute.getAttribute().substring(PREFIX.length());
				MethodInvocation methodInvocation = (MethodInvocation) object;
				Object target = methodInvocation.getThis();
				if(target instanceof ContentRelatedRoleService) {
					ContentRelatedRoleService service =  (ContentRelatedRoleService) target;
					Object id = methodInvocation.getArguments()[0];
					if("OWNER".equalsIgnoreCase(role)) {
						if(service.isContentOwner(id, authentication)) {
							return ACCESS_GRANTED;
						}
					} else if("EDITOR".equalsIgnoreCase(role)) {
						if(service.isContentEditor(id, authentication)) {
							return ACCESS_GRANTED;
						}
					} else if("VIEWER".equalsIgnoreCase(role)) {
						if(service.isContentViewer(id, authentication)) {
							return ACCESS_GRANTED;
						}
					}
				}
			}
		}
		return rv;
	}

}
