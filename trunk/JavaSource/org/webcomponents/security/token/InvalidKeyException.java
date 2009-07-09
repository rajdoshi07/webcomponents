package org.webcomponents.security.token;

public class InvalidKeyException extends TokenException {

	private static final long serialVersionUID = -5334021466986850349L;
	
	private final Object key;

	public InvalidKeyException(String token, Object key) {
		super(token, "Invalid key " + key + " associated to token " + token);
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

}
