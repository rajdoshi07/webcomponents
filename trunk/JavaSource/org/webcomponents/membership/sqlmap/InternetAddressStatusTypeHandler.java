package org.webcomponents.membership.sqlmap;

import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.webcomponents.membership.InternetAddressStatus;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class InternetAddressStatusTypeHandler implements TypeHandlerCallback {
	
	private static final Logger logger = Logger.getLogger(InternetAddressStatus.class);

	public Object valueOf(String value) {
		try {
			return InternetAddressStatus.parse(value);
		} catch (ParseException e) {
			logger.warn("Unable to parse InternetAddressStatus " + value);
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
			setter.setNull(Types.NUMERIC);
		} else {
			InternetAddressStatus status = (InternetAddressStatus) parameter;
			setter.setInt(status.getOrdinal());
		}
	}

}
