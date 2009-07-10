package org.webcomponents.summary;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface ClusterDAO {
	
	TreeMap<Date, Number> getDailyCount(Date begin, Date end);
	TreeMap<Date, Number> getWeeklyCount(Date begin, Date end);	
	TreeMap<Date, Number> getMonthlyCount(Date begin, Date end);
	
	List<Cluster> getClusters(Date begin, Date end);
	
	Map<String, Number> getStatusCount(Date begin, Date end);
}
