/*
 * ###
 * windows-phone-maven-plugin Maven Mojo
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
package com.photon.phresco.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.plugins.model.WP8PackageInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.PluginUtils;
import com.photon.phresco.util.Utility;

/**
 * Goal which generated the installable file for Windows Phone 7
 * 
 * @goal package
 * 
 */

public class WPPackage extends AbstractMojo implements PluginConstants {

	/**
	 * The Maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * @parameter expression="${project.basedir}" required="true"
	 * @readonly
	 */
	protected File baseDir;

	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;

	/**
	 * @parameter expression="${buildName}" required="true"
	 */
	protected String buildName;

	/**
	 * @parameter expression="${buildNumber}" required="true"
	 */
	protected String buildNumber;

	/**
	 * @parameter expression="${project.name}" required="true"
	 * @readonly
	 */
	protected String projectName;
	
	/**
	 * @parameter expression="${type}" required="true"
	 * @readonly
	 */
	protected String type;
	
	/**
	 * @parameter expression="${configuration}" required="true"
	 * Release / Debug
	 */
	protected String configuration;
	
	/**
	 * @parameter expression="${platform}" required="true"
	 * AnyCPU / ARM / x86 / x64
	 */
	protected String platform;

	protected int buildNo;

	private File buildDir;
	private File buildInfoFile;
	private File tempDir;
	private List<BuildInfo> buildInfoList;
	private int nextBuildNo;
	private String zipName;
	private Date currentDate;
	private String sourceDirectory = "\\source";
	private File[] solutionFile;
	private String projectRootFolder;
	private WP8PackageInfo packageInfo;
	private File rootDir;

	public void execute() throws MojoExecutionException, MojoFailureException {
		init();
		if(type.equalsIgnoreCase("wp8")) {
			generateWP8Package();
		} else {
			generateWP7Package();
		}
		boolean buildStatus = build();
		writeBuildInfo(buildStatus);
		cleanUp();
	}


