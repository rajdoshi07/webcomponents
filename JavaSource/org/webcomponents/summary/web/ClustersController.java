package org.webcomponents.summary.web;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.webcomponents.summary.Cluster;
import org.webcomponents.summary.SummaryService;
import org.webcomponents.web.servlet.view.document.JxlsView;

@Controller
public class ClustersController {

	private static final KeyComparator comparator = new KeyComparator();
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final Logger logger = Logger.getLogger(ClustersController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	protected SummaryService service;
	
	protected String xlsViewName;
	
	public ClustersController() {
		super();
	}

	public SummaryService getSummaryService() {
		return service;
	}

	public final void setService(SummaryService service) {
		this.service = service;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value = "dashboard.*", method = RequestMethod.GET)
	public Map<String, Object> getSummary(@RequestParam(value="begin", required=false)Date begin, @RequestParam(value="end", required=false)Date end, @RequestParam(value="freq", required=false)Integer freq) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> rv = new HashMap<String, Object>();
		int f = freq == null ? Calendar.MONTH : freq;
		rv.put("freq", freq);
		populateSummary(begin, end, f, rv);
		return rv;
	}
	
	@RequestMapping(value = "export*.xls", method = RequestMethod.GET)
	public ModelAndView getCluster(@RequestParam(value="begin", required=false)Date begin, @RequestParam(value="end", required=false)Date end, @RequestParam(value="cluster")String clusterName) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		putClusters(begin, end, model);
		JxlsView view = new JxlsView();
		view.setApplicationContext(applicationContext);
		view.setUrl(xlsViewName);
		ModelAndView rv = new ModelAndView(view, model);
		return rv;
	}
	
	protected void populateSummary(Date begin, Date end, int freq, Map<String, Object> model) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		putClusters(begin, end, model);
		putStatusCount(begin, end, model);
	}

	protected void putStatusCount(Date begin, Date end, Map<String, Object> model) {
		Map<String, Number> statusCount = service.getStatusCount(begin, end);
		model.put("statusCount", statusCount);
	}

	protected void putClusters(Date begin, Date end, Map<String, Object> model) {
		List<Cluster> summaries = service.getClusters(begin, end);
		
		try {
			Map<String, Map<String, Long>> areaGenderSummary = getPropertyGenderSummary(summaries, "area");
			model.put("areaGenderSummary", areaGenderSummary);
			Map<String, Map<String, Long>> regionGenderSummary = getPropertyGenderSummary(summaries, "region");
			model.put("regionGenderSummary", regionGenderSummary);
			Map<String, Map<String, Long>> ageRangeGenderSummary = getPropertyGenderSummary(summaries, "ageRangeLabel");
			model.put("ageRangeGenderSummary", ageRangeGenderSummary);
			Map<String, Map<String, Map<String,Long>>> areaAgeRangeGenderSummary = getPropertyAgeRangeGenderSummary(summaries, "area");
			model.put("areaAgeRangeGenderSummary", areaAgeRangeGenderSummary);
			Map<String, Map<String, Map<String,Long>>> regionAgeRangeGenderSummary = getPropertyAgeRangeGenderSummary(summaries, "region");
			model.put("regionAgeRangeGenderSummary", regionAgeRangeGenderSummary);
			Map<String, Long> genderSummary = getGenderSummary(summaries);
			model.put("genderSummary", genderSummary);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		}
	}

	private Map<String,Map<String, Long>> getPropertyGenderSummary(List<Cluster> summaries, String property)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
				Map<String,Map<String, Long>> rv = new TreeMap<String, Map<String,Long>>(comparator);
				for (Cluster cluster : summaries) {
					String mainKey = BeanUtils.getProperty(cluster, property);
					String genderLabel = cluster.getGenderLabel();
					Long count = cluster.getCount();
					Map<String, Long> m = rv.get(mainKey);
					if(m == null) {
						m = new HashMap<String, Long>(3);
						m.put(genderLabel, count);
						rv.put(mainKey, m);
					} else {
						Long c = m.get(genderLabel);
						if(c == null) {
							m.put(genderLabel, count);
						} else {
							m.put(genderLabel, c + count);
						}
					}
				}
				return rv;		
			}

	private Map<String, Map<String, Map<String, Long>>> getPropertyAgeRangeGenderSummary(List<Cluster> summaries, String property)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
				Map<String,Map<String,Map<String,Long>>> rv = new TreeMap<String,Map<String,Map<String,Long>>>(comparator);
				for (Cluster cluster : summaries) {
					String mainKey = BeanUtils.getProperty(cluster, property);
					String ageRangeLabel = cluster.getAgeRangeLabel();
					String genderLabel = cluster.getGenderLabel();
					Long count = cluster.getCount();
					
					Map<String,Map<String,Long>> ageRangeMap = rv.get(mainKey);
					if(ageRangeMap == null){
						HashMap<String, Long> genderMap = new HashMap<String, Long>();
						genderMap.put(genderLabel, count);
						
						ageRangeMap = new TreeMap<String, Map<String,Long>>();
						ageRangeMap.put(ageRangeLabel, genderMap);
						
						rv.put(mainKey, ageRangeMap);
					} else {
						Map<String, Long> genderMap = ageRangeMap.get(ageRangeLabel);
						if(genderMap == null) {
							genderMap = new HashMap<String, Long>();
							genderMap.put(genderLabel, count);
							
							ageRangeMap.put(ageRangeLabel, genderMap);
						} else {
							Long c = genderMap.get(genderLabel);
							if(c == null) {
								genderMap.put(genderLabel, count);
							} else {
								genderMap.put(genderLabel, c + count);
							}
						}
					}
				}
				return rv;
			}

	private Map<String, Long> getGenderSummary(List<Cluster> summaries) {
		Map<String, Long> m = new HashMap<String, Long>(3);
		for (Cluster cluster : summaries) {
			String genderLabel = cluster.getGenderLabel();
			Long count = cluster.getCount();
			Long c = m.get(genderLabel);
			if(c == null) {
				m.put(genderLabel, count);
			} else {
				m.put(genderLabel, c + count);
			}
		}
		return m;
	}

	public void setXlsViewName(String xlsViewName) {
		this.xlsViewName = xlsViewName;
	}

}