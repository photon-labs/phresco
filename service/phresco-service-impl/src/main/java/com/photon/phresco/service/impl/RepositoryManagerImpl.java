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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.deployment.DeployRequest;
import org.sonatype.aether.deployment.DeploymentException;
import org.sonatype.aether.repository.Authentication;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.SubArtifact;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;

public class RepositoryManagerImpl implements RepositoryManager {

	private static final Logger S_LOGGER= Logger.getLogger(RepositoryManagerImpl.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private static final String XML = ".xml";
	private static final String DEFAULT = "default";
//	private static final String JAXB_PACKAGE_NAME = "com.photon.phresco.service.jaxb";
	private static final int HTTP_NOT_FOUND = 404;
	private static final String LOCAL_REPO = "../temp/target/local-repo";

	private JAXBContext jaxbContext = null;
	private Unmarshaller unMarshal = null;
	private Marshaller marshal = null;
	private ServerConfiguration config = null;
	private Gson gson = null;

	// TODO:Add ehcaching
	private static HashMap<String, ModuleGroup> modulesCache = new HashMap<String, ModuleGroup>(16);
	private static Map<String, String[]> versionMap = new HashMap<String, String[]>(16);

	private String url;
	private String username;
	private String password;
	
	private void initMap() {
		versionMap.put(TechnologyTypes.PHP, new String[]{"5.4.x", "5.3.x", "5.2.x", "5.1.x", "5.0.x"});
		versionMap.put(TechnologyTypes.PHP_DRUPAL6, new String[]{"6.3", "6.25", "6.19","6.14"});
		versionMap.put(TechnologyTypes.PHP_DRUPAL7, new String[]{"7.8","7.12","7.14"});
		versionMap.put(TechnologyTypes.JAVA_WEBSERVICE, new String[]{"1.6", "1.5"});
		versionMap.put(TechnologyTypes.JAVA_STANDALONE, new String[]{"1.6", "1.5"});
		versionMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, new String[]{"1.6", "1.5"});
		versionMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, new String[]{"1.6", "1.5"});
		versionMap.put(TechnologyTypes.HTML5_WIDGET, new String[]{"1.6", "1.5"});
		versionMap.put(TechnologyTypes.ANDROID_HYBRID, new String[]{"4.0.3", "2.3.3", "2.2"});
		versionMap.put(TechnologyTypes.ANDROID_NATIVE, new String[]{"4.0.3", "2.3.3", "2.2"});
		/*versionMap.put(TechnologyTypes.IPHONE_HYBRID, new String[]{"4.0.3", "2.3.3", "2.2"});
		versionMap.put(TechnologyTypes.IPHONE_NATIVE, new String[]{"4.0.3", "2.3.3", "2.2"});*/
		versionMap.put(TechnologyTypes.WORDPRESS, new String[]{"3.3.1"});
		versionMap.put(TechnologyTypes.DOT_NET, new String[]{"3.5", "3.0", "2.0"});
		versionMap.put(TechnologyTypes.SHAREPOINT, new String[]{"3.5", "3.0", "2.0"});
		versionMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, new String[]{"0.6.x","0.7.x", "0.8.x"});
		
	}
	
	public RepositoryManagerImpl(ServerConfiguration config) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.RepositoryManagerImpl(ServerConfiguration config)");
		}
		this.config = config;
		try {
//			jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE_NAME);
//			unMarshal = jaxbContext.createUnmarshaller();
//			// unMarshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
//			// Boolean.TRUE);
//			marshal = jaxbContext.createMarshaller();
//			// marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
			// Boolean.TRUE);

			gson = new Gson();
			initMap();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	public RepositoryManagerImpl(String url, String username, String password) throws PhrescoException {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	private com.photon.phresco.model.Technology generateTechnology(Technology technology) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.generateTechnology(Technology technology)");
		}

		String id = technology.getId();		
		com.photon.phresco.model.Technology tech = new com.photon.phresco.model.Technology(id,
				technology.getName());
		
		List<String> versions = new ArrayList<String>();
		if(versionMap.get(id) != null) {
		versions = Arrays.asList(versionMap.get(id));
		}
		
		tech.setVersions(versions);
		tech.setModules(getModules(id));
		tech.setJsLibraries(getJSLibraries(id));
//		tech.setDatabases(getDatabases());
//		tech.setServers(getServers());
//		tech.setWebservices(getWebservices());
		return tech;
	}

