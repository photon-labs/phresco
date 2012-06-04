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
import com.photon.phresco.model.DocumentTypes;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.LibraryDoc;
import com.photon.phresco.model.LibrayDocs;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.PilotTechModuleVersions;
import com.photon.phresco.model.PilotTechModules;
import com.photon.phresco.model.PilotTechnology;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.Settings;
import com.photon.phresco.model.SettingsApplies;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.SettingsPropPossibleValues;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.jaxb.Document;
import com.photon.phresco.service.jaxb.Library;
import com.photon.phresco.model.Documents;
import com.photon.phresco.model.Module;

public interface PhrescoDataManager {

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
	 * Returns the list of  Module Groups
	 * @return
	 * @throws PhrescoException
	 */
	List<ModuleGroup> getModuleGroup() throws PhrescoException;
	/**
	 * Adds the Module Groups
	 * @param module group
	 * @throws PhrescoException
	 */
	void addModuleGroup(List<ModuleGroup> moduleGroup) throws PhrescoException;

	/**
	 * Returns the list of modules for a technology
	 * @param techId
	 * @return
	 * @throws PhrescoException
	 */
	List<ModuleGroup> getModules(String techId) throws PhrescoException;

	/**
	 * Returns the list of JSLibraries for a technology
	 * @param techId
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
	 * Adds the Settings Info
	 * @param SettingsInfo
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
	 * Adds the AdminConfigInfo Info
	 * @param AdminConfigInfo
	 * @throws PhrescoException
	 */

	void addAdminConfigInfo(List<AdminConfigInfo> adminConfigInfo) throws PhrescoException;

	/**
	 * Returns the videos which are displayed in the Homepage
	 * @return
	 * @throws PhrescoException
	 */
	List<VideoInfo> getVideoInfos() throws PhrescoException;

	/**
	 * Adds the VideoInfo Info
	 * @param VideoInfo
	 * @throws PhrescoException
	 */

	void addVideos(List<VideoInfo> videoInfo) throws PhrescoException;

	/**
	 * Returns all the downloads information
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
	 * @param DownloadInfo
	 * @throws PhrescoException
	 */
	void addDownloadInfo(List<DownloadInfo> downloadInfo) throws PhrescoException;

	/**
	 * Returns the pilot project information for a technology
	 * @param techId
	 * @return
	 * @throws PhrescoException
	 */
	List<ProjectInfo> getPilots(String techId) throws PhrescoException;
	/**
	 * Adds the ProjectInfo
	 * @param ProjectInfo
	 * @throws PhrescoException
	 */
	void addProjectInfo(ProjectInfo projectInfo) throws PhrescoException;
	/**
	 * Returns all the Technology information
	 * @return
	 * @throws PhrescoException
	 */
	List<Technology> getTechnologies()throws PhrescoException;
	/**
	 * Returns all the Module information
	 * @return
	 * @throws PhrescoException
	 */
	List<Module> getModule()throws PhrescoException;
	/**
	 * Adds the Module
	 * @param Module
	 * @throws PhrescoException
	 */

	void addTechnologies(List<Technology> technologies) throws PhrescoException;
	/**
	 * Returns all the Documentation information
	 * @return
	 * @throws PhrescoException
	 */
	List<Documentation> getDocumentations()throws PhrescoException;
	/**
	 * Adds the Documentation
	 * @param Documentation
	 * @throws PhrescoException
	 */
	void addDocumentations(List<Documentation> documentations) throws PhrescoException;
	/**
	 * Returns all the ProjectInfo information
	 * @return
	 * @throws PhrescoException
	 */
	List<ProjectInfo> getProjectInfo() throws PhrescoException;

	List<DocumentTypes> getDocumentTypes(String docType) throws PhrescoException;

	DocumentTypes [] getDocumentType(String name)throws PhrescoException;

	List<DocumentTypes> getDocumentTypes()throws PhrescoException;

	void addDocument(List<com.photon.phresco.model.Document> documentsList)throws PhrescoException;

	void addDocuments(List<com.photon.phresco.model.Documents> documentsList) throws PhrescoException;

	void addModule(List<com.photon.phresco.model.Module> moduleDataList)throws PhrescoException;

	void addModules(List<com.photon.phresco.model.Modules> modulesDataList)throws PhrescoException;

	void addLibrary(List<com.photon.phresco.model.Library> libraryList) throws PhrescoException;

	void addLibraries(List<com.photon.phresco.model.Libraries> librariesList)throws PhrescoException;

	//SETTINGS OBJECT

	void addSettings(List<com.photon.phresco.model.Settings> settingsDataList)throws PhrescoException;

	void addPropertyTemplate(List<PropertyTemplate> propTemplateDataList)throws PhrescoException;

	void addSettingsApplies(List<SettingsApplies> settingsAppliesList) throws PhrescoException;

	void addSettingsPropPossibleValues(List<SettingsPropPossibleValues> possibleValueList)throws PhrescoException;

	void addPilotTechModules(List<PilotTechModules> pilotTechModuleList )throws PhrescoException;

	void addPilotTechModuleVersionsList(List<PilotTechModuleVersions> pilotTechModuleVersionsList) throws PhrescoException;

	void addPilotTechnology(List<PilotTechnology> pilotTechnologyList)throws PhrescoException;

	void addProjectInfo(List<ProjectInfo> pilotProjectInfo) throws PhrescoException;

	void addLibraryDoc(List<LibraryDoc> libDocList) throws PhrescoException;

	void addLibraryDocs(List<LibrayDocs> libDocList) throws PhrescoException;



}
