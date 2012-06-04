package com.photon.phresco.service.admin.actions.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class GlobalUrlList extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(GlobalUrlList.class);
	
	private String name = null;
	private String nameError = null;
	private String url = null;
	private String urlError = null;
	private boolean errorFound = false;
	
	public String list() {
		S_LOGGER.debug("Entering Method GlobalUrlList.list()");
		return ADMIN_GLOBALURL_LIST;	
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method GlobalUrlList.list()");
		return ADMIN_GLOBALURL_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method GlobalUrlList.list()");
		
		if (validateForm()) {
			setErrorFound(true);
			return SUCCESS;
		}
		return  ADMIN_GLOBALURL_LIST;
	}
	
	private boolean validateForm() {
		boolean isError = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(url)) {
			setUrlError(getText(KEY_I18N_ERR_URL_EMPTY));
			isError = true;
		} 
		
		return isError;
	}
	
	public String cancel() {
		S_LOGGER.debug("Entering Method GlobalUrlList.list()");
		return ADMIN_GLOBALURL_CANCEL;
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlError() {
		return urlError;
	}

	public void setUrlError(String urlError) {
		this.urlError = urlError;
	}

	

	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}

	
}