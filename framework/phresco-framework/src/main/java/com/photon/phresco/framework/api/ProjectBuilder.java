package com.photon.phresco.framework.api;

import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;

public interface ProjectBuilder {
	
	 void generateBuild(String buildDir, String targetDir, String deployLocation, 
			String serverName, String databaseName) throws PhrescoException;
	
	 void writeBuildInfo(List<BuildInfo> buildInfos) throws PhrescoException;
	
	 void deleteBuild(List<BuildInfo> buildInfos) throws PhrescoException;

}
