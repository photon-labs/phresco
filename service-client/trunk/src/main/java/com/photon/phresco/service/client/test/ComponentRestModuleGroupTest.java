package com.photon.phresco.service.client.test;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.Server;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ComponentRestModuleGroupTest {
	
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
    public void testGetModuleGroup() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<ModuleGroup> customerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_MODULES);
            customerClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>() {};
            List<ModuleGroup> list = customerClient.get(genericType);
            for (ModuleGroup ModuleGroup : list) {
                System.out.println("ModuleGroup Name == " + ModuleGroup.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

   @Ignore
    public void testPostModuleGroup() throws PhrescoException {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	Client client = new Client();
            WebResource resource = client.resource("http://localhost:3030/service/admin/modules");
            Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>() {};
//          ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, "");
    }
    
  
   @Ignore
    public void testPutModuleGroup() throws PhrescoException {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	Client client = new Client();
            WebResource resource = client.resource("http://localhost:3030/service/admin/modules");
            Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>() {};
//            ClientResponse response = resource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class,"");
    }
  
  
    @Ignore
    public void testDeleteServer() throws PhrescoException {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
        	Client client = new Client();
            WebResource resource = client.resource("http://localhost:3030/service/admin/modules");
            Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>() {};
//            ClientResponse response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class,"");
    }
    


}
