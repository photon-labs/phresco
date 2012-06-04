package com.photon.phresco.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/repo")
public class RepositoryService {

	@GET
	@Path("/ci/config")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCiConfigPath() throws PhrescoException {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		return repoMgr.getRepositoryURL() + repoMgr.getCiConfigPath();
	}

	@GET
	@Path("/ci/svn")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCiSvnPath() throws PhrescoException {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		return repoMgr.getRepositoryURL() + repoMgr.getCiSvnPath();
	}

	@GET
	@Path("/ci/credentialsxml")
	@Produces(MediaType.APPLICATION_XML)
	public InputStream getCredentialXmlFile() throws PhrescoException {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		repoMgr.getCiCredentialXmlFilePath();
		return repoMgr.getArtifactAsStream(repoMgr.getCiCredentialXmlFilePath());
	}
	
	@GET
	@Path("/ci/javahomexml")
	@Produces(MediaType.APPLICATION_XML)
	public InputStream getJavaHomeXmlFile() throws PhrescoException {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		repoMgr.getJavaHomeConfigPath();
		return repoMgr.getArtifactAsStream(repoMgr.getJavaHomeConfigPath());
	}
	
	@GET
	@Path("/ci/mavenhomexml")
	@Produces(MediaType.APPLICATION_XML)
	public InputStream getMavenHomeXmlFile() throws PhrescoException {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		repoMgr.getMavenHomeConfigPath();
		return repoMgr.getArtifactAsStream(repoMgr.getMavenHomeConfigPath());
	}
	
	@GET
	@Path("/ci/mailxml")
	@Produces({ MediaType.APPLICATION_XML })
	public InputStream getProducts()throws Exception {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		InputStream credential = repoMgr.getArtifactAsStream(repoMgr.getCredentialFile());
		return credential;
	}
	
	@GET
	@Path("/ci/emailext")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public InputStream getEmailExtFile()throws Exception {
		PhrescoServerFactory.initialize();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		return repoMgr.getArtifactAsStream(repoMgr.getEmailExtFile());
	}
}
