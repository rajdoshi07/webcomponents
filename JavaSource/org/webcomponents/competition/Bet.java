package org.webcomponents.competition;

import java.util.Date;

import org.springframework.util.StringUtils;

public class Bet {
	
	protected String code;
	
	private Date betAt;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if(StringUtils.hasText(code)) {
			this.code = StringUtils.trimAllWhitespace(code);
		} else {
			this.code = null;
		}
	}

	public Date getBetAt() {
		if(betAt == null) {
			betAt = new Date();
		}
		return betAt;
	}

	public void setBetAt(Date betAt) {
		this.betAt = betAt;
	}

}
