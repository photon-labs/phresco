package com.photon.phresco.service.client.test;

import java.util.ArrayList;
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
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ComponentRestTechnologysTest {
	
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
    public void testGetTechnology() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Technology> technologyClient = serviceManager.getRestClient(RestResourceURIs.REST_API_TECHNOLOGIES);
            technologyClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Technology>> genericType = new GenericType<List<Technology>>() {};
            List<Technology> list = technologyClient.get(genericType);
            for (Technology Technology : list) {
                System.out.println("Technology Name == " + Technology.getDescription());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void testPostTechnology() throws PhrescoException {
    	Technology technology = new Technology();
    	technology.setAppTypeId("PHP");
    	technology.setCreationDate(new Date());
    	technology.setDescription("PHP Technology");
    	technology.setEmailSupported(true);
    	technology.setGlobal(true);
    	Set<Technology> technologies = new HashSet<Technology>();
    	technologies.add(technology);
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
         String customerJson = new Gson().toJson(technologies);
         RestClient<Technology> technologyClient = serviceManager.getRestClient(RestResourceURIs.REST_API_TECHNOLOGIES);
         technologyClient.setAccept(MediaType.APPLICATION_JSON);
         technologyClient.setType(MediaType.APPLICATION_JSON);
         technologyClient.create(customerJson);
    }
    //Updating a list of Objects
    @Ignore
    public void testPutTechnology() throws PhrescoException {
    	Technology technology = new Technology();
    	technology.setAppTypeId("Drupal");
    	technology.setCreationDate(new Date());
    	technology.setDescription("PHP Technology");
    	technology.setEmailSupported(true);
    	technology.setGlobal(true);
    	technology.setName("JAVA");
    	technology.setId("2c909c4836e8921f0136e8ab88bf0003");
    	List<Technology> technologies = new ArrayList<Technology>();
    	technologies.add(technology);
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        String technologyJson = new Gson().toJson(technologies);
        RestClient<Technology> technologyClient = serviceManager.getRestClient(RestResourceURIs.REST_API_TECHNOLOGIES);
        technologyClient.setAccept(MediaType.APPLICATION_JSON);
        technologyClient.setType(MediaType.APPLICATION_JSON);
        technologyClient.update(technologyJson);
    }
   
    @Ignore
    public void testGetTechnologyByID() {
        try {
        	String id = "2c909c4836e7c7390136e7c764640003";
        	serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Technology> technologyClient = serviceManager.getRestClient(RestResourceURIs.REST_API_TECHNOLOGIES+"/" +id);
            technologyClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Technology>> genericType = new GenericType<List<Technology>>() {};
            List<Technology> list = technologyClient.get(genericType);
            for (Technology Technology : list) {
                System.out.println("name == " + Technology.getDescription());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    //Updating a single object
    
    @Test
    public void testPutTechnologyById() throws PhrescoException {
    	String id = "2c909c4836e8921f0136e8ab88bf0003";
    	Technology technology = new Technology();
    	technology.setName("Drupal 9");
    	technology.setAppTypeId("Drupal 9 ");
    	technology.setCreationDate(new Date());
    	technology.setDescription("Drupal Technology9");
    	technology.setEmailSupported(true);
    	technology.setGlobal(true);
    	technology.setId(id);
    	List<Technology> technologies = new ArrayList<Technology>();
    	technologies.add(technology);
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         String technologyJson = new Gson().toJson(technologies);
         RestClient<Technology> technologyClient = serviceManager.getRestClient(RestResourceURIs.REST_API_TECHNOLOGIES);
         technologyClient.setAccept(MediaType.APPLICATION_JSON);
         technologyClient.setType(MediaType.APPLICATION_JSON);
         technologyClient.update(technologyJson);
    }
    
    
    @Ignore
    public void testDeleteTechnology() throws PhrescoException {
    	String id = "2c909c4836e879090136e879c7a20004";    
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<Technology> technologyClient = serviceManager.getRestClient(RestResourceURIs.REST_API_TECHNOLOGIES);
        technologyClient.delete(id);
    }
    


}
