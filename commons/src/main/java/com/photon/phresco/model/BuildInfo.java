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

import java.util.List;
import java.util.Map;

/**
 * A Simple Bean holds information about the build. As of now stored as JSON in
 * the client file system.
 */
public class BuildInfo {
	/**
	 * Build number
	 */
	private int buildNo;
	
	/**
	 * Build Time stamp
	 */
	private String timeStamp;
	
	/**
	 * Build status (FAILURE/SUCCESS)
	 */
	private String buildStatus;
	
	/**
	 * Name of the build (usually the deployable file)
	 */
	private String buildName;
	
	/**
	 * Deploy location
	 */
	private String deployLocation;
	
	/**
	 * Server configuration name
	 */
	private String serverName;
	
	/**
	 * Database configuration name
	 */
	private String databaseName;
	
	/**
	 * import sql file
	 */
	private String importsql;
	
	/**
	 * web service configuration name
	 */
	private String webServiceName;
	
	/**
	 * 
	 */
	private String context;
	
	private List<String> environments;

	/**
	 * Deliverables (file name)
	 */
	private String deliverables;

	/**
	 * ModuleName
	 */
	private String moduleName;
		
	/**
	 * MapVariable
	 */
	private Map<String, Boolean> options;
		
	/**
	 * 
	 */
	public BuildInfo() {

	}

	/**
	 * @return
	 */
	public int getBuildNo() {
		return buildNo;
	}

	/**
	 * @param buildNo
	 */
	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}

	/**
	 * @return
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return
	 */
	public String getBuildStatus() {
		return buildStatus;
	}

	/**
	 * @param buildStatus
	 */
	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	/**
	 * @return the buildName
	 */
	public String getBuildName() {
		return buildName;
	}

	/**
	 * @param buildName the buildName to set
	 */
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	/**
	 * @return the deployLocation
	 */
	public String getDeployLocation() {
		return deployLocation;
	}

	/**
	 * @param deployLocation the deployLocation to set
	 */
	public void setDeployLocation(String deployLocation) {
		this.deployLocation = deployLocation;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return
	 */
	public String getWebServiceName() {
		return webServiceName;
	}

	/**
	 * @param webServiceName
	 */
	public void setWebServiceName(String webServiceName) {
		this.webServiceName = webServiceName;
	}

	/**
	 * @return the deliverables
	 */
	public String getDeliverables() {
		return deliverables;
	}

	/**
	 * @param deliverables
	 *            the deliverables to set
	 */
	public void setDeliverables(String deliverables) {
		this.deliverables = deliverables;
	}

	public void setImportsql(String importsql) {
		this.importsql = importsql;
	}

	public String getImportsql() {
		return importsql;
	}

	public void setEnvironments(List<String> environments) {
		this.environments = environments;
	}

	public List<String> getEnvironments() {
		return environments;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Map<String, Boolean> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Boolean> options) {
		this.options = options;
	}
	
	
}
