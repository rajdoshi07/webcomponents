package org.webcomponents.membership;

import java.io.Writer;
import java.util.Collections;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;

public class PersonsMembership implements Membership {

	protected Logger logger = Logger.getLogger(PersonsMembership.class);
	private PersonDao personDao;
	
	public PersonsMembership(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public Member insertMember(Member member, String password) throws BindException {
		try {
			String username = personDao.insertPerson((Person)member, password);
			return personDao.getPerson(username);
		} catch (DataIntegrityViolationException e) {
			BindException ex = new BindException(member, "member");
			Person p = personDao.getPerson(member.getEmail());
			if(p == null) {
				ex.rejectValue("screenName", "invalid.duplicate");
			} else {
				ex.rejectValue("email", "invalid.duplicate");
			}
			throw ex;
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
	
	public Member editMemberPassword(Object username, String password) throws MemberNotFoundException, BindException {
		if (!personDao.updatePassword(username, password)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member editMemberEmail(Object username, InternetAddress email) throws MemberNotFoundException, BindException {
		try {
			if (!personDao.updateEmail(username, email)) {
				Person p = personDao.getPerson(email);
				if(p == null) {
					throw new MemberNotFoundException(username);
				} else {
					BindException ex = new BindException(email, "email");
					ex.rejectValue("address", "editEmail.unchanged.error");
					throw ex;
				}
			}
		} catch (DataIntegrityViolationException e) {
			BindException ex = new BindException(email, "email");
			ex.rejectValue("address", "invalid.duplicate.email");
			throw ex;
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
}
