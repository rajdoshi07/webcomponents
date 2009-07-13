package org.webcomponents.web.servlet.mvc;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.javamail.InternetAddressEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class FormController {

	protected String[] allowedFields;
	protected String[] requiredFields;
	protected Validator[] validators;
	protected String successView;

	protected Map<String, Object> referenceData;

	public FormController() {
		super();
	}

	public final void setAllowedFields(String[] allowedFields) {
		this.allowedFields = allowedFields;
	}

	public final void setRequiredFields(String[] requiredFields) {
		this.requiredFields = requiredFields;
	}

	public void setValidators(Validator[] validators) {
		this.validators = validators;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	@ModelAttribute
	public void putValidatorsData(Model model) {
		if(referenceData == null) {
			return;
		}
		model.addAllAttributes(referenceData);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, Locale locale) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
		binder.registerCustomEditor(InternetAddress.class, new InternetAddressEditor());
		if(allowedFields != null) {
			binder.setAllowedFields(allowedFields);
		}
		if(requiredFields != null) {
			binder.setRequiredFields(requiredFields);
		}
	}

	@SuppressWarnings("unchecked")
	public void init() throws Exception {
		if(this.validators == null) {
			return;
		}
		referenceData = new HashMap();
		for(int i = 0; i < this.validators.length; i++) {
			Validator validator = this.validators[i];
			Method[] methods = validator.getClass().getMethods();
			for(int j = 0; j < methods.length; j++) {
				Method m = methods[j];
				String name = m.getName();
				Class returnType = m.getReturnType();
				if(name.startsWith("get") && (Map.class.isAssignableFrom(returnType) || Collection.class.isAssignableFrom(returnType))) {
					name = name.substring("get".length());
					name = StringUtils.uncapitalize(name);
					Object o = m.invoke(validator, (Object[]) null);
					referenceData.put(name, o);
				}
			}
		}
	}

	protected void validateCommand(Object command, Errors errors) {
		if(this.validators == null) {
			return;
		}
		for(int i = 0; i < this.validators.length; i++) {
			if(validators[i].supports(command.getClass())) {
				validators[i].validate(command, errors);
			}
		}
	}

}