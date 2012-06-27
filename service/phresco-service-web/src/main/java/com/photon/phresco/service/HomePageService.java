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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.ServiceConstants;

/**
 * Example resource class hosted at the URI path "/video"
 */
@Path("/homepagevideos")
public class HomePageService implements ServiceConstants {
	private static final Logger S_LOGGER = Logger.getLogger(HomePageService.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<VideoInfo> getHomePageVideo() throws PhrescoException,
			IOException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method HomePageService.getHomePageVideo()");
		}
		PhrescoServerFactory.initialize();
		Gson gson = new Gson();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		String videoInfoJSON = repoMgr.getArtifactAsString(repoMgr.getHomePageJsonFile());
		// convert the json string back to object
		Type type = new TypeToken<List<VideoInfo>>() {
		}.getType();
		List<VideoInfo> videoInfoList = gson.fromJson(videoInfoJSON, type);
		return videoInfoList;
	}

	@GET
	@Path("/webm")
	@Produces({ "video/webm" })
	public StreamingOutput webmVideo(@QueryParam("file") String filename)
			throws FileNotFoundException {
		return new ServiceOutput(filename);
	}

	@GET
	@Path("/mp4")
	@Produces({ "video/mp4" })
	public StreamingOutput mp4Video(@QueryParam("file") String filename)
			throws FileNotFoundException {
		return new ServiceOutput(filename);
	}

	@GET
	@Path("/ogv")
	@Produces({ "video/webm" })
	public StreamingOutput ogvVideo(@QueryParam("file") String filename)
			throws FileNotFoundException {
		return new ServiceOutput(filename);
	}

	@GET
	@Path("/ogg")
	@Produces({ "video/ogg" })
	public StreamingOutput oggVideo(@QueryParam("file") String filename)
			throws FileNotFoundException {
		return new ServiceOutput(filename);
	}

	class ServiceOutput implements StreamingOutput {
		String projectPath = "";

		public ServiceOutput(String projectPath) {
			this.projectPath = projectPath;
		}

		public void write(OutputStream output) throws IOException {
			if (isDebugEnabled) {
				S_LOGGER.debug("Entering Method HomePageService.write(OutputStream output)");
			}
			InputStream fis = null;
			try {
				PhrescoServerFactory.initialize();
				String path = PhrescoServerFactory.getRepositoryManager()
						.getRepositoryURL();
				URL url = new URL(path + projectPath);
				fis = url.openStream();
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = fis.read(buf)) != -1) {
					output.write(buf, 0, i);
				}
			} catch (Exception e) {
				S_LOGGER.error("Error During Stream write()"+e);
				throw new WebApplicationException(e);
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		}

	}
}