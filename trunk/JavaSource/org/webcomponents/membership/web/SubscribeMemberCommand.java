package org.webcomponents.membership.web;


public class SubscribeMemberCommand extends SubscriptionCommand {

	private static final long serialVersionUID = 2730377982068636922L;
	protected String password;
	private String passwordConfirm;
	/**
	 * 
	 */
	public SubscribeMemberCommand() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

}
