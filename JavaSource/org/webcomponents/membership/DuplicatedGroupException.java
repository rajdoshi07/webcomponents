package org.webcomponents.membership;

public class DuplicatedGroupException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 560682391442094291L;

	public DuplicatedGroupException(String name) {
		super(name + " group already exists");
	}

}
