package org.webcomponents.membership;

import java.io.Writer;
import java.security.Principal;
import java.util.List;

import javax.mail.internet.InternetAddress;

public interface PersonDao {

	/**
	 * 
	 * @param principal TODO
	 * @param
	 * @return username an implementation specifique primary key into member profile
	 */
	public String insertPerson(Person person, String password, Principal principal);
	public Person getPerson(Object username);
	public boolean updatePassword(Object username, String password, Principal principal);
	public boolean updateStatus(Object username, MemberStatus status, Principal principal);
	public boolean updateEmail(Object username, InternetAddress email, Principal principal);
	public boolean updatePerson(Object username, Person person, Principal principal);
	public boolean deletePerson(Object username);
	public boolean updateEmailStatus(InternetAddress address, InternetAddressStatus status, Principal principal);
	public List<Person> findPersonsByKey(List<Object> keys);
	public void exportPersonsByKey(List<Object> keys, Writer out);

}