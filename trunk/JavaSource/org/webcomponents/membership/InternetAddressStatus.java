package org.webcomponents.membership;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InternetAddressStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2286184842441885528L;
	private static int nextOrdinal = 0;
	private final int ordinal = nextOrdinal++;

	public static final InternetAddressStatus UNKNOWN = new InternetAddressStatus();
	public static final InternetAddressStatus VALID = new InternetAddressStatus();
	public static final InternetAddressStatus INVALID = new InternetAddressStatus();

	private static final InternetAddressStatus[] PRIVATE_VALUES = { UNKNOWN, VALID };
	public static final List<InternetAddressStatus> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

	private InternetAddressStatus() {
	}

	public String toString() {
		return String.valueOf(ordinal);
	}

	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj instanceof InternetAddressStatus && ((InternetAddressStatus) obj).ordinal == this.ordinal) {
			return true;
		}
		return false;
	}

	public int getOrdinal() {
		return ordinal;
	}
	
	public static InternetAddressStatus parse(String value) throws ParseException {
		InternetAddressStatus rv = parse(Integer.parseInt(value));
		if(rv == null) {
			throw new ParseException(value, 0);
		}
		return rv;
	}

	public static InternetAddressStatus parse(int value) {
		if(value < 0 || value >= PRIVATE_VALUES.length) {
			return null;
		}
		return PRIVATE_VALUES[value];
	}
}
