package org.webcomponents.content;

public class ContentNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4763558130397949105L;

	public ContentNotFoundException(String id) {
		super("Publishing " + id + " not found");
	}
	
	

}
