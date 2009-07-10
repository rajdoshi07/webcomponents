package org.webcomponents.competition;

public class Balance {

	public static final int STATUS_INCONSISTENT = 0;	
	public static final int STATUS_CONSISTENT = 1;
	private int status;	
	private int availableCredits = 0;
	private int spentCredits = 0;
	
	public int getAvailableCredits() {
		return availableCredits;
	}
	public void setAvailableCredits(int availableCredits) {
		this.availableCredits = availableCredits;
	}
	public int getSpentCredits() {
		return spentCredits;
	}
	public void setSpentCredits(int spentCredits) {
		this.spentCredits = spentCredits;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
