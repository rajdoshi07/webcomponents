package org.webcomponents.security.token;

public class TokenNotFoundException extends TokenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1685997206193894190L;

	public TokenNotFoundException(String token) {
		super(token, "Token " + token + " not found");
	}

}
