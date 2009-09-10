package org.webcomponents.resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public interface ResourceDao {
	
	void put(URI path, File source) throws IOException;

	void remove(URI path) throws IOException;

	URI getAccessUri(URI path) throws IOException;
	
	File getFile(URI path) throws IOException;
	
	public void export(URI path, OutputStream out) throws IOException;
	
}
