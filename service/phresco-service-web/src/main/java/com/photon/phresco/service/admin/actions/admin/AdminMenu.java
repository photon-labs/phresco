package com.photon.phresco.service.admin.actions.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.service.admin.commons.ServiceActions;

public class AdminMenu extends ActionSupport implements ServiceActions { 

	public String getAdminMenu() {
		return ADMIN_MENU;	
	}
}