package org.webcomponents.competition;

import java.security.Principal;
import java.util.Date;

public class Vote {
	
	private Date votedAt;
	
	private int value;
	
	private Object object;
	
	private Principal votedBy;

	public Date getVotedAt() {
		if(votedAt == null) {
			votedAt = new Date();
		}
		return votedAt;
	}

	public void setVotedAt(Date votedAt) {
		this.votedAt = votedAt;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int vote) {
		this.value = vote;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Principal getVotedBy() {
		return votedBy;
	}

	public void setVotedBy(Principal votedBy) {
		this.votedBy = votedBy;
	}

}
