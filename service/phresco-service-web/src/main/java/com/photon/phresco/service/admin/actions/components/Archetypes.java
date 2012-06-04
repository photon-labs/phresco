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

public class Archetypes extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(Archetypes.class);
	
	private String name = null;
	private String nameError = null;
	private String version = null;
	private String verError = null;
	private String apptype = null;
	private String appError = null;
	private String fileError = null;
	private boolean errorFound = false;
	
	private File applnArc;
	private String applnArcFileName;
	private String applnArcContentType;
	
	private File pluginArc;
	private String pluginArcFileName;
	private String pluginArcContentType;


	public String list() {
		S_LOGGER.debug("Entering Method Archetypes.list()");
		return COMP_ARCHETYPE_LIST;
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method Archetypes.add()");
		return COMP_ARCHETYPE_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method Archetypes.save()");
		try {
			boolean isValid = validateForm();
			InputStream inputStream = null;
			FileOutputStream outputStream = null;
//			boolean isMultipart = ServletFileUpload.isMultipartContent(getHttpRequest());
			if (isValid) {
				inputStream = new FileInputStream(applnArc);
				/*outputStream = new FileOutputStream(new File("c:/temp/" + applnArcFileName));
				IOUtils.copy(inputStream, outputStream);*/
			
				if(pluginArc != null) {
					inputStream = new FileInputStream(pluginArc);
					outputStream = new FileOutputStream(new File("c:/temp/" + pluginArcFileName));
					IOUtils.copy(inputStream, outputStream);
				}
				return COMP_ARCHETYPE_LIST;
			}
			
		} catch (IOException e) {
			
		} 

		setErrorFound(true);
		return SUCCESS;
	}
	
	private boolean validateForm() {
		boolean success = true;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY ));
			success = false;
		}
		
		if (StringUtils.isEmpty(version)) {
			setVerError(getText(KEY_I18N_ERR_VER_EMPTY));
			success = false;
		}
		
		if (StringUtils.isEmpty(apptype)) {
			setAppError(getText(KEY_I18N_ERR_APPTYPE_EMPTY));
			success = false;
		}
		
		if (StringUtils.isEmpty(applnArcFileName) || applnArc == null) {
			setFileError(getText(KEY_I18N_ERR_APPLNJAR_EMPTY));
			success = false;
		}
		return success;
	}
	public String cancel() {
		S_LOGGER.debug("Entering Method Archetypes.cancel()");
		return COMP_ARCHETYPE_CANCEL;
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
	
	
}
