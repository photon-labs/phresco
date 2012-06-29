/*
 * ###
 * Phresco Commons
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
