package org.webcomponents.resource;

import java.net.URI;
import java.util.Date;

public interface ResourceMetaData {
	
	public String getContentType();
	
	public long getSize();
	
	public URI getUri();
	
	public String getName();
	
	public int getStatus();
	
	public Date getCreatedAt();
	
	public String getDescription();
	
	public String getCaption();
}
