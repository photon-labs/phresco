/*
 * ###
 * Xcodebuild Command-Line Wrapper
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
 * Copyright (c)  2012 Photon infotech.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 *  Contributors:
 *  	  Photon infotech - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.plugins.xcode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.photon.phresco.plugin.commons.PluginUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.plexus.archiver.zip.ZipArchiver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.BuildInfo;
import com.photon.phresco.commons.XCodeConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.xcode.utils.SdkVerifier;
import com.photon.phresco.plugins.xcode.utils.XcodeUtil;

/**
 * Run the xcodebuild command line program
 * 
 * @goal xcodebuild
 * @phase compile
 */
public class XcodeBuild extends AbstractMojo {

	private static final String DO_NOT_CHECKIN_BUILD = "/do_not_checkin/build";

	/**
	 * Location of the xcodebuild executable.
	 * 
	 * @parameter expression="${xcodebuild}" default-value="/usr/bin/xcodebuild"
	 */
	private File xcodeCommandLine;

	/**
	 * Project Name
	 * 
	 * @parameter
	 */
	private String xcodeProject;

	/**
	 * Target to be built
	 * 
	 * @parameter expression="${targetName}"
	 */
	private String xcodeTarget;
	
	/**
	 * @parameter expression="${encrypt}"
	 */
	private boolean encrypt;

	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * @parameter expression="${basedir}"
	 */
	private String basedir;
	/**
	 * @parameter expression="${unittest}"
	 */
	private boolean unittest;
	/**
	 * Build directory.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File buildDirectory;

	/**
	 * @parameter expression="${configuration}" default-value="Debug"
	 */
	private String configuration;

	/**
	 * @parameter expression="${sdk}" default-value="iphonesimulator5.0"
	 */
	private String sdk;
	
	/**
	 * @parameter 
	 */
	protected String gccpreprocessor;

	/**
	 * The java sources directory.
	 * 
	 * @parameter default-value="${project.basedir}"
	 * 
	 * @readonly
	 */
	protected File baseDir;

	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;

	/**
	 * XML property list file. In this file the webserverName
	 * 
	 * @parameter expression="${plistfile}"
	 *            default-value="phresco-env-config.xml"
	 */
	protected String plistFile;

	private File srcDir;
	private File buildDirFile;
	private File buildInfoFile;
	private List<BuildInfo> buildInfoList;
	private int nextBuildNo;
	private Date currentDate;
	private String appFileName;
	private String dSYMFileName;
	private String deliverable;
	private Map<String, Object> sdkOptions;
	
