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

package com.photon.phresco.service.client.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestPilotTest implements ServiceConstants {

	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() throws PhrescoException {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
	}
	

	@Test
	public void testCreatePilots() throws PhrescoException {
		Technology technology=new Technology();
		technology.setId("Html5");
	    List<ProjectInfo> projectInfo = new ArrayList<ProjectInfo>();
	    ProjectInfo pi = new ProjectInfo();
	    pi.setId("testPilot");
	    pi.setName("TestPilot");
	    pi.setDescription("This is a test pilot");
	    pi.setCustomerId("photon");
	    pi.setTechnology(technology);
	    projectInfo.add(pi);
        RestClient<ProjectInfo> newPilot = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
        ClientResponse clientResponse = newPilot.create(projectInfo);
    }
	
	@Test
    public void testFindPilots() throws PhrescoException {
		String techID="Html5";
    	RestClient<ProjectInfo> pilotClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
    	pilotClient.queryString(REST_QUERY_TECHID, techID);
		GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};
		List<ProjectInfo> pi = pilotClient.get(genericType);
		assertNotNull(pi);
    }
	

	@Test
	public void testUpdatePilot() throws PhrescoException{
		RestClient<ProjectInfo> pilotClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
	    List<ProjectInfo> projectInfo = new ArrayList<ProjectInfo>();
	    Technology technology=new Technology();
	    technology.setId("Html5");
	    ProjectInfo pi = new ProjectInfo();
	    pi.setId("testPilot");
	    pi.setName("TestPilot");
	    pi.setDescription("This is a test pilot update");
	    pi.setCustomerId("photon");
	    pi.setTechnology(technology);
	    projectInfo.add(pi);
	    GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>() {};
	    List<ProjectInfo> clientResponse = pilotClient.update(projectInfo, genericType);
	}

	@Test
    public void testGetPilotById() throws PhrescoException {
		String Id = "testPilot";
    	RestClient<ProjectInfo> pilotClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
    	pilotClient.queryString(REST_API_PATH_PARAM_ID, Id);
		GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};
		List<ProjectInfo> projectInfo = pilotClient.get(genericType);
		assertNotNull(projectInfo);
    }
	
	@Test
	public void testUpdatePilotById() throws PhrescoException {
        RestClient<ProjectInfo> editPI = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
        Technology technology =new Technology();
        technology.setId("Html5");
        ProjectInfo pi = new ProjectInfo();
        pi.setId("testPilot");
	    pi.setName("TestPilotUpdateBYId");
	    pi.setDescription("This is a test pilot updateId");
	    pi.setCustomerId("photon");
	    pi.setTechnology(technology);
	    editPI.setPath("testPilot");
        GenericType<ProjectInfo> genericType = new GenericType<ProjectInfo>() {};
        editPI.updateById(pi, genericType);
    }
	
	@Test
	public void testDeletePilot() throws PhrescoException {
        RestClient<ProjectInfo> deletePilot = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
        deletePilot.setPath("testPilot");
        ClientResponse clientResponse = deletePilot.deleteById();
    }
	
}