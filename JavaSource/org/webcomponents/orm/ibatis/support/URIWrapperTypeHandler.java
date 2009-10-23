package org.webcomponents.orm.ibatis.support;

import java.sql.SQLException;
import java.sql.Types;

import org.webcomponents.net.URIWrapper;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class URIWrapperTypeHandler implements TypeHandlerCallback {
	
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
			URIWrapper value = (URIWrapper) parameter;
			setter.setString(value.toString());
		}
	}

	@Override
	public Object valueOf(String value) {
		if(value == null) {
			return null;
		}
		return URIWrapper.create(value);
	}

}
