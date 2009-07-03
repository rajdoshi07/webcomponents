package org.webcomponents.security.providers.josso;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.AbstractAuthenticationToken;

/**
 * 
 * @author Andrea Bandera
 */
public class JOSSOAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private String assertionId;

	public JOSSOAuthenticationToken(String assertionId, GrantedAuthority[] authorities) {
		super(authorities);
		this.assertionId = assertionId;
	}

	public String getAssertionId() {
		return assertionId;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return getDetails();
	}
}
