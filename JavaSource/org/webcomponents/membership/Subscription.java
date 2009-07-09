package org.webcomponents.membership;

import org.springframework.util.StringUtils;

public class Subscription extends BusinessPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1976311483298564233L;

	private int type;
	
	private String notes;
	
	private boolean termsOfUseAccepted = false;

	private boolean privacyPolicyAccepted = false;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isTermsOfUseAccepted() {
		return termsOfUseAccepted;
	}

	public void setTermsOfUseAccepted(boolean useEnabled) {
		this.termsOfUseAccepted = useEnabled;
	}

	public boolean isPrivacyPolicyAccepted() {
		return privacyPolicyAccepted;
	}

	public void setPrivacyPolicyAccepted(boolean privacyEnabled) {
		this.privacyPolicyAccepted = privacyEnabled;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		if(StringUtils.hasText(notes)) {
			this.notes = StringUtils.trimWhitespace(notes);
		} else {
			this.notes = null;
		}
	}

}
