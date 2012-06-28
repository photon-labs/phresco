package com.photon.phresco.service.client.impl;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Credentials;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class ServiceManagerImpl implements ServiceManager, ServiceClientConstant {

    private static final Logger S_LOGGER = Logger.getLogger(ServiceManagerImpl.class);

    
    private String serverPath = null;
    UserInfo userInfo = null;

	public ServiceManagerImpl(String serverPath) throws PhrescoException {
    	super();
    	this.serverPath = serverPath;
    }

    public ServiceManagerImpl(ServiceContext context) throws PhrescoException {
    	super();
    	init(context);
    }
    
    public <E> RestClient<E> getRestClient(String contextPath) throws PhrescoException {
    	S_LOGGER.debug("Entered into RestClient.getRestClient(String contextPath)");
		StringBuilder builder = new StringBuilder();
		builder.append(serverPath);
		builder.append(contextPath);
		return new RestClient<E>(builder.toString());	
	}

    public UserInfo getUserInfo() throws PhrescoException {
    	S_LOGGER.debug("Entered into RestClient.getUserInfo())");
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) throws PhrescoException {
		this.userInfo = userInfo;
	}
	
	private void init(ServiceContext context) throws PhrescoException {
		this.serverPath = (String) context.get(SERVICE_URL);
    	String password = (String) context.get(SERVICE_PASSWORD);
		String username = (String) context.get(SERVICE_USERNAME);
		doLogin(username, password);
	}
	
    private void doLogin(String username, String password) throws PhrescoException {
    	S_LOGGER.debug("Entered into RestClient.doLogin(String username, String password)");
    	Credentials credentials = new Credentials(username, password); 
    	Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + "/login");
        resource.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, credentials);
        GenericType<UserInfo> genericType = new GenericType<UserInfo>() {};
        userInfo = response.getEntity(genericType);
    }

}