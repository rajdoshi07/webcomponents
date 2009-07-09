package org.webcomponents.security.token;

import org.webcomponents.membership.MemberNotFoundException;

public class InvalidTokenException extends TokenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3873907427841888876L;

	public InvalidTokenException(Token t) {
		super("Unable to validate " + t.getValue() + ".");
	}

	public InvalidTokenException(Token t, MemberNotFoundException e) {
		super("Unable to validate " + t.getValue() + "." + e.getMessage());
	}
	
}
