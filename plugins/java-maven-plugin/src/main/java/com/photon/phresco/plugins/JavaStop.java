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

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.plugin.commons.PluginConstants;
import com.photon.phresco.util.Constants;

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
		stopServer();
	}

	private void stopServer() throws MojoExecutionException {
		String serverPort = "";
		String ServerShutdownPort = "";
		try {
			ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
			List<SettingsInfo> settingsInfos = projAdmin.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER, baseDir.getName(), environmentName);
			for (SettingsInfo settingsInfo : settingsInfos) {
				serverPort = settingsInfo.getPropertyInfo(Constants.SERVER_PORT).getValue();
				Integer stport = Integer.valueOf(serverPort) + 1;
				ServerShutdownPort = Integer.toString(stport);
				break;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(MVN_CMD);
			sb.append(STR_SPACE);
			sb.append(T7_STOP_GOAL);
			sb.append(STR_SPACE);
			sb.append(SERVER_SHUTDOWN_PORT);
			sb.append(ServerShutdownPort);
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir);
			Process process = cl.execute();
		}catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getMessage());
		} 
	}
}
