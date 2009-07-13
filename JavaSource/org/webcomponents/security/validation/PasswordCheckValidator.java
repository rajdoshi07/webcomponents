package org.webcomponents.security.validation;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordCheckValidator implements Validator {
	
	private AuthenticationProvider authenticationProvider;

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		try {
			clazz.getMethod("getPasswordCheck", (Class[]) null);
			return true;
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	public void validate(Object obj, Errors errors) {
		if(errors.hasFieldErrors("passwordCheck")) {
			return;
		}
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		if(principal == null) {
			errors.rejectValue("passwordCheck", "not_logged");
		}
		try {
			String password = BeanUtils.getSimpleProperty(obj, "passwordCheck");
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal.getName(), password);
			authenticationProvider.authenticate(authentication);
		} catch (AuthenticationException e) {
			errors.rejectValue("passwordCheck", "invalid");
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

}
