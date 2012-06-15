package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider;

public class ComponentRestModuleTest {
	
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
    public void testGetModule() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<ModuleGroup> ServerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_MODULES);
            ServerClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>() {};
            List<ModuleGroup> list = ServerClient.get(genericType);
            for (ModuleGroup moduleGroup : list) {
                System.out.println("ModuleGroup GroupId == " + moduleGroup.getGroupId());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    @Ignore
    public void testPostModule() throws PhrescoException {
		List<ModuleGroup> modulegroups = new ArrayList<ModuleGroup>();
		ModuleGroup modulegroup = new ModuleGroup();
		modulegroup.setGlobal(true);
		modulegroup.setGroupId("com");
		modulegroup.setArtifactId("photon.phresco");
		modulegroup.setType("String");
		Set<Documentation> documentations = new HashSet<Documentation>();
		Documentation documentation = new Documentation();
		documentation.setContent("Content");
		documentation.setUrl("www.google.com");
		documentations.add(documentation);
		Set<Module> modules = new HashSet<Module>();
		Module module = new Module();
		module.setArtifactId("artifact");
		module.setContentType("content");
		module.setName("name");
		module.setStatus(123);
		Set<ModuleGroup> dependentModules = new HashSet<ModuleGroup>();
		module.setDependentModules(dependentModules);
		modules.add(module);
		modulegroup.setVersions(modules);
		modulegroup.setDocs(documentations);
		modulegroups.add(modulegroup);
		serviceManager = ServiceClientFactory.getServiceManager(context);   
		String moduleJson = new Gson().toJson(modulegroups);
		RestClient<ModuleGroup> moduleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_MODULES);
		moduleClient.setAccept(MediaType.APPLICATION_JSON);
		moduleClient.setType(MediaType.APPLICATION_JSON);
		moduleClient.create(moduleJson);
       
    }
    
    @Ignore
    public void testPutModule() throws PhrescoException {
    	List<ModuleGroup> modulegroups = new ArrayList<ModuleGroup>();
		ModuleGroup modulegroup = new ModuleGroup();
		modulegroup.setName("ModuleGroup");
		modulegroup.setGlobal(true);
		modulegroup.setGroupId("com");
		modulegroup.setArtifactId("photon.phresco");
		modulegroup.setType("String");
		modulegroup.setId("2c909aa236eec0f30136eec108460003");
		Set<Documentation> documentations = new HashSet<Documentation>();
		Documentation documentation = new Documentation();
		documentation.setContent("Content");
		documentation.setUrl("www.google.com");
		documentations.add(documentation);
		Set<Module> modules = new HashSet<Module>();
		Module module = new Module();
		module.setArtifactId("artifact");
		module.setContentType("content");
		module.setName("name");
		module.setStatus(123);
		Set<ModuleGroup> dependentModules = new HashSet<ModuleGroup>();
		module.setDependentModules(dependentModules);
		modules.add(module);
		modulegroup.setVersions(modules);
		modulegroup.setDocs(documentations);
		modulegroups.add(modulegroup);
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
		String moduleJson = new Gson().toJson(modulegroups);
		RestClient<ModuleGroup> moduleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_MODULES );
		moduleClient.setType(MediaType.APPLICATION_JSON);
		moduleClient.setAccept(MediaType.APPLICATION_JSON);
		moduleClient.update(moduleJson);
    }
    
    @Ignore
    public void testPutModuleById() throws PhrescoException {
    	String id = "2c909aa236eec0f30136eeccc70c0006";
    	List<ModuleGroup> modulegroups = new ArrayList<ModuleGroup>();
		ModuleGroup modulegroup = new ModuleGroup();
		modulegroup.setName("ModuleGroup");
		modulegroup.setGlobal(true);
		modulegroup.setGroupId("com");
		modulegroup.setArtifactId("photon.phresco");
		modulegroup.setType("String");
		modulegroup.setId(id);
		Set<Documentation> documentations = new HashSet<Documentation>();
		Documentation documentation = new Documentation();
		documentation.setContent("Content");
		documentation.setUrl("www.google.com");
		documentations.add(documentation);
		Set<Module> modules = new HashSet<Module>();
		Module module = new Module();
		module.setArtifactId("artifact");
		module.setContentType("content");
		module.setName("name");
		module.setStatus(123);
		Set<ModuleGroup> dependentModules = new HashSet<ModuleGroup>();
		module.setDependentModules(dependentModules);
		modules.add(module);
		modulegroup.setVersions(modules);
		modulegroup.setDocs(documentations);
		modulegroups.add(modulegroup);
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
		String moduleJson = new Gson().toJson(modulegroups);
		RestClient<ModuleGroup> moduleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_MODULES );
		moduleClient.setType(MediaType.APPLICATION_JSON);
		moduleClient.setAccept(MediaType.APPLICATION_JSON);
		moduleClient.update(moduleJson);
    
    }
    
    @Ignore
    public void testDeleteModuleById() throws PhrescoException {
    	String id = "2c909aa236eec0f30136eec108460003";    
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<ModuleGroup> moduleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_MODULES);
        moduleClient.delete(id);
    	
    }
    

}
