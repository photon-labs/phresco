package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Server;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestServerTest implements ServiceConstants {

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
    public void testGetServers() throws PhrescoException {
    	RestClient<Server> serverClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SERVERS);
		GenericType<List<Server>> genericType = new GenericType<List<Server>>(){};
		List<Server> servers = serverClient.get(genericType);
        System.out.println("servers.size() in testGetServers():" + servers.size());
    }
	
	@Ignore
    public void testGetServersByTechnology() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<Server> serverClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SERVERBYID);
    	serverClient.setPath(techId);
		GenericType<List<Server>> genericType = new GenericType<List<Server>>(){};
		List<Server> servers = serverClient.get(genericType);
        System.out.println("servers.size() in testGetServers():" + servers.size());
    }
}