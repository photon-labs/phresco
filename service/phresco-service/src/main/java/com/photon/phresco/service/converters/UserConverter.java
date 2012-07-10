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

package com.photon.phresco.service.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.Converter;
import com.photon.phresco.service.dao.UserDAO;
import com.photon.phresco.util.ServiceConstants;

public class UserConverter implements Converter<UserDAO, User>, ServiceConstants {

	@Override
	public User convertDAOToObject(UserDAO dao, MongoOperations mongoOperation) throws PhrescoException {
		User user = new User();
		user.setId(dao.getId());
		List<String> customerIds = dao.getCustomerIds();
		if (customerIds != null) {
			user.setCustomers(getObjects(mongoOperation, CUSTOMERS_COLLECTION_NAME, Customer.class, dao.getCustomerIds()));
		}
		List<String> roleIds = dao.getRoleIds();
		if (roleIds != null) {
			user.setRoles(getObjects(mongoOperation, ROLES_COLLECTION_NAME, Role.class, dao.getRoleIds()));
		}
		return user;
	}
	
	@Override
	public UserDAO convertObjectToDAO(User user) throws PhrescoException {
		
		UserDAO dao = new UserDAO();
		dao.setId(user.getId());
		List<Role> roles = user.getRoles();
		if (roles  != null) { 
			List<String> roleIds = new ArrayList<String>(roles.size() * 2);
			for (Role role : roles) {
				roleIds.add(role.getId());
			}

			dao.setRoleIds(roleIds);
		}
		List<Customer> customers = user.getCustomers();
		if (customers != null) {
			List<String> customerIds = new ArrayList<String>();
			for (Customer customer : customers) {
				customerIds.add(customer.getId());
			}

			dao.setCustomerIds(customerIds);
		}
		return dao;
	}
	
	public static <X> List<X> getObjects(MongoOperations mongoOperation, String collectionName, Class<X> type, List<String> ids) {
		List<X> objects = new ArrayList<X>(ids.size() * 2);
		for (String id : ids) {
			X object = mongoOperation.findOne(collectionName, new Query(Criteria.whereId().is(id)), type);
			objects.add(object);
		}
		return objects; 
	}
}
