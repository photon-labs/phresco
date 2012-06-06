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
import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.GenericType;

public class AdminRestRoleTest {
	
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
    public void testGetRole() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Role> roleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_ROLES);
            roleClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Role>> genericType = new GenericType<List<Role>>() {};
            List<Role> list = roleClient.get(genericType);
            for (Role Role : list) {
                System.out.println("name == " + Role.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    @Ignore
    public void testPostRole() throws PhrescoException {
    	Permission permission = new Permission();
         Set<Permission> permissions = new HashSet<Permission>();
         permission.setCreationDate(new Date());
         permission.setDescription("Managing All Activities");
         permission.setName("Software");
         permissions.add(permission);
         Role role = new Role();
         role.setCreationDate(new Date());
         role.setGlobal(true);
         role.setName("Runner");
         role.setSystem(true);
         role.setPermissions(permissions);
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         List<Role> roles = new ArrayList<Role>();
         roles.add(role);
         String customerJson = new Gson().toJson(roles);
         RestClient<Role> roleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_ROLES);
         roleClient.setType(MediaType.APPLICATION_JSON);
         roleClient.setAccept(MediaType.APPLICATION_JSON);
         roleClient.setType(MediaType.APPLICATION_JSON);
         roleClient.create(customerJson);
    }
   
    @Test
    public void testPutRole() throws PhrescoException {
    	Permission permission = new Permission();
        Set<Permission> permissions = new HashSet<Permission>();
        permission.setCreationDate(new Date());
        permission.setDescription("Managing All Activities");
        permission.setName("Software");
        permissions.add(permission);
        Role role = new Role();
        role.setCreationDate(new Date());
        role.setGlobal(true);
        role.setName("mangggg");
        role.setSystem(true);
        role.setPermissions(permissions);
        role.setDescription("Developer");
        role.setId("2c909a0a36e815dc0136e81b29860006");
        serviceManager = ServiceClientFactory.getServiceManager(context);            
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        String customerJson = new Gson().toJson(roles);
        RestClient<Role> roleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_ROLES );
        roleClient.setType(MediaType.APPLICATION_JSON);
        roleClient.setAccept(MediaType.APPLICATION_JSON);
        roleClient.setType(MediaType.APPLICATION_JSON);
        roleClient.update(customerJson);
    }
    
   
    @Ignore
    public void testGetRoleByID() {
        try {
        	String id = "2c909c4836df90080136df9060390003";
        	serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<Role> roleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_ROLES+"/" +id);
            roleClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<Role>> genericType = new GenericType<List<Role>>() {};
            List<Role> list = roleClient.get(genericType);
            for (Role Role : list) {
                System.out.println("name == " + Role.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    @Ignore
    public void testPutRoleById() throws PhrescoException {
    	String id = "2c909c4836df90080136df9060390003";
    	Permission permission = new Permission();
         Set<Permission> permissions = new HashSet<Permission>();
         permission.setCreationDate(new Date());
         permission.setDescription("Managing Activities");
         permission.setName("Auditor");
         permissions.add(permission);
         Role role = new Role();
         role.setCreationDate(new Date());
         role.setGlobal(true);
         role.setName("Employee");
         role.setSystem(true);
         role.setDescription("GENERAL");
         role.setId("2c909a0a36e2c6980136e2d087cc0003");
         role.setPermissions(permissions);
    
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         List<Role> roles = new ArrayList<Role>();
         roles.add(role);
         String customerJson = new Gson().toJson(roles);
         RestClient<Role> roleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_ROLES );
         roleClient.setType(MediaType.APPLICATION_JSON);
         roleClient.setAccept(MediaType.APPLICATION_JSON);
         roleClient.setType(MediaType.APPLICATION_JSON);
         roleClient.update(customerJson);
    }
    
    
    @Ignore
    public void testDeleteRoleById() throws PhrescoException {
    	String id = "2c909a0a36e45d3e0136e45d7ae80003";    
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<Role> roleClient = serviceManager.getRestClient(RestResourceURIs.REST_API_ROLES);
        roleClient.delete(id);
    	
        }
    
}
