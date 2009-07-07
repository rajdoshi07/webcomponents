/*
 * Created on 10-nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.webcomponents.membership;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrea Bandera
 *
 */
public class AstrologicalSign {

	// Ordinal of next AstrologicalSign to be created
	private static int nextOrdinal = 0;
	
	// Assign an ordinal to this AstrologicalSign
	private final int ordinal = nextOrdinal++;
	
	public static final AstrologicalSign ARIES = new AstrologicalSign();
	public static final AstrologicalSign TAURUS = new AstrologicalSign();
	public static final AstrologicalSign GEMINI = new AstrologicalSign();
	public static final AstrologicalSign CANCER = new AstrologicalSign();
	public static final AstrologicalSign LEO = new AstrologicalSign();
	public static final AstrologicalSign VIRGO = new AstrologicalSign();
	public static final AstrologicalSign LIBRA = new AstrologicalSign();
	public static final AstrologicalSign SCORPIO = new AstrologicalSign();
	public static final AstrologicalSign SAGITTARIUS = new AstrologicalSign();
	public static final AstrologicalSign CAPRICORN = new AstrologicalSign();
	public static final AstrologicalSign AQUARIUS = new AstrologicalSign();
	public static final AstrologicalSign PISCES = new AstrologicalSign();

	private static final AstrologicalSign[] PRIVATE_VALUES = { ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES };
	public static final List<AstrologicalSign> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

	/**
	 * 
	 */
	private AstrologicalSign() {
	}

	public String toString() {
		return String.valueOf(ordinal);
	}
	
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj instanceof AstrologicalSign && ((AstrologicalSign) obj).ordinal == this.ordinal) {
			return true;
		}
		return false;
	}

	public int getOrdinal() {
		return ordinal;
	}
	
	public static AstrologicalSign parse(String value) throws ParseException {
		AstrologicalSign rv = parse(Integer.parseInt(value));
		if(rv == null) {
			throw new ParseException(value, 0);
		}
		return rv;
	}

	public static AstrologicalSign parse(int value) {
		if(value < 0 || value >= PRIVATE_VALUES.length) {
			return null;
		}
		return PRIVATE_VALUES[value];
	}
}
