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
package com.photon.phresco.service.data.api;

import java.util.List;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;


public interface AdminManager {
	
	
	List<Customer> findCustomers() throws PhrescoException;
	
	Customer getCustomer(String id) throws PhrescoException;
	
	void createCustomers(List<Customer> customers) throws PhrescoException;
	
	List<Customer> updateCustomers(List<Customer> customers) throws PhrescoException;
	
	void deleteCustomer(String id) throws PhrescoException;

	
//	List<User> findUsers() throws PhrescoException;
//
//	User getUser(String id) throws PhrescoException;
//
//	void createUsers(List<User> customers) throws PhrescoException;
//	
//	void updateUsers(List<User> customers) throws PhrescoException;
//	
//	void deleteUser(String id) throws PhrescoException;

}
