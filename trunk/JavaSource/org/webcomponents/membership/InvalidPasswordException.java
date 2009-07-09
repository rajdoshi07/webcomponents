package org.webcomponents.membership;

public class InvalidPasswordException extends MemberException {
	
	private final String value;
	
	private final String regexp;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7226079276561006497L;

	public InvalidPasswordException(String value, String regexp) {
		super("Password " + value + " does not match regexp " + regexp);
		this.value = value;
		this.regexp = regexp;
	}

	public String getValue() {
		return value;
	}

	public String getRegexp() {
		return regexp;
	}
	
}
