package com.photon.phresco.model;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class VersionInfo {
	String serviceversion;
	String frameworkversion;
	String message;
	boolean updateAvailable;

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isUpdateAvailable() {
		return updateAvailable;
	}

	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}

	public String getFrameworkversion() {
		return frameworkversion;
	}

	public void setFrameworkversion(String frameworkversion) {
		this.frameworkversion = frameworkversion;
	}
	public String getServiceversion() {
		return serviceversion;
	}

	public void setServiceversion(String serviceversion) {
		this.serviceversion = serviceversion;
	}
}
