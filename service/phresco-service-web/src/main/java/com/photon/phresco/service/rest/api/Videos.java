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

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_VIDEOS)
public class Videos implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Videos.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	
	private static Map<String, VideoInfo> infoMap = new HashMap<String, VideoInfo>(8);
	
	static{
		createDownLoads();
	}
	
	private static void createDownLoads() {
		VideoType type = new VideoType("mpeg", "http://java.mpeg", "mpeg");
		VideoType type1 = new VideoType("ogg", "http://java.ogg", "ogg");
		List<VideoType> videoList = new ArrayList<VideoType>();
		videoList.add(type);
		List<VideoType> videoList1 = new ArrayList<VideoType>();
		videoList1.add(type1);
		VideoInfo info = new VideoInfo("java project creation", "java project creation", "http://java.png", "java", "java project creation", videoList);
		info.setId("1");
		VideoInfo info2 = new VideoInfo("java project deploy", "java project deploy", "http://java.png", "java", "java project deploy", videoList1);
		info2.setId("2");
		infoMap.put(info.getId(), info);
		infoMap.put(info2.getId(), info2);
	}
	
	/**
	 * Returns the list of videos
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VideoInfo> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
		
        return new ArrayList<VideoInfo>(infoMap.values());
	}
	
	/**
	 * Creates the video objects as specifed in the parameter
	 * @param videos
	 * @throws PhrescoException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<VideoInfo> videos) throws PhrescoException {
		for (VideoInfo videoInfo : videos) {
			String id = videoInfo.getId();
			if (infoMap.containsKey(id)) {
				throw new PhrescoException(id + " already exist ");
			}
		}

		for (VideoInfo videoInfo : videos) {
			infoMap.put(videoInfo.getId(), videoInfo);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<VideoInfo> update(List<VideoInfo> videos) throws PhrescoException {
		for (VideoInfo videoInfo : videos) {
			String id = videoInfo.getId();
			if (!infoMap.containsKey(id)) {
				throw new PhrescoException(id + " does not exist ");
			}
		}
		
		for (VideoInfo videoInfo : videos) {
			infoMap.put(videoInfo.getId() , videoInfo);
		}
		
		return videos;
	}
	
	/**
	 * Deletes all the videos in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	

	/**
	 * Returns the list of videos
	 * @return
	 * @throws PhrescoException 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public VideoInfo get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		VideoInfo videoInfo = infoMap.get(id);
		if (videoInfo == null) {
			throw new PhrescoException(id + " does not exist ");
		}
		
		return videoInfo;
	}
	
	/**
	 * Creates the video objects as specified in the parameter
	 * @param videos
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id, 
			VideoInfo video) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public VideoInfo update(String id, VideoInfo info) throws PhrescoException {
		if (!id.equals(info.getId())) {
			throw new PhrescoException("The ids does not match");
		}
		
		if (!infoMap.containsKey(id)) {
			throw new PhrescoException(id + " is invalid");
		}
	
		infoMap.put(id, info);
		
		return info;
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
