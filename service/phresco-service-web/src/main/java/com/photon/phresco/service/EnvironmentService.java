package com.photon.phresco.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.photon.phresco.configuration.Environment;
import com.photon.phresco.service.model.ServerConstants;

@Path("/settings/env")
public class EnvironmentService implements ServerConstants {

    private static final Logger S_LOGGER = Logger.getLogger(EnvironmentService.class);
    private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Environment> getEnvInfos()throws Exception {
        Environment enviromentInfos = new Environment("Production", "Production Environment is used for Development purpose only", true);
        List<Environment> envs = new ArrayList<Environment>();
        envs.add(enviromentInfos);
        return envs;
    }
}

