package org.webcomponents.resource;

import java.util.List;

public interface ResourceMetadataDao {
	
	public void edit(ResourceMetaData meta);
	
	public int bulkUpdate(List<? extends ResourceMetaData> meta);
	
	public List<? extends ResourceMetaData> list(String uri, int offset, int size);
	
	public Long count(String uri);
	
	public boolean remove(String uri);
	
	public boolean removeAll(String uri);

}
