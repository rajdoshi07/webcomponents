package org.webcomponents.competition.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.webcomponents.competition.CompetitionSummaryService;
import org.webcomponents.summary.web.TimeframeProgressController;

public class TimeframeProgressSummaryController extends TimeframeProgressController {
	
	public CompetitionSummaryService getCompetitionSummaryService() {
		return (CompetitionSummaryService) service;
	}

	@Override
	protected void populateSummary(Date begin, Date end, int freq, Map<String, Object> model) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		super.populateSummary(begin, end, freq, model);
		TreeMap<Date, Number> participantsCountProgress = getCompetitionSummaryService().getParticipantsCount(begin, end, freq);
		model.put("participantsCountProgress", participantsCountProgress);
	}

}
