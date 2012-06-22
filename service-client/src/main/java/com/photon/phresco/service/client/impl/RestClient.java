package com.photon.phresco.service.client.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import com.photon.phresco.commons.model.Role;
import com.photon.phresco.exception.PhrescoException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RestClient<E> {

	private WebResource resource = null;
	private Builder builder = null;

	public RestClient(String serverUrl) {
		Client client = ClientHelper.createClient();
		resource = client.resource(serverUrl);
	}

	public void setAccept(String mediaType) {
		resource.accept(mediaType);
	}

	public void setType(String mediaType) {
		builder = resource.type(mediaType);
	}

	public void addHeader(String key, String value) {
		resource.header(key, value);
	}

	public void addQueryString(String key, String value) {
		resource.queryParam(key, value);
	}

	public void addQueryStrings(Map<String, String> headers) {
		Set<String> keySet = headers.keySet();
		MultivaluedMap<String, String> queryStrings = new MultivaluedMapImpl();
		for (String key : keySet) {
			queryStrings.add(key, headers.get(key));
		}
		resource.queryParams(queryStrings);
	}

	@SuppressWarnings("unchecked")
	public List<E> get(GenericType<?> genericType) {
		return (List<E>) builder.get(genericType);
	}

	public void create(List<E> infos) throws PhrescoException {
		ClientResponse clientResponse = builder.post(ClientResponse.class, infos);
		isErrorThrow(clientResponse);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> update(List<E> infos, GenericType<?> genericType) throws PhrescoException {
		ClientResponse clientResponse = builder.put(ClientResponse.class, infos);
		isErrorThrow(clientResponse);
		return (List<E>) clientResponse.getEntity(genericType);
	}

	public void delete(List<E> infos) throws PhrescoException {
		ClientResponse clientResponse = builder.delete(ClientResponse.class, infos);
		isErrorThrow(clientResponse);
	}
	
	private void isErrorThrow(ClientResponse clientResponse) throws PhrescoException {
		int status = clientResponse.getStatus();
		if (status != ClientResponse.Status.ACCEPTED.getStatusCode()
				|| status != ClientResponse.Status.OK.getStatusCode()) {
			throw new PhrescoException("Not able to Create");
		}
	}

	public void create(String infos) {
		ClientResponse clientResponse = builder.post(ClientResponse.class, infos);
	}
	
	public void update(String infos) {
		ClientResponse clientResponse = builder.put(ClientResponse.class, infos);
	}

	public void updatebyId(Role infos) {
		ClientResponse clientResponse = builder.put(ClientResponse.class, infos);
	}

	public void delete(String id) {
		resource.path(id).delete(ClientResponse.class);
	}

	public void setPath(String id) {
		resource.path(id);
	}
}
