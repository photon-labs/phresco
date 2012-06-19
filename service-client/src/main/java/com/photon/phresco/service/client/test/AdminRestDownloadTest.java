package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.ApplyTo;
import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadGroup;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.PlatformInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class AdminRestDownloadTest {
	
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
    public void testGetDownloadInfo() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<DownloadGroup> downloadGroupClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DOWNLOADS);
            downloadGroupClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<DownloadGroup>> genericType = new GenericType<List<DownloadGroup>>() {};
            List<DownloadGroup> list =downloadGroupClient.get(genericType);
            for (DownloadGroup downloadGroup : list) {
                System.out.println("name == " + downloadGroup.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
   
    @Ignore
    public void testPostDownloadInfo() throws PhrescoException {
			DownloadGroup downloadGroup = new DownloadGroup();
			List<DownloadGroup> downloadGroups = new ArrayList<DownloadGroup>();
			downloadGroup.setDescription("group of modules");
			downloadGroup.setName("Module Group");
			Set<DownloadInfo> downloadInfos = new HashSet<DownloadInfo>();
			DownloadInfo downloadInfo = new DownloadInfo();
			downloadInfo.setCreationDate(new Date());
			downloadInfo.setDescription("downloadInfo");
			downloadInfo.setFileName("apptypes");
			downloadInfo.setFileSize(23.22);
			downloadInfo.setName("downloads");
			Set<Technology> technologies = new HashSet<Technology>();
			Technology technology = new Technology();
			technology.setAppTypeId("PHP");
			technology.setCreationDate(new Date());
			technology.setDescription("PHP");
			technology.setEmailSupported(true);
			technology.setGlobal(true);
			technologies.add(technology);
			downloadInfo.setAppliesTo(technologies);
			Set<PlatformInfo> platformInfos = new HashSet<PlatformInfo>();
			PlatformInfo platforms = new PlatformInfo();
			platforms.setDescription("PHP PLATFORM");
			platforms.setId("dot.net");
			platformInfos.add(platforms);
			downloadInfo.setPlatforms(platformInfos);
			downloadInfos.add(downloadInfo);
			downloadGroup.setDownloadInfos(downloadInfos);
			downloadGroups.add(downloadGroup);
	
			serviceManager = ServiceClientFactory.getServiceManager(context);
			String downloadGroupjson = new Gson().toJson(downloadGroups);
			System.out.println(downloadGroupjson);
			RestClient<DownloadGroup> downloadGroupClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DOWNLOADS);
			// API creation ends
			downloadGroupClient.setType(MediaType.APPLICATION_JSON);
			downloadGroupClient.setAccept(MediaType.APPLICATION_JSON);
	
			// API initialization ends
			downloadGroupClient.create(downloadGroupjson);
			// API call ends

    }
    
    @Ignore
    public void testPutDownload() throws PhrescoException {
			DownloadGroup downloadGroup = new DownloadGroup();
			List<DownloadGroup> downloadGroups = new ArrayList<DownloadGroup>();
			downloadGroup.setDescription("Downloads");
			downloadGroup.setName("Module Group");
			downloadGroup.setId("2c90968936e9e5100136e9e56dc90003");
			Set<DownloadInfo> downloadInfos = new HashSet<DownloadInfo>();
			DownloadInfo downloadInfo = new DownloadInfo();
			downloadInfo.setCreationDate(new Date());
			downloadInfo.setDescription("downloadInfo");
			downloadInfo.setFileName("apptypes");
			downloadInfo.setFileSize(23.22);
			downloadInfo.setName("downloads");
			Set<Technology> technologies = new HashSet<Technology>();
			Technology technology = new Technology();
			technology.setAppTypeId("PHP");
			technology.setCreationDate(new Date());
			technology.setDescription("PHP");
			technology.setEmailSupported(true);
			technology.setGlobal(true);
			technologies.add(technology);
			downloadInfo.setAppliesTo(technologies);
			Set<PlatformInfo> platformInfos = new HashSet<PlatformInfo>();
			PlatformInfo platforms = new PlatformInfo();
			platforms.setDescription("PHP PLATFORM");
			platforms.setId("dot.net");
			platformInfos.add(platforms);
			downloadInfo.setPlatforms(platformInfos);
			downloadInfos.add(downloadInfo);
			downloadGroup.setDownloadInfos(downloadInfos);
			downloadGroups.add(downloadGroup);
			String downloadJson = new Gson().toJson(downloadGroups);
			serviceManager = ServiceClientFactory.getServiceManager(context);
			RestClient<DownloadGroup> downloadGroupClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DOWNLOADS);
			downloadGroupClient.setType(MediaType.APPLICATION_JSON);
			downloadGroupClient.setAccept(MediaType.APPLICATION_JSON);
			downloadGroupClient.update(downloadJson);
		}
    
    @Ignore
    public void testGetDownloadByID(){
    try {
			String id = "2c90968936e9e5100136e9e56dc90003";
			serviceManager = ServiceClientFactory.getServiceManager(context);
			RestClient<DownloadGroup> downloadInfoClient = serviceManager
					.getRestClient(RestResourceURIs.REST_API_DOWNLOADS + "/" + "2c90968936e9e5100136e9e56dc90003");
			downloadInfoClient.setType(MediaType.APPLICATION_JSON);
			GenericType<List<DownloadGroup>> genericType = new GenericType<List<DownloadGroup>>() {
			};
			List<DownloadGroup> list = downloadInfoClient.get(genericType);
			for (DownloadGroup downloadGroup : list) {
				System.out.println("name == " + downloadGroup.getName());
			}
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
	}
    
    
    @Ignore
    public void testPutDownloadByID() throws PhrescoException {
			DownloadGroup downloadGroup = new DownloadGroup();
			List<DownloadGroup> downloadGroups = new ArrayList<DownloadGroup>();
			downloadGroup.setDescription("Downloads");
			downloadGroup.setName("Module Group");
			downloadGroup.setId("2c90968936e9e5100136e9e56dc90003");
			Set<DownloadInfo> downloadInfos = new HashSet<DownloadInfo>();
			DownloadInfo downloadInfo = new DownloadInfo();
			downloadInfo.setCreationDate(new Date());
			downloadInfo.setDescription("downloadInfo");
			downloadInfo.setFileName("apptypes");
			downloadInfo.setFileSize(23.22);
			downloadInfo.setName("downloads");
			Set<Technology> technologies = new HashSet<Technology>();
			Technology technology = new Technology();
			technology.setAppTypeId("PHP");
			technology.setCreationDate(new Date());
			technology.setDescription("PHP");
			technology.setEmailSupported(true);
			technology.setGlobal(true);
			technologies.add(technology);
			downloadInfo.setAppliesTo(technologies);
			Set<PlatformInfo> platformInfos = new HashSet<PlatformInfo>();
			PlatformInfo platforms = new PlatformInfo();
			platforms.setDescription("PHP PLATFORM");
			platforms.setId("dot.net");
			platformInfos.add(platforms);
			downloadInfo.setPlatforms(platformInfos);
			downloadInfos.add(downloadInfo);
			downloadGroup.setDownloadInfos(downloadInfos);
			downloadGroups.add(downloadGroup);
			String downloadJson = new Gson().toJson(downloadGroups);
			serviceManager = ServiceClientFactory.getServiceManager(context);
			RestClient<DownloadGroup> downloadGroupClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DOWNLOADS);
			downloadGroupClient.setType(MediaType.APPLICATION_JSON);
			downloadGroupClient.setAccept(MediaType.APPLICATION_JSON);
			downloadGroupClient.update(downloadJson);
		}
   
    @Test
    public void testDeleteDownload() throws PhrescoException {
	    	String id = "2c90968936e9e5100136e9e56dc90003"; 
	    	serviceManager = ServiceClientFactory.getServiceManager(context);            
	        RestClient<DownloadGroup> downloadInfoClient = serviceManager.getRestClient(RestResourceURIs.REST_API_DOWNLOADS);
	        downloadInfoClient.delete(id);
    }
    
}
