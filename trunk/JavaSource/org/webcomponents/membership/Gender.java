package org.webcomponents.membership;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * TODO: Credo sia più opportuno rappresentare i sessi con il singolo carattere M o F (anche nel db). E definire un PropertyEditor
 * per il binding automatico
 * @author andreab
 *
 */
public class Gender implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6471475775385386299L;
	
	private final String code;
	private static int nextOrdinal = 0;
	private final int ordinal = nextOrdinal++;

	public static final Gender MALE = new Gender("M");
	public static final Gender FEMALE = new Gender("F");

	private static final Gender[] PRIVATE_VALUES = { MALE, FEMALE };
	public static final List<Gender> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

	private Gender(String code) {
		this.code = code;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public static Gender parse(String value) throws ParseException {
		if(StringUtils.hasText(value)) {
			for (int i = 0; i < PRIVATE_VALUES.length; i++) {
				if (PRIVATE_VALUES[i].toString().equalsIgnoreCase(value)) {
					return PRIVATE_VALUES[i];
				}
			}
		}
		throw new ParseException(value, 0);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return code;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof String) {
			if(toString().equalsIgnoreCase((String) obj)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
