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
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class Features extends ServiceBaseAction {

	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(Features.class);
	
	private String name = null;
	private String version = null;
	private String nameError = null;
	private String versError = null;
	private String fileError = null;
	private boolean errorFound = false;
	
	private File featureArc;
	private String featureArcFileName;
	private String featureArcContentType;

	public String list() {
		S_LOGGER.debug("Entering Method  Features.list()");
		
		return COMP_FEATURES_LIST;
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method  Features.add()");
		
		return COMP_FEATURES_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method  Features.save()");

		try {
			boolean isValid = validateForm();
//			boolean isMultipart = ServletFileUpload.isMultipartContent(getHttpRequest());
			if (isValid) {
				InputStream inputStream = new FileInputStream(featureArc);
				FileOutputStream outputStream = new FileOutputStream(new File("c:/" + featureArcFileName));
				IOUtils.copy(inputStream, outputStream);
				return COMP_FEATURES_LIST;
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
			setVersError(getText(KEY_I18N_ERR_VER_EMPTY));
			success = false;
		}
		
		if (StringUtils.isEmpty(featureArcFileName) || featureArc == null) {
			setFileError(getText(KEY_I18N_ERR_FILE_EMPTY));
			success = false;
		}
		return success;
	}

	public String cancel() {
		S_LOGGER.debug("Entering Method  Features.cancel()");

		return COMP_FEATURES_CANCEL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNameError() {
		return nameError;
	}
	
	public void setNameError(String nameError) {
		this.nameError = nameError;
	}
	
	public String getVersError() {
		return versError;
	}

	public void setVersError(String versError) {
		this.versError = versError;
	}

	public String getFileError() {
		return fileError;
	}

	public void setFileError(String fileError) {
		this.fileError = fileError;
	}

	public File getFeatureArc() {
		return featureArc;
	}

	public void setFeatureArc(File featureArc) {
		this.featureArc = featureArc;
	}

	public String getFeatureArcFileName() {
		return featureArcFileName;
	}

	public void setFeatureArcFileName(String featureArcFileName) {
		this.featureArcFileName = featureArcFileName;
	}

	public String getFeatureArcContentType() {
		return featureArcContentType;
	}

	public void setFeatureArcContentType(String featureArcContentType) {
		this.featureArcContentType = featureArcContentType;
	}

	public boolean getErrorFound() {
		return errorFound;
	}

	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}
	
}