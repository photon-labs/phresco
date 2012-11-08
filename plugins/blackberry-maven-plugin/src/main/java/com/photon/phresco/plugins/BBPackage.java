/*
 * ###
 * blackberry-maven-plugin Maven Mojo
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.PluginUtils;
import com.photon.phresco.util.Utility;

/**
 * Goal which generated the installable file (.cod) for BlackBerry
 * 
 * @goal package
 * 
 */

public class BBPackage extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${keyPass}" required="false"
	 */
	protected String keyPass;
	
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
	private String tempZipName,tempZipFilePath, zipName, zipFilePath;
	private Date currentDate;
	private String sourceDirectory = "\\source";
	private String phrescoDirectory = "\\.phresco";
	private File rootDir;
	// private ArrayList<String> configUrls;
	private HashMap<String, String> configUrls;
	private String jsonString;

	public void execute() throws MojoExecutionException, MojoFailureException {
		init();
		convertXMLToJSON();
		writeJSONtoJavaScript();
		generateSourceArchive();	// generate .zip file of source folder contents
		boolean buildStatus = build();
		generateArchive();
		writeBuildInfo(buildStatus);
		cleanUp();
	}

	private void init() throws MojoExecutionException {
		try {
			if (StringUtils.isEmpty(environmentName)) {
				callUsage();
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

	

	private void convertXMLToJSON() throws MojoExecutionException {
		try {
			File configFile = new File(baseDir.getPath() + phrescoDirectory
					+ File.separator + "phresco-env-config.xml");
			InputStream is = new FileInputStream(configFile);
			String xml = IOUtils.toString(is);

			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON json = xmlSerializer.read(xml);
			jsonString = json.toString();
			//getLog().info("Converted JSON = " + jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void writeJSONtoJavaScript() throws MojoExecutionException {
		BufferedWriter out = null;
		try {
			File configJSFile = new File(baseDir.getPath() + sourceDirectory
					+ File.separator + "js" + File.separator + "config.js");
			if (configJSFile.exists()) {
				configJSFile.delete();
			}
			configJSFile.createNewFile();

			out = new BufferedWriter(
					new FileWriter(configJSFile));
			//getLog().info("Config JSON = " + jsonString);
			out.write("var ENV_CONFIG_JSON  = '" + jsonString + "';\n");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} 
	}

	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Package Goal");
		getLog().info(
				"mvn blackberry:package -DenvironmentName=\"Multivalued evnironment names\""
						+ " -Doutput=\"Path to output folder\"");
		throw new MojoExecutionException(
				"Invalid Usage. Please see the Usage of Package Goal");
	}

	
	private void generateSourceArchive() throws MojoExecutionException {
		try {
			tempZipName = "temp.zip";
			tempDir = new File(baseDir + sourceDirectory);
			//getLog().info("generateSourceArchive: == tempDir == " + tempDir.getPath());
			
			tempZipFilePath = tempDir.getPath() + File.separator + tempZipName;
			//getLog().info("zipFilePath == " + tempZipFilePath);

			ArchiveUtil.createArchive(tempDir.getPath(), tempZipFilePath,
					ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}
	
	private void generateArchive() throws MojoExecutionException {
		try {
			if (buildName != null) {
				zipName = buildName + ".zip";
			} else {
				if (buildNumber != null) {
					zipName = projectName + STR_UNDERSCORE + buildNumber
							+ STR_UNDERSCORE
							+ getTimeStampForBuildName(currentDate) + ".zip";
				} else {
					zipName = projectName + STR_UNDERSCORE + nextBuildNo
							+ STR_UNDERSCORE
							+ getTimeStampForBuildName(currentDate) + ".zip";
				}
			}
			zipFilePath = buildDir.getPath() + File.separator + zipName;
			//getLog().info("zipFilePath == " + zipFilePath);
			tempDir = new File(baseDir + sourceDirectory + File.separator + tempZipName.substring(0, tempZipName.length() - 4));

			//getLog().info("generateArchive: == tempDir == " + tempDir.getPath());

			ArchiveUtil.createArchive(tempDir.getPath(), zipFilePath,
					ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}

	private boolean build() throws MojoExecutionException {
		boolean isBuildSuccess = true;
		try {
			createPackage();
		} catch (Exception e) {
			isBuildSuccess = false;
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
		return isBuildSuccess;
	}

	private void createPackage() throws MojoExecutionException,
			PhrescoException {
		BufferedReader in = null;
		try {
			getLog().info("Building project ...");

			// bbwp <filename>.zip -g photon123 -o <path\to\output\folder>
			StringBuilder sb = new StringBuilder();
			sb.append(BB_BBWP_HOME);
			sb.append(STR_SPACE);
			sb.append(tempZipName);
			if (!(keyPass == null && keyPass.isEmpty())) {
				sb.append(STR_SPACE);
				sb.append("-g");
				sb.append(STR_SPACE);
				sb.append(keyPass); 
			}
			sb.append(STR_SPACE);
			sb.append("-o");
			sb.append(STR_SPACE);
			sb.append(tempZipName.substring(0, tempZipName.length() - 4));

			//getLog().info("Build command: " + sb.toString());
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir.getPath() + sourceDirectory);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
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

	private void writeBuildInfo(boolean isBuildSuccess)
			throws MojoExecutionException {
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
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MMM/yyyy HH:mm:ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}

	private String getTimeStampForBuildName(Date currentDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd-MMM-yyyy-HH-mm-ss");
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
			//getLog().info("Temp dir in cleanup = " + tempDir.getPath());
			FileUtils.deleteDirectory(tempDir);
			File f =new File(baseDir + sourceDirectory + File.separator + tempZipName);
			f.delete();
			
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}

	}
}
