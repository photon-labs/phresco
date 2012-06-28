/*
 * ###
 * Phresco Service Implemenation
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
package com.photon.phresco.service.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.commons.model.Customer;

public class PhrescoServiceDataManagerTest {

	static PhrescoDataManager dataManager = null;
	static AdminManager adminManager= null;

	@BeforeClass
	public static void initialize() throws PhrescoException {
		PhrescoServerFactory.initialize();
		dataManager = PhrescoServerFactory.getPhrescoDataManager();
		adminManager = com.photon.phresco.service.api.PhrescoServerFactory.getAdminManager();
	}

	@Test
	public void testCustomer() throws PhrescoException {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = createCustomer("Photon", "Photon Infotech");
		customers.add(customer);
		adminManager.createCustomers(customers);
	}

	private Customer createCustomer(String name, String desc) {
		Customer customer = new Customer(name, desc);
		customer.setStatus(Constants.STATUS_OPEN);

		Date validFrom = new Date();
		//customer.setValidFrom(validFrom);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 3);
		//customer.setValidUpto(cal.getTime());

		return customer;
	}

}
