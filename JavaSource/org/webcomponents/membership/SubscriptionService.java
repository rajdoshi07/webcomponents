package org.webcomponents.membership;

import java.io.Writer;
import java.util.List;

public interface SubscriptionService {
	
	public Object subscribe(Subscription subscription) throws DuplicateMemberException;
	
	public List<Subscription> list();
	
	public List<Subscription> list(int type);
	
	public void export(Writer out);
	
	public void export(int type, Writer out);

}
