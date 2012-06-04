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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.ProjectService;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.projects.ProjectServiceFactory;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;

/**
 * Phresco Service Class hosted at the URI path "/api"
 */

@Path("/apps")
public class PhrescoService {
	private static final Logger S_LOGGER = Logger.getLogger(PhrescoService.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ApplicationType> getApplicationTypes() throws PhrescoException {
		RepositoryManager repManager = PhrescoServerFactory.getRepositoryManager();
		String repositoryURL = repManager.getRepositoryURL();
		return repManager.getApplicationTypes();
		
		//return PhrescoServerFactory.getDBManager().getApplicationTypes();
	}

	@POST
	@Produces("application/zip")
	@Consumes(MediaType.APPLICATION_JSON)
	public StreamingOutput createProject(ProjectInfo projectInfo) throws PhrescoException, IOException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method PhrescoService.createProject(ProjectInfo projectInfo)");
		}
		String projectPathStr = "";
		File projectPath = null;
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("createProject() ProjectInfo=" + projectInfo.getCode());
			}
			ProjectService projectService = ProjectServiceFactory.getNewProjectService(projectInfo);
			projectPath = projectService.createProject(projectInfo);
			projectPathStr = projectPath.getPath();

			if (isDebugEnabled) {
				S_LOGGER.debug("Project Path = " + projectPathStr);
			}

			ArchiveUtil.createArchive(projectPathStr, projectPathStr + ".zip", ArchiveType.ZIP);
//			FileUtil.delete(projectPath);
			return new ServiceOutput(projectPathStr);
		} catch (Exception pe) {
			S_LOGGER.error("Error During createProject(projectInfo)", pe);
			throw new PhrescoException(pe);
			// //TODO: Need to design a proper way to throw the error response
			// to client
		}
	}

	@POST
	@Path("/update")
	@Produces("application/zip")
	@Consumes(MediaType.APPLICATION_JSON)
	public StreamingOutput updateProject(ProjectInfo projectInfo) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method PhrescoService.updateProject(ProjectInfo projectInfo)");
			S_LOGGER.debug("updateProject() ProjectInfo=" + projectInfo.getCode());
		}
		String projectPathStr = "";
		try {
			ProjectService projectService = ProjectServiceFactory.getProjectService(projectInfo);
			File projectPath = projectService.updateProject(projectInfo);
			projectPathStr = projectPath.getPath();
			if (isDebugEnabled) {
				S_LOGGER.debug("updateProject() ProjectPath=" + projectPathStr);
			}
			ArchiveUtil.createArchive(projectPathStr, projectPathStr + ".zip", ArchiveType.ZIP);
		} catch (Exception pe) {
			S_LOGGER.error("Error During updateProject(projectInfo)" + pe);
			throw new PhrescoException(pe);
			// //TODO: Need to design a proper way to throw the error response
			// to client
		}

		return new ServiceOutput(projectPathStr);
	}
	
	@POST
	@Path("/updatedocs")
	@Produces("application/zip")
	@Consumes(MediaType.APPLICATION_JSON)
	public StreamingOutput updateDoc(ProjectInfo projectInfo) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method PhrescoService.updateDoc(ProjectInfo projectInfo)");
			S_LOGGER.debug("updateProject() ProjectInfo=" + projectInfo.getCode());
		}
		String projectPathStr = "";
		try {
			ProjectService projectService = ProjectServiceFactory.getProjectService(projectInfo);
			File projectPath = projectService.updateDocumentProject(projectInfo);
			projectPathStr = projectPath.getPath();
			ArchiveUtil.createArchive(projectPathStr, projectPathStr + ".zip", ArchiveType.ZIP);
		} catch (Exception pe) {
			S_LOGGER.error("Error During updateProject(projectInfo)" + pe);
			throw new PhrescoException(pe);
		}
		return new ServiceOutput(projectPathStr);
	}
	
	class ServiceOutput implements StreamingOutput {
		String projectPath = "";

		public ServiceOutput(String projectPath) {
			this.projectPath = projectPath;
		}

		public void write(OutputStream output) throws IOException{
			if (isDebugEnabled) {
				S_LOGGER.debug("Entering Method PhrescoService.write(OutputStream output)");
			}
			FileInputStream fis = null;
			File path = new File(projectPath);
			if (isDebugEnabled) {
				S_LOGGER.debug("PhrescoService.write() FILe PATH = " + path.getPath());
			}
			try {
				fis = new FileInputStream(projectPath + ".zip");
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = fis.read(buf)) != -1) {
					output.write(buf, 0, i);
				}
			} catch (Exception e) {
				S_LOGGER.error("Error During Stream write()", e);
				throw new WebApplicationException(e);
			}
			// //TODO: Temporay File path deleted. Need to revisit the logic
			finally {
				if (fis != null) {
					fis.close();
					FileUtils.deleteDirectory(path.getParentFile());
				}
			}
		}
	}

}