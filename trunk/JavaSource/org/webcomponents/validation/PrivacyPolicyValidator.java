package org.webcomponents.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.webcomponents.membership.Subscription;

public class PrivacyPolicyValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return Subscription.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		Subscription subscription = (Subscription) command;
		if(!(errors.hasFieldErrors("privacyPolicyAccepted") || subscription.isPrivacyPolicyAccepted())) {
			errors.rejectValue("privacyPolicyAccepted", "invalid");
		}
	}

}
