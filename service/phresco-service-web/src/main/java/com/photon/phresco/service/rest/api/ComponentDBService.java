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
import javax.ws.rs.core.MediaType;

import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.model.ServerConstants;

@Component
@Path(ServerConstants.REST_API_COMPONENT)
public class ComponentDBService extends DbService implements ServerConstants{
	
	public ComponentDBService() {
		super();
    }
	
	/**
	 * Returns the list of apptypes
	 * @return
	 */
	@GET
	@Path(REST_API_APPTYPES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ApplicationType> findAppTypes() {
    	List<ApplicationType> appTypeList = mongoOperation.getCollection(APPTYPES_COLLECTION_NAME , ApplicationType.class);
    	return appTypeList;
	}
	
	/**
	 * Creates the list of apptypes
	 * @param appTypes
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_APPTYPES)
	public void createAppTypes(List<ApplicationType> appTypes) {
		mongoOperation.insertList(APPTYPES_COLLECTION_NAME , appTypes);
	}
	
	/**
	 * Updates the list of apptypes
	 * @param appTypes
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_APPTYPES)
	public List<ApplicationType> updateAppTypes(List<ApplicationType> appTypes) {
		for (ApplicationType appType : appTypes) {
			ApplicationType applnType = mongoOperation.findOne(APPTYPES_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(appType.getId())), ApplicationType.class);
			if (applnType != null) {
				mongoOperation.save(APPTYPES_COLLECTION_NAME, appType);
			}
		}
		return appTypes;
	}
	
	/**
	 * Updates the list of apptypes
	 * @param appTypes
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_APPTYPES + REST_API_PATH_ID)
	public ApplicationType updateAppType(@PathParam(REST_API_PATH_PARAM_ID) String id , ApplicationType appType) {
		if(id.equals(appType.getId())) {
			mongoOperation.save(APPTYPES_COLLECTION_NAME, appType);
		}
		return appType;
	}
	
	/**
	 * Deletes the list of apptypes
	 * @param appTypes
	 */
	@DELETE
	@Path(REST_API_APPTYPES)
	public void deleteAppTypes(List<ApplicationType> appTypes) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the apptype by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_APPTYPES + REST_API_PATH_ID)
	public ApplicationType getApptype(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		ApplicationType appType = mongoOperation.findOne(APPTYPES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ApplicationType.class);
    	return appType;
	}
	
