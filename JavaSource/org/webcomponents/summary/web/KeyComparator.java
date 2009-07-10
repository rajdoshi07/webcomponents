package org.webcomponents.summary.web;

import java.util.Comparator;

public class KeyComparator implements Comparator<String> {

	static final String ESTERO = "Estero";
	static final String ND = "N.D.";

	public int compare(String arg0, String arg1) {
		if(arg0.equals(arg1)) {
			return 0;
		}
		if(ESTERO.equals(arg0)) {
			return 1;
		}
		if(ESTERO.equals(arg1)) {
			return -1;
		}
		if(ND.equals(arg0)) {
			return 1;
		}
		if(ND.equals(arg1)) {
			return -1;
		}
		if("Nord".equalsIgnoreCase(arg0)) {
			return -1;
		}
		if("Centro".equalsIgnoreCase(arg0)) {
			return  "Nord".equalsIgnoreCase(arg1) ? 1 : -1;
		}
		if("Sud".equalsIgnoreCase(arg0)) {
			return "Isole".equalsIgnoreCase(arg1) ? -1 : 1;
		}
		if("Isole".equalsIgnoreCase(arg0)) {
			return 1;
		}
		return arg0.compareToIgnoreCase(arg1);
	}

}
