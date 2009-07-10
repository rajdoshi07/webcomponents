package org.webcomponents.competition;

public class WinningPrize extends Prize {
	protected String id;
	
	public WinningPrize() {
		super();
	}

	public WinningPrize(String id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
