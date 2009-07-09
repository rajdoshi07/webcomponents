package org.webcomponents.mail;

import org.springframework.core.io.Resource;

public class MailContent {

	private String subjectPart;
	private String htmlPart;
	private String plainTextPart;
	private Resource[] attachmentsPart;
	private Resource[] embeddedPart;

	public MailContent() {
	}

	public Resource[] getAttachmentsPart() {
		return attachmentsPart;
	}

	public void setAttachmentsPart(Resource[] attachmentsPart) {
		this.attachmentsPart = attachmentsPart;
	}

	public Resource[] getEmbeddedPart() {
		return embeddedPart;
	}

	public void setEmbeddedPart(Resource[] embeddedPart) {
		this.embeddedPart = embeddedPart;
	}

	public String getHtmlPart() {
		return htmlPart;
	}

	public void setHtmlPart(String htmlPart) {
		this.htmlPart = htmlPart;
	}

	public String getPlainTextPart() {
		return plainTextPart;
	}

	public void setPlainTextPart(String plainTextPart) {
		this.plainTextPart = plainTextPart;
	}

	public String getSubjectPart() {
		return subjectPart;
	}

	public void setSubjectPart(String subjectPart) {
		this.subjectPart = subjectPart;
	}

}
