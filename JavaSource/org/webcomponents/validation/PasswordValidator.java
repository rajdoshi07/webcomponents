package org.webcomponents.validation;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		try {
			clazz.getMethod("getPassword", (Class[]) null);
			clazz.getMethod("getPasswordConfirm", (Class[]) null);
			return true;
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	public void validate(Object obj, Errors errors) {
		if(errors.hasFieldErrors("password")) {
			return;
		}
		try {
			String password = BeanUtils.getSimpleProperty(obj, "password");
			String passwordConfirm = BeanUtils.getSimpleProperty(obj, "passwordConfirm");
			if(password != null && !password.equals(passwordConfirm)) {
				errors.rejectValue("password", "unconfirmed");
			}
		} catch (NoSuchMethodException e){
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
