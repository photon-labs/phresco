package com.photon.phresco.service.admin.actions.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.service.admin.commons.ServiceActions;

public class UserList extends ActionSupport implements ServiceActions { 

	public String list() {
		return ADMIN_USER_LIST;	
	}
}