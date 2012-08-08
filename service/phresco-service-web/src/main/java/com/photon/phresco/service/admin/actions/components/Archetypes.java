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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.admin.actions.ServiceBaseAction;
import com.sun.jersey.api.client.ClientResponse;

public class Archetypes extends ServiceBaseAction { 

	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(Archetypes.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	private String name = null;
	private String nameError = null;
	private String version = null;
	private List<String> versionList = null;
	private String verError = null;
	private String apptype = null;
	private String appError = null;
	private String fileError = null;
	private boolean errorFound = false;

	private String description;
	private String fromPage = null;
	private String techId = null;
    private String customerId = null;
	
	private String versionComment = null;
	private List<String> appType = null;
	private String appJar = null;
	private String pluginJar = null;

	private File applnArc;
	private String applnArcFileName;
	private String applnArcContentType;

	private File pluginArc;
	private String pluginArcFileName;
	private String pluginArcContentType;


	public String list() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Archetypes.list()");
		}

		try {
			List<Technology> technologys = getServiceManager().getArcheTypes(customerId);
			getHttpRequest().setAttribute(REQ_ARCHE_TYPES, technologys);
			getHttpRequest().setAttribute(REQ_CUST_CUSTOMER_ID, customerId);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return COMP_ARCHETYPE_LIST;
	}

	public String add() throws PhrescoException {
	    if (isDebugEnabled) {
	        S_LOGGER.debug("Entering Method Archetypes.add()");
	    }

		try {
			List<ApplicationType> appTypes = getServiceManager().getApplicationTypes(customerId);
			getHttpRequest().setAttribute(REQ_APP_TYPES, appTypes);
		} catch (PhrescoException e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}

		return COMP_ARCHETYPE_ADD;
	}

	public String edit() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Archetypes.edit()");
		}

		try {
			Technology technology = getServiceManager().getArcheType(techId);
			getHttpRequest().setAttribute(REQ_ARCHE_TYPE,  technology);
			getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
			List<ApplicationType> appTypes = getServiceManager().getApplicationTypes(customerId);
			getHttpRequest().setAttribute(REQ_APP_TYPES, appTypes);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return COMP_ARCHETYPE_ADD;
	}

	public String save() throws PhrescoException {
	    if (isDebugEnabled) {
	        S_LOGGER.debug("Entering Method Archetypes.save()");
	    }
		
		try {
	

//			InputStream inputStream = null;
//			FileOutputStream outputStream = null;
			//			boolean isMultipart = ServletFileUpload.isMultipartContent(getHttpRequest());
			     //inputStream = new FileInputStream(applnArc);
			/*outputStream = new FileOutputStream(new File("c:/temp/" + applnArcFileName));
				IOUtils.copy(inputStream, outputStream);*/

			/*if(pluginArc != null) {
				inputStream = new FileInputStream(pluginArc);
				outputStream = new FileOutputStream(new File("c:/temp/" + pluginArcFileName));
				IOUtils.copy(inputStream, outputStream);
			}*/

			List<Technology> technologies = new ArrayList<Technology>();
			Technology technology = new Technology(name, description, versionList, appType);
			technology.setCustomerId(customerId);
			technologies.add(technology);
			ClientResponse clientResponse = getServiceManager().createArcheTypes(technologies, customerId);

			if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 201) {
				addActionError(getText(ARCHETYPE_NOT_ADDED, Collections.singletonList(name)));
			} else {
				addActionMessage(getText(ARCHETYPE_ADDED, Collections.singletonList(name)));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		} 

		return list();
	}

	public String update() throws PhrescoException {
	    if (isDebugEnabled) {
	        S_LOGGER.debug("Entering Method Archetypes.update()");
	    }

		try {
			Technology technology = new Technology(name, description, versionList, appType);
			technology.setId(techId);
			getServiceManager().updateArcheTypes(technology, techId);
		} catch(Exception e) {
			throw new PhrescoException(e);
		}
		
		return list();
	}

	public String delete() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Archetypes.delete()");
		}

		try {
			String[] techTypeIds = getHttpRequest().getParameterValues(REQ_ARCHE_TECHID);
			if (techTypeIds != null) {
				for (String techId : techTypeIds) {
					ClientResponse clientResponse = getServiceManager().deleteArcheType(techId);
					if (clientResponse.getStatus() != 200) {
						addActionError(getText(ARCHETYPE_NOT_DELETED));
					}
				}
				addActionMessage(getText(ARCHETYPE_DELETED));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return list();
	}

	public String validateForm() {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Archetypes.validateForm()");
		}
		
		boolean isError = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY ));
			isError = true;
		}

		if (StringUtils.isEmpty(version)) {
			setVerError(getText(KEY_I18N_ERR_VER_EMPTY));
			isError = true;
		}

		if (StringUtils.isEmpty(apptype)) {
			setAppError(getText(KEY_I18N_ERR_APPTYPE_EMPTY));
			isError = true;
		}

		if (StringUtils.isEmpty(applnArcFileName) || applnArc == null) {
			setFileError(getText(KEY_I18N_ERR_APPLNJAR_EMPTY));
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVerError() {
		return verError;
	}

	public void setVerError(String verError) {
		this.verError = verError;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public String getAppError() {
		return appError;
	}

	public void setAppError(String appError) {
		this.appError = appError;
	}

	public String getFileError() {
		return fileError;
	}

	public void setFileError(String fileError) {
		this.fileError = fileError;
	} 

	public File getApplnArc() {
		return applnArc;
	}

	public void setApplnArc(File applnArc) {
		this.applnArc = applnArc;
	}

	public String getApplnArcFileName() {
		return applnArcFileName;
	}

	public void setApplnArcFileName(String applnArcFileName) {
		this.applnArcFileName = applnArcFileName;
	}

	public String getApplnArcContentType() {
		return applnArcContentType;
	}

	public void setApplnArcContentType(String applnArcContentType) {
		this.applnArcContentType = applnArcContentType;
	}

	public File getPluginArc() {
		return pluginArc;
	}

	public void setPluginArc(File pluginArc) {
		this.pluginArc = pluginArc;
	}

	public String getPluginArcFileName() {
		return pluginArcFileName;
	}

	public void setPluginArcFileName(String pluginArcFileName) {
		this.pluginArcFileName = pluginArcFileName;
	}

	public String getPluginArcContentType() {
		return pluginArcContentType;
	}

	public void setPluginArcContentType(String pluginArcContentType) {
		this.pluginArcContentType = pluginArcContentType;
	}

	public boolean isErrorFound() {
		return errorFound;
	}

	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	public List<String> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<String> versionList) {
		this.versionList = versionList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersionComment() {
		return versionComment;
	}

	public void setVersionComment(String versionComment) {
		this.versionComment = versionComment;
	}

	public List<String> getAppType() {
		return appType;
	}

	public void setAppType(List<String> appType) {
		this.appType = appType;
	}

	public String getAppJar() {
		return appJar;
	}

	public void setAppJar(String appJar) {
		this.appJar = appJar;
	}

	public String getPluginJar() {
		return pluginJar;
	}

	public void setPluginJar(String pluginJar) {
		this.pluginJar = pluginJar;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}