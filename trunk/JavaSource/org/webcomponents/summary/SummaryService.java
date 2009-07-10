package org.webcomponents.summary;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public interface SummaryService {

	List<Cluster> getClusters(Date begin, Date end);
		
	TreeMap<Date, Number> getTimeframeProgress(Date begin, Date end, int freq);

	Map<String, Number> getStatusCount(Date begin, Date end);

}
