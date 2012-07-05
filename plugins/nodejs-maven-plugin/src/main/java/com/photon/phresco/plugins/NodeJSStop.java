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

		stopNodeJS();

	}

	private void stopNodeJS() throws MojoExecutionException {
		ByteArrayInputStream is = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		FileWriter fileWriter = null;
		String result = "";
		if (System.getProperty(NODE_OS_NAME).startsWith(NODE_WINDOWS)) {
			stopNodeJSInWindows();
		} else if (System.getProperty(NODE_OS_NAME).startsWith("Mac")) {
			stopNodeJSInMac();
		} else {
			stopNodeJSInUnix();
		}
		result = "Server Stopped Successfully...";
		is = new ByteArrayInputStream(result.getBytes());
		isr = new InputStreamReader(is);
		reader = new BufferedReader(isr);
		try {
			fileWriter = new FileWriter(baseDir.getPath() + NODE_LOG_FILE_DIRECTORY + NODE_LOG_FILE, false);
			LogWriter writer = new LogWriter();
			writer.writeLog(reader, fileWriter);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (Exception e) {
				throw new MojoExecutionException(e.getMessage());
			}
		}
	}

	private void stopNodeJSInWindows() throws MojoExecutionException {
		try {
			Process p = Runtime.getRuntime().exec("cmd /X /C taskkill /F /IM node.exe");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
	
	private void stopNodeJSInMac() throws MojoExecutionException {
		try {
			Commandline cl = new Commandline("killall");
			String[] args1 = { "node" };
			cl.addArguments(args1);
			cl.setWorkingDirectory(sourceDirectory);
			Process execute;
			execute = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(execute.getErrorStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

	private void stopNodeJSInUnix() throws MojoExecutionException {
		try {
			Commandline cl = new Commandline(NODE_UNIX_PROCESS_KILL_CMD);
			String[] args1 = { "node" };
			cl.addArguments(args1);
			cl.setWorkingDirectory(sourceDirectory);
			Process execute;
			execute = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(execute.getErrorStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}

		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
	
}
