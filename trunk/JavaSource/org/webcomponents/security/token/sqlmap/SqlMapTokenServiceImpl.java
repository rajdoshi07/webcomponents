package org.webcomponents.security.token.sqlmap;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.webcomponents.orm.ibatis.support.SqlMapClientDaoSupport;
import org.webcomponents.security.token.InvalidKeyException;
import org.webcomponents.security.token.Token;
import org.webcomponents.security.token.TokenException;
import org.webcomponents.security.token.TokenNotFoundException;
import org.webcomponents.security.token.TokenService;

public class SqlMapTokenServiceImpl extends SqlMapClientDaoSupport implements TokenService {
	
	private int defaultSpan = 604800000;	// 1 week

	public String createToken(Object key, Object value) {
		Calendar createdAt = Calendar.getInstance();
		Calendar expireAt = Calendar.getInstance();
		expireAt.setTime(createdAt.getTime());
		expireAt.add(Calendar.MILLISECOND, defaultSpan);
		return createToken(key, value, createdAt.getTime(), expireAt.getTime());
	}

	public String createToken(Object key, Object value, Date expireAt) {
		return createToken(key, value, new Date(), expireAt);
	}

	private String createToken(Object key, Object value, Date createdAt, Date expireAt) {
		UUID id = UUID.randomUUID();
		Token t = new Token();
		t.setId(id.toString());
		t.setKey(key);
		t.setValue(value);
		t.setExpireAt(expireAt);
		t.setInsertedAt(createdAt);
		getSqlMapClientTemplate().insert(applyNamespace("insertToken"), t);
		return t.getId();
	}

	public void setDefaultSpan(int defaultSpan) {
		this.defaultSpan = defaultSpan;
	}

	public Token getToken(String token, Object key) throws TokenException {
		Token rv = (Token) getSqlMapClientTemplate().queryForObject(applyNamespace("getToken"), token);
		if(rv == null) {
			throw new TokenNotFoundException(token);
		}
		if(!(rv.getKey() == null || rv.getKey().equals(key))) {
			throw new InvalidKeyException(token, key);
		}
		return rv;
	}

}
