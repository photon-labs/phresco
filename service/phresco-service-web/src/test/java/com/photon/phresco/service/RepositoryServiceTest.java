package com.photon.phresco.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;

public class RepositoryServiceTest {
	
	RepositoryManager repoMgr = null;
	String repositoryURL = null;
	
	@Before
	public void init() throws PhrescoException {
		PhrescoServerFactory.initialize();
		repoMgr = PhrescoServerFactory.getRepositoryManager();
		repositoryURL = repoMgr.getRepositoryURL();	
	}
	
	@Ignore
	public void testGetCiConfigPath() throws PhrescoException {
		String configPath= repoMgr.getRepositoryURL() + repoMgr.getCiConfigPath();
		assertEquals(repositoryURL + "/config/ci/config/0.2/config-0.2.xml", configPath);
	}

	@Ignore
	public void testGetCredentialXmlFile() throws PhrescoException {
		InputStream cXmlFile= repoMgr.getArtifactAsStream(repoMgr.getCiCredentialXmlFilePath());
		assertNotNull(cXmlFile);
	}

	@Ignore
	public void testGetJavaHomeXmlFile() throws PhrescoException {
		InputStream javaHome= repoMgr.getArtifactAsStream(repoMgr.getJavaHomeConfigPath());
		assertNotNull(javaHome);
	}

	@Ignore
	public void testGetMavenHomeXmlFile() throws PhrescoException {
		InputStream mavenHome= repoMgr.getArtifactAsStream(repoMgr.getMavenHomeConfigPath());
		assertNotNull(mavenHome);
	}

	@Ignore
	public void testGetProducts() throws PhrescoException {
		InputStream credential = repoMgr.getArtifactAsStream(repoMgr.getCredentialFile());
		assertNotNull(credential);
	}

	@Test
	public void testGetEmailExtFile() throws PhrescoException {
		InputStream eeFile=repoMgr.getArtifactAsStream(repoMgr.getEmailExtFile());
		assertNotNull(eeFile);
	}
}
