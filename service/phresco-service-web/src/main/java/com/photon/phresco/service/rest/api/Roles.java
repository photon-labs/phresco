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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_ROLES)
public class Roles implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Role.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	private static Map<String, Role> roleMap = new HashMap<String, Role>(10);
	
	static { 
		createRole();
	}
	/**
	 * Returns the list of Role
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
        return new ArrayList<Role>(roleMap.values());
	}

	/**
	 * Creates the Role objects as specifed in the parameter
	 * @param role
	 * @throws PhrescoException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Role> roles) throws PhrescoException {
		for (Role role :roles ) {
			String id = role.getId();
			if(roleMap.containsKey(role.getId())){
				throw new PhrescoException(id + "already exist");
			}
		}
		for (Role role : roles) {
			roleMap.put(role.getId(),role);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> update(List<Role> roles) throws PhrescoException {
		for (Role role : roles) {
			String id = role.getId();
			if(!roleMap.containsKey(id)){
				throw new PhrescoException(id + " is invalid");
			}
		}
		for (Role role : roles) {
			roleMap.put(role.getId(), role);
		}
		return roles;
	}

	/**
	 * Deletes all the roles in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Returns the list of Role
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Role get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		Role role = roleMap.get(id);
		if(role == null) {
			throw new PhrescoException(id + "does not exist");
		}
		return role;
	}
	

	/**
	 * Creates the role objects as specified in the parameter
	 * @param Roles
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id, Role role) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	public Role update(@PathParam(REST_API_PATH_PARAM_ID) String id, Role role) throws PhrescoException {
		if(!id.equals(role.getId())){
			throw new PhrescoException(id + "does not exist");
		}
		if(!roleMap.containsKey(id)){
			throw new PhrescoException(id + "is invalid");
		}
		roleMap.put(id, role);
		return role;
	}
	
	/**
	 * Deletes the Role as specified by the id
	 * @throws PhrescoException 
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		if(!roleMap.containsKey(id)){
			throw new PhrescoException(id + "not found");
		}
		roleMap.remove(id);
	}

	private static void createRole() {
		Role role = new  Role();
		Role role1 = new Role();
		role.setId("1");
		role.setName("Saravanan");
		role.setCreationDate(null);
		role.setDescription("MANAGE ALL TECHNOLOGY");
		List<Permission> permissionList = new ArrayList<Permission>();
		Permission permission = new Permission();
		permission.setCreationDate(null);
		permission.setDescription("Roles of Manager");
		permission.setId("1");
		permission.setName("Controller");
		permissionList.add(permission);
		
		role1.setId("2");
		role.setName("Kumar");
		role.setCreationDate(null);
		role.setDescription("Controll ALL TECHNOLOGY");
		List<Permission> permissionList1 = new ArrayList<Permission>();
		Permission permission1 = new Permission();
		permission1.setCreationDate(null);
		permission1.setDescription("Roles of Manager");
		permission1.setId("1");
		permission1.setName("Controller");
		permissionList1.add(permission1);
		
		role.setPermissions(permissionList);
		role1.setPermissions(permissionList1);
		roleMap.put(role.getId(), role);
		roleMap.put(role1.getId(), role1);
	}

}