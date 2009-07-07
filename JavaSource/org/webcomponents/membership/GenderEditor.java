package org.webcomponents.membership;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;

import org.springframework.util.StringUtils;

public class GenderEditor extends PropertyEditorSupport {

	public void setAsText(String text) {
		if (StringUtils.hasText(text)) {
			try {
				Gender value = Gender.parse(text);
				setValue(value);
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse gender: " + ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}

	public String getAsText() {
		Gender value = (Gender) getValue();
		return (value != null ? value.toString() : "");
	}
	
}
