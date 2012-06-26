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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.service.model.User;
import com.photon.phresco.service.model.Users;
import com.photon.phresco.service.util.AuthenticationUtil;
import com.photon.phresco.util.Credentials;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@Path("/login")
public class LoginService {
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInfo login(Credentials credentials) throws PhrescoException {
		Client client = Client.create();
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		WebResource resource = client.resource(repoMgr.getAuthServiceURL() + ServerConstants.AUTHENTICATE);
        resource.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, credentials);
        GenericType<UserInfo> genericType = new GenericType<UserInfo>() {};
        UserInfo userInfo = response.getEntity(genericType);
        fillUserRoles(userInfo);
        AuthenticationUtil authTokenUtil = AuthenticationUtil.getInstance();
        userInfo.setToken(authTokenUtil.generateToken(credentials.getUsername()));
        return userInfo;
    }
	
	private void fillUserRoles(UserInfo userInfo) throws PhrescoException {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("UserRoleMapping.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
          	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Users users = (Users) jaxbUnmarshaller.unmarshal(inputStream);
			List<User> userList = users.getUsers();
			for (User userobj : userList) {
				if (userobj.getId().equals(userInfo.getUserName())) {
					List<String> roles = userobj.getRoles();
					userInfo.setRoles(roles);
					break;
				}
			}
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
}
