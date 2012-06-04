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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.UpdateManager;
import com.photon.phresco.model.VersionInfo;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UpdateManagerImpl implements UpdateManager {
	VersionInfo version = null;
	private static final Logger S_LOGGER = Logger.getLogger(UpdateManagerImpl.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();

	public VersionInfo checkForUpdate(String versionNo) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method UpdateManagerImpl.checkForUpdate(String versionNo)");
		}
		if (DebugEnabled) {
			S_LOGGER.debug("checkForUpdate() Version Number = " + versionNo);
		}
		Client client = Client.create();
		FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
		WebResource resource = client.resource(configuration.getServerPath() + "/version/" + versionNo);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		VersionInfo version = response.getEntity(VersionInfo.class);
		return version;
	}

	public String getCurrentVersion() throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method UpdateManagerImpl.getCurrentVersion()");
		}
		try {
			PomProcessor processor = new PomProcessor(new File(FrameworkConstants.POM_FILE));
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
		try {
			backupPomFile();
			PomProcessor processor = new PomProcessor(new File(FrameworkConstants.POM_FILE));
			processor.setModelVersion(newVersion);
			processor.setProperty(FrameworkConstants.PROPERTY_VERSION, newVersion);
			processor.save();
			markVersionUpdated(newVersion);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	private void backupPomFile() throws IOException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method UpdateManagerImpl.backupPomFile()");
		}
		FileReader in = null;
		FileWriter out = null;
		try {
			File inputFile = new File(FrameworkConstants.POM_FILE);
			File outputFile = new File("pom-backup.xml");
	
			in = new FileReader(inputFile);
			out = new FileWriter(outputFile);
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (in != null) {
				out.close();
			}
		}
	}

	public void markVersionUpdated(String newVersion) throws PhrescoException {
		FileWriter writer = null;
		try {
			String fileName = Utility.getPhrescoTemp() + "upgrade.properties";
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