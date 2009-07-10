package org.webcomponents.competition;

import java.util.Date;

public class CompetitionExpiredException extends CompetitionException {
	private static final long serialVersionUID = 2410450150665886720L;
	private Date now;
	private Date endOn;
	
	public CompetitionExpiredException() {
		super();
	}

	public CompetitionExpiredException(Date now, Date endOn) {
		this.now = now;
		this.endOn = endOn;
	}

	public Date getEndOn() {
		return endOn;
	}

	public void setEndOn(Date endOn) {
		this.endOn = endOn;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	@Override
	public String getMessage() {
		return "Competition isn't active on " + now.toString() + ". It expired on " + endOn.toString();
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
