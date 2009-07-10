package org.webcomponents.competition;

import java.util.Date;

public class CodeExpiredException extends CodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date expiredOn;

	public CodeExpiredException() {
		super();
	}

	public CodeExpiredException(String code) {
		super(code);
	}

	public CodeExpiredException(String code, Date expiredOn) {
		super(code);
		this.expiredOn = expiredOn;
	}

	@Override
	public String getMessage() {
		return "Code " + getCode() + " expired on " + expiredOn;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

}
