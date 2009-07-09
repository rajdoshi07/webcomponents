package org.webcomponents.security.token;

public class TokenException extends Exception {

	private static final long serialVersionUID = 8766225011045520561L;
	
	private final String token;

	public TokenException(String token) {
		super();
		this.token = token;
	}

	public TokenException(String token, String message) {
		super(message);
		this.token = token;
	}

	public TokenException(String token, Throwable cause) {
		super(cause);
		this.token = token;
	}

	public TokenException(String token, String message, Throwable cause) {
		super(message, cause);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}