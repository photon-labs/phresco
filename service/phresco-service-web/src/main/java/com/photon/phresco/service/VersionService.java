/*
 * ###
 * Service Web Archive
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
package com.photon.phresco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.sonatype.aether.resolution.VersionRangeResolutionException;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VersionInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.service.util.ServerConstants;

@Path("/version")
public class VersionService implements ServerConstants {
	
	private static final String VERSION = "version";
	private static final String STR_DOT = ".";
	private static final String STR_HIPHEN = "-";
	private static final String ALPHA = "alpha";
	private static final String BETA = "beta";	
	private static final String SNAPSHOT = "SNAPSHOT";
	private static final Logger S_LOGGER = Logger.getLogger(VersionService.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	public ServerConfiguration config = null;

	@GET
	@Path("{version}")
	@Produces({ MediaType.APPLICATION_JSON })
	public VersionInfo getVersionJSON(@PathParam(VERSION) String currentVersion) throws VersionRangeResolutionException, PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method VersionServic.getVersionJSON(@PathParam(version) String currentVersion)");
		}
		
		return getVersion(currentVersion);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public VersionInfo getVersionInfo(@QueryParam(VERSION) String currentVersion) throws VersionRangeResolutionException, PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method VersionServic.getVersionJSON(@PathParam(version) String currentVersion)");
		}
		
		return getVersion(currentVersion);
	}

	private VersionInfo getVersion(String currentVersion) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("getVersionJSON() Getting the current Version=" + currentVersion + "");
		}
		
		RepositoryManager repositoryManager = PhrescoServerFactory.getRepositoryManager();
		String latestVersion = repositoryManager.getFrameworkVersion();
		VersionInfo versionInfo = new VersionInfo();
		if (isUpdateRequired(currentVersion, latestVersion)) {
			versionInfo.setUpdateAvailable(true);
			versionInfo.setMessage("Update is available");
		} else {
			versionInfo.setUpdateAvailable(false);
			versionInfo.setMessage("No update is available");
		}
		versionInfo.setFrameworkversion(latestVersion);
		
		return versionInfo;
	}


	private boolean isUpdateRequired(String currentVersion, String latestVersion) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method VersionServic.isUpdateRequired(String currentVersion, String latestVersion)");
		}
		if (isDebugEnabled) {
			S_LOGGER.debug("isUpdateRequired() currentVersion="+currentVersion);
		}
		if (isDebugEnabled) {
			S_LOGGER.debug("isUpdateRequired() LatestVersion="+latestVersion);
		}
		boolean updateRequired = false;
		if (isSnapshot(currentVersion, latestVersion)) {
			if (isDebugEnabled) {
				S_LOGGER.debug("Ignoring update check because of SNAPSHOT version");
			}
			return false;
		}

		String curVerNumericPart = "";
		String curVerAlphabetPart = "";
		if (currentVersion.contains(ALPHA) || currentVersion.contains(BETA)) {
			curVerNumericPart = currentVersion.substring(0, currentVersion.indexOf(STR_HIPHEN));
			curVerAlphabetPart = currentVersion.substring(currentVersion.indexOf(STR_HIPHEN) + 1);
		} else {
			curVerNumericPart = currentVersion;
		}

		String latVerNumericPart = "";
		String latVerAlphabetPart = "";
		if (latestVersion.contains(ALPHA) || latestVersion.contains(BETA)) {
			latVerNumericPart = latestVersion.substring(0, latestVersion.indexOf(STR_HIPHEN));
			latVerAlphabetPart = latestVersion.substring(latestVersion.indexOf(STR_HIPHEN) + 1);
		} else {
			latVerNumericPart = latestVersion;
		}

		if (isUpdateRequiredNumeric(curVerNumericPart, latVerNumericPart)) {
			updateRequired = true;
		} else if (!curVerAlphabetPart.isEmpty() && !latVerAlphabetPart.isEmpty()) {
			updateRequired = isUpdateRequiredAlphabetic(curVerAlphabetPart, latVerAlphabetPart);
		}

		return updateRequired;
	}

	private boolean isSnapshot(String currentVersion, String latestVersion) {
		boolean isSnapshotVersion = false;
		if (currentVersion.contains(SNAPSHOT) || latestVersion.contains(SNAPSHOT)) {
			isSnapshotVersion = true;
		}
		return isSnapshotVersion;
	}

	private boolean isUpdateRequiredAlphabetic(String curVerAlphabetPart, String latVerAlphabetPart) {
		boolean isUpdate = false;
		String currentVersion = "";
		String latestVersion = "";

		if (curVerAlphabetPart.contains(ALPHA) && latVerAlphabetPart.contains(BETA)) {
			return true;
		}

		if (curVerAlphabetPart.indexOf(STR_HIPHEN) > 0) {
			currentVersion = curVerAlphabetPart.substring(curVerAlphabetPart.indexOf(STR_HIPHEN) + 1);
		}
		if (latVerAlphabetPart.indexOf(STR_HIPHEN) > 0) {
			latestVersion = latVerAlphabetPart.substring(latVerAlphabetPart.indexOf(STR_HIPHEN) + 1);
		}
		if (currentVersion.isEmpty() && !latestVersion.isEmpty()) {
			isUpdate = true;
		}
		if (!currentVersion.isEmpty() && !latestVersion.isEmpty()) {
			return isUpdateRequiredNumeric(currentVersion, latestVersion);
		}
		return isUpdate;
	}

	private boolean isUpdateRequiredNumeric(String currentVersion, String latestVersion) {
		StringTokenizer cvst = new StringTokenizer(currentVersion, STR_DOT);
		List<String> currentVersionList = new ArrayList<String>();
		while (cvst.hasMoreTokens()) {
			currentVersionList.add(cvst.nextToken());
		}
		StringTokenizer lvst = new StringTokenizer(latestVersion, STR_DOT);
		List<String> latestVersionList = new ArrayList<String>();
		while (lvst.hasMoreTokens()) {
			latestVersionList.add(lvst.nextToken());
		}
		
		//Below condition solves the major release vs minor release
		// For Ex: if we release the major release as 1.2.0 and the previous
		// minor release as 1.2.0.16000 then the below condition provides
		// the update available option
		if (latestVersionList.size() < currentVersionList.size()) {
			return true;
		}

		alignVersionSize(currentVersionList, latestVersionList);

		int count = currentVersionList.size();
		for (int i = 0; i < count; i++) {
			int cvInt = Integer.parseInt(currentVersionList.get(i));
			int lvInt = Integer.parseInt(latestVersionList.get(i));
			if (lvInt > cvInt) {
				return true;
			} else if (cvInt > lvInt) {
				return false;
			}
		}
		return false;
	}

	private void alignVersionSize(List<String> currentVersionList, List<String> latestVersionList) {
		int cvSize = currentVersionList.size();
		int lvSize = latestVersionList.size();
		if (cvSize > lvSize) {
			int count = cvSize - lvSize;
			for (int i = 0; i < count; i++) {
				latestVersionList.add("0");
			}
		} else if (cvSize < lvSize) {
			int count = lvSize - cvSize;
			for (int i = 0; i < count; i++) {
				currentVersionList.add("0");
			}
		}
	}
	
}