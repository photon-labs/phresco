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
package com.photon.phresco.framework.actions.home;

import java.util.List;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkActions;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;

public class Home extends FrameworkBaseAction implements FrameworkActions {
	private static final long serialVersionUID = -9002492813622189809L;
	
	private static final Logger S_LOGGER   = Logger.getLogger(Home.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	public String welcome() {
        return HOME_WELCOME;
    }
	
	public String view() {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method  Home.view()");
		}

	    try {
			ProjectAdministrator projectAdministrator = getProjectAdministrator();
			List<VideoInfo> videoInfos = projectAdministrator.getVideoInfos();
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			getHttpRequest().setAttribute(REQ_SERVER_URL, configuration.getServerPath());
			getHttpRequest().setAttribute(REQ_VIDEO_INFOS, videoInfos);
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
	    
		return HOME_VIEW;
	}

	public String video() {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method  Home.video()");
		}

		try {
			String videoName = getHttpRequest().getParameter(REQ_VIDEO);
			ProjectAdministrator projectAdministrator = getProjectAdministrator();
			List<VideoType> videoTypes = projectAdministrator.getVideoTypes(videoName);
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			getHttpRequest().setAttribute(REQ_SERVER_URL, configuration.getServerPath());
			getHttpRequest().setAttribute(REQ_VIDEO_TYPES, videoTypes);
		} catch (PhrescoException e) {
			e.printStackTrace();
		}

		return HOME_VIDEO;
	}
}
