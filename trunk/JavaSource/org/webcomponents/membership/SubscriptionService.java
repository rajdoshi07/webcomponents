package org.webcomponents.membership;

import java.io.Writer;
import java.util.List;

import org.springframework.security.annotation.Secured;

public interface SubscriptionService {
	
	public Object subscribe(Subscription subscription) throws DuplicatedUsernameException;
	
	@Secured("ROLE_ADMIN")
	public List<Subscription> list();
	
	@Secured("ROLE_ADMIN")
	public List<Subscription> list(int type);
	
	@Secured("ROLE_ADMIN")
	public void export(Writer out);
	
	@Secured("ROLE_ADMIN")
	public void export(int type, Writer out);

}
