package org.webcomponents.membership.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.GroupManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.webcomponents.membership.DuplicatedGroupException;

@Controller
public class GroupController {
	
	private GroupManager groupManager;
	
	private GrantedAuthority[] defaultAuthorities = new GrantedAuthorityImpl[] {
		new GrantedAuthorityImpl("user")
	};
	
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
	
	@RequestMapping(method=RequestMethod.POST)
	public void createGroup(@RequestParam("name")String name) throws DuplicatedGroupException {
		try {
			groupManager.createGroup(name, defaultAuthorities);
		} catch(DataIntegrityViolationException e) {
			throw new DuplicatedGroupException(name);
		}
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}

	public void setDefaultAuthorities(GrantedAuthority[] defaultAuthorities) {
		this.defaultAuthorities = defaultAuthorities;
	}

}
