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

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;

/**
 * Example resource class hosted at the URI path "/apps"
 */
@Path("/pilots")
public class PilotProjectsService implements ServerConstants {
	
	private static final Logger S_LOGGER = Logger.getLogger(PilotProjectsService.class);

    static {
        try {
            PhrescoServerFactory.initialize();
        } catch (PhrescoException e) {
           S_LOGGER.fatal("Couldn't initialize Phresco server factory", e);
        }
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public List<ProjectInfo> getPilotProjects(String techId) throws PhrescoException, JSONException {
    	S_LOGGER.info("Retrieving pilot projects for technology " + techId);
        RepositoryManager repoManager = PhrescoServerFactory.getRepositoryManager();
        return repoManager.getPilotProjects(techId);
    }

}