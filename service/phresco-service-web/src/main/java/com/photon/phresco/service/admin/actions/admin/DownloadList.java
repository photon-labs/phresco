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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class DownloadList extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(DownloadList.class);
	
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

	public String list() {
		S_LOGGER.debug("Entering Method DownloadList.list()");
		return ADMIN_DOWNLOAD_LIST;	
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method DownloadList.add()");
		return ADMIN_DOWNLOAD_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method DownloadList.save()");
		
		try {
			boolean isValid = validateForm();
//			boolean isMultipart = ServletFileUpload.isMultipartContent(getHttpRequest());
			if (isValid) {
				if(fileArc != null) {
					InputStream inputStream = new FileInputStream(fileArc);
					/*FileOutputStream outputStream = new FileOutputStream(new File("c:/" + fileArcFileName));
					IOUtils.copy(inputStream, outputStream);*/
				}
				if(iconArc != null) {
					InputStream inputStream = new FileInputStream(iconArc);
					/*FileOutputStream outputStream = new FileOutputStream(new File("c:/" + iconArcFileName));
					IOUtils.copy(inputStream, outputStream);*/
				}
				return ADMIN_DOWNLOAD_LIST;
			}
			
		} catch (IOException e) {
			
		} 

		setErrorFound(true);
		return SUCCESS;
	}
	
	private boolean validateForm() {
		boolean success = true;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY));
			success = false;
		} 
		
		if (StringUtils.isEmpty(version)) {
			setVerError(getText(KEY_I18N_ERR_VER_EMPTY));
			success = false;
		} 
		
		if (StringUtils.isEmpty(application)) {
			setAppltError(getText(KEY_I18N_ERR_APPLNPLTF_EMPTY));
			success = false;
		} 
		
		if (StringUtils.isEmpty(group)) {
			setGroupError(getText(KEY_I18N_ERR_GROUP_EMPTY));
			success = false;
		}
		
		return success;
	}
	
	
	public String cancel() {
		S_LOGGER.debug("Entering Method DownloadList.cancel()");
		return ADMIN_DOWNLOAD_CANCEL;
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

}