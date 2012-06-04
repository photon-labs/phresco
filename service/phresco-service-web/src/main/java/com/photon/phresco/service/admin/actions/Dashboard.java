package com.photon.phresco.service.admin.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.service.admin.commons.ServiceActions;

public class Dashboard extends ActionSupport implements ServiceActions {

	public String list() {
		return DASHBOARD_LIST;
	}
}
