package org.webcomponents.membership;

public class DuplicatedUsernameException extends MemberException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5434486719310599136L;
	public DuplicatedUsernameException(String username) {
		super("Duplicated username " + username);
	}
}
