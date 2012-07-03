/*
 * ###
 * Service Web Archive
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
package com.photon.phresco.service.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.service.admin.commons.ServiceActions;
import com.photon.phresco.service.admin.commons.ServiceUIConstants;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.ServiceConstants;

public class ServiceBaseAction extends ActionSupport implements ServiceActions, ServiceUIConstants, ServiceClientConstant, ServerConstants {

//	private static final Logger S_LOGGER = Logger.getLogger(ServiceBaseAction.class);
    private static final long serialVersionUID = 1L;
    
    private static ServiceManager serviceManager = null;
    
    public ServiceManager getServiceManager() {
		return serviceManager;
	}

	protected User doLogin(String userName, String password) throws PhrescoException {
		StringBuilder serverURL = new StringBuilder();
		serverURL.append(getHttpRequest().getScheme());
		serverURL.append(ServiceConstants.COLON_DOUBLE_SLASH);
		serverURL.append(getHttpRequest().getServerName());
		serverURL.append(ServiceConstants.COLON);
		serverURL.append(getHttpRequest().getServerPort());
		serverURL.append(getHttpRequest().getContextPath());
		serverURL.append(ServiceConstants.SLASH_REST_SLASH_API);
    	ServiceContext context = new ServiceContext();
		context.put(SERVICE_URL, serverURL.toString());
		context.put(SERVICE_USERNAME, userName);
		context.put(SERVICE_PASSWORD, password);
		serviceManager = ServiceClientFactory.getServiceManager(context);
		return serviceManager.getUserInfo();
    }
    
    
    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }
    
    public HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }
    
}
