package org.webcomponents.mobile;

import java.io.Serializable;
import java.text.ParseException;

import org.springframework.util.StringUtils;

/**
 * @author Administrator
 */
public class MobileNumber implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1560275147406135662L;

	private static final String MOBILE_NUMBER_REG_EXP = "^\\+?\\d{3,15}";

	String number;
	
	String personal;

	public MobileNumber() {
	}

	/**
	 * @param number
	 * @throws ParseException 
	 */
	public MobileNumber(String number) throws ParseException {
		setNumber(number);
	}

	/**
	 * @param number
	 * @param personal
	 * @throws ParseException 
	 */
	public MobileNumber(String number, String personal) throws ParseException {
		setNumber(number);
		setPersonal(personal);
	}

	public static MobileNumber parse(String value) throws ParseException {
		if(StringUtils.hasText(value)) {
			String n = StringUtils.trimAllWhitespace(value);
			if(n.matches(MOBILE_NUMBER_REG_EXP)) {
				return new MobileNumber(value);
			}
		}
		throw new ParseException(value, 0);
	}
	
	public String toString() {
		return number;
	}

	/**
	 * @return Returns the personal.
	 */
	public String getPersonal() {
		return personal;
	}
	/**
	 * @param personal The personal to set.
	 */
	public void setPersonal(String personal) {
		this.personal = personal;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 * @throws ParseException 
	 */
	public void setNumber(String value) throws ParseException {
		if(StringUtils.hasText(value)) {
			String n = StringUtils.trimAllWhitespace(value);
			if(n.matches(MOBILE_NUMBER_REG_EXP)) {
				this.number = n;
				return;
			}
		}
		throw new ParseException(value, 0);
	}

	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		return obj.equals(this.number);
	}
}