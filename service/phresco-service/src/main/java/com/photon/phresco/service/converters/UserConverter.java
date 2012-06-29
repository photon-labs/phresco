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
			user.setRoles(getObjects(mongoOperation, "roles", Role.class, dao.getRoleIds()));
		}
		return user;
	}
	
	public <X> List<X> getObjects(MongoOperations mongoOperation, String collectionName, Class<X> type, List<String> ids) {
		List<X> objects = new ArrayList<X>(ids.size() * 2);
		for (String id : ids) {
			X object = mongoOperation.findOne(collectionName, new Query(Criteria.whereId().is(id)), type);
			System.out.println("id = " + id + " object " + object);
			objects.add(object);
		}
		System.out.println("return... objects.... " + objects);
		return objects; 
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
			System.out.println(customers);
			List<String> customerIds = new ArrayList<String>();
			for (Customer customer : customers) {
				customerIds.add(customer.getId());
			}

			dao.setCustomerIds(customerIds);
		}
		return dao;
	}
	
}
