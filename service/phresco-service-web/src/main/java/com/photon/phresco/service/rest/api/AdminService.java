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
package com.photon.phresco.service.rest.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Component;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.model.ServerConstants;

@Component
@Path(ServerConstants.REST_API_ADMIN)
public class AdminService extends DbService implements ServerConstants{
	
	private static final Logger S_LOGGER= Logger.getLogger(AdminService.class);
	
    public AdminService() throws PhrescoException {
    	super();
    }
    
    /**
     * Returns the list of customers
     * @return
     */
    @GET
    @Path(REST_API_CUSTOMERS)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCustomer() {
    	S_LOGGER.debug("Entered into AdminService.findCustomer()");
    	List<Customer> customerList = mongoOperation.getCollection(CUSTOMERS_COLLECTION_NAME , Customer.class);
    	return Response.status(Response.Status.OK).entity(customerList).build();
    }

    /**
     * Creates the list of customers
     * @param customer
     * @return 
     */
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Path(REST_API_CUSTOMERS)
    public Response createCustomer(List<Customer> customer) {
    	S_LOGGER.debug("Entered into AdminService.createCustomer(List<Customer> customer)");
    	mongoOperation.insertList(CUSTOMERS_COLLECTION_NAME , customer);
    	return Response.status(Response.Status.OK).build();
    }

