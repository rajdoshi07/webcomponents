package org.webcomponents.membership;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class Firm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8158229159588072465L;
	
	protected static final int NAME_MAX_LENGTH = 30;
	protected static final String NAME_REG_EXP = "^[\\w/\\-\\. 'אטילעש]{2," + NAME_MAX_LENGTH + "}$";
	protected static final String PLACE_REG_EXP = NAME_REG_EXP;

	private String name;
	
	private String street;
	
	private String town;
	
	private String phone;
	
	private String business;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(StringUtils.hasText(name)) {
			String value = StringUtils.trimWhitespace(name);
			if(value.matches(NAME_REG_EXP)) {
				this.name = value;
			} else {
				throw new IllegalArgumentException("Invalid firm name " + StringUtils.quote(name));
			}
		} else {
			this.name = null;
		}
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if(StringUtils.hasText(street)) {
			String value = StringUtils.trimWhitespace(street);
			if(value.matches(PLACE_REG_EXP)) {
				this.street = value;
			} else {
				throw new IllegalArgumentException("Invalid firm street " + StringUtils.quote(street));
			}
		} else {
			this.street = null;
		}
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		if(StringUtils.hasText(town)) {
			String value = StringUtils.trimWhitespace(town);
			if(value.matches(PLACE_REG_EXP)) {
				this.town = value;
			} else {
				throw new IllegalArgumentException("Invalid firm town " + StringUtils.quote(town));
			}
		} else {
			this.town = null;
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if(StringUtils.hasText(phone)) {
			String fp = StringUtils.trimAllWhitespace(phone);
			if(fp.matches(Person.PHONE_NUMBER_REG_EXP)) {
				this.phone = fp;
			} else {
				throw new IllegalArgumentException("Invalid firm phone " + StringUtils.quote(phone));
			}
		} else {
			this.phone = null;
		}
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

}
