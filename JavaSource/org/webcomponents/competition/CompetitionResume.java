package org.webcomponents.competition;

public class CompetitionResume {

	private int engagedCredits = 0;
	private int spentCredits = 0;

	public CompetitionResume() {
	}
	
	public CompetitionResume(int engagedCredits, int spentCredits) {
		this.engagedCredits = engagedCredits;
		this.spentCredits = spentCredits;
	}

	public int getEngagedCredits() {
		return engagedCredits;
	}
	
	public void setEngagedCredits(int engagedCredits) {
		this.engagedCredits = engagedCredits;
	}

	public int getSpentCredits() {
		return spentCredits;
	}
	
	public void setSpentCredits(int spentCredits) {
		this.spentCredits = spentCredits;
	}
}
