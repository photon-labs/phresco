package com.photon.phresco.service.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.data.api.AdminManager;
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
		customer.setValidFrom(validFrom);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 3);
		customer.setValidUpto(cal.getTime());

		return customer;
	}

}
