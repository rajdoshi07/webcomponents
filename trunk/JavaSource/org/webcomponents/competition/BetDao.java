package org.webcomponents.competition;

import java.util.List;
import java.util.Map;

import org.webcomponents.membership.Member;

public interface BetDao {

	public Prize bet(Bet bet, Member member) throws CompetitionException;

	public List<Map<String,Object>> getParticipations(String screenName);
	
}