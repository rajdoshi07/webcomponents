package org.webcomponents.membership.web;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.webcomponents.membership.DuplicatedEmailException;
import org.webcomponents.membership.Member;
import org.webcomponents.membership.MemberNotFoundException;
import org.webcomponents.membership.Membership;
import org.webcomponents.web.servlet.mvc.FormController;

@Controller
@SessionAttributes("email")
public class EditEmailController extends FormController {
	
	private Membership membership;
	
	@ModelAttribute("email")
	public InternetAddress getEmail() throws IOException {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		String username = principal.getName();
		Member member = this.membership.getMember(username);
		return member.getEmail();
	}

	@RequestMapping(method=RequestMethod.GET)
	public Map<String, Object> setupForm() {
		return Collections.emptyMap();
	}

	@RequestMapping(method=RequestMethod.POST)
	public String editEmail(@ModelAttribute("email")InternetAddress email, Errors errors) throws MemberNotFoundException, DuplicatedEmailException, IOException {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		String username = principal.getName();
		try {
			membership.editMemberEmail(username, email);
			return this.successView;
		} catch (IllegalArgumentException e) {
			errors.reject("address", "typeMismatch");
		}
		return null;
	}

	@Required
	public final void setMembership(Membership membership) {
		this.membership = membership;
	}

}
