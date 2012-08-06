/*
 * ###
 * java-maven-plugin Maven Mojo
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

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.PluginUtils;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

/**
 * Goal which builds the Java WebApp
 * 
 * @goal package
 * 
 */

public class JavaPackage extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${moduleName}" required="true"
	 */

	protected String moduleName;

	/**
	 * @parameter expression="${buildName}" required="true"
	 */
	protected String buildName;

	/**
	 * @parameter expression="${buildNumber}" required="true"
	 */
	protected String buildNumber;

	protected int buildNo;

	/**
	 * @parameter expression="${mainClassName}" required="true"
	 */
	protected String mainClassName;
	
	/**
	 * @parameter expression="${jarName}" required="true"
	 */
	protected String jarName;
	

	private File targetDir;
	private File buildDir;
	private File buildInfoFile;
	private File tempDir;
	private List<BuildInfo> buildInfoList;
	private int nextBuildNo;
	private String zipName;
	private Date currentDate;
	private String context;

	public void execute() throws MojoExecutionException, MojoFailureException {
		init();	
		if (environmentName != null) {
			updateFinalName();
			configure();
		}
		executeMvnPackage();
		boolean buildStatus = build();
		writeBuildInfo(buildStatus);
		cleanUp();
	}

	private void init() throws MojoExecutionException {
		try {
			buildInfoList = new ArrayList<BuildInfo>(); // initialization
			buildDir = new File(baseDir.getPath() + PluginConstants.BUILD_DIRECTORY);
			targetDir = new File(baseDir + File.separator + PluginConstants.TARGET_DIRECTORY);
			baseDir = getProjectRoot(baseDir);
			if (!buildDir.exists()) {
				buildDir.mkdirs();
				getLog().info("Build directory created..." + buildDir.getPath());
			}
			buildInfoFile = new File(baseDir.getPath() + PluginConstants.BUILD_DIRECTORY + BUILD_INFO_FILE);
			File buildInfoDir = new File(baseDir.getPath() + PluginConstants.BUILD_DIRECTORY);
			if (!buildInfoDir.exists()) {
				buildInfoDir.mkdirs();
				getLog().info("Build directory created..." + buildDir.getPath());
			}
			nextBuildNo = generateNextBuildNo();
			currentDate = Calendar.getInstance().getTime();
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private File getProjectRoot(File childDir) {
		File[] listFiles = childDir.listFiles(new PhrescoDirFilter());
		if (listFiles != null && listFiles.length > 0) {
			return childDir;
		}
		if (childDir.getParentFile() != null) {
			return getProjectRoot(childDir.getParentFile());
		}
		return null;
	}

	public class PhrescoDirFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			return name.equals(DOT_PHRESCO_FOLDER);
		}
	}

	private void updateFinalName() throws MojoExecutionException {
		try {
			ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
			Project currentProject = projAdmin.getProjectByWorkspace(baseDir);
			String techId = currentProject.getProjectInfo().getTechnology().getId();
			if (!techId.equals(TechnologyTypes.JAVA_STANDALONE)) {
				String envName = environmentName;
				if (environmentName.indexOf(',') > -1) { // multi-value
					envName = projAdmin.getDefaultEnvName(baseDir.getName());
				}
				List<SettingsInfo> settingsInfos = projAdmin.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER,
						currentProject.getProjectInfo().getCode(), envName);
				for (SettingsInfo settingsInfo : settingsInfos) {
					context = settingsInfo.getPropertyInfo(Constants.SERVER_CONTEXT).getValue();
					break;
				}
			} else {
				context = jarName;
				updatemainClassName();
			}
			if (StringUtils.isEmpty(context)) {
				return;
			}
			File pom = project.getFile();
			PomProcessor pomprocessor = new PomProcessor(pom);
			pomprocessor.setFinalName(context);
			pomprocessor.save();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private void updatemainClassName() throws MojoExecutionException {
		try {
			if (StringUtils.isEmpty(mainClassName)) {
				return;
			}
			File pom = project.getFile();
			List<Element> configList = new ArrayList<Element>();
			PomProcessor pomprocessor = new PomProcessor(pom);
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element archive = doc.createElement(JAVA_POM_ARCHIVE);
			Element manifest = doc.createElement(JAVA_POM_MANIFEST);
			Element addClasspath = doc.createElement(JAVA_POM_ADD_PATH);
			addClasspath.setTextContent("true");
			manifest.appendChild(addClasspath);
			Element mainClass = doc.createElement(JAVA_POM_MAINCLASS);
			mainClass.setTextContent(mainClassName);
			manifest.appendChild(addClasspath);
			manifest.appendChild(mainClass);
			archive.appendChild(manifest);
			configList.add(archive);

			pomprocessor.addConfiguration(JAR_PLUGIN_GROUPID, JAR_PLUGIN_ARTIFACT_ID, configList, false);
			pomprocessor.save();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private void executeMvnPackage() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Packaging the project...");
			StringBuilder sb = new StringBuilder();
			sb.append(MVN_CMD);
			sb.append(STR_SPACE);
			sb.append(MVN_PHASE_CLEAN);
			sb.append(STR_SPACE);
			sb.append(MVN_PHASE_PACKAGE);
			sb.append(STR_SPACE);
			sb.append(SKIP_TESTS);

			Commandline cl = new Commandline(sb.toString());
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.out.println(line); //do not use getLog() here as this line already contains the log type.
				}
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

	private void configure() throws MojoExecutionException {
		getLog().info("Configuring the project....");
		adaptDbConfig();
		adaptSourceConfig();
	}

	private void adaptSourceConfig() throws MojoExecutionException {
		String basedir = baseDir.getName();
		String modulePath = "";
		if (moduleName != null) {
			modulePath = File.separatorChar + moduleName;
		}
		File sourceConfigXML = new File(baseDir + modulePath + JAVA_WEBAPP_CONFIG_FILE);
		File parentFile = sourceConfigXML.getParentFile();
		if (parentFile.exists()) {
			PluginUtils pu = new PluginUtils();
			pu.executeUtil(environmentName, basedir, sourceConfigXML);
		}
	}

	private void adaptDbConfig() {
		String basedir = baseDir.getName();
		String modulePath = "";
		if (moduleName != null) {
			modulePath = File.separatorChar + moduleName;
		}
		File sourceConfigXML = new File(baseDir + modulePath + JAVA_CONFIG_FILE);
		File parentFile = sourceConfigXML.getParentFile();
		if (parentFile.exists()) {
			PluginUtils pu = new PluginUtils();
			pu.executeUtil(environmentName, basedir, sourceConfigXML);
		}
	}

	private void createPackage() throws MojoExecutionException, IOException {
		try {
			if (buildName != null) {
				zipName = buildName + ".zip";
			} else {
				if (buildNumber != null) {
					zipName = PROJECT_CODE + buildNumber + STR_UNDERSCORE + getTimeStampForBuildName(currentDate)
							+ ".zip";
				} else {
					zipName = PROJECT_CODE + nextBuildNo + STR_UNDERSCORE + getTimeStampForBuildName(currentDate)
							+ ".zip";
				}
			}
			String zipFilePath = buildDir.getPath() + File.separator + zipName;
			String zipNameWithoutExt = zipName.substring(0, zipName.lastIndexOf('.'));
			ProjectAdministrator projectAdministrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project currentProject = projectAdministrator.getProjectByWorkspace(baseDir);
			String techId = currentProject.getProjectInfo().getTechnology().getId();
			if (techId.equals(TechnologyTypes.JAVA_STANDALONE)) {

				copyJarToPackage(zipNameWithoutExt);
			} else {
				copyWarToPackage(zipNameWithoutExt, context);
			}
			ArchiveUtil.createArchive(tempDir.getPath(), zipFilePath, ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}

	private void copyJarToPackage(String zipNameWithoutExt) throws IOException {
		String[] list = targetDir.list(new JarFileNameFilter());
		if (list.length > 0) {
			File jarFile = new File(targetDir.getPath() + File.separator + list[0]);
			tempDir = new File(buildDir.getPath() + File.separator + zipNameWithoutExt);
			tempDir.mkdir();
			FileUtils.copyFileToDirectory(jarFile, tempDir);
		}
	}

	private void copyWarToPackage(String zipNameWithoutExt, String context) throws MojoExecutionException {
		try {
			String[] list = targetDir.list(new WarFileNameFilter());
			if (list.length > 0) {
				File warFile = new File(targetDir.getPath() + File.separator + list[0]);
				tempDir = new File(buildDir.getPath() + File.separator + zipNameWithoutExt);
				tempDir.mkdir();
				File contextWarFile = new File(targetDir.getPath() + File.separator + context + ".war");
				warFile.renameTo(contextWarFile);
				FileUtils.copyFileToDirectory(contextWarFile, tempDir);
			} else {
				throw new MojoExecutionException("Compilation Failure...");
			}
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
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
			buildInfo.setContext(context);
			buildInfo.setEnvironments(envList);
			if (StringUtils.isNotEmpty(moduleName)) {
				buildInfo.setModuleName(moduleName);
			}
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

class WarFileNameFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return name.endsWith(".war");
	}
}

class JarFileNameFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return name.endsWith(".jar");
	}
}
