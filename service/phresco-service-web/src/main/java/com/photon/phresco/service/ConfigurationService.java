package com.photon.phresco.service;

import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;

/**
 * Example resource class hosted at the URI path "/apps"
 */
@Path("/settings")
public class ConfigurationService implements ServerConstants {
	private static final Logger S_LOGGER = Logger.getLogger(ConfigurationService.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	static {
		try {
			PhrescoServerFactory.initialize();
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SettingsTemplate> getSettingsTemplates() throws PhrescoException, JSONException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ConfigurationService.getSettingsTemplates()");
		}
		RepositoryManager repoManager = PhrescoServerFactory.getRepositoryManager();
		String settingsJson = repoManager.getArtifactAsString(repoManager.getSettingConfigFile());
		Gson gson = new Gson();
		Type type = new TypeToken<List<SettingsTemplate>>(){}.getType();
		List<SettingsTemplate> settings = (List<SettingsTemplate>)gson.fromJson(settingsJson, type);
		return settings;
	}
	
}
