package org.webcomponents.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.webcomponents.membership.web.SubscribeMemberCommand;

public class PasswordValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return SubscribeMemberCommand.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		if(errors.hasFieldErrors("password")) {
			return;
		}
		SubscribeMemberCommand command = (SubscribeMemberCommand) obj;
		if(!(command.getPassword() == null || command.getPassword().equals(command.getPasswordConfirm()))) {
			errors.rejectValue("password", "unconfirmed");
		}
	}

}
