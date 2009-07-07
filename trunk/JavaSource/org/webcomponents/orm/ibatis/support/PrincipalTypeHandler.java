package org.webcomponents.orm.ibatis.support;

import java.security.Principal;
import java.sql.SQLException;
import java.sql.Types;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.sun.security.auth.UserPrincipal;

public class PrincipalTypeHandler implements TypeHandlerCallback {

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
			Principal value = (Principal) parameter;
			setter.setString(value.getName());
		}
	}

	@Override
	public Object valueOf(String value) {
		if(value == null) {
			return null;
		}
		UserPrincipal rv = new UserPrincipal(value);
		return rv;
	}

}
