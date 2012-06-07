package com.photon.phresco.service.client.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ClientHelper;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class ServiceClientFactory implements ServiceClientConstant {
	
	private static final Map<UserInfo, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<UserInfo, ServiceManager>();
	
 	private ServiceClientFactory() {

 	}
 	
	public static final ServiceManager getServiceManager(ServiceContext context) throws PhrescoException {
		ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(context);
		if (serviceManager == null) {
			serviceManager = new ServiceManagerImpl(context);
			CONTEXT_MANAGER_MAP.put(serviceManager.getUserInfo(), serviceManager);	
		}
		
		return serviceManager;
	}
	
	public static void main(String a[]) {
		ServiceContext context = new ServiceContext();
		context.put(SERVICE_URL, "http://localhost:3030/service/rest");
		context.put(SERVICE_USERNAME, "demouser");
		context.put(SERVICE_PASSWORD, "phresco");
		ServiceManager serviceManager;
		try {
			serviceManager = ServiceClientFactory.getServiceManager(context);
			RestClient<Customer> customerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS);
			Customer c1 = new Customer("Macys", "Macys");
			c1.setId("c4");
			List<Customer> newList = new ArrayList<Customer>();
			newList.add(c1);
			customerClient.setType(MediaType.APPLICATION_JSON);
//			customerClient.create(newList);

			GenericType<List<Customer>> genericType = new GenericType<List<Customer>>() {};
			List<Customer> list = customerClient.get(genericType);
			for (Customer customer : list) {
				System.out.println("name == " + customer.getName());
			}
		} catch (PhrescoException e) {
			e.printStackTrace();
		}

	}
	
	
	
	/*public static void main(String[] args) {
//	        resource.accept(MediaType.APPLICATION_OCTET_STREAM);
//	        resource.accept(MediaType.APPLICATION_JSON);
	        Customer c1 = new Customer("VSR", "VSR Infotech");
			c1.setId("c6");
			List<Customer> newList = new ArrayList<Customer>(1);
			newList.add(c1);
			GenericType<List<Customer>> genericType = new GenericType<List<Customer>>() {};
//	        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, newList);
			
			Client client = ClientHelper.createClient();
	        WebResource resource = client.resource("http://localhost:3030/service/rest/" + RestResourceURIs.REST_API_CUSTOMERS);
	        resource.accept(MediaType.APPLICATION_JSON_TYPE);
	        ClientResponse response = resource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, newList);
			
			
	}*/
}
