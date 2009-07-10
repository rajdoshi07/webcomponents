package org.webcomponents.competition;

import java.util.Date;

public class CodePendingException extends CodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date activeOn;

	public CodePendingException() {
		super();
	}

	public CodePendingException(String code) {
		super(code);
	}

	public CodePendingException(String code, Date activeOn) {
		super(code);
		this.activeOn = activeOn;
	}

	@Override
	public String getMessage() {
		return "Code " + getCode() + " will be active from " + activeOn;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

	public Date getActiveOn() {
		return activeOn;
	}

	public void setActiveOn(Date activeOn) {
		this.activeOn = activeOn;
	}

}
