package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
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

public class ComponentRestModulesTest implements ServiceConstants{

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
	
	@Test
	public void getModules() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ModuleGroup> moduleGroupClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
    	moduleGroupClient.setPath(techId);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	List<ModuleGroup> modules = moduleGroupClient.get(genericType);
    	System.out.println("modules.size():" + modules.size());
    	if (modules != null) {
	    	for (ModuleGroup module : modules) {
				System.out.println("module IDs :" + module.getModuleId());
			}
    	}
    }
	
	@Test
	public void getCoreModules() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ModuleGroup> moduleGroupClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
    	moduleGroupClient.setPath(techId);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	List<ModuleGroup> modules = moduleGroupClient.get(genericType);
    	System.out.println("modules.size():" + modules.size());
    	if (modules != null) {
	    	for (ModuleGroup module : modules) {
	    		if (module.isCore()) {
	    			System.out.println("Core module IDs :" + module.getModuleId());
				}
			}
    	}
    }
	
	@Test
	public void getCustomModules() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ModuleGroup> moduleGroupClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
    	moduleGroupClient.setPath(techId);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	List<ModuleGroup> modules = moduleGroupClient.get(genericType);
    	System.out.println("modules.size():" + modules.size());
    	if (modules != null) {
	    	for (ModuleGroup module : modules) {
	    		if (!module.isCore()) {
	    			System.out.println("Custom module IDs :" + module.getModuleId());
				}
			}
    	}
    }
	
	@Test
	public void getDefaultCoreModules() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ModuleGroup> moduleGroupClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
    	moduleGroupClient.setPath(techId);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	List<ModuleGroup> modules = moduleGroupClient.get(genericType);
    	System.out.println("modules.size():" + modules.size());
    	if (modules != null) {
	    	for (ModuleGroup module : modules) {
	    		if (module.isCore() && module.isRequired()) {
	    			System.out.println("Default Core module IDs :" + module.getModuleId());
				}
			}
    	}
    }
	
	@Test
	public void getDefaultCustomModules() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ModuleGroup> moduleGroupClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
    	moduleGroupClient.setPath(techId);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	List<ModuleGroup> modules = moduleGroupClient.get(genericType);
    	System.out.println("modules.size():" + modules.size());
    	if (modules != null) {
	    	for (ModuleGroup module : modules) {
	    		if (!module.isCore() && module.isRequired()) {
	    			System.out.println("Default Custom IDs :" + module.getModuleId());
				}
			}
    	}
    }
}