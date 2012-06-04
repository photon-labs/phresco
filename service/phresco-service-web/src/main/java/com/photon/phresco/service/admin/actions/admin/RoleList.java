package com.photon.phresco.service.admin.actions.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class RoleList extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(RoleList.class);
	
	private String name = null;
	private String nameError = null;
	private boolean errorFound = false;
	
	public String list() {
		S_LOGGER.debug("Entering Method RolesList.list()");
		return ADMIN_ROLE_LIST;	
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method RolesList.add()");
		return ADMIN_ROLE_ADD;	
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method RolesList.save()");
		
		if (validateForm()) {
			setErrorFound(true);
			return SUCCESS;
		}
		return  ADMIN_ROLE_LIST;
	}
		
	private boolean validateForm() {
		boolean isError = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY));
			isError = true;
		} 
		
		return isError;
	}
	
	public String cancel() {
		S_LOGGER.debug("Entering Method RolesList.cancel()");
		return ADMIN_ROLE_CANCEL;	
	}
	
	public String assign() {
		S_LOGGER.debug("Entering Method RolesList.assign()");
		return ADMIN_ROLE_ASSIGN;	
	}
	
	public String assignSave() {
		S_LOGGER.debug("Entering Method RolesList.assignSave()");
		return ADMIN_ROLE_ASSIGN_SAVE;	
	}
	
	public String assignCancel() {
		S_LOGGER.debug("Entering Method RolesList.assignCancel()");
		return ADMIN_ROLE_ASSIGN_CANCEL;	
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