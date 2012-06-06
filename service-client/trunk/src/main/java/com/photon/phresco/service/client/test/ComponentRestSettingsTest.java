package com.photon.phresco.service.client.test;

import java.util.ArrayList;
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
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.PossibleValues;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.Settings;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ComponentRestSettingsTest {
	
	public ServiceContext context = null;
	public ServiceManager serviceManager = null;
	
	@Before
	public void Initilaization() {
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}

    @Test
    public void testGetSettings() {
        try {
			serviceManager = ServiceClientFactory.getServiceManager(context);            
			RestClient<SettingsTemplate> settingsClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SETTING_TEMPLATES);
			settingsClient.setType(MediaType.APPLICATION_JSON);
			GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>() {};
			List<SettingsTemplate> list = settingsClient.get(genericType);
			for (SettingsTemplate  SettingsTemplate : list) {
			System.out.println("Setting Template id == " + SettingsTemplate.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void testPostSettings() throws PhrescoException {
    		PossibleValues possiblevalue = new PossibleValues();
    		Set<PossibleValues> possibleValues = new HashSet<PossibleValues>();
    		possiblevalue.setPossiblevalue("ht/hts");
    		possibleValues.add(possiblevalue);
    		Set<PropertyTemplate> propertytemplates = new HashSet<PropertyTemplate>();
    		PropertyTemplate propertytemplate = new PropertyTemplate();
    		propertytemplate.setCreationDate(new Date());
    		propertytemplate.setDataType("it");
    		propertytemplate.setiName("ime");
			propertytemplate.setIsRequired(true);
			propertytemplate.setKey("set");
			propertytemplate.setCreationDate(new Date());
			propertytemplate.setPossibleValues(possibleValues);
			propertytemplates.add(propertytemplate);
			ApplyTo applyto = new ApplyTo();
			Set<ApplyTo> applytos = new HashSet<ApplyTo>();
			applyto.setTechnology("PHP");
			applytos.add(applyto);
			SettingsTemplate settingTemplate = new SettingsTemplate();
			List<SettingsTemplate> settingTemplates = new ArrayList<SettingsTemplate>();
			settingTemplate.setName("Setting");
			settingTemplate.setDescription("MYFIELDs");
			settingTemplate.setCreationDate(new Date());
		    settingTemplate.setPropertyTemplates(propertytemplates);
			settingTemplate.setApplyTo(applytos);
			settingTemplates.add(settingTemplate);

            serviceManager = ServiceClientFactory.getServiceManager(context);   
            RestClient<SettingsTemplate> settingTemplateClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SETTING_TEMPLATES);
            String settingTemplateGroupjson = new Gson().toJson(settingTemplates);
            settingTemplateClient.setType(MediaType.APPLICATION_JSON);
            settingTemplateClient.setAccept(MediaType.APPLICATION_JSON);
            settingTemplateClient.create(settingTemplateGroupjson);
    }
    
    @Ignore
    public void testPutSettings() throws PhrescoException {
			PossibleValues possiblevalue = new PossibleValues();
			Set<PossibleValues> possibleValues = new HashSet<PossibleValues>();
			possiblevalue.setPossiblevalue("ht/hts");
			possibleValues.add(possiblevalue);
			Set<PropertyTemplate> propertytemplates = new HashSet<PropertyTemplate>();
			PropertyTemplate propertytemplate = new PropertyTemplate();
			propertytemplate.setCreationDate(new Date());
			propertytemplate.setDataType("it");
			propertytemplate.setiName("ime");
			propertytemplate.setIsRequired(true);
			propertytemplate.setKey("set");
			propertytemplate.setCreationDate(new Date());
			propertytemplate.setPossibleValues(possibleValues);
			propertytemplates.add(propertytemplate);
			ApplyTo applyto = new ApplyTo();
			Set<ApplyTo> applytos = new HashSet<ApplyTo>();
			applyto.setTechnology("PHP");
			applytos.add(applyto);
			SettingsTemplate settingTemplate = new SettingsTemplate();
			List<SettingsTemplate> settingTemplates = new ArrayList<SettingsTemplate>();
			settingTemplate.setName("Setting");
			settingTemplate.setDescription("MYFIELDs");
			settingTemplate.setCreationDate(new Date());
			settingTemplate.setId("2c90968936eeb66e0136eecf2f7f0007");
			settingTemplate.setPropertyTemplates(propertytemplates);
			settingTemplate.setApplyTo(applytos);
			settingTemplates.add(settingTemplate);
			
			serviceManager = ServiceClientFactory.getServiceManager(context);   
			RestClient<SettingsTemplate> settingTemplateClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SETTING_TEMPLATES);
			String settingTemplateGroupjson = new Gson().toJson(settingTemplates);
			
			//API creation ends
			settingTemplateClient.setType(MediaType.APPLICATION_JSON);
			settingTemplateClient.setAccept(MediaType.APPLICATION_JSON);
			//API initialization ends
			settingTemplateClient.update(settingTemplateGroupjson);
	    }
    
    
    
    @Ignore
    public void testGetSettingsTemplateByID() {
	        try {
	        String id = "2c90968936eeb66e0136eecf2f7f0007";
	        serviceManager = ServiceClientFactory.getServiceManager(context);            
	        RestClient<SettingsTemplate> settingsClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SETTING_TEMPLATES+"/" +id);
	        settingsClient.setType(MediaType.APPLICATION_JSON);
	        GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>() {};
	        List<SettingsTemplate> list = settingsClient.get(genericType);
	        for (SettingsTemplate SettingsTemplate : list) {
	        System.out.println("Setting Template id == " + SettingsTemplate.getName());
	       }
	      } catch (PhrescoException e) {
	            e.printStackTrace();
	        }
	    }
	    
    @Ignore
    public void testPutByIdSettings() throws PhrescoException {
	    	String id="2c90968936eeb66e0136eecf2f7f0007";
			PossibleValues possiblevalue = new PossibleValues();
			Set<PossibleValues> possibleValues = new HashSet<PossibleValues>();
			possiblevalue.setPossiblevalue("ht/hts");
			possibleValues.add(possiblevalue);
			
			Set<PropertyTemplate> propertytemplates = new HashSet<PropertyTemplate>();
			PropertyTemplate propertytemplate = new PropertyTemplate();
			propertytemplate.setCreationDate(new Date());
			propertytemplate.setDataType("it");
			propertytemplate.setiName("ime");
			propertytemplate.setIsRequired(true);
			propertytemplate.setKey("set");
			propertytemplate.setCreationDate(new Date());
			propertytemplate.setPossibleValues(possibleValues);
			propertytemplates.add(propertytemplate);
			
			ApplyTo applyto = new ApplyTo();
			Set<ApplyTo> applytos = new HashSet<ApplyTo>();
			applyto.setTechnology("PHP");
			applytos.add(applyto);
			
			SettingsTemplate settingTemplate = new SettingsTemplate();
			List<SettingsTemplate> settingTemplates = new ArrayList<SettingsTemplate>();
			settingTemplate.setName("Setting");
			settingTemplate.setDescription("MY");
			settingTemplate.setCreationDate(new Date());
			settingTemplate.setId(id);
			settingTemplate.setPropertyTemplates(propertytemplates);
			settingTemplate.setApplyTo(applytos);
			settingTemplates.add(settingTemplate);
			
			serviceManager = ServiceClientFactory.getServiceManager(context);   
			RestClient<SettingsTemplate> settingTemplateClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SETTING_TEMPLATES);
			String settingTemplateGroupjson = new Gson().toJson(settingTemplates);
			
			//API creation ends
			settingTemplateClient.setType(MediaType.APPLICATION_JSON);
			settingTemplateClient.setAccept(MediaType.APPLICATION_JSON);
			//API initialization ends
			settingTemplateClient.update(settingTemplateGroupjson);
	    }
    @Ignore
    public void testDeleteRoleById() throws PhrescoException {
	    	String id = "2c90968936eeb66e0136eecf2f7f0007";    
	    	serviceManager = ServiceClientFactory.getServiceManager(context);            
	        RestClient<SettingsTemplate> SettingsTemplateClient = serviceManager.getRestClient(RestResourceURIs.REST_API_SETTING_TEMPLATES);
	        SettingsTemplateClient.delete(id);
    	
        }
}


