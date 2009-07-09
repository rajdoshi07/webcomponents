package org.webcomponents.membership;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;

public class PersonsMembership implements Membership {

	private String passwordRegExp;
	
	private String usernameRegExp;
	
	private PersonDao personDao;
	
	public PersonsMembership(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public Member insertMember(Member member, String password) throws InvalidUsernameException, InvalidPasswordException, DuplicatedUsernameException, DuplicatedEmailException, IOException {
		if(!isValid(member.getUsername(), usernameRegExp)) {
			throw new InvalidUsernameException(member.getUsername(), usernameRegExp);			
		}
		if(!isValid(password, passwordRegExp)) {
			throw new InvalidPasswordException(password, passwordRegExp);
		}
		try {
			String id = personDao.insertPerson((Person)member, password);
			return personDao.getPerson(id);
		} catch (DataIntegrityViolationException e) {
			Person p = personDao.getPerson(member.getEmail());
			if(p == null) {
				throw new DuplicatedUsernameException(member.getUsername());
			} else {
				throw new DuplicatedEmailException(member.getEmail().getAddress());
			}
		}
	}

	public Member getMember(Object username) {
		return personDao.getPerson(username);
	}

	public Member removeMember(Object username) throws MemberNotFoundException {
		if (!personDao.updateStatus(username, MemberStatus.DELETE_ALL_MARK)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member enableMember(Object username) throws MemberNotFoundException {
		if (!personDao.updateStatus(username, MemberStatus.ACTIVE)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member disableMember(Object username) throws MemberNotFoundException {
		if (!personDao.updateStatus(username, MemberStatus.INACTIVE)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}
	
	public Member editMemberPassword(Object username, String password) throws MemberNotFoundException, InvalidPasswordException {
		if(!isValid(password, passwordRegExp)) {
			throw new InvalidPasswordException(password, passwordRegExp);
		}
		if (!personDao.updatePassword(username, password)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member editMemberEmail(Object username, InternetAddress email) throws MemberNotFoundException, DuplicatedEmailException {
		try {
			if (!personDao.updateEmail(username, email)) {
				Person p = personDao.getPerson(email);
				if(p == null) {
					throw new MemberNotFoundException(username);
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new DuplicatedEmailException(email.getAddress());
		}
		
		Person person = personDao.getPerson(username);		
		return person;
	}

	public Member editMemberDetails(Object username, Member member) throws MemberNotFoundException, BindException {
		if (!personDao.updatePerson(username, (Person)member)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public void validateAddress(InternetAddress email) throws MemberNotFoundException {
		if(!personDao.updateAddressStatus(email, InternetAddressStatus.VALID)) {
			throw new MemberNotFoundException(email);
		}
	}

	public List<? extends Member> findMembersByKey(List<Object> keys) {
		if(CollectionUtils.isEmpty(keys)) {
			return Collections.emptyList();
		}
		return personDao.findPersonsByKey(keys);
	}

	public void exportMembersByKey(List<Object> keys, Writer out) {
		if(CollectionUtils.isEmpty(keys)) {
			return;
		}
		personDao.exportPersonsByKey(keys, out);
	}

	public void setPasswordRegExp(String passwordRegExp) {
		this.passwordRegExp = passwordRegExp;
	}

	public void setUsernameRegExp(String usernameRegExp) {
		this.usernameRegExp = usernameRegExp;
	}
	
	private boolean isValid(String value, String regexp) {
		if(!(regexp == null || (value != null && value.matches(regexp)))) {
			return false;
		}
		return true;
	}
}
