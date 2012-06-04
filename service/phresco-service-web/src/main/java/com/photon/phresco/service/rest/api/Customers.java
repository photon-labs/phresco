package com.photon.phresco.service.rest.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.ServiceConstants;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_CUSTOMERS)
public class Customers implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Customers.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	
	/**
	 * Returns the list of customers
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
        
		//TODO: JEB - 
		//Validate AuthToken
        //Validate Permissions
		return adminManager.findCustomers();
	}
	
	/**
	 * Creates the customer objects as specifed in the parameter
	 * @param customers
	 * @throws PhrescoException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Customer> customers) throws PhrescoException {
		adminManager.createCustomers(customers);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> update(List<Customer> customers) throws PhrescoException {
		return adminManager.updateCustomers(customers);
	}
	
	/**
	 * Deletes all the customers in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	

	/**
	 * Returns the list of customers
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Customer get(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		return null;
	}
	
	/**
	 * Creates the customer objects as specified in the parameter
	 * @param customers
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id, 
			Customer customer) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Customer update(Customer customer) {
		//The customer should exist and the id should be the same
		return null;
	}
	
	/**
	 * Deletes the customer as specified by the id
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		
	}
	
}
