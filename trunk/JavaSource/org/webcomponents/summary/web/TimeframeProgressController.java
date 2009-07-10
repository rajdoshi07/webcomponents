package org.webcomponents.summary.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.CollectionUtils;

public class TimeframeProgressController extends ClustersController {

	protected static final String FREQ_ATTRIBUTE = "freq";

	@Override
	protected void populateSummary(Date begin, Date end, int freq, Map<String, Object> model) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		super.populateSummary(begin, end, freq, model);
		putTimeframeProgress(begin, end, freq, model);
	}

	protected void putTimeframeProgress(Date begin, Date end, int freq, Map<String, Object> model) {
		TreeMap<Date, Number> progress = getSummaryService().getTimeframeProgress(begin, end, freq);
		model.put("progress", progress);
		if(!CollectionUtils.isEmpty(progress)) {
			model.put("begin", progress.firstKey());
			model.put("end", progress.lastKey());
		}
	}

}
