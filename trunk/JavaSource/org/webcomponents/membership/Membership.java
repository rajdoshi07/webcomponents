package org.webcomponents.membership;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.security.annotation.Secured;
import org.springframework.validation.BindException;

public interface Membership {
	
	public Member insertMember(Member member, String password) throws DuplicatedUsernameException, DuplicatedEmailException, InvalidUsernameException, InvalidPasswordException, IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Member getMember(Object username) throws IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Member removeMember(Object username) throws MemberNotFoundException, IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Member enableMember(Object username) throws MemberNotFoundException, IOException;

	@Secured("ROLE_ADMIN")
	public Member disableMember(Object username) throws MemberNotFoundException, IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Member editMemberPassword(Object username, String password) throws MemberNotFoundException, InvalidPasswordException, IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Member editMemberEmail(Object username, InternetAddress email) throws MemberNotFoundException, DuplicatedEmailException, IOException;

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public Member editMemberDetails(Object username, Member member) throws MemberNotFoundException, BindException, IOException;	

	@Secured({"ROLE_ADMIN","CONTENT_OWNER"})
	public void validateEmail(InternetAddress email) throws MemberNotFoundException;
	
	@Secured("ROLE_ADMIN")
	public List<? extends Member> findMembersByKey(List<Object> keys);
	
	@Secured("ROLE_ADMIN")
	public void exportMembersByKey(List<Object> keys, Writer out);
}