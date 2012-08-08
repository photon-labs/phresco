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
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestWebserviceTest implements ServiceConstants {

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
	public void testCreateWebservice() throws PhrescoException {
		List<String> technologies=new ArrayList<String>();
		
		technologies.add("Html5");
	    List<WebService> wss = new ArrayList<WebService>();
	    WebService ws = new WebService();
	    ws.setId("testws");
	    ws.setName("TestWebService");
	    ws.setDescription("This is a test webservice");
	    ws.setCustomerId("photon");
	    ws.setTechnologies(technologies);
	    wss.add(ws);
        RestClient<WebService> newApp = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
        ClientResponse clientResponse = newApp.create(wss);
    }
	
	@Test
    public void testGetWebservices() throws PhrescoException {
    	RestClient<WebService> webserviceClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
    	webserviceClient.queryString(REST_QUERY_TECHID, "Html5");
    	webserviceClient.queryString(REST_QUERY_CUSTOMERID, "photon");
		GenericType<List<WebService>> genericType = new GenericType<List<WebService>>(){};
		List<WebService> webservices = webserviceClient.get(genericType);
		assertNotNull(webservices);
    }
	
	@Test
	public void testUpdateWebservices() throws PhrescoException{
		RestClient<WebService> webserviceClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
		List<String> technologies=new ArrayList<String>();
		technologies.add("Html5");
	    List<WebService> wss = new ArrayList<WebService>();
	    WebService ws = new WebService();
	    ws.setId("testws");
	    ws.setName("TestWebService");
	    ws.setDescription("This is a test webservice update");
	    ws.setCustomerId("photon");
	    ws.setTechnologies(technologies);
	    wss.add(ws);
	    GenericType<List<WebService>> genericType = new GenericType<List<WebService>>() {};
	   
	    List<WebService> clientResponse = webserviceClient.update(wss, genericType);
	    
	}

	@Test
    public void testGetWebservicesById() throws PhrescoException {
		String techId = "testws";
    	RestClient<WebService> webserviceClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
    	webserviceClient.queryString(REST_API_PATH_PARAM_ID, techId);
		GenericType<List<WebService>> genericType = new GenericType<List<WebService>>(){};
		List<WebService> webservices = webserviceClient.get(genericType);
		assertNotNull(webservices);
    }
	
	@Test
	public void testUpdateWebserviceById() throws PhrescoException {
        RestClient<WebService> editWS = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
        WebService ws = new WebService();
        ws.setId("testws");
	    ws.setName("TestWebServiceUpdate");
	    ws.setDescription("This is a test webservice updateId");
	    ws.setCustomerId("photon");
	    editWS.setPath("testws");
        GenericType<WebService> genericType = new GenericType<WebService>() {};
        editWS.updateById(ws, genericType);
    }

	@Test
	public void testDeleteWebservice() throws PhrescoException {
        RestClient<WebService> deletewebservice = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
        deletewebservice.setPath("testws");
        ClientResponse clientResponse = deletewebservice.deleteById();
    }
	
}