	/**
	 * Execute the xcode command line utility.
	 */
	public void execute() throws MojoExecutionException {
		if (!xcodeCommandLine.exists()) {
			throw new MojoExecutionException("Invalid path, invalid xcodebuild file: "
					+ xcodeCommandLine.getAbsolutePath());
		}
		/*
		 * // Compute archive name String archiveName =
		 * project.getBuild().getFinalName() + ".cust"; File finalDir = new
		 * File(buildDirectory, archiveName);
		 * 
		 * // Configure archiver MavenArchiver archiver = new MavenArchiver();
		 * archiver.setArchiver(jarArchiver); archiver.setOutputFile(finalDir);
		 */

			try {
				if(!SdkVerifier.isAvailable(sdk)) {
					throw new MojoExecutionException("Selected version " +sdk +" is not available!");
				}
			} catch (IOException e2) {
				throw new MojoExecutionException("SDK verification failed!");
			} catch (InterruptedException e2) {
				throw new MojoExecutionException("SDK verification interrupted!");
            }
			
			try {
			init();
			configure();
			ProcessBuilder pb = new ProcessBuilder(xcodeCommandLine.getAbsolutePath());
			// Include errors in output
			pb.redirectErrorStream(true);

			List<String> commands = pb.command();
			if (xcodeProject != null) {
				commands.add("-project");
				commands.add(xcodeProject);
			}
			if (StringUtils.isNotBlank(configuration)) {
				commands.add("-configuration");
				commands.add(configuration);
			}

			if (StringUtils.isNotBlank(sdk)) {
				commands.add("-sdk");
				commands.add(sdk);
			}

			commands.add("OBJROOT=" + buildDirectory);
			commands.add("SYMROOT=" + buildDirectory);
			commands.add("DSTROOT=" + buildDirectory);

			if (StringUtils.isNotBlank(xcodeTarget)) {
				commands.add("-target");
				commands.add(xcodeTarget);
			}
			
			if(StringUtils.isNotBlank(gccpreprocessor)) {
				commands.add("GCC_PREPROCESSOR_DEFINITIONS="+gccpreprocessor);
			}

			if (unittest) {
				commands.add("clean");
				commands.add("build");
			}

			getLog().info("List of commands" + pb.command());
			// pb.command().add("install");
			pb.directory(new File(basedir));
			Process child = pb.start();

			// Consume subprocess output and write to stdout for debugging
			InputStream is = new BufferedInputStream(child.getInputStream());
			int singleByte = 0;
			while ((singleByte = is.read()) != -1) {
				// output.write(buffer, 0, bytesRead);
				System.out.write(singleByte);
			}

			child.waitFor();
			int exitValue = child.exitValue();
			getLog().info("Exit Value: " + exitValue);
			if (exitValue != 0) {
				throw new MojoExecutionException("Compilation error occured. Resolve the error(s) and try again!");
			}
			
			if(!unittest) {
				//In case of unit testcases run, the APP files will not be generated.
				createdSYM();
				createApp(); 
			}
			/*
			 * child.waitFor();
			 * 
			 * InputStream in = child.getInputStream(); InputStream err =
			 * child.getErrorStream(); getLog().error(sb.toString());
			 */
		} catch (IOException e) {
			getLog().error("An IOException occured.");
			throw new MojoExecutionException("An IOException occured", e);
		} catch (InterruptedException e) {
			getLog().error("The clean process was been interrupted.");
			throw new MojoExecutionException("The clean process was been interrupted", e);
		} catch (MojoFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File directory = new File(this.basedir + "/pom.xml");
		this.project.getArtifact().setFile(directory);
	}

	private void init() throws MojoExecutionException, MojoFailureException {

		try {
			// To Delete the buildDirectory if already exists
			if (buildDirectory.exists()) {
				FileUtils.deleteDirectory(buildDirectory);
				buildDirectory.mkdirs();
			}

			buildInfoList = new ArrayList<BuildInfo>(); // initialization
			// srcDir = new File(baseDir.getPath() + File.separator +
			// sourceDirectory);
			buildDirFile = new File(baseDir, DO_NOT_CHECKIN_BUILD);
			if (!buildDirFile.exists()) {
				buildDirFile.mkdirs();
				getLog().info("Build directory created..." + buildDirFile.getPath());
			}
			buildInfoFile = new File(buildDirFile.getPath() + "/build.info");
			System.out.println("file created " + buildInfoFile);
			nextBuildNo = generateNextBuildNo();
			currentDate = Calendar.getInstance().getTime();
		}
		catch (IOException e) {
			throw new MojoFailureException("APP could not initialize " + e.getLocalizedMessage());
		 }
	}

	private int generateNextBuildNo() throws IOException {
		int nextBuildNo = 1;
		if (!buildInfoFile.exists()) {	
   			return nextBuildNo;
			}
		
		BufferedReader read = new BufferedReader(new FileReader(buildInfoFile));
		String content = read.readLine();
		Gson gson = new Gson();
		java.lang.reflect.Type listType = new TypeToken<List<BuildInfo>>() {
		}.getType();
		buildInfoList = (List<BuildInfo>) gson.fromJson(content, listType);
		if (buildInfoList == null || buildInfoList.size() == 0) {
			return nextBuildNo;
		}
		int buildArray[] = new int[buildInfoList.size()];
		int count = 0;
		for (BuildInfo buildInfo : buildInfoList) {
			buildArray[count] = buildInfo.getBuildNo();
			count++;
		}

		Arrays.sort(buildArray); // sort to the array to find the max build no

		nextBuildNo = buildArray[buildArray.length - 1] + 1; // increment 1 to
																// the max in
																// the build
																// list
		return nextBuildNo;

		
	}
	

	private void createApp() throws MojoExecutionException {
		File outputFile = getAppName();
		if (outputFile == null) {
			getLog().error("xcodebuild failed. resultant APP not generated!");
			throw new MojoExecutionException("xcodebuild has been failed");
		}
		if (outputFile.exists()) {

			try {
				System.out.println("Completed " + outputFile.getAbsolutePath());
				getLog().info("APP created.. Copying to Build directory.....");
				String buildName = project.getBuild().getFinalName() + '_' + getTimeStampForBuildName(currentDate);
				File baseFolder = new File(baseDir + DO_NOT_CHECKIN_BUILD, buildName);
				if (!baseFolder.exists()) {
					baseFolder.mkdirs();
					getLog().info("build output direcory created at " + baseFolder.getAbsolutePath());
				}
				File destFile = new File(baseFolder, outputFile.getName());
				getLog().info("Destination file " + destFile.getAbsolutePath());
				XcodeUtil.copyFolder(outputFile, destFile);
				getLog().info("copied to..." + destFile.getName());
				appFileName = destFile.getAbsolutePath();

				getLog().info("Creating deliverables.....");
				ZipArchiver zipArchiver = new ZipArchiver();
				zipArchiver.addDirectory(baseFolder);
				File deliverableZip = new File(baseDir + DO_NOT_CHECKIN_BUILD, buildName + ".zip");
				zipArchiver.setDestFile(deliverableZip);
				zipArchiver.createArchive();

				deliverable = deliverableZip.getAbsolutePath();
				getLog().info("Deliverables available at " + deliverableZip.getName());
				writeBuildInfo(true);
			} catch (IOException e) {
				throw new MojoExecutionException("Error in writing output..." + e.getLocalizedMessage());
			}

		} else {
			getLog().info("output directory not found");
		}
	}

	private void createdSYM() throws MojoExecutionException {
		File outputFile = getdSYMName();
		if (outputFile == null) {
			getLog().error("xcodebuild failed. resultant dSYM not generated!");
			throw new MojoExecutionException("xcodebuild has been failed");
		}
		if (outputFile.exists()) {

			try {
				System.out.println("Completed " + outputFile.getAbsolutePath());
				getLog().info("dSYM created.. Copying to Build directory.....");
				String buildName = project.getBuild().getFinalName() + '_' + getTimeStampForBuildName(currentDate);
				File baseFolder = new File(baseDir + DO_NOT_CHECKIN_BUILD, buildName);
				if (!baseFolder.exists()) {
					baseFolder.mkdirs();
					getLog().info("build output direcory created at " + baseFolder.getAbsolutePath());
				}
				File destFile = new File(baseFolder, outputFile.getName());
				getLog().info("Destination file " + destFile.getAbsolutePath());
				XcodeUtil.copyFolder(outputFile, destFile);
				getLog().info("copied to..." + destFile.getName());
				dSYMFileName = destFile.getAbsolutePath();

				getLog().info("Creating deliverables.....");
				ZipArchiver zipArchiver = new ZipArchiver();
				zipArchiver.addDirectory(baseFolder);
				File deliverableZip = new File(baseDir + DO_NOT_CHECKIN_BUILD, buildName + ".zip");
				zipArchiver.setDestFile(deliverableZip);
				zipArchiver.createArchive();

				deliverable = deliverableZip.getAbsolutePath();
				getLog().info("Deliverables available at " + deliverableZip.getName());

			} catch (IOException e) {
				throw new MojoExecutionException("Error in writing output..." + e.getLocalizedMessage());
			}

		} else {
			getLog().info("output directory not found");
		}
	}

	private File getAppName() {
		String path = configuration + "-";
		if (sdk.startsWith("iphoneos")) {
			path = path + "iphoneos";
		} else {
			path = path + "iphonesimulator";
		}

		File baseFolder = new File(buildDirectory, path);
		File[] files = baseFolder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.getName().endsWith("app")) {
				return file;
			}
		}
		return null;
	}

	private File getdSYMName() {
		String path = configuration + "-";
		if (sdk.startsWith("iphoneos")) {
			path = path + "iphoneos";
		} else {
			path = path + "iphonesimulator";
		}

		File baseFolder = new File(buildDirectory, path);
		File[] files = baseFolder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.getName().endsWith("dSYM")) {
				return file;
			}
		}
		return null;
	}

	private void writeBuildInfo(boolean isBuildSuccess) throws MojoExecutionException {
		try {
			PluginUtils pu = new PluginUtils();
			BuildInfo buildInfo = new BuildInfo();
			List<String> envList = pu.csvToList(environmentName);
			buildInfo.setBuildNo(nextBuildNo);
			buildInfo.setTimeStamp(getTimeStampForDisplay(currentDate));
			if (isBuildSuccess) {
				buildInfo.setBuildStatus("SUCCESS");
			} else {
				buildInfo.setBuildStatus("FAILURE");
			}
			buildInfo.setBuildName(appFileName);
			buildInfo.setDeliverables(deliverable);
			buildInfo.setEnvironments(envList);

			Map<String, Boolean> sdkOptions = new HashMap<String, Boolean>(2);
			boolean isDeviceBuild = Boolean.FALSE;
			if (sdk.startsWith("iphoneos")) {
				isDeviceBuild = Boolean.TRUE;
			}
			sdkOptions.put(XCodeConstants.CAN_CREATE_IPA, isDeviceBuild);
			sdkOptions.put(XCodeConstants.DEVICE_DEPLOY, isDeviceBuild);
	        buildInfo.setOptions(sdkOptions);
	        
//			Gson gson2 = new Gson();	       
//	        String json = gson2.toJson(sdkOptions);
//	        System.out.println("json = " + json);
			
			buildInfoList.add(buildInfo);
			Gson gson2 = new Gson();
			FileWriter writer = new FileWriter(buildInfoFile);
			//gson.toJson(buildInfoList, writer);
			String json = gson2.toJson(buildInfoList);
			System.out.println("json = " + json);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private String getTimeStampForDisplay(Date currentDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}

	private String getTimeStampForBuildName(Date currentDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}

	private void configure() throws MojoExecutionException {
		if (StringUtils.isEmpty(environmentName)) {
			return;
		}
//		try {
		getLog().info("Configuring the project....");
		getLog().info("environment name :" + environmentName);
		getLog().info("base dir name :" + baseDir.getName());
		File srcConfigFile = new File(baseDir, project.getBuild().getSourceDirectory() + File.separator + plistFile);
		String basedir = baseDir.getName();
		PluginUtils pu = new PluginUtils();
		pu.executeUtil(environmentName, basedir, srcConfigFile);
	//	if(encrypt) {
	//	pu.encode(srcConfigFile);
	//	}
//		} catch (PhrescoException e) {
//			throw new MojoExecutionException(e.getMessage(), e);
//		}

	}
}
