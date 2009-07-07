package org.webcomponents.membership;

import java.io.Writer;
import java.util.List;


public interface SubscriptionDao {
	
	public String insertSubscription(Object subscription);
	
	public boolean updateSubscription(Object subscription);
	
	public Object getSubscription(String id);

	public List<Subscription> getSubscriptions();

	public List<Subscription> getSubscriptionsByType(int type);

	public void exportSubscriptions(Writer out);

	public void exportSubscriptions(int type, Writer out);

}
