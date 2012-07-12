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
package com.photon.phresco.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;

public class ImageDownloader extends Thread implements ServerConstants {
	private static final String WEB_APPS_FOLDER = "/webapps/";
	private static final String IMAGES_FOLDER = "/images";
	private static final String BACK_FOLDER = "../";
	private static final String SEPERATOR = "/";
	private static final Logger s_logger = Logger.getLogger(ImageDownloader.class);
	private static Boolean debugEnabled = s_logger.isDebugEnabled();
	private static Map<String, String> pathMap = new HashMap<String, String>();
	
	static {
		initMap();
	}
	
	private static void initMap() {
		pathMap.put(TechnologyTypes.ANDROID_HYBRID, "modules/tech-android-hybrid/icons/1.0/icons-1.0.zip");
		pathMap.put(TechnologyTypes.ANDROID_NATIVE, "modules/tech-android-native/icons/1.0/icons-1.0.zip");
		pathMap.put(TechnologyTypes.IPHONE_NATIVE, "modules/tech-iphone-native/icons/1.0/icons-1.0.zip");
		pathMap.put(TechnologyTypes.PHP, "modules/tech-php/icons/1.0/icons-1.0.zip");
		pathMap.put(TechnologyTypes.SHAREPOINT, "modules/tech-sharepoint/icons/1.0/icons-1.0.zip");
	}
	
	public void run()  {
		if (debugEnabled) {
			s_logger.debug("Entering Method ImageDownloader.run()");
		}
		try {
			for (String tech : pathMap.values()) {
				saveFile(tech);
			}
		} catch (PhrescoException e) {
			if (debugEnabled) {
				s_logger.debug("Entering Method ImageDownloader.run()");
			}
		}
	}

	private void saveFile(String tech) throws PhrescoException {
		try {
			InputStream inputStream = null;
			URL url = new URL(getRepositoryUrl() + tech);
			URLConnection connection = url.openConnection();
			inputStream = connection.getInputStream();
			int index = tech.lastIndexOf(SEPERATOR);
			String fileName = tech.substring(index + 1);
            File imageFile = new File(BACK_FOLDER + WEB_APPS_FOLDER + getServerName() + IMAGES_FOLDER);
            if (!imageFile.exists()) {
            	imageFile.mkdirs();
			}
			FileOutputStream fileOutputStream = null;
			File archiveFile = new File(imageFile , fileName);
			fileOutputStream = new FileOutputStream(archiveFile);
			try {
				byte[] data = new byte[1024];
				int i = 0;
				while ((i = inputStream.read(data)) != -1) {
					fileOutputStream.write(data, 0, i);
				}
				fileOutputStream.flush();
				ArchiveUtil.extractArchive(archiveFile.getPath(), imageFile.getPath(), ArchiveType.ZIP);
			} finally {
				Utility.closeStream(inputStream);
				Utility.closeStream(fileOutputStream);
				archiveFile.delete();
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			
		}
	}

	private String getRepositoryUrl() throws PhrescoException {
		String repositoryUrl = PhrescoServerFactory.getRepositoryManager().getRepositoryURL();
		return repositoryUrl;
	}
	
	private String getServerName() throws PhrescoException {
	    PhrescoServerFactory.initialize();
	    String serviceName = PhrescoServerFactory.getRepositoryManager().getServiceContextName();
        return serviceName;
	}
	
}
