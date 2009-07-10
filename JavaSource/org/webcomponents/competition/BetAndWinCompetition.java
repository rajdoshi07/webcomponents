package org.webcomponents.competition;

import org.webcomponents.membership.Member;

public interface BetAndWinCompetition {
	
	Prize participate(int bet, Member caller) throws CompetitionException;

}
