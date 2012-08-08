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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.plugin.commons.PluginConstants;

/**
 * Goal which deploys the Java WebApp to a server
 * 
 * @goal wp8deploy
 * 
 */
public class WP8Deploy extends AbstractMojo implements PluginConstants {

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
	 * Build file name to deploy
	 * 
	 * @parameter expression="${buildName}" required="true"
	 */
	protected String buildName;

	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;
					 

	private File buildFile;
	private File tempDir;
	private File buildDir;
	private File temp;
	
	

	public void execute() throws MojoExecutionException {
		init();
		extractBuild();
		deploy();
	}

	private void init() throws MojoExecutionException {
		try {

			if (StringUtils.isEmpty(buildName) || StringUtils.isEmpty(environmentName)) {
				callUsage();
			}
			buildDir = new File(baseDir.getPath() + BUILD_DIRECTORY);
			buildFile = new File(buildDir.getPath() + File.separator + buildName);
			tempDir = new File(buildDir.getPath() + File.separator + buildFile.getName().substring(0, buildFile.getName().length() - 4));
			tempDir.mkdirs();
			temp = new File(tempDir.getPath());	
			
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Deploy Goal");
		getLog().info(
				"mvn windows-phone:wp8deploy -DbuildName=\"Name of the build\""
						+ " -DenvironmentName=\"Multivalued evnironment names\"");
		throw new MojoExecutionException("Invalid Usage. Please see the Usage of Deploy Goal");
	}

	private void extractBuild() throws MojoExecutionException {
		try {
			ArchiveUtil.extractArchive(buildFile.getPath(), tempDir.getPath(), ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		} 
	}

	private void deploy() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Deploying project ...");
			
			// Get .ps1 file from the extracted contents
			File[] ps1File = tempDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".ps1");
				}
			});
			
			// PowerShell .\Add-AppDevPackage.ps1
			StringBuilder sb = new StringBuilder();
			sb.append(WP_POWERSHELL_PATH);
			sb.append(WP_STR_DOT);
			sb.append(WINDOWS_STR_BACKSLASH);
			sb.append(ps1File[0].getName());
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
		} 
	}

	
}
