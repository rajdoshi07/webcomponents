package org.webcomponents.content;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.webcomponents.net.URIWrapper;

public interface ResourceDao {
	
	void put(URIWrapper path, File source) throws IOException;

	void remove(URIWrapper path) throws IOException;

	URIWrapper getAccessUri(URIWrapper path) throws IOException;
	
	File getFile(URIWrapper path) throws IOException;
	
	public void export(URIWrapper path, OutputStream out) throws IOException;
	
}
