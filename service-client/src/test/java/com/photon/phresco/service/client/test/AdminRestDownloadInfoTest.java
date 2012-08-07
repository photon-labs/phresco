package com.photon.phresco.service.client.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

 public class AdminRestDownloadInfoTest implements ServiceConstants {
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
	 public void testCreateDownloadInfo() throws PhrescoException {
        List<DownloadInfo> DownloadInfos = new ArrayList<DownloadInfo>();
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setId("test-downloadinfo");
        downloadInfo.setName("Test customer");
        DownloadInfos.add(downloadInfo);
        RestClient<DownloadInfo> DownloadInfoClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
        ClientResponse clientResponse = DownloadInfoClient.create(DownloadInfos);
        assertNotNull(clientResponse);
    }
	
	    @Test
	    public void getDownloadInfos() throws PhrescoException {
	        RestClient<DownloadInfo> DownloadInfoClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
	        GenericType<List<DownloadInfo>> genericType = new GenericType<List<DownloadInfo>>(){};
	        List<DownloadInfo> DownloadInfos = DownloadInfoClient.get(genericType);
	        assertNotNull(DownloadInfos);
	    }
	    
	    @Test
	    public void getDownloadInfo() throws PhrescoException {
	        String downloadInfoId = "test-downloadinfo";
	        RestClient<DownloadInfo> DownloadInfoClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
	        DownloadInfoClient.setPath(downloadInfoId);
	        GenericType<DownloadInfo> genericType = new GenericType<DownloadInfo>(){};
	        DownloadInfo info = DownloadInfoClient.getById(genericType);
	        assertNotNull(info);
	    } 
	    
	    @Test
	    public void updateDownloadInfo() throws PhrescoException {
	        String downloadInfoId = "test-downloadinfo";
	        DownloadInfo downloadInfo = new DownloadInfo();
	        downloadInfo.setId("test-downloadinfo");
	        downloadInfo.setName("Test customer update");
	        RestClient<DownloadInfo> DownloadInfoClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
	        DownloadInfoClient.setPath(downloadInfoId);
	        GenericType<DownloadInfo> genericType = new GenericType<DownloadInfo>() {};
	        DownloadInfo info=DownloadInfoClient.updateById(downloadInfo, genericType);
	        assertNotNull(info); 
	    }
	    
	    @Test
	    public void deleteDownloadInfo() throws PhrescoException {
	        String downloadInfoId = "test-downloadinfo";
	        RestClient<DownloadInfo> DownloadInfoClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
	        DownloadInfoClient.setPath(downloadInfoId);
	        ClientResponse clientResponse = DownloadInfoClient.deleteById();
	        assertNotNull(clientResponse);
	    }
	}
