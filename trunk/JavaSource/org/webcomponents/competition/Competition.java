package org.webcomponents.competition;

import java.util.Date;

import org.webcomponents.content.ExpiringObject;
import org.webcomponents.content.PersistentObject;

public class Competition extends PersistentObject implements ExpiringObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -298047857059752069L;

	private Date begin;
	
	private Date end;
	
	private int participationPerDay;
	
	private String name;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isActive() {
		return isActiveAt(new Date());
	}

	public boolean isActiveAt(Date date) {
		return !(isPendingAt(date) || isExpiredAt(date));
	}

	public boolean isExpired() {
		return isExpiredAt(new Date());
	}

	public boolean isExpiredAt(Date date) {
		return getEnd() == null ? false : date.after(getEnd());
	}

	public boolean isPending() {
		return isPendingAt(new Date());
	}

	public boolean isPendingAt(Date date) {
		return getBegin() == null ? false : date.before(getBegin());
	}

	public void setParticipationPerDay(int participationUpperBound) {
		this.participationPerDay = participationUpperBound;
	}

	public int getParticipationPerDay() {
		return participationPerDay;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

}
