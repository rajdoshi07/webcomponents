package org.webcomponents.membership.web;

import org.springframework.util.StringUtils;

public class SubscribeMemberCommand extends SubscriptionCommand {

	private static final long serialVersionUID = 2730377982068636922L;
	private static final String SCREEN_NAME_REG_EXP = "\\w{3,64}";
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

	public void setScreenName(String screenName) {
		if (StringUtils.hasText(screenName)) {
			String value = StringUtils.trimWhitespace(screenName);
			if (value == null || !value.matches(SCREEN_NAME_REG_EXP)) {
				throw new IllegalArgumentException("Invalid screenName: " + value);
			}
			super.setScreenName(value);
		} else {
			super.setScreenName(null);
		}
	}

}
