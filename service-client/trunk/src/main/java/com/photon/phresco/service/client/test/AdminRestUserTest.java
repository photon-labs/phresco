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
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.GenericType;

public class AdminRestUserTest {
	
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
    public void testGetUser() {
        try {
            serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<User> customerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_USERS);
            customerClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<User>> genericType = new GenericType<List<User>>() {};
            List<User> list = customerClient.get(genericType);
            for (User User : list) {
                System.out.println("name == " + User.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    @Ignore
    public void testPostUser() throws PhrescoException {
    	Permission permission = new Permission();
         Set<Permission> permissions = new HashSet<Permission>();
         permission.setCreationDate(new Date());
         permission.setDescription("sugumar");
         permission.setName("Software");
         permissions.add(permission);
         Set<Role> roles=new HashSet<Role>();
         Role role = new Role();
         role.setCreationDate(new Date());
         role.setGlobal(true);
         role.setName("sugumar");
         role.setSystem(true);
         role.setPermissions(permissions);
         roles.add(role);
         User user=new User();
         user.setName("sugumar");
         user.setCreationDate(new Date());
         user.setDescription("manager");
         user.setRoles(roles);
         List<User> users = new ArrayList<User>();
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         users.add(user);
         String customerJson = new Gson().toJson(users);
         System.out.print(user);
         RestClient<User> userClient = serviceManager.getRestClient(RestResourceURIs.REST_API_USERS);
         userClient.setType(MediaType.APPLICATION_JSON);
         userClient.setAccept(MediaType.APPLICATION_JSON);
         userClient.setType(MediaType.APPLICATION_JSON);
         userClient.create(customerJson);
    }
    
    @Ignore
    public void testPutUser() throws PhrescoException {
    	Permission permission = new Permission();
        Set<Permission> permissions = new HashSet<Permission>();
        permission.setCreationDate(new Date());
        permission.setDescription("Managing All Activities");
        permission.setName("Software");
        permissions.add(permission);
        Set<Role> roles=new HashSet<Role>();
        Role role = new Role();
        role.setCreationDate(new Date());
        role.setGlobal(true);
        role.setName("Kumar");
        role.setSystem(true);
        role.setPermissions(permissions);
        role.setDescription("Developer");
        roles.add(role);
        User user=new User();
        user.setName("dunksten");
        user.setCreationDate(new Date());
        user.setDescription("done");
        user.setRoles(roles);
        user.setId("2c909a0a36e815dc0136e81bc4270008");
        List<User> users = new ArrayList<User>();
        serviceManager = ServiceClientFactory.getServiceManager(context);            
        users.add(user);
        String customerJson = new Gson().toJson(users);
        System.out.print(user);
        RestClient<User> userClient = serviceManager.getRestClient(RestResourceURIs.REST_API_USERS);
        userClient.setType(MediaType.APPLICATION_JSON);
        userClient.setAccept(MediaType.APPLICATION_JSON);
        userClient.setType(MediaType.APPLICATION_JSON);
        userClient.update(customerJson);
    }
    
    @Ignore
    public void testGetUserByID() {
        try {
        	String id = "2c909a0a36e815dc0136e81bc4270008";
        	serviceManager = ServiceClientFactory.getServiceManager(context);            
            RestClient<User> userClient = serviceManager.getRestClient(RestResourceURIs.REST_API_USERS+"/" +id);
            userClient.setType(MediaType.APPLICATION_JSON);
            GenericType<List<User>> genericType = new GenericType<List<User>>() {};
            List<User> list = userClient.get(genericType);
            for (User User : list) {
                System.out.println("name == " + User.getName());
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testPutUserById() throws PhrescoException {
    	String id = "2c909a0a36e8228b0136e8367d3a000b";
    	Permission permission = new Permission();
         Set<Permission> permissions = new HashSet<Permission>();
         permission.setCreationDate(new Date());
         permission.setDescription("Managing Activities");
         permission.setName("Auditor");
         permissions.add(permission);
         Set<Role> roles=new HashSet<Role>();
         Role role = new Role();
         role.setCreationDate(new Date());
         role.setGlobal(true);
         role.setName("Employee");
         role.setSystem(true);
         role.setDescription("GENERAL");
         role.setPermissions(permissions);
         roles.add(role);
         User user=new User();
         user.setName("checking it  ");
         user.setCreationDate(new Date());
         user.setDescription("updated by id newly  ");
         user.setRoles(roles);
         user.setId(id);
         serviceManager = ServiceClientFactory.getServiceManager(context);            
         List<User> users = new ArrayList<User>();
         users.add(user);
         String customerJson = new Gson().toJson(users);         
         RestClient<User> userClient = serviceManager.getRestClient(RestResourceURIs.REST_API_USERS );
         userClient.setType(MediaType.APPLICATION_JSON);
         userClient.setAccept(MediaType.APPLICATION_JSON);
         userClient.setType(MediaType.APPLICATION_JSON);
         userClient.update(customerJson);
    }
    
    @Ignore
    public void testDeleteUserById() throws PhrescoException {
    	String id = "2c909a0a36e815dc0136e81b29860005";    
    	serviceManager = ServiceClientFactory.getServiceManager(context);            
        RestClient<User> userClient = serviceManager.getRestClient(RestResourceURIs.REST_API_USERS);
        userClient.delete(id);
    	
        }
}
