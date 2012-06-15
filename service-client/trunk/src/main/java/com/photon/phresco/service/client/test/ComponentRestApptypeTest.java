package com.photon.phresco.service.client.test;

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
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AppType;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestApptypeTest {
	
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
    public void testGetAppType() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<ApplicationType> AppTypeClient = serviceManager.getRestClient(RestResourceURIs.REST_API_APPTYPES);
            AppTypeClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>() {};
            List<ApplicationType> list = AppTypeClient.get(genericType);
            for (ApplicationType ApplicationType : list) {
                System.out.println("ApplicationType == " + ApplicationType.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
	 
	 @Ignore
	 public void testPostApptype() throws PhrescoException {
    	 ApplicationType apptype=new ApplicationType();
      	 Set<ApplicationType> apptypes = new HashSet<ApplicationType>();
      	 apptype.setCreationDate(new Date());
      	 apptype.setGlobal(true);
      	 apptype.setDescription("Phresco");
      	 apptype.setName("Photon ");
      	 Set<Technology> technologies = new HashSet<Technology>();
      	 Technology technology = new Technology();
      	 technology.setAppTypeId("Drupal");
      	 technology.setCreationDate(new Date());
      	 technology.setDescription("PHP");
      	 technology.setEmailSupported(true);
      	 technology.setGlobal(true);
      	 technologies.add(technology);
      	 apptype.setTechnologies(technologies);
      	 apptypes.add(apptype);
         String customerJson = new Gson().toJson(apptypes);
         serviceManager = ServiceClientFactory.getServiceManager(context);  
         RestClient<ApplicationType> appTypeClient = serviceManager.getRestClient(RestResourceURIs.REST_API_APPTYPES);
         appTypeClient.setType(MediaType.APPLICATION_JSON);
         appTypeClient.setAccept(MediaType.APPLICATION_JSON);
         appTypeClient.create(customerJson);
}
   
    
   
      @Ignore
        public void testPutApptype() throws PhrescoException {
	    	 ApplicationType apptype=new ApplicationType();
	      	 Set<ApplicationType> apptypes = new HashSet<ApplicationType>();
	      	 apptype.setCreationDate(new Date());
	      	 apptype.setGlobal(true);
	      	 apptype.setDescription("Phrescos");
	      	 apptype.setName("Photons Technology");
	      	 apptype.setId("2c90968936f28e680136f28eee440003");
	      	 Set<Technology> technologies = new HashSet<Technology>();
	      	 Technology technology = new Technology();
	      	 technology.setAppTypeId("PHP");
	      	 technology.setCreationDate(new Date());
	      	 technology.setDescription("PHP");
	      	 technology.setEmailSupported(true);
	      	 technology.setGlobal(true);
	      	 technologies.add(technology);
	      	 apptype.setTechnologies(technologies);
	      	 apptypes.add(apptype);
	         String customerJson = new Gson().toJson(apptypes);
	         serviceManager = ServiceClientFactory.getServiceManager(context);  
	         RestClient<ApplicationType> appTypeClient = serviceManager.getRestClient(RestResourceURIs.REST_API_APPTYPES);
	         appTypeClient.setType(MediaType.APPLICATION_JSON);
	         appTypeClient.setAccept(MediaType.APPLICATION_JSON);
	         appTypeClient.update(customerJson);
    }
     
      @Ignore
      public void testGetRoleByID() {
	         try {
	         String id = "2c90968936f28e680136f28eee440003";
	         serviceManager = ServiceClientFactory.getServiceManager(context);            
	         RestClient<ApplicationType> AppTypeClient = serviceManager.getRestClient(RestResourceURIs.REST_API_APPTYPES + "/" + id);
	         AppTypeClient.setType(MediaType.APPLICATION_JSON);
	         GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>() {};
	         List<ApplicationType> list = AppTypeClient.get(genericType);
	         for (ApplicationType ApplicationType : list) {
	         System.out.println("ApplicationType == " + ApplicationType.getName());
	             }
	         } catch (PhrescoException e) {
	             e.printStackTrace();
	         }
	      } 
      
      @Ignore
      public void testPutApptypeById() throws PhrescoException {
    	  ApplicationType apptype=new ApplicationType();
        	Set<ApplicationType> apptypes = new HashSet<ApplicationType>();
        	apptype.setCreationDate(new Date());
        	apptype.setGlobal(true);
        	apptype.setDescription("Phresco");
        	apptype.setName("Photon TEchnology");
        	apptype.setId("2c90968936f28e680136f28ff1350005");
        	Set<Technology> technologies = new HashSet<Technology>();
        	Technology technology = new Technology();
        	technology.setAppTypeId("PHP");
        	technology.setCreationDate(new Date());
        	technology.setDescription("PHP");
        	technology.setEmailSupported(true);
        	technology.setGlobal(true);
        	technologies.add(technology);
        	apptype.setTechnologies(technologies);
        	apptypes.add(apptype);
            String customerJson = new Gson().toJson(apptypes);
            serviceManager = ServiceClientFactory.getServiceManager(context);  
            RestClient<ApplicationType> appTypeClient = serviceManager.getRestClient(RestResourceURIs.REST_API_APPTYPES);
            appTypeClient.setType(MediaType.APPLICATION_JSON);
            appTypeClient.setAccept(MediaType.APPLICATION_JSON);
            appTypeClient.update(customerJson);
      }
      
 
      @Test
      public void testDeleteApptypeById() throws PhrescoException {
      	String id = "2c90968936f28e680136f28ff1350005";    
      	 serviceManager = ServiceClientFactory.getServiceManager(context);            
          RestClient<ApplicationType> apptypeClient = serviceManager.getRestClient(RestResourceURIs.REST_API_APPTYPES);
          apptypeClient.delete(id);
      	
          }
    

}


