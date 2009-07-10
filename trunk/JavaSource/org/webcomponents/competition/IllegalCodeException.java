package org.webcomponents.competition;

/**
 * TODO: rename to InvalidCodeException
 * @author andreab
 *
 */
public class IllegalCodeException extends CodeException {
	private static final long serialVersionUID = 4049923753272095541L;

	public IllegalCodeException() {
		super();
	}

	public IllegalCodeException(String code) {
		super(code);
	}

	@Override
	public String getMessage() {
		return "Illegal code " + getCode();
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
