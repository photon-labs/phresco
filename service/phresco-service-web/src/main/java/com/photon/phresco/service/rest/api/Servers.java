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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_SERVERS)
public class Servers implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Technology.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	static {
		createServer();
	}
	/**
	 * Returns the list of servers
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Server> list(@Context HttpServletRequest request,
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit,
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		List<Server> serverList = createServer();
		return serverList;
	}

	/**
	 * Creates the Server objects as specified in the parameter
	 * @param technologies
	 * @throws PhrescoException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Server> servers) throws PhrescoException {
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Server> update(List<Server> servers) throws PhrescoException {
		return servers;
		
	}

	/**
	 * Deletes all the servers
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Returns the list of server
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Server get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		return null;
	}
	/**
	 * Creates the server objects as specified in the parameter
	 * @param technologies
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id,
			Server server) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Server update(@PathParam(REST_API_PATH_PARAM_ID) String id, Server server) throws PhrescoException {
		return null;
	}

	/**
	 * Deletes the server as specified by the id
	 * @throws PhrescoException
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
	   
	}

	

	private static  List<Server> createServer() {
	    List<Server> servers = new ArrayList<Server>();

        List<String> versions = new ArrayList<String>(2);
        versions.add("7.0.x");
        versions.add("6.0.x");
        versions.add("5.5.x");
        
        servers.add(new Server(1, "Apache Tomcat", versions, "Apache Tomcat Server"));
        versions = new ArrayList<String>(2);
        versions.add("7.1.x");
        versions.add("7.0.x");
        versions.add("6.1.x");
        versions.add("6.0.x");
        versions.add("5.1.x");
        versions.add("5.0.x");
        versions.add("4.2.x");
        versions.add("4.0.x");
        servers.add(new Server(2, "JBoss", versions, "JBoss application server"));

        versions = new ArrayList<String>(2);
        versions.add("7.5");
        versions.add("7.0");
        versions.add("6.0");
        versions.add("5.1");
        versions.add("5.0");
        servers.add(new Server(3, "IIS", versions, "IIS Server"));

        versions = new ArrayList<String>(2);
        versions.add("12c(12.1.1)");
        versions.add("11gR1(10.3.6)");
        versions.add("11g(10.3.1)");
        versions.add("10.3");
        versions.add("10.0");
        versions.add("9.2");
        versions.add("9.1");
        servers.add(new Server(4, "WebLogic", versions, "Web Logic"));
        
        versions = new ArrayList<String>(2);
        versions.add("2.3");
        versions.add("2.2");
        versions.add("2.0");
        versions.add("1.3");
        servers.add(new Server(5, "Apache", versions, "Apache"));
        
        versions = new ArrayList<String>(2);
        versions.add("0.6.x");
        versions.add("0.7.x");
        servers.add(new Server(6, "NodeJS", versions, "NodeJS"));
        
        versions = new ArrayList<String>(2);
        versions.add("8.x");
        versions.add("7.x");
        versions.add("6.x");
        versions.add("5.x");
        versions.add("4.x");
        servers.add(new Server(7, "Jetty", versions, "Jetty"));

		return servers;
	}


}
