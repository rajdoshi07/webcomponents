package org.webcomponents.resource;

public class ResourceOutOfCountException extends ResourceException {
	
	private final int maxResources;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3005266440664307619L;

	public ResourceOutOfCountException(int maxResources) {
		this.maxResources = maxResources;
	}

	@Override
	public String getMessage() {
		return "This resource exceeds the limit: " + maxResources;
	}

	public int getMaxResources() {
		return maxResources;
	}
	
}
