package org.webcomponents.security.token;

import java.util.Date;

/**
 * TODO: better to use org.springframework.security.token.TokenService?
 * @author andreab
 *
 */
public interface TokenService {
	
	public String createToken(Object key, Object value);

	public String createToken(Object key, Object value, Date expireAt);
	
	public Token getToken(String token, Object key) throws TokenException;
}
