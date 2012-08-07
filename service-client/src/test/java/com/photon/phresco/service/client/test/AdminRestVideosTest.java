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

package com.photon.phresco.service.client.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
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
	public void testCreateVideoInfos() throws PhrescoException {
		List<VideoInfo> videolist = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo("About phresco", "intro about phresoc", null, null, null, null);
		videolist.add(info);
		info.setId("TestvideoInfo"); 
		videolist.indexOf("Testvideo");
		RestClient<VideoInfo> videoInfoclient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
		 ClientResponse clientResponse = videoInfoclient.create(videolist);
		 assertNotNull(clientResponse);
		
	}
    
	@Test
    public void getVideoInfo() throws PhrescoException {
        String VideoInfoId = "TestvideoInfo";
        RestClient<VideoInfo> videoInfoclient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
        videoInfoclient.setPath(VideoInfoId);
        GenericType<VideoInfo> genericType = new GenericType<VideoInfo>(){};
        VideoInfo Info = videoInfoclient.getById(genericType);
        assertNotNull(Info);
    }
	
	@Test
	public void FindVideoInfos() throws PhrescoException {
		RestClient<VideoInfo> videoInfoclient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
		GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>(){};
        List<VideoInfo> VideoInfos = videoInfoclient.get(genericType);
		assertNotNull(VideoInfos);
	}
	
	
	@Test
	public void UpdateVideoInfos() throws PhrescoException {
		String VideoInfoId ="TestvideoInfo";
		List<VideoInfo> videolist = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo("About phresco ", "intro about phresco", null, null, null, null);
		info.setId("TestvideoInfo");
		videolist.add(info);
		RestClient<VideoInfo> videoInfoclient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
		videoInfoclient.setPath(VideoInfoId); 
        GenericType<VideoInfo> genericType = new GenericType<VideoInfo>() {};
        videoInfoclient.updateById(info, genericType);
	}

	@Ignore
	public void testDeleteVideosInfos() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}


	
	
	@Test
	public void getVideoInfos() throws PhrescoException  {
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
	@Test
	public void DeleteVideoInfo() throws PhrescoException  {
	String VideoInfoId = "TestvideoInfo";
	RestClient<VideoInfo> videoInfosClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
	videoInfosClient.setPath(VideoInfoId);
	ClientResponse clientResponse = videoInfosClient.deleteById();
	assertNotNull(clientResponse);
	   }    	
	    	
    	
}



