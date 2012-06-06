package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Database;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider;

public class ComponentRestDatabaseTest {
	
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
    public void testGetDatabase() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Database> databaseClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DATABASES);
            databaseClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Database>> genericType = new GenericType<List<Database>>() {};
            List<Database> list = databaseClient.get(genericType);
            for (Database  database : list) {
                System.out.println("name == " + database.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostDatabase() throws PhrescoException {
        List<Database> databases = new ArrayList<Database>();
		Database database = new Database();
         database.setCreationDate(new Date());
         database.setName("Developer");
         database.setId("id");
         database.setDescription("description");
         databases.add(database);
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         String databasejson = new Gson().toJson(databases);
         RestClient<Role> databaseClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DATABASES);
         databaseClient.setAccept(MediaType.APPLICATION_JSON);
         databaseClient.setType(MediaType.APPLICATION_JSON);
         databaseClient.create(databasejson);
    }
    
    @Ignore
    public void testPutDatabase() throws PhrescoException {	
	        Database database = new Database();
	        database.setCreationDate(new Date());
	        database.setName("MANAGING");
	        database.setDescription("AUDITING");
	        database.setId("2c909aa236e905920136e90837a90006");
	       
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            List<Database> databases = new ArrayList<Database>();
            databases.add(database);
            String databaseJson = new Gson().toJson(databases);
            RestClient<Database> databaseClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DATABASES);
            databaseClient.setType(MediaType.APPLICATION_JSON);
            databaseClient.setAccept(MediaType.APPLICATION_JSON);
            databaseClient.update(databaseJson);
    }
    
    @Ignore
    public void testGetDatabaseById() throws PhrescoException {
        try {
	    	String id = "2c909aa236e905920136e9080c340005";
	    	serviceManager=ServiceClientFactory.getServiceManager(context);
	    	RestClient<Database> databaseClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DATABASES);
	    	databaseClient.setType(MediaType.APPLICATION_JSON);
	    	GenericType<List<Database>> genericType = new GenericType<List<Database>>()  {};
	    	List<Database> list = databaseClient.get(genericType);
	    	for (Database database :list ){
	    		System.out.println("name == " + database.getName());
    	    }
        }catch(PhrescoException e){
        	e.printStackTrace();
        }
    }
    
    @Ignore
    public void testPutDatabaseById() throws PhrescoException {
           
    	String id = "2c909aa236e991850136e9928b190003";
    	Database database = new Database();
    	database.setCreationDate(new Date());
    	database.setName("Mysql");
    	database.setDescription("hello");
    	database.setId(id);
    
    	serviceManager= ServiceClientFactory.getServiceManager(context);
    	List<Database> databases = new ArrayList<Database>();
        databases.add(database);
        String databaseJson = new Gson().toJson(databases);
    	RestClient<Database> databaseClient= serviceManager.getRestClient(RestResourceURIs.REST_API_DATABASES);
    	databaseClient.setType(MediaType.APPLICATION_JSON);
    	databaseClient.setAccept(MediaType.APPLICATION_JSON);
    	databaseClient.update(databaseJson);
    }
    
    
    @Ignore
    public void testDeleteDatabaseById() throws PhrescoException {
    	    String id = "2c909aa236e991850136e9928b190003" ;
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	RestClient<Database> databaseClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DATABASES);
            databaseClient.delete(id);
    }
}
