/*
 * Created on 14-giu-2006
 * @author davidedc
 */
package org.webcomponents.mail;

import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MailContentTemplate extends MailContent {
	private InternetAddress sender;
	private InternetAddress bcc;

	public InternetAddress getSender() {
		return sender;
	}

	public void setSender(InternetAddress sender) {
		this.sender = sender;
	}

	/**
	 * @return the bcc
	 */
	public final InternetAddress getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public final void setBcc(InternetAddress bcc) {
		this.bcc = bcc;
	}
	
	public boolean isMultipart() {
		if(getAttachmentsPart() != null && getAttachmentsPart().length > 0) {
			return true;
		}
		if(getPlainTextPart() != null && getHtmlPart() != null) {
			return true;
		}
		if(getHtmlPart() != null && getEmbeddedPart() != null && getEmbeddedPart().length > 0) {
			return true;
		}
		return false;
	}
	
	public MimeMessagePreparator createMimeMessagePreparator(final Map<String, Object> model, final InternetAddress to) {
		return new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = isMultipart() ? new MimeMessageHelper(mimeMessage, true) : new MimeMessageHelper(mimeMessage);
					message.setTo(to);
					message.setFrom(getSender());
					if(getBcc() != null) {
						message.setBcc(getBcc());
					}
					message.setSubject(getSubjectPart());
					if(getPlainTextPart() != null) {
						message.setText(getPlainTextPart());
					}
					if(getHtmlPart() != null) {
						message.setText(getHtmlPart(), true);
						Resource[] inlineAttachments = getEmbeddedPart();
						for(int i = 0; inlineAttachments != null && i < inlineAttachments.length; i++) {
							message.addInline(inlineAttachments[i].getFilename(), inlineAttachments[i]);
						}
					}
					Resource[] attachments = getAttachmentsPart();
					for(int i = 0; attachments != null && i < attachments.length; i++) {
						message.addAttachment(attachments[i].getFilename(), attachments[i]);
					}
				}
			};
	}
}
