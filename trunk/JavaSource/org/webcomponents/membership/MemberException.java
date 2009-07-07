/*
 * Created on 30/gen/07
 * @author davidedc
 */
package org.webcomponents.membership;

public class MemberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberException(String message) {
		super(message);
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
	}

}
