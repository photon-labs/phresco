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