	private void init() throws MojoExecutionException {
		try {
			if (StringUtils.isEmpty(environmentName) || StringUtils.isEmpty(type)) {
				callUsage();
			}
			
			if(type.equalsIgnoreCase("wp8")) {
				getSolutionFile();
				packageInfo = new WP8PackageInfo(rootDir);
			}
			
			buildInfoList = new ArrayList<BuildInfo>(); // initialization
			buildDir = new File(baseDir.getPath() + BUILD_DIRECTORY);
			if (!buildDir.exists()) {
				buildDir.mkdirs();
				getLog().info("Build directory created..." + buildDir.getPath());
			}
			buildInfoFile = new File(buildDir.getPath() + BUILD_INFO_FILE);
			nextBuildNo = generateNextBuildNo();
			currentDate = Calendar.getInstance().getTime();
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Package Goal");
		getLog().info("mvn windows-phone:package -DenvironmentName=\"Multivalued evnironment names\"" 
					+ " -Dtype=\"Windows Phone platform\"");
		throw new MojoExecutionException("Invalid Usage. Please see the Usage of Package Goal");
	}
	
	private void getSolutionFile() throws MojoExecutionException {
		try {
			// Get .sln file from the source folder
			File sourceDir = new File(baseDir.getPath() + sourceDirectory);
			solutionFile = sourceDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".sln");
				}
			});
			
			projectRootFolder = solutionFile[0].getName().substring(0, solutionFile[0].getName().length() - 4);
			
			// Get the source/<ProjectRoot> folder
			rootDir = new File(baseDir.getPath() + sourceDirectory + WINDOWS_STR_BACKSLASH + projectRootFolder);
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private void generateWP7Package() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Building project ...");
			
			// Get .sln file from the source folder
			File solutionDir = new File(baseDir.getPath() + sourceDirectory);
			solutionFile = solutionDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".sln");
				}
			});
						
			// MSBuild MyApp.sln /t:Rebuild /p:Configuration=Release
			StringBuilder sb = new StringBuilder();
			sb.append(WP_MSBUILD_PATH);
			sb.append(STR_SPACE);
			sb.append(baseDir.getPath() + sourceDirectory);
			sb.append(WINDOWS_STR_BACKSLASH);
			sb.append(solutionFile[0].getName());
			sb.append(STR_SPACE);
			sb.append(WP_STR_TARGET);
			sb.append(WP_STR_COLON);
			sb.append("Rebuild");
			sb.append(STR_SPACE);
			sb.append(WP_STR_PROPERTY);
			sb.append(WP_STR_COLON);
			sb.append("Configuration=Release");
			System.out.println("Build Command: " + sb.toString());
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir.getPath() + sourceDirectory);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} finally {
			Utility.closeStream(in);
		}
	}
	
	private void generateWP8Package() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Building project ...");
			
			// MSBuild MyApp.sln /t:Rebuild /p:Configuration=Release
			StringBuilder sb = new StringBuilder();
			sb.append(WP_MSBUILD_PATH);
			sb.append(STR_SPACE);
			sb.append(baseDir.getPath() + sourceDirectory);
			sb.append(WINDOWS_STR_BACKSLASH);
			sb.append(solutionFile[0].getName());
			sb.append(STR_SPACE);
			sb.append(WP_STR_TARGET);
			sb.append(WP_STR_COLON);
			sb.append("Rebuild");
			sb.append(STR_SPACE);
			sb.append(WP_STR_PROPERTY);
			sb.append(WP_STR_COLON);
			sb.append(WP_STR_CONFIGURATION + "=" + configuration);
			sb.append(WP_STR_SEMICOLON);
			sb.append(WP_STR_PLATFORM + "=" + WP_STR_DOUBLEQUOTES + platform + WP_STR_DOUBLEQUOTES);
			System.out.println(sb.toString());
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir.getPath() + sourceDirectory);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} finally {
			Utility.closeStream(in);
		}
	}

	
	private boolean build() throws MojoExecutionException {
		boolean isBuildSuccess = true;
		try {
			getLog().info("Building the project...");
			createPackage();
		} catch (Exception e) {
			isBuildSuccess = false;
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
		return isBuildSuccess;
	}

	private void createPackage() throws MojoExecutionException {
		try {
			if (buildName != null) {
				zipName = buildName + ".zip";
			} else {
				if (buildNumber != null) {
					zipName = projectName + STR_UNDERSCORE + buildNumber + STR_UNDERSCORE + getTimeStampForBuildName(currentDate)
							+ ".zip";
				} else {
					zipName = projectName + STR_UNDERSCORE + nextBuildNo + STR_UNDERSCORE + getTimeStampForBuildName(currentDate)
							+ ".zip";
				}
			}
			String zipFilePath = buildDir.getPath() + File.separator + zipName;
			
			if(type.equalsIgnoreCase("wp8")) {
				String packageVersion = packageInfo.getPackageVersion();
				System.out.println("packageVersion = " + packageVersion);
				String tempFilePath = baseDir + sourceDirectory + WINDOWS_STR_BACKSLASH + projectRootFolder + WP_APP_PACKAGE + WINDOWS_STR_BACKSLASH + projectRootFolder + STR_UNDERSCORE + packageVersion + STR_UNDERSCORE + (platform.equalsIgnoreCase("any cpu")?"AnyCPU":platform) + (configuration.equalsIgnoreCase("debug")? STR_UNDERSCORE + configuration : "") + WP_TEST;
				System.out.println("tempFilePath = " + tempFilePath);
				tempDir = new File(tempFilePath);
			} else if(type.equalsIgnoreCase("wp7")) {
				String packageFolder = solutionFile[0].getName().substring(0, solutionFile[0].getName().length() - 4);
				tempDir = new File(baseDir + sourceDirectory + WINDOWS_STR_BACKSLASH  + packageFolder + WP7_BIN_FOLDER + WP7_RELEASE_FOLDER);	
			}
			
			ArchiveUtil.createArchive(tempDir.getPath(), zipFilePath, ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}

	private void writeBuildInfo(boolean isBuildSuccess) throws MojoExecutionException {
		try {
			if (buildNumber != null) {
				buildNo = Integer.parseInt(buildNumber);
			}

			PluginUtils pu = new PluginUtils();
			BuildInfo buildInfo = new BuildInfo();
			List<String> envList = pu.csvToList(environmentName);
			if (buildNo > 0) {
				buildInfo.setBuildNo(buildNo);
			} else {
				buildInfo.setBuildNo(nextBuildNo);
			}
			buildInfo.setTimeStamp(getTimeStampForDisplay(currentDate));
			if (isBuildSuccess) {
				buildInfo.setBuildStatus(SUCCESS);
			} else {
				buildInfo.setBuildStatus(FAILURE);
			}
			buildInfo.setBuildName(zipName);
			buildInfo.setEnvironments(envList);
			buildInfoList.add(buildInfo);
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(buildInfoFile);
			gson.toJson(buildInfoList, writer);

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

	private int generateNextBuildNo() throws IOException {
		int nextBuildNo = 1;
		if (!buildInfoFile.exists()) {
			return nextBuildNo;
		}

		BufferedReader read = new BufferedReader(new FileReader(buildInfoFile));
		String content = read.readLine();
		Gson gson = new Gson();
		Type listType = new TypeToken<List<BuildInfo>>() {
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
		// sort to the array to find the max build no
		Arrays.sort(buildArray);

		// increment 1 to the max in the build list
		nextBuildNo = buildArray[buildArray.length - 1] + 1;
		return nextBuildNo;
	}

	private void cleanUp() throws MojoExecutionException {
		try {
			FileUtils.deleteDirectory(tempDir);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}

	}
}
