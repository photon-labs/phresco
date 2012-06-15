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
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.PluginUtils;

/**
 * Goal which builds the Java WebApp
 * 
 * @goal start
 * 
 */

public class NodeJSStart extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${importSql}" required="true"
	 */
	protected boolean importSql;
	
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		configure();
		storeEnvName(environmentName);
		createDb();
		startNodeJS();
	}
	
	private void configure() throws MojoExecutionException {
		getLog().info("Configuring the project....");
		File ConfigFile = new File(baseDir + NODE_CONFIG_FILE);
		String basedir = baseDir.getName();
		PluginUtils pu = new PluginUtils();
		pu.executeUtil(environmentName, basedir, ConfigFile);
	}

	private void createDb() throws MojoExecutionException {
		PluginUtils util = new PluginUtils();
		try {
			if (importSql) {
				ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
				List<SettingsInfo> settingsInfos = projAdmin.getSettingsInfos(Constants.SETTINGS_TEMPLATE_DB, baseDir
						.getName(), environmentName);
				for (SettingsInfo databaseDetails : settingsInfos) {
				util.executeSql(databaseDetails,baseDir, NODE_SQL_DIR, NODE_SQL_FILE);
					
				}
			}
		} catch (PhrescoException e) {
			getLog().info("server startup failed");
			throw new MojoExecutionException(e.getMessage());
		}
	}

	private void startNodeJS() throws MojoExecutionException {
		BufferedReader br = null;
		InputStreamReader isr = null;
		FileWriter fileWriter = null;
		try {
			ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
			String path = "";
			boolean tempConnectionAlive = false;
			String mavenHome = System.getProperty(JAVA_LIB_PATH);
			String[] split = mavenHome.split(";");
			for (int i = 0; i < split.length; i++) {
				path = split[i];
				if (path.indexOf(NODEJS_DIR_NAME) != -1) {
					break;
				}
			}

			ProcessBuilder pb = new ProcessBuilder(path + NODE_EXE_PATH);
			File workingDirectory = new File(baseDir + FORWARD_SLASH
					+ PROJECT_FOLDER);
			pb.redirectErrorStream(true);
			List<String> commands = pb.command();
			commands.add(NODE_SERVER_FILE);
			commands.add(environmentName);
			pb.directory(workingDirectory);
			Process proc = pb.start();
			List<SettingsInfo> settingsInfos = projAdmin.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER, baseDir
					.getName(), environmentName);
			for (SettingsInfo serverDetails : settingsInfos) {
				String serverhost = serverDetails.getPropertyInfo(Constants.SERVER_HOST).getValue();
				int serverport = Integer.parseInt(serverDetails.getPropertyInfo(Constants.SERVER_PORT).getValue());
				String serverProtocol = serverDetails.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
				 tempConnectionAlive =isConnectionAlive(serverProtocol, serverhost, serverport);
			}
			if(!tempConnectionAlive) {
				getLog().info("server startup failed");
			} else {
				getLog().info("server started");
			}
			File file = new File(baseDir.getPath() + NODE_LOG_FILE_DIRECTORY);
			if (!file.exists()) {
				file.mkdirs();
			}

			isr = new InputStreamReader(proc.getInputStream());
			br = new BufferedReader(isr);
			fileWriter = new FileWriter(baseDir.getPath()
					+ NODE_LOG_FILE_DIRECTORY + NODE_LOG_FILE, false);
			LogWriter logWriter = new LogWriter();
			logWriter.writeLog(br, fileWriter);
		} catch (Exception e) {
			getLog().info("server startup failed");
			throw new MojoExecutionException(e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e) {
				getLog().info("server startup failed");
				throw new MojoExecutionException(e.getMessage());
			}
		}
	}

	private void storeEnvName(String envName) throws MojoExecutionException {
		FileOutputStream fos = null;
		File file = new File(baseDir.getPath() + File.separator + DOT_PHRESCO_FOLDER + File.separator + NODE_ENV_FILE);
		try {
			fos = new FileOutputStream(file, false);
            fos.write(envName.getBytes());
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				throw new MojoExecutionException(e.getMessage());
			}
		}
	}
	
	public static boolean isConnectionAlive(String protocol, String host, int port) {
		boolean isAlive = true;
		try {
			URL url = new URL(protocol, host, port, "");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			isAlive = false;
		}
		return isAlive;
	}
	
}