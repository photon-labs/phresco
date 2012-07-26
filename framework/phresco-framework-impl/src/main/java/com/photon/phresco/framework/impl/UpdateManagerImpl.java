/*
 * ###
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.UpdateManager;
import com.photon.phresco.model.VersionInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UpdateManagerImpl implements UpdateManager {
	VersionInfo version = null;
	String previousVersion = "";
	private static final Logger S_LOGGER = Logger.getLogger(UpdateManagerImpl.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();

	public VersionInfo checkForUpdate(String versionNo) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method UpdateManagerImpl.checkForUpdate(String versionNo)");
		}
		if (DebugEnabled) {
			S_LOGGER.debug("checkForUpdate() Version Number = " + versionNo);
		}
		previousVersion = versionNo;
		Client client = Client.create();
		FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
		WebResource resource = client.resource(configuration.getServerPath() + "/" + FrameworkConstants.VERSION_SERVICE_PATH);
		resource = resource.queryParam(FrameworkConstants.VERSION_SERVICE_PATH, versionNo);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		VersionInfo version = response.getEntity(VersionInfo.class);
		return version;
	}

	public String getCurrentVersion() throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method UpdateManagerImpl.getCurrentVersion()");
		}
		
		try {
			File pomFile = new File (Utility.getPhrescoHome() + File.separator + 
					FrameworkConstants.BIN_DIR +  File.separator + FrameworkConstants.POM_FILE);
			PomProcessor processor = new PomProcessor(pomFile);
			return processor.getProperty(FrameworkConstants.PROPERTY_VERSION);
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	public void doUpdate(String newVersion) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method UpdateManagerImpl.doUpdate(String newVersion)");
		}
		InputStream latestVersionZip = null;
		OutputStream outPutFile = null;
		File tempFile = null;
		try {
			createBackUp();
			latestVersionZip = PhrescoFrameworkFactory.getServiceManager().getLatestVersionPom();
			tempFile = new File(Utility.getPhrescoTemp(), FrameworkConstants.TEMP_ZIP_FILE);
			outPutFile = new FileOutputStream(tempFile);
			 
			int read = 0;
			byte[] bytes = new byte[1024];
		 
			while ((read = latestVersionZip.read(bytes)) != -1) {
				outPutFile.write(bytes, 0, read);
			}
			
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(latestVersionZip);
			Utility.closeStream(outPutFile);
		}
		extractUpdate(tempFile);
	}

	private void extractUpdate(File tempFile) throws PhrescoException {
		ArchiveUtil.extractArchive(tempFile.getPath(), FrameworkConstants.PREV_DIR, ArchiveType.ZIP);
		FileUtil.delete(tempFile);
	}


	private void createBackUp() throws IOException, PhrescoException {
		File tempFile = new File(Utility.getPhrescoTemp(), FrameworkConstants.TEMP_FOLDER);
		tempFile.mkdir();
		File settingsFile = new File(FrameworkConstants.MAVEN_SETTINGS_FILE);
		File binFile = new File(FrameworkConstants.PREV_DIR + FrameworkConstants.BIN_DIR);
		if (binFile.exists()) {
			File[] binFilesList = binFile.listFiles();
			for (File file : binFilesList) {
				if (!file.isDirectory()) {
					FileUtils.copyFileToDirectory(file, new File(tempFile, FrameworkConstants.BIN_DIR));
				}
			}
		}
		if(settingsFile.exists()) {
			FileUtils.copyFileToDirectory(settingsFile, new File(tempFile, FrameworkConstants.OUTPUT_SETTINGS_DIR));
		}
		File fileBackups = new File(FrameworkConstants.BACKUP_DIRNAME);
		if (!fileBackups.exists()) {
			fileBackups.mkdir();
		}
		ArchiveUtil.createArchive(tempFile, 
				new File(FrameworkConstants.BACKUP_DIRNAME + File.separator + previousVersion + FrameworkConstants.ARCHIVE_EXTENSION), ArchiveType.ZIP);
		FileUtil.delete(tempFile);
	}

	public void markVersionUpdated(String newVersion) throws PhrescoException {
		FileWriter writer = null;
		try {
			String fileName = Utility.getPhrescoTemp() + FrameworkConstants.UPGRADE_PROP_NAME;
			writer = new FileWriter(fileName);
			writer.write(newVersion);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
		}
	}
}