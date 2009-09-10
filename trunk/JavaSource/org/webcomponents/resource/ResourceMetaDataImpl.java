package org.webcomponents.resource;

import java.net.URI;

import org.webcomponents.content.PersistentObject;

public class ResourceMetaDataImpl extends PersistentObject implements ResourceMetaData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024051589695058275L;

	private String contentType;
	
	private long size;
	
	private URI uri;
	
	private int status;
	
	private String description;
	
	private String caption;
	
	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public URI getUri() {
		return uri;
	}

	@Override
	public int getStatus() {
		return status;
	}
	
	@Override
	public String getId() {
		return uri == null ? null : uri.toString();
	}
		
	@Override
	public void setId(String id) {
		if(id == null) {
			setUri(null);
		}
		URI value = URI.create(id);
		setUri(value);
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public String getName() {
		if(uri != null) {
			String path = uri.getPath();
			int p = path.lastIndexOf('/');
			if(p > -1) {
				return path.substring(p + 1);
			}
			return path;
		}
		return null;
	}

}
