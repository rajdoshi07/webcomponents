package org.webcomponents.competition;

public class CodeException extends CompetitionException {
	private static final long serialVersionUID = 4121410722165961271L;

	private String code;
	
	public CodeException() {
		super();
	}

	public CodeException(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
