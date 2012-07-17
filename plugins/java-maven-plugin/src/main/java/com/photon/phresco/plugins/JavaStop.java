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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PluginConstants;

/**
 * Goal which builds the Java WebApp
 * 
 * @goal stop
 * 
 */

public class JavaStop extends AbstractMojo implements PluginConstants {

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

	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			stopServer();
			getLog().info("Server stopped successfully");
		} catch (MojoExecutionException e) {
			getLog().error("Failed to stop server "+ e);
			throw e;
		}
	}

	private void stopServer() throws MojoExecutionException {
		String portNo = findPortNumber();
		if (System.getProperty(OS_NAME).startsWith(WINDOWS_PLATFORM)) {
			stopJavaServerInWindows("netstat -ao | findstr " + portNo + " | findstr LISTENING");
		} else if (System.getProperty(OS_NAME).startsWith("Mac")) {
			stopJavaServer("lsof -i tcp:" + portNo + " | awk '{print $2}'");
		} else {
			stopJavaServer("fuser " + portNo + "/tcp " + "|" + "awk '{print $1}'");
		}
	}

	private void stopJavaServerInWindows(String command) throws MojoExecutionException {
		try {
			String pid = "";
			BufferedReader in = null;
			Commandline cl = new Commandline(command);
			Process process = cl.execute(); 
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				pid = line.substring(line.length() - 4, line.length());
			}
			Runtime.getRuntime().exec("cmd /X /C taskkill /F /PID " + pid);			
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

	private void stopJavaServer(String command) throws MojoExecutionException {
		try {
			String pid = "";
			BufferedReader in = null;
			Commandline cl = new Commandline(command);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int count = 1;
			while ((line = in.readLine()) != null) {
				if (count == 2) {
					pid = line.trim();
				}
				count++;
			}
			Runtime.getRuntime().exec(JAVA_UNIX_PROCESS_KILL_CMD + pid);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

	public String findPortNumber() throws MojoExecutionException {
		String serverPort = "";
		try {
			ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
			List<SettingsInfo> settingsInfos = projAdmin.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER, baseDir
					.getName(), environmentName);
			for (SettingsInfo settingsInfo : settingsInfos) {
				serverPort = settingsInfo.getPropertyInfo(Constants.SERVER_PORT).getValue();
				break;
			}
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage());
		}
		return serverPort;
	}
}
