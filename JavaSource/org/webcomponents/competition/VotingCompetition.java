/*
 * VotingCompetition.java
 *
 * Created on 14 marzo 2003, 19.45
 */

package org.webcomponents.competition;

import java.util.Date;
import java.util.List;



public interface VotingCompetition {
	
	public void participate(Vote vote) throws CompetitionException;
	
	public List<VoteSummary> getChart(Date start, Date end, int from, int to);
	
	public List<VoteSummary> getChart();
	
}