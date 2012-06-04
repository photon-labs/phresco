/*
 * ###
 * Phresco Service Implemenation
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
/**
 *
 */
package com.photon.phresco.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Element;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.DocumentTypes;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Documents;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Libraries;
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
import com.photon.phresco.service.api.DBManager;
import com.photon.phresco.service.db.DBService;
import com.photon.phresco.service.jaxb.Document;
import com.photon.phresco.service.jaxb.Library;

public class DBManagerImpl implements DBManager {

	private DBService dbService;

	public DBManagerImpl() {
		dbService = findBean("phresco-db-service");
	}

	public static DBService findBean(String beanName) {
		System.out.println("Finding Bean "  + beanName);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring-hibernate.xml");
		System.out.println("Found Bean");
		return (DBService) context.getBean(beanName);
	}

	public List<Customer> findCustomers() throws PhrescoException {
		return dbService.findCustomers();
	}

	public List<ApplicationType> getApplicationTypes() throws PhrescoException {
		return dbService.getApplicationTypes();
	}

	public void addApplicationTypes(List<ApplicationType> applicaitonTypes) throws PhrescoException {
		dbService.addApplicationTypes(applicaitonTypes);
	}

	public List<Technology> getTechnologies(String appTypeId)
			throws PhrescoException {
		return dbService.getTechnology();
	}

	public List<SettingsInfo> getSettings() throws PhrescoException {
		return dbService.getSettingsInfo();
	}

	public List<SettingsInfo> getSettings(String techId)
			throws PhrescoException {
		return null;
	}

	public List<AdminConfigInfo> getAdminConfigInfo() throws PhrescoException {
		return dbService.getAdminConfigInfo();
	}

	public List<VideoInfo> getVideoInfos() throws PhrescoException {
		return dbService.getVideoInfo();
	}

	public void addVideos(List<VideoInfo> videoInfo) throws PhrescoException {
		dbService.addVideoInfo(videoInfo);
	}

	public List<DownloadInfo> getDownloads() throws PhrescoException {
		return dbService.getDownloadInfo();
	}

	public List<DownloadInfo> getDownloads(String techId, String platform)
			throws PhrescoException {
		return dbService.getDownloadInfo(techId, platform);
	}

	public List<ProjectInfo> getPilots() throws PhrescoException {

		return null;
	}

	public void addSettingsInfo(List<SettingsInfo> settingsInfo)
			throws PhrescoException {
		dbService.addSettingsInfo(settingsInfo);

	}

	public void addAdminConfig(List<AdminConfigInfo> adminConfigInfo) throws PhrescoException {
		dbService.addAdminConfigInfo(adminConfigInfo);
	}

	public void addDownloadInfo(List<DownloadInfo> downloadInfo)
			throws PhrescoException {
		dbService.addDownloadInfo(downloadInfo);

	}

	public void addModuleGroup(List<ModuleGroup> moduleGroup)
			throws PhrescoException {
		dbService.addModuleGroup(moduleGroup);
	}

	public List<ModuleGroup> getModuleGroup() throws PhrescoException {
		return dbService.getModuleGroup();
	}

	public List<ProjectInfo> getPilots(String arg0) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addProjectInfo(ProjectInfo projectInfo)
			throws PhrescoException {
		dbService.addProjectinfo(projectInfo);
	}

	public List<Technology> getTechnologies() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.getTechnology();
	}

	public List<Module> getModule() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.getModule();
	}

	public void addModules(List<com.photon.phresco.model.Modules> modules) throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addModules (modules);
	}

	public void addTechnologies(List<Technology> technologies)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addTechnologies (technologies);

	}

	public List<Documentation> getDocumentations() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.getDocumentations ();
	}

	public void addDocumentations(List<Documentation> documentations)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addDocumentations(documentations);

	}

	public List<ProjectInfo> getProjectInfo() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.getProjectInfo ();
	}
	public List<ModuleGroup> getModules(String techId) throws PhrescoException {
		return null;
	}

	public List<ModuleGroup> getJSLibraries(String techId) throws PhrescoException {
		return null;
	}

	public List<ModuleGroup> getFrameworks(String techId) throws PhrescoException {
		return null;
	}

	public List<DocumentTypes> getDocumentTypes(String docType)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.getDocumentTypes (docType);
	}

	public DocumentTypes []getDocumentType(String docType)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
		//return dbService.getDocumentType (docType);
	}

	public List<DocumentTypes> getDocumentType() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.getDocumentType ();
	}

	public void addDocument(List<com.photon.phresco.model.Document> documentsList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addDocument(documentsList);

	}

	public void addDocuments(List<com.photon.phresco.model.Documents> documentsList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addDocuments(documentsList);

	}

	public void addModule(List<com.photon.phresco.model.Module> moduleDataList) throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addModule(moduleDataList);
	}

	public void addLibrary(List<com.photon.phresco.model.Library> libraryList) throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addLibrary (libraryList);
	}

	public void addLibraries(List<com.photon.phresco.model.Libraries> librariesList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addLibraries (librariesList);
	}

	public void addSettings(List<com.photon.phresco.model.Settings> settingsDataList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addSettings(settingsDataList);
	}

	public void addPropertyTemplate(List<PropertyTemplate> propTemplateDataList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addPropertyTemplate (propTemplateDataList);
	}

	public void addSettingsApplies(List<SettingsApplies> settingsAppliesList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addSettingsApplies(settingsAppliesList);
	}

	public void addSettingsPropPossibleValues(List<SettingsPropPossibleValues> possibleValueList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addSettingsPropPossibleValues(possibleValueList);
	}

	public void addPilotTechModules(List<PilotTechModules> pilotTechModuleList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addPilotTechModules (pilotTechModuleList);
	}

	public void addPilotTechModuleVersionsList(List<PilotTechModuleVersions> pilotTechModuleVersionsList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addPilotTechModuleVersionsList (pilotTechModuleVersionsList);
	}

	public void addPilotTechnology(List<PilotTechnology> pilotTechnologyList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addPilotTechnology(pilotTechnologyList);
	}

	public void addProjectInfo(List<ProjectInfo> pilotProjectInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addProjectInfo (pilotProjectInfo);
	}

	public void addLibraryDoc(List<LibraryDoc> libDocList)throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addLibraryDoc(libDocList);
	}

	public void addLibraryDocs(List<LibrayDocs> libDocList)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbService.addLibraryDocs (libDocList);
	}

	public List<Element> save(List<Element> elements) throws PhrescoException {
		// TODO Auto-generated method stub
		return dbService.save(elements);
	}

}



