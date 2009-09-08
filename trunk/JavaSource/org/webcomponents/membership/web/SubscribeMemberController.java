package org.webcomponents.membership.web;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.userdetails.jdbc.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.webcomponents.membership.DuplicatedEmailException;
import org.webcomponents.membership.DuplicatedUsernameException;
import org.webcomponents.membership.InvalidPasswordException;
import org.webcomponents.membership.InvalidUsernameException;
import org.webcomponents.membership.MemberStatus;
import org.webcomponents.membership.Membership;
import org.webcomponents.web.servlet.mvc.CaptchaFormController;

@Controller
@SessionAttributes("subscription")
public class SubscribeMemberController extends CaptchaFormController {
	
	private Membership membership;
	
	private JdbcUserDetailsManager detailsManager;
	
	private String defaultGroup;
	
	@ModelAttribute("subscription")
	public SubscribeMemberCommand getCommand() {
		SubscribeMemberCommand rv = new SubscribeMemberCommand();
		rv.setStatus(MemberStatus.PENDING);
		return rv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("subscription") SubscribeMemberCommand command, Errors errors, HttpSession session) throws IOException {
		validateCaptcha(command, errors, session);
		validateCommand(command, errors);
		if(!errors.hasErrors()) {
			try {
				membership.insertMember(command, command.getPassword());
				if(detailsManager != null) {
					detailsManager.addUserToGroup(command.getUsername(), defaultGroup);
				}
				return successView;
			} catch (InvalidUsernameException e) {
				errors.rejectValue("screenName", "invalid");
			} catch (InvalidPasswordException e) {
				errors.rejectValue("password", "invalid");
			} catch (DuplicatedUsernameException e) {
				errors.rejectValue("screenName", "duplicate");
			} catch (DuplicatedEmailException e) {
				errors.rejectValue("email", "duplicate");
			}
		}
		return null;
	}

	@Required
	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public void setDetailsManager(JdbcUserDetailsManager detailsManager) {
		this.detailsManager = detailsManager;
	}

	public void setDefaultGroup(String defaultGroup) {
		this.defaultGroup = defaultGroup;
	}
	
}
