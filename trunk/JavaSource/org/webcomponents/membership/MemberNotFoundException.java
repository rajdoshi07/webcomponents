package org.webcomponents.membership;

import org.springframework.util.ObjectUtils;

public class MemberNotFoundException extends MemberException {

	private static final long serialVersionUID = 5206312055094458760L;
	private final Object username;

	public MemberNotFoundException(Object username) {
		super("Cannot find a member with unique key '" + ObjectUtils.nullSafeToString(username) + "'");
		this.username = username;
	}
	
	public Object getUsername() {
		return username;
	}
}
