package org.webcomponents.security.token;

public class TokenExpiredException extends TokenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2852355222715661509L;
	
	public TokenExpiredException(Token t) {
		super("Token " + t.getId() + " expired on " + t.getExpireAt());
	}

}
