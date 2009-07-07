package org.webcomponents.orm.ibatis.support;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.event.RowHandler;

public abstract class ExportRowHandler implements RowHandler {

	protected static final Logger logger = Logger.getLogger(ExportRowHandler.class);
	protected Writer out;

	public ExportRowHandler() {
		super();
	}

	public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
	}

	public void handleRow(Object valueObj) {
		try {
			handleRow(valueObj, out);
		} catch (IOException e) {
			logger.warn("Unable to write object " + valueObj.toString() + " due to " + e.getMessage());
		}
	}

	protected abstract void handleRow(Object valueObj, Writer out) throws IOException;

}