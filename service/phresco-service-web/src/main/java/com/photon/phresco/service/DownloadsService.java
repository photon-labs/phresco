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
package com.photon.phresco.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;

/**
 * Example resource class hosted at the URI path "/downloads"
 */
@Path("/downloads")
public class DownloadsService implements ServerConstants {

	private static final String SOFTWARE_REPO_PATH = "/softwares/info/1.0/info-1.0.json";
	private static final Logger S_LOGGER = Logger.getLogger(DownloadsService.class);
	
    @GET
    @Path("{osName}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<DownloadInfo> getAvailableDownloads(@PathParam("osName") String osName) throws PhrescoException, JSONException, FileNotFoundException {
    	S_LOGGER.info("Retrieving downloads ");
    	List<DownloadInfo> downloadList = new ArrayList<DownloadInfo>();
    	PhrescoServerFactory.initialize();
    	RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		String downloadInfoJSON = repoMgr.getArtifactAsString(SOFTWARE_REPO_PATH);
    	Type type = new TypeToken<List<DownloadInfo>>() {}.getType();
		Gson gson = new Gson();
		List<DownloadInfo> downloadInfoList = gson.fromJson(downloadInfoJSON, type);
    	for (DownloadInfo downloadInfo : downloadInfoList) {
				String[] platform = downloadInfo.getPlatform();
				for (String string : platform) {
					if(string.equals(osName)){
						downloadList.add(downloadInfo);
					}
				}
    	}
    	return downloadList;
    }
}