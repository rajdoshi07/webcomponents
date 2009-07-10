package org.webcomponents.summary.sqlmap;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.webcomponents.orm.ibatis.support.SqlMapClientDaoSupport;
import org.webcomponents.summary.Cluster;
import org.webcomponents.summary.ClusterDAO;

public class SqlMapClusterDAO extends SqlMapClientDaoSupport implements ClusterDAO {

	@SuppressWarnings("unchecked")
	public List<Cluster> getClusters(Date begin, Date end) {
		Map<String, Date> model = Collections.emptyMap();
		if(begin != null) {
			model = new HashMap<String, Date>(2);
			model.put("begin", begin);
		}
		if(end != null)  {
			if (model.equals(Collections.emptyMap())) {
				model = new HashMap<String, Date>(1);
			}
			model.put("end", end);
		}
		return getSqlMapClientTemplate().queryForList(applyNamespace("getClusters"), model);
	}
		
	@SuppressWarnings("unchecked")
	public TreeMap<Date, Number> getDailyCount(Date begin, Date end){
		Map<String, Date> model = Collections.emptyMap();
		if(begin != null) {
			model = new HashMap<String, Date>(2);
			model.put("begin", begin);
		}
		if(end != null)  {
			if (model.equals(Collections.emptyMap())) {
				model = new HashMap<String, Date>(1);
			}
			model.put("end", end);
		}
		TreeMap<Date, Number> rv = new TreeMap<Date, Number>(getSqlMapClientTemplate().queryForMap(applyNamespace("getDailyProgress"), model, "DAY", "COUNT(*)"));
		return rv;
	}
	@SuppressWarnings("unchecked")
	public TreeMap<Date, Number> getWeeklyCount(Date begin, Date end) {
		Map<String, Date> model = Collections.emptyMap();
		if(begin != null) {
			model = new HashMap<String, Date>(2);
			model.put("begin", begin);
		}
		if(end != null)  {
			if (model.equals(Collections.emptyMap())) {
				model = new HashMap<String, Date>(1);
			}
			model.put("end", end);
		}
		return new TreeMap<Date, Number>(getSqlMapClientTemplate().queryForMap(applyNamespace("getWeeklyProgress"), model, "WEEK", "COUNT(*)"));
	}
	@SuppressWarnings("unchecked")
	public TreeMap<Date, Number> getMonthlyCount(Date begin, Date end){
		Map<String, Date> model = Collections.emptyMap();
		if(begin != null) {
			model = new HashMap<String, Date>(2);
			model.put("begin", begin);
		}
		if(end != null)  {
			if (model.equals(Collections.emptyMap())) {
				model = new HashMap<String, Date>(1);
			}
			model.put("end", end);
		}
		return new TreeMap<Date, Number>(getSqlMapClientTemplate().queryForMap(applyNamespace("getMonthlyProgress"), model, "MONTH", "COUNT(*)"));
	}

	@SuppressWarnings("unchecked")
	public Map<String, Number> getStatusCount(Date begin, Date end) {
		Map<String, Date> model = Collections.emptyMap();
		if(begin != null) {
			model = new HashMap<String, Date>(2);
			model.put("begin", begin);
		}
		if(end != null)  {
			if (model.equals(Collections.emptyMap())) {
				model = new HashMap<String, Date>(1);
			}
			model.put("end", end);
		}
		return getSqlMapClientTemplate().queryForMap(applyNamespace("getStatusCount"), model, "USER_STATUS", "COUNT(*)");
	}

}
