package org.webcomponents.mail;

import javax.mail.internet.InternetAddress;

public class RuntimeReceiverEmailSender extends EmailSender {

	public RuntimeReceiverEmailSender() {
		super();
	}

	protected InternetAddress getReceiver(Object obj) {
		if(obj instanceof MailReceiver) {
			MailReceiver rv = (MailReceiver) obj;
			return rv.getEmail();
		}
		return null;
	}

}