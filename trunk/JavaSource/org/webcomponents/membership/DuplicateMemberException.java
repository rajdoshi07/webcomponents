package org.webcomponents.membership;

public class DuplicateMemberException extends MemberException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5434486719310599136L;
	public DuplicateMemberException(Throwable cause) {
		super("Duplicated member entry", cause);
		// TODO Auto-generated constructor stub
	}
}
