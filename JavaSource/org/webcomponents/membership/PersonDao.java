package org.webcomponents.membership;

import java.io.Writer;
import java.util.List;

import javax.mail.internet.InternetAddress;

public interface PersonDao {

	/**
	 * 
	 * @param
	 * @return username an implementation specifique primary key into member profile
	 */
	public String insertPerson(Person person, String password);
	public Person getPerson(Object username);
	public boolean updatePassword(Object username, String password);
	public boolean updateStatus(Object username, MemberStatus status);
	public boolean updateEmail(Object username, InternetAddress email);
	public boolean updatePerson(Object username, Person person);
	public boolean deletePerson(Object username);
	public boolean updateAddressStatus(InternetAddress address, InternetAddressStatus status);
	public List<Person> findPersonsByKey(List<Object> keys);
	public void exportPersonsByKey(List<Object> keys, Writer out);

}