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