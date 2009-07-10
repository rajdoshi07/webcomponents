package org.webcomponents.competition;

import java.util.Date;
import java.util.List;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;



public class VotingCompetitionImpl extends Competition implements VotingCompetition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4326912735875985584L;

	private VoteDao voteDao;
	
	private int participationsPerDay;

	public void participate(Vote vote) throws CompetitionException {
		if(this.isPendingAt(vote.getVotedAt())) throw new CompetitionPendingException(vote.getVotedAt(), this.getBegin());
		if(this.isExpiredAt(vote.getVotedAt())) throw new CompetitionExpiredException(vote.getVotedAt(), this.getEnd());
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();

		List<Vote> votes = voteDao.getDayVotes(principal, vote.getVotedAt());
		if(votes.size() < participationsPerDay) {
			vote.setVotedBy(principal);
			voteDao.vote(vote);			
		} else {
			throw new ParticipationOutOfBoundException(participationsPerDay);
		}
	}

	public final void setVoteDao(VoteDao chartDao) {
		this.voteDao = chartDao;
	}
	
	public final void setParticipationsPerDay(int participationsPerDay) {
		this.participationsPerDay = participationsPerDay;
	}

	public List<VoteSummary> getChart(Date start, Date end, int from, int to) {
		return this.voteDao.getChart(start, end, from, to);
	}

	public List<VoteSummary> getChart() {
		// TODO Auto-generated method stub
		return null;
	}

}
