package org.webcomponents.membership;

import java.security.Principal;

import javax.mail.internet.InternetAddress;

import org.springframework.util.StringUtils;
import org.webcomponents.content.PersistentObject;
import org.webcomponents.mail.MailReceiver;
import org.webcomponents.mobile.MobileNumber;
import org.webcomponents.mobile.MobileReceiver;

public class Member extends PersistentObject implements Principal, MailReceiver, MobileReceiver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3483071076070600310L;
	
	public static final String EMAIL_REG_EXP = "^[a-zA-Z0-9_!#\\$%&'\\*\\+/=\\?\\^`\\{\\|\\}~\\.\\-]{1,64}@\\w+[\\w\\.\\-]*?\\.[a-zA-Z]{1,4}$";

	private MemberStatus status;

	protected String screenName;

	private InternetAddress email;

	private MobileNumber mobileNumber;

	private boolean emailEnabled = false;

	private boolean mobileEnabled = false;
	
	private InternetAddressStatus internetAddressStatus = null;

	public Member() {
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName
	 *                the screenName to set
	 */
	public void setScreenName(String screenName) {
		if(StringUtils.hasText(screenName)) {
			this.screenName = StringUtils.trimWhitespace(screenName);
		} else {
			this.screenName = null;
		}
	}

	public MemberStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *                the status to set
	 */
	public final void setStatus(MemberStatus status) {
		this.status = status;
	}

	public String getUsername() {
		if(getScreenName() == null) {
			if(getEmail() != null) {
				return getEmail().getAddress();
			}
		}
		return getScreenName();
	}

	public InternetAddress getEmail() {
		return email;
	}

	public void setEmail(InternetAddress email) {
		if(email != null) {
			String value = email.getAddress();
			if(value == null || !value.matches(EMAIL_REG_EXP)) {
				throw new IllegalArgumentException("Invalid email address: " + value);
			}
		}
		this.email = email;
	}

	public MobileNumber getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(MobileNumber mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	public boolean isMobileEnabled() {
		return mobileEnabled;
	}

	public void setMobileEnabled(boolean mobileEnabled) {
		this.mobileEnabled = mobileEnabled;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Member) {
			Member member = (Member) obj;
			return member.getUsername().equals(getUsername());
		}
		return false;
	}

	public void setId(String id) {
		setScreenName(id);
	}

	public String getId() {
		return getScreenName();
	}

	public InternetAddressStatus getEmailStatus() {
		return internetAddressStatus;
	}

	public final void setEmailStatus(InternetAddressStatus internetAddressStatus) {
		this.internetAddressStatus = internetAddressStatus;
	}

	public String getName() {
		return getUsername();
	}

}
