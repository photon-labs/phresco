package com.photon.phresco.service.rest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_PERMISSIONS)
public class Permissions implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Permissions.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	private static Map<String, Permission> permissionMap = new HashMap<String, Permission>(10);
	
	static { 
		createPermission();
	}
	
	/**
	 * Returns the list of Permissions
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Permission> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
        return new ArrayList<Permission>(permissionMap.values());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Permission> permissions) throws PhrescoException {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> update(List<Permission> permissions) throws PhrescoException {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Deletes all the Permissions in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	private static void createPermission() { 
		Permission permission = new Permission();
		Permission permission1 = new Permission();
		permission.setCreationDate(null);
		permission.setDescription("Roles of Manager");
		permission.setId("1");
		permission.setName("Saravanan");
		permission.setCreationDate(null);
		permission.setDescription("Roles of Manager");
		permission1.setCreationDate(null);
		permission1.setDescription("Technical Manager");
		permission1.setId("2");
		permission1.setName("Kumar");
		permission1.setCreationDate(null);
		permission1.setDescription("Manage the Technical issue");
		
		permissionMap.put(permission.getId(),permission);
		permissionMap.put(permission1.getId(),permission1);
	}
}