package org.webcomponents.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.webcomponents.net.URIWrapper;

public class LocalResourceDao implements ResourceDao {
	
	private Resource root;

	@Override
	public URIWrapper getAccessUri(URIWrapper path) throws IOException {
		return path;
	}

	@Override
	public File getFile(URIWrapper path) throws IOException {
		File r = root.getFile();
		return new File(r, path.toString());
	}

	@Override
	public void remove(URIWrapper path) throws IOException {
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
	public void export(URIWrapper path, OutputStream out) throws IOException {
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
	public void put(URIWrapper path, File source) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
