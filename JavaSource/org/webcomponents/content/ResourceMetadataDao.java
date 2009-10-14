package org.webcomponents.content;

import java.util.List;

public interface ResourceMetadataDao {
	
	public void insert(ResourceMetaData meta);
	
	public boolean update(ResourceMetaData meta);
	
	public int bulkUpdate(List<? extends ResourceMetaData> meta);
	
	public List<? extends ResourceMetaData> list(String uri, int offset, int size, String rel);
	
	public Long count(String uri);
	
	public boolean remove(String uri);
	
	public boolean removeAll(String uri);

}
