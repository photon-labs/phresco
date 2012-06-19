package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ComponentRestWebserviceTest {
	
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
    public void testGetWebservice() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<WebService> webserviceClient = serviceManager.getRestClient(RestResourceURIs.REST_API_WEBSERVICES);
            webserviceClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<WebService>> genericType = new GenericType<List<WebService>>() {};
            List<WebService> list = webserviceClient.get(genericType);
            for (WebService  webservice : list) {
                System.out.println("name == " + webservice.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void testPostWebservice() throws PhrescoException {
    	    WebService webservice = new WebService();
    	    webservice.setName("web1");
    	    webservice.setDescription("new release");
    	   
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	List<WebService> webServices = new ArrayList<WebService>();
        	webServices.add(webservice);
            String webserviceJson = new Gson().toJson(webServices);
            RestClient<WebService>  webserviceClient = serviceManager.getRestClient(RestResourceURIs.REST_API_WEBSERVICES);
            webserviceClient.setType(MediaType.APPLICATION_JSON);
            webserviceClient.setAccept(MediaType.APPLICATION_JSON);
            webserviceClient.create(webserviceJson);
            
    }
    
    @Ignore
    public void testPutWebservice() throws PhrescoException {
    	 List<WebService> webServices = new ArrayList<WebService>();
    	WebService webservice = new WebService();
	    webservice.setName("Rest");
	    webservice.setDescription("new");
	    webservice.setId("2c909aa236e9c1190136e9c135430003");
    	webServices.add(webservice);
    	String webserviceJson = new Gson().toJson(webServices);
        serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<WebService>  webserviceClient = serviceManager.getRestClient(RestResourceURIs.REST_API_WEBSERVICES);
        webserviceClient.setAccept(MediaType.APPLICATION_JSON);
        webserviceClient.setType(MediaType.APPLICATION_JSON);
        System.out.println(webserviceJson);
        webserviceClient.update(webserviceJson);
    }
    
    @Test
    public void testGetWebserviceById() {
        try {
        	String id = "2c909aa236e9c1190136e9c135430003";
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<WebService> webserviceClient = serviceManager.getRestClient(RestResourceURIs.REST_API_WEBSERVICES);
            webserviceClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<WebService>> genericType = new GenericType<List<WebService>>() {};
            List<WebService> list = webserviceClient.get(genericType);
            for (WebService  webservice : list) {
                System.out.println("name == " + webservice.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    @Ignore
    public void testPutWebserviceById() throws PhrescoException {
    	List<WebService> webServices = new ArrayList<WebService>();
    	WebService webservice = new WebService();
	    webservice.setName("Rest");
	    webservice.setDescription("new");
	    webservice.setId("2c909aa236e9c1190136e9c135430003");
	 	webServices.add(webservice);
	 	String webserviceJson = new Gson().toJson(webServices);
	    serviceManager = ServiceClientFactory.getServiceManager(context);            
	    RestClient<WebService>  webserviceClient = serviceManager.getRestClient(RestResourceURIs.REST_API_WEBSERVICES);
	    webserviceClient.setAccept(MediaType.APPLICATION_JSON);
	    webserviceClient.setType(MediaType.APPLICATION_JSON);
	    webserviceClient.update(webserviceJson);
    }
    
    @Ignore
    public void testDeleteWebserviceById() throws PhrescoException {
    	 String id = "2c909aa236e996710136e9a3a0a20004" ;
         serviceManager = ServiceClientFactory.getServiceManager(context);            
     	 RestClient<WebService> webserviceClient = serviceManager.getRestClient(RestResourceURIs.REST_API_WEBSERVICES);
         webserviceClient.delete(id);
    }
}
