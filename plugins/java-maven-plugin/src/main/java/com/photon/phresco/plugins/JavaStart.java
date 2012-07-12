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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import com.photon.phresco.plugin.commons.PluginConstants;
import com.photon.phresco.plugin.commons.PluginUtils;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.phresco.pom.util.PomProcessor;

/**
 * Goal which builds the Java WebApp
 * 
 * @goal start
 * 
 */
public class JavaStart extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${importSql}" required="true"
	 */
	protected boolean importSql;
	
	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;

	private String shutdownport;
	private String serverport;
	private String context;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (environmentName != null) {
			updateFinalName();
			configure();
			storeEnvName(environmentName);
		}
		createDb();
		executePhase();
	}

	private void updateFinalName() throws MojoExecutionException {
		try {
				ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
				String envName = environmentName;
				if (environmentName.indexOf(',') > -1) { // multi-value
					envName = projAdmin.getDefaultEnvName(baseDir.getName());
				}
				List<SettingsInfo> settingsInfos = projAdmin.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER,
						baseDir.getName(), envName);
				for (SettingsInfo settingsInfo : settingsInfos) {
					context = settingsInfo.getPropertyInfo(Constants.SERVER_CONTEXT).getValue();
					break;
				}
			File pom = project.getFile();
			PomProcessor pomprocessor = new PomProcessor(pom);
			pomprocessor.setFinalName(context);
			pomprocessor.save();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void configure() throws MojoExecutionException {
		try {
			getLog().info("Configuring the project....");
			List<SettingsInfo> settingsInfo  = getSettingsInfo(Constants.SETTINGS_TEMPLATE_SERVER);
			for (SettingsInfo serverDetails : settingsInfo) {
				PropertyInfo port = serverDetails.getPropertyInfo(Constants.SERVER_PORT);
				serverport = port.getValue();
				Integer stport = Integer.valueOf(serverport) + 1;
				shutdownport = Integer.toString(stport);
				break;
			}
			adaptSourceConfig();
			adaptDbConfig();
		} catch (PhrescoException e) {
			getLog().error(e.getErrorMessage());
			throw new MojoExecutionException(e.getErrorMessage(), e);
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
	
	private void createDb() throws MojoExecutionException {
		PluginUtils util = new PluginUtils();
		try {
			if (importSql) {
				List<SettingsInfo> settingsInfos = getSettingsInfo(Constants.SETTINGS_TEMPLATE_DB);
				for (SettingsInfo databaseDetails : settingsInfos) {
					String databaseType = databaseDetails.getPropertyInfo(Constants.DB_TYPE).getValue();
					util.getSqlFilePath(databaseDetails,baseDir, databaseType);
				}
			}
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void adaptDbConfig() throws MojoExecutionException {
		File dbConfigFile = new File(baseDir + JAVA_CONFIG_FILE);
		File parentFile = dbConfigFile.getParentFile();
		String basedir = baseDir.getName();
		if (parentFile.exists()) {
			PluginUtils pu = new PluginUtils();
			pu.executeUtil(environmentName, basedir, dbConfigFile);
		}
	}
	
	private void adaptSourceConfig() throws MojoExecutionException {
		File wsConfigFile = new File(baseDir.getPath() + JAVA_WEBAPP_CONFIG_FILE);
		File parentFile = wsConfigFile.getParentFile();
		String basedir = baseDir.getName();
		if (parentFile.exists()) {
			PluginUtils pu = new PluginUtils();
			pu.executeUtil(environmentName, basedir, wsConfigFile);
		}
	}

	private void executePhase() throws MojoExecutionException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(MVN_CMD);
			sb.append(STR_SPACE);
			sb.append(MVN_PHASE_CLEAN);
			sb.append(STR_SPACE);
			sb.append(MVN_PHASE_INSTALL);
			sb.append(STR_SPACE);
			sb.append(T7_START_GOAL);
			sb.append(STR_SPACE);
			sb.append(SERVER_PORT);
			sb.append(serverport);
			sb.append(STR_SPACE);
			sb.append(SERVER_SHUTDOWN_PORT);
			sb.append(shutdownport);
			sb.append(STR_SPACE);
			sb.append(SKIP_TESTS);
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir);
			Process process = cl.execute();
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private List<SettingsInfo> getSettingsInfo(String configType) throws PhrescoException {
		ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
		return projAdmin.getSettingsInfos(configType, baseDir.getName(), environmentName);
	}
}