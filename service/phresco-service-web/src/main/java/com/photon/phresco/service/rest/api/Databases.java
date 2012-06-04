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
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_DATABASES)
public class Databases implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Technology.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	static {
		createDatabase();
	}
	/**
	 * Returns the list of Databases
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Database> list(@Context HttpServletRequest request,
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit,
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		List<Database> databaseList = createDatabase();
		return databaseList;
	}

	/**
	 * Creates the Database objects as specified in the parameter
	 * @param technologies
	 * @throws PhrescoException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Database> database) throws PhrescoException {
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Database> update(List<Database> database) throws PhrescoException {
		return database;
		
	}

	/**
	 * Deletes all the databases
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Returns the list of databases
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Database get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		return null;
	}
	/**
	 * Creates the Darabase objects as specified in the parameter
	 * @param technologies
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id,
			Database database) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public Database update(@PathParam(REST_API_PATH_PARAM_ID) String id, Database database) throws PhrescoException {
		return null;
	}

	/**
	 * Deletes the Database as specified by the id
	 * @throws PhrescoException
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
	   
	}

	

	private static  List<Database> createDatabase() {
		List<Database> databases = new ArrayList<Database>();
		
		List<String> versions = new ArrayList<String>(2);
		versions.add("5.5.1");
		versions.add("5.5");
		versions.add("5.1");
		versions.add("5.0");
		versions.add("4.1");
		versions.add("4.0");
		databases.add(new Database(1, "MySQL", versions, "My SQL DB"));
		
		versions = new ArrayList<String>(2);
		versions.add("11g Release 2");
		versions.add("11g Release 1");
		versions.add("10g Release 2");
		versions.add("10g Release 1");
		versions.add("9i Release 2");
		versions.add("9i Release 1");
		databases.add(new Database(2, "Oracle", versions, "Oracle DB"));

		versions = new ArrayList<String>(2);
		versions.add("2.0.4");
		versions.add("1.8.5");
		databases.add(new Database(3, "MongoDB", versions, "Mongo DB"));
		
		versions = new ArrayList<String>(2);
		versions.add("10");
		versions.add("9.7");
		versions.add("9.5");
		versions.add("9");
		databases.add(new Database(4, "DB2", versions, "DB2 DB"));
		
		versions = new ArrayList<String>(2);
		versions.add("SQL Server 2012");
		versions.add("SQL Server 2008 R2");
		versions.add("SQL Server 2008");
		versions.add("SQL Server 2005");
		databases.add(new Database(5, "MSSQL", versions, "MSSQL DB"));
		
		return databases;
	}


}
