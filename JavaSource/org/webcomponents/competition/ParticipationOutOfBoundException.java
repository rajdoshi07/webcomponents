package org.webcomponents.competition;

/**
 * This exception should be thrown when a user try to participate to a competition
 * more than the maximum times (daily, weekly, monthly, total).
 * @author andreab
 *
 */
public class ParticipationOutOfBoundException extends CompetitionException {
	private static final long serialVersionUID = 4121410722165961271L;
	private int upperBound;
	
	public ParticipationOutOfBoundException() {
		super();
	}

	public ParticipationOutOfBoundException(int upperBound) {
		this.upperBound = upperBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}	

	@Override
	public String getMessage() {
		return "Participation out of bound, upper bound is: " + upperBound;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
