package org.webcomponents.competition;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.webcomponents.mail.MailContentTemplate;
import org.webcomponents.membership.Member;

public class PrizeEmailSender {
	
	private Map<String, MailContentTemplate> templates;
	
	private JavaMailSender mailer;
	
	public void sendEmail(Prize prize, Member person) {
		MailContentTemplate template = getTemplate(prize);
		if(template == null) {
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("prize", prize);
		map.put("person", person);
		MimeMessagePreparator preparator = template.createMimeMessagePreparator(map, person.getEmail());
		this.mailer.send(preparator);
	}

	public final void setMailer(JavaMailSender mailer) {
		this.mailer = mailer;
	}

	public final void setTemplates(Map<String, MailContentTemplate> mailTemplate) {
		this.templates = mailTemplate;
	}

	protected MailContentTemplate getTemplate(Prize prize) {
		return templates.get(prize.getClass().getName());
	}
}
