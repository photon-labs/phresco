/*
 * ###
 * Phresco Service Implemenation
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
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
package com.photon.phresco.service.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.service.model.ServerConstants;

@Component
@Path("/rest")
public class AdminDBService {
	private ApplicationContext ctx;
	private MongoOperations mongoOperation;
	
	
    public AdminDBService() {
    	ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
    	mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
    }
    
    @GET
	@Path(ServerConstants.REST_API_CUSTOMERS)
	public Response findCustomers() {
    	List<Customer> listCustomer = mongoOperation.getCollection("customers" , Customer.class);
    	GenericEntity<List<Customer>> entity = new GenericEntity<List<Customer>>(listCustomer) {};
    	return Response.ok().type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	public Customer getCustomer(String id) {
		return null;
	}
	
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(ServerConstants.REST_API_CUSTOMERS)
	public void createCustomers(List<Customer> customers) {
		mongoOperation.insertList("customers" , customers);
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(ServerConstants.REST_API_CUSTOMERS)
	public List<Customer> updateCustomers(List<Customer> customers) {
		mongoOperation.insertList(customers);
		return customers;
	}
	
	@DELETE
	@Path(ServerConstants.REST_API_PATH_ID)
	public void deleteCustomer(@PathParam(ServerConstants.REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove("customers", new Query(Criteria.where("id").is(id)), Customer.class);
	}
 }