	/**
	 * Deletes the apptype by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_APPTYPES + REST_API_PATH_ID)
	public void deleteAppType(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(APPTYPES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ApplicationType.class);
	}
	
	/**
	 * Returns the list of settings
	 * @return
	 */
	@GET
	@Path(REST_API_SETTINGS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SettingsTemplate> findSettings() {
    	List<SettingsTemplate> settingsList = mongoOperation.getCollection(SETTINGS_COLLECTION_NAME , SettingsTemplate.class);
    	return settingsList;
	}
	
	/**
	 * Creates the list of settings
	 * @param settings
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_SETTINGS)
	public void createSettings(List<SettingsTemplate> settings) {
		mongoOperation.insertList(SETTINGS_COLLECTION_NAME , settings);
	}
	
	/**
	 * Updates the list of settings
	 * @param settings
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_SETTINGS)
	public List<SettingsTemplate> updateSettings(List<SettingsTemplate> settings) {
		for (SettingsTemplate settingTemplate : settings) {
			SettingsTemplate settingTemplateInfo = mongoOperation.findOne(SETTINGS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(settingTemplate.getId())), SettingsTemplate.class);
			if (settingTemplateInfo != null) {
				mongoOperation.save(SETTINGS_COLLECTION_NAME, settingTemplate);
			}
		}
		return settings;
	}
	
	/**
	 * Deletes the list of settings
	 * @param settings
	 */
	@DELETE
	@Path(REST_API_SETTINGS)
	public void deleteSettings(List<SettingsTemplate> settings) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the settingTemplate by id 
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_SETTINGS + REST_API_PATH_ID)
	public SettingsTemplate getSettingsTemplate(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		SettingsTemplate settingTemplate = mongoOperation.findOne(SETTINGS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), SettingsTemplate.class);
    	return settingTemplate;
	}
	
	/**
	 * Updates the list of setting
	 * @param settings
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_SETTINGS + REST_API_PATH_ID)
	public SettingsTemplate updateAppType(@PathParam(REST_API_PATH_PARAM_ID) String id , SettingsTemplate settingsTemplate) {
		if(id.equals(settingsTemplate.getId())) {
			mongoOperation.save(SETTINGS_COLLECTION_NAME, settingsTemplate);
		}
		return settingsTemplate;
	}
	
	/**
	 * Deletes the settingsTemplate by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_SETTINGS + REST_API_PATH_ID)
	public void deleteSettingsTemplate(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(SETTINGS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), SettingsTemplate.class);
	}
	
	/**
	 * Returns the list of modules
	 * @return
	 */
	@GET
	@Path(REST_API_MODULES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleGroup> findModules() {
    	List<ModuleGroup> modulesList = mongoOperation.getCollection(MODULES_COLLECTION_NAME , ModuleGroup.class);
    	return modulesList;
	}
	
	/**
	 * Creates the list of modules
	 * @param modules
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_MODULES)
	public void createModules(List<ModuleGroup> modules) {
		mongoOperation.insertList(MODULES_COLLECTION_NAME , modules);
	}
	
	/**
	 * Updates the list of modules
	 * @param modules
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_MODULES)
	public List<ModuleGroup> updateModules(List<ModuleGroup> modules) {
		for (ModuleGroup moduleGroup : modules) {
			ModuleGroup module = mongoOperation.findOne(MODULES_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(moduleGroup.getId())), ModuleGroup.class);
			if (module != null) {
				mongoOperation.save(MODULES_COLLECTION_NAME, moduleGroup);
			}
		}
		return modules;
	}
	
	/**
	 * Deletes the list of modules
	 * @param modules
	 */
	@DELETE
	@Path(REST_API_MODULES)
	public void deleteModules(List<ModuleGroup> modules) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the module by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_MODULES + REST_API_PATH_ID)
	public ModuleGroup getModule(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		ModuleGroup module = mongoOperation.findOne(MODULES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ModuleGroup.class);
    	return module;
	}
	
	/**
	 * Updates the module given by the parameter
	 * @param id
	 * @param module
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_MODULES + REST_API_PATH_ID)
	public ModuleGroup updatemodule(@PathParam(REST_API_PATH_PARAM_ID) String id , ModuleGroup module) {
		if(id.equals(module.getId())) {
			mongoOperation.save(PILOTS_COLLECTION_NAME, module);
		}
		return module;
	}
	
	/**
	 * Deletes the module by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_MODULES + REST_API_PATH_ID)
	public void deleteModules(String id) {
		mongoOperation.remove(MODULES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ModuleGroup.class);
	}
	
	/**
	 * Returns the list of pilots
	 * @return
	 */
	@GET
	@Path(REST_API_PILOTS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectInfo> findPilots() {
    	List<ProjectInfo> pilotList = mongoOperation.getCollection(PILOTS_COLLECTION_NAME , ProjectInfo.class);
    	return pilotList;
	}
	
	/**
	 * Creates the list of pilots
	 * @param projectInfos
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_PILOTS)
	public void createPilots(List<ProjectInfo> projectInfos) {
		mongoOperation.insertList(PILOTS_COLLECTION_NAME , projectInfos);
	}
	
	/**
	 * Updates the list of pilots
	 * @param projectInfos
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_PILOTS)
	public List<ProjectInfo> updatePilots(List<ProjectInfo> pilots) {
		for (ProjectInfo pilot : pilots) {
			ProjectInfo projectInfo = mongoOperation.findOne(PILOTS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(pilot.getId())), ProjectInfo.class);
			if (projectInfo != null) {
				mongoOperation.save(PILOTS_COLLECTION_NAME, pilot);
			}
		}
		return pilots;
	}
	
	/**
	 * Deletes the list of pilots
	 * @param pilots
	 */
	@DELETE
	@Path(REST_API_PILOTS)
	public void deletePilots(List<ProjectInfo> pilots) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the pilot by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PILOTS + REST_API_PATH_ID)
	public ProjectInfo getPilot(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		ProjectInfo projectInfo = mongoOperation.findOne(PILOTS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ProjectInfo.class);
    	return projectInfo;
	}
	
	/**
	 * Updates the pilot given by the parameter
	 * @param id
	 * @param pilot
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_PILOTS + REST_API_PATH_ID)
	public ProjectInfo updatePilot(@PathParam(REST_API_PATH_PARAM_ID) String id , ProjectInfo pilot) {
		if(id.equals(pilot.getId())) {
			mongoOperation.save(PILOTS_COLLECTION_NAME, pilot);
		}
		return pilot;
	}
	
	/**
	 * Deletes the pilot by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_PILOTS + REST_API_PATH_ID)
	public void deletePilot(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(PILOTS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ProjectInfo.class);
	}
	
	/**
	 * Returns the list of servers
	 * @return
	 */
	@GET
	@Path(REST_API_SERVERS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Server> findServers() {
    	List<Server> serverList = mongoOperation.getCollection(SERVERS_COLLECTION_NAME , Server.class);
    	return serverList;
	}
	
	/**
	 * Creates the list of servers
	 * @param servers
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_SERVERS)
	public void createServers(List<Server> servers) {
		mongoOperation.insertList(SERVERS_COLLECTION_NAME , servers);
	}
	
	/**
	 * Updates the list of servers
	 * @param servers
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_SERVERS)
	public List<Server> updateServers(List<Server> servers) {
		for (Server server : servers) {
			Server serverInfo = mongoOperation.findOne(SERVERS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(server.getId())), Server.class);
			if (serverInfo != null) {
				mongoOperation.save(SERVERS_COLLECTION_NAME , server);
			}
		}
		return servers;
	}
	
	/**
	 * Deletes the list of servers
	 * @param servers
	 */
	@DELETE
	@Path(REST_API_SERVERS)
	public void deleteServers(List<Server> servers) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the server by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_SERVERS + REST_API_PATH_ID)
	public Server getServer(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		Server server = mongoOperation.findOne(SERVERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Server.class);
    	return server;
	}
	
	/**
	 * Updates the server given by the parameter
	 * @param id
	 * @param server
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_SERVERS + REST_API_PATH_ID)
	public Server updateServer(@PathParam(REST_API_PATH_PARAM_ID) String id , Server server) {
		if(id.equals(server.getId())) {
			mongoOperation.save(SERVERS_COLLECTION_NAME, server);
		}
		return server;
	}
	
	/**
	 * Deletes the server by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_SERVERS + REST_API_PATH_ID)
	public void deleteServer(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(SERVERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Server.class);
	}
	
	/**
	 * Returns the list of databases
	 * @return
	 */
	@GET
	@Path(REST_API_DATABASES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Database> findDatabases() {
    	List<Database> dataBaseList = mongoOperation.getCollection(DATABASES_COLLECTION_NAME , Database.class);
    	return dataBaseList;
	}
	
	/**
	 * Creates the list of databases
	 * @param databases
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_DATABASES)
	public void createDatabases(List<Database> databases) {
		mongoOperation.insertList(DATABASES_COLLECTION_NAME , databases);
	}
	
	/**
	 * Updates the list of databases
	 * @param databases
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_DATABASES)
	public List<Database> updateDatabases(List<Database> databases) {
		for (Database dataBase : databases) {
			Database dataBaseInfo = mongoOperation.findOne(DATABASES_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(dataBase.getId())), Database.class);
			if (dataBaseInfo != null) {
				mongoOperation.save(DATABASES_COLLECTION_NAME , dataBase);
			}
		}
		return databases;
	}
	
	/**
	 * Deletes the list of databases
	 * @param databases
	 */
	@DELETE
	@Path(REST_API_DATABASES)
	public void deleteDatabases(List<Database> databases) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the database by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DATABASES + REST_API_PATH_ID)
	public Database getDatabase(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		Database database = mongoOperation.findOne(DATABASES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Database.class);
    	return database;
	}
	
	/**
	 * Updates the database given by the parameter
	 * @param id
	 * @param database
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_DATABASES + REST_API_PATH_ID)
	public Database updateDatabase(@PathParam(REST_API_PATH_PARAM_ID) String id , Database database) {
		if(id.equals(database.getId())) {
			mongoOperation.save(DATABASES_COLLECTION_NAME, database);
		}
		return database;
	}
	
	/**
	 * Deletes the server by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_DATABASES + REST_API_PATH_ID)
	public void deleteDatabase(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(DATABASES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Database.class);
	}
	
	/**
	 * Returns the list of webservices
	 * @return
	 */
	@GET
	@Path(REST_API_WEBSERVICES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<WebService> findWebServices() {
    	List<WebService> webServiceList = mongoOperation.getCollection(WEBSERVICES_COLLECTION_NAME , WebService.class);
    	return webServiceList;
	}
	
	/**
	 * Creates the list of webservices
	 * @param webServices
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_WEBSERVICES)
	public void createWebServices(List<WebService> webServices) {
		mongoOperation.insertList(WEBSERVICES_COLLECTION_NAME , webServices);
	}
	
	/**
	 * Updates the list of webservices
	 * @param webServices
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_WEBSERVICES)
	public List<WebService> updateWebServices(List<WebService> webServices) {
		for (WebService webService : webServices) {
			WebService webServiceInfo = mongoOperation.findOne(WEBSERVICES_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(webService.getId())), WebService.class);
			if (webServiceInfo != null) {
				System.out.println("Entered");
				mongoOperation.save(WEBSERVICES_COLLECTION_NAME , webService);
			}
		}
		return webServices;
	}
	
	/**
	 * Deletes the list of webservices
	 * @param webServices
	 */
	@DELETE
	@Path(REST_API_WEBSERVICES)
	public void deleteWebServices(List<WebService> webServices) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the database by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WEBSERVICES + REST_API_PATH_ID)
	public WebService getWebService(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		WebService webService = mongoOperation.findOne(WEBSERVICES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), WebService.class);
    	return webService;
	}
	
	/**
	 * Updates the database given by the parameter
	 * @param id
	 * @param webService
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_WEBSERVICES + REST_API_PATH_ID)
	public WebService updateWebService(@PathParam(REST_API_PATH_PARAM_ID) String id , WebService webService) {
		if(id.equals(webService.getId())) {
			mongoOperation.save(WEBSERVICES_COLLECTION_NAME, webService);
		}
		return webService;
	}
	
	/**
	 * Deletes the server by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_WEBSERVICES + REST_API_PATH_ID)
	public void deleteWebService(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(WEBSERVICES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), WebService.class);
	}
	
	/**
	 * Returns the list of technologies
	 * @return
	 */
	@GET
	@Path(REST_API_TECHNOLOGIES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Technology> findTechnologies() {
    	List<Technology> techList = mongoOperation.getCollection(TECHNOLOGIES_COLLECTION_NAME , Technology.class);
    	return techList;
	}
	
	/**
	 * Creates the list of technologies
	 * @param technologies
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_TECHNOLOGIES)
	public void createTechnologies(List<Technology> technologies) {
		mongoOperation.insertList(TECHNOLOGIES_COLLECTION_NAME , technologies);
	}
	
	/**
	 * Updates the list of technologies
	 * @param technologies
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_TECHNOLOGIES)
	public List<Technology> updateTechnologies(List<Technology> technologies) {
		for (Technology tech : technologies) {
			Technology techInfo = mongoOperation.findOne(TECHNOLOGIES_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(tech.getId())), Technology.class);
			if (techInfo != null) {
				System.out.println("Entered");
				mongoOperation.save(TECHNOLOGIES_COLLECTION_NAME , tech);
			}
		}
		return technologies;
	}
	
	/**
	 * Deletes the list of technologies
	 * @param technologies
	 */
	@DELETE
	@Path(REST_API_TECHNOLOGIES)
	public void deleteTechnologies(List<WebService> technologies) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	/**
	 * Get the technology by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_TECHNOLOGIES + REST_API_PATH_ID)
	public Technology getTechnology(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		Technology technology = mongoOperation.findOne(TECHNOLOGIES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Technology.class);
    	return technology;
	}
	
	/**
	 * Updates the technology given by the parameter
	 * @param id
	 * @param technology
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_TECHNOLOGIES + REST_API_PATH_ID)
	public Technology updateTechnology(@PathParam(REST_API_PATH_PARAM_ID) String id , Technology technology) {
		if(id.equals(technology.getId())) {
			mongoOperation.save(TECHNOLOGIES_COLLECTION_NAME, technology);
		}
		return technology;
	}
	
	/**
	 * Deletes the server by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_TECHNOLOGIES + REST_API_PATH_ID)
	public void deleteTechnology(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(TECHNOLOGIES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Technology.class);
	}
}
