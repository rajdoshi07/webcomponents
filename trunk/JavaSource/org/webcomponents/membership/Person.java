package org.webcomponents.membership;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.springframework.util.StringUtils;
import org.webcomponents.mobile.MobileNumber;

public class Person extends Member {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4619791865228073626L;
	
	public static final String FISCAL_CODE_REG_EXP = "^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$";
	public static final String POSTAL_CODE_REG_EXP = "^\\d{5}$";
	public static final String PHONE_NUMBER_REG_EXP = "^\\+?\\d{3,15}$";
	protected static final int NAME_MAX_LENGTH = 30;
	protected static final String NAME_REG_EXP = "^[A-Za-z\u00C0-\u00F6\u00F8-\u00FF\\-\\. ']{2," + NAME_MAX_LENGTH + "}$";
	public static final String PLACE_REG_EXP = "^[A-Za-z\u00C0-\u00F6\u00F8-\u00FF0-9_/\\-\\. ,°']{2,64}$";
	public static final String STREET_REG_EXP = "^[A-Za-z\u00C0-\u00F6\u00F8-\u00FF0-9_/\\-\\. ,°'()]{2,64}$";
	
	public static final int MARITAL_STATUS_MARRIED = 4;

	private String firstName;
	private String lastName;
	private Date birthdate;
	private String birthplace;
	private String birthProvince;
	private String citizenship;
	private Gender gender;
	private String fiscalCode;
	private String street;
	private String town;
	private String province;
	private String country;
	private String postalCode;
	private String phone;
	private String fax;
	protected Person partner;
	private int maritalStatus;
	
	
	public Person() {
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(StringUtils.hasText(firstName)) {
			String value = StringUtils.trimWhitespace(firstName);
			if(value.matches(NAME_REG_EXP)) {
				this.firstName = value;
			} else {
				throw new IllegalArgumentException("Invalid first name: " + firstName);
			}
		} else {
			this.firstName = null;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(StringUtils.hasText(lastName)) {
			String value = StringUtils.trimWhitespace(lastName);
			if(value.matches(NAME_REG_EXP)) {
				this.lastName = value;
			} else {
				throw new IllegalArgumentException("Invalid last name: " + lastName);
			}
		} else {
			this.lastName = null;
		}
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		if(StringUtils.hasText(birthplace)) {
			String value = StringUtils.trimWhitespace(birthplace);
			if(value.matches(PLACE_REG_EXP)) {
				this.birthplace = value;
			} else {
				throw new IllegalArgumentException("Invalid birthplace: " + birthplace);
			}
		} else {
			this.birthplace = null;
		}
	}

	public String getBirthProvince() {
		return birthProvince;
	}

	public void setBirthProvince(String province) {
		if(StringUtils.hasText(province)) {
			this.birthProvince = StringUtils.trimWhitespace(province);
		} else {
			this.birthProvince = null;
		}
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		if(StringUtils.hasText(fiscalCode)) {
			String fc = StringUtils.trimAllWhitespace(fiscalCode.toUpperCase());
			if(fc.matches(FISCAL_CODE_REG_EXP)) {
				this.fiscalCode = fc;
			} else {
				throw new IllegalArgumentException("Invalid fiscal code: " + fiscalCode);
			}
		} else {
			this.fiscalCode = null;
		}
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		if(StringUtils.hasText(citizenship)) {
			this.citizenship = StringUtils.trimWhitespace(citizenship);
		} else {
			this.citizenship = null;
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if(StringUtils.hasText(street)) {
			String value = StringUtils.trimWhitespace(street);
			if(value.matches(STREET_REG_EXP)) {
				this.street = value;
			} else {
				throw new IllegalArgumentException("Invalid street: " + street);
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
				throw new IllegalArgumentException("Invalid town: " + town);
			}
		} else {
			this.town = null;
		}
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		if(StringUtils.hasText(province)) {
			this.province = StringUtils.trimWhitespace(province);
		} else {
			this.province = null;
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if(StringUtils.hasText(country)) {
			this.country = StringUtils.trimWhitespace(country);
		} else {
			this.country = null;
		}
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		if(StringUtils.hasText(postalCode)) {
			String pc = StringUtils.trimAllWhitespace(postalCode);
			if(pc.matches(POSTAL_CODE_REG_EXP)) {
				this.postalCode = pc;
			} else {
				throw new IllegalArgumentException("Invalid postal code: " + postalCode);
			}
		} else {
			this.postalCode = null;
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if(StringUtils.hasText(phone)) {
			String p = StringUtils.trimAllWhitespace(phone);
			if(p.matches(PHONE_NUMBER_REG_EXP)) {
				this.phone = p;
			} else {
				throw new IllegalArgumentException("Invalid phone number: " + phone);
			}
		} else {
			this.phone = null;
		}
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		if(StringUtils.hasText(fax)) {
			String f = StringUtils.trimAllWhitespace(fax);
			if(f.matches(PHONE_NUMBER_REG_EXP)) {
				this.fax = f;
			} else {
				throw new IllegalArgumentException("Invalid fax number: " + fax);
			}
		} else {
			this.fax = null;
		}
	}

	public Person getPartner() {
		if(partner == null) {
			partner = new Person();
		}
		return partner;
	}

	public void setPartner(Person partner) {
		this.partner = partner;
	}
	
	public boolean isCoupled() {
		return !(this.partner == null);
	}

	public int getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(int maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public int getAge() {
		LocalDate birthdate = new LocalDate(getBirthdate());
		LocalDate now = new LocalDate();
		Period life = new Period(birthdate, now);
		return life.getYears();
	}

	public String getContactPhone() {
		String rv = null;
		MobileNumber mobile = getMobileNumber();
		if(mobile != null) {
			rv = mobile.toString();
		}
		if(!StringUtils.hasText(rv)) {
			rv = getPhone();
		}
		return rv;
	}

}
