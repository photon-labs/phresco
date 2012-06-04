package com.photon.phresco.service.api;

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
