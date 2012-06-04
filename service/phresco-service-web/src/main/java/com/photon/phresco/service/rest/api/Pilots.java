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
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_PILOTS)
    public class Pilots implements ServerConstants {
	private static final Logger S_LOGGER = Logger.getLogger(ProjectInfo.class);
	private static Map<String, ProjectInfo> infoMap = new HashMap<String, ProjectInfo>();

    static {
        createPilots();
    }

	/**
	 * Returns the list of pilots
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectInfo> list(@Context HttpServletRequest request,
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit,
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {

		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);

		//TODO: JEB -
		//Validate AuthToken
        //Validate Permissions
		return new ArrayList<ProjectInfo>(infoMap.values());
	}

	/**
     * Create ProjectInfo
     * @return
     */
	private static void createPilots() {
        // TODO Auto-generated method stub
	    ProjectInfo projectInfo =  new ProjectInfo();
	    projectInfo.setApplication("E-shopping");
	    projectInfo.setCode("e_sp");
	    projectInfo.setContentURLId("http:\\");
	    projectInfo.setDescription("E-Com shopping project");
	    projectInfo.setId("e_shop");
	    projectInfo.setName("shopping project");
	    projectInfo.setPilotid(1);
	    projectInfo.setPilotProjectName("on line shopping");
	    String[] projectURLS = new String[] {"http://" , "https://"};
	    projectInfo.setPilotProjectUrls(projectURLS);
	    projectInfo.setTechID("PHP");
	    Technology technology = createTechnology();
	    projectInfo.setTechnology(technology);
	    infoMap.put(projectInfo.getId(), projectInfo);
    }

	/**
     * Create Technology
     * @return
     */
	private static Technology createTechnology() {
        // TODO Auto-generated method stub
        List<Technology> technologyList = new ArrayList <Technology> ();
        Technology technology = new Technology ();
        technology.setAppTypeId("Mobile");
        List <Database> databaseList = populateDataBase ();
        technology.setDatabases(databaseList);
        technologyList.add(technology);
        technology.setEmailSupported(true);
        List<ModuleGroup> frameWorkModuleList = populateFramworksModules();
        technology.setFrameworks(frameWorkModuleList);
        List<ModuleGroup> jsLibrayModuleList = populateJsFramWorkModules();
        technology.setJsLibraries(jsLibrayModuleList);
        List <ModuleGroup> modules = populateModules ();
        technology.setModules(modules);
        technology.setName("Mobil Tech");
        List <Server> serverList = createServers ();
        technology.setServers(serverList);
        return technology;
    }

	/**
     * Create Server
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
     * Create ModuleGroup
     * @return
     * @throws PhrescoException
     */

    private static List<ModuleGroup> populateModules() {
        // TODO Auto-generated method stub
        List <ModuleGroup> modules = new ArrayList<ModuleGroup> ();
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
        return modules;
    }

    /**
     * Create ModuleGroup
     * @return
     */

    private static List<ModuleGroup> populateJsFramWorkModules() {
        // TODO Auto-generated method stub
        List <ModuleGroup> jsLibraryModuleList = new ArrayList<ModuleGroup> ();
        ModuleGroup jsLibModule = new ModuleGroup ();
        jsLibModule.setArtifactId("Mobile JQUERY");
        jsLibModule.setGroupId("Mobile Users");
        jsLibModule.setName("Mobile Js lib");
        List <Module> versions= populatejsVersions ();
        jsLibModule.setVersions(versions);
        jsLibModule.setCore(true);
        jsLibModule.setVendor("Apache");
        List <Documentation> documentationList = populateDocumentation ();
        jsLibModule.setDocs(documentationList);
        return jsLibraryModuleList;
    }
    /**
     * Create Module
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
     * Create ModuleGroup
     * @return
     * @throws PhrescoException
     */
    private static List<ModuleGroup> populateFramworksModules() {
        // TODO Auto-generated method stub
        List <ModuleGroup> frameworkModuleList = new ArrayList<ModuleGroup> ();
        ModuleGroup frameworkModule = new ModuleGroup ();
        frameworkModule.setArtifactId("web");
        frameworkModule.setCore(true);
        List <Documentation> documentationList = populateDocumentation ();
        frameworkModule.setDocs(documentationList);
        return frameworkModuleList;
    }
    /**
     * Create ModuleGroup
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
     * Create ModuleGroup
     * @return
     * @throws PhrescoException
     */
    private static List<Database> populateDataBase() {
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
	 * Creates the pilot objects as specified in the parameter
	 * @param pilots
	 * @throws PhrescoException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<ProjectInfo> projectInfos) throws PhrescoException {
	    for (ProjectInfo projectInfo : projectInfos) {
            String id = projectInfo.getId();
            if (infoMap.containsKey(id)) {
                throw new PhrescoException(id + " already exist ");
            }
        }

        for (ProjectInfo projectInfo : projectInfos) {
            infoMap.put(projectInfo.getId(), projectInfo);
        }
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectInfo> update(List<ProjectInfo> projectInfos) throws PhrescoException {
	    for (ProjectInfo projectInfo : projectInfos) {
            String id = projectInfo.getId();
            if (!infoMap.containsKey(id)) {
                throw new PhrescoException(id + " does not exist ");
            }
        }

        for (ProjectInfo projectInfo : projectInfos) {
            infoMap.put(projectInfo.getId(), projectInfo);
        }

        return projectInfos;
	}

	/**
	 * Deletes all the pilots in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}


	/**
	 * Returns the list of pilots
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public ProjectInfo get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
	    ProjectInfo projectInfo = infoMap.get(id);
        if (projectInfo == null) {
            throw new PhrescoException(id + " does not exist ");
        }

        return projectInfo;
	}

	/**
	 * Creates the pilot objects as specified in the parameter
	 * @param customers
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id,
			ProjectInfo pilot) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REST_API_PATH_ID)
    public ProjectInfo update(@PathParam(REST_API_PATH_PARAM_ID) String id, ProjectInfo projectInfo) throws PhrescoException {
        if (!id.equals(projectInfo.getId())) {
            throw new PhrescoException("The ids does not match");
        }

        if (infoMap.containsKey(id)) {
            throw new PhrescoException(id + " is invalid");
        }
        infoMap.put(id, projectInfo);
        return projectInfo;
    }

	/**
	 * Deletes the pilot as specified by the id
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


