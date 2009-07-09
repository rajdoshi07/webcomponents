package org.webcomponents.orm.ibatis.support;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

public abstract class SqlMapClientDaoSupport extends org.springframework.orm.ibatis.support.SqlMapClientDaoSupport {

	private String namespace;

	public SqlMapClientDaoSupport() {
		super();
	}

	@Required
	public final void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	protected String applyNamespace(String value) {
		return StringUtils.hasText(namespace) ? namespace + "." + value : value;
	}

}