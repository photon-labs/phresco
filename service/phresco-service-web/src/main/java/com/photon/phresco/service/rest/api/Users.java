/*
 * ###
 * Service Web Archive
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

import com.photon.phresco.commons.model.Role;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_USERS)
public class Users implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Users.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	private static Map<String,User> userMap = new HashMap<String, User>(10);
	
	static {
		createUser();
	}
	
	/**
	 * 
	 * Returns the list of Users
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
		return new ArrayList<User>(userMap.values());
	}

	/**
	 * Creates the User objects as specifed in the parameter
	 * @param user
	 * @throws PhrescoException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<User> users) throws PhrescoException {
		for (User user : users) {
			String id = user.getId();
			if(userMap.containsKey(user.getId())){
				throw new PhrescoException(id + "already exist");
			}
		}
		for (User user : users) {
			userMap.put(user.getId(),user);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> update(List<User> users) throws PhrescoException {
		for (User user : users) {
			String id = user.getId();
			if(!userMap.containsKey(id)){
				throw new PhrescoException(id +  " is invalid");
			}
		}
		for (User user : users) {
			userMap.put(user.getId(), user);
		}
		return users;
	}

	/**
	 * Deletes all the users in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Returns the list of Users
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public User get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		User user = userMap.get(id);
		if(user == null) {
			throw new PhrescoException(id + "does not exist");
		}
		return user;
	}

	/**
	 * Creates the user objects as specified in the parameter
	 * @param users
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id, User user) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public User update(@PathParam(REST_API_PATH_PARAM_ID) String id, User user) throws PhrescoException {
		if(!id.equals(user.getId())){
			throw new PhrescoException(id + "does not exist");
		}
		if(!userMap.containsKey(id)){
			throw new PhrescoException(id + "is invalid");
		}
		userMap.put(id, user);
		return user;
	}

	/**
	 * Deletes the Role as specified by the id
	 * @throws PhrescoException 
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		if(!userMap.containsKey(id)){
			throw new PhrescoException(id + "not found");
		}
		userMap.remove(id);
	}
	
	private static void createUser() {
		User user = new User();
		User user1 = new User();
		user.setId("1");
		user.setCreationDate(null);
		user.setDescription("Saravanan");
		List<Role> roleList = new  ArrayList<Role>();
		Role role = new  Role();
		role.setCreationDate(null);
		role.setDescription("Technical Manager Role");
		roleList.add(role);
		user.setRoles(roleList);
		user.setStatus(200);
		
		user1.setId("2");
		user1.setCreationDate(null);
		user1.setDescription("Kumar");
		List<Role> roleList1 = new  ArrayList<Role>();
		Role role1 = new  Role();
		role1.setCreationDate(null);
		role1.setDescription("Technical Leader");
		roleList1.add(role1);
		user1.setRoles(roleList);
		user1.setStatus(200);
	
		userMap.put(user.getId(),user);
		userMap.put(user1.getId(),user1);
	}
}