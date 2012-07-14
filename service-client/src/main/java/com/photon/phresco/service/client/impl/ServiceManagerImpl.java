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
package com.photon.phresco.service.client.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class ServiceManagerImpl implements ServiceManager, ServiceClientConstant, ServiceConstants, Constants {

    private static final Logger S_LOGGER = Logger.getLogger(ServiceManagerImpl.class);
    private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
    private EhCacheManager manager;
    
    private String serverPath = null;
    User userInfo = null;

	public ServiceManagerImpl(String serverPath) throws PhrescoException {
    	super();
    	this.serverPath = serverPath;
    }

    public ServiceManagerImpl(ServiceContext context) throws PhrescoException {
    	super();
    	init(context);
    	manager = new EhCacheManager();
    }
    
    public <E> RestClient<E> getRestClient(String contextPath) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getRestClient(String contextPath)" + contextPath);
        }
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append(serverPath);
    	builder.append(contextPath);
    	RestClient<E> restClient = new RestClient<E>(builder.toString());
    	restClient.addHeader(PHR_AUTH_TOKEN, userInfo.getToken());
    	
    	return restClient;
	}
    
    public User getUserInfo() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getUserInfo())");
        }
    	
		return userInfo;
	}

	public void setUserInfo(User userInfo) throws PhrescoException {
		this.userInfo = userInfo;
	}
	
	private void init(ServiceContext context) throws PhrescoException {
		this.serverPath = (String) context.get(SERVICE_URL);
    	String password = (String) context.get(SERVICE_PASSWORD);
		String username = (String) context.get(SERVICE_USERNAME);
		doLogin(username, password);
	}
	
    private void doLogin(String username, String password) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.doLogin(String username, String password)");
        }
    	
    	Credentials credentials = new Credentials(username, password); 
    	Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + "/login");
        resource.accept(MediaType.APPLICATION_JSON);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, credentials);
        GenericType<User> genericType = new GenericType<User>() {};
        userInfo = response.getEntity(genericType);
    }
    
    public List<VideoInfo> getVideoInfos() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getVideoInfos()");
        }
    	
    	RestClient<VideoInfo> videoInfosClient = getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
    	GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>(){};
    	
    	return videoInfosClient.get(genericType);
    }
    
    private List<ApplicationType> getApplicationTypesFromServer() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getApplicationTypesFromServer()");
        }
    	
    	RestClient<ApplicationType> appTypeClient = getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
		GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
		
		return appTypeClient.get(genericType);
    }
    
    public List<ApplicationType> getApplicationTypes() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getApplicationTypes()");
        }

    	List<ApplicationType> appInfoValues = manager.getAppInfo(userInfo.getLoginId()); 
    	try {	
    		if (CollectionUtils.isEmpty(appInfoValues)) {
    			appInfoValues = getApplicationTypesFromServer();
    			manager.addAppInfo(userInfo.getLoginId(), appInfoValues);
    		}
    	} catch(Exception e){
    		throw new PhrescoException(e);
    	}
    	
    	return appInfoValues;
	}
    
    public ApplicationType getApplicationType(String appTypeId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getApplicationType(String appTypeId)");
        }

        RestClient<ApplicationType> appTypeClient = getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
        appTypeClient.setPath(appTypeId);
        GenericType<ApplicationType> genericType = new GenericType<ApplicationType>(){};
        
        return appTypeClient.getById(genericType);
    }
    
    public ClientResponse createApplicationTypes(List<ApplicationType> appTypes) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.createApplicationTypes(List<ApplicationType> appTypes)");
        }
    	
    	RestClient<ApplicationType> newApp = getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
		ClientResponse clientResponse = newApp.create(appTypes);
		manager.addAppInfo(userInfo.getLoginId(), getApplicationTypesFromServer());
		
		return clientResponse;
    }
    
    public void updateApplicationTypes(ApplicationType appType, String appTypeId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.updateApplicationTypes(ApplicationType appType, String appTypeId)" + appTypeId);
        }
    	
    	RestClient<ApplicationType> editApptype = getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
    	editApptype.setPath(appTypeId);
		GenericType<ApplicationType> genericType = new GenericType<ApplicationType>() {};
		editApptype.updateById(appType, genericType);
		manager.addAppInfo(userInfo.getLoginId(), getApplicationTypesFromServer());
    }
    
    public ClientResponse deleteApplicationType(String appTypeId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.deleteApplicationType(String appTypeId)" + appTypeId);
        }
    	
	    RestClient<ApplicationType> deleteApptype = getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
	    deleteApptype.setPath(appTypeId);
	    ClientResponse clientResponse = deleteApptype.deleteById();
	    manager.addAppInfo(userInfo.getLoginId(), getApplicationTypesFromServer());
	    
	    return clientResponse;
    }
    
    public List<Server> getServers(String techId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClientgetServers(String techId)" + techId);
        }
    	
		RestClient<Server> serverClient = getRestClient(REST_API_COMPONENT + REST_API_SERVERS);
		serverClient.queryString(REST_QUERY_TECHID, techId);
		GenericType<List<Server>> genericType = new GenericType<List<Server>>(){};
		
		return serverClient.get(genericType);
	}
    
    public List<Database> getDatabases(String techId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getDatabases(String techId)" + techId);
        }
    	
		RestClient<Database> dbClient = getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
		dbClient.queryString(REST_QUERY_TECHID, techId);
		GenericType<List<Database>> genericType = new GenericType<List<Database>>(){};
		
		return dbClient.get(genericType);
	}
    
    public List<WebService> getWebServices(String techId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getWebServices(String techId)" + techId);
        }
    	
		RestClient<WebService> webServiceClient = getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
		webServiceClient.queryString(REST_QUERY_TECHID, techId);
		GenericType<List<WebService>> genericType = new GenericType<List<WebService>>(){};
		
		return webServiceClient.get(genericType);
	}
    
    public List<ProjectInfo> getPilots(String techId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getPilots(String techId)" + techId);
        }
    	
    	RestClient<ProjectInfo> pilotClient = getRestClient(REST_API_COMPONENT + REST_API_PILOTS);
    	pilotClient.queryString(REST_QUERY_TECHID, techId);
    	GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};
    	
    	return pilotClient.get(genericType);
    }
    
    public List<ModuleGroup> getModules(String techId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getModules(String techId)" + techId);
        }
    	
    	RestClient<ModuleGroup> moduleGroupClient = getRestClient(REST_API_COMPONENT + REST_API_MODULES);
    	Map<String, String> headers = new HashMap<String, String>();
    	headers.put(REST_QUERY_TECHID, techId);
    	headers.put(REST_QUERY_TYPE, REST_QUERY_TYPE_MODULE);
    	moduleGroupClient.queryStrings(headers);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	
    	return moduleGroupClient.get(genericType);
    }
    
    public List<ModuleGroup> getJSLibs(String techId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getJSLibs(String techId)" + techId);
        }
    	
    	RestClient<ModuleGroup> jsLibClient = getRestClient(REST_API_COMPONENT + REST_API_MODULES);
    	Map<String, String> headers = new HashMap<String, String>();
    	headers.put(REST_QUERY_TECHID, techId);
    	headers.put(REST_QUERY_TYPE, REST_QUERY_TYPE_JS);
    	jsLibClient.queryStrings(headers);
    	GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
    	
    	return jsLibClient.get(genericType);
    }
    
    public List<Customer> getCustomers() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getCustomers()");
        }
        
        RestClient<Customer> customersClient = getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        GenericType<List<Customer>> genericType = new GenericType<List<Customer>>(){};
        
        return customersClient.get(genericType);
    }
    
    public Customer getCustomer(String customerId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getCustomer(String customerId)" + customerId);
        }
        
        RestClient<Customer> customersClient = getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        customersClient.setPath(customerId);
        GenericType<Customer> genericType = new GenericType<Customer>(){};
        
        return customersClient.getById(genericType);
    }
    
    public ClientResponse createCustomers(List<Customer> customers) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.createCustomers(List<Customer> customers)");
        }
        
        RestClient<Customer> customersClient = getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        
        return customersClient.create(customers);
    }
    
    public void updateCustomer(Customer customer, String customerId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.updateCustomer(Customer customer, String customerId)" + customerId);
        }
        
        RestClient<Customer> customersClient = getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        customersClient.setPath(customerId);
        GenericType<Customer> genericType = new GenericType<Customer>() {};
        customersClient.updateById(customer, genericType);
    }
    
    public ClientResponse deleteCustomer(String customerId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.deleteCustomer(String customerId)" + customerId);
        }
        
        RestClient<Customer> customersClient = getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        customersClient.setPath(customerId);
        
        return customersClient.deleteById();
    }
    
    // COMPONENTS STARTS HERE
       //ConfigTemplates
    public List<SettingsTemplate> getSettings() throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getSettings()");
        }
        
        RestClient<SettingsTemplate> settingClient = getRestClient("/components" + REST_API_SETTINGS);
        GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>(){};
        
        return settingClient.get(genericType);
    }
    
    public SettingsTemplate getSettings(String settingsId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getSettings(String settingsId)" + settingsId);
        }
        
        RestClient<SettingsTemplate> settingClient = getRestClient("/components" + REST_API_SETTINGS);
        settingClient.setPath(settingsId);
        GenericType<SettingsTemplate> genericType = new GenericType<SettingsTemplate>(){};
        
        return settingClient.getById(genericType);
    }
    
    public ClientResponse createSettings(List<SettingsTemplate> settings) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.createSettings(List<SettingTemplate> settings)");
        }
        
        RestClient<SettingsTemplate> settingsClient = getRestClient("/components" + REST_API_SETTINGS);
        
        return settingsClient.create(settings);
    }
    
     //Pilot Projects
    public List<ProjectInfo> getPilotProjects() throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entered into RestClient.getPilotProjects(List<ProjectInfo> proInfo)");
    	}

    	RestClient<ProjectInfo> pilotClient = getRestClient("/components" + REST_API_PILOTS);
    	GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};

    	return pilotClient.get(genericType); 
    }
    
    public ProjectInfo getPilotProject(String projectId) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entered into RestClient.getPilotsProjects(List<ProjectInfo> proInfo)");
    	}
    	
    	RestClient<ProjectInfo> pilotClient = getRestClient("/components" + REST_API_PILOTS );
    	pilotClient.setPath(projectId);
    	GenericType<ProjectInfo> genericType = new GenericType<ProjectInfo>(){};
    	
    	return pilotClient.getById(genericType);
    }
    
    public ClientResponse createPilotProject(List<ProjectInfo> proInfo) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.createPilotProjects(List<ProjectInfo> proInfo)");
        }
        
        RestClient<ProjectInfo> pilotClient = getRestClient("/components" + REST_API_PILOTS);
        
        return pilotClient.create(proInfo);
    }
    
    public void updatePilotProject(ProjectInfo projectInfo, String projectId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.updatePilotProjects(ProjectInfo projectInfo, String id)" + projectId);
        }
        
        RestClient<ProjectInfo> pilotproClient = getRestClient("/components" + REST_API_PILOTS);
        pilotproClient.setPath(projectId);
        GenericType<ProjectInfo> genericType = new GenericType<ProjectInfo>() {};
        pilotproClient.updateById(projectInfo, genericType);
    }
    
    public ClientResponse deletePilotProject(String projectId) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.deletePilotProjects(String id)" + projectId);
        }
        
        RestClient<ProjectInfo> pilotproClient = getRestClient("/components" + REST_API_PILOTS);
        pilotproClient.setPath(projectId);
        
        return pilotproClient.deleteById();
    }
    
   
    
    
    //ADMIN STARTS HERE
    //Roles
    public List<Role> getRoles() throws PhrescoException {
    	  if (isDebugEnabled) {
              S_LOGGER.debug("Entered into RestClient.createroles(List<Roles> role)");
          }
    	
    	RestClient<Role> roleClient = getRestClient(REST_API_ADMIN + REST_API_ROLES);
        GenericType<List<Role>> genericType = new GenericType<List<Role>>(){};
        return roleClient.get(genericType);	
    }
    
    public Role getRole(String roleId) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entered into RestClient.getPilotsProjects(List<ProjectInfo> proInfo)");
    	}
    	
    	RestClient<Role> roleClient = getRestClient(REST_API_ADMIN + REST_API_ROLES);
    	roleClient.setPath(roleId);
    	GenericType<Role> genericType = new GenericType<Role>(){};
    	return roleClient.getById(genericType);
    }
    
    public ClientResponse createRoles(List<Role> role) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entered into RestClient.createroles(List<Role> role)");
    	}	
    	RestClient<Role> roleClient = getRestClient(REST_API_ADMIN + REST_API_ROLES);
    	return roleClient.create(role);
    }
    
    //Downloads
    public List<DownloadInfo> getDownloads() throws PhrescoException {
    	if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.getDownloadInfo(List<DownloadInfo> downloadInfo)");
        }
    	
    	RestClient<DownloadInfo> downloadClient = getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
    	GenericType<List<DownloadInfo>> genericType = new GenericType<List<DownloadInfo>>(){};
    	return downloadClient.get(genericType);
    }
    
    public DownloadInfo getDownload(String id) throws PhrescoException {
    	if(isDebugEnabled){
    		S_LOGGER.debug("Entered into Restclient.getDownloadInfo(List<downloadInfo>) downloadInfo");
    	}
    	RestClient<DownloadInfo> downloadClient = getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
    	downloadClient.setPath(id);
    	GenericType<DownloadInfo> genericType = new GenericType<DownloadInfo>(){};
    	return downloadClient.getById(genericType);
    }
    
    public ClientResponse createDownload(List<DownloadInfo> downloadInfo) throws PhrescoException{
    	if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.createDownloadInfo(List<DownloadInfo> downloadInfo)");
        }
    	RestClient<DownloadInfo> downloadClient = getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
    	return downloadClient.create(downloadInfo);
    }
    
    public void updateDownload(DownloadInfo downloadInfo, String id) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.updateDownload(,DownloadInfo downloadInfo, String id)" + id);
        }
        
        RestClient<DownloadInfo> downloadClient = getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
        downloadClient.setPath(id);
        GenericType<DownloadInfo> genericType = new GenericType<DownloadInfo>() {};
        downloadClient.updateById(downloadInfo, genericType);
    }
    
    public ClientResponse deleteDownloadInfo(String id) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entered into RestClient.deleteDownload(String id)" + id);
        }
        
        RestClient<DownloadInfo> downloadClient = getRestClient(REST_API_ADMIN + REST_API_DOWNLOADS);
        downloadClient.setPath(id);
        
        return downloadClient.deleteById();
    }
}