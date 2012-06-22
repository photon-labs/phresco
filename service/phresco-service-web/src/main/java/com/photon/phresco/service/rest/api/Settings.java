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

import com.photon.phresco.commons.model.Setting;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.AdminManager;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_SETTINGS)
public class Settings implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Settings.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Setting> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
        return null;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Setting> update(List<Setting> setting) throws PhrescoException {
		return null;
	}
	
	/**
	 * Deletes all the customers in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Setting> setting) throws PhrescoException {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Returns the list of settings
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_LDAP)
	public Setting get(@PathParam(REST_API_LDAP_PARAM_ID) String id) {
		return null;
	}
	
	/**
	 * Creates the setting objects as specified in the parameter
	 * @param customers
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id, Setting setting) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_LDAP)
	public Setting update(Setting setting) {
		//The settings should exist and the id should be the same
		return null;
	}
	
	/**
	 * Deletes the setting as specified by the id
	 */
	@DELETE
	@Path(REST_API_LDAP)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		
	}
}