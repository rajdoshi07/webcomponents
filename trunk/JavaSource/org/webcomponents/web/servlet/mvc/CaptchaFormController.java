package org.webcomponents.web.servlet.mvc;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.webcomponents.membership.web.SubscriptionCommand;
import org.webcomponents.membership.web.SubscriptionFormController;

import com.octo.captcha.service.image.AbstractManageableImageCaptchaService;

public class CaptchaFormController extends FormController {

	private static final Logger logger = Logger.getLogger(SubscriptionFormController.class);
	private AbstractManageableImageCaptchaService captcha;

	public CaptchaFormController() {
		super();
	}

	public void setCaptcha(AbstractManageableImageCaptchaService captcha) {
		this.captcha = captcha;
	}

	protected void validateCaptcha(SubscriptionCommand command, Errors errors, HttpSession session) {
		if (captcha == null) {
			logger.info("Captcha service is null");
			return;
		}
		if(!errors.hasFieldErrors("humanTest")) {
			Boolean valid = captcha.validateResponseForID(session.getId(), command.getHumanTest());
			if(!valid) {
				errors.rejectValue("humanTest", "invalid");
			}
		}
	}

}