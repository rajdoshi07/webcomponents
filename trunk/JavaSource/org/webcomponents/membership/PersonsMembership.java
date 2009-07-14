package org.webcomponents.membership;

import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.webcomponents.security.vote.ContentRelatedRoleService;

@Service
public class PersonsMembership implements Membership, ContentRelatedRoleService {

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
			Principal principal = SecurityContextHolder.getContext().getAuthentication();
			if(principal == null || principal instanceof AnonymousAuthenticationToken) {
				principal = member;
			}
			String id = personDao.insertPerson((Person)member, password, principal);
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
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		if (!personDao.updateStatus(username, MemberStatus.DELETE_ALL_MARK, principal)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member enableMember(Object username) throws MemberNotFoundException {
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		if (!personDao.updateStatus(username, MemberStatus.ACTIVE, principal)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member disableMember(Object username) throws MemberNotFoundException {
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		if (!personDao.updateStatus(username, MemberStatus.INACTIVE, principal)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}
	
	public Member editMemberPassword(Object username, String password) throws MemberNotFoundException, InvalidPasswordException {
		if(!isValid(password, passwordRegExp)) {
			throw new InvalidPasswordException(password, passwordRegExp);
		}
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		if (!personDao.updatePassword(username, password, principal)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public Member editMemberEmail(Object username, InternetAddress email) throws MemberNotFoundException, DuplicatedEmailException {
		try {
			Principal principal = SecurityContextHolder.getContext().getAuthentication();
			if (!personDao.updateEmail(username, email, principal)) {
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
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		if (!personDao.updatePerson(username, (Person)member, principal)) {
			throw new MemberNotFoundException(username);
		}
		return personDao.getPerson(username);
	}

	public void validateEmail(InternetAddress email) throws MemberNotFoundException {
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		if(!personDao.updateEmailStatus(email, InternetAddressStatus.VALID, principal)) {
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

	@Override
	public boolean isContentEditor(Object id, Principal principal) {
		return false;
	}

	@Override
	public boolean isContentOwner(Object id, Principal principal) {
		String username = principal.getName();
		if(id instanceof InternetAddress) {
			if(username.indexOf('@') < 0) {
				Member member = getMember(username);
				InternetAddress email = member.getEmail();
				if(email == null) {
					return false;
				}
				String address = ((InternetAddress) id).getAddress();
				return email.getAddress().equalsIgnoreCase(address);
			}
		}
		return username.equalsIgnoreCase(id.toString());
	}
}
