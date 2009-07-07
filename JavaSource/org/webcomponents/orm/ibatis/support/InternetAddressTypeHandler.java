package org.webcomponents.orm.ibatis.support;

import java.sql.SQLException;
import java.sql.Types;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class InternetAddressTypeHandler implements TypeHandlerCallback {
	
	private static Logger logger = Logger.getLogger(InternetAddressTypeHandler.class);

	public Object valueOf(String value) {
		try {
			return new InternetAddress(value);
		} catch (AddressException e) {
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
			InternetAddress address = (InternetAddress) parameter;
			setter.setString(address.getAddress());
		}
	}

}
