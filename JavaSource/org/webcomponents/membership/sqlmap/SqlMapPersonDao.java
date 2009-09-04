package org.webcomponents.membership.sqlmap;

import java.io.Writer;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.webcomponents.membership.InternetAddressStatus;
import org.webcomponents.membership.MemberStatus;
import org.webcomponents.membership.Person;
import org.webcomponents.membership.PersonDao;
import org.webcomponents.orm.ibatis.support.ExportRowHandler;

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

	private String insertPersonStatement = "insertPerson";
	private String personStatement = "getPerson";

	private String updatePersonStatement = "updatePerson";

	private String updateEmailStatement = "updateEmail";

	private String updatePasswordStatement = "updatePassword";

	private String updateStatusStatement = "updateStatus";

	private String deletePersonStatement = "deletePerson";

	private String updateEmailStatusStatement = "updateEmailStatus";

	private String findByKeyStatement = "findByKey";

	public String insertPerson(Person person, String password, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		try {
			model.put("person", person);
			model.put("password", password);
			model.put("updatedBy", principal);
			getSqlMapClientTemplate().insert(insertPersonStatement, model);
			return person.getUsername();
		} finally {
			model.clear();
		}
	}

	public Person getPerson(Object username) {
		String s = getUsernameAsString(username);
		return (Person) getSqlMapClientTemplate().queryForObject(personStatement, s);
	}

	public boolean updatePerson(Object username, Person person, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		try {
			model.put("username", getUsernameAsString(username));
			model.put("person", person);
			model.put("updatedBy", principal);
			return getSqlMapClientTemplate().update(updatePersonStatement, model) == 1;
		} finally {
			model.clear();
		}
	}

	public boolean updateEmail(Object username, InternetAddress email, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		try {
			model.put("username", getUsernameAsString(username));
			model.put("email", email);
			model.put("updatedBy", principal);
			return getSqlMapClientTemplate().update(updateEmailStatement, model) == 1;
		} finally {
			model.clear();
		}
	}

	public boolean updatePassword(Object username, String password, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		try {
			model.put("username", getUsernameAsString(username));
			model.put("password", password);
			model.put("updatedBy", principal);
			return getSqlMapClientTemplate().update(updatePasswordStatement, model) == 1;
		} finally {
			model.clear();
		}
	}

	public boolean updateStatus(Object username, MemberStatus status, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		try {
			model.put("username", getUsernameAsString(username));
			model.put("status", status);
			model.put("updatedBy", principal);
			return getSqlMapClientTemplate().update(updateStatusStatement, model) == 1;
		} finally {
			model.clear();
		}
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
		return getSqlMapClientTemplate().delete(deletePersonStatement, s) == 1;
	}

	public boolean updateEmailStatus(InternetAddress address, InternetAddressStatus status, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		try {
			model.put("status", status);
			model.put("email", address);
			model.put("updatedBy", principal);
			return getSqlMapClientTemplate().update(updateEmailStatusStatement, model) == 1;
		} finally {
			model.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Person> findPersonsByKey(List<Object> keys) {
		int size = keys.size();	// ORA-01795: il numero massimo di espressioni in un elenco è 1000
		int r = size % STEP;
		List<Person> rv = getSqlMapClientTemplate().queryForList(findByKeyStatement, keys.subList(size - r, size));

		int n = size / STEP;
		int i = 0;
		while(i < n * STEP) {
			List<Object> subList = keys.subList(i, i+STEP);
			List<Person> l = getSqlMapClientTemplate().queryForList(findByKeyStatement, subList);
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
		getSqlMapClientTemplate().queryWithRowHandler(findByKeyStatement, keys.subList(size - r, size), rowHandler);

		int n = size / STEP;
		int i = 0;
		while(i < n * STEP) {
			getSqlMapClientTemplate().queryWithRowHandler(findByKeyStatement, keys.subList(i, i+STEP), rowHandler);
			i += STEP;
		}
	}

	public void setExportRowHandlerId(String exportRowHandlerId) {
		this.exportRowHandlerId = exportRowHandlerId;
	}

	public void setInsertPersonStatement(String insertPersonStatement) {
		this.insertPersonStatement = insertPersonStatement;
	}

	public void setPersonStatement(String personStatement) {
		this.personStatement = personStatement;
	}

	public void setUpdatePersonStatement(String updatePersonStatement) {
		this.updatePersonStatement = updatePersonStatement;
	}

	public void setUpdateEmailStatement(String updateEmailStatement) {
		this.updateEmailStatement = updateEmailStatement;
	}

	public void setUpdatePasswordStatement(String updatePasswordStatement) {
		this.updatePasswordStatement = updatePasswordStatement;
	}

	public void setUpdateStatusStatement(String updateStatusStatement) {
		this.updateStatusStatement = updateStatusStatement;
	}

	public void setDeletePersonStatement(String deletePersonStatement) {
		this.deletePersonStatement = deletePersonStatement;
	}

	public void setUpdateEmailStatusStatement(String updateEmailStatusStatement) {
		this.updateEmailStatusStatement = updateEmailStatusStatement;
	}

	public void setFindByKeyStatement(String findByKeyStatement) {
		this.findByKeyStatement = findByKeyStatement;
	}

}