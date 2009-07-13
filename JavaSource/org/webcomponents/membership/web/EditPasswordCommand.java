package org.webcomponents.membership.web;


public class EditPasswordCommand {
	
	private String passwordCheck;
	private String passwordConfirm;
	private String password;

	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	public String getPasswordCheck() {
		return passwordCheck;
	}
	
	public void setPasswordCheck(String value) {
		this.passwordCheck = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
