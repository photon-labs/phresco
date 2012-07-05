package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
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
    public void testGetWebservices() throws PhrescoException {
    	RestClient<WebService> webserviceClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
		GenericType<List<WebService>> genericType = new GenericType<List<WebService>>(){};
		List<WebService> webservices = webserviceClient.get(genericType);
        System.out.println("webservices.size():" + webservices.size());
    }
	
	@Test
    public void testGetWebservicesByTechnology() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<WebService> webserviceClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICESBYID);
    	webserviceClient.setPath(techId);
		GenericType<List<WebService>> genericType = new GenericType<List<WebService>>(){};
		List<WebService> webservices = webserviceClient.get(genericType);
        System.out.println("webservices.size():" + webservices.size());
    }
}