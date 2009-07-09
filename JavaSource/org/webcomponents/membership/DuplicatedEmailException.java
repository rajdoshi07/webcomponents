package org.webcomponents.membership;

public class DuplicatedEmailException extends MemberException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5434486719310599136L;
	public DuplicatedEmailException(String email) {
		super("Duplicated email " + email);
	}
}
