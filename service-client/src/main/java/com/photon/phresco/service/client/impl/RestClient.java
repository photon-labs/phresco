package com.photon.phresco.service.client.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Technology;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RestClient<E> {
	
	private static final Logger S_LOGGER= Logger.getLogger(RestClient.class);
	private WebResource resource = null;
	private Builder builder = null;

	public RestClient(String serverUrl) {
		Client client = ClientHelper.createClient();
		resource = client.resource(serverUrl);
	}
	
	/**
	 * Given additional path added to the URI of the web resource 
	 * @param id
	 */
	public void setPath(String path) {
		S_LOGGER.debug("Entered into RestClient.setPath(String path)" + path);
		resource = resource.path(path);
	}
	
	/**
	 * Add an HTTP header and value
	 * @param key
	 * @param value
	 */
	public void addHeader(String key, String value) {
		S_LOGGER.debug("Entered into RestClient.addHeader(String key, String value)");
		resource.header(key, value);
	}
	
	/**
	 * Create a new WebResource from this web resource with an additional query parameter added to the URI of this web resource
	 * @param key
	 * @param value
	 */
	public void addQueryString(String key, String value) {
		S_LOGGER.debug("Entered into RestClient.addQueryString(String key, String value)");
		resource.queryParam(key, value);
	}
	
	/**
	 * Create a new WebResource from this web resource with additional query parameters added to the URI of this web resource
	 * @param headers
	 */
	public void addQueryStrings(Map<String, String> headers) {
		S_LOGGER.debug("Entered into RestClient.addQueryStrings(Map<String, String> headers)");
		Set<String> keySet = headers.keySet();
		MultivaluedMap<String, String> queryStrings = new MultivaluedMapImpl();
		for (String key : keySet) {
			queryStrings.add(key, headers.get(key));
		}
		resource.queryParams(queryStrings);
	}
	
	/**
	 * Get List of objects for the specified generic type object
	 * @param genericType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> get(GenericType<List<E>> genericType) {
		S_LOGGER.debug("Entered into RestClient.get(Type type)");
		return get(genericType, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Get List of objects for the specified generic type object
	 * @param genericType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> get(GenericType<List<E>> genericType, String accept, String type) {
		S_LOGGER.debug("Entered into RestClient.get(Type type)");
		builder = resource.accept(accept).type(type);
//		String jsonObject = builder.get(String.class);
		return  builder.get(genericType);
	}
	
	/**
	 * Get object for the specified generic type object using the given id 
	 * @param genericType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getById(GenericType<?> genericType) {
		S_LOGGER.debug("Entered into RestClient.getById(GenericType<?> genericType)");
		return getById(genericType, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Get object for the specified generic type object using the given id 
	 * @param genericType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getById(GenericType<?> genericType, String accept, String type) {
		S_LOGGER.debug("Entered into RestClient.getById(GenericType<?> genericType)");
		builder = resource.accept(accept).type(type);
		return (E) builder.get(genericType);
	}
	
	/**
	 * Creates List of objects
	 * @param infos
	 * @throws PhrescoException
	 */
	public ClientResponse create(List<E> infos) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.create(List<E> infos)");
		return create(infos, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Creates List of objects
	 * @param infos
	 * @throws PhrescoException
	 */
	public ClientResponse create(List<E> infos, String accept, String type) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.create(List<E> infos)");
		builder = resource.accept(accept).type(type);
		ClientResponse clientResponse = builder.post(ClientResponse.class, infos);
		isErrorThrow(clientResponse);
		return clientResponse;
	}
	
	/**
	 * Updates List of objects for the given type 
	 * @param infos
	 * @param type
	 * @return
	 * @throws PhrescoException
	 */
	public List<E> update(List<E> infos, GenericType<List<E>> type) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.update(List<E> infos, Type type)");
		return update(infos, type, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Updates List of objects for the given type 
	 * @param infos
	 * @param type
	 * @return
	 * @throws PhrescoException
	 */
	@SuppressWarnings("unchecked")
	public List<E> update(List<E> infos, GenericType<List<E>> gtype, String accept, String type) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.update(List<E> infos, Type type)");
		builder = resource.accept(accept).type(type);
//		ClientResponse clientResponse = builder.put(ClientResponse.class, new Gson().toJson(infos));
		ClientResponse clientResponse = builder.put(ClientResponse.class, infos);
		isErrorThrow(clientResponse);
//		return (List<E>) (new Gson()).fromJson(clientResponse.getEntity(String.class), type);
		return clientResponse.getEntity(gtype);
	}
	
	/**
	 * Update the given object by using the type given
	 * @param obj
	 * @param type
	 * @return
	 * @throws PhrescoException
	 */
	public ClientResponse updateById(E obj, Type type) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.updateById(E obj, Type type)");
		return updateById(obj, type, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Update the given object by using the type given
	 * @param obj
	 * @param type
	 * @return
	 * @throws PhrescoException
	 */
	@SuppressWarnings("unchecked")
	public ClientResponse updateById(E obj, Type gType, String accept, String type) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.updateById(E obj, Type type)");
		builder = resource.accept(accept).type(type);
		ClientResponse clientResponse = builder.put(ClientResponse.class, new Gson().toJson(obj));
		isErrorThrow(clientResponse);
		return clientResponse;
	}
	
	/**
	 * Deletes the List of objects
	 * @param infos
	 * @throws PhrescoException
	 */
	public void delete(List<E> infos) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.delete(List<E> infos)");
		ClientResponse clientResponse = builder.delete(ClientResponse.class, infos);
		isErrorThrow(clientResponse);
	}
	
	/**
	 * Throws exception when status not equal to Accepted and Ok status codes
	 * @param clientResponse
	 * @throws PhrescoException
	 */
	private void isErrorThrow(ClientResponse clientResponse) throws PhrescoException {
		S_LOGGER.debug("Entered into RestClient.isErrorThrow(ClientResponse clientResponse)");
		int status = clientResponse.getStatus();
		if (status == ClientResponse.Status.ACCEPTED.getStatusCode() || 
				status == ClientResponse.Status.OK.getStatusCode()) {
		} else {
			throw new PhrescoException("Not able to Create");
		}
	}

	/*public void create(String infos) {
		ClientResponse clientResponse = builder.post(ClientResponse.class, infos);
	}
	
	public void update(String infos) {
		ClientResponse clientResponse = builder.put(ClientResponse.class, infos);
	}

	public void updatebyId(Role infos) {
		ClientResponse clientResponse = builder.put(ClientResponse.class, infos);
	}*/
	
	/**
	 * Delete the object by given id parameter
	 */
	public ClientResponse deleteById() {
		S_LOGGER.debug("Entered into RestClient.deleteById()");
		return resource.delete(ClientResponse.class);
	}

}
