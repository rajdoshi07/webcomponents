package org.webcomponents.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerMailTemplate extends MailContentTemplate implements InitializingBean {
	
	private Configuration configuration;

	private String subjectTemplateName;
	
	private String plainTextTemplateName;
	
	private String htmlTextTemplateName;
	
	protected Locale locale;

	private Template subjectTemplate;

	private Template plainTextTemplate;

	private Template htmlTextTemplate;

	public final Locale getLocale() {
		return locale;
	}

	public final void setLocale(Locale locale) {
		this.locale = locale;
	}

	public final String getSubjectTemplateName() {
		return subjectTemplateName;
	}

	public final void setSubjectTemplateName(String subjectTemplate) {
		this.subjectTemplateName = subjectTemplate;
	}

	public final String getPlainTextTemplateName() {
		return plainTextTemplateName;
	}

	public final void setPlainTextTemplateName(String plainTextTemplate) {
		this.plainTextTemplateName = plainTextTemplate;
	}

	public final String getHtmlTextTemplateName() {
		return htmlTextTemplateName;
	}

	public final void setHtmlTextTemplateName(String htmlTextTemplate) {
		this.htmlTextTemplateName = htmlTextTemplate;
	}

	public void afterPropertiesSet() throws Exception {
		if(subjectTemplateName != null) {
			subjectTemplate = configuration.getTemplate(subjectTemplateName, locale);
		}
		if(plainTextTemplateName != null) {
			plainTextTemplate = configuration.getTemplate(plainTextTemplateName, locale);
		}
		if(htmlTextTemplateName != null) {
			htmlTextTemplate = configuration.getTemplate(htmlTextTemplateName, locale);
		}
	}

	public final void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public boolean isMultipart() {
		if(getAttachmentsPart() != null && getAttachmentsPart().length > 0) {
			return true;
		}
		if(plainTextTemplate != null && htmlTextTemplate != null) {
			return true;
		}
		if(htmlTextTemplate != null && getEmbeddedPart() != null && getEmbeddedPart().length > 0) {
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
						message.addBcc(getBcc());
					}
					if(getBcc() != null) {
						message.setBcc(getBcc());
					}
					if(subjectTemplate == null) {
						message.setSubject(getSubjectPart());
					} else {
						message.setSubject(FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, model));
					}
					String plainText = plainTextTemplate == null ? getPlainTextPart() : FreeMarkerTemplateUtils.processTemplateIntoString(plainTextTemplate, model);
					String htmlText = htmlTextTemplate == null ? getHtmlPart() : FreeMarkerTemplateUtils.processTemplateIntoString(htmlTextTemplate, model);
					if(plainText != null) {
						message.setText(plainText);
					}
					if(htmlText != null) {
						message.setText(htmlText, true);
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
