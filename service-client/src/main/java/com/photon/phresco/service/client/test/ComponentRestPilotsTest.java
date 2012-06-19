package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ComponentRestPilotsTest {
	
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
    public  void testGetPilots() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<ProjectInfo> ServerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_PILOTS);
            ServerClient.setType(MediaType.APPLICATION_JSON);
            GenericType<Collection<ProjectInfo>> genericType = new GenericType<Collection<ProjectInfo>>() {};
            Collection<ProjectInfo> list = ServerClient.get(genericType);
            for (ProjectInfo ProjectInfo : list) {
              System.out.println(ProjectInfo.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void testPostPilots() throws PhrescoException {
    	
    	Set<ProjectInfo> infos = new HashSet<ProjectInfo>();
		ProjectInfo info = new ProjectInfo();
		info.setApplication("Drupel Application");
		info.setCode("PHR");
		info.setContentUrl("C:");
		info.setCreationDate(new Date());
		info.setName("Drupel Technology");
		Technology technology = new Technology();
		technology.setCreationDate(new Date());
		technology.setDescription("Web Technology");
		technology.setAppTypeId("11");
		technology.setEmailSupported(true);
		technology.setGlobal(true);
		technology.setName("Drupel Technology");
		info.setTechnology(technology);
		Set<Server> servers = new HashSet<Server>();
		Server server = new Server();
		server.setDescription("weblogic");
		servers.add(server);
		info.setServers(servers);
		Database database = new Database();
		Set<Database> databases = new HashSet<Database>();
		database.setCreationDate(new Date());
		database.setDescription("Local Database");
		
		database.setName("MYSQL");
		databases.add(database);
		info.setDatabases(databases);
		
		WebService webservice = new WebService();
		Set<WebService> webServices = new HashSet<WebService>();
		webservice.setDescription("Online Transaction");
		webServices.add(webservice);
		info.setWebServices(webServices);
		infos.add(info);
    	
    	serviceManager = ServiceClientFactory.getServiceManager(context);  
    	RestClient<ProjectInfo> pilotclient = serviceManager.getRestClient(RestResourceURIs.REST_API_PILOTS);
           String pilotJson = new Gson().toJson(infos); 
           System.out.println(pilotJson);
        	pilotclient.setAccept(MediaType.APPLICATION_JSON);
        	pilotclient.setType(MediaType.APPLICATION_JSON);
        	pilotclient.create(pilotJson);
            
    }
    
    @Ignore
    public void testPutPilots() throws PhrescoException {
    	
    	Set<ProjectInfo> infos = new HashSet<ProjectInfo>();
		ProjectInfo info = new ProjectInfo();
		info.setApplication("PHP Application");
		info.setCode("PHR");
		info.setContentUrl("C:");
		info.setCreationDate(new Date());
		info.setName("PHP Technology");
		info.setId("2c90994436e86fca0136e87eff2e0003");
		Set<Server> servers = new HashSet<Server>();
		Server server = new Server();
		server.setDescription("weblogic");
		servers.add(server);
		info.setServers(servers);
		Database database = new Database();
		Set<Database> databases = new HashSet<Database>();
		database.setCreationDate(new Date());
		database.setDescription("Local Database");
		database.setName("oracle");
		databases.add(database);
		info.setDatabases(databases);
		Technology technology = new Technology();
		technology.setCreationDate(new Date());
		technology.setDescription("Web Technology");
		technology.setAppTypeId("11");
		technology.setEmailSupported(true);
		technology.setGlobal(true);
		technology.setName("PHP Technology");
		info.setTechnology(technology);
		WebService webservice = new WebService();
		Set<WebService> webServices = new HashSet<WebService>();
		webservice.setDescription("Online Transaction");
		webServices.add(webservice);
		info.setWebServices(webServices);
		infos.add(info);
    	
		
		serviceManager = ServiceClientFactory.getServiceManager(context);  
     	RestClient<ProjectInfo> pilotclient=serviceManager.getRestClient(RestResourceURIs.REST_API_PILOTS);
     	String pilotJson=new Gson().toJson(infos); 
     	pilotclient.setAccept(MediaType.APPLICATION_JSON);
     	pilotclient.setType(MediaType.APPLICATION_JSON);
     	pilotclient.update(pilotJson);
         
			}
    @Ignore
    public void testGetPilotByID() {
        try {
        	String id = "2c90994436e8218e0136e8226f9f0007";
        	serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<ProjectInfo> pilotclient = serviceManager.getRestClient(RestResourceURIs.REST_API_PILOTS+"/" +id);
            pilotclient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>() {};
            List<ProjectInfo> list = pilotclient.get(genericType);
            for (ProjectInfo projectInfo : list) {
                System.out.println("name == " + projectInfo.getServers());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPutPilotById() throws PhrescoException {
    	String id = "2c90994436e8218e0136e821c6bd0003";
    	Set<ProjectInfo> infos = new HashSet<ProjectInfo>();
		ProjectInfo info = new ProjectInfo();
		info.setApplication("Dotnet Application");
		info.setCode("PHR");
		info.setContentUrl("C:");
		info.setCreationDate(new Date());
		info.setName("Dotnet Technology");
		info.setId(id);
		Set<Server> servers = new HashSet<Server>();
		Server server = new Server();
		server.setDescription("Tomcat");
		servers.add(server);
		info.setServers(servers);
		Database database = new Database();
		Set<Database> databases = new HashSet<Database>();
		database.setCreationDate(new Date());
		database.setDescription("Local Database");
		database.setName("DB2");
		databases.add(database);
		info.setDatabases(databases);
		Technology technology = new Technology();
		technology.setCreationDate(new Date());
		technology.setDescription("Web Technology");
		technology.setAppTypeId("11");
		technology.setEmailSupported(true);
		technology.setGlobal(true);
		technology.setName("Dotnet Technology");
		info.setTechnology(technology);
		WebService webservice = new WebService();
		Set<WebService> webServices = new HashSet<WebService>();
		webservice.setDescription("Online Transaction");
		webServices.add(webservice);
		info.setWebServices(webServices);
		infos.add(info);
    
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         RestClient<ProjectInfo> pilotclient = serviceManager.getRestClient(RestResourceURIs.REST_API_PILOTS );
         String pilotJson = new Gson().toJson(infos);
         System.out.println(pilotJson);
         pilotclient.setAccept(MediaType.APPLICATION_JSON);
         pilotclient.setType(MediaType.APPLICATION_JSON);
         pilotclient.update(pilotJson);
    }
    @Ignore
    public void testDeletePilotById() throws PhrescoException {
    	String id = "2c90994436e8218e0136e8226f9f0007";    
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<ProjectInfo> pilotclient = serviceManager.getRestClient(RestResourceURIs.REST_API_PILOTS);
        pilotclient.delete(id);
    }
    
}
