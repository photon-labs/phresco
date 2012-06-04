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
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_DOWNLOADS)
public class Downloads implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Downloads.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	
	private static Map<String, DownloadInfo> infoMap = new HashMap<String, DownloadInfo>(8);
	
	static {
		createDownLoads();
	}

	private static void createDownLoads() {
		// TODO Auto-generated method stub
		String[] platForm = new String[] {"windows xp" , "windows7"};
		String[] appliesTo = new String[] {"java" , "html5"};
		DownloadInfo info = new DownloadInfo("tomcat", "tomcat server", "7.0.25", "http://apache","server",appliesTo ,platForm);
		DownloadInfo info2 = new DownloadInfo("jboss", "jboss server", "7.0.25", "http://jboss", "server", appliesTo, platForm);
		infoMap.put(info.getId(), info);
		infoMap.put(info2.getId(), info2);
	}
	
	/**
	 * Returns the list of downloads
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DownloadInfo> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
        
        return new ArrayList<DownloadInfo>(infoMap.values());
	}
	

	/**
	 * Creates the download objects as specifed in the parameter
	 * @param downloads
	 * @throws PhrescoException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<DownloadInfo> downloads) throws PhrescoException {
		for (DownloadInfo downloadInfo : downloads) {
			String id = downloadInfo.getId();
			if (infoMap.containsKey(id)) {
				throw new PhrescoException(id + " already exist ");
			}
		}

		for (DownloadInfo downloadInfo : downloads) {
			infoMap.put(downloadInfo.getId(), downloadInfo);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DownloadInfo> update(List<DownloadInfo> downloads) throws PhrescoException {
		for (DownloadInfo downloadInfo : downloads) {
			String id = downloadInfo.getId();
			if (!infoMap.containsKey(id)) {
				throw new PhrescoException(id + " does not exist ");
			}
		}

		for (DownloadInfo downloadInfo : downloads) {
			infoMap.put(downloadInfo.getId(), downloadInfo);
		}
		
		return downloads;
	}
	
	/**
	 * Deletes all the downloads in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	

	/**
	 * Returns the list of downloads
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public DownloadInfo get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		DownloadInfo downloadInfo = infoMap.get(id);
		if (downloadInfo == null) {
			throw new PhrescoException(id + " does not exist ");
		}
		
		return downloadInfo;
	}
	
	/**
	 * Creates the download objects as specified in the parameter
	 * @param downloads
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id, 
			DownloadInfo download) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public DownloadInfo update(@PathParam(REST_API_PATH_PARAM_ID) String id, DownloadInfo download) throws PhrescoException {
		if (!id.equals(download.getId())) {
			throw new PhrescoException("The ids does not match");
		}
		
		if (!infoMap.containsKey(id)) {
			throw new PhrescoException(id + " is invalid");
		}
	
		infoMap.put(id, download);
		
		return download;
	}
	
	/**
	 * Deletes the download as specified by the id
	 * @throws PhrescoException 
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		if (!infoMap.containsKey(id)) {
			throw new PhrescoException(id + " does not exist");
		}
		
		infoMap.remove(id);
	}
	
}
