package com.photon.phresco.service.client.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestTechnologiesTest {
	
	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}

	@Test
    public void testGetTechnologies() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Technology> techClient = serviceManager.getRestClient("component");
            techClient.setPath("technologies");
            GenericType<List<Technology>> genericType = new GenericType<List<Technology>>(){};
            List<Technology> list = techClient.get(genericType);
            for (Technology tech : list) {
                System.out.println("Tech Name == " + tech.getName() + " id " + tech.getId());
            }
            
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

	@Test
    public void testCreateServer() throws PhrescoException {
    	List<Technology> techs = new ArrayList<Technology>();
    	Technology tech = new Technology();
    	tech.setName("Drupal");
    	List<String> versions = new ArrayList<String>();
    	versions.add("6.18");
    	versions.add("6.19");
    	versions.add("7.0");
		tech.setVersions(versions);
		techs.add(tech);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies");
		ClientResponse create = techClient.create(techs);
    }
    
	@Ignore
    public void testPutServer() throws PhrescoException {
    	List<Technology> techs = new ArrayList<Technology>();
    	Technology tech = new Technology();
    	tech.setId("4fe46c0d230d28c9353c4b69");
    	tech.setName("Java");
    	List<String> versions = new ArrayList<String>();
    	versions.add("1.5");
    	versions.add("1.6");
		tech.setVersions(versions);
		techs.add(tech);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies");
		GenericType<List<Technology>> type = new GenericType<List<Technology>>(){};
		List<Technology> entity = techClient.update(techs, type);
		for (Technology technology : entity) {
			System.out.println("tec " + technology);
		}
    }
    
	@Ignore
    public void testGetServerById() throws PhrescoException {
        try {
	    	String id = "4fe46c00230d28c9353c4b68";
	    	serviceManager=ServiceClientFactory.getServiceManager(context);
	    	RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies/" + id);
//	    	techClient.setAccept(MediaType.APPLICATION_JSON);
	    	GenericType<Technology> genericType = new GenericType<Technology>()  {};
	    	Technology tech = techClient.getById(genericType);
	    	System.out.println("name == " + tech);
    	    
        }catch(PhrescoException e){
        	e.printStackTrace();
        }
    }
    
	@Ignore
    public void testPutServerById() throws PhrescoException {
    	String id="4fe026c6230d6868296be32a";
    	Technology tech = new Technology();
    	tech.setId("4fe026c6230d6868296be32a");
    	tech.setName("android-native");
    	List<String> versions = new ArrayList<String>();
    	versions.add("1.0");
    	versions.add("3.0");
		tech.setVersions(versions);
    	serviceManager = ServiceClientFactory.getServiceManager(context);
		RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies/" + id);
//		techClient.setAccept(MediaType.APPLICATION_JSON);
//		techClient.setType(MediaType.APPLICATION_JSON);
		Type type = new TypeToken<Technology>() {}.getType();
		ClientResponse clientResponse = techClient.updateById(tech, type);
    }

	@Ignore
    public void testDeleteServerById() throws PhrescoException {
    	String id = "4fe451a5230da2b1ceb2f5b7" ;
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
    	RestClient<Technology> techClient = serviceManager.getRestClient("component/technologies/" + id);
    	ClientResponse response = techClient.deleteById();
    }
}
