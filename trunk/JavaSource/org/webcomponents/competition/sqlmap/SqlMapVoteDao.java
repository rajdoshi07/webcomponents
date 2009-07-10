package org.webcomponents.competition.sqlmap;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.webcomponents.competition.Vote;
import org.webcomponents.competition.VoteDao;
import org.webcomponents.competition.VoteSummary;

public class SqlMapVoteDao extends SqlMapClientDaoSupport implements VoteDao {
	
	public void vote(Vote vote) {
		getSqlMapClientTemplate().insert("vote", vote);
	}

	@SuppressWarnings("unchecked")
	public List<Vote> getDayVotes(Principal principal, Date when) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		model.put("day", when);
		model.put("votedBy", principal);
		try {
			return getSqlMapClientTemplate().queryForList("getVotes", model);
		} finally {
			model.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public List<VoteSummary> getChart(Date start, Date end, int from, int to) {
		Map<String, Object> model = new HashMap<String, Object>(4);
		model.put("start", start);
		model.put("end", end);
		model.put("from", new Integer(from));
		model.put("to", new Integer(to));
		try {
			return getSqlMapClientTemplate().queryForList("getChart", model);
		} finally {
			model.clear();
		}
	}

}
