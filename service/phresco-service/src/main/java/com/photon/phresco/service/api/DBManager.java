/*
 * ###
 * Phresco Service
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
package com.photon.phresco.service.api;

import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Element;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.model.DocumentTypes;

public interface DBManager {

	/**
	 * saves the list of elements to the database
	 * @param elements
	 * @throws PhrescoException
	 */
	List<Element> save(List<Element> elements) throws PhrescoException;

	/**
	 * Returns the list of customers
	 * @return
	 * @throws PhrescoException
	 */
	List<Customer> findCustomers() throws PhrescoException;

	/**
	 * Returns the list of applications types and technologies
	 * @return
	 * @throws PhrescoException
	 */
	List<ApplicationType> getApplicationTypes() throws PhrescoException;

	/**
	 * Adds the application types
	 * @param applicationTypes
	 * @throws PhrescoException
	 */
	void addApplicationTypes(List<ApplicationType> applicationTypes) throws PhrescoException;

	/**
	 * Returns the list of technologies for an application type.
	 * @param appTypeId
	 * @return
	 * @throws PhrescoException
	 */
	List<Technology> getTechnologies(String appTypeId) throws PhrescoException;

	/**
	 * Returns the list of modules for a technology
	 * @param techId
	 * @return
	 * @throws PhrescoException
	 */
	List<ModuleGroup> getModules(String techId) throws PhrescoException;
	/**
	 * Returns the list of Module Group.
	 * @param appTypeId
	 * @return
	 * @throws PhrescoException
	 */
	List<ModuleGroup> getModuleGroup() throws PhrescoException;
	/**
	 * Adds the ModuleGroup
	 * @param applicationTypes
	 * @throws PhrescoException
	 */
	void addModuleGroup(List<ModuleGroup> moduleGroup) throws PhrescoException;

	/**
	 * Returns the list of JSLibraries for a technology	 *
	 * @return
	 * @throws PhrescoException
	 */
	List<ModuleGroup> getJSLibraries(String techId) throws PhrescoException;

	/**
	 * Returns the list of Frameworks for a technology
	 * @param techId
	 * @return
	 * @throws PhrescoException
	 */
	List<ModuleGroup> getFrameworks(String techId) throws PhrescoException;

	/**
	 * Returns all the setting templates
	 * @return
	 * @throws PhrescoException
	 */
	List<SettingsInfo> getSettings() throws PhrescoException;
	/**
	 * Adds the SettingsInfo
	 * @param applicationTypes
	 * @throws PhrescoException
	 */
	void addSettingsInfo(List<SettingsInfo> settingsInfo) throws PhrescoException;

	/**
	 * Returns the list of setting templates applicable for the technology
	 * @param techId
	 * @return
	 * @throws PhrescoException
	 */

	List<SettingsInfo> getSettings(String techId) throws PhrescoException;

	/**
	 * Returns the admin configurations defined in the system
	 * @return
	 * @throws PhrescoException
	 */

	List<AdminConfigInfo> getAdminConfigInfo() throws PhrescoException;
	/**
	 * Adds the AdminConfigInfo
	 * @param applicationTypes
	 * @throws PhrescoException
	 */

	void addAdminConfig(List<AdminConfigInfo> adminConfigInfo) throws PhrescoException;

	/**
	 * Returns the videos which are displayed in the Home page
	 * @return
	 * @throws PhrescoException
	 */


	List<VideoInfo> getVideoInfos() throws PhrescoException;

	/**
	 * Returns all the downloads information
	 * @return
	 * @throws PhrescoException
	 */

	void addVideos(List<VideoInfo> videoInfo) throws PhrescoException;
	/**
	 * Returns DownLoadInfo
	 * @return
	 * @throws PhrescoException
	 */

	List<DownloadInfo> getDownloads() throws PhrescoException;

	/**
	 * Returns the list of downloads for a technology on a platform
	 * @param techId
	 * @param platform
	 * @return
	 * @throws PhrescoException
	 */

	List<DownloadInfo> getDownloads(String techId, String platform) throws PhrescoException;
	/**
	 * Adds the DownloadInfo
	 * @param applicationTypes
	 * @throws PhrescoException
	 */

	void addDownloadInfo(List<DownloadInfo> downloadInfo) throws PhrescoException;

	/**
	 * Returns the pilot project information for a technology
	 * @param techId
	 * @return
	 * @throws PhrescoException
	 */

	List<ProjectInfo> getPilots() throws PhrescoException;
	/**
	 * Adds the ProjectInfo
	 * @param applicationTypes
	 * @throws PhrescoException
	 */

	void addProjectInfo(ProjectInfo projectInfo) throws PhrescoException;

	/**
	 * Returns Technologies
	 * @return
	 * @throws PhrescoException
	 */

	List<Technology> getTechnologies() throws PhrescoException;

	/**
	 * Returns Module
	 * @return
	 * @throws PhrescoException
	 */

	List<Module> getModule() throws PhrescoException;
	/**
	 * Adds the Modules
	 * @param applicationTypes
	 * @throws PhrescoException
	 */

	void addModules(List<com.photon.phresco.service.model.Modules> modules) throws PhrescoException;
	/**
	 * Adds the Technologies
	 * @param applicationTypes
	 * @throws PhrescoException
	 */

	void addTechnologies(List<Technology> technologies) throws PhrescoException;
	/**
	 * Returns Documentation
	 * @return
	 * @throws PhrescoException
	 */

	List<Documentation> getDocumentations () throws PhrescoException;
	/**
	 * Adds the Documentations
	 * @param applicationTypes
	 * @throws PhrescoException
	 */

	void addDocumentations (List<Documentation> documentations) throws PhrescoException;
	/**
	 * Returns ProjectInfo
	 * @return
	 * @throws PhrescoException
	 */
	List<ProjectInfo> getProjectInfo()throws PhrescoException;

	List<DocumentTypes> getDocumentTypes(String docType) throws PhrescoException;

	DocumentTypes [] getDocumentType(String docType)throws PhrescoException;

	List<DocumentTypes> getDocumentType()throws PhrescoException;

	void addDocument(List<com.photon.phresco.service.model.Document> documentsList) throws PhrescoException;

	void addDocuments(List<com.photon.phresco.service.model.Documents> documentsList)throws PhrescoException;

	void addModule(List<Module> moduleDataList) throws PhrescoException;

}
