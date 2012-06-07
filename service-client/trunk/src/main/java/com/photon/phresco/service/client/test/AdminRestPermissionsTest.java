package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.exception.PhrescoException;
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



public class AdminRestPermissionsTest {
	
	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}

    @Test
    public void testGetPermissions() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Permission> PermissionsClient = serviceManager.getRestClient(RestResourceURIs.REST_API_PERMISSIONS);
            PermissionsClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Permission>> genericType = new GenericType<List<Permission>>() {};
            List<Permission> list = PermissionsClient.get(genericType);
            for (Permission permission : list) {
				System.out.println("permission=="+permission.getName());
			}
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
        
    }
    
   /* @Ignore
    public void testPostPermissions() throws PhrescoException {
    	List<Permission> infos = new ArrayList<Permission>();
		Permission info = new Permission();  
		info.setId("E3511");
		info.setName("surendar");
		info.setDescription("developer");
		info.setCreationDate(new Date());
		infos.add(info);
		serviceManager = ServiceClientFactory.getServiceManager(context);            
        Client client = new Client();
        WebResource resource = client.resource("http://localhost:3030/service/admin/Customer");
        Builder builder = resource.accept(MediaType.APPLICATION_JSON);
        GenericType<List<Permission>> genericType = new GenericType<List<Permission>>() {};
        System.out.println("info");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,info);
    }
    
    @Ignore
    public void testPutPermissions() throws PhrescoException {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	Client client = new Client();
            WebResource resource = client.resource("http://localhost:3030/service/admin/roles");
            Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            GenericType<List<Permission>> genericType = new GenericType<List<Permission>>() {};
//            ClientResponse response = resource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class,"");
    }
    
    @Ignore
    public void testDeletePermissions() throws PhrescoException {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	Client client = new Client();
            WebResource resource = client.resource("http://localhost:3030/service/admin/roles");
            Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            GenericType<List<Permission>> genericType = new GenericType<List<Permission>>() {};
//            ClientResponse response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class,"");
    }
    
*/}
