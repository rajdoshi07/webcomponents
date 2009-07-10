package org.webcomponents.summary;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.CollectionUtils;




public class SummaryServiceImpl implements SummaryService {
	
	protected static final TreeMap<Date, Number> emptyTreeMap = new TreeMap<Date, Number>();
	private ClusterDAO clusterDao;

	public List<Cluster> getClusters(Date begin, Date end) {
		return clusterDao.getClusters(begin, end);
	}

	public TreeMap<Date, Number> getTimeframeProgress(Date begin, Date end, int freq) {
		if(end != null && begin != null && end.before(begin)){
			throw new IllegalArgumentException();
		}
		if(!(freq == Calendar.DATE || freq == Calendar.WEEK_OF_YEAR || freq == Calendar.MONTH)) {
			throw new IllegalArgumentException("Unknown frequency value. Use one of Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH");
		}
				
		TreeMap<Date, Number> rv = null;
		switch(freq) {
		case Calendar.DATE:
			rv = clusterDao.getDailyCount(begin, end);
			break;
		case Calendar.WEEK_OF_YEAR:
			rv = clusterDao.getWeeklyCount(begin, end);
			break;
		case Calendar.MONTH:
			rv = clusterDao.getMonthlyCount(begin, end);
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

	public void setClusterDao(ClusterDAO clusterDao) {
		this.clusterDao = clusterDao;
	}

	public ClusterDAO getClusterDao() {
		return clusterDao;
	}

	public Map<String, Number> getStatusCount(Date begin, Date end) {
		return getClusterDao().getStatusCount(begin, end);
	}


}
