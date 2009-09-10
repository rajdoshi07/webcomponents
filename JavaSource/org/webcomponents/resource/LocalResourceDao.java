package org.webcomponents.resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

public class LocalResourceDao implements ResourceDao {
	
	private Resource root;

	@Override
	public URI getAccessUri(URI path) throws IOException {
		return path;
	}

	@Override
	public File getFile(URI path) throws IOException {
		File r = root.getFile();
		return new File(r, path.toString());
	}

	@Override
	public void remove(URI path) throws IOException {
		File r = root.getFile();
		File file = new File(r, path.toString());
		if(file.exists()) {
			file.delete();
		}
	}

	@Required
	public void setRoot(Resource root) {
		this.root = root;
	}

	@Override
	public void export(URI path, OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void put(URI path, File source) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
