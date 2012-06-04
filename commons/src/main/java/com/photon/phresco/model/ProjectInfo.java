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

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class ProjectInfo implements Cloneable ,Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int pilotid;
	private String id;
	private String name;
	private String code;
	private String version;
	private String description;
	private Technology technology;
	private String application;
	private String[] pilotProjectUrls;
	private String techID;
	private String contentURLId;
	private String pilotProjectName;
	private String projectCode;
	private String groupId;
	private String artifactId;

	/**
	 * @return the pilotid
	 */
	public int getPilotid() {
		return pilotid;
	}

	/**
	 * @param pilotid the pilotid to set
	 */
	public void setPilotid(int pilotid) {
		this.pilotid = pilotid;
	}

	/**
	 * @return the techID
	 */
	public String getTechID() {
		return techID;
	}

	/**
	 * @param techID the techID to set
	 */
	public void setTechID(String techID) {
		this.techID = techID;
	}

	/**
	 * @return the contentURLId
	 */
	public String getContentURLId() {
		return contentURLId;
	}

	/**
	 * @param contentURLId the contentURLId to set
	 */
	public void setContentURLId(String contentURLId) {
		this.contentURLId = contentURLId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProjectInfo [id=" + id + ", name=" + name + ", projectCode=" + projectCode + ", code=" + code
				+ ", version=" + version + ", description=" + description + ", groupId=" + groupId + ", artifactId=" + artifactId
				+ ", technology=" + technology + ", application=" + application
				+ ", pilotProjectUrls=" + Arrays.toString(pilotProjectUrls)
				+ ", techID=" + techID + ", contentURLId=" + contentURLId + "]";
	}

	/**
	 * @return the pilotProjectUrls
	 */
	public String[] getPilotProjectUrls() {
		return pilotProjectUrls;
	}

	/**
	 * @param pilotProjectUrls the pilotProjectUrls to set
	 */
	public void setPilotProjectUrls(String[] pilotProjectUrls) {
		this.pilotProjectUrls = pilotProjectUrls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public ProjectInfo clone(){
		ProjectInfo infos = new ProjectInfo();
		infos.setApplication(application);
		infos.setCode(code);
		infos.setProjectCode(projectCode);
		infos.setContentURLId(contentURLId);
		infos.setDescription(description);
		infos.setId(id);
		infos.setName(name);
		infos.setPilotProjectUrls(pilotProjectUrls);
		infos.setTechID(techID);
		infos.setVersion(version);
		infos.setGroupId(groupId);
		infos.setArtifactId(artifactId);
		infos.setPilotProjectName(pilotProjectName);
		infos.setTechnology(technology.clone());
		return infos;
	}

	public String getPilotProjectName() {
		return pilotProjectName;
	}

	public void setPilotProjectName(String pilotProjectName) {
		this.pilotProjectName = pilotProjectName;
	}

}