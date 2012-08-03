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
package com.photon.phresco.service.admin.actions.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.admin.actions.ServiceBaseAction;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class Downloads extends ServiceBaseAction { 

	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(Downloads.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	private String name = null;
	private String nameError = null;
	private String version = null;
	private String verError = null;
	private String application = null;
	private String appltError = null;
	private String group = null;
	private String groupError = null;
	private String fileError = null;
	private boolean errorFound = false;

	private File fileArc;
	private String fileArcFileName;
	private String fileArcContentType;

	private File iconArc;
	private String iconArcFileName;
	private String iconArcContentType;

	private String description=null;
	private String id = null;
	private String fromPage = null;
	private String customerId = null;

	public String list() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Downloads.list()");
		}
		try {
			List<DownloadInfo> downloadInfo = getServiceManager().getDownloads(customerId);
			getHttpRequest().setAttribute(REQ_DOWNLOAD_INFO, downloadInfo);
			getHttpRequest().setAttribute(REQ_CUST_CUSTOMER_ID, customerId);
		} catch(Exception e){
			throw new PhrescoException(e);
		}

		return ADMIN_DOWNLOAD_LIST;	
	}

	public String add() throws PhrescoException {
		if (isDebugEnabled) {	
			S_LOGGER.debug("Entering Method Downloads.add()");
		}
		try {
			RestClient<Technology> technology = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_TECHNOLOGIES);
			GenericType<List<Technology>> genericType = new GenericType<List<Technology>>(){};
			List<Technology> technologys = technology.get(genericType);
			getHttpRequest().setAttribute(REQ_ARCHE_TYPES, technologys);
		} catch(Exception e) {
			throw new PhrescoException(e);
		}
		
		return ADMIN_DOWNLOAD_ADD;
	}

	public String save() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Downloads.save()");
		}

		try {
			/*if(fileArc != null) {
					InputStream inputStream = new FileInputStream(fileArc);
					FileOutputStream outputStream = new FileOutputStream(new File("c:/" + fileArcFileName));
					IOUtils.copy(inputStream, outputStream);
				}
				if(iconArc != null) {
					InputStream inputStream = new FileInputStream(iconArc);
					FileOutputStream outputStream = new FileOutputStream(new File("c:/" + iconArcFileName));
					IOUtils.copy(inputStream, outputStream);
				}*/

			List<DownloadInfo> downloadInfo = new ArrayList<DownloadInfo>();
			DownloadInfo download = new DownloadInfo();
			download.setName(name);
			download.setDescription(description);
			download.setVersion(version);
			downloadInfo.add(download);
			ClientResponse clientResponse = getServiceManager().createDownload(downloadInfo);
			if(clientResponse.getStatus() != 200){
				addActionError(getText(DOWNLOAD_NOT_ADDED, Collections.singletonList(name)));
			} else {
				addActionMessage(getText(DOWNLOAD_ADDED, Collections.singletonList(name)));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return list();
	}

	public String edit() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Downloads.edit()");
		}

		try {
			DownloadInfo downloadInfo = getServiceManager().getDownload(id);
			getHttpRequest().setAttribute(REQ_DOWNLOAD_INFO, downloadInfo);
			getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return ADMIN_DOWNLOAD_ADD;
	}


	public String update() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  Downloads.update()");
		}

		try {
			DownloadInfo download = new DownloadInfo();
			download.setId(id);
			download.setName(name);
			download.setDescription(description);
			download.setVersion(version);
			getServiceManager().updateDownload(download, id);
		} catch(Exception e) {
			throw new PhrescoException(e);
		}

		return list();
	}

	public String delete() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Downloads.delete()");
		}

		try {
			String[] downloadIds = getHttpRequest().getParameterValues(REQ_DOWNLOAD_ID);
			if (downloadIds != null) {
				for (String downloadId : downloadIds) {
					ClientResponse clientResponse =getServiceManager().deleteDownloadInfo(downloadId);
					if (clientResponse.getStatus() != 200) {
						addActionError(getText(DOWNLOAD_NOT_DELETED));
					}
				}
				addActionMessage(getText(DOWNLOAD_DELETED));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return list();
	}


	public String validateForm() {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Downloads.validateForm()");
		}
		
		boolean isError = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY));
			isError = true;
		} 

		if (StringUtils.isEmpty(version)) {
			setVerError(getText(KEY_I18N_ERR_VER_EMPTY));
			isError = true;
		} 

		if (StringUtils.isEmpty(application)) {
			setAppltError(getText(KEY_I18N_ERR_APPLNPLTF_EMPTY));
			isError = true;
		} 

		if (StringUtils.isEmpty(group)) {
			setGroupError(getText(KEY_I18N_ERR_GROUP_EMPTY));
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

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getAppltError() {
		return appltError;
	}

	public void setAppltError(String appltError) {
		this.appltError = appltError;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroupError() {
		return groupError;
	}

	public void setGroupError(String groupError) {
		this.groupError = groupError;
	}

	public String getFileError() {
		return fileError;
	}

	public void setFileError(String fileError) {
		this.fileError = fileError;
	}

	public File getFileArc() {
		return fileArc;
	}

	public void setFileArc(File fileArc) {
		this.fileArc = fileArc;
	}

	public String getFileArcFileName() {
		return fileArcFileName;
	}

	public void setFileArcFileName(String fileArcFileName) {
		this.fileArcFileName = fileArcFileName;
	}

	public String getFileArcContentType() {
		return fileArcContentType;
	}

	public void setFileArcContentType(String fileArcContentType) {
		this.fileArcContentType = fileArcContentType;
	}

	public File getIconArc() {
		return iconArc;
	}

	public void setIconArc(File iconArc) {
		this.iconArc = iconArc;
	}

	public String getIconArcFileName() {
		return iconArcFileName;
	}

	public void setIconArcFileName(String iconArcFileName) {
		this.iconArcFileName = iconArcFileName;
	}

	public String getIconArcContentType() {
		return iconArcContentType;
	}

	public void setIconArcContentType(String iconArcContentType) {
		this.iconArcContentType = iconArcContentType;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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