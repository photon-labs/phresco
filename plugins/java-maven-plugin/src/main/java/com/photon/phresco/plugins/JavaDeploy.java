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
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import com.photon.phresco.plugin.commons.PluginConstants;
import com.photon.phresco.plugin.commons.PluginUtils;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.Constants;
import com.phresco.pom.util.PomProcessor;

/**
 * Goal which deploys the Java WebApp to a server
 * 
 * @goal deploy
 * 
 */
public class JavaDeploy extends AbstractMojo implements PluginConstants {

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
	
	/**
	 * @parameter expression="${importSql}" required="true"
	 */
	protected boolean importSql;

	private File buildFile;
	private File tempDir;
	private File buildDir;
	private String context;

	public void execute() throws MojoExecutionException {
		init();
		updateFinalName();
		createDb();
		extractBuild();
		deployToServer();
		cleanUp();
	}

	private void init() throws MojoExecutionException {
		try {
			if (StringUtils.isEmpty(buildName) || StringUtils.isEmpty(environmentName)) {
				callUsage();
			}
			buildDir = new File(baseDir.getPath() + PluginConstants.BUILD_DIRECTORY);// build dir
			buildFile = new File(buildDir.getPath() + File.separator + buildName);// filename
			tempDir = new File(buildDir.getPath() + TEMP_DIR);// temp dir
			tempDir.mkdirs();
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Deploy Goal");
		getLog().info(
				"mvn java:deploy -DbuildName=\"Name of the build\""
						+ " -DenvironmentName=\"Multivalued evnironment names\""
						+ " -DimportSql=\"Does the deployment needs to import sql(TRUE/FALSE?)\"");
		throw new MojoExecutionException("Invalid Usage. Please see the Usage of Deploy Goal");
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

	private void extractBuild() throws MojoExecutionException {
		try {
			ArchiveUtil.extractArchive(buildFile.getPath(), tempDir.getPath(), ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}

	private void deployToServer() throws MojoExecutionException {
		try {
			List<SettingsInfo> settingsInfos  = getSettingsInfo(Constants.SETTINGS_TEMPLATE_SERVER);
			for (SettingsInfo serverDetails : settingsInfos) {
				deploy(serverDetails);
			}			
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}
	
	private void deploy(SettingsInfo info) throws MojoExecutionException {
		if (info == null) {
			return;
		}
		String serverhost = info.getPropertyInfo(Constants.SERVER_HOST).getValue();
		String serverport = info.getPropertyInfo(Constants.SERVER_PORT).getValue();
		String serverprotocol = info.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
		String serverusername = info.getPropertyInfo(Constants.SERVER_ADMIN_USERNAME).getValue();
		String serverpassword = info.getPropertyInfo(Constants.SERVER_ADMIN_PASSWORD).getValue();
		String version = info.getPropertyInfo(Constants.SERVER_VERSION).getValue();
		String servertype = info.getPropertyInfo(Constants.SERVER_TYPE).getValue();
		context = info.getPropertyInfo(Constants.SERVER_CONTEXT).getValue();
		renameWar(context);

		// no remote deployment
		if (serverusername.isEmpty() && serverpassword.isEmpty()) {
		//	renameWar(context);
			deploy();
			return;
		}

		// remote deployment
		if (servertype.contains(TYPE_TOMCAT)
				&& ((version.equals("7.0.x")) || (version.equals("7.1.x")) || (version.equals("6.0.x")))) {
			deployToTomcatServer(serverprotocol, serverhost, serverport, serverusername, serverpassword);
		} else if (servertype.contains(TYPE_JBOSS) && (version.equals("7.0.x"))) {
			deployToJbossServer(serverport, serverprotocol, serverhost, serverusername, serverpassword);
		} else if (servertype.contains(TYPE_WEBLOGIC) && (version.equals("12c(12.1.1)"))) {
			deployToWeblogicServer(serverprotocol, serverhost, serverport, serverusername, serverpassword);
		} else {
			// for other servers
			deploy();
		}
	}

	private void renameWar(String context) throws MojoExecutionException {
		String contextName = context + ".war";
		String warFileName = "";
		String[] list = tempDir.list(new JDWarFileNameFilter());
		if (list.length > 0) {
			warFileName = list[0];
			if (!warFileName.contains(contextName)) {
				File oldWar = new File(tempDir.getPath() + "/" + warFileName);
				File newWar = new File(tempDir.getPath() + "/" + contextName);
				oldWar.renameTo(newWar);
			}
		}
	}
	
	
	private void deployToTomcatServer(String serverprotocol, String serverhost, String serverport,
			String serverusername, String serverpassword) throws MojoExecutionException {
		BufferedReader in = null;
		boolean errorParam = false;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(MVN_CMD);
			sb.append(STR_SPACE);
			sb.append(TOMCAT_GOAL);
			sb.append(STR_SPACE);
			sb.append(SERVER_HOST);
			sb.append(serverhost);
			sb.append(STR_SPACE);
			sb.append(SERVER_PORT);
			sb.append(serverport);
			sb.append(STR_SPACE);
			sb.append(SERVER_USERNAME);
			sb.append(serverusername);
			sb.append(STR_SPACE);
			sb.append(SERVER_PASSWORD);
			sb.append(serverpassword);
			sb.append(STR_SPACE);
			sb.append(SKIP_TESTS);
			Commandline cl = new Commandline(sb.toString());
			Process process = cl.execute();
			cl.setWorkingDirectory(baseDir);
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.out.println(line); //do not use getLog() here as this line already contains the log type.
					errorParam = true;
				}
			}
			if (errorParam) {
				throw new MojoExecutionException("Remote Deploy Failed ");
			} else {
				getLog().info(
						" Project is Deploying into " + serverprotocol + "://" + serverhost + ":" + serverport + "/"
								+ context);
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void deployToJbossServer(String serverport, String serverprotocol, String serverhost, String serverusername, String serverpassword)
			throws MojoExecutionException {
		BufferedReader in = null;
		boolean errorParam = false;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(MVN_CMD);
			sb.append(STR_SPACE);
			sb.append(JBOSS_GOAL);
			sb.append(STR_SPACE);
			sb.append(SERVER_HOST);
			sb.append(serverhost);
			sb.append(STR_SPACE);
			sb.append(SERVER_USERNAME);
			sb.append(serverusername);
			sb.append(STR_SPACE);
			sb.append(SERVER_PASSWORD);
			sb.append(serverpassword);
			sb.append(STR_SPACE);
			sb.append(SKIP_TESTS);
			
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.out.println(line); //do not use getLog() here as this line already contains the log type.
					errorParam = true;
				}
			}
			if (errorParam) {
				throw new MojoExecutionException("Remote Deploy Failed ");
			} else {
				getLog().info(
						" Project is Deploying into " + serverprotocol + "://" + serverhost + ":" + serverport + "/"
								+ context);
			}
			} catch (CommandLineException e) {
				throw new MojoExecutionException(e.getMessage(), e);
			} catch (IOException e) {
				throw new MojoExecutionException(e.getMessage(), e);
			}
	}

	private void deployToWeblogicServer(String serverprotocol, String serverhost, String serverport, String serverusername,
			String serverpassword) throws MojoExecutionException {
		BufferedReader in = null;
		boolean errorParam = false;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(MVN_CMD);
			sb.append(STR_SPACE);
			sb.append(WEBLOGIC_GOAL);
			sb.append(STR_SPACE);
			sb.append(SERVER_HOST);
			sb.append(serverhost);
			sb.append(STR_SPACE);
			sb.append(SERVER_PORT);
			sb.append(serverport);
			sb.append(STR_SPACE);
			sb.append(SERVER_USERNAME);
			sb.append(serverusername);
			sb.append(STR_SPACE);
			sb.append(SERVER_PASSWORD);
			sb.append(serverpassword);
			sb.append(STR_SPACE);
			sb.append(SKIP_TESTS);
			
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir);
				Process process = cl.execute();
				in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					if (line.startsWith("[ERROR]")) {
						System.out.println(line); //do not use getLog() here as this line already contains the log type.
						errorParam = true;
					}
				}
				if (errorParam) {
					throw new MojoExecutionException("Remote Deploy Failed ");
				} else {
					getLog().info(
							" Project is Deploying into " + serverprotocol + "://" + serverhost + ":" + serverport + "/"
									+ context);
				}
			} catch (CommandLineException e) {
				throw new MojoExecutionException(e.getMessage(), e);
			} catch (IOException e) {
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
	
	private void deploy() throws MojoExecutionException {
		String deployLocation = "";
		try {
			List<SettingsInfo> settingsInfos = getSettingsInfo(Constants.SETTINGS_TEMPLATE_SERVER);
			for (SettingsInfo serverDetails : settingsInfos) {
				deployLocation = serverDetails.getPropertyInfo(Constants.SERVER_DEPLOY_DIR).getValue();
				break;
			}		
			File deployDir = new File(deployLocation);
				if (!deployDir.exists()) {
				throw new MojoExecutionException(" Deploy Directory" + deployLocation + " Does Not Exists ");
			}
			getLog().info("Project is deploying into " + deployLocation);
			FileUtils.copyDirectoryStructure(tempDir.getAbsoluteFile(), deployDir);
			getLog().info("Project is deployed successfully");
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private List<SettingsInfo> getSettingsInfo(String configType) throws PhrescoException {
		ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
		return projAdmin.getSettingsInfos(configType, getProjectRoot(baseDir).getName(), environmentName);
	}
	
	private void cleanUp() throws MojoExecutionException {
		try {
			FileUtils.deleteDirectory(tempDir);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}

class JDWarFileNameFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return name.endsWith(".war");
	}
}