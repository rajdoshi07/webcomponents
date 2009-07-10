package org.webcomponents.content;

import java.util.Date;

public interface ExpiringObject {
	
	public Date getBegin();
	
	public Date getEnd();
	
	public boolean isExpired();
	
	public boolean isExpiredAt(Date date);
	
	public boolean isPending();
	
	public boolean isPendingAt(Date date);
	
	public boolean isActive();
	
	public boolean isActiveAt(Date date);

}
