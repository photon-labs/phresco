package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestjsLibsTest implements ServiceConstants {

	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() throws PhrescoException {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
	}
	
	@Ignore
	public void getJSLibs() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ModuleGroup> jsLibClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_JSBYID);
    	jsLibClient.setPath(techId);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	List<ModuleGroup> jsLibs = jsLibClient.get(genericType);
    	System.out.println("jsLibs.size():" + jsLibs.size());
    	if (jsLibs != null) {
    		for (ModuleGroup jsLib : jsLibs) {
				System.out.println("jsLib.getModuleId():" + jsLib.getModuleId());
			}
    	}
    }
}