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

import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Component;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.model.ServerConstants;

@Component
@Path("/rest")
public class AdminDBService extends DbService implements ServerConstants{
	
    public AdminDBService() {
    	super();
    }
    
    /**
     * Returns the list of customers
     * @return
     */
    @GET
    @Path(REST_API_CUSTOMERS)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> findCustomer() {
    	List<Customer> customerList = mongoOperation.getCollection(CUSTOMERS_COLLECTION_NAME , Customer.class);
    	return customerList;
    }

    /**
     * Creates the list of customers
     * @param customer
     */
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Path(REST_API_CUSTOMERS)
    public void createCustomer(List<Customer> customer) {
    	mongoOperation.insertList(CUSTOMERS_COLLECTION_NAME , customer);
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
    public List<Customer> updateCustomer(List<Customer> customers) {
    	for (Customer customer : customers) {
    		Customer updateCustomers = mongoOperation.findOne(CUSTOMERS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(customer.getId())), Customer.class);
    		if (updateCustomers != null) {
    			mongoOperation.save(CUSTOMERS_COLLECTION_NAME, customer);
    		}
    	}
    	return customers;
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
    public Customer updateCustomer(@PathParam(REST_API_PATH_PARAM_ID) String id , Customer updateCustomers) {
    	if(id.equals(updateCustomers.getId())) {
    		mongoOperation.save(CUSTOMERS_COLLECTION_NAME, updateCustomers);
    	}
    	return updateCustomers;
    }

    /**
     * Deletes the list of customers
     * @param deleteCustomers
     */
    @DELETE
    @Path(REST_API_CUSTOMERS)
    public void deleteCustomer(List<Customer> deleteCustomers) {
    	throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
    }

    /**
     * Get the customer by id for the given parameter
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REST_API_CUSTOMERS + REST_API_PATH_ID)
    public Customer getCustomer(@PathParam(REST_API_PATH_PARAM_ID) String id) {
    	Customer getCustomer = mongoOperation.findOne(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
    	return getCustomer;
    }

    /**
     * Deletes the customer by id for the given parameter
     * @param id
     */
    @DELETE
    @Path(REST_API_CUSTOMERS + REST_API_PATH_ID)
    public void deleteCustomer(@PathParam(REST_API_PATH_PARAM_ID) String id) {
    	mongoOperation.remove(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
    }

	
	/**
	 * Returns the list of videos
	 * @return
	 */
	@GET
	@Path(REST_API_VIDEOS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<VideoInfo> findVideos() {
		List<VideoInfo> videoList = mongoOperation.getCollection(VIDEOS_COLLECTION_NAME , VideoInfo.class);
		return videoList;
	}

	/**
	 * Creates the list of videos
	 * @param videos
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_VIDEOS)
	public void createVideo(List<VideoInfo> videos) {
		System.out.println(videos);
		mongoOperation.insertList(VIDEOS_COLLECTION_NAME , videos);
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
	public List<VideoInfo> updateVideos(List<VideoInfo> videos) {
		for (VideoInfo video : videos) {
			VideoInfo videoInfo = mongoOperation.findOne(VIDEOS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(video.getId())), VideoInfo.class);
			if (videoInfo  != null) {
				mongoOperation.save(VIDEOS_COLLECTION_NAME, video);
			}
		}
		return videos;
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
	public VideoInfo updateVideo(@PathParam(REST_API_PATH_PARAM_ID) String id , VideoInfo videoInfo) {
		if(id.equals(videoInfo.getId())) {
			mongoOperation.save(VIDEOS_COLLECTION_NAME, videoInfo);
		}
		return videoInfo;
	}

	/**
	 * Deletes the list of Videos
	 * @param videos
	 */
	@DELETE
	@Path(REST_API_VIDEOS)
	public void deleteVideos(List<VideoInfo> videos) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Get the videos by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_VIDEOS + REST_API_PATH_ID)
	public VideoInfo getVideo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		VideoInfo videoInfo = mongoOperation.findOne(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
		return videoInfo;
	}

	/**
	 * Deletes the Video by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_VIDEOS + REST_API_PATH_ID)
	public void deleteVideo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
	}

	
	/**
	 * Returns the list of users
	 * @return
	 */
	@GET
	@Path(REST_API_USERS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findUsers() {
		List<User> userList = mongoOperation.getCollection(USERS_COLLECTION_NAME , User.class);
		return userList;
	}

	/**
	 * Creates the list of users
	 * @param users
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_USERS)
	public void createUser(List<User> users) {
		mongoOperation.insertList(USERS_COLLECTION_NAME , users);
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
	public List<User> updateUsers(List<User> users) {
		for (User user : users) {
			User userInfo = mongoOperation.findOne(USERS_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(user.getId())), User.class);
			if (userInfo  != null) {
				mongoOperation.save(USERS_COLLECTION_NAME, user);
			}
		}
		return users;
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
	public User updateUser(@PathParam(REST_API_PATH_PARAM_ID) String id , User user) {
		if(id.equals(user.getId())) {
			mongoOperation.save(USERS_COLLECTION_NAME, user);
		}
		return user;
	}

	/**
	 * Deletes the list of Users
	 * @param users
	 */
	@DELETE
	@Path(REST_API_USERS)
	public void deleteUsers(List<User> users) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Get the user by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_USERS + REST_API_PATH_ID)
	public User getUser(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		User userInfo = mongoOperation.findOne(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
		return userInfo;
	}

	/**
	 * Deletes the user by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_USERS + REST_API_PATH_ID)
	public void deleteUser(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
	}


	/**
	 * Returns the list of downloadInfo
	 * @return
	 */
	@GET
	@Path(REST_API_DOWNLOADS)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DownloadInfo> findDownloadInfo() {
		List<DownloadInfo> downloadList = mongoOperation.getCollection(DOWNLOAD_COLLECTION_NAME , DownloadInfo.class);
		return downloadList;
	}

	/**
	 * Creates the list of downloads
	 * @param downloadInfos
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Path(REST_API_DOWNLOADS)
	public void createDownloadInfo(List<DownloadInfo> downloadInfos) {
		mongoOperation.insertList(DOWNLOAD_COLLECTION_NAME , downloadInfos);
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
	public List<DownloadInfo> updateDownloadInfo(List<DownloadInfo> downloads) {
		for (DownloadInfo download : downloads) {
			DownloadInfo downloadInfo = mongoOperation.findOne(DOWNLOAD_COLLECTION_NAME , new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(download.getId())), DownloadInfo.class);
			if (downloadInfo  != null) {
				mongoOperation.save(DOWNLOAD_COLLECTION_NAME, download);
			}
		}
		return downloads;
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
	public DownloadInfo updateDownloadInfo(@PathParam(REST_API_PATH_PARAM_ID) String id , DownloadInfo downloadInfos) {
		if(id.equals(downloadInfos.getId())) {
			mongoOperation.save(DOWNLOAD_COLLECTION_NAME, downloadInfos);
		}
		return downloadInfos;
	}

	/**
	 * Deletes the list of DownloadInfo
	 * @param downloadInfos
	 */
	@DELETE
	@Path(REST_API_DOWNLOADS)
	public void deleteDownloadInfo(List<DownloadInfo> downloadInfos) {
		throw new UnsupportedOperationException(ServerConstants.ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Get the downloadInfo by id for the given parameter
	 * @param id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DOWNLOADS + REST_API_PATH_ID)
	public DownloadInfo getDownloadInfo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		DownloadInfo downloadInfo = mongoOperation.findOne(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
		return downloadInfo;
	}

	/**
	 * Deletes the user by id for the given parameter
	 * @param id
	 */
	@DELETE
	@Path(REST_API_DOWNLOADS + REST_API_PATH_ID)
	public void deleteDownloadInfo(@PathParam(REST_API_PATH_PARAM_ID) String id) {
		mongoOperation.remove(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
	}
 }
