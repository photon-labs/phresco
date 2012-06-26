/*
 * ###
 * Phresco Service
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
package com.photon.phresco.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import javax.ws.rs.core.MediaType;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.model.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ManageUserRoleUtil {

	//add UserRoles
	public void addUserRole(User user) throws PhrescoException {
		Client client = Client.create();
		WebResource resource = client
				.resource("http://localhost:3030/service/userrole/add");
		resource.accept(MediaType.APPLICATION_JSON_TYPE);
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON_TYPE).post(
						ClientResponse.class, user);
		if (response.getStatus() == 204) {
			System.out.println("Process completed successfully...");
		} else {
			System.out.println("Process failed...");
		}
	}

	//edit UserRoles
	public void editUserRole(User user) throws PhrescoException {
		Client client = Client.create();
		WebResource resource = client
				.resource("http://localhost:3030/service/userrole/edit");
		resource.accept(MediaType.APPLICATION_JSON_TYPE);
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON_TYPE).post(
						ClientResponse.class, user);
		if (response.getStatus() == 204) {
			System.out.println("Process completed successfully...");
		} else {
			System.out.println("Process failed...");
		}
	}

	public static void main(String[] args) throws PhrescoException, IOException {
		ManageUserRoleUtil util = new ManageUserRoleUtil();
		User user = new User();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		System.out
				.println("Please enter the type of operation from the below list");
		System.out.println("1. Add User Role");
		System.out.println("2. Edit User Role");
		String opType = br.readLine();
		if (!opType.equals("1") && !opType.equals("2")) {
			System.err.println("Invalid option...");
		}

		br = new BufferedReader(isr);
		System.out.println("Please enter the user id : ");
		String userID = br.readLine();

		br = new BufferedReader(isr);
		System.out.println("Please enter the role name : ");
		String roleName = br.readLine();
		user.setId(userID);
		user.setRoles(Collections.singletonList(roleName));
		if (opType.equals("1")) {
			util.addUserRole(user);
		} else if (opType.equals("2")) {
			util.editUserRole(user);
		}
	}
}