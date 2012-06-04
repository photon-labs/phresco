package com.photon.phresco.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.service.api.LDAPManager;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.util.Credentials;

@Path("/authenticate")
public class AuthService {
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInfo authenticate(Credentials credentials) throws PhrescoException {
		PhrescoServerFactory.initialize();
       LDAPManager ldapManager = PhrescoServerFactory.getLDAPManager();
		return ldapManager.authenticate(credentials);
    }
}
