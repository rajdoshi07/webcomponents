package org.webcomponents.beans.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

public class GrantedAuthorityEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		GrantedAuthority authority = (GrantedAuthority) getValue();
		return authority == null ? null : authority.getAuthority();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text == null ? null : new GrantedAuthorityImpl(text));
	}
	
}
