/*
 * ###
 * Service Web Archive
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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
package com.photon.phresco.service.admin.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.photon.phresco.model.UserInfo;

public class Login extends ServiceBaseAction {

	private static final long serialVersionUID = -1858839078372821734L;
	private static final Logger S_LOGGER = Logger.getLogger(Login.class);
	private String username = null;
	private String password = null;
	private boolean loginFirst = true;
	private String css = null;
	
	public String login() {
		S_LOGGER.debug("Entering Method  Login.login()");

		if (loginFirst) {
			HttpServletRequest request = getHttpRequest();
			Cookie[] cookies = request.getCookies();
			for(int i = 0; i < cookies.length; i++) { 
				Cookie cookiecss = cookies[i];
				if (cookiecss.getName().equals("css")) {
					css = cookiecss.getValue();
					css = css.replace("%2F","/");
					request.setAttribute("css", css);
				}  
			} 
			return LOGIN_RESULT;	
		}

		if (validateLogin()) {
			return authenticate();
		}
		
		return LOGIN_FAILURE;
	}
	
	private String authenticate() {
		S_LOGGER.debug("Entering Method  Login.authenticate()");
		
		UserInfo userInfo = null;
			try {
				byte[] encodeBase64 = Base64.encodeBase64(password.getBytes());
				String encodedPassword = new String(encodeBase64);
				
				userInfo = doLogin(username, encodedPassword);
				if (StringUtils.isEmpty(userInfo.getDisplayName())) {
					getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN));
					return LOGIN_FAILURE;
				}
				if (!userInfo.isPhrescoEnabled()) {
					getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(ERROR_LOGIN_ACCESS_DENIED));
					return LOGIN_FAILURE;
				} 
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN_FAILURE;
			}
			
		return LOGIN_SUCCESS;
	}

	private boolean validateLogin() {
		S_LOGGER.debug("Entering Method  Login.validateLogin()");
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			getHttpRequest().setAttribute(REQ_LOGIN_ERROR, getText(KEY_I18N_LOGIN_EMPTY_CRED));
			return false;
		}
		
		return true;
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

	public boolean isLoginFirst() {
		return loginFirst;
	}

	public void setLoginFirst(boolean loginFirst) {
		this.loginFirst = loginFirst;
	}
}
