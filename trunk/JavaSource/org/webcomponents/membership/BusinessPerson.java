package org.webcomponents.membership;

import org.springframework.util.StringUtils;

public class BusinessPerson extends Person {
	
	protected Firm firm;
	
	private String role;
	
	private String group;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3286330345568823406L;

	public Firm getFirm() {
		if(firm == null) {
			firm = new Firm();
		}
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		if(StringUtils.hasText(role)) {
			this.role = role;
		} else {
			this.role = null;
		}
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		if(StringUtils.hasText(group)) {
			this.group = group;
		} else {
			this.group = null;
		}
	}

}
