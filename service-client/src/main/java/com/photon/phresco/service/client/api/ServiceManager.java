/*
 * ###
 * Phresco Service Client
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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
package com.photon.phresco.service.client.api;

import java.util.List;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Interface for making service calls to Phresco Framework
 */
public interface ServiceManager {
	
	<E> RestClient<E> getRestClient(String contextPath) throws PhrescoException;
	
	User getUserInfo() throws PhrescoException;
	
	List<VideoInfo> getVideoInfos() throws PhrescoException;
	
	ClientResponse createApplicationTypes(List<ApplicationType> appTypes, String customerId) throws PhrescoException;
	
	void updateApplicationTypes(ApplicationType appType, String appTypeId, String customerId) throws PhrescoException;
	
	ClientResponse deleteApplicationType(String appTypeId, String customerId) throws PhrescoException;
   
	List<Technology> getArcheTypes(String customerId) throws PhrescoException;
	
	Technology getArcheType(String archeTypeId) throws PhrescoException;
	
	ClientResponse createArcheTypes(List<Technology> archeTypes, String customerId) throws PhrescoException;
	
	void updateArcheTypes(Technology technology, String archeTypeId) throws PhrescoException;
	
	ClientResponse deleteArcheType(String archeTypeId) throws PhrescoException;
	
	List<ApplicationType> getApplicationTypes(String customerId) throws PhrescoException;
	
	ApplicationType getApplicationType(String appTypeId) throws PhrescoException;
	
	List<Server> getServers(String techId, String customerId) throws PhrescoException;
	
	List<Database> getDatabases(String techId, String customerId) throws PhrescoException;
	
	List<WebService> getWebServices(String techId, String customerId) throws PhrescoException;
	
	List<ModuleGroup> getModules(String techId, String customerId) throws PhrescoException;
	
	ModuleGroup getModule(String moduleId) throws PhrescoException;
	
	List<ModuleGroup> getJSLibs(String techId, String customerId) throws PhrescoException;
	
	ClientResponse createModules(List<ModuleGroup> modules) throws PhrescoException;
	
	void updateModuleGroups(ModuleGroup moduleGroup, String moduleId) throws PhrescoException;
	
	ClientResponse deleteModule(String moduleId) throws PhrescoException;
	
	List<Customer> getCustomers() throws PhrescoException;
	
	Customer getCustomer(String customerId) throws PhrescoException;
	
	ClientResponse createCustomers(List<Customer> customers) throws PhrescoException;
	
	void updateCustomer(Customer customer, String customerId) throws PhrescoException;
	
	ClientResponse deleteCustomer(String customerId) throws PhrescoException;
	
	List<SettingsTemplate> getSettings() throws PhrescoException;
	
	SettingsTemplate getSettings(String settingsId) throws PhrescoException;
	
	ClientResponse createSettings(List<SettingsTemplate> settings) throws PhrescoException;
	
	List<ProjectInfo> getPilotProject(String customerId)throws PhrescoException;
	
	List<ProjectInfo> getPilots(String techId, String customerId) throws PhrescoException;
	
	ProjectInfo getPilotPro(String projectId) throws PhrescoException;
	
	ClientResponse createPilotProject(List<ProjectInfo> proInfo) throws PhrescoException;
	
	void updatePilotProject(ProjectInfo projectInfo, String projectId) throws PhrescoException;
	
	ClientResponse deletePilotProject(String projectId) throws PhrescoException;
	
	List<Role> getRoles() throws PhrescoException;
	
	Role getRole(String roleId) throws PhrescoException;

	ClientResponse createRoles(List<Role> role) throws PhrescoException;
	
	List<DownloadInfo> getDownloads(String customerId) throws PhrescoException;
	
	DownloadInfo getDownload(String id) throws PhrescoException;
	
	ClientResponse createDownload(List<DownloadInfo> downloadInfo) throws PhrescoException;
	
	void updateDownload(DownloadInfo downloadInfo, String id) throws PhrescoException;
	
	ClientResponse deleteDownloadInfo(String id) throws PhrescoException;
}