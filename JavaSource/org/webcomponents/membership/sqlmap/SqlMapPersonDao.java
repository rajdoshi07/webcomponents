package org.webcomponents.membership.sqlmap;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.webcomponents.membership.InternetAddressStatus;
import org.webcomponents.membership.MemberStatus;
import org.webcomponents.membership.Person;
import org.webcomponents.membership.PersonDao;
import org.webcomponents.orm.ibatis.support.ExportRowHandler;
import org.webcomponents.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 
 * @author andreab
 *
 */
@Repository
public class SqlMapPersonDao extends SqlMapClientDaoSupport implements PersonDao {

	private static final int STEP = 250;

	@Autowired
	private ApplicationContext context;

	private String exportRowHandlerId;

	public String insertPerson(Person person, String password) {
		getSqlMapClientTemplate().insert(applyNamespace("insertPerson"), person);
		return person.getUsername();
	}

	public Person getPerson(Object username) {
		String s = getUsernameAsString(username);
		return (Person) getSqlMapClientTemplate().queryForObject(applyNamespace("getPerson"), s);
	}

	public boolean updatePerson(Object username, Person person) {
		return getSqlMapClientTemplate().update(applyNamespace("updatePerson"), person) == 1;
	}

	public boolean updateEmail(Object username, InternetAddress email) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", getUsernameAsString(username));
		map.put("email", email);
		return getSqlMapClientTemplate().update(applyNamespace("updateEmail"), map) == 1;
	}

	public boolean updatePassword(Object username, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", getUsernameAsString(username));
		map.put("password", password);
		return getSqlMapClientTemplate().update(applyNamespace("updatePassword"), map) == 1;
	}

	public boolean updateStatus(Object username, MemberStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", getUsernameAsString(username));
		map.put("status", status);
		return getSqlMapClientTemplate().update(applyNamespace("updateStatus"), map) == 1;
	}

	/**
	 * @param username
	 * @return
	 */
	protected String getUsernameAsString(Object username) {
		if(username == null) {
			return null;
		}
		if(username instanceof InternetAddress) {
			return ((InternetAddress) username).getAddress();
		}
		return username.toString();
	}

	public boolean deletePerson(Object username) {
		String s = getUsernameAsString(username);
		return getSqlMapClientTemplate().delete(applyNamespace("deletePerson"), s) == 1;
	}

	public boolean updateAddressStatus(InternetAddress address, InternetAddressStatus status) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		model.put("status", status);
		model.put("email", address);
		return getSqlMapClientTemplate().update(applyNamespace("updateEmailStatus"), model) == 1;
	}

	@SuppressWarnings("unchecked")
	public List<Person> findPersonsByKey(List<Object> keys) {
		int size = keys.size();	// ORA-01795: il numero massimo di espressioni in un elenco è 1000
		int r = size % STEP;
		List<Person> rv = getSqlMapClientTemplate().queryForList(applyNamespace("findByKey"), keys.subList(size - r, size));

		int n = size / STEP;
		int i = 0;
		while(i < n * STEP) {
			List<Object> subList = keys.subList(i, i+STEP);
			List<Person> l = getSqlMapClientTemplate().queryForList(applyNamespace("findByKey"), subList);
			rv.addAll(l);
			i += STEP;
		}
		return rv;
	}

	public void exportPersonsByKey(List<Object> keys, Writer out) {
		ExportRowHandler rowHandler = (ExportRowHandler) context.getBean(exportRowHandlerId);
		rowHandler.setOut(out);
		
		int size = keys.size();	// ORA-01795: il numero massimo di espressioni in un elenco è 1000
		int r = size % STEP;
		getSqlMapClientTemplate().queryWithRowHandler(applyNamespace("findByKey"), keys.subList(size - r, size), rowHandler);

		int n = size / STEP;
		int i = 0;
		while(i < n * STEP) {
			getSqlMapClientTemplate().queryWithRowHandler(applyNamespace("findByKey"), keys.subList(i, i+STEP), rowHandler);
			i += STEP;
		}
	}

	public void setExportRowHandlerId(String exportRowHandlerId) {
		this.exportRowHandlerId = exportRowHandlerId;
	}

}