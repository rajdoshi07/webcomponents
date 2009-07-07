package org.webcomponents.orm.ibatis.support;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

public class CsvRowHandler extends ExportRowHandler {
	
	private String separator;
	
	private List<String> properties;
	
	private String[] row;

	public void handleRow(Object valueObject, Writer out) throws IOException {
		if(properties == null) {
			out.write(valueObject.toString() + "\n");
		} else {
			if(row == null) {
				out.write(StringUtils.collectionToDelimitedString(properties, separator) + "\n");
				row = new String[properties.size()];
			}
			int j = 0;
			for(String property : properties) {
				try {
					String value = BeanUtils.getProperty(valueObject, property);
					row[j] = value == null ? "" : value;
				} catch (IllegalAccessException e) {
					logger.warn("Unable to get property " + StringUtils.quote(property) + " on bean " + valueObject.getClass().getName() + " due to " + e.getMessage());
					row[j] = null;
				} catch (InvocationTargetException e) {
					logger.warn("Unable to get property " + StringUtils.quote(property) + " on bean " + valueObject.getClass().getName() + " due to " + e.getMessage());
					row[j] = null;
				} catch (NoSuchMethodException e) {
					logger.warn("Unable to get property " + StringUtils.quote(property) + " on bean " + valueObject.getClass().getName() + " due to " + e.getMessage());
					row[j] = null;
				}
				j++;
			}
			out.write(StringUtils.arrayToDelimitedString(row, separator) + "\n");
		}
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public List<String> getProperties() {
		return properties;
	}

	public void setProperties(List<String> properties) {
		this.properties = properties;
	}

}
