package com.photon.phresco.service.rest.api;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.Role;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.GlobalURL;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.api.DbService;
import com.photon.phresco.util.ServiceConstants;

public class AdminServiceTest extends DbService implements ServiceConstants{
	
	public AdminServiceTest() throws PhrescoException {
		super();
	}
	
	@Test
	public void testCreateCustomer() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer("phresco", "From Phresco");
		customer.setId("testCustomer");
		customers.add(customer);
		mongoOperation.insertList(CUSTOMERS_COLLECTION_NAME , customers);
	}

	@Test
	public void testFindCustomer() {
		List<Customer> customers = mongoOperation.getCollection(CUSTOMERS_COLLECTION_NAME , Customer.class);
		assertNotNull(customers);
	}

	@Test
	public void testUpdateCustomerListOfCustomer() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer("photon","Phresco");
		customer.setId("testCustomer");
		customers.add(customer); 
		for (Customer customerUpdate : customers) {
			mongoOperation.save(CUSTOMERS_COLLECTION_NAME, customerUpdate);
		}
	}

	@Ignore
	public void testDeleteCustomerListOfCustomer() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetCustomer() {
		String id = "testCustomer";
		Customer customer = mongoOperation.findOne(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
		assertNotNull(customer);
	}

	@Test
	public void testUpdateCustomerStringCustomer() {
		Customer customer = new Customer("photon", "From Phresco");
		customer.setId("testCustomer");
		mongoOperation.save(CUSTOMERS_COLLECTION_NAME, customer);
	}

	@Test
	public void testDeleteCustomerString() {
		String id = "testCustomer";
		mongoOperation.remove(CUSTOMERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Customer.class);
	}

	
	@Test
	public void testCreateVideo() {
		List<VideoInfo> videolist = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo("About phresco", "intro about phresoc", null, null, null, null);
		videolist.add(info);
		info.setId("testvideo"); 
		videolist.indexOf("testvideo");
		mongoOperation.insertList(VIDEOS_COLLECTION_NAME , videolist);
	}

	
	@Test
	public void testFindVideos() {
		List<VideoInfo> videolist = mongoOperation.getCollection(VIDEOS_COLLECTION_NAME , VideoInfo.class);
		assertNotNull(videolist);
	}
	
	
	@Test
	public void testUpdateVideos() {
		List<VideoInfo> videolist = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo("About phresco ", "intro about phresco", null, null, null, null);
		info.setId("testvideo");
		videolist.add(info);
		mongoOperation.save(VIDEOS_COLLECTION_NAME, videolist);
	}

	@Ignore
	public void testDeleteVideos() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetVideo() {
		String id = "testvideo";
		VideoInfo videoInfo = mongoOperation.findOne(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
		assertNotNull(videoInfo);
		
	} 

	@Test
	public void testUpdateVideo() {
		VideoInfo info = new VideoInfo("About phresco info", "intro about phresoc", null, null, null, null);
		info.setId("testvideo");
		mongoOperation.save(VIDEOS_COLLECTION_NAME, info);
	}

	@Test
	public void testDeleteVideo() {
		String id = "testvideo";
		mongoOperation.remove(VIDEOS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), VideoInfo.class);
	}

	

	@Test
	public void testCreateUser() {
		List<User> users = new ArrayList<User>();
		User user = new User("photon", "from phresco");
		user.setId("testUser");
		users.add(user);
		mongoOperation.insertList(USERS_COLLECTION_NAME , users);
	}
    
	@Test
	public void testFindUsers() {
		List<User> userist = mongoOperation.getCollection(USERS_COLLECTION_NAME , User.class);
		assertNotNull(userist);
	}
	
	@Test
	public void testUpdateUsers() {
		List<User> users = new ArrayList<User>();
		User user = new User("phresco", "from phresco");
		user.setId("testUser");
		users.add(user);
		mongoOperation.save(USERS_COLLECTION_NAME, users);
	}

	@Ignore
	public void testDeleteUsers() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetUser() {
		String id = "testUser";
		User user = mongoOperation.findOne(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
		assertNotNull(user);
	}

	@Test
	public void testUpdateUser() {
		User user = new User("photon", "from phresco");
		user.setId("testUser");
		mongoOperation.save(USERS_COLLECTION_NAME, user);
	} 

	@Test
	public void testDeleteUser() {
		String id = "testUser";
		mongoOperation.remove(USERS_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), User.class);
	}

	

	@Test
	public void testCreateDownloadInfo() {
		List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
		DownloadInfo info = new DownloadInfo();
		info.setName("Eclipse");
		info.setType("Editor");
		info.setVersion("juno");
		info.setId("testdownload");
		infos.add(info);
		mongoOperation.insertList(DOWNLOAD_COLLECTION_NAME , infos);
	}
    
	@Test
	public void testFindDownloadInfo() {
		List<DownloadInfo> downloadList = mongoOperation.getCollection(DOWNLOAD_COLLECTION_NAME , DownloadInfo.class);
		assertNotNull(downloadList);
	}
	
	@Test
	public void testUpdateDownloadInfoListOfDownloadInfo() {
		List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
		DownloadInfo info = new DownloadInfo();
		info.setName("Eclipse-Juno");
		info.setType("Editor");
		info.setVersion("juno");
		info.setId("testdownload");
		infos.add(info);
		for (DownloadInfo downloadInfo : infos) {
			mongoOperation.save(DOWNLOAD_COLLECTION_NAME, downloadInfo );
		}
	}

	@Ignore
	public void testDeleteDownloadInfoListOfDownloadInfo() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetDownloadInfo() {
		String id = "testdownload";
		DownloadInfo downloadInfo = mongoOperation.findOne(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
		assertNotNull(downloadInfo);
	}

	@Test
	public void testUpdateDownloadInfoStringDownloadInfo() {
		DownloadInfo info = new DownloadInfo();
		info.setName("Eclipse-Juno");
		info.setType("Editor");
		info.setVersion("juno");
		info.setId("testdownload");
		mongoOperation.save(DOWNLOAD_COLLECTION_NAME, info);
	}

	@Test
	public void testDeleteDownloadInfoString() {
		String id = "testdownload";
		mongoOperation.remove(DOWNLOAD_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), DownloadInfo.class);
	}
	
	
	
	@Test
	public void createRoles() {
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role("developer", "developer");
		roles.add(role);
		role.setId("testrole");
		mongoOperation.insertList("roles", roles);
	}
	
	
	@Test
	public void testFindRoles() {
		List<Role> rolelist = mongoOperation.getCollection(ROLES_COLLECTION_NAME , Role.class);
		assertNotNull(rolelist);
	}
	
	@Test
	public void testUpdateRoles() {
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role("QA", "from phresco");
		role.setId("testrole");
		roles.add(role);
		for (Role roleupdate : roles) {
			mongoOperation.save(ROLES_COLLECTION_NAME, roleupdate);	
		}
		
	}

	@Ignore
	public void testDeleteRoles() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetRole() {
		String id = "testrole";
		Role role = mongoOperation.findOne(ROLES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Role.class);
		assertNotNull(role);
	}

	@Test
	public void testUpdateRole() {
		Role role = new Role("support", "from phresco");
		role.setId("testrole");
		mongoOperation.save(ROLES_COLLECTION_NAME, role);
		
	} 

	@Test
	public void testDeleteRole() {
		String id = "testrole";
		mongoOperation.remove(ROLES_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), Role.class);
	}

	@Test
	public void createGlobalURL() {
		List<GlobalURL> gurl = new ArrayList<GlobalURL>();
		GlobalURL url = new GlobalURL();
		url.setId("testUrl");
		url.setUrl("testGlobalURL");
		gurl.add(url);
		mongoOperation.insertList(GLOBALURL_COLLECTION_NAME, gurl);
	}
	
	
	@Test
	public void testFindGlobalURL() {
		List<GlobalURL> urllist = mongoOperation.getCollection(GLOBALURL_COLLECTION_NAME , GlobalURL.class);
		assertNotNull(urllist);
	}
	
	@Test
	public void testUpdateGlobalURL() {
		List<GlobalURL> gURL = new ArrayList<GlobalURL>();
		GlobalURL url = new GlobalURL();
		url.setId("testUrl");
		url.setUrl("testGlobalURLUpdate");
		gURL.add(url);
		for (GlobalURL urlupdate : gURL) {
			mongoOperation.save(GLOBALURL_COLLECTION_NAME, urlupdate);	
		}
		
	}

	@Ignore
	public void testDeleteGlobalURL() throws PhrescoException {
		throw new PhrescoException(EX_PHEX00001);
	}

	@Test
	public void testGetGlobalURLById() {
		String id = "testUrl";
		GlobalURL url = mongoOperation.findOne(GLOBALURL_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), GlobalURL.class);
		assertNotNull(url);
	}

	@Test
	public void testUpdateGlobalURLById() {
		GlobalURL url = new GlobalURL();
		url.setId("testUrl");
		url.setUrl("testURLUpdateById");
		mongoOperation.save(GLOBALURL_COLLECTION_NAME, url);
		
	} 

	@Test
	public void testDeleteGlobalURLById() {
		String id = "testUrl";
		mongoOperation.remove(GLOBALURL_COLLECTION_NAME, new Query(Criteria.where(REST_API_PATH_PARAM_ID).is(id)), GlobalURL.class);
	}
	
}
