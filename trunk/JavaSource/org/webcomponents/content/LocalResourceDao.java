package org.webcomponents.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.webcomponents.net.URI;

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
		File r = root.getFile();
		File file = new File(r, path.toString());
		if(!file.exists()) {
			return;
		}
		InputStream is = new FileInputStream(file);
		try {
			IOUtils.copy(is, out);
		} finally {
			is.close();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void put(URI path, File source) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
