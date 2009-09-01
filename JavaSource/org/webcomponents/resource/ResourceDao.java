package org.webcomponents.resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface ResourceDao {
	
	void put(String path, File source) throws IOException;

	void remove(String path) throws IOException;

	String getAccessUrl(String path) throws IOException;
	
	URI getAccessUri(URI path) throws IOException, URISyntaxException;
	
}
