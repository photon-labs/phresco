package com.photon.phresco.service.client.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.ClientHelper;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ComponentRestTechnologiesTest {
	
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
    public void testGetTechnologies() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies");
            techClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Technology>> genericType = new GenericType<List<Technology>>() {};
            List<Technology> list = techClient.get(genericType);
            for (Technology tech : list) {
                System.out.println("Tech Name == " + tech.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateServer() throws PhrescoException {
    	List<Technology> techs = new ArrayList<Technology>();
    	Technology tech = new Technology();
    	tech.setName("php");
    	List<String> versions = new ArrayList<String>();
    	versions.add("6.0");
    	versions.add("7.0");
		tech.setVersions(versions);
		techs.add(tech);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
    	Gson gson = new Gson();
    	String techJson = gson.toJson(techs);
    	System.out.println(techJson);
		Type type = new TypeToken<List<Technology>>() {}.getType();
		List<Technology> technologyList = gson.fromJson(techJson, type);
		
//		RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies");
//		techClient.setAccept(MediaType.APPLICATION_JSON);
//		techClient.setType(MediaType.APPLICATION_JSON);
		Client client = ClientHelper.createClient();
		WebResource resource = client.resource("");
		Builder buildes = resource.type(MediaType.APPLICATION_JSON);
//		ClientResponse clientResponse = builder.post(ClientResponse.class, infos);
    }
    
    @Ignore
    public void testPutServer() throws PhrescoException {
    	String id="2c909c4836f2bb7b0136f2bba6db0003";
    	List<Server> servers = new ArrayList<Server>();
    	Server server = new Server();
    	server.setName("Local Server");
    	server.setDescription("Apache tomcat");
    	servers.add(server);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		String ServerJson = new Gson().toJson(servers);
		RestClient<Server> ServerClient = serviceManager.getRestClient("");
		ServerClient.setAccept(MediaType.APPLICATION_JSON);
		ServerClient.setType(MediaType.APPLICATION_JSON);
		ServerClient.update(ServerJson);
    }
    
    @Ignore
    public void testGetServerById() throws PhrescoException {
        try {
	    	String id = "2c909c4836f2bb7b0136f2bba6db0003";
	    	serviceManager=ServiceClientFactory.getServiceManager(context);
	    	RestClient<Server> ServerClient = serviceManager.getRestClient("");
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
    
    @Ignore
    public void testPutServerById() throws PhrescoException {
    	String id="2c909c4836f2bb7b0136f2bba6db0003";
    	List<Server> servers = new ArrayList<Server>();
    	Server server = new Server();
    	server.setName("Server");
    	server.setDescription("Apache tomcat");
    	servers.add(server);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		String ServerJson = new Gson().toJson(servers);
		RestClient<Server> ServerClient = serviceManager.getRestClient("" + "/" + id);
		ServerClient.setAccept(MediaType.APPLICATION_JSON);
		ServerClient.setType(MediaType.APPLICATION_JSON);
		ServerClient.update(ServerJson);
    }
    
    @Ignore
    public void testDeleteServerById() throws PhrescoException {
    	    String id = "2c909c4836f2bb7b0136f2bba6db0003" ;
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	RestClient<Server> databaseClient = serviceManager.getRestClient("");
            databaseClient.delete(id);
    }
}
