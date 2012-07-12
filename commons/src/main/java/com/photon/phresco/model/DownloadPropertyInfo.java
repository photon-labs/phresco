package com.photon.phresco.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DownloadPropertyInfo {
	
	private String osName;
	private String techId;
	
	public DownloadPropertyInfo() {

	}
	
	public DownloadPropertyInfo(String osName, String techId) {
		super();
		this.osName = osName;
		this.techId = techId;
	}
	
	public String getOsName() {
		return osName;
	}
	
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getTechId() {
		return techId;
	}
	
	public void setTechId(String techId) {
		this.techId = techId;
	}
	
	@Override
	public String toString() {
		return "DownloadPropertyInfo [osName=" + osName + ", techId=" + techId
				+ "]";
	}
}
