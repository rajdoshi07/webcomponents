package org.webcomponents.competition.web;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.webcomponents.competition.InstantWinCompetition;

@Controller
public class ParticipationController {

	private InstantWinCompetition instantWinCompetitionService;
	private String viewName;

	public final InstantWinCompetition getCompetition() {
		return instantWinCompetitionService;
	}

	public final void setCompetition(InstantWinCompetition instantWinCompetitionService) {
		this.instantWinCompetitionService = instantWinCompetitionService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getParticipations(HttpServletRequest request) {
		ModelAndView rv = new ModelAndView();
		rv.setViewName(viewName);

		String requestURI = request.getRequestURI();
		Pattern p = Pattern.compile("/(\\w+)/index\\.html$");
		Matcher m = p.matcher(requestURI);
		if (m.find()) {
			String screenName = m.group(1);
			rv.addObject("keyword", screenName);
			List<Map<String, Object>> participations = instantWinCompetitionService.getParticipations(screenName);
			rv.addObject("participations", participations);
		}
		return rv;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

}
