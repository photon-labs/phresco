package com.photon.phresco.service.client.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class AdminRestCustomersTest implements ServiceConstants {

    public ServiceContext context = null;
    public ServiceManager serviceManager = null;
    
    @Before
    public void Initilaization() throws PhrescoException {
        context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
    }
    
    @Test
    public void testCreateCustomers() throws PhrescoException {
        List<Customer> customers = new ArrayList<Customer>();
        Customer customer = new Customer();
        customer.setId("test-customer");
        customer.setName("Test customer");
        customer.setValidFrom("");
        customer.setValidUpto("");
        customer.setDescription("");
        customer.setAddress("");
        customer.setContactNumber("");
        customer.setCountry("");
        customer.setZipcode("");
        customer.setState("");
        customers.add(customer);
        RestClient<Customer> customersClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        ClientResponse clientResponse = customersClient.create(customers);
        assertNotNull(clientResponse);
    }
    
    @Test
    public void getCustomers() throws PhrescoException {
        RestClient<Customer> customersClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        GenericType<List<Customer>> genericType = new GenericType<List<Customer>>(){};
        List<Customer> customers = customersClient.get(genericType);
        assertNotNull(customers);
    }
    
    
	@Test
    public void getCustomer() throws PhrescoException {
        String customerId = "test-customer";
        RestClient<Customer> customersClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        customersClient.setPath(customerId);
        GenericType<Customer> genericType = new GenericType<Customer>(){};
        Customer customer = customersClient.getById(genericType);
        assertNotNull(customer);
    }
    
    @Test
    public void updateCustomer() throws PhrescoException {
        String customerId = "test-customer";
        Customer customer = new Customer();
        customer.setId("test-customer");
        customer.setName("Test customer update");
        customer.setValidFrom("");
        customer.setValidUpto("");
        customer.setDescription("");
        customer.setAddress("");
        customer.setContactNumber("");
        customer.setCountry("");
        customer.setZipcode("");
        customer.setState("");
        RestClient<Customer> customersClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        customersClient.setPath(customerId);
        GenericType<Customer> genericType = new GenericType<Customer>() {};
        customersClient.updateById(customer, genericType);
    }
    
    @Test
    public void deleteCustomer() throws PhrescoException {
        String customerId = "test-customer";
        RestClient<Customer> customersClient = serviceManager.getRestClient(REST_API_ADMIN + REST_API_CUSTOMERS);
        customersClient.setPath(customerId);
        ClientResponse clientResponse = customersClient.deleteById();
        assertNotNull(clientResponse);
    }
}