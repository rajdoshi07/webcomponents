package org.webcomponents.competition;

public class NotEnoughCreditsException extends CompetitionException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int lowerBound;
	
	private int actualCredits;

	public NotEnoughCreditsException() {
		super();
	}

	public NotEnoughCreditsException(int lowerBound, int actualCredits) {
		this.lowerBound = lowerBound;
		this.actualCredits = actualCredits;
	}

	/**
	 * @return the actualCredits
	 */
	public final int getActualCredits() {
		return actualCredits;
	}

	/**
	 * @return the lowerBound
	 */
	public final int getLowerBound() {
		return lowerBound;
	}

	@Override
	public String getMessage() {
		return actualCredits + " credits aren't enough to participate to this competition. At least " + lowerBound + " credits are required";
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
