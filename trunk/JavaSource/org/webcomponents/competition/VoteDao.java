package org.webcomponents.competition;


import java.security.Principal;
import java.util.Date;
import java.util.List;



public interface VoteDao {
	
	public void vote(Vote vote) throws CompetitionException;
	
	public List<Vote> getDayVotes(Principal principal, Date when);

	public List<VoteSummary> getChart(Date start, Date end, int from, int to);
	
}
