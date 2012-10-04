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
/*
 * $Id: Login.java 471756 2006-11-06 15:01:43Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.photon.phresco.framework.actions;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.util.Credentials;

public class Login extends FrameworkBaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final Logger S_LOGGER = Logger.getLogger(Login.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
	private String username = null;
	private String password = null;
	private String fromPage = "";

	public String login() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Login.login()");
		}

		UserInfo userInfo = null;
		UserInfo sessionUserInfo = (UserInfo)getHttpSession().getAttribute(REQ_USER_INFO);
		if (sessionUserInfo != null) {
			loginSuccess(sessionUserInfo);
			return LOGIN_SUCCESS;
		}
		
		// When the user hits login button it ll come here
		if (fromPage.equals("login")) {
	        if (isInvalid(getUsername())) {
	        	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN_INVALID));
	        	return LOGIN_FAILURE;
	        }
	
	        if (isInvalid(getPassword())) {
	        	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN_INVALID));
	        	return LOGIN_FAILURE;
	        }
	        try {
	            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
	            Credentials credentials = new Credentials(username, password);
	            userInfo = administrator.doLogin(credentials);
	            
//	            if (userInfo.getDisplayName() == null) {
//	            	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN));
//	                return LOGIN_FAILURE;
//	            }
	            if (!userInfo.isValidLogin()) {
	            	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN));
	                return LOGIN_FAILURE;
	            }
	            if (!userInfo.isPhrescoEnabled()) {
	            	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN_ACCESS_DENIED));
	                return LOGIN_FAILURE;
	            } else {
	            	loginSuccess(userInfo);
	                return LOGIN_SUCCESS;
	            }
	            
	        } catch (Exception e) {
	        	if (debugEnabled) {
	                S_LOGGER.error("Entered into catch block of Login.login()"+ FrameworkUtil.getStackTraceAsString(e));
	    		}
	        	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_EXCEPTION));
	//        	getHttpRequest().setAttribute(REQ_LOGIN_ERROR, e.getLocalizedMessage());
	            return LOGIN_FAILURE;
	        }
		} else {
			// When the user enters the phresco url it ll come here
			getHttpRequest().setAttribute(REQ_LOGIN_ERROR, "");
			return LOGIN_FAILURE;
		}
    }
	
	public String logout() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Login.logout()");
		}
		getHttpSession().removeAttribute(REQ_USER_INFO);

		String errorTxt = (String) getHttpSession().getAttribute(REQ_LOGIN_ERROR);
		getHttpSession().removeAttribute(REQ_LOGIN_ERROR);
		if (StringUtils.isNotEmpty(errorTxt)) {
			getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(errorTxt));
		} else {
			getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(SUCCESS_LOGOUT));
		}
        return SUCCESS;
    }
	
	public void loginSuccess(UserInfo userInfo) {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Login.loginSuccess(UserInfo userInfo)");
		}
		if (debugEnabled) {
			S_LOGGER.debug("loginSuccess()  UserName = "+ userInfo.getUserName());
		}
		getHttpSession().setAttribute(REQ_USER_INFO, userInfo);
    	getHttpRequest().setAttribute(REQ_SHOW_WELCOME, getText(WELCOME_SHOW));
    	try {
    	ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
        List<VideoInfo> videoInfos = administrator.getVideoInfos();
        FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
        getHttpRequest().setAttribute(REQ_SERVER_URL, configuration.getServerPath());
        getHttpRequest().setAttribute(REQ_VIDEO_INFOS, videoInfos);
        
    	} catch (PhrescoException e) {
        	if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of Login.loginSuccess()"+ FrameworkUtil.getStackTraceAsString(e));
    		}
    	}
	}
	
	public String cmdLogin(){
		getHttpRequest().setAttribute("cmdLogin", "cmdLogin");
		return login();
	}
	
    private boolean isInvalid(String value) {
        return (value == null || value.length() == 0);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
    
}