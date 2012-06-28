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
package com.photon.phresco.service.admin.actions.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.service.admin.actions.ServiceBaseAction;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ApplicationTypes extends ServiceBaseAction { 

	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(ApplicationTypes.class);

	private String name = null;
	private String description = null;
	private String nameError = null;
	private boolean errorFound = false;
	private String fromPage = null;
	private String appTypeId = null;
	private String oldName = null;

	public String list() throws PhrescoException {
		S_LOGGER.debug("Entering Method ApplicationTypes.list()");
		try {
			RestClient<ApplicationType> appType = getServiceManager().getRestClient("component" + ServiceConstants.REST_API_APPTYPES);
			GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
			List<ApplicationType> appTypes = appType.get(genericType);
			getHttpRequest().setAttribute("appTypes", appTypes);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return COMP_APPTYPE_LIST;
	}

	public String add() {
		S_LOGGER.debug("Entering Method ApplicationTypes.add()");
		return COMP_APPTYPE_ADD;
	}

	public String edit() throws PhrescoException {
		try {
			RestClient<ApplicationType> appTypes = getServiceManager().getRestClient("component" + ServiceConstants.REST_API_APPTYPES + "/" + appTypeId);
			GenericType<ApplicationType> genericType = new GenericType<ApplicationType>(){};
			ApplicationType appType = appTypes.getById(genericType);
			getHttpRequest().setAttribute("appType", appType);
			getHttpRequest().setAttribute("fromPage", fromPage);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return COMP_APPTYPE_ADD;
	}

	public String save() throws PhrescoException {
		S_LOGGER.debug("Entering Method ApplicationTypes.save()");
		try {
			if (validateForm()) {
				setErrorFound(true);
				return SUCCESS;
			}
			List<ApplicationType> appTypes = new ArrayList<ApplicationType>();
			ApplicationType appType = new ApplicationType(name, description);
			appTypes.add(appType);
			RestClient<ApplicationType> newApp = getServiceManager().getRestClient("component" + ServiceConstants.REST_API_APPTYPES);
			ClientResponse clientResponse = newApp.create(appTypes);
			if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 201) {
				addActionError(getText(APPLNTYPES_NOT_ADDED, Collections.singletonList(name)));
			} else {
				addActionMessage(getText(APPLNTYPES_ADDED, Collections.singletonList(name)));
			} 
		}catch (Exception e) {
			throw new PhrescoException(e);
		}
		return  list();
	}

	public String update() throws PhrescoException {
		S_LOGGER.debug("Entering Method  Apptypes.update()");

		try {
			if (validateForm()) {
				setErrorFound(true);
				return SUCCESS;
			}
			ApplicationType appType = new ApplicationType(name, description);
			appType.setId(appTypeId);
			RestClient<ApplicationType> editApptype = getServiceManager().getRestClient("component" + ServiceConstants.REST_API_APPTYPES + "/" + appTypeId);
			GenericType<ApplicationType> genericType = new GenericType<ApplicationType>() {};
			ApplicationType updatedAppType = editApptype.updateById(appType, genericType);
		} catch(Exception e)  {
			throw new PhrescoException(e);
		}

		return list();
	}

	public String delete() throws PhrescoException {
		S_LOGGER.debug("Entering Method  AppType.delete()");

		try {
			String[] appTypeIds = getHttpRequest().getParameterValues("apptypeId");
			if (appTypeIds != null) {
				for (String appTypeId : appTypeIds) {
					RestClient<ApplicationType> deleteApptype = getServiceManager().getRestClient("component" + ServiceConstants.REST_API_APPTYPES);
					deleteApptype.setPath(appTypeId);
					ClientResponse clientResponse = deleteApptype.deleteById();
					if (clientResponse.getStatus() != 200) {
						addActionError(getText(APPLNTYPES_NOT_DELETED));
					}
				}
				addActionMessage(getText(APPLNTYPES_DELETED));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return list();
	}

	private boolean validateForm() {
		boolean isError = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY ));
			isError = true;
		}
		return isError;
	}

	public String cancel() {
		S_LOGGER.debug("Entering Method ApplicationTypes.cancel()");
		return COMP_APPTYPE_CANCEL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}

	public boolean isErrorFound() {
		return errorFound;
	}

	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}

	public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
}
