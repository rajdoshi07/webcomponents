package org.webcomponents.sqlmap;

import java.sql.SQLException;
import java.sql.Types;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class CharacterTypeHandler implements TypeHandlerCallback {

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
			setter.setString(parameter.toString());
		}
	}

	public Object valueOf(String value) {
		if(value == null) {
			return null;
		} else {
			return new Character(value.charAt(0));
		}
	}

}
