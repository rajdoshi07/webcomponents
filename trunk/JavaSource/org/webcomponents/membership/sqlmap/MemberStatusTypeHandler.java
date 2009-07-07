package org.webcomponents.membership.sqlmap;

import java.sql.SQLException;
import java.sql.Types;

import org.webcomponents.membership.MemberStatus;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class MemberStatusTypeHandler implements TypeHandlerCallback {

	public Object valueOf(String value) {
		return MemberStatus.valueOf(value);
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
			MemberStatus status = (MemberStatus) parameter;
			int ordinal = status.getOrdinal();
			setter.setInt(ordinal);
		}
	}

}
