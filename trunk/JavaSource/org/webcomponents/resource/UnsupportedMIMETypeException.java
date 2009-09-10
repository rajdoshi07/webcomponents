package org.webcomponents.resource;

import eu.medsea.mimeutil.MimeType;

public class UnsupportedMIMETypeException extends ResourceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MimeType mimeType;
	
	private String filename;

	public UnsupportedMIMETypeException() {
		super();
	}

	public UnsupportedMIMETypeException(MimeType type, String filename) {
		super();
		mimeType = type;
		this.filename = filename;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String getMessage() {
		return "Unsupported MIME Type " + mimeType + " for resource " + filename;
	}

}
