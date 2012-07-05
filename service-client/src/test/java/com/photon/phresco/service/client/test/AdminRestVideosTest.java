package com.photon.phresco.service.client.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.GenericType;

public class AdminRestVideosTest implements ServiceConstants {

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
	public void getVideoInfos() throws PhrescoException {
    	RestClient<VideoInfo> videoInfosClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
    	GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>(){};
    	List<VideoInfo> videoInfos = videoInfosClient.get(genericType);
    	System.out.println("videoInfos.size():" + videoInfos.size());
    	if (videoInfos != null) {
	    	for (VideoInfo videoInfo : videoInfos) {
	    		System.out.println("videoInfo.getName():" + videoInfo.getName());
			}
    	}
    }
}
