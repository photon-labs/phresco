/*
 * ###
 * Phresco Framework
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
package com.photon.phresco.framework.api;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.DownloadPropertyInfo;
import com.photon.phresco.model.LogInfo;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.util.Credentials;
import com.phresco.pom.site.Reports;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Interface for making service calls to Phresco Framework
 */
public interface ServiceManager {

    /**
     * Returns all application types
     * @return
     * @throws PhrescoException
     */
    List<ApplicationType> getApplicationTypes() throws PhrescoException;

    /**
     * Returns Application type object for the given name
     * @param name of the application type [Web, Mobile or Webservice]
     * @return Application Type
     * @throws PhrescoException
     */
    ApplicationType getApplicationType(String name) throws PhrescoException;

    /**
     * Returns all technologies
     * @return
     * @throws PhrescoException
     */
    Map<String, Technology> getAllTechnologies() throws PhrescoException;

    /**
     * Returns all the settings template available in the service repository
     * @return
     * @throws PhrescoException
     */
    List<SettingsTemplate> getSettingsTemplates() throws PhrescoException;

    /**
     * Returns environment name
     * @return
     * @throws PhrescoException
     */
    
    List<Environment> getEnvInfoFromService() throws PhrescoException;
    
    /**
     * Returns the list of Videos
     * @return
     * @throws PhrescoException
     */
    List<VideoInfo> getVideoInfos() throws PhrescoException;
    
    /**
     * Returns the list of downloads
     * @return
     * @throws PhrescoException
     */
    List<DownloadInfo> getDownloadsFromService(DownloadPropertyInfo downloadPropertyInfo) throws PhrescoException;

    /**
     * Triggers the create project to the server and returns the client response
     * @param info
     * @return
     * @throws PhrescoException
     */
    ClientResponse createProject(ProjectInfo info) throws PhrescoException;
    
    /**
     * Triggers the update project to the server and returns the client response
     * @param info
     * @return
     * @throws PhrescoException
     */
    ClientResponse updateProject(ProjectInfo info) throws PhrescoException;
  
    /**
     * Triggers the update project to the server and returns the client response
     * @param info
     * @return
     * @throws PhrescoException
     */
    ClientResponse updateDocumentProject(ProjectInfo info) throws PhrescoException;
  
    /**
     * authenticates the user credentials and returns the user information.
     * @param credentials
     * @return
     * @throws PhrescoException
     */
    UserInfo doLogin(Credentials credentials) throws PhrescoException;
    
    /**
     * Get the admin config information from the server and return as Map.
     * @return
     * @throws PhrescoException
     */
    List<AdminConfigInfo> getAdminConfig() throws PhrescoException;
    
    /**
     * Get the job configuration xml file from the server and return as InputStream.
     * @return
     * @throws PhrescoException
     */
    String getCiConfigPath() throws PhrescoException;
    
    String getCiSvnPath() throws PhrescoException;

	ClientResponse sendReport(LogInfo loginfo) throws PhrescoException ;
	
    /**
     * Get the creadential xml file from the server and return as InputStream.
     * @return
     * @throws PhrescoException
     */
	InputStream getCredentialXml() throws PhrescoException;
	
    /**
     * Get the jdk home xml file from the server and return as InputStream.
     * @return
     * @throws PhrescoException
     */
	InputStream getJdkHomeXml() throws PhrescoException;
	
    /**
     * Get the maven home xml file from the server and return as InputStream.
     * @return
     * @throws PhrescoException
     */
	InputStream getMavenHomeXml() throws PhrescoException;
	
    /**
     * Get the mailer xml file from the server and return as InputStream.
     * @return
     * @throws PhrescoException
     */
	InputStream getMailerXml() throws PhrescoException;
	
    /**
     * Get the Email-ext plugin from the server and return as InputStream.
     * @return
     * @throws PhrescoException
     */
	ClientResponse getEmailExtPlugin() throws PhrescoException;
	
    /**
     * Returns the list of serverss
     * @return
     * @throws PhrescoException
     */
    List<Server> getServers() throws PhrescoException;
    
    /**
     * Returns the list of database
     * @return
     * @throws PhrescoException
     */
    List<Database> getDatabases() throws PhrescoException;
	
	// To reset the AppTypes in cache
	void reSetCacheAppTypes() throws PhrescoException;

	List<Reports> getReports(String techId) throws PhrescoException;


}