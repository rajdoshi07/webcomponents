package org.webcomponents.membership.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.GroupManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {
	
	private GroupManager groupManager;
	
	@RequestMapping(value="groups", method=RequestMethod.GET)
	@ModelAttribute("groups")
	public List<String> getGroups() {
		return Arrays.asList(groupManager.findAllGroups());
	}
	
	@RequestMapping(value="groupDetails", method=RequestMethod.GET)
	public Map<String, List<?>> getGroupDetails(@RequestParam("group")String name) {
		Map<String, List<?>> model = new HashMap<String, List<?>>(2);
		GrantedAuthority[] authorities = groupManager.findGroupAuthorities(name);
		if(authorities != null) {
			model.put("authorities", Arrays.asList(authorities));
		}
		String[] users = groupManager.findUsersInGroup(name);
		if(users != null) {
			model.put("users", Arrays.asList(users));
		}
		return model;
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}

}
