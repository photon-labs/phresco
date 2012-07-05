package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestPilotTest implements ServiceConstants {

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
	public void getPilots() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ProjectInfo> pilotClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTSBYID);
    	pilotClient.setPath(techId);
    	GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};
    	List<ProjectInfo> pilotProjectInfos = pilotClient.get(genericType);
    	System.out.println("pilotProjectInfos.size():" + pilotProjectInfos.size());
    }
	
	@Test
	public void getPilotModules() throws PhrescoException {
		String techId = "tech-php";
    	RestClient<ProjectInfo> pilotClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_PILOTSBYID);
    	pilotClient.setPath(techId);
    	GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};
    	List<ProjectInfo> pilotProjectInfos = pilotClient.get(genericType);
    	System.out.println("pilotProjectInfo.size():" + pilotProjectInfos.size());
    	if (pilotProjectInfos != null) {
    		for (ProjectInfo pilotProjectInfo : pilotProjectInfos) {
				List<ModuleGroup> pilotModules = pilotProjectInfo.getTechnology().getModules();
				if (pilotModules != null) {
					for (ModuleGroup pilotModule : pilotModules) {
						System.out.println("pilotModule.getModuleId():" + pilotModule.getModuleId());
					}
				}
			}
    	}
    }
}