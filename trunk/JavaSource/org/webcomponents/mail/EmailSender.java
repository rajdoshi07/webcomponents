package org.webcomponents.mail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public abstract class EmailSender {

	protected static final Logger logger = Logger.getLogger(EmailSender.class);
	protected Map<String, MailContentTemplate> templates;
	private JavaMailSender mailer;

	public EmailSender() {
		super();
	}

	public final void setMailer(JavaMailSender mailer) {
		this.mailer = mailer;
	}

	public final void setTemplates(Map<String, MailContentTemplate> templates) {
		this.templates = templates;
	}

	public void sendEmail(Object obj) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		String[] names = attributes.getAttributeNames(RequestAttributes.SCOPE_REQUEST);
		for(int i = 0; names != null && i < names.length; i++) {
			String name = names[i];
			Object attribute = attributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
			model.put(name, attribute);
		}
		
		populateModel(model, obj);
		
		MailContentTemplate template = getTemplate(obj);
		if(template == null) {
			logger.warn("No template found to send object " + ObjectUtils.nullSafeToString(obj));
			return;
		}
		InternetAddress receiver = getReceiver(obj);
		if(receiver == null) {
			logger.warn("No receiver found to send object " + ObjectUtils.nullSafeToString(obj));
			return;
		}
		MimeMessagePreparator preparator = template.createMimeMessagePreparator(model, receiver);
		this.mailer.send(preparator);
	}

	protected void populateModel(Map<String, Object> model, Object obj) {
		model.put("object", obj);
	}

	private MailContentTemplate getTemplate(Object obj) {
		if(templates == null || templates.isEmpty()) {
			return null;
		}
		String key = getTemplateKey(obj);
		if(key == null) {
			return null;
		}
		return templates.get(key);
	}

	protected String getTemplateKey(Object obj) {
		Set<String> keys = templates.keySet();
		Iterator<String> i = keys.iterator();
		if(i.hasNext()) {
			return i.next();
		}
		return null;
	}
	
	protected abstract InternetAddress getReceiver(Object obj);

}