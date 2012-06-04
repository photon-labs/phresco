package com.photon.phresco.service.admin.actions.components;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class ApplicationTypes extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(ApplicationTypes.class);
	
	private String name = null;
	private String nameError = null;
	private boolean errorFound = false;
	
	public String list() {
		S_LOGGER.debug("Entering Method ApplicationTypes.list()");
		return COMP_APPTYPE_LIST;
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method ApplicationTypes.add()");
		return COMP_APPTYPE_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method ApplicationTypes.save()");
		if (validateForm()) {
			setErrorFound(true);
			return SUCCESS;
		}
		return  COMP_APPTYPE_LIST;
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
}
