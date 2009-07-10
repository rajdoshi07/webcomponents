package org.webcomponents.competition;

public class AlreadyUsedByOtherCodeException extends CodeException {
	private static final long serialVersionUID = -7190590049110516573L;

	public AlreadyUsedByOtherCodeException() {
		super();
	}

	public AlreadyUsedByOtherCodeException(String code) {
		super(code);
	}
	
	@Override
	public String getMessage() {
		return "Someone else already participated using code " + getCode();
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