//	private List<Database> getDatabases() {
//		List<Database> databases = new ArrayList<Database>();
//		
//		List<String> versions = new ArrayList<String>(2);
//		versions.add("5.5.1");
//		versions.add("5.5");
//		versions.add("5.1");
//		versions.add("5.0");
//		versions.add("4.1");
//		versions.add("4.0");
//		databases.add(new Database(1, "MySQL", versions, "My SQL DB"));
//		
//		versions = new ArrayList<String>(2);
//		versions.add("11gR2");
//		versions.add("11gR1");
//		versions.add("10gR2");
//		versions.add("10gR1");
//		versions.add("9iR2");
//		versions.add("9iR1");
//		versions.add("8iR3");
//		versions.add("8iR2");
//		versions.add("8iR1");
//		versions.add("8i");
//		databases.add(new Database(2, "Oracle", versions, "Oracle DB"));
//
//		versions = new ArrayList<String>(2);
//		versions.add("2.0.4");
//		versions.add("1.8.5 ");
//		databases.add(new Database(3, "MongoDB", versions, "Mongo DB"));
//		
//		versions = new ArrayList<String>(2);
//		versions.add("10");
//		versions.add("9.7");
//		versions.add("9.5");
//		versions.add("9");
//		databases.add(new Database(4, "DB2", versions, "DB2 DB"));
//		
//		versions = new ArrayList<String>(2);
//		versions.add("2012");
//		versions.add("2008 R2");
//		versions.add("2008");
//		versions.add("2005");
//		databases.add(new Database(5, "MSSQL", versions, "MSSQL DB"));
//		
//		return databases;
//	}
//
//	private List<Server> getServers() {
//		List<Server> servers = new ArrayList<Server>();
//
//		List<String> versions = new ArrayList<String>(2);
//		versions.add("7.0.x");
//		versions.add("6.0.x");
//		versions.add("5.5.x");
//		
//		servers.add(new Server(1, "Apache Tomcat", versions, "Apache Tomcat Server"));
//		versions = new ArrayList<String>(2);
//		versions.add("7.1.x");
//		versions.add("7.0.x");
//		versions.add("6.1.x");
//		versions.add("6.0.x");
//		versions.add("5.1.x");
//		versions.add("5.0.x");
//		versions.add("4.2.x");
//		versions.add("4.0.x");
//		servers.add(new Server(2, "JBoss", versions, "JBoss application server"));
//
//		versions = new ArrayList<String>(2);
//		versions.add("7.5");
//		versions.add("7.0");
//		versions.add("6.0");
//		versions.add("5.1");
//		versions.add("5.0");
//		servers.add(new Server(3, "IIS", versions, "IIS Server"));
//
//		versions = new ArrayList<String>(2);
//		versions.add("12c(12.1.1)");
//		versions.add("11gR1(10.3.6)");
//		versions.add("11g(10.3.1)");
//		versions.add("10.3");
//		versions.add("10.0");
//		versions.add("9.2");
//		versions.add("9.1");
//		servers.add(new Server(4, "WebLogic", versions, "Web Logic"));
//		
//		versions = new ArrayList<String>(2);
//		versions.add("2.3");
//		versions.add("2.2");
//		versions.add("2.0");
//        versions.add("1.3");
//        servers.add(new Server(5, "Apache", versions, "Apache"));
//        
//        versions = new ArrayList<String>(2);
//        versions.add("0.6.x");
//        servers.add(new Server(6, "NodeJS", versions, "NodeJS"));
//        
//        versions = new ArrayList<String>(2);
//        versions.add("8.x");
//        versions.add("7.x");
//        versions.add("6.x");
//        versions.add("5.x");
//        versions.add("4.x");
//        servers.add(new Server(7, "Jetty", versions, "Jetty"));
//
//		return servers;
//	}
//
//	private List<WebService> getWebservices() {
//		List<WebService> databases = new ArrayList<WebService>();
//		databases.add(new WebService(1, "REST/JSON", "1.0", "REST JSON web services"));
//		databases.add(new WebService(2, "REST/XML", "1.0", "REST XML web services"));
//		databases.add(new WebService(3, "SOAP", "1.1", "SOAP 1.1"));
//		databases.add(new WebService(4, "SOAP", "1.2", "SOAP 1.2"));
//		return databases;
//	}

	public List<ModuleGroup> getModules(String techId) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getModules(String techId)");
		}
		if (isDebugEnabled) {
			S_LOGGER.debug("getModules() Technology ID=" + techId);
		}
		 
		 return null;
	}

	public List<ModuleGroup> getJSLibraries(String techId) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getJSLibraries(String techId)");
		}
		if (isDebugEnabled) {
			S_LOGGER.debug("getJSLibraries() Technology ID=" + techId);
		}
		String jslibraryFile = config.getjsLibrariesFile(techId);
		if (Utility.isEmpty(jslibraryFile)) {
			return Collections.emptyList();
		}
		
		return null;
	}

	// TODO:Initialize only once on the constructor
	private RepositorySystem newRepositorySystem() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.newRepositorySystem())");
		}
		try {
			return new DefaultPlexusContainer().lookup(RepositorySystem.class);
		} catch (ComponentLookupException e) {
			throw new PhrescoException(e);
		} catch (PlexusContainerException e) {
			throw new PhrescoException(e);
		}
	}

	private RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.newRepositorySystemSession(RepositorySystem system))");
		}
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();

		LocalRepository localRepo = new LocalRepository(LOCAL_REPO);
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));

		return session;
	}

	public String addArtifact(ArtifactInfo info, File artifactFile) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.addArtifact(ArtifactInfo info, File artifactFile)");
		}
		RepositorySystem system = newRepositorySystem();
		RepositorySystemSession session = newRepositorySystemSession(system);
		Artifact artifact = new DefaultArtifact(info.getGroupId(), info.getArtifact(), info.getClassifier(), info
				.getPackage(), info.getVersion());

		artifact = artifact.setFile(artifactFile);

		RemoteRepository distRepo = new RemoteRepository("", DEFAULT, config.getRepositoryURL());

		Authentication authentication = new Authentication(config.getRepositoryUser(), config.getRepositoryPassword());
		distRepo.setAuthentication(authentication);
		DeployRequest deployRequest = new DeployRequest();
		deployRequest.addArtifact(artifact);
		if (info.getPomFile() != null) {
			Artifact pom = new SubArtifact(artifact, null, "pom");
			pom = pom.setFile(info.getPomFile());
			deployRequest.addArtifact(pom);
		}

		deployRequest.setRepository(distRepo);

		try {
			system.deploy(session, deployRequest);
		} catch (DeploymentException e) {
			throw new PhrescoException(e);
		}

		return "Succesfully Deployed";
	}

	@Override
	public boolean isExist(String filePath) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getArtifactAsString(String filePath)");
		}
		InputStream is = null;
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("getArtifactAsString() FilePath=" + filePath);
			}
			URL url = new URL(config.getRepositoryURL() + filePath);
			URLConnection openConnection = url.openConnection();
			int responseCode = ((HttpURLConnection) openConnection).getResponseCode();
			return (responseCode != HTTP_NOT_FOUND);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
		}
	}

	@Override
	public String getArtifactAsString(String filePath) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getArtifactAsString(String filePath)");
		}
		InputStream is = null;
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("getArtifactAsString() FilePath=" + filePath);
			}
			URL url = new URL(config.getRepositoryURL() + filePath);
			is = url.openStream();
			return IOUtils.toString(is);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
		}
	}

	@Override
	public InputStream getArtifactAsStream(String filePath) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getArtifactAsStream(String filePath)");
		}
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("getArtifactAsStream() FilePath=" + filePath);
			}
			URL url = new URL(config.getRepositoryURL() + filePath);
			return url.openStream();
		} catch (MalformedURLException e) {
			S_LOGGER.debug("getArtifactAsStream =" + filePath, e);
			throw new PhrescoException(e);
		} catch (IOException e) {
			S_LOGGER.debug("getArtifactAsStream =" + filePath, e);
			throw new PhrescoException(e);
		}
	}

	@Override
	public String getRepositoryURL() throws PhrescoException {
		return config.getRepositoryURL();
	}

	// get highest service version
	@Override
	public String getFrameworkVersion() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getFrameworkVersion()");
		}
		String latestFrameworkVersion = config.getLatestFrameworkVersion();
		return latestFrameworkVersion;
	}

	@Override
	public String getServerVersion() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method RepositoryManagerImpl.getFrameworkVersion()");
		}
		String latestServiceVersion = config.getLatestServiceVersion();
		return latestServiceVersion;
	}

	private void removeLocalRepo() {
		File localRepo = new File(LOCAL_REPO);
		removeDirectory(localRepo.getParentFile());
	}

	public static boolean removeDirectory(File directory) {
		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();

		// Some JVMs return null for File.list() when the
		// directory is empty.
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);
				if (entry.isDirectory()) {
					if (!removeDirectory(entry))
						return false;
				} else {
					if (!entry.delete())
						return false;
				}
			}
		}

		return directory.delete();
	}

	@Override
	public String getCiConfigPath() throws PhrescoException {
		return config.getCiConfigFile();
	}

	@Override
	public String getCiSvnPath() throws PhrescoException {
		return config.getCiSvnFile();
	}

	@Override
	public String getJavaHomeConfigPath() throws PhrescoException {
		return config.getJavaHomeConfigFile();
	}

	@Override
	public String getMavenHomeConfigPath() throws PhrescoException {
		return config.getMavenHomeConfigFile();
	}

	@Override
	public String getCiCredentialXmlFilePath() throws PhrescoException {
		return config.getCiCredentialXmlFilePath();
	}

	public String getSettingConfigFile() throws PhrescoException {
		return config.getSettingConfigFile();
	}

	public String getHomePageJsonFile() throws PhrescoException {
		return config.getHomePageJsonFile();
	}

	public String getAdminConfigFile() throws PhrescoException {
		return config.getAdminConfigFile();
	}
	
	public String getCredentialFile() throws PhrescoException {
		return config.getCredentialFile();
	}
	
	public String getAuthServiceURL() throws PhrescoException {
		return config.getAuthServiceURL();
	}
	
	@Override
	public String getEmailExtFile() throws PhrescoException {
		return config.getEmailExtFile();
	} 
	
	public List<ProjectInfo> addPilotProjects(String techId) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTechnology(String apptype, com.photon.phresco.model.Technology tech) throws PhrescoException {
		// TODO Auto-generated method stub
	}
	
	public void addVideo(VideoInfo videoInfo, File dirPath) throws PhrescoException {
		boolean exist = isExist(ServerConstants.HOMEPAGE_JSON_FILE);

		List<VideoInfo> videoInfoList = null;
		if (exist) {
			// Read the VideoInfos from Nexus
			String videoInfoJSON = getArtifactAsString(ServerConstants.HOMEPAGE_JSON_FILE);

			// Convert it into VideoInfo JSON objects
			Type type = new TypeToken<List<VideoInfo>>() {
			}.getType();
			videoInfoList = gson.fromJson(videoInfoJSON, type);
		} else {
			videoInfoList = new ArrayList<VideoInfo>(32);
		}

		// Add the video to the list
		videoInfoList.add(videoInfo);

		// convert the JSON objects into String
		String json = gson.toJson(videoInfoList);

		// Write to temp File
		File tempFile = new File(Utility.getSystemTemp(), "video.json");
		writeToFile(json, tempFile);

		// add to nexus
		ArtifactInfo info = new ArtifactInfo("videos.homepage", "videoinfo", "", "json", "1.0");
		addArtifact(info, tempFile);

		// TODO:Upload the video content from the inputFile

		// delete the temp file
		FileUtil.delete(tempFile);
	}

	private void writeToFile(String content, File file) throws PhrescoException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addSettings(SettingsTemplate settingsTemplate, File dirPath) throws PhrescoException {
		boolean exist = isExist(ServerConstants.SETTINGS_CONFIG_FILE);
		List<SettingsTemplate> settingsList = null;

		if (exist) {
			// Read the VideoInfos from Nexus
			String settingsJson = getArtifactAsString(ServerConstants.SETTINGS_CONFIG_FILE);

			// Convert it into VideoInfo JSON objects
			Type type = new TypeToken<List<SettingsTemplate>>() {
			}.getType();
			settingsList = gson.fromJson(settingsJson, type);
		} else {
			settingsList = new ArrayList<SettingsTemplate>(32);
		}

		// Add the video to the list
		settingsList.add(settingsTemplate);

		// convert the JSON objects into String
		String json = gson.toJson(settingsList);

		// Write to temp File
		File tempFile = new File(Utility.getSystemTemp(), "settings.json");
		writeToFile(json, tempFile);

		// add to nexus
		ArtifactInfo info = new ArtifactInfo("config", "settings", "", "json", "1.0");
		addArtifact(info, tempFile);

		// delete the temp file
		FileUtil.delete(tempFile);
	}

	public void removeVideo(VideoInfo videoInfo) throws PhrescoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAdminConfig(AdminConfigInfo configInfo) throws PhrescoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAdminConfig(AdminConfigInfo videoInfo) throws PhrescoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDownload(DownloadInfo downloadInfo, File dirPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Download(DownloadInfo downloadInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ApplicationType> getApplicationTypes() throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addApplicationTypes(List<ApplicationType> apptypes)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProjectInfo> getPilotProjects(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getFrameWorkLatestFile() throws PhrescoException {
		return config.getFrameWorkLatestFile();
	}

}
