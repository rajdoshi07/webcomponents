package org.webcomponents.security.token;

import java.util.Date;

import org.springframework.util.StringUtils;

public class Token {
	
	private String id;
	
	private Object key;
	
	private Object value;
	
	private Date insertedAt;
	
	private Date expireAt;

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = StringUtils.delete(id, "-");
	}

	public final Object getKey() {
		return key;
	}

	public final void setKey(Object key) {
		this.key = key;
	}

	public final Object getValue() {
		return value;
	}

	public final void setValue(Object value) {
		this.value = value;
	}

	public final Date getInsertedAt() {
		return insertedAt;
	}

	public final void setInsertedAt(Date insertedAt) {
		this.insertedAt = insertedAt;
	}

	public final Date getExpireAt() {
		return expireAt;
	}

	public final void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}
	
	public boolean isExpiredAt(Date date) {
		return date.after(getExpireAt());
	}
	
	public boolean isExpiredNow() {
		return isExpiredAt(new Date());
	}
}
