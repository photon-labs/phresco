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
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ComponentRestDatabaseTest implements ServiceConstants {

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
	public void testCreateDatabase() throws PhrescoException {
		List<String> technologies=new ArrayList<String>();
		
		technologies.add("Html5");
	    List<Database> database = new ArrayList<Database>();
	    Database db = new Database();
	    db.setId("testDatabase");
	    db.setName("TestDatabase");
	    db.setDescription("This is a test database");
	    db.setCustomerId("photon");
	    db.setTechnologies(technologies);
	    database.add(db);
        RestClient<Database> newApp = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
        ClientResponse clientResponse = newApp.create(database);
    }
	
	
	@Test
    public void testGetDatabases() throws PhrescoException {
		String techId="Html5";
    	RestClient<Database> dbClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
    	dbClient.queryString(REST_QUERY_TECHID, techId);
    	dbClient.queryString(REST_QUERY_CUSTOMERID, "photon");
		GenericType<List<Database>> genericType = new GenericType<List<Database>>(){};
		List<Database> databases = dbClient.get(genericType);
		assertNotNull(databases);
    }

	@Test
	public void testUpdateDatabase() throws PhrescoException{
		RestClient<Database> databaseClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
		List<String> technologies=new ArrayList<String>();
		technologies.add("Html5");
	    List<Database> database = new ArrayList<Database>();
	    Database db = new Database();
	    db.setId("testDatabase");
	    db.setName("TestDatabase");
	    db.setDescription("This is a test database update");
	    db.setCustomerId("photon");
	    db.setTechnologies(technologies);
	    database.add(db);
	    GenericType<List<Database>> genericType = new GenericType<List<Database>>() {};
	   
	    List<Database> clientResponse = databaseClient.update(database, genericType);
	    
	}

	@Test
    public void testGetDatabasesById() throws PhrescoException {
		String Id = "Html5";
    	RestClient<Database> dbClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
    	dbClient.queryString(REST_QUERY_TECHID, Id);
		GenericType<List<Database>> genericType = new GenericType<List<Database>>(){};
		List<Database> databases = dbClient.get(genericType);
		assertNotNull(databases);
    }
	
	@Test
	public void testUpdateDatabaseById() throws PhrescoException {
        RestClient<Database> editDB = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
        Database db = new Database();
        db.setId("testDatabase");
	    db.setName("TestDatabaseUpdateBYId");
	    db.setDescription("This is a test database updateId");
	    db.setCustomerId("photon");
	    editDB.setPath("testDatabase");
        GenericType<Database> genericType = new GenericType<Database>() {};
        editDB.updateById(db, genericType);
    }

	@Test
	public void testDeleteDatabase() throws PhrescoException {
        RestClient<Database> deleteDatabase = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
        deleteDatabase.setPath("testDatabase");
        ClientResponse clientResponse = deleteDatabase.deleteById();
    }
}