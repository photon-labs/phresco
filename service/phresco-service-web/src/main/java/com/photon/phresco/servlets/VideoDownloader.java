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
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;

public class VideoDownloader extends Thread implements ServerConstants {

	private static final String VIDEO_FOLDER = "/webapps/";
	private static final Logger s_logger = Logger.getLogger(VideoDownloader.class);
	private static Boolean debugEnabled = s_logger.isDebugEnabled();

	public void run()  {
		if (debugEnabled) {
			s_logger.debug("Entering Method  VideoDownloader.run()");
		}
		try {
		    PhrescoServerFactory.initialize();
			downloadFiles();
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
		
	}

	private void downloadFiles() throws PhrescoException{
		
		PhrescoServerFactory.initialize();
		Gson gson = new Gson();
		RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		String videoInfoJSON = repoMgr.getArtifactAsString(HOMEPAGE_JSON_FILE);
		Type type = new TypeToken<List<VideoInfo>>() {
		}.getType();
		List<VideoInfo> videoInfoList = gson.fromJson(videoInfoJSON, type);
		downloadVideos(videoInfoList);
		downloadImageFiles(videoInfoList);
		
	}
	
	private void downloadVideos(List<VideoInfo> videoInfoList) throws PhrescoException {
	    for (VideoInfo videoinfo : videoInfoList) {
            List<VideoType> videoList = videoinfo.getVideoList();
            for (VideoType videoType : videoList) {
                saveFile(videoType.getUrl());
            }
        }
	}
	
	private void downloadImageFiles(List<VideoInfo> videoInfoList) throws PhrescoException {
	    for (VideoInfo videoinfo : videoInfoList) {
            String imgUrl = videoinfo.getImageurl();
            saveFile(imgUrl);
            }
	}
	
	private void saveFile(String videoURL) throws PhrescoException {
		InputStream in = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(getRepositoryUrl() + videoURL);
			URLConnection connection = url.openConnection();
			in = connection.getInputStream();
			int index = videoURL.lastIndexOf("/");
			String fileName = videoURL.substring(index + 1);
			String filePathStr = videoURL.substring(0, index);
			File filePath = new File("../" + VIDEO_FOLDER + getServerName() + filePathStr);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File videoFile = new File(filePath, fileName);
			if(!videoFile.exists()){
			fos = new FileOutputStream(videoFile);
			byte[] buf = new byte[2048];
			while (true) {
				int len;
				len = in.read(buf);
				if (len == -1) {
					break;
				}
				fos.write(buf, 0, len);
			}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
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
