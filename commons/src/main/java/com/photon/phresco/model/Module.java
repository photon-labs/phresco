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
import java.util.List;

public class Module implements Serializable {

	 private String id;
	 private String contentType;
	 private String contentURL;
	 private List<String> technolgoyRef;
	 private List<ModuleGroup> dependentModules;
	 private int documentsid;
	 private String name;
	 private String version;
	 private Boolean core;
	 private Boolean required;
	 private String groupId;
	 private String artifactId;
	 private String url;
	 private int modulesid;

	 /**
	 * @return the documentsid
	 */
	public int getDocumentsid() {
		return documentsid;
	}
	/**
	 * @param documentsid the documentsid to set
	 */
	public void setDocumentsid(int documentsid) {
		this.documentsid = documentsid;
	}
	/**
	 * @return the moduleid
	 */
	public int getModulesid() {
		return modulesid;
	}
	/**
	 * @param moduleid the moduleid to set
	 */
	public void setModulesid(int moduleid) {
		this.modulesid = moduleid;
	}
	/**
	 * @return the documentsId
	 */
	public int getDocumentsId() {
		return documentsid;
	}
	/**
	 * @param documentsId the documentsId to set
	 * @param documentsid
	 */
	public void setDocumentsId(int documentsid) {
		this.documentsid = documentsid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the contentURL
	 */
	public String getContentURL() {
		return contentURL;
	}
	/**
	 * @param contentURL the contentURL to set
	 */
	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}
	/**
	 * @return the technolgoyRef
	 */
	public List<String> getTechnolgoyRef() {
		return technolgoyRef;
	}
	/**
	 * @param technolgoyRef the technolgoyRef to set
	 */
	public void setTechnolgoyRef(List<String> technolgoyRef) {
		this.technolgoyRef = technolgoyRef;
	}
	/**
	 * @return the dependentModules
	 */
	public List<ModuleGroup> getDependentModules() {
		return dependentModules;
	}
	/**
	 * @param dependentModules the dependentModules to set
	 */
	public void setDependentModules(List<ModuleGroup> dependentModules) {
		this.dependentModules = dependentModules;
	}
	/**
	 * @return the documents
	 */
	public int getDocuments() {
		return documentsid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the core
	 */
	public Boolean getCore() {
		return core;
	}
	/**
	 * @param core the core to set
	 */
	public void setCore(Boolean core) {
		this.core = core;
	}
	/**
	 * @return the required
	 */
	public Boolean getRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(Boolean required) {
		this.required = required;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return artifactId;
	}
	/**
	 * @param artifactId the artifactId to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public Module(String id, String contentType, String contentURL,
			List<String> technolgoyRef, List dependentModules,
			String documentsId, String name, String version, Boolean core,
			Boolean required, String groupId, String artifactId) {
		super();
		this.id = id;
		this.contentType = contentType;
		this.contentURL = contentURL;
		this.technolgoyRef = technolgoyRef;
//		this.dependentModules = dependentModules;
		this.documentsid = documentsid;
		this.name = name;
		this.version = version;
		this.core = core;
		this.required = required;
		this.groupId = groupId;
		this.artifactId = artifactId;
	}

//	public Module(String id, String contentType, String contentURL,
//			List<String> technolgoyRef, List<ModuleGroup> dependentModules,
//			String documentsId, String name, String version, Boolean core,
//			Boolean required, String groupId, String artifactId) {
//		super();
//		this.id = id;
//		this.contentType = contentType;
//		this.contentURL = contentURL;
//		this.technolgoyRef = technolgoyRef;
//		this.dependentModules = dependentModules;
//		this.documentsId = documentsId;
//		this.name = name;
//		this.version = version;
//		this.core = core;
//		this.required = required;
//		this.groupId = groupId;
//		this.artifactId = artifactId;
//	}

	public Module() {
		// TODO Auto-generated constructor stub
	}
	public void Modules () {

	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Modules [id=" + id + ", contentType=" + contentType
				+ ", contentURL=" + contentURL + ", technolgoyRef="
				+ technolgoyRef + ", dependentModules=" + dependentModules
				+ ", documents=" + documentsid + ", name=" + name + ", version="
				+ version + ", core=" + core + ", required=" + required
				+ ", groupId=" + groupId + ", artifactId=" + artifactId + "]";
	}


}
