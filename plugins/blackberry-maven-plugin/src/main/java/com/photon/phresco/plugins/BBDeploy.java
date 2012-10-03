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
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.PluginUtils;
import com.photon.phresco.util.Utility;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
/**
 * Goal which deploys the Java WebApp to a server
 * 
 * @goal deploy
 * 
 */
public class BBDeploy extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${buildNumber}" required="true"
	 */
	protected String buildNumber;

	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;
	
	private File buildFile;
	private File tempDir;
	private File buildDir;
	private BuildInfo buildInfo;
	
	public void execute() throws MojoExecutionException {
		init();
		extractBuild();
		renameFiles();
		deployBuild();
	}

	private void init() throws MojoExecutionException {
		try {

			if (StringUtils.isEmpty(buildNumber) || StringUtils.isEmpty(environmentName)) {
				callUsage();
			}
			
			PluginUtils pu = new PluginUtils();
			buildInfo = pu.getBuildInfo(Integer.parseInt(buildNumber));
			
			buildDir = new File(baseDir.getPath() + BUILD_DIRECTORY);
			buildFile = new File(buildDir.getPath() + File.separator + buildInfo.getBuildName());
			tempDir = new File(buildDir.getPath() + File.separator + buildFile.getName().substring(0, buildFile.getName().length() - 4));
			tempDir.mkdirs();
			
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void extractBuild() throws MojoExecutionException {
		try {
			ArchiveUtil.extractArchive(buildFile.getPath(), tempDir.getPath(), ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		} 
	}
	
	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Deploy Goal");
		getLog().info(
				"mvn blackberry:deploy -DbuildNumber=\"Build Number\""
						+ " -DenvironmentName=\"Multivalued evnironment names\"");
		throw new MojoExecutionException("Invalid Usage. Please see the Usage of Deploy Goal");
	}
	
	

	private void deployBuild() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Deploying project ...");
			tempDir = new File(tempDir.getPath() + File.separator + BB_STANDARD_INSTALL);
			
			// Get .cod file from the extracted contents
			File[] codFile = tempDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".cod");
				}
			});
			
			// javaloader -usb load <FileName>.cod
			StringBuilder sb = new StringBuilder();
			sb.append(BB_JAVA_LOADER_HOME);
			sb.append(BB_USB);
			sb.append(STR_SPACE);
			sb.append(BB_LOAD);
			sb.append(STR_SPACE);
			sb.append(codFile[0].getName());
			
			getLog().info("Deploy command: " + sb.toString());
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(tempDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}  finally {
			Utility.closeStream(in);
		}
	}
	
	private void renameFiles() throws MojoExecutionException {
		try {
			File stdInstallDir = new File(tempDir.getPath() + File.separator + BB_STANDARD_INSTALL);
			String bName = buildFile.getName().substring(0, buildFile.getName().length() - 4);
			renameFileNames(stdInstallDir, bName, true);
			
			File OTAInstallDir = new File(tempDir.getPath() + File.separator + BB_OTA_INSTALL);
			renameFileNames(OTAInstallDir, bName, true);
			
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} 
	}
	
	private void renameFileNames(File dir, String bName, boolean replaceFlag) throws MojoExecutionException {
		try {
			// Rename all the files in working dir with buildname
			File[] fileList = dir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.toLowerCase().startsWith("temp");
				}
			});
			
			for (int i = 0; i < fileList.length; i++) {
			    File oldFile = fileList[i];
			    String replace = fileList[i].getName().replace(fileList[i].getName().substring(0, 4), bName);
				File newFile = new File(dir.getAbsolutePath() + File.separator + replace);
				boolean isFileRenamed = false;
				if (replaceFlag)
					isFileRenamed = oldFile.renameTo(newFile);
			}
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} 
	}
	
	
}
