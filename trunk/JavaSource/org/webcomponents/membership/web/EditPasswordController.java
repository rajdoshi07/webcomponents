package org.webcomponents.membership.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.webcomponents.membership.InvalidPasswordException;
import org.webcomponents.membership.MemberNotFoundException;
import org.webcomponents.membership.Membership;
import org.webcomponents.web.servlet.mvc.FormController;

@Controller
@SessionAttributes("command")
public class EditPasswordController extends FormController {
	
	private Membership membership;
	
	@ModelAttribute("command")
	public EditPasswordCommand getCommand() {
		return new EditPasswordCommand();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String processSubmit(@ModelAttribute("command")EditPasswordCommand command, Errors errors) throws IOException {
		validateCommand(command, errors);
		if(!errors.hasErrors()) {
			Authentication principal = SecurityContextHolder.getContext().getAuthentication();
			try {
				membership.editMemberPassword(principal.getName(), command.getPassword());
				return successView;
			} catch (MemberNotFoundException e) {
				errors.reject(e.getClass().getSimpleName());
			} catch (InvalidPasswordException e) {
				errors.rejectValue("password", "invalid");
			}
		}
		return null;
	}

	@Required
	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	protected Membership getMembership() {
		return membership;
	}

}
