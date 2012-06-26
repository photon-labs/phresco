package com.photon.phresco.service.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.model.ServerConstants;

public class AdminServiceTest extends DbService implements ServerConstants{
	
	public AdminServiceTest() throws PhrescoException {
		super();
	}
	
	@Test
	public void testFindCustomer() {
		List<Customer> customers = mongoOperation.getCollection(CUSTOMERS_COLLECTION_NAME , Customer.class);
		Customer customer = customers.get(0);
		assertEquals(customer.getName(),"john");
	}

	@Test
	public void testCreateCustomer() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer("John", "From Phresco");
		customers.add(customer);
		mongoOperation.insertList(CUSTOMERS_COLLECTION_NAME , customers);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateCustomerListOfCustomer() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer("John", "From Phresco");
		customer.setId("4fe80d7e230d37b3444dfb32");
		customers.add(customer);
		mongoOperation.save(CUSTOMERS_COLLECTION_NAME, customers);
		assertEquals(Response.status(Response.Status.OK).entity(customers).build().getStatus(), 200);
	}

	@Test
	public void testDeleteCustomerListOfCustomer() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetCustomer() {
		String id = "4fe80d7e230d37b3444dfb32";
		Customer customer = mongoOperation.findOne(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
		assertEquals(customer.getName(), "John");
	}

	@Test
	public void testUpdateCustomerStringCustomer() {
		Customer customer = new Customer("John", "From Phresco");
		customer.setId("4fe80d7e230d37b3444dfb32");
		mongoOperation.save(CUSTOMERS_COLLECTION_NAME, customer);
		assertEquals(Response.status(Response.Status.OK).entity(customer).build().getStatus(), 200);
	}

	@Test
	public void testDeleteCustomerString() {
		String id = "4fe80d7e230d37b3444dfb32";
		mongoOperation.remove(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindVideos() {
		List<VideoInfo> videolist = mongoOperation.getCollection(VIDEOS_COLLECTION_NAME , VideoInfo.class);
		VideoInfo video = videolist.get(0);
		assertEquals(video.getName(), "About phresco");
	}

	@Test
	public void testCreateVideo() {
		List<VideoInfo> videolist = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo("About phresco", "intro about phresoc", null, null, null, null);
		videolist.add(info);
		mongoOperation.insertList(VIDEOS_COLLECTION_NAME , videolist);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateVideos() {
		List<VideoInfo> videolist = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo("About phresco", "intro about phresoc", null, null, null, null);
		info.setId("4fe80d7e230d37b3444dfb32");
		videolist.add(info);
		mongoOperation.save(VIDEOS_COLLECTION_NAME, videolist);
		assertEquals(Response.status(Response.Status.OK).entity(videolist).build().getStatus(), 200);
	}

	@Test
	public void testDeleteVideos() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetVideo() {
		String id = "4fe80d7e230d37b3444dfb32";
		VideoInfo videoInfo = mongoOperation.findOne(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
		assertEquals(videoInfo.getName(), "About phresco");
	}

	@Test
	public void testUpdateVideo() {
		VideoInfo info = new VideoInfo("About phresco", "intro about phresoc", null, null, null, null);
		info.setId("4fe80d7e230d37b3444dfb32");
		mongoOperation.save(VIDEOS_COLLECTION_NAME, info);
		assertEquals(Response.status(Response.Status.OK).entity(info).build().getStatus(), 200);
	}

	@Test
	public void testDeleteVideo() {
		String id = "4fe80d7e230d37b3444dfb32";
		mongoOperation.remove(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindUsers() {
		List<User> userist = mongoOperation.getCollection(USERS_COLLECTION_NAME , User.class);
		User user = userist.get(0);
		assertEquals(user.getName(), "john");
	}

	@Test
	public void testCreateUser() {
		List<User> users = new ArrayList<User>();
		User user = new User("john", "from phresco");
		users.add(user);
		mongoOperation.insertList(USERS_COLLECTION_NAME , users);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateUsers() {
		List<User> users = new ArrayList<User>();
		User user = new User("john", "from phresco");
		user.setId("4fe80d7e230d37b3444dfb32");
		users.add(user);
		mongoOperation.save(USERS_COLLECTION_NAME, users);
		assertEquals(Response.status(Response.Status.OK).entity(users).build().getStatus(), 200);
	}

	@Test
	public void testDeleteUsers() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetUser() {
		String id = "4fe80d7e230d37b3444dfb32";
		User user = mongoOperation.findOne(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
		assertEquals(user.getName(), "john");
	}

	@Test
	public void testUpdateUser() {
		User user = new User("john", "from phresco");
		user.setId("4fe80d7e230d37b3444dfb32");
		mongoOperation.save(USERS_COLLECTION_NAME, user);
		assertEquals(Response.status(Response.Status.OK).entity(user).build().getStatus(), 200);
	}

	@Test
	public void testDeleteUser() {
		String id = "4fe80d7e230d37b3444dfb32";
		mongoOperation.remove(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testFindDownloadInfo() {
		List<DownloadInfo> downloadList = mongoOperation.getCollection(DOWNLOAD_COLLECTION_NAME , DownloadInfo.class);
		DownloadInfo download = downloadList.get(0);
		assertEquals(download.getName(), "eclipse");
	}

	@Test
	public void testCreateDownloadInfo() {
		List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
		DownloadInfo info = new DownloadInfo();
		info.setName("Eclipse");
		info.setType("Editor");
		info.setVersion("juno");
		infos.add(info);
		mongoOperation.insertList(DOWNLOAD_COLLECTION_NAME , infos);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

	@Test
	public void testUpdateDownloadInfoListOfDownloadInfo() {
		List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
		DownloadInfo info = new DownloadInfo();
		info.setName("Eclipse-Juno");
		info.setType("Editor");
		info.setVersion("juno");
		info.setId("4fe80d7e230d37b3444dfb32");
		infos.add(info);
		mongoOperation.save(DOWNLOAD_COLLECTION_NAME, infos);
		assertEquals(Response.status(Response.Status.OK).entity(infos).build().getStatus(), 200);
	}

	@Test
	public void testDeleteDownloadInfoListOfDownloadInfo() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetDownloadInfo() {
		String id = "4fe80d7e230d37b3444dfb32";
		DownloadInfo downloadInfo = mongoOperation.findOne(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
		assertEquals(downloadInfo.getName(), "Eclipse-Juno");
	}

	@Test
	public void testUpdateDownloadInfoStringDownloadInfo() {
		DownloadInfo info = new DownloadInfo();
		info.setName("Eclipse-Juno");
		info.setType("Editor");
		info.setVersion("juno");
		info.setId("4fe80d7e230d37b3444dfb32");
		mongoOperation.save(DOWNLOAD_COLLECTION_NAME, info);
		assertEquals(Response.status(Response.Status.OK).entity(info).build().getStatus(), 200);
	}

	@Test
	public void testDeleteDownloadInfoString() {
		String id = "4fe80d7e230d37b3444dfb32";
		mongoOperation.remove(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
		assertEquals(Response.status(Response.Status.OK).build().getStatus(), 200);
	}

}
