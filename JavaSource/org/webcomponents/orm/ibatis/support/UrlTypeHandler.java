package org.webcomponents.orm.ibatis.support;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class UrlTypeHandler implements TypeHandlerCallback {
	
	private static final Logger logger = Logger.getLogger(UrlTypeHandler.class);

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		String value = getter.getString();
		if(getter.wasNull()) {
			return null;
		}
		return valueOf(value);
	}

	@Override
	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		if(parameter == null) {
			setter.setNull(Types.VARCHAR);
		} else {
			URL value = (URL) parameter;
			setter.setString(value.toString());
		}
	}

	@Override
	public Object valueOf(String value) {
		if(value == null) {
			return null;
		}
		try {
			return new URL(value);
		} catch (MalformedURLException e) {
			logger.info("Cannot convert database value " + StringUtils.quote(value) + " into java.net.URI. " + e.getMessage());
			return null;
		}
	}

}
