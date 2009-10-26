package org.webcomponents.mail;

import java.util.Map;

import org.webcomponents.membership.Member;
import org.webcomponents.security.token.TokenService;

public class TokenEmailSender extends RuntimeReceiverEmailSender {
	
	private TokenService tokenService;
	
	private String key;
	
	protected void populateModel(Map<String, Object> model, Object obj) {
		model.put("object", obj);
		model.put("code", prepareCode(obj));
	}

	private String prepareCode(Object obj) {
		if(obj instanceof Member) {
			Member member = (Member) obj;
			String value = member.getEmail().toString();
			String rv = tokenService.createToken(key, value);
			return rv;
		}
		throw new IllegalArgumentException("Object of class " + obj.getClass().getName() + " isn't instance of Member");
	}

	public final TokenService getTokenService() {
		return tokenService;
	}

	public final void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public final String getKey() {
		return key;
	}

	public final void setKey(String key) {
		this.key = key;
	}
	
}
