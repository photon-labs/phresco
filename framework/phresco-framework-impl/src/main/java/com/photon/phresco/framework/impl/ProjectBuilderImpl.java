package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;

public class ProjectBuilderImpl {
	private static final Logger S_LOGGER = Logger.getLogger(ProjectBuilderImpl.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	private File buildInfoFile;
	private Date currentDate;
	private List<BuildInfo> buildInfoList;
	private String zipName;
	
	public static final String BUILD_CODE	 		= "PHR";
	public static final String STR_UNDERSCORE		= "_";
	public static final String SUCCESS		 		= "SUCCESS";
	
	public ProjectBuilderImpl(File buildInfoFile) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectBuilderImpl.ProjectBuilderImpl(File buildInfoFile)");
		}
		if (DebugEnabled) {
			S_LOGGER.debug("ProjectBuilderImpl() Build FilePath = "+buildInfoFile.getPath());
		}
		this.buildInfoFile = buildInfoFile;
		currentDate = Calendar.getInstance().getTime();
		init();
	}
	
	private void init() throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectBuilderImpl.init()");
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(buildInfoFile));
			String content = reader.readLine();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<BuildInfo>>(){}.getType();
			buildInfoList = (List<BuildInfo>)gson.fromJson(content, listType);
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	/**
	 * This method generates a new build and updates the build info json.
	 * @param buildDir - The base folder of the project
	 * @param targetDir - The target folder in the project
	 * @param deployLocation - deployLocation of the build
	 * @param serverName - server name against which the build is configured
	 * @param databaseName - database name against which the build is configured
	 * @throws PhrescoException
	 */
	public void generateBuild(String buildDir, String targetDir, String deployLocation, 
			String serverName, String databaseName) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectBuilderImpl.generateBuild" +
					"(String buildDir, String targetDir, String deployLocation,String serverName, String databaseName)");
		}
		int nextBuildNo;
		try {
			nextBuildNo = generateNextBuildNo();
			if (DebugEnabled) {
				S_LOGGER.debug("generateBuild() Next build Number = "+ nextBuildNo);
			}
			zipName = BUILD_CODE	+ nextBuildNo + STR_UNDERSCORE + getTimeStampForBuildName() + ".zip";
			String zipFilePath = buildDir + File.separator + zipName;
			ArchiveUtil.createArchive(targetDir, zipFilePath,	ArchiveType.ZIP);
			
			BuildInfo buildInfo = new BuildInfo();
			buildInfo.setBuildNo(nextBuildNo);
			buildInfo.setTimeStamp(getTimeStampForDisplay());
			/*if (isBuildSuccess) {
				buildInfo.setBuildStatus(SUCCESS);
			} else {
				buildInfo.setBuildStatus(FAILURE);
			}*/
			buildInfo.setBuildStatus(SUCCESS);
			if (DebugEnabled) {
				S_LOGGER.debug("generateBuild()  Build Status = "+ buildInfo.getBuildStatus());
			}
			buildInfo.setBuildName(zipName);
			buildInfo.setDeployLocation(deployLocation);
			buildInfo.setServerName(serverName);
			buildInfo.setDatabaseName(databaseName);
			
			buildInfoList.add(buildInfo);
			writeBuildInfo(buildInfoList);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	
	/**
	 * This file writes the list of build info to the build info json file.
	 * @param buildInfos List of build information
	 * @throws PhrescoException
	 */
	public void writeBuildInfo(List<BuildInfo> buildInfos) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectBuilderImpl.writeBuildInfo(List<BuildInfo> buildInfos)");
		}
		FileWriter writer = null;
		try {
			Gson gson = new Gson();
			writer = new FileWriter(buildInfoFile);
			gson.toJson(buildInfos, writer);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	public void deleteBuild(List<BuildInfo> buildInfos) throws PhrescoException {
		deleteBuildInfo(buildInfos);
		deleteBuildArchive(buildInfos);
	}
	
	private void deleteBuildInfo(List<BuildInfo> buildInfos) throws PhrescoException {
		buildInfoList.removeAll(buildInfos);
		writeBuildInfo(buildInfoList);
	}
	
	private void deleteBuildArchive(List<BuildInfo> buildInfos) {
		for (BuildInfo buildInfo : buildInfos) {
			File buildArchive = new File(buildInfoFile.getParent() + buildInfo.getBuildName());
			buildArchive.delete();
		}
	}

	private String getTimeStampForDisplay() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}
	
	private String getTimeStampForBuildName() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}
	
	private int generateNextBuildNo() throws IOException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectBuilderImpl.generateNextBuildNo()");
		}
		int nextBuildNo = 1;
		if (!buildInfoFile.exists()) {
			return nextBuildNo;
		}
		
		int buildArray[] = new int[buildInfoList.size()];
		int count = 0;
		for (BuildInfo buildInfo : buildInfoList) {
			buildArray[count] = buildInfo.getBuildNo();
			count++;
		}
		
		Arrays.sort(buildArray); //sort to the array to find the max build no
		
		nextBuildNo = buildArray[buildArray.length-1]+1; //increment 1 to the max in the build list
		return nextBuildNo;
	}

}
