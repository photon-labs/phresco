package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
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
	
	@Test
    public void testGetAppTypes() throws PhrescoException {
        RestClient<ApplicationType> applicationTypeClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
		GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
		List<ApplicationType> applicationTypes = applicationTypeClient.get(genericType);
        System.out.println("applicationTypes.size():" + applicationTypes.size());
    }
	
	@Test
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
}