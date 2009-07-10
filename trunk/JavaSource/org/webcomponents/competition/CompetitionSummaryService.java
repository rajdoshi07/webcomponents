package org.webcomponents.competition;

import java.util.Date;
import java.util.TreeMap;

import org.webcomponents.summary.SummaryService;

public interface CompetitionSummaryService extends SummaryService {

	TreeMap<Date, Number> getParticipationsCount(Date begin, Date end, int freq);

	TreeMap<Date, Number> getParticipantsCount(Date begin, Date end, int freq);
}
