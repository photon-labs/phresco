/*
 * ###
 * Phresco Service Client
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.service.client.factory;

import java.util.HashMap;
import java.util.Map;

import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;

public class ServiceClientFactory implements ServiceClientConstant {
	
	private static final Map<User, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<User, ServiceManager>();
	
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
	
	/*public static void main(String a[]) {
		ServiceContext context = new ServiceContext();
		context.put(SERVICE_URL, "http://localhost:3030/service/rest");
		context.put(SERVICE_USERNAME, "demouser");
		context.put(SERVICE_PASSWORD, "phresco");
		ServiceManager serviceManager;
		try {
			serviceManager = ServiceClientFactory.getServiceManager(context);
			RestClient<Customer> customerClient = serviceManager.getRestClient("");
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

	}*/
	
}
