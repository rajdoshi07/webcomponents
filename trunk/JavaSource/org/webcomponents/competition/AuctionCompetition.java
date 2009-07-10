package org.webcomponents.competition;

import org.webcomponents.membership.Member;


public interface AuctionCompetition {
	
	void participate(int bet, Member member) throws CompetitionException;
	
}
