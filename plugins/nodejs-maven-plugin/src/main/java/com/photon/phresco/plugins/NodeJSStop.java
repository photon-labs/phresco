/*
 * ###
 * nodejs-maven-plugin Maven Mojo
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import com.photon.phresco.plugin.commons.PluginConstants;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;


/**
 * Goal which builds the Java WebApp
 * 
 * @goal stop
 * 
 */

public class NodeJSStop extends AbstractMojo implements PluginConstants {

	/**
	 * @parameter expression="${project.basedir}/source" required="true"
	 * @readonly
	 */
	protected File sourceDirectory;

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

	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			stopNodeJS();
			getLog().info("Server stopped successfully");
		} catch (MojoExecutionException e) {
			getLog().error("Failed to stop server "+ e);
			throw e;
		}
	}

	private void stopNodeJS() throws MojoExecutionException {
		try {
			if (System.getProperty(OS_NAME).startsWith(WINDOWS_PLATFORM)) {
				Runtime.getRuntime().exec("cmd /X /C taskkill /F /IM node.exe");
			} else if (System.getProperty(OS_NAME).startsWith("Mac")) {
				Runtime.getRuntime().exec("killall node");
			} else {
				Runtime.getRuntime().exec("pkill node");
			}
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
}
