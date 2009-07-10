package org.webcomponents.competition;

import java.util.List;
import java.util.Map;

import org.webcomponents.membership.Member;

public interface InstantWinCompetition {

	Prize participate(Bet bet, Member person) throws CompetitionException;
	
	public List<Map<String,Object>> getParticipations(String screenName);
	
}