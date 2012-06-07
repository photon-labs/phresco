package com.photon.phresco.service.client.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Permission;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.GenericType;

public class AdminRestCustomersTest {

	public ServiceContext context = null;
	public ServiceManager serviceManager = null;

	@Before
	public void Initilaization() {
		context = new ServiceContext();
		context.put(ServiceClientConstant.SERVICE_URL,
				"http://localhost:3030/service");
		context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
		context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}

	@Test
	public void testGetcustomers() {
		try {
			serviceManager = ServiceClientFactory.getServiceManager(context);
			RestClient<Customer> CustomersClient = serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS);
			CustomersClient.setType(MediaType.APPLICATION_JSON);
			GenericType<List<Customer>> genericType = new GenericType<List<Customer>>() {};
			List<Customer> list = CustomersClient.get(genericType);
			for (Customer customer : list) {
				System.out.println("permission==" + customer.getLicenseType());
			}
		} catch (PhrescoException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testPostCustomers() throws PhrescoException {

		Set<Permission> permissions = new HashSet<Permission>();

		Permission permission = new Permission();
		permission.setCreationDate(new Date());
		permission.setDescription("WORKING");
		permissions.add(permission);

		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setSystem(true);
		role.setGlobal(true);
		role.setPermissions(permissions);
		roles.add(role);

		Set<User> users = new HashSet<User>();
		User user = new User();
		user.setName("surendar");
		user.setDescription("developer");
		user.setStatus(1);
		user.setRoles(roles);
		users.add(user);

		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer();
		customer.setCreationDate(new Date());
		customer.setValidUpto(new Date());
		customer.setType(1);
		customer.setStatus(1);
		customer.setUsers(users);
		customers.add(customer);

		serviceManager = ServiceClientFactory.getServiceManager(context);
		String customerJson = new Gson().toJson(customers);
		RestClient<Customer> customerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS);
		customerClient.setAccept(MediaType.APPLICATION_JSON);
		customerClient.setType(MediaType.APPLICATION_JSON);
		customerClient.create(customerJson);
		
	}

	
	  @Ignore
	  public void testPutCustomers() throws PhrescoException {
	 
	  List<Customer> customers = new ArrayList<Customer>();
	  Set<User> users =new HashSet<User>(); 
	  Set<Role> roles = new HashSet<Role>();
	  Set<Permission> permissions= new HashSet<Permission>();
	  
	  
	  Permission permission =new Permission();
	  permission.setCreationDate(new Date()); 
	  permission.setDescription("WORKING1");
	  permission.setName("phresco1");
	  permissions.add(permission);
	  
	  Role role =new Role();
	  role.setSystem(false); 
	  role.setGlobal(true);
	  role.setName("role2");
	  role.setPermissions(permissions); 
	  roles.add(role);
	 
	  User user= new User(); 
	  user.setName("surendar2");
	  user.setDescription("Archetecture1"); 
	  user.setStatus(2);
	  user.setRoles(roles); 
	  users.add(user);
	  
	  Customer customer = new Customer(); 
	  customer.setCreationDate(new Date());
	  customer.setType(10); 
	  customer.setStatus(1);
	  customer.setName("customer1");
	  customer.setDescription("description1");
	  customer.setUsers(users);
	  customer.setId("2c909b4736e909b40136e91ddd620007");
	  
	  serviceManager = ServiceClientFactory.getServiceManager(context);
	  List<Customer> Customers = new ArrayList<Customer>();
	  Customers.add(customer); 
	  String customerJson = new Gson().toJson(Customers); 
	  RestClient<Customer> customerClient = serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS);
	  customerClient.setType(MediaType.APPLICATION_JSON);
	  customerClient.setAccept(MediaType.APPLICATION_JSON);
	  customerClient.update(customerJson);
	  
	  }
	  
	  
	  @Ignore
	  public void testGetCustomerByID() { try { String id = "2c909b4736e8d6280136e8da51b70003";
	  serviceManager = ServiceClientFactory.getServiceManager(context);
	  RestClient<Customer> customerClient =serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS+"/"+id);
	  customerClient.setType(MediaType.APPLICATION_JSON);
	  GenericType<List<Customer>> genericType = new GenericType<List<Customer>>() {};
	  List<Customer> list =customerClient.get(genericType); 
	  for (Customer Customer : list) {
	  System.out.println("name == " + Customer.getName());
	  } } catch
	  (PhrescoException e) { e.printStackTrace(); 
	  } }
	  
	  @Ignore
	  public void testPutCustomerById() throws PhrescoException {
		  String id = "2c909b4736e8d6280136e8da51b70003"; 
		  List<Customer> customers = new ArrayList<Customer>();
	  Set<User> users = new HashSet<User>(); 
	  Set<Role> roles = new HashSet<Role>(); 
	  
	  
	  Permission permission = new Permission(); 
	  Set<Permission> permissions =new HashSet<Permission>();
	  permission.setCreationDate(new Date());
	  permission.setDescription("Manage Activities");
	  permission.setName("Auditor11"); 
	  permissions.add(permission); 
	  Role role =new Role(); 
	  role.setCreationDate(new Date()); 
	  role.setGlobal(true);
	  role.setName("Employee11"); 
	  role.setSystem(false);
	  role.setDescription("GENERAL11");
	  role.setPermissions(permissions); 
	  roles.add(role);
	  User user= new User();
	  user.setName("suren11");
	  user.setDescription("developer11"); 
	  user.setStatus(2);
	  user.setRoles(roles); 
	  users.add(user);
	  
	  Customer customer = new Customer();
	  customer.setCreationDate(new Date()); 
	  customer.setType(1);
	  customer.setStatus(2);
	  customer.setName("cust");
	  customer.setUsers(users); 
	  customer.setId(id);
	 customers.add(customer);
	  serviceManager = ServiceClientFactory.getServiceManager(context);
	  
	  String customerJson = new Gson().toJson(customers); 
	  RestClient<Customer>customerClient =serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS); 
	  customerClient.setAccept(MediaType.APPLICATION_JSON);
	  customerClient.setType(MediaType.APPLICATION_JSON);
	  customerClient.update(customerJson);
	  }
	  
	  
	  @Ignore
	  public void testDeleteCustomerById() throws PhrescoException {
	  String id = "2c909b4736e909b40136e91367e10003"; 
	  serviceManager = ServiceClientFactory.getServiceManager(context); 
	  RestClient<Customer> customerClient =serviceManager.getRestClient(RestResourceURIs.REST_API_CUSTOMERS);
	  customerClient.delete(id);
	 
	  }
	 
}
