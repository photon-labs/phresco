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
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_APPTYPES)
public class AppTypes implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(ApplicationType.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private static Map<String, ApplicationType> infoMap = new HashMap<String, ApplicationType>();

    static {
        createAppTypes();
    }

	/**
	 * Returns the list of AppTypes
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ApplicationType> list(@Context HttpServletRequest request,
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit,
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
		//TODO: JEB -
		//Validate AuthToken
        //Validate Permissions
		return new ArrayList<ApplicationType>(infoMap.values());
	}

	/**
     * Creates the list of ApplicationTypes
     * @throws PhrescoException
     */
	private static void createAppTypes() {
        // TODO Auto-generated method stub
	    ApplicationType appType = new ApplicationType ();
	    appType = createApplicationTypes ();
        List<Technology> technologyList = createTechnology ();
        appType.setTechnologies(technologyList);
        infoMap.put(appType.getId(), appType);
    }

	/**
     * Returns ApplicationType
     * @throws PhrescoException
     */
	private static ApplicationType createApplicationTypes() {
		// TODO Auto-generated method stub
	    ApplicationType appType = new ApplicationType ();
	    appType.setId("Mobile");
        appType.setName("Mobil");
        appType.setDisplayName("Mobile-Application");
		return appType;
	}

	/**
     * Returns the list of Technologies
     * @return
     */
	private static List<Technology> createTechnology() {
		// TODO Auto-generated method stub
		List<Technology> technologyList = new ArrayList <Technology> ();
		Technology technology = new Technology ();
		technology.setAppTypeId("Mobile");
		List <Database> databaseList = createDataBase ();
		technology.setDatabases(databaseList);
		technologyList.add(technology);
		technology.setEmailSupported(true);
		List<ModuleGroup> frameWorkModuleList = createFramworksModules();
		technology.setFrameworks(frameWorkModuleList);
		List<ModuleGroup> jsLibrayModuleList = createJsFramWorkModules();
		technology.setJsLibraries(jsLibrayModuleList);
		List <ModuleGroup> modules = createModules ();
		technology.setModules(modules);
		technology.setName("Mobil Tech");
		List <Server> serverList = createServers ();
		technology.setServers(serverList);
		return technologyList;
	}

	/**
     * Returns the list of Server
     * @return
     */
	private static List<Server> createServers() {
		// TODO Auto-generated method stub
		List <Server> serverList = new ArrayList <Server> ();
		Server server = new Server ();
		server.setName("Web Server");
		return serverList;
	}

	/**
     * Returns the list of ModuleGroup
     * @return
     * @throws PhrescoException
     */
	private static List<ModuleGroup> createModules() {
		// TODO Auto-generated method stub
		List <ModuleGroup> modules = new ArrayList<ModuleGroup> ();
		ModuleGroup module = new ModuleGroup ();
		module.setArtifactId("Mobile JQUERY");
		module.setGroupId("Mobile Users");
		module.setName("Mobile Js lib");
		List <Module> versions= createjsVersions ();
		module.setVersions(versions);
		module.setCore(true);
		module.setVendor("Apache");
		List <Documentation> documentationList = createDocumentation ();
		module.setDocs(documentationList);
		return modules;

	}

	/**
     * Returns the list of ModuleGroup
     * @return
     * @throws PhrescoException
     */
	private static List<ModuleGroup> createJsFramWorkModules() {
		// TODO Auto-generated method stub
		List <ModuleGroup> jsLibraryModuleList = new ArrayList<ModuleGroup> ();
		ModuleGroup jsLibModule = new ModuleGroup ();
		jsLibModule.setArtifactId("Mobile JQUERY");
		jsLibModule.setGroupId("Mobile Users");
		jsLibModule.setName("Mobile Js lib");
		List <Module> versions= createjsVersions ();
		jsLibModule.setVersions(versions);
		jsLibModule.setCore(true);
		jsLibModule.setVendor("Apache");
		List <Documentation> documentationList = createDocumentation ();
		jsLibModule.setDocs(documentationList);
		return jsLibraryModuleList;
	}

	/**
     * Returns the list of Module
     * @return
     * @throws PhrescoException
     */
	private static List<Module> createjsVersions() {
		// TODO Auto-generated method stub
		List <Module> jsVersionList= new ArrayList <Module> ();
		Module module = new Module ();
		module.setName("1.2.2");
		return jsVersionList;
	}

	/**
     * Returns the list of ModuleGroup
     * @return
     * @throws PhrescoException
     */
	private static List<ModuleGroup> createFramworksModules() {
		// TODO Auto-generated method stub
		List <ModuleGroup> frameworkModuleList = new ArrayList<ModuleGroup> ();
		ModuleGroup frameworkModule = new ModuleGroup ();
		frameworkModule.setArtifactId("web");
		frameworkModule.setCore(true);
		List <Documentation> documentationList = createDocumentation ();
		frameworkModule.setDocs(documentationList);
		return frameworkModuleList;
	}

	/**
     * Returns the list of Documentation
     * @return
     * @throws PhrescoException
     */
	private static List<Documentation> createDocumentation() {
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
     * Returns the list of Database
     * @return
     * @throws PhrescoException
     */
	private static List<Database> createDataBase() {
		// TODO Auto-generated method stub
		List <Database> databaseList = new ArrayList<Database> ();
		Database database = new Database();
		database.setId(1);
		database.setName("Oracle");

        List<String> versions = new ArrayList<String>(2);
        versions.add("10g");
		database.setVersions(versions);
		database.setDescription("Oracle Database");
		databaseList.add(database);
		return databaseList;
	}

	/**
	 * Creates the application types objects as specified in the parameter
	 * @param apptypes
	 * @throws PhrescoException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<ApplicationType> appTypes) throws PhrescoException {
		//componentManager.createAppTypes(appTypes);
	    for (ApplicationType applicationType : appTypes) {
            String id = applicationType.getId();
            if (infoMap.containsKey(id)) {
                throw new PhrescoException(id + " already exist ");
            }
        }
        for (ApplicationType applicationType : appTypes) {
            infoMap.put(applicationType.getId(), applicationType);
        }
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ApplicationType> update(List<ApplicationType> appTypes) throws PhrescoException {
	    for (ApplicationType applicationType : appTypes) {
            String id = applicationType.getId();
            if (!infoMap.containsKey(id)) {
                throw new PhrescoException(id + " does not exist ");
            }
        }
        for (ApplicationType applicationType : appTypes) {
            infoMap.put(applicationType.getId(), applicationType);
        }
        return appTypes;
	}

	/**
	 * Deletes all the application types in the database
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}


	/**
	 * Returns the list of application types
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public ApplicationType get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
	    ApplicationType applicationType = infoMap.get(id);
        if (applicationType == null) {
            throw new PhrescoException(id + " does not exist ");
        }

        return applicationType;
	}

	/**
	 * Creates the application types objects as specified in the parameter
	 * @param apptypes
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id,
			ApplicationType appType) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REST_API_PATH_ID)
    public ApplicationType update(@PathParam(REST_API_PATH_PARAM_ID) String id, ApplicationType applicationType) throws PhrescoException {
        if (!id.equals(applicationType.getId())) {
            throw new PhrescoException("The ids does not match");
        }

        if (infoMap.containsKey(id)) {
            throw new PhrescoException(id + " is invalid");
        }

        infoMap.put(id, applicationType);

        return applicationType;
    }

	/**
	 * Deletes the customer as specified by the id
	 * @throws PhrescoException
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
	    if (infoMap.containsKey(id)) {
            throw new PhrescoException(id + " does not exist");
        }
        infoMap.remove(id);
	}

}
