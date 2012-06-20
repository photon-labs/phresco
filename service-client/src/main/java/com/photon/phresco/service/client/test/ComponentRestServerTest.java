package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Server;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestServerTest {
	
	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}

    @Ignore
    public void testGetServer() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Server> customerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SERVERS);
            customerClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Server>> genericType = new GenericType<List<Server>>() {};
            List<Server> list = customerClient.get(genericType);
            for (Server Server : list) {
                System.out.println("Server Name == " + Server.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void testPostServer() throws PhrescoException {
    	List<Server> servers = new ArrayList<Server>();
    	Server server = new Server();
    	server.setName("hello");
    	server.setDescription("tomcat");
        servers.add(server); 
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		String ServerJson = new Gson().toJson(servers);
		RestClient<Server> ServerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SERVERS);
		ServerClient.setAccept(MediaType.APPLICATION_JSON);
		ServerClient.setType(MediaType.APPLICATION_JSON);
		ServerClient.create(ServerJson);
    }
    
    @Ignore
    public void testPutServer() throws PhrescoException {
    	String id="2c909c4836f2bb7b0136f2bba6db0003";
    	List<Server> servers = new ArrayList<Server>();
    	Server server = new Server();
    	server.setName("Local Server");
    	server.setDescription("Apache tomcat");
    	servers.add(server);
    	server.setId(id);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		String ServerJson = new Gson().toJson(servers);
		RestClient<Server> ServerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SERVERS);
		ServerClient.setAccept(MediaType.APPLICATION_JSON);
		ServerClient.setType(MediaType.APPLICATION_JSON);
		ServerClient.update(ServerJson);
    }
    
    @Ignore
    public void testGetServerById() throws PhrescoException {
        try {
	    	String id = "2c909c4836f2bb7b0136f2bba6db0003";
	    	serviceManager=ServiceClientFactory.getServiceManager(context);
	    	RestClient<Server> ServerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SERVERS);
	    	ServerClient.setType(MediaType.APPLICATION_JSON);
	    	GenericType<List<Server>> genericType = new GenericType<List<Server>>()  {};
	    	List<Server> list = ServerClient.get(genericType);
	    	for (Server Server :list ){
	    		System.out.println("name == " + Server.getName());
    	    }
        }catch(PhrescoException e){
        	e.printStackTrace();
        }
    }
    
    @Test
    public void testPutServerById() throws PhrescoException {
    	String id="2c909c4836f2bb7b0136f2bba6db0003";
    	List<Server> servers = new ArrayList<Server>();
    	Server server = new Server();
    	server.setName("Server");
    	server.setDescription("Apache tomcat");
    	servers.add(server);
    	server.setId(id);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		String ServerJson = new Gson().toJson(servers);
		RestClient<Server> ServerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SERVERS + "/" + id);
		ServerClient.setAccept(MediaType.APPLICATION_JSON);
		ServerClient.setType(MediaType.APPLICATION_JSON);
		ServerClient.update(ServerJson);
    }
    
    @Test
    public void testDeleteServerById() throws PhrescoException {
    	    String id = "2c909c4836f2bb7b0136f2bba6db0003" ;
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	RestClient<Server> databaseClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SERVERS);
            databaseClient.delete(id);
    }
}
