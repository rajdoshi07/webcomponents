package org.webcomponents.orm.ibatis.support;

import java.sql.SQLException;
import java.sql.Types;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class GrantedAuthorityTypeHandler implements TypeHandlerCallback {

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		String value = getter.getString();
		if(getter.wasNull()) {
			return null;
		}
		return valueOf(value);
	}

	@Override
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		if(parameter == null) {
			setter.setNull(Types.VARCHAR);
		} else {
			GrantedAuthority value = (GrantedAuthority) parameter;
			setter.setString(value.getAuthority());
		}
	}

	@Override
	public Object valueOf(String value) {
		if(value == null) {
			return null;
		}
		return new GrantedAuthorityImpl(value);
	}

}
