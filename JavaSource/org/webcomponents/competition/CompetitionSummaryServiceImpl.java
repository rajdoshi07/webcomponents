package org.webcomponents.competition;

import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import org.springframework.util.CollectionUtils;
import org.webcomponents.summary.SummaryServiceImpl;

public class CompetitionSummaryServiceImpl extends SummaryServiceImpl implements CompetitionSummaryService {

	public TreeMap<Date, Number> getParticipantsCount(Date begin, Date end, int freq) {
		if(end != null && begin != null && end.before(begin)){
			throw new IllegalArgumentException();
		}
		if(!(freq == Calendar.DATE || freq == Calendar.WEEK_OF_YEAR || freq == Calendar.MONTH)) {
			throw new IllegalArgumentException("Unknown frequency value. Use one of Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH");
		}
		
		CompetitionSummaryDao summaryDao = getCompetitionSummaryDao();
		TreeMap<Date, Number> rv = null;
		switch(freq) {
		case Calendar.DATE:
			rv = summaryDao.getDailyParticipantsCount(begin, end);
			break;
		case Calendar.WEEK_OF_YEAR:
			rv = summaryDao.getWeeklyParticipantsCount(begin, end);
			break;
		case Calendar.MONTH:
			rv = summaryDao.getMonthlyParticipantsCount(begin, end);
			break;
		}
		if(CollectionUtils.isEmpty(rv)) {
			return emptyTreeMap;
		}
		long e = rv.lastKey().getTime();
		long time = rv.firstKey().getTime();
		
		while(time < e) {
			Calendar i = Calendar.getInstance();
			i.setTimeInMillis(time);
			i.add(freq, 1);
			if(rv.get(i.getTime()) == null) {
				rv.put(i.getTime(), 0L);
			}
			time = i.getTimeInMillis();
		}
		return rv;
	}

	public TreeMap<Date, Number> getParticipationsCount(Date begin, Date end, int freq) {
		return getTimeframeProgress(begin, end, freq);
	}

	public void setCompetitionSummaryDao(CompetitionSummaryDao summaryDao) {
		this.setClusterDao(summaryDao);
	}
	
	public CompetitionSummaryDao getCompetitionSummaryDao() {
		return (CompetitionSummaryDao) getClusterDao();
	}
}
