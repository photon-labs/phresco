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
package com.photon.phresco.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.api.DBManager;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.service.model.DocumentTypes;
import com.photon.phresco.service.model.ServerConstants;

public class PhrescoDataManagerImpl implements PhrescoDataManager, ServerConstants {

	private RepositoryManager repoManager;
	private DBManager dbManager;

	public PhrescoDataManagerImpl() throws PhrescoException {
		initialize();
	}

	private void initialize() throws PhrescoException {
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("files/config.properties");
		Properties props = new Properties();
		try {
			props.load(inStream);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}

		String url = getRepoURL(props);
		repoManager = new RepositoryManagerImpl(url, props.getProperty(CONFIG_KEY_REPOSITORY_USERNAME),
				props.getProperty(CONFIG_KEY_REPOSITORY_PASSWORD));

		dbManager = new DBManagerImpl();
	}

	private String getRepoURL(Properties props) {
		StringBuffer buff = new StringBuffer(512);
		String protocol = props.getProperty(CONFIG_KEY_REPOSITORY_PROTOCOL);
		String host = props.getProperty(CONFIG_KEY_REPOSITORY_HOST);
		String port = props.getProperty(CONFIG_KEY_REPOSITORY_PORT);
		String path = props.getProperty(CONFIG_KEY_REPOSITORY_PATH);
		buff.append(protocol);
		buff.append("://");
		buff.append(host);
		buff.append(":");
		buff.append(port);
		buff.append("/");
		buff.append(path);

		return buff.toString();
	}

	public List<Customer> getCustomers() throws PhrescoException {
//		return dbManager.findCustomers();
		return null;
	}

	public void save(List<Customer> customers) throws PhrescoException {
//		dbManager.save(customers);
	}
	
	public List<ApplicationType> getApplicationTypes() throws PhrescoException {
		return dbManager.getApplicationTypes();
		// TODO Auto-generated method stub
	}

	public List<Module> getModule()throws PhrescoException {
		return dbManager.getModule();
		// TODO Auto-generated method stub
	}

	public void addApplicationTypes(List<ApplicationType> applicationTypes)
			throws PhrescoException {
		dbManager.addApplicationTypes(applicationTypes);
		// TODO Auto-generated method stub
	}

	public List<Technology> getTechnologies(String appTypeId)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getTechnologies(appTypeId);
	}

	public List<ModuleGroup> getModuleGroup() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getModuleGroup();
	}

	public void addModuleGroup(List<ModuleGroup> moduleGroup)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addModuleGroup(moduleGroup);
	}

	public List<SettingsInfo> getSettings() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getSettings();
	}

	public void addSettingsInfo(List<SettingsInfo> settingsInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addSettingsInfo(settingsInfo);
	}

	public List<SettingsInfo> getSettings(String techId)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getSettings();
	}

	public List<AdminConfigInfo> getAdminConfigInfo() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getAdminConfigInfo();
	}

	public void addAdminConfigInfo(List<AdminConfigInfo> adminConfigInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addAdminConfig(adminConfigInfo);
	}

	public List<VideoInfo> getVideoInfos() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getVideoInfos();
	}

	public void addVideos(List<VideoInfo> videoInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addVideos(videoInfo);
	}

	public List<DownloadInfo> getDownloads() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getDownloads();
	}

	public void addDownloadInfo(List<DownloadInfo> downloadInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addDownloadInfo(downloadInfo);

	}

	public void addProjectInfo(ProjectInfo projectInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addProjectInfo(projectInfo);
	}

	public List<Technology> getTechnologies() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getTechnologies();
	}

	public void addModules(List<com.photon.phresco.service.model.Modules> modules) throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addModules (modules);
	}

	public void addTechnologies(List<Technology> technologies)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addTechnologies (technologies);
	}

	public List<Documentation> getDocumentations() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getDocumentations ();
	}

	public void addDocumentations(List<Documentation> documentations)
			throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addDocumentations(documentations);
	}
	public List<ProjectInfo> getProjectInfo() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getProjectInfo ();
	}
	public List<ModuleGroup> getJSLibraries(String techId)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ModuleGroup> getFrameworks(String techId)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ModuleGroup> getModules(String techId) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DownloadInfo> getDownloads(String techId, String platform)
			throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ProjectInfo> getPilots(String techId) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DocumentTypes> getDocumentTypes(String docType) throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getDocumentTypes (docType);
	}

	public DocumentTypes [] getDocumentType(String docType) throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getDocumentType (docType);
	}

	public List<DocumentTypes> getDocumentTypes() throws PhrescoException {
		// TODO Auto-generated method stub
		return dbManager.getDocumentType ();
	}

	public void addDocument(List<com.photon.phresco.service.model.Document> documentList)throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addDocument(documentList);

	}

	public void addDocuments(List<com.photon.phresco.service.model.Documents> documentsList) throws PhrescoException{
		// TODO Auto-generated method stub
		dbManager.addDocuments(documentsList);

	}

	public void addModule(List<com.photon.phresco.model.Module> moduleDataList) throws PhrescoException {
		// TODO Auto-generated method stub
		dbManager.addModule(moduleDataList);
	}

}
