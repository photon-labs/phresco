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

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.service.api.ComponentManager;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_MODULES)
public class Modules implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(com.photon.phresco.service.model.Modules.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private static ComponentManager componentManager = PhrescoServerFactory.getComponentManager();
	private static Map<String, ModuleGroup> infoMap = new HashMap<String, ModuleGroup>();
	static {
		createModules();
	}

	/**
	 * Returns the list of modules
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleGroup> list(@Context HttpServletRequest request,
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit,
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
		//TODO: JEB -
		//Validate AuthToken
        //Validate Permissions
		return new ArrayList<ModuleGroup>(infoMap.values());
	}

	/**
     * Creates list of modules
     * @return
     */
	private static void createModules() {
		// TODO Auto-generated method stub
		ModuleGroup module = new ModuleGroup ();
		module.setArtifactId("Mobile JQUERY");
		module.setGroupId("Mobile Users");
		module.setName("Mobile Js lib");
		List <Module> versions= populatejsVersions ();
		module.setVersions(versions);
		module.setCore(true);
		module.setVendor("Apache");
		List <Documentation> documentationList = populateDocumentation ();
		module.setDocs(documentationList);
		infoMap.put(module.getId(), module);
	}

	/**
     * Returns the list of Documentation
     * @return
     * @throws PhrescoException
     */
	private static List<Documentation> populateDocumentation() {
		// TODO Auto-generated method stub
		List <Documentation> documentationList = new ArrayList <Documentation> ();
		Documentation documentation = new Documentation ();
		documentation.setContent("Mobile techlogy in Androd and iPHONE.....");
		documentation.setId("mobile technology application");
		documentation.setType(DocumentationType.HELP_TEXT);
		documentation.setUrl("http:\\apache");
		documentationList.add(documentation);
		return documentationList;
	}

	/**
     * Returns the list of Documentation
     * @return
     * @throws PhrescoException
     */
	private static List<Module> populatejsVersions() {
		// TODO Auto-generated method stub
		List <Module> jsVersionList= new ArrayList <Module> ();
		Module module = new Module ();
		module.setName("1.2.2");
		return jsVersionList;
	}

	/**
	 * Creates the module objects as specified in the parameter
	 * @param modules
	 * @throws PhrescoException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<com.photon.phresco.service.model.Modules> modules) throws PhrescoException {
		componentManager.createModules(modules);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleGroup> update(List<ModuleGroup> modules) throws PhrescoException {
		for (ModuleGroup moduleGroup : modules) {
			String id = moduleGroup.getId();
			if (!infoMap.containsKey(id)) {
				throw new PhrescoException(id + " does not exist ");
			}
		}
		for (ModuleGroup moduleGroup : modules) {
			infoMap.put(moduleGroup.getId(), moduleGroup);
		}
		return modules;
	}

	/**
	 * Deletes all the modules in the database
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Returns the list of modules
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public ModuleGroup get(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		return null;
	}

	/**
	 * Creates the ModuleGroup objects as specified in the parameter
	 *
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id,
			ModuleGroup moduleGroup) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public ModuleGroup update(@PathParam(REST_API_PATH_PARAM_ID) String id, ModuleGroup module) throws PhrescoException {
		if (!id.equals(module.getId())) {
			throw new PhrescoException("The ids does not match");
		}
		if (infoMap.containsKey(id)) {
			throw new PhrescoException(id + " is invalid");
		}
		infoMap.put(id, module);
		return module;
	}

	/**
	 * Deletes the module as specified by the id
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) {

	}

}
