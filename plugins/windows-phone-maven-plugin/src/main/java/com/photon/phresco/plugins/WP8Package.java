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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;

import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.commons.BuildInfo;
import com.photon.phresco.plugin.commons.PluginConstants;
import com.photon.phresco.plugin.commons.PluginUtils;
import com.photon.phresco.util.Utility;

/**
 * Goal which generated the installable file for Windows Phone 8
 * 
 * @goal wp8package
 * 
 */

public class WP8Package extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${configuration}" required="true"
	 * Release / Debug
	 */
	protected String configuration;
	
	/**
	 * @parameter expression="${platform}" required="true"
	 * AnyCPU / ARM / x86 / x64
	 */
	protected String platform;

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
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		init();
		executeExe();
		boolean buildStatus = build();
		writeBuildInfo(buildStatus);
		cleanUp();
	}


	private void init() throws MojoExecutionException {
		try {
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

	private void executeExe() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Building project ...");
			
			
			// Get .sln file from the source folder
			File sourceDir = new File(baseDir.getPath() + sourceDirectory);
			solutionFile = sourceDir.listFiles(new FilenameFilter() { 
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
			projectRootFolder = solutionFile[0].getName().substring(0, solutionFile[0].getName().length() - 4);
			String packageVersion = readPackageManifestInfo();
			System.out.println("packageVersion = " + packageVersion);
			String tempFilePath = baseDir + sourceDirectory + WINDOWS_STR_BACKSLASH + projectRootFolder + WP_APP_PACKAGE + WINDOWS_STR_BACKSLASH + projectRootFolder + STR_UNDERSCORE + packageVersion + STR_UNDERSCORE + (platform.equalsIgnoreCase("any cpu")?"AnyCPU":platform) + (configuration.equalsIgnoreCase("debug")? STR_UNDERSCORE + configuration : "") + WP_TEST;
			System.out.println("tempFilePath = " + tempFilePath);
			tempDir = new File(tempFilePath);
			System.out.println("temp Dir name = " + tempDir.getPath());
			ArchiveUtil.createArchive(tempDir.getPath(), zipFilePath, ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}

	private String readPackageManifestInfo() throws MojoExecutionException {
		String version="1.0.0.0";
		try {
			
			// Get Package.appxmanifest file from the source/<ProjectRoot> folder
			File rootDir = new File(baseDir.getPath() + sourceDirectory + WINDOWS_STR_BACKSLASH + projectRootFolder);
			File[] manifestFile = rootDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".appxmanifest");
				}
			});
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setIgnoringComments(true);

			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = null;

			builder = factory.newDocumentBuilder();

			Document doc = null;
System.out.println("Manifest file path = " + rootDir.getPath() + WINDOWS_STR_BACKSLASH + manifestFile[0].getName());
			doc = builder.parse(new InputSource(rootDir.getPath() + WINDOWS_STR_BACKSLASH + manifestFile[0].getName()));

			Element root = doc.getDocumentElement();

			System.out.println("ROOT: " + root.getNodeName());

			NodeList childNodes = root.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i) instanceof Element) {
					String data = ((Element) childNodes.item(i)).getNodeName();
					System.out.println("Node Name: " + data);
					if (data.equalsIgnoreCase("identity")) {
						version = ((Element) childNodes.item(i))
								.getAttribute("Version");
						System.out.println("Version: " + version);
						break;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} 
		return version;
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
