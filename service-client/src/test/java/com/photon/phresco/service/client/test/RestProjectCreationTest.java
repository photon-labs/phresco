package com.photon.phresco.service.client.test;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;

public class RestProjectCreationTest implements ServiceConstants {
    
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
   public void createProject() throws PhrescoException {
       ProjectInfo projectInfo = new ProjectInfo();
       projectInfo.setTechId("tech-php");
       projectInfo.setCode("php-blog");
       projectInfo.setVersion("1.0");
       projectInfo.setPilotProjectName("phpblog");
       Technology technology = new Technology();
       technology.setId("tech-php");
       projectInfo.setTechnology(technology);
       RestClient<ProjectInfo> customersClient = serviceManager.getRestClient("/project");
       ClientResponse response = customersClient.create(projectInfo, "application/zip", MediaType.APPLICATION_JSON);
       assertNotNull(response);
   }
}
