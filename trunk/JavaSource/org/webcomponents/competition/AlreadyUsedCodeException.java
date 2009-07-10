package org.webcomponents.competition;

public class AlreadyUsedCodeException extends CodeException {
	private static final long serialVersionUID = 3833470599920890168L;

	public AlreadyUsedCodeException() {
		super();
	}

	public AlreadyUsedCodeException(String code) {
		super(code);
	}

	@Override
	public String getMessage() {
		return "User already participated using code " + getCode();
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
