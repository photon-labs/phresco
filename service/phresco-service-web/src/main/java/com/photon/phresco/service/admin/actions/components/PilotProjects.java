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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.admin.actions.ServiceBaseAction;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class PilotProjects extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(PilotProjects.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private String name = null;
	private String nameError = null;
	private String fileError = null;
	private boolean errorFound = false;
	
	private File projArc;
	private String projArcFileName;
	private String projArcContentType;

	private String description = null;
	private String projectId = null;
	private String fromPage = null;
	private String customerId = null;
	

    public String list() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method PilotProjects.list()");
        }

		try {
			List<ProjectInfo> pilotProjects = getServiceManager().getPilotProject(customerId);
			getHttpRequest().setAttribute(REQ_PILOT_PROJECTS, pilotProjects);
			getHttpRequest().setAttribute(REQ_CUST_CUSTOMER_ID, customerId);
			
			RestClient<Technology> technology = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_TECHNOLOGIES);
			GenericType<List<Technology>> genericType = new GenericType<List<Technology>>(){};
			List<Technology> technologys = technology.get(genericType);
			getHttpRequest().setAttribute(REQ_ARCHE_TYPES, technologys);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		
		return COMP_PILOTPROJ_LIST;
	}
	
    public String add() throws PhrescoException {
    	if (isDebugEnabled) {	
    		S_LOGGER.debug("Entering Method PilotProjects.add()");
    	}
    	
    	try {
    		RestClient<Technology> technology = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_TECHNOLOGIES);
    		GenericType<List<Technology>> genericType = new GenericType<List<Technology>>(){};
    		List<Technology> technologys = technology.get(genericType);
    		getHttpRequest().setAttribute(REQ_ARCHE_TYPES, technologys);
    	} catch(Exception e) {
    		throw new PhrescoException(e);
    	} 
    	return COMP_PILOTPROJ_ADD;
    }
	
    public String save() throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method PilotProjects.save()");
    	}

    	try {
    		InputStream inputStream = new FileInputStream(projArc);
    		FileOutputStream outputStream = new FileOutputStream(new File("c:/" + projArcFileName));
    		IOUtils.copy(inputStream, outputStream);
    		List<ProjectInfo> pilotProInfo = new ArrayList<ProjectInfo>();
    		ProjectInfo proInfo = new ProjectInfo();
    		proInfo.setName(name);
    		proInfo.setDescription(description);
    		pilotProInfo.add(proInfo);
    		ClientResponse clientResponse = getServiceManager().createPilotProject(pilotProInfo);
    		if(clientResponse.getStatus() != 200 && clientResponse.getStatus() != 201  ){
    			addActionError(getText(PLTPROJ_NOT_ADDED, Collections.singletonList(name)));
    		} else {
    			addActionMessage(getText(PLTPROJ_ADDED, Collections.singletonList(name)));
    		}
    	} catch (Exception e) {
    		throw new PhrescoException(e);
    	}

    	return list();
    }
	
    public String edit() throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method PilotProjects.edit()");
    	}

    	try {
    		ProjectInfo pilotProjectInfo = getServiceManager().getPilotPro(projectId);
    		getHttpRequest().setAttribute(REQ_PILOT_PROINFO, pilotProjectInfo);
    		getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
    	} catch (Exception e) {
    		throw new PhrescoException(e);
    	}

    	return COMP_PILOTPROJ_ADD;
    }
	
    public String update() throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method  PilotProjects.update()");
    	}

    	try {
    		ProjectInfo pilotProInfo = new ProjectInfo();
    		pilotProInfo.setId(projectId);
    		pilotProInfo.setName(name);
    		pilotProInfo.setDescription(description);
    		getServiceManager().updatePilotProject(pilotProInfo, projectId);
    	} catch(Exception e) {
    		throw new PhrescoException(e);
    	}

    	return list();
    }
	
    public String delete() throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method PilotProjects.delete()");
    	}

    	try {
    		String[] projectIds = getHttpRequest().getParameterValues("projectId");
    		if (projectIds != null) {
    			for (String projectID : projectIds) {
    				ClientResponse clientResponse =getServiceManager().deletePilotProject( projectID);
    				if (clientResponse.getStatus() != 200) {
    					addActionError(getText(PLTPROJ_NOT_DELETED));
    				}
    			}
    			addActionMessage(getText(PLTPROJ_DELETED));
    		}
    	} catch (Exception e) {
    		throw new PhrescoException(e);
    	}

    	return list();
    }
	
    public String validateForm() {
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  PilotProjects.validateForm()");
		}
    	
    	boolean isError = false;
    	if (StringUtils.isEmpty(name)) {
    		setNameError(getText(KEY_I18N_ERR_NAME_EMPTY ));
    		isError = true;
    	}

    	if (StringUtils.isEmpty(projArcFileName) || projArc == null) {
    		setFileError(getText(KEY_I18N_ERR_PLTPROJ_EMPTY));
    		isError = true;
    	}
    	
    	if (isError) {
    		setErrorFound(true);
    	}

    	return SUCCESS;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}
	
	public String getFileError() {
		return fileError;
	}

	public void setFileError(String fileError) {
		this.fileError = fileError;
	}

	public File getProjArc() {
		return projArc;
	}

	public void setProjArc(File projArc) {
		this.projArc = projArc;
	}

	public String getProjArcFileName() {
		return projArcFileName;
	}

	public void setProjArcFileName(String projArcFileName) {
		this.projArcFileName = projArcFileName;
	}

	public String getProjArcContentType() {
		return projArcContentType;
	}

	public void setProjArcContentType(String projArcContentType) {
		this.projArcContentType = projArcContentType;
	}

	public boolean isErrorFound() {
		return errorFound;
	}

	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}