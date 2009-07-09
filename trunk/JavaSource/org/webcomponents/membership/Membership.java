package org.webcomponents.membership;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.validation.BindException;

public interface Membership {
	
	/**
	 * TODO: dovrebbe tirare anche una DuplicatedUsernameException
	 * @param member
	 * @param password
	 * @return
	 * @throws BindException
	 */
	public Member insertMember(Member member, String password) throws DuplicatedUsernameException, DuplicatedEmailException, InvalidUsernameException, InvalidPasswordException, IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 */
	public Member getMember(Object username) throws IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 */
	public Member removeMember(Object username) throws MemberNotFoundException, IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 */
	public Member enableMember(Object username) throws MemberNotFoundException, IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 */
	public Member disableMember(Object username) throws MemberNotFoundException, IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 * @throws InvalidPasswordException TODO
	 */
	public Member editMemberPassword(Object username, String password) throws MemberNotFoundException, InvalidPasswordException, IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 * @throws DuplicatedEmailException TODO
	 */
	public Member editMemberEmail(Object username, InternetAddress email) throws MemberNotFoundException, DuplicatedEmailException, IOException;
	/**
	 * 
	 * @param username could be one of any unique key into member profile
	 * @return
	 */
	public Member editMemberDetails(Object username, Member member) throws MemberNotFoundException, BindException, IOException;	


	public void validateAddress(InternetAddress email) throws MemberNotFoundException;
	
	public List<? extends Member> findMembersByKey(List<Object> keys);
	
	public void exportMembersByKey(List<Object> keys, Writer out);
}