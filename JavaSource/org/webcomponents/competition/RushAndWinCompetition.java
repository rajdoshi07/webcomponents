package org.webcomponents.competition;

import org.webcomponents.membership.Member;


public interface RushAndWinCompetition {
	
	void participate(int bet, Member profile) throws CompetitionException;
	
}
