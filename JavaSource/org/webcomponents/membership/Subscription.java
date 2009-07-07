package org.webcomponents.membership;

import org.springframework.util.StringUtils;

public class Subscription extends BusinessPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1976311483298564233L;

	private int type;
	
	private String notes;
	
	private boolean useEnabled = false;

	private boolean privacyEnabled = false;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isUseEnabled() {
		return useEnabled;
	}

	public void setUseEnabled(boolean useEnabled) {
		this.useEnabled = useEnabled;
	}

	public boolean isPrivacyEnabled() {
		return privacyEnabled;
	}

	public void setPrivacyEnabled(boolean privacyEnabled) {
		this.privacyEnabled = privacyEnabled;
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
