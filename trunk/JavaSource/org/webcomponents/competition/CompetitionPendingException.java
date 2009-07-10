package org.webcomponents.competition;

import java.util.Date;

public class CompetitionPendingException extends CompetitionException {
	private static final long serialVersionUID = 2410450150665886720L;
	private Date now;
	private Date startOn;
	
	public CompetitionPendingException() {
		super();
	}

	public CompetitionPendingException(Date now, Date startOn) {
		this.now = now;
		this.startOn = startOn;
	}

	public Date getStartOn() {
		return startOn;
	}

	public void setStartOn(Date startOn) {
		this.startOn = startOn;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	@Override
	public String getMessage() {
		return "Competition isn't active on " + now.toString() + ". It starts on " + startOn.toString();
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
