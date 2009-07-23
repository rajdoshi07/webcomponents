package org.webcomponents.orm.ibatis.support;

import java.sql.SQLException;
import java.sql.Types;

import org.ho.yaml.Yaml;
import org.springframework.util.StringUtils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class YamlHandler implements TypeHandlerCallback {

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
			String value = Yaml.dump(parameter);
			setter.setString(value);
		}
	}

	@Override
	public Object valueOf(String value) {
		if(!StringUtils.hasText(value)) {
			return null;
		}
		Object rv = Yaml.load(value);
		return rv;
	}

}
