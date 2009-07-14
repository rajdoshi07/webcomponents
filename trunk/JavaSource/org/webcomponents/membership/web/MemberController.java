package org.webcomponents.membership.web;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.webcomponents.mail.TokenEmailSender;
import org.webcomponents.membership.Member;
import org.webcomponents.membership.MemberNotFoundException;
import org.webcomponents.membership.Membership;
import org.webcomponents.security.token.InvalidTokenException;
import org.webcomponents.security.token.Token;
import org.webcomponents.security.token.TokenExpiredException;

@Controller
public class MemberController {
	
	private static final String[] EMPTY_KEYS = new String[0];

	private Membership membership;
	
	private TokenEmailSender validationMailSender;

	private String textFileCharset = "ISO-8859-1";

	@Required
	public final void setMembership(Membership membership) {
		this.membership = membership;
	}
	
	public final void setValidationMailSender(TokenEmailSender activationMailSender) {
		this.validationMailSender = activationMailSender;
	}
	
	public void setTextFileCharset(String textFileCharset) {
		this.textFileCharset = textFileCharset;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringMultipartFileEditor(textFileCharset));
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	@ModelAttribute("results")
	public List<? extends Member> search(@RequestParam(value="q", required=false) String query) {
		Object[] keys = getKeys(query);
		if(ObjectUtils.isEmpty(keys)) {
			return null;
		}
		List<? extends Member> rv = membership.findMembersByKey(Arrays.asList(keys));
		return rv;
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public void export(@RequestParam(value="q", required=false) String query, Writer out) {
		Object[] keys = getKeys(query);
		if(ObjectUtils.isEmpty(keys)) {
			return;
		}
		membership.exportMembersByKey(Arrays.asList(keys), out);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public void validateEmail(@RequestParam("code") String token) throws Exception {
		Token t = validationMailSender.getTokenService().getToken(token, validationMailSender.getKey());
		String email = (String) t.getValue();
		InternetAddress mail = new InternetAddress(email);
		try {
			if(t.isExpiredNow()) {
				retry(email);
				throw new TokenExpiredException(t);
			} else {
				membership.validateEmail(mail);
			}
		} catch (MemberNotFoundException e) {
			throw new InvalidTokenException(t, e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ModelAttribute("profile")
	public Member getMember(@RequestParam("username") Object username) throws IOException {
		return membership.getMember(username);
	}

	private void retry(String username) throws IOException, MemberNotFoundException {
		Member member = membership.getMember(username);
		if(member == null) {
			throw new MemberNotFoundException(username);
		}
		validationMailSender.sendEmail(member);
	}

	private String[] getKeys(String query) {
		if(StringUtils.hasText(query)) {
			String[] rv = StringUtils.tokenizeToStringArray(query, " \n");
			return ObjectUtils.isEmpty(rv) ? EMPTY_KEYS : rv;
		}
		return EMPTY_KEYS;
	}

}
