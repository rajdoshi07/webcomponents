package org.webcomponents.resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public interface ResourceDao {
	
	void put(URI path, File source) throws IOException;

	void remove(String path) throws IOException;

	String getAccessUrl(String path) throws IOException;
	
	URI getAccessUri(URI path) throws IOException, URISyntaxException;
	
	File getFile(URI path) throws IOException;
	
	public void export(String path, OutputStream out) throws IOException;
	
}