    /**
     * Updates the list of customers
     * @param customers
     * @return
     */
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    @Path(REST_API_CUSTOMERS)
    public Response updateCustomer(List<Customer> customers) {
    	S_LOGGER.debug("Entered into AdminService.updateCustomer(List<Customer> customers)");
    	for (Customer customer : customers) {
    		Customer updateCustomers = mongoOperation.findOne(CUSTOMERS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(customer.getId())), Customer.class);
    		if (updateCustomers != null) {
    			mongoOperation.save(CUSTOMERS_COLLECTION_NAME, customer);
    		}
    	}
    	return Response.status(Response.Status.OK).entity(customers).build();
    }

    /**
     * Deletes the list of customers
     * @param deleteCustomers
     * @throws PhrescoException 
     */
    @DELETE
    @Path(REST_API_CUSTOMERS)
    public void deleteCustomer(List<Customer> deleteCustomers) throws PhrescoException {
    	S_LOGGER.debug("Entered into AdminService.deleteCustomer(List<Customer> deleteCustomers)");
    	PhrescoException phrescoException = new PhrescoException(EX_PHEX00001);
    	S_LOGGER.error("PhrescoException Is" + phrescoException.getErrorMessage());
    	throw phrescoException;
    }

    /**
     * Get the customer by id for the given parameter
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REST_API_CUSTOMERS + REST_API_PATH_ID)
    public Response getCustomer(@PathParam(REST_API_PATH_PARAM_ID) String id) {
    	S_LOGGER.debug("Entered into AdminService.getCustomer(String id)");
    	Customer customer = mongoOperation.findOne(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
    	return Response.status(Response.Status.OK).entity(customer).build();
    }
    
    /**
     * Updates the list of customers by Id
     * @param updateCustomers
     * @return
     */
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    @Path(REST_API_CUSTOMERS + REST_API_PATH_ID)
    public Response updateCustomer(@PathParam(REST_API_PATH_PARAM_ID) String id , Customer updateCustomers) {
    	S_LOGGER.debug("Entered into AdminService.updateCustomer(String id , Customer updateCustomers)");
    	if(id.equals(updateCustomers.getId())) {
    		mongoOperation.save(CUSTOMERS_COLLECTION_NAME, updateCustomers);
    	}
    	return Response.status(Response.Status.OK).entity(updateCustomers).build();
    }

    
    /**
     * Deletes the customer by id for the given parameter
     * @param id
     * @return 
     */
    @DELETE
    @Path(REST_API_CUSTOMERS + REST_API_PATH_ID)
    public Response deleteCustomer(@PathParam(REST_API_PATH_PARAM_ID) String id) {
    	S_LOGGER.debug("Entered into AdminService.deleteCustomer(String id)");
    	mongoOperation.remove(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
    	return Response.status(Response.Status.OK).build();
    }

	
	/**
	 * Returns the list of videos
	 * @return
	 */
	@GET
	@Path(REST_API_VIDEOS)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findVideos() {
		S_LOGGER.debug("Entered into AdminService.findVideos()");
		List<VideoInfo> videoList = mongoOperation.getCollection(VIDEOS_COLLECTION_NAME , VideoInfo.class);
		return Response.status(Response.Status.OK).entity(videoList).build();
	}

	/**
	 * Creates the list of videos
	 * @param videos
	 * @return 
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_VIDEOS)
	public Response createVideo(List<VideoInfo> videos) {
		S_LOGGER.debug("Entered into AdminService.createVideo(List<VideoInfo> videos)");
		System.out.println(videos);
		mongoOperation.insertList(VIDEOS_COLLECTION_NAME , videos);
		return Response.status(Response.Status.OK).build();
	}

	/**
	 * Updates the list of Videos
	 * @param videos
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_VIDEOS)
	public Response updateVideos(List<VideoInfo> videos) {
		S_LOGGER.debug("Entered into AdminService.updateVideos(List<VideoInfo> videos)");
		for (VideoInfo video : videos) {
			VideoInfo videoInfo = mongoOperation.findOne(VIDEOS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(video.getId())), VideoInfo.class);
			if (videoInfo  != null) {
				mongoOperation.save(VIDEOS_COLLECTION_NAME, video);
			}
		}
		return Response.status(Response.Status.OK).entity(videos).build();
	}

	/**
	 * Deletes the list of Videos
	 * @param videos
	 * @throws PhrescoException 
	 */
	@DELETE
	@Path(REST_API_VIDEOS)
	public void deleteVideos(List<VideoInfo> videos) throws PhrescoException {
		S_LOGGER.debug("Entered into AdminService.deleteVideos(List<VideoInfo> videos)");
		PhrescoException phrescoException = new PhrescoException(EX_PHEX00001);
		S_LOGGER.error("PhrescoException Is" + phrescoException.getErrorMessage());
		throw phrescoException;
	}

	/**
	 * Get the videos by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_VIDEOS + REST_API_PATH_ID)
	public Response getVideo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		S_LOGGER.debug("Entered into AdminService.getVideo(String id)");
		VideoInfo videoInfo = mongoOperation.findOne(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
		return Response.status(Response.Status.OK).entity(videoInfo).build();
	}
	
	/**
	 * Updates the list of video bu Id
	 * @param videoInfo
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_VIDEOS + REST_API_PATH_ID)
	public Response updateVideo(@PathParam(REST_API_PATH_PARAM_ID) String id , VideoInfo videoInfo) {
		S_LOGGER.debug("Entered into AdminService.updateVideo(String id , VideoInfo videoInfo)");
		if(id.equals(videoInfo.getId())) {
			mongoOperation.save(VIDEOS_COLLECTION_NAME, videoInfo);
		}
		return Response.status(Response.Status.OK).entity(videoInfo).build();
	}

	
	/**
	 * Deletes the Video by id for the given parameter
	 * @param id
	 * @return 
	 */
	@DELETE
	@Path(REST_API_VIDEOS + REST_API_PATH_ID)
	public Response deleteVideo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		S_LOGGER.debug("Entered into AdminService.deleteVideo(String id)");
		mongoOperation.remove(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
		return Response.status(Response.Status.OK).build();
	}

	
	/**
	 * Returns the list of users
	 * @return
	 */
	@GET
	@Path(REST_API_USERS)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUsers() {
		S_LOGGER.debug("Entered into AdminService.findUsers()");
		List<User> userList = mongoOperation.getCollection(USERS_COLLECTION_NAME , User.class);
		return Response.status(Response.Status.OK).entity(userList).build();
	}

	/**
	 * Creates the list of users
	 * @param users
	 * @return 
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_USERS)
	public Response createUser(List<User> users) {
		S_LOGGER.debug("Entered into AdminService.createUser(List<User> users)");
		mongoOperation.insertList(USERS_COLLECTION_NAME , users);
		return Response.status(Response.Status.OK).build();
	}

	/**
	 * Updates the list of Users
	 * @param users
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_USERS)
	public Response updateUsers(List<User> users) {
		S_LOGGER.debug("Entered into AdminService.updateUsers(List<User> users)");
		for (User user : users) {
			User userInfo = mongoOperation.findOne(USERS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(user.getId())), User.class);
			if (userInfo  != null) {
				mongoOperation.save(USERS_COLLECTION_NAME, user);
			}
		}
		return Response.status(Response.Status.OK).entity(users).build();
	}

	/**
	 * Deletes the list of Users
	 * @param users
	 * @throws PhrescoException 
	 */
	@DELETE
	@Path(REST_API_USERS)
	public void deleteUsers(List<User> users) throws PhrescoException {
		S_LOGGER.debug("Entered into AdminService.deleteUsers(List<User> users)");
		PhrescoException phrescoException = new PhrescoException(EX_PHEX00001);
		S_LOGGER.error("PhrescoException Is" + phrescoException.getErrorMessage());
		throw phrescoException;
	}

	/**
	 * Get the user by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_USERS + REST_API_PATH_ID)
	public Response getUser(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		S_LOGGER.debug("Entered into AdminService.getUser(String id)");
		User userInfo = mongoOperation.findOne(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
		return Response.status(Response.Status.OK).entity(userInfo).build();
	}
	
	/**
	 * Updates the list of User by id
	 * @param users
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_USERS + REST_API_PATH_ID)
	public Response updateUser(@PathParam(REST_API_PATH_PARAM_ID) String id , User user) {
		S_LOGGER.debug("Entered into AdminService.updateUser(String id , User user)");
		if(id.equals(user.getId())) {
			mongoOperation.save(USERS_COLLECTION_NAME, user);
		}
		return Response.status(Response.Status.OK).entity(user).build();
	}
	
	/**
	 * Deletes the user by id for the given parameter
	 * @param id
	 * @return 
	 */
	@DELETE
	@Path(REST_API_USERS + REST_API_PATH_ID)
	public Response deleteUser(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		S_LOGGER.debug("Entered into AdminService.deleteUser(String id)");
		mongoOperation.remove(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
		return Response.status(Response.Status.OK).build();
	}


	/**
	 * Returns the list of downloadInfo
	 * @return
	 */
	@GET
	@Path(REST_API_DOWNLOADS)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findDownloadInfo() {
		S_LOGGER.debug("Entered into AdminService.findDownloadInfo()");
		List<DownloadInfo> downloadList = mongoOperation.getCollection(DOWNLOAD_COLLECTION_NAME , DownloadInfo.class);
		return Response.status(Response.Status.OK).entity(downloadList).build();
	}

	/**
	 * Creates the list of downloads
	 * @param downloadInfos
	 * @return 
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_DOWNLOADS)
	public Response createDownloadInfo(List<DownloadInfo> downloadInfos) {
		S_LOGGER.debug("Entered into AdminService.createDownloadInfo(List<DownloadInfo> downloadInfos)");
		mongoOperation.insertList(DOWNLOAD_COLLECTION_NAME , downloadInfos);
		return Response.status(Response.Status.OK).build();
	}

	/**
	 * Updates the list of downloadInfos
	 * @param downloads
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_DOWNLOADS)
	public Response updateDownloadInfo(List<DownloadInfo> downloads) {
		S_LOGGER.debug("Entered into AdminService.updateDownloadInfo(List<DownloadInfo> downloads)");
		for (DownloadInfo download : downloads) {
			DownloadInfo downloadInfo = mongoOperation.findOne(DOWNLOAD_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(download.getId())), DownloadInfo.class);
			if (downloadInfo  != null) {
				mongoOperation.save(DOWNLOAD_COLLECTION_NAME, download);
			}
		}
		return Response.status(Response.Status.OK).entity(downloads).build();
	}

	/**
	 * Deletes the list of DownloadInfo
	 * @param downloadInfos
	 * @throws PhrescoException 
	 */
	@DELETE
	@Path(REST_API_DOWNLOADS)
	public void deleteDownloadInfo(List<DownloadInfo> downloadInfos) throws PhrescoException {
		S_LOGGER.debug("Entered into AdminService.deleteDownloadInfo(List<DownloadInfo> downloadInfos)");
		PhrescoException phrescoException = new PhrescoException(EX_PHEX00001);
		S_LOGGER.error("PhrescoException Is"  + phrescoException.getErrorMessage());
		throw phrescoException;
	}

	/**
	 * Get the downloadInfo by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DOWNLOADS + REST_API_PATH_ID)
	public Response getDownloadInfo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		S_LOGGER.debug("Entered into AdminService.getDownloadInfo(String id)");
		DownloadInfo downloadInfo = mongoOperation.findOne(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
		return Response.status(Response.Status.OK).entity(downloadInfo).build();
	}
	
	/**
	 * Updates the list of downloadInfo by id
	 * @param downloadInfos
	 * @return
	 */
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	@Path(REST_API_DOWNLOADS + REST_API_PATH_ID)
	public Response updateDownloadInfo(@PathParam(REST_API_PATH_PARAM_ID) String id , DownloadInfo downloadInfo) {
		S_LOGGER.debug("Entered into AdminService.updateDownloadInfo(String id , DownloadInfo downloadInfos)");
		if(id.equals(downloadInfo.getId())) {
			mongoOperation.save(DOWNLOAD_COLLECTION_NAME, downloadInfo);
		}
		return Response.status(Response.Status.OK).entity(downloadInfo).build();
	}
	
	/**
	 * Deletes the user by id for the given parameter
	 * @param id
	 * @return 
	 */
	@DELETE
	@Path(REST_API_DOWNLOADS + REST_API_PATH_ID)
	public Response deleteDownloadInfo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		S_LOGGER.debug("Entered into AdminService.deleteDownloadInfo(String id)");
		mongoOperation.remove(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
		return Response.status(Response.Status.OK).build();
	}
 }
