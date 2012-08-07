package com.photon.phresco.service;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;

public class DownloadsServiceTest implements ServerConstants{

	private static final String SOFTWARE_REPO_PATH = "/softwares/info/1.0/info-1.0.json";
	@Test
	public void testGetAvailableDownloads() throws PhrescoException {
    	PhrescoServerFactory.initialize();
    	RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
		String downloadInfoJSON = repoMgr.getArtifactAsString(SOFTWARE_REPO_PATH);
    	Type type = new TypeToken<List<DownloadInfo>>() {}.getType();
		Gson gson = new Gson();
		List<DownloadInfo> downloadInfoList = gson.fromJson(downloadInfoJSON, type);
    	assertNotNull(downloadInfoList);
	}

}
