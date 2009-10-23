package org.webcomponents.content;

import java.util.Date;

import org.webcomponents.net.URIWrapper;

public interface ResourceMetaData {
	
	public String getContentType();
	
	public long getSize();
	
	public URIWrapper getUri();
	
	public String getName();
	
	public int getStatus();
	
	public Date getCreatedAt();
	
	public String getDescription();
	
	public String getCaption();
}
