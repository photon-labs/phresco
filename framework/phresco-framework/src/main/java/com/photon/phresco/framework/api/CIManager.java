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

import java.util.List;

import com.photon.phresco.commons.CIJob;
import com.photon.phresco.commons.CIJobStatus;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.CIBuild;
import com.sun.jersey.api.client.ClientResponse;


/**
 * Interface for communicating with Jenkins (CI)
 */
public interface CIManager {
    /**
     * Creates jobs in Jenkins.
     * @return
     * @throws PhrescoException
     */
    CIJobStatus createJob(CIJob job) throws PhrescoException;
    
    /**
     * Updates jobs in Jenkins.
     * @return
     * @throws PhrescoException
     */
    CIJobStatus updateJob(CIJob job) throws PhrescoException;
    
    /**
     * builds job in Jenkins.
     * @return
     * @throws PhrescoException
     */
    CIJobStatus buildJob(CIJob job) throws PhrescoException;
    
    /**
     * Gets builds info from the server.
     * @return
     * @throws PhrescoException
     */
    List<CIBuild> getCIBuilds(CIJob job) throws PhrescoException;
    
    /**
     * Get the jdk home xml file from the server and store in jenkins_home.
     * @return
     * @throws PhrescoException
     */
    void getJdkHomeXml() throws PhrescoException;

    /**
     * Get the maven home xml file from the server and store in jenkins_home.
     * @return
     * @throws PhrescoException
     */
    void getMavenHomeXml() throws PhrescoException;
    
    /**
     * Get the credential xml file from the server when job is created.
     * @return
     * @throws PhrescoException
     */
    void setMailCredential(CIJob job) throws PhrescoException;
    
    /**
     * Returns total number of builds in progress.
     * @return
     * @throws PhrescoException
     */
    int getTotalBuilds(CIJob job) throws PhrescoException;
    
    /**
     * Deletes jobs.
     * @return
     * @throws PhrescoException
     */
    CIJobStatus deleteCI(CIJob job, List<String> builds) throws PhrescoException;

    /**
     * Returns int value based on the progress of build
     * @return
     * @throws PhrescoException
     */
    int getProgressInBuild(CIJob job) throws PhrescoException;
    
    /**
     * Get the Email-ext plugin from the server.
     * @return
     * @throws PhrescoException
     */
	void getEmailExtPlugin() throws PhrescoException;
	
    /**
     * Delete already existing builds inside do_not_checkin folder.
     * @return
     * @throws PhrescoException
     */
	void deleteDoNotCheckin(CIJob job) throws PhrescoException;
}