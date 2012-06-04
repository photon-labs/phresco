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
package com.photon.phresco.framework.actions.applications;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.UpdateManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.VersionInfo;

public class VersionUpdate extends FrameworkBaseAction {
	private static final long serialVersionUID = 1L;

	private static final Logger S_LOGGER = Logger.getLogger(VersionUpdate.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	private VersionInfo versionInfo = null;
	// current version
	private String latestVersion = null;
	private String currentVersion = null;
	private String message = null;
	private boolean isUpdateAvail;

	public String about() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method VersionUpdate.about()");
		}
		return ABOUT;
	}

	public String versionInfo() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method VersionUpdate.versionInfo()");
		}
		try {
			versionInfo = new VersionInfo();
			UpdateManager updateManager = PhrescoFrameworkFactory.getUpdateManager();
			currentVersion = updateManager.getCurrentVersion();
			versionInfo = updateManager.checkForUpdate(currentVersion);
			message = versionInfo.getMessage();
			latestVersion = versionInfo.getFrameworkversion();
			isUpdateAvail = versionInfo.isUpdateAvailable();
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of VersionUpdate.versionInfo()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return SUCCESS;
	}

	public String update() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method VersionUpdate.update()");
		}
		try {
			UpdateManager updateManager = PhrescoFrameworkFactory.getUpdateManager();
			HttpServletRequest request = getHttpRequest();
			String newVersion = (String) getHttpRequest().getAttribute(REQ_LATEST_VERSION);
			updateManager.doUpdate(newVersion);
			request.setAttribute(REQ_UPDATED_MESSAGE, getText(ABOUT_SUCCESS_UPDATE));
		} catch (Exception e) {
			getHttpRequest().setAttribute(REQ_UPDATED_MESSAGE, getText(ABOUT_FAILURE_FAILURE));
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of VersionUpdate.update()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return SUCCESS;
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isUpdateAvail() {
		return isUpdateAvail;
	}

	public void setUpdateAvail(boolean isUpdateAvail) {
		this.isUpdateAvail = isUpdateAvail;
	}

}
