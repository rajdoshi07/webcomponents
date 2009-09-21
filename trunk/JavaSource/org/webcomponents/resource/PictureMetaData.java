package org.webcomponents.resource;

import java.net.URI;


public class PictureMetaData extends ResourceMetaDataImpl {
	
	public static final String REL_HI_RES = "hi";
	public static final String REL_THUMB = "thumb";
	public static final String REL_LOW_RES = "low";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1341864927923537050L;
	private int resolution;
	private URI hiResolutionPicture;
	private int width;
	private int height;
	private String relationship;

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public URI getHiResolutionPicture() {
		return hiResolutionPicture;
	}
	
	public void setHiResolutionPicture(URI uri) {
		this.hiResolutionPicture = uri;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getRelationship() {
		return this.relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getHiResolutionPictureName() {
		if(hiResolutionPicture != null) {
			String path = hiResolutionPicture.getPath();
			int p = path.lastIndexOf('/');
			if(p > -1) {
				return path.substring(p + 1);
			}
			return path;
		}
		return null;
	}
}
