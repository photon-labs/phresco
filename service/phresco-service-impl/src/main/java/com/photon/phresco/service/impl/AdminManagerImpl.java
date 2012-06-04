package com.photon.phresco.service.impl;

import java.util.List;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.DBManager;
import com.photon.phresco.service.api.AdminManager;
import com.photon.phresco.service.impl.DBManagerImpl;

public class AdminManagerImpl implements AdminManager {

	DBManager dbManager = null;

	public AdminManagerImpl() {
		dbManager = new DBManagerImpl();
	}

	public List<Customer> findCustomers() throws PhrescoException {
		return dbManager.findCustomers();
	}

	public Customer getCustomer(String id) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public void createCustomers(List customers) throws PhrescoException {
		//TODO: check if these customers does not exist

	}

	public List updateCustomers(List customers) throws PhrescoException {
		//TODO: check if these customers already exist
		return null;
	}

	public void deleteCustomer(String id) throws PhrescoException {
		// TODO Auto-generated method stub
	}

}