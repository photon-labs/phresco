package com.photon.phresco.service.client.test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestSettingsTest implements ServiceConstants {
	
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
	public void testCreateSettings() throws PhrescoException {
		List<SettingsTemplate> settingTemplate=new ArrayList<SettingsTemplate>();
		SettingsTemplate st=new SettingsTemplate();
		st.setId("testSetting");
		st.setType("server");
		st.setCustomerId("photon");
		settingTemplate.add(st);
        RestClient<SettingsTemplate> newSetting = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SETTINGS);
        ClientResponse clientResponse = newSetting.create(settingTemplate);
    }
	

	@Test
    public void testGetSettings() throws PhrescoException {
    	RestClient<SettingsTemplate> stClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SETTINGS);
		GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>(){};
		List<SettingsTemplate> settingTemplate = stClient.get(genericType);
		assertNotNull(settingTemplate);
    }
	

	@Test
	public void testUpdateSettingTemplate() throws PhrescoException{
		RestClient<SettingsTemplate> stClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SETTINGS);
	    List<SettingsTemplate> settingTemp = new ArrayList<SettingsTemplate>();
	    SettingsTemplate st = new SettingsTemplate();
	    st.setId("testSetting");
	    st.setType("server update");
	    st.setCustomerId("photon");
	    settingTemp.add(st);
	    GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>() {};
	    List<SettingsTemplate> clientResponse = stClient.update(settingTemp, genericType);
	}
	
	@Test
    public void testGetSettingsById() throws PhrescoException {
		String Id = "testSetting";
    	RestClient<SettingsTemplate> stClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SETTINGS);
    	stClient.queryString(REST_API_PATH_PARAM_ID, Id);
		GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>(){};
		List<SettingsTemplate> settingsTemplate = stClient.get(genericType);
		assertNotNull(settingsTemplate);
    }
	
	@Test
	public void testUpdateSettingsById() throws PhrescoException {
    	RestClient<SettingsTemplate> stClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SETTINGS);
		GenericType<SettingsTemplate> genericType = new GenericType<SettingsTemplate>(){};
		SettingsTemplate st=new SettingsTemplate();
		st.setId("testSetting");
		st.setType("server updateById");
		st.setCustomerId("photon");
		stClient.setPath("testSetting");
		stClient.updateById(st, genericType);
    }
	
	@Test
	public void testDeleteSetting() throws PhrescoException {
        RestClient<SettingsTemplate> deleteSettingTemplate = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SETTINGS);
        deleteSettingTemplate.setPath("testSetting");
        ClientResponse clientResponse = deleteSettingTemplate.deleteById();
    }
	

}
