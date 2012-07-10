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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestAppTypesTest implements ServiceConstants {
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
	
	@Ignore
    public void testGetAppTypes() throws PhrescoException {
        RestClient<ApplicationType> applicationTypeClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
		GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
		List<ApplicationType> applicationTypes = applicationTypeClient.get(genericType);
        System.out.println("applicationTypes.size():" + applicationTypes.size());
//	    //System.out.println("applicationTypes.size():" + applicationTypes.size());
    }
	
	@Ignore
    public void testGetAppTypesByName() throws PhrescoException {
    	String appName = "apptype-webapp";
        RestClient<ApplicationType> applicationTypeClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
		GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
		List<ApplicationType> applicationTypes = applicationTypeClient.get(genericType);
		if (applicationTypes != null) {
			for (ApplicationType applicationType : applicationTypes) {
				if (applicationType.getName().equals(appName)) {
					System.out.println("applicationType.getDisplayName() " + applicationType.getDisplayName());
	            }
			}
		}
    }
	
	@Ignore
	public void createAppTypes() throws PhrescoException {
		List<ApplicationType> appTypes = new ArrayList<ApplicationType>();
		ApplicationType app = new ApplicationType("mobile-app", "mobile applications");
		app.setDisplayName("Mobile Applications");
		appTypes.add(app);
		RestClient<ApplicationType> applicationTypeClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
		ClientResponse response = applicationTypeClient.create(appTypes);
		System.out.println(response.getStatus());
	}
	
	@Ignore
	public void createTech() throws PhrescoException {
    	List<Technology> techs = new ArrayList<Technology>();
        Technology tech = new Technology();
        tech.setName("Drupal");
        tech.setDescription("web applications");
        List<String> versions = new ArrayList<String>();
        versions.add("6.18");
        versions.add("6.19");
        versions.add("7.0");
        tech.setVersions(versions);
        techs.add(tech);
        serviceManager = ServiceClientFactory.getServiceManager(context);
        RestClient<Technology> techClient = serviceManager.getRestClient("/component/technologies");
        techClient.queryString("techId", "4ffa6520230d17004cdb4828");
        ClientResponse response = techClient.create(techs);
        System.out.println("response " + response.getStatus());
	}
	
	
}