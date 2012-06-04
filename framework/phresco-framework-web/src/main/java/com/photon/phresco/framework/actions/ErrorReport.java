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
package com.photon.phresco.framework.actions;

import org.apache.log4j.Logger;

import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.LogInfo;

public class ErrorReport extends FrameworkBaseAction {
	private static final long serialVersionUID = 1L;
	
	private static final Logger S_LOGGER = Logger.getLogger(ErrorReport.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
	
	private String message = null;
	private String action = null;
	private String userid = null;
	private String trace = null;
	private String reportStatus = null;
	
	public String sendReport() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  ErrorReport.sendReport() message" + message + " action" + action + " userid" + userid );
		}
        try {
        	LogInfo loginfo = new LogInfo(message, trace, action, userid);
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
        	if (debugEnabled) {
        		S_LOGGER.debug("Going to send error report to service ");
    		}
        	reportStatus = administrator.sendReport(loginfo);
        } catch (Exception e) {
            if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of  ErrorReport.sendReport()" + FrameworkUtil.getStackTraceAsString(e));
            }
        }
        return "success";
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the trace
	 */
	public String getTrace() {
		return trace;
	}
	/**
	 * @param trace the trace to set
	 */
	public void setTrace(String trace) {
		this.trace = trace;
	}

	/**
	 * @return the reportStatus
	 */
	public String getReportStatus() {
		return reportStatus;
	}

	/**
	 * @param reportStatus the reportStatus to set
	 */
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	

}
