/*
 * ###
 * Service Auth
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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.LDAPManager;
import com.photon.phresco.service.impl.LDAPManagerImpl;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.util.Credentials;

@Path("/authenticate")
public class AuthService {
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User authenticate(Credentials credentials) throws PhrescoException {
		LDAPManager ldapManager = ConfigFactory.getLDAPManager();
		return ldapManager.authenticate(credentials);
    }
}

class ConfigFactory {
	
    private static final String SERVER_CONFIG_FILE = "server.config";
    private static LDAPManager ldapManager 				= null;
    private static ServerConfiguration serverConfig     = null;

    public static synchronized LDAPManager getLDAPManager() throws PhrescoException {
        if (serverConfig == null) {
            serverConfig = new ServerConfiguration(SERVER_CONFIG_FILE);
            ldapManager = new LDAPManagerImpl(serverConfig);
        }
        
        return ldapManager;
    }
    
}
