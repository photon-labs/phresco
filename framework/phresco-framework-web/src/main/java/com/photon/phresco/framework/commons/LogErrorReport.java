/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
