package com.photon.phresco.framework.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.model.LogInfo;
import com.photon.phresco.model.UserInfo;

public class LogErrorReport extends FrameworkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogErrorReport(Exception e, String action) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        LogInfo log = new LogInfo(e.getLocalizedMessage(), stacktrace, action, ((UserInfo)getHttpSession().getAttribute(REQ_USER_INFO)).getUserName());
        getHttpRequest().setAttribute("logReport", log);
        addActionError(e.getLocalizedMessage());
	}
}
