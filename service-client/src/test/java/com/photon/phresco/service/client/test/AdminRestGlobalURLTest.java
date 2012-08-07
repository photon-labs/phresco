package com.photon.phresco.service.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.GlobalURL;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class AdminRestGlobalURLTest implements ServiceConstants{


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
    public void testCreateGlobalRUL() throws PhrescoException {
        List<GlobalURL> gURL = new ArrayList<GlobalURL>();
        GlobalURL url=new GlobalURL();
        url.setId("testURL");
        url.setUrl("TestURL");
        gURL.add(url);
        RestClient<GlobalURL> globalClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_GLOBALURL);
        ClientResponse clientResponse = globalClient.create(gURL);
        assertEquals(clientResponse.getStatus(), 200);
    }
    
    @Test
    public void testFindGlobalURL() throws PhrescoException {
        RestClient<GlobalURL> globalClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_GLOBALURL);
        GenericType<List<GlobalURL>> genericType = new GenericType<List<GlobalURL>>(){};
        List<GlobalURL> grl = globalClient.get(genericType);
        assertNotNull(grl);
    }
    
    @Test
    public void testUpdateGlobalURL() throws PhrescoException {
    	List<GlobalURL> gURL = new ArrayList<GlobalURL>();
    	GlobalURL global=new GlobalURL();
    	global.setId("testURL");
    	global.setUrl("TestURLUpdate");
    	gURL.add(global);
        RestClient<GlobalURL> globalClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_GLOBALURL);
        GenericType<List<GlobalURL>> genericType = new GenericType<List<GlobalURL>>(){};
        List<GlobalURL> grl = globalClient.update(gURL, genericType);
        
    }
    
    @Test
    public void testUpdateGlobalURLById() throws PhrescoException {
        String id = "testURL";
        GlobalURL global=new GlobalURL();
        global.setId("testURL");
        global.setUrl("TestURLUpdateById");
        RestClient<GlobalURL> globalClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_GLOBALURL);
        globalClient.setPath(id);
        GenericType<GlobalURL> genericType = new GenericType<GlobalURL>() {};
        globalClient.updateById(global, genericType);
    }
    
    @Test
    public void testDeleteGlobalURLById() throws PhrescoException {
        String id = "testURL";
        RestClient<GlobalURL> globalClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_GLOBALURL);
        globalClient.setPath(id);
        ClientResponse clientResponse = globalClient.deleteById();
        assertEquals(clientResponse.getStatus(), 200);
    }
}
