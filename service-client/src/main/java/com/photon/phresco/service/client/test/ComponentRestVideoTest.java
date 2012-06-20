package com.photon.phresco.service.client.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestVideoTest {
	
	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}

    @Ignore
    public void testGetVideoInfo() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<VideoInfo> VideoInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_VIDEOS);
            VideoInfoClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>() {};
            List<VideoInfo> list = VideoInfoClient.get(genericType);
            for (VideoInfo VideoInfo : list) {
                System.out.println("VideoInfo Name == " + VideoInfo.getDescription());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void testPostVideoInfo() throws PhrescoException {
    	List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
    	VideoInfo videoInfo = new VideoInfo();
    	videoInfo.setCategories("MPEG");
    	videoInfo.setName("PHP VIDEO");
    	videoInfo.setImageurl("C:/php.mpeg");
    	videoInfo.setHelpText("Introduction to php Technology");
    	VideoType videoType = new VideoType();
    	videoType.setCodecs("Mpeg");
    	videoType.setType("MPEG");
    	videoType.setUrl("C:/php.mpeg");
    	Set<VideoType> videoTypes = new HashSet<VideoType>();
    	videoTypes.add(videoType);
    	videoInfo.setVideoList(videoTypes);
    	videoInfos.add(videoInfo);
    	serviceManager = ServiceClientFactory.getServiceManager(context);     
        RestClient<VideoInfo> VideoInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_VIDEOS);
         String videoJson = new Gson().toJson(videoInfos);
         System.out.println(videoJson);
         VideoInfoClient.setAccept(MediaType.APPLICATION_JSON);
         VideoInfoClient.setType(MediaType.APPLICATION_JSON);
         VideoInfoClient.create(videoJson);
    }
  
    
    
    //Updating a list of Objects
    @Ignore
    public void testPutVideoInfo() throws PhrescoException {
    	List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
    	VideoInfo videoInfo = new VideoInfo();
    	videoInfo.setId("2c909c4836e90f2e0136e91011b90003");
    	videoInfo.setCategories("MPEG");
    	videoInfo.setName("DRUPAL VIDEO");
    	videoInfo.setImageurl("C:/php.mpeg");
    	videoInfo.setHelpText("Introduction to php Technology");
    	VideoType videoType = new VideoType();
    	videoType.setCodecs("Mpeg");
    	videoType.setType("MPEG");
    	videoType.setUrl("C:/php.mpeg");
    	Set<VideoType> videoTypes = new HashSet<VideoType>();
    	videoTypes.add(videoType);
    	videoInfo.setVideoList(videoTypes);
    	videoInfos.add(videoInfo);
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        String VideoInfoJson = new Gson().toJson(videoInfos);
        RestClient<VideoInfo> VideoInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_VIDEOS);
        VideoInfoClient.setAccept(MediaType.APPLICATION_JSON);
        VideoInfoClient.setType(MediaType.APPLICATION_JSON);
        VideoInfoClient.update(VideoInfoJson);
    }
  
    @Ignore
    public void testGetVideoInfoByID() {
        try {
        	String id = "2c909c4836e911510136e911765c0003";
        	serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<VideoInfo> VideoInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_VIDEOS+"/" +id);
            VideoInfoClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>() {};
            List<VideoInfo> list = VideoInfoClient.get(genericType);
            for (VideoInfo VideoInfo : list) {
                System.out.println("name == " + VideoInfo.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    //Updating a single object
   
    @Ignore
    public void testPutVideoInfoById() throws PhrescoException {
    	List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
    	VideoInfo videoInfo = new VideoInfo();
    	videoInfo.setId("2c909c4836e90f2e0136e91011b90003");
    	videoInfo.setCategories("MPEG");
    	videoInfo.setName("Sharepoint VIDEO");
    	videoInfo.setImageurl("C:/php.mpeg");
    	videoInfo.setHelpText("Introduction to php Technology");
    	VideoType videoType = new VideoType();
    	videoType.setCodecs("Mpeg");
    	videoType.setType("MPEG");
    	videoType.setUrl("C:/php.mpeg");
    	Set<VideoType> videoTypes = new HashSet<VideoType>();
    	videoTypes.add(videoType);
    	videoInfo.setVideoList(videoTypes);
    	videoInfos.add(videoInfo);
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        String VideoInfoJson = new Gson().toJson(videoInfos);
        RestClient<VideoInfo> VideoInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_VIDEOS);
        VideoInfoClient.setAccept(MediaType.APPLICATION_JSON);
        VideoInfoClient.setType(MediaType.APPLICATION_JSON);
        VideoInfoClient.update(VideoInfoJson);
    }
    
  
    @Test
    public void testDeleteVideoInfo() throws PhrescoException {
    	String id = "2c909c4836e911510136e911765c0003";    
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<VideoInfo> VideoInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_VIDEOS);
        VideoInfoClient.delete(id);
    }

}
