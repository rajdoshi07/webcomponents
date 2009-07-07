package org.webcomponents.mobile;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;

import org.springframework.util.StringUtils;

public class MobileNumberEditor extends PropertyEditorSupport {

	public void setAsText(String text) {
		if (StringUtils.hasText(text)) {
			try {
				MobileNumber value = new MobileNumber(text);
				setValue(value);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Could not parse mobile number: " + e.getMessage());
			}
		} else {
			setValue(null);
		}
	}

	public String getAsText() {
		MobileNumber value = (MobileNumber) getValue();
		return (value != null ? value.toString() : "");
	}

}
