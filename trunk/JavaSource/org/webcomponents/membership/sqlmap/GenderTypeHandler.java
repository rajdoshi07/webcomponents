package org.webcomponents.membership.sqlmap;

import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.webcomponents.membership.Gender;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class GenderTypeHandler implements TypeHandlerCallback {
	
	private static final Logger logger = Logger.getLogger(GenderTypeHandler.class);

	public Object valueOf(String value) {
		try {
			return Gender.parse(value);
		} catch (ParseException e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	public Object getResult(ResultGetter getter) throws SQLException {
		String value = getter.getString();
		if(getter.wasNull()) {
			return null;
		}
		return valueOf(value);
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		if(parameter == null) {
			setter.setNull(Types.VARCHAR);
		} else {
			setter.setString(parameter.toString());
		}
	}

}
