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
import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class PilotProjects extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(PilotProjects.class);
	
	private String name = null;
	private String nameError = null;
	private String fileError = null;
	private boolean errorFound = false;
	
	private File projArc;
	private String projArcFileName;
	private String projArcContentType;

	public String list() {
		S_LOGGER.debug("Entering Method PilotProjects.list()");
		return COMP_PILOTPROJ_LIST;
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method PilotProjects.add()");
		return COMP_PILOTPROJ_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method PilotProjects.save()");
		
		try {
			if (validateForm()) {
				setErrorFound(true);
				return SUCCESS;
			}
				InputStream inputStream = new FileInputStream(projArc);
				/* FileOutputStream outputStream = new FileOutputStream(new File("c:/" + projArcFileName));
				IOUtils.copy(inputStream, outputStream);*/
				addActionMessage(getText(PLTPROJ_ADDED, Collections.singletonList(name)));
		} catch (Exception e) {
			addActionError(getText(PLTPROJ_NOT_ADDED, Collections.singletonList(name)));
		} 
		return COMP_PILOTPROJ_LIST;
	}
	
	private boolean validateForm() {
		boolean success = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY ));
			success = true;
		}
		
		if (StringUtils.isEmpty(projArcFileName) || projArc == null) {
			setFileError(getText(KEY_I18N_ERR_PLTPROJ_EMPTY));
			success = true;
		}
		return success;
	}
	
	public String cancel() {
		S_LOGGER.debug("Entering Method PilotProjects.cancel()");
		return COMP_PILOTPROJ_CANCEL;
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
	
}
