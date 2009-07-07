package org.webcomponents.orm.ibatis.support;

import java.sql.SQLException;
import java.sql.Types;

import org.springframework.util.StringUtils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class ArrayTypeHandler implements TypeHandlerCallback {

	public Object getResult(ResultGetter getter) throws SQLException {
		String value = getter.getString();
		if(getter.wasNull()) {
			return null;
		}
		return valueOf(value);
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		if(parameter == null) {
			setter.setNull(Types.CHAR);
		} else {
			String value = StringUtils.arrayToCommaDelimitedString((Object[]) parameter);
			setter.setString(value);
		}
	}

	public Object valueOf(String value) {
		if(value == null) {
			return null;
		} else {
			return StringUtils.commaDelimitedListToStringArray(value);
		}
	}

}
