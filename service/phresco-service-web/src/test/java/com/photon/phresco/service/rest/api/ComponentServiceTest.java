package com.photon.phresco.service.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.model.AppType;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.model.ServerConstants;


public class ComponentServiceTest extends DbService implements ServerConstants{
	
	public ComponentServiceTest() throws PhrescoException {
		super();
	}
	
	@Test
	public void testFindAppTypes() {
		List<ApplicationType> appTypeList = mongoOperation.getCollection(APPTYPES_COLLECTION_NAME , ApplicationType.class);
		ApplicationType applicationType = appTypeList.get(0);
		assertEquals(applicationType.getName(),"mobile");
	}

	@Test
	public void testCreateAppTypes() {
		List<AppType> appTypes = new ArrayList<AppType>();
		AppType app = new AppType("Html5", "webApplications", null);
		appTypes.add(app);
		mongoOperation.insertList(APPTYPES_COLLECTION_NAME , appTypes);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateAppTypes() {
		List<AppType> appTypes = new ArrayList<AppType>();
		AppType appType = new AppType("Html5", "WebApplications", null);
		appTypes.add(appType);
		appType.setId("4fe80d7e230d37b3444dfb32");
		mongoOperation.save(APPTYPES_COLLECTION_NAME, appTypes);
		assertEquals(Response.status(Response.Status.OK).entity(appTypes).build().getStatus(), 200);
	}

	@Test
	public void testDeleteAppTypes() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetApptype() {
		String id = "4fe80d7e230d37b3444dfb32";
		ApplicationType appType = mongoOperation.findOne(APPTYPES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ApplicationType.class);
		assertEquals(appType.getName(), "Html5");
	}
	
	@Test
	public void testUpdateAppType() {
		AppType appType = new AppType("Html5", "WebApplications", null);
		appType.setId("4fe80d7e230d37b3444dfb32");
		mongoOperation.save(APPTYPES_COLLECTION_NAME, appType);
		assertEquals(Response.status(Response.Status.OK).entity(appType).build().getStatus(), 200);
	}
	
	@Test
	public void testDeleteAppType() {
		String id = "4fe80d7e230d37b3444dfb32";
		mongoOperation.remove(APPTYPES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ApplicationType.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindSettings() {
		List<SettingsTemplate> appTypeList = mongoOperation.getCollection(SETTINGS_COLLECTION_NAME , SettingsTemplate.class);
		SettingsTemplate setting = appTypeList.get(0);
		assertEquals(setting.getType(), "server");
	}

	@Test
	public void testCreateSettings() {
		List<SettingsTemplate> settings = new ArrayList<SettingsTemplate>();
		SettingsTemplate setting = new SettingsTemplate("server", null, null);
		settings.add(setting);
		mongoOperation.insertList(SETTINGS_COLLECTION_NAME , settings);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateSettings() {
		List<SettingsTemplate> settings = new ArrayList<SettingsTemplate>();
		SettingsTemplate setting = new SettingsTemplate("database", null, null);
		settings.add(setting);
		setting.setId("4fe8165e230deb07e9aea1f3");
		mongoOperation.save(APPTYPES_COLLECTION_NAME, settings);
		assertEquals(Response.status(Response.Status.OK).entity(settings).build().getStatus(), 200);
	}

	@Test
	public void testDeleteSettings() throws PhrescoException {
		throw new PhrescoWebServiceException(EX_PHEX00006, "apptypes");
	}

	@Test
	public void testGetSettingsTemplate() {
		String id = "4fe8165e230deb07e9aea1f3";
		SettingsTemplate setting = mongoOperation.findOne(SETTINGS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), SettingsTemplate.class);
		assertEquals(setting.getType(), "database");
	}

	@Test
	public void testUpdateSetting() {
		SettingsTemplate setting = new SettingsTemplate("web-service", null, null);
		setting.setId("4fe8165e230deb07e9aea1f3");
		mongoOperation.save(SETTINGS_COLLECTION_NAME, setting);
		assertEquals(Response.status(Response.Status.OK).entity(setting).build().getStatus(), 200);
	}

	@Test
	public void testDeleteSettingsTemplate() {
		String id = "4fe8165e230deb07e9aea1f3";
		mongoOperation.remove(SETTINGS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), SettingsTemplate.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindModules() {
		List<ModuleGroup> moduleList = mongoOperation.getCollection(MODULES_COLLECTION_NAME , ModuleGroup.class);
		ModuleGroup module = moduleList.get(0);
		assertEquals(module.getName(), "log4j");
	}

	@Test
	public void testCreateModules() {
		List<ModuleGroup> modules = new ArrayList<ModuleGroup>();
		ModuleGroup module = new ModuleGroup();
		module.setName("log4j");
		module.setGroupId("log4j");
		module.setArtifactId("log4j");
		modules.add(module);
		mongoOperation.insertList(MODULES_COLLECTION_NAME , modules);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateModules() {
		List<ModuleGroup> modules = new ArrayList<ModuleGroup>();
		ModuleGroup module = new ModuleGroup();
		module.setName("log4j");
		module.setGroupId("log4j");
		module.setArtifactId("log4j");
		module.setId("4fe81a71230df81749b2cc18");
		modules.add(module);
		mongoOperation.save(MODULES_COLLECTION_NAME, modules);
		assertEquals(Response.status(Response.Status.OK).entity(modules).build().getStatus(), 200);
	}

	@Test
	public void testDeleteModules() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetModule() {
		String id = "4fe81a71230df81749b2cc18";
		ModuleGroup module = mongoOperation.findOne(MODULES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ModuleGroup.class);
		assertEquals(module.getName(), "log4j");
	}

	@Test
	public void testUpdatemodule() {
		ModuleGroup module = new ModuleGroup();
		module.setName("log4j");
		module.setGroupId("log4j");
		module.setArtifactId("log4j");
		module.setId("4fe81a71230df81749b2cc18");
		mongoOperation.save(MODULES_COLLECTION_NAME, module);
		assertEquals(Response.status(Response.Status.OK).entity(module).build().getStatus(), 200);
	}

	@Test
	public void testDeleteModulesString() {
		String id = "4fe81a71230df81749b2cc18";
		mongoOperation.remove(MODULES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ModuleGroup.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindPilots() {
		List<ProjectInfo> pilotsList = mongoOperation.getCollection(PILOTS_COLLECTION_NAME , ProjectInfo.class);
		ProjectInfo pilot = pilotsList.get(0);
		assertEquals(pilot.getName(), "eShop");
	}

	@Test
	public void testCreatePilots() {
		List<ProjectInfo> infos = new ArrayList<ProjectInfo>();
		ProjectInfo info = new ProjectInfo();
		info.setName("shoppingcart");
		info.setVersion("1.0");
		info.setGroupId("com.photo.phresco");
		info.setArtifactId("phresco-projects");
		infos.add(info);
		mongoOperation.insertList(PILOTS_COLLECTION_NAME , infos);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdatePilots() {
		List<ProjectInfo> infos = new ArrayList<ProjectInfo>();
		ProjectInfo info = new ProjectInfo();
		info.setName("shoppingcart");
		info.setVersion("1.0");
		info.setGroupId("com.photo.phresco");
		info.setArtifactId("phresco-projects");
		info.setId("4fe8262e230d8aa51d425aa1");
		infos.add(info);
		mongoOperation.save(PILOTS_COLLECTION_NAME, infos);
		assertEquals(Response.status(Response.Status.OK).entity(infos).build().getStatus(), 200);
	}

	@Test
	public void testDeletePilots() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetPilot() {
		String id = "4fe8262e230d8aa51d425aa1";
		ProjectInfo info = mongoOperation.findOne(PILOTS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ProjectInfo.class);
		assertEquals(info.getName(), "shoppingcart");
	}

	@Test
	public void testUpdatePilot() {
		ProjectInfo info = new ProjectInfo();
		info.setName("shoppingcart");
		info.setVersion("1.0");
		info.setGroupId("com.photo.phresco");
		info.setArtifactId("phresco-projects");
		info.setId("4fe8262e230d8aa51d425aa1");
		mongoOperation.save(PILOTS_COLLECTION_NAME, info);
		assertEquals(Response.status(Response.Status.OK).entity(info).build().getStatus(), 200);
	}

	@Test
	public void testDeletePilot() {
		String id = "4fe8262e230d8aa51d425aa1";
		mongoOperation.remove(PILOTS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), ProjectInfo.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindServers() {
		List<Server> serversList = mongoOperation.getCollection(SERVERS_COLLECTION_NAME , Server.class);
		System.out.println(serversList);
		Server server = serversList.get(0);
		assertEquals(server.getName(), "Tomcat");
	}

	@Test
	public void testCreateServers() {
		List<Server> servers = new ArrayList<Server>();
		Server server = new Server();
		server.setName("Tomcat");
		server.setDescription("For Deploying");
		servers.add(server);
		mongoOperation.insertList(SERVERS_COLLECTION_NAME , servers);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateServers() {
		List<Server> servers = new ArrayList<Server>();
		Server server = new Server();
		server.setName("Tomcat");
		server.setDescription("For Deploying");
		servers.add(server);
		mongoOperation.save(SERVERS_COLLECTION_NAME, servers);
		assertEquals(Response.status(Response.Status.OK).entity(servers).build().getStatus(), 200);
	}

	@Test
	public void testDeleteServers() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetServer() {
		String id = "4fe8262e230d8aa51d425aa1";
		Server server = mongoOperation.findOne(SERVERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Server.class);
		assertEquals(server.getName(), "shoppingcart");
	}

	@Test
	public void testUpdateServer() {
		Server server = new Server();
		server.setName("Tomcat");
		server.setDescription("For Deploying");
		mongoOperation.save(SERVERS_COLLECTION_NAME, server);
		assertEquals(Response.status(Response.Status.OK).entity(server).build().getStatus(), 200);
	}

	@Test
	public void testDeleteServer() {
		String id = "4fe8262e230d8aa51d425aa1";
		mongoOperation.remove(SERVERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Server.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindDatabases() {
		List<Database> databasesList = mongoOperation.getCollection(DATABASES_COLLECTION_NAME , Database.class);
		Database database = databasesList.get(0);
		assertEquals(database.getName(), "mongo");
	}

	@Test
	public void testCreateDatabases() {
		List<Database> databases = new ArrayList<Database>();
		Database database = new Database();
		database.setName("mongo");
		database.setDescription("To save objects");
		databases.add(database);
		mongoOperation.insertList(DATABASES_COLLECTION_NAME , databases);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateDatabases() {
		List<Database> databses = new ArrayList<Database>();
		Database database = new Database();
		database.setName("mongo");
		database.setDescription("To save objects");
		databses.add(database);
		mongoOperation.save(DATABASES_COLLECTION_NAME, databses);
		assertEquals(Response.status(Response.Status.OK).entity(databses).build().getStatus(), 200);
	}

	@Test
	public void testDeleteDatabases() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetDatabase() {
		String id = "4fe8262e230d8aa51d425aa1";
		Database database = mongoOperation.findOne(DATABASES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Database.class);
		assertEquals(database.getName(), "mongo");
	}

	@Test
	public void testUpdateDatabase() {
		Database database = new Database();
		database.setName("mongo");
		database.setDescription("To save objects");
		mongoOperation.save(DATABASES_COLLECTION_NAME, database);
		assertEquals(Response.status(Response.Status.OK).entity(database).build().getStatus(), 200);
	}

	@Test
	public void testDeleteDatabase() {
		String id = "4fe8262e230d8aa51d425aa1";
		mongoOperation.remove(DATABASES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Database.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindWebServices() {
		List<WebService> webServiceList = mongoOperation.getCollection(WEBSERVICES_COLLECTION_NAME , WebService.class);
		WebService service = webServiceList.get(0);
		assertEquals(service.getName(), "jersey");
	}

	@Test
	public void testCreateWebServices() {
		List<WebService> webServices = new ArrayList<WebService>();
		WebService webService = new WebService();
		webService.setName("jersey");
		webService.setDescription("For rest");
		webServices.add(webService);
		mongoOperation.insertList(WEBSERVICES_COLLECTION_NAME , webServices);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateWebServices() {
		List<WebService> webServices = new ArrayList<WebService>();
		WebService webService = new WebService();
		webService.setName("jersey");
		webService.setDescription("For rest");
		webServices.add(webService);
		mongoOperation.save(WEBSERVICES_COLLECTION_NAME, webServices);
		assertEquals(Response.status(Response.Status.OK).entity(webServices).build().getStatus(), 200);
	}

	@Test
	public void testDeleteWebServices() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetWebService() {
		String id = "4fe8262e230d8aa51d425aa1";
		WebService webService = mongoOperation.findOne(WEBSERVICES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), WebService.class);
		assertEquals(webService.getName(), "jersey");
	}

	@Test
	public void testUpdateWebService() {
		WebService webService = new WebService();
		webService.setName("jersey");
		webService.setDescription("For rest");
		mongoOperation.save(WEBSERVICES_COLLECTION_NAME, webService);
		assertEquals(Response.status(Response.Status.OK).entity(webService).build().getStatus(), 200);
	}

	@Test
	public void testDeleteWebService() throws PhrescoException {
		String id = "4fe8262e230d8aa51d425aa1";
		mongoOperation.remove(WEBSERVICES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), WebService.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindTechnologies() {
		List<Technology> technologies = mongoOperation.getCollection(TECHNOLOGIES_COLLECTION_NAME , Technology.class);
		Technology tech = technologies.get(0);
		assertEquals(tech.getName(), "Tomcat");
	}

	@Test
	public void testCreateTechnologies() {
		List<Technology> techs = new ArrayList<Technology>();
		Technology tech = new Technology();
		tech.setName("drupal");
		List<String> versions = new ArrayList<String>();
		versions.add("6");
		versions.add("7");
		tech.setVersions(versions);
		mongoOperation.insertList(TECHNOLOGIES_COLLECTION_NAME , techs);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateTechnologies() {
		List<Technology> techs = new ArrayList<Technology>();
		Technology tech = new Technology();
		tech.setName("drupal");
		tech.setId("4fe8262e230d8aa51d425aa1");
		List<String> versions = new ArrayList<String>();
		versions.add("6");
		versions.add("7");
		tech.setVersions(versions);
		mongoOperation.save(TECHNOLOGIES_COLLECTION_NAME, techs);
		assertEquals(Response.status(Response.Status.OK).entity(techs).build().getStatus(), 200);
	}

	@Test
	public void testDeleteTechnologies() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetTechnology() {
		String id = "4fe8262e230d8aa51d425aa1";
		Technology tech = mongoOperation.findOne(TECHNOLOGIES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Technology.class);
		assertEquals(tech.getName(), "drupal");
	}

	@Test
	public void testUpdateTechnology() {
		Technology tech = new Technology();
		tech.setName("drupal");
		tech.setId("4fe8262e230d8aa51d425aa1");
		List<String> versions = new ArrayList<String>();
		versions.add("6");
		versions.add("7");
		tech.setVersions(versions);
		mongoOperation.save(TECHNOLOGIES_COLLECTION_NAME, tech);
		assertEquals(Response.status(Response.Status.OK).entity(tech).build().getStatus(), 200);
	}

	@Test
	public void testDeleteTechnology() {
		String id = "4fe8262e230d8aa51d425aa1";
		mongoOperation.remove(TECHNOLOGIES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Technology.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

}
