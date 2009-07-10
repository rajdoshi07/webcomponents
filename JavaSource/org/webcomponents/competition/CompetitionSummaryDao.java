package org.webcomponents.competition;

import java.util.Date;
import java.util.TreeMap;

import org.webcomponents.summary.ClusterDAO;

public interface CompetitionSummaryDao extends ClusterDAO {

	TreeMap<Date, Number> getDailyParticipationsCount(Date begin, Date end);
	TreeMap<Date, Number> getWeeklyParticipationsCount(Date begin, Date end);	
	TreeMap<Date, Number> getMonthlyParticipationsCount(Date begin, Date end);

	TreeMap<Date, Number> getDailyParticipantsCount(Date begin, Date end);
	TreeMap<Date, Number> getWeeklyParticipantsCount(Date begin, Date end);	
	TreeMap<Date, Number> getMonthlyParticipantsCount(Date begin, Date end);
}
