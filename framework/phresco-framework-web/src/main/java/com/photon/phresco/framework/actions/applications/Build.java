/*
 * ###
 * Framework Web Archive
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
package com.photon.phresco.framework.actions.applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.photon.phresco.commons.AndroidConstants;
import com.photon.phresco.commons.BuildInfo;
import com.photon.phresco.commons.PluginProperties;
import com.photon.phresco.commons.XCodeConstants;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.api.ProjectRuntimeManager;
import com.photon.phresco.framework.commons.ApplicationsUtil;
import com.photon.phresco.framework.commons.DiagnoseUtil;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.framework.commons.PBXNativeTarget;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.IosSdkUtil;
import com.photon.phresco.util.IosSdkUtil.MacSdkType;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.android.AndroidProfile;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.PluginExecution.Configuration;
import com.phresco.pom.model.PluginExecution.Goals;
import com.phresco.pom.util.AndroidPomProcessor;
import com.phresco.pom.util.PomProcessor;

public class Build extends FrameworkBaseAction {

	private static final long serialVersionUID = -9172394838984622961L;
	private static final Logger S_LOGGER = Logger.getLogger(Build.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	private String showSettings = null;
	private String database = null;
	private String server = null;
	private String email = null;
	private String webservice = null;
	private String importSql = null;
	private String showError = null;
	private String hideLog = null;
	private String skipTest = null;
	private String showDebug = null;
	private InputStream fileInputStream;
	private String fileName = "";
	private String connectionAlive = "false";
	private String projectCode = null;
	private String sdk = null;
	private String target = "";
	private String mode = null;
	private String androidVersion = null;
	private String environments = null;
	private String serialNumber = null;
	private String proguard = null;
	private String projectModule = null;
	private String mainClassName = null;
	private String jarName = null;
	// Iphone deploy option
	private String deployTo = "";
	private String userBuildName = null;
	private String userBuildNumber = null;

	// Create profile
	private String keystore = null;
	private String storepass = null;
	private String keypass = null;
	private String alias = null;
	private boolean profileCreationStatus = false;
	private String profileCreationMessage = null;
	private String profileAvailable = null;
	private String signing = null;
	private List<String> databases = null;
	private List<String> sqlFiles = null;
	private static Map<String, List<String>> projectModuleMap = Collections
			.synchronizedMap(new HashMap<String, List<String>>(8));
	private static Map<String, String> sqlFolderPathMap = new HashMap<String, String>();

	// DbWithSqlFiles
	private String DbWithSqlFiles = null;
	static {
		initDbPathMap();
	}

	public String view() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method  Build.view()");

		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			List<BuildInfo> builds = administrator.getBuildInfos(project);
			getHttpRequest().setAttribute(REQ_SELECTED_APP_TYPE, project.getProjectInfo().getTechnology().getId());
			getHttpRequest().setAttribute(REQ_BUILD, builds);
			getHttpRequest().setAttribute(REQ_PROJECT, project);
			String techId = project.getProjectInfo().getTechnology().getId();
			String readLogFile = "";
			boolean tempConnectionAlive = false;
			int serverPort = 0;
			if (TechnologyTypes.NODE_JS_WEBSERVICE.equals(techId)) {
				String serverProtocol = (String) getHttpSession().getAttribute(
						projectCode + SESSION_NODEJS_SERVER_PROTOCOL_VALUE);
				String serverHost = (String) getHttpSession().getAttribute(
						projectCode + SESSION_NODEJS_SERVER_HOST_VALUE);
				String serverPortStr = (String) getHttpSession().getAttribute(
						projectCode + SESSION_NODEJS_SERVER_PORT_VALUE);
				if (serverProtocol == null && serverHost == null && serverPortStr == null) {
					String runAgainstInfoEnv = readRunAgainstInfo(projectCode);
					if (runAgainstInfoEnv != null && !runAgainstInfoEnv.isEmpty()) {
						List<SettingsInfo> settingsInfos = administrator.getSettingsInfos(
								Constants.SETTINGS_TEMPLATE_SERVER, projectCode, runAgainstInfoEnv);
						if (settingsInfos != null && CollectionUtils.isNotEmpty(settingsInfos)) {
							for (SettingsInfo settingsInfo : settingsInfos) {
								serverProtocol = settingsInfo.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
								serverHost = settingsInfo.getPropertyInfo(Constants.SERVER_HOST).getValue();
								serverPortStr = settingsInfo.getPropertyInfo(Constants.SERVER_PORT).getValue();
								readLogFile = "";

							}
						}
					}
				}
				if (serverPortStr != null) {
					serverPort = Integer.parseInt(serverPortStr);
				}
				if (serverProtocol != null && serverHost != null && serverPort != 0) {
					tempConnectionAlive = DiagnoseUtil.isConnectionAlive(serverProtocol, serverHost, serverPort);
					getHttpSession().setAttribute(projectCode + SESSION_NODEJS_SERVER_STATUS, tempConnectionAlive);
				}
				if (tempConnectionAlive) {
					readLogFile = readLogFile(project, READ_LOG_VIEW);
				} else {
					deleteNodejsLogFile(projectCode);
					readLogFile = "";

				}
			}
			if (TechnologyTypes.HTML5_MOBILE_WIDGET.equals(techId) || TechnologyTypes.HTML5_WIDGET.equals(techId)
					|| TechnologyTypes.JAVA_WEBSERVICE.equals(techId)
					|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(techId)) {
				String serverProtocol = (String) getHttpSession().getAttribute(
						projectCode + SESSION_JAVA_SERVER_PROTOCOL_VALUE);
				String serverHost = (String) getHttpSession()
						.getAttribute(projectCode + SESSION_JAVA_SERVER_HOST_VALUE);
				String serverPortStr = (String) getHttpSession().getAttribute(
						projectCode + SESSION_JAVA_SERVER_PORT_VALUE);
				if (serverProtocol == null && serverHost == null && serverPortStr == null) {
					String runAgainstInfoEnv = readRunAgainstInfo(projectCode);
					if (runAgainstInfoEnv != null && !runAgainstInfoEnv.isEmpty()) {
						List<SettingsInfo> settingsInfos = administrator.getSettingsInfos(
								Constants.SETTINGS_TEMPLATE_SERVER, projectCode, runAgainstInfoEnv);
						if (settingsInfos != null && CollectionUtils.isNotEmpty(settingsInfos)) {
							for (SettingsInfo settingsInfo : settingsInfos) {
								serverProtocol = settingsInfo.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
								serverHost = settingsInfo.getPropertyInfo(Constants.SERVER_HOST).getValue();
								serverPortStr = settingsInfo.getPropertyInfo(Constants.SERVER_PORT).getValue();
								readLogFile = "";

							}
						}
					}
				}
				if (serverPortStr != null) {
					serverPort = Integer.parseInt(serverPortStr);
				}
				if (serverProtocol != null && serverHost != null && serverPort != 0) {
					boolean connectionAlive = DiagnoseUtil.isConnectionAlive(serverProtocol, serverHost, serverPort);
					if (connectionAlive) {
						readLogFile = javaReadLogFile();
					} else {
						readLogFile = "";
					}
					getHttpSession().setAttribute(projectCode + SESSION_JAVA_SERVER_STATUS, connectionAlive);
				}
			}
			getHttpRequest().setAttribute(REQ_SERVER_LOG, readLogFile);

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.view()" + FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Build view");
		}

		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return APP_BUILD;
	}

	public String generateBuild() {
		S_LOGGER.debug("Entering Method  Build.generateBuild()");

		String technology = null;
		String from = null;
		Project project = null;
		List<String> projectModules = null;
		String buildNumber = null;
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			project = administrator.getProject(projectCode);
			technology = project.getProjectInfo().getTechnology().getId();
			from = getHttpRequest().getParameter(REQ_BUILD_FROM);
			importSqlFlag(project);
			buildNumber = getHttpRequest().getParameter(REQ_DEPLOY_BUILD_NUMBER);
			if (DEPLOY.equals(from)) {
				BuildInfo buildInfo = administrator.getBuildInfo(project, Integer.parseInt(buildNumber));
				List<String> buildInfoEnvs = buildInfo.getEnvironments();
				getHttpRequest().setAttribute(BUILD_INFO_ENVS, buildInfoEnvs);
			} else {
				List<Environment> environments = administrator.getEnvironments(project);
				getHttpRequest().setAttribute(REQ_ENVIRONMENTS, environments);
			}
			// Get xcode targets
			if (TechnologyTypes.IPHONES.contains(technology)) {
				List<PBXNativeTarget> xcodeConfigs = ApplicationsUtil.getXcodeConfiguration(projectCode);
				for (PBXNativeTarget xcodeConfig : xcodeConfigs) {
					S_LOGGER.debug("Iphone technology terget name" + xcodeConfig.getName());
				}
				getHttpRequest().setAttribute(REQ_XCODE_CONFIGS, xcodeConfigs);
				// get list of sdks
				List<String> iphoneSdks = IosSdkUtil.getMacSdks(MacSdkType.iphoneos);
				iphoneSdks.addAll(IosSdkUtil.getMacSdks(MacSdkType.iphonesimulator));
				iphoneSdks.addAll(IosSdkUtil.getMacSdks(MacSdkType.macosx));
				getHttpRequest().setAttribute(REQ_IPHONE_SDKS, iphoneSdks);
			}

			// get has signing info from android pom
			if (TechnologyTypes.ANDROIDS.contains(technology)) {
				StringBuilder builder = new StringBuilder(Utility.getProjectHome());
				builder.append(projectCode);
				builder.append(File.separatorChar);
				builder.append(POM_XML);
				File pomPath = new File(builder.toString());
				AndroidPomProcessor processor = new AndroidPomProcessor(pomPath);
				if (pomPath.exists() && processor.hasSigning()) {
					getHttpRequest().setAttribute(REQ_ANDROID_HAS_SIGNING, true);
				} else {
					getHttpRequest().setAttribute(REQ_ANDROID_HAS_SIGNING, false);
				}
			}

			projectModules = projectModuleMap.get(projectCode);
			if (CollectionUtils.isEmpty(projectModules)) {
				projectModules = getWarProjectModules(projectCode);
				projectModuleMap.put(projectCode, projectModules);
			}

			if (TechnologyTypes.JAVA_STANDALONE.equals(technology)) {
				getValueFromJavaStdAlonePom();
			}

		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Build.generateBuild()" + FrameworkUtil.getStackTraceAsString(e));
		}

		if (CollectionUtils.isNotEmpty(projectModules)) {
			getHttpRequest().setAttribute(REQ_PROJECT_MODULES, projectModules);
		}

		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
		getHttpRequest().setAttribute(REQ_PROJECT, project);
		getHttpRequest().setAttribute(REQ_BUILD_FROM, from);
		getHttpRequest().setAttribute(REQ_TECHNOLOGY, technology);
		getHttpRequest().setAttribute(REQ_DEPLOY_BUILD_NUMBER, buildNumber);
		return APP_GENERATE_BUILD;
	}

	public String builds() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method  Build.builds()");

		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			Project project = administrator.getProject(projectCode);
			List<BuildInfo> builds = administrator.getBuildInfos(project);
			getHttpRequest().setAttribute(REQ_BUILD, builds);
			getHttpRequest().setAttribute(REQ_PROJECT, project);

			sessionMap.remove(SESSION_PROPERTY_INFO_LIST);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.builds()" + FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Getting builds info");
		}

		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return APP_BUILDS;
	}

	public String build() {
		S_LOGGER.debug("Entering Method  Build.build()");
		try {
			ActionType actionType = null;
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			String technology = project.getProjectInfo().getTechnology().getId();

			Map<String, String> settingsInfoMap = new HashMap<String, String>(2);

			if (StringUtils.isNotEmpty(environments)) {
				settingsInfoMap.put(ENVIRONMENT_NAME, environments);
			}

			if (StringUtils.isNotEmpty(userBuildName)) {
				settingsInfoMap.put(BUILD_NAME, userBuildName);
			}

			if (StringUtils.isNotEmpty(userBuildNumber)) {
				settingsInfoMap.put(BUILD_NUMBER, userBuildNumber);
			}

			if (StringUtils.isNotEmpty(androidVersion)) {
				settingsInfoMap.put(AndroidConstants.ANDROID_VERSION_MVN_PARAM, androidVersion);
			}

			if (TechnologyTypes.JAVA_STANDALONE.contains(technology)) {
				settingsInfoMap.put(MAINCLASSNAME, mainClassName);
				settingsInfoMap.put(JARNAME, jarName);
			}

			if (TechnologyTypes.IPHONES.contains(technology)) {
				settingsInfoMap.put(IPHONE_SDK, sdk);
				settingsInfoMap.put(IPHONE_CONFIGURATION, mode);
				settingsInfoMap.put(IPHONE_TARGET_NAME, target);
				if (TechnologyTypes.IPHONE_HYBRID.equals(technology)) {
					settingsInfoMap.put(IPHONE_PLISTFILE, XCodeConstants.HYBRID_PLIST);
					settingsInfoMap.put(ENCRYPT, FALSE);
				} else if (TechnologyTypes.IPHONE_NATIVE.equals(technology)) {
					settingsInfoMap.put(IPHONE_PLISTFILE, XCodeConstants.NATIVE_PLIST);
					settingsInfoMap.put(ENCRYPT, TRUE);
				}
			}

			if (TechnologyTypes.ANDROIDS.contains(technology)) {
				actionType = ActionType.MOBILE_COMMON_COMMAND;
				actionType.setSkipTest(true);
				actionType.setProfileId("");
				if (StringUtils.isNotEmpty(signing) && StringUtils.isNotEmpty(profileAvailable)) {
					StringBuilder builder = new StringBuilder(Utility.getProjectHome());
					builder.append(projectCode);
					builder.append(File.separatorChar);
					builder.append(POM_XML);
					File pomPath = new File(builder.toString());
					AndroidPomProcessor processor = new AndroidPomProcessor(pomPath);
					if (pomPath.exists() && processor.hasSigning()) {
						actionType.setProfileId(processor.getSigningProfile());
					}
				}
				if (StringUtils.isEmpty(proguard)) {
					// if the checkbox is selected value should be set to false
					// otherwise true
					proguard = TRUE;
				}
				settingsInfoMap.put(ANDROID_PROGUARD_SKIP, proguard);
				// settingsInfoMap.put(SKIPTESTS, TRUE);
			} else if (TechnologyTypes.IPHONES.contains(technology)) {
				actionType = ActionType.IPHONE_BUILD_UNIT_TEST;
			} else {
				actionType = ActionType.BUILD;
			}

			if (StringUtils.isNotEmpty(projectModule)) {
				settingsInfoMap.put(MODULE_NAME, projectModule);
				actionType.setWorkingDirectory(Utility.getProjectHome() + project.getProjectInfo().getCode()
						+ File.separatorChar + projectModule);
			} else {
				actionType.setWorkingDirectory(null);
			}
			actionType.setHideLog(Boolean.parseBoolean(hideLog));
			actionType.setShowError(Boolean.parseBoolean(showError));
			actionType.setShowDebug(Boolean.parseBoolean(showDebug));
			actionType.setSkipTest(Boolean.parseBoolean(skipTest));
			BufferedReader reader = runtimeManager.performAction(project, actionType, settingsInfoMap, null);
			getHttpSession().setAttribute(projectCode + REQ_BUILD, reader);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, REQ_BUILD);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Build.build()" + FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Building ");
		}

		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);

		return APP_ENVIRONMENT_READER;
	}

	private File isFileExists(Project project) throws IOException {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(project.getProjectInfo().getCode());
		builder.append(File.separator);
		builder.append("do_not_checkin");
		builder.append(File.separator);
		builder.append("temp");
		File tempFolder = new File(builder.toString());
		if (!tempFolder.exists()) {
			tempFolder.mkdir();
		}
		builder.append(File.separator);
		builder.append("importsql.property");
		File configFile = new File(builder.toString());
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		return configFile;
	}

	private void updateImportSqlConfig(Project project) throws PhrescoException {

		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Build.checkImportsqlConfig(Project project)");
		}
		if (debugEnabled) {
			S_LOGGER.debug("adaptImportsqlConfig ProjectInfo = " + project.getProjectInfo());
		}
		InputStream is = null;
		FileWriter fw = null;

		try {
			File configFile = isFileExists(project);

			is = new FileInputStream(configFile);
			PluginProperties configProps = new PluginProperties();
			configProps.load(is);
			fw = new FileWriter(configFile);
			fw.write("build.import.sql.first.time=" + importSql);
			fw.flush();
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private void importSqlFlag(Project project) throws PhrescoException {
		String technology = project.getProjectInfo().getTechnology().getId();
		InputStream is = null;
		String importSqlElement;
		try {

			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(project.getProjectInfo().getCode());
			builder.append(File.separator);
			builder.append(DO_NOT_CHECKIN_DIR);
			builder.append(File.separator);
			builder.append(TEMP_FOLDER);
			builder.append(File.separator);
			builder.append(IMPORT_PROPERTY);
			File configFile = new File(builder.toString());
			if (!configFile.exists() && !TechnologyTypes.IPHONES.contains(technology)
					|| !TechnologyTypes.ANDROIDS.contains(technology)) {
				getHttpRequest().setAttribute(REQ_IMPORT_SQL, Boolean.TRUE.toString());
			}

			if (configFile.exists()) {
				is = new FileInputStream(configFile);
				PluginProperties configProps = new PluginProperties();
				configProps.load(is);
				@SuppressWarnings("rawtypes")
				Enumeration enumProps = configProps.keys();

				while (enumProps.hasMoreElements()) {
					importSqlElement = (String) enumProps.nextElement();
					String importSqlProps = (String) configProps.get(importSqlElement);
					getHttpRequest().setAttribute(REQ_IMPORT_SQL, importSqlProps);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String delete() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Build.delete()");
		}

		String[] buildNumbers = getHttpRequest().getParameterValues(REQ_BUILD_NUMBER);
		if (buildNumbers == null || buildNumbers.length == 0) {
			// TODO: Warn the user
		}

		int[] buildInts = new int[buildNumbers.length];
		for (int i = 0; i < buildNumbers.length; i++) {
			if (debugEnabled) {
				S_LOGGER.debug("To be deleted build numbers " + buildNumbers[i]);
			}
			buildInts[i] = Integer.parseInt(buildNumbers[i]);
		}

		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			administrator.deleteBuildInfos(project, buildInts);
			getHttpRequest().setAttribute(REQ_PROJECT, project);
			addActionMessage(getText(SUCCESS_BUILD_DELETE));
		} catch (PhrescoException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.delete()" + FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Deleting build");
		}

		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return view();
	}

	public String deploy() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Build.deploy()");
		}
		String buildNumber = getHttpRequest().getParameter(REQ_DEPLOY_BUILD_NUMBER);
		String simulatorVersion = getHttpRequest().getParameter(REQ_DEPLOY_IPHONE_SIMULATOR_VERSION);
		try {
			if (StringUtils.isNotEmpty(importSql)) {
				configureSqlExecution();
			}
			ActionType actionType = null;
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			BuildInfo buildInfo = administrator.getBuildInfo(project, Integer.parseInt(buildNumber));
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			Map<String, String> valuesMap = new HashMap<String, String>(2);
			String techId = project.getProjectInfo().getTechnology().getId();
			if (TechnologyTypes.IPHONES.contains(techId)) {
				valuesMap.put(IPHONE_BUILD_NAME, buildInfo.getBuildName());
				// if deploy to device is selected we have to pass device deploy
				// param as additional param
				if (StringUtils.isNotEmpty(deployTo) && deployTo.equals(REQ_IPHONE_SIMULATOR)) {
					valuesMap.put(IPHONE_SIMULATOR_VERSION, simulatorVersion);
				} else {
					valuesMap.put(DEVICE_DEPLOY, TRUE);
				}
			} else {
				valuesMap.put(DEPLOY_BUILD_NAME, buildInfo.getBuildName());
			}

			if (!(TechnologyTypes.IPHONES.contains(techId) || TechnologyTypes.ANDROIDS.contains(techId) || TechnologyTypes.SHAREPOINT
					.equals(techId))
					&& StringUtils.isNotEmpty(importSql)) {
				valuesMap.put(DEPLOY_IMPORT_SQL, importSql);
			}

			if (StringUtils.isNotEmpty(environments)) {
				valuesMap.put(ENVIRONMENT_NAME, environments);
			}

			if (TechnologyTypes.SHAREPOINT.equals(project.getProjectInfo().getTechnology().getId())) {
				valuesMap.put(DEPLOY_SERVERNAME, buildInfo.getServerName());
			}

			if (debugEnabled) {
				S_LOGGER.debug("To be deployed build name" + buildInfo.getBuildName());
				S_LOGGER.debug("To be deployed build location" + buildInfo.getDeployLocation());
				S_LOGGER.debug("To be deployed build context" + buildInfo.getContext());
			}
			Technology technology = project.getProjectInfo().getTechnology();
			if (TechnologyTypes.ANDROIDS.contains(technology.getId())) {
				String device = getHttpRequest().getParameter(REQ_ANDROID_DEVICE);
				if (device.equals(SERIAL_NUMBER)) {
					device = serialNumber;
				}
				if (debugEnabled) {
					S_LOGGER.debug("To be deployed Android device name" + device);
				}
				valuesMap.put(DEPLOY_ANDROID_DEVICE_MODE, device); // TODO: Need
																	// to be
																	// changed
				valuesMap.put(DEPLOY_ANDROID_EMULATOR_AVD, REQ_ANDROID_DEFAULT);
				// valuesMap.put(AndroidConstants.ANDROID_VERSION_MVN_PARAM,
				// androidVersion);
			}
			if (TechnologyTypes.IPHONES.contains(technology) || TechnologyTypes.ANDROIDS.contains(technology)) {
				actionType = ActionType.MOBILE_COMMON_COMMAND;
			} else {
				actionType = ActionType.DEPLOY;
			}

			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(project.getProjectInfo().getCode());
			if (StringUtils.isNotEmpty(buildInfo.getModuleName())) {
				builder.append(File.separator);
				builder.append(buildInfo.getModuleName());
			}
			actionType.setWorkingDirectory(builder.toString());
			actionType.setHideLog(Boolean.parseBoolean(hideLog));
			actionType.setShowError(Boolean.parseBoolean(showError));
			actionType.setShowDebug(Boolean.parseBoolean(showDebug));
			actionType.setSkipTest(Boolean.parseBoolean(skipTest));
			BufferedReader reader = runtimeManager.performAction(project, actionType, valuesMap, null);
			getHttpSession().setAttribute(projectCode + REQ_FROM_TAB_DEPLOY, reader);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, REQ_FROM_TAB_DEPLOY);

			// This is for first time to check import Sql checkbox, and not
			// required for the below technologies
			if (!(TechnologyTypes.IPHONES.contains(technology) || TechnologyTypes.ANDROIDS.contains(technology) || TechnologyTypes.SHAREPOINT
					.equals(technology))) {
				updateImportSqlConfig(project);
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.deploy()" + FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Deploying");
		}

		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return APP_ENVIRONMENT_READER;
	}

	public String deployAndroid() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Build.deployAndroid()");
		}
		try {
			String buildNumber = getHttpRequest().getParameter(REQ_DEPLOY_BUILD_NUMBER);
			if (debugEnabled) {
				S_LOGGER.debug("Deploy Android build number" + buildNumber);
			}
			getHttpRequest().setAttribute("projectCode", projectCode);
			getHttpRequest().setAttribute(REQ_DEPLOY_BUILD_NUMBER, buildNumber);
			getHttpRequest().setAttribute(REQ_FROM_TAB, REQ_FROM_TAB_DEPLOY);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.deployAndroid()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Deploying android");
		}
		return APP_DEPLOY_ANDROID;
	}

	public String deployIphone() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Build.deployIphone()");
		}
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			String buildNumber = getHttpRequest().getParameter(REQ_DEPLOY_BUILD_NUMBER);
			BuildInfo buildInfo = administrator.getBuildInfo(project, Integer.parseInt(buildNumber));
			boolean createIpa = MapUtils.getBooleanValue(buildInfo.getOptions(), CAN_CREATE_IPA);
			boolean deviceDeploy = MapUtils.getBooleanValue(buildInfo.getOptions(), DEPLOY_TO_DEVICE);
			getHttpRequest().setAttribute(REQ_DEPLOY_BUILD_NUMBER, buildNumber);
			if (debugEnabled) {
				S_LOGGER.debug("Deploy IPhone build number" + buildNumber);
			}
			getHttpRequest().setAttribute("projectCode", projectCode);
			getHttpRequest().setAttribute(REQ_HIDE_DEPLOY_TO_SIMULATOR,
					new Boolean(!createIpa && !deviceDeploy ? true : false));
			getHttpRequest().setAttribute(REQ_HIDE_DEPLOY_TO_DEVICE,
					new Boolean(createIpa && deviceDeploy ? true : false));
			getHttpRequest().setAttribute(REQ_FROM_TAB, REQ_FROM_TAB_DEPLOY);
			// get list of sdk versions
			List<String> iphoneSimulatorSdks = IosSdkUtil.getMacSdksVersions(MacSdkType.iphonesimulator);
			getHttpRequest().setAttribute(REQ_IPHONE_SIMULATOR_SDKS, iphoneSimulatorSdks);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.Iphone()" + FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Deploying Iphone");
		}
		return APP_DEPLOY_IPHONE;
	}

	public String download() {

		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Build.download()");
		}
		String buildNumber = getHttpRequest().getParameter(REQ_DEPLOY_BUILD_NUMBER);
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(project.getProjectInfo().getCode());
			builder.append(File.separator);
			String moduleName = administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getModuleName();
			if (StringUtils.isNotEmpty(moduleName)) {
				builder.append(moduleName);
				builder.append(File.separator);
			}
			builder.append(BUILD_DIR);
			builder.append(File.separator);
			builder.append(administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName());
			if (debugEnabled) {
				S_LOGGER.debug("Download build number " + buildNumber + " Download location " + builder.toString());
			}
			if (TechnologyTypes.IPHONES.contains(project.getProjectInfo().getTechnology().getId())) {
				String path = administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getDeliverables();
				fileInputStream = new FileInputStream(new File(path));
				fileName = administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName();
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			} else {
				fileInputStream = new FileInputStream(new File(builder.toString()));
				fileName = administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName();
			}
			return SUCCESS;
		} catch (FileNotFoundException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.download()" + e);
			}
			new LogErrorReport(e, "Download builds");

		} catch (Exception e1) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.download()" + FrameworkUtil.getStackTraceAsString(e1));
			}
			new LogErrorReport(e1, "Download builds");
		}
		return view();
	}

	public String downloadIpa() {

		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Build.downloadIPA()");
		}
		String buildNumber = getHttpRequest().getParameter(REQ_DEPLOY_BUILD_NUMBER);
		try {
			ActionType actionType = ActionType.IPHONE_DOWNLOADIPA_COMMAND;
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			Project project = administrator.getProject(projectCode);
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(project.getProjectInfo().getCode());
			builder.append(File.separator);
			builder.append(BUILD_DIR);
			builder.append(File.separator);
			builder.append(administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName());
			String buildName = administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName();
			String buildNameSubstring = buildName.substring(0, buildName.lastIndexOf("/"));
			String appBuildName = buildNameSubstring.substring(buildNameSubstring.lastIndexOf("/") + 1);
			Map<String, String> valuesMap = new HashMap<String, String>(2);
			valuesMap.put("application.name", projectCode);
			valuesMap
					.put("app.path", administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName());
			valuesMap.put("build.name", appBuildName);
			BufferedReader reader = runtimeManager.performAction(project, actionType, valuesMap, null);
			while (reader.readLine() != null) {
				System.out.println("reader.readLine() ======> " + reader.readLine());
			}
			String ipaPath = administrator.getBuildInfo(project, Integer.parseInt(buildNumber)).getBuildName();
			ipaPath = ipaPath.substring(0, ipaPath.lastIndexOf("/")) + FILE_SEPARATOR + projectCode + ".ipa";
			fileInputStream = new FileInputStream(new File(ipaPath));
			fileName = projectCode + ".ipa";
			return SUCCESS;

		} catch (FileNotFoundException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.download()" + e);
			}
			new LogErrorReport(e, "Download builds");

		} catch (Exception e1) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.downloadIpa()"
						+ FrameworkUtil.getStackTraceAsString(e1));
			}
			new LogErrorReport(e1, "Download buildsIPA");
		}
		return view();
	}

	public String NodeJSRunAgainstSource() throws PhrescoException {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.runAgainstSource()");

		String serverHost = null;
		String serverProtocol = null;
		int serverPort = 0;

		try {
			if (StringUtils.isNotEmpty(importSql)) {
				configureSqlExecution();
			}
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			String projectCode = project.getProjectInfo().getCode();
			String importSQL = (String) getHttpRequest().getParameter(IMPORT_SQL);
			getHttpSession().setAttribute(projectCode + SESSION_NODEJS_IMPORTSQL_VALUE, importSQL);
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			List<SettingsInfo> serverDetails = administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER,
					projectCode, environments);
			for (SettingsInfo settingsInfo : serverDetails) {
				serverHost = settingsInfo.getPropertyInfo(Constants.SERVER_HOST).getValue();
				serverProtocol = settingsInfo.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
				serverPort = Integer.parseInt(settingsInfo.getPropertyInfo(Constants.SERVER_PORT).getValue());
			}
			boolean tempConnectionAlive = DiagnoseUtil.isConnectionAlive(serverProtocol, serverHost, serverPort);
			if (!tempConnectionAlive) {
				deleteNodejsLogFile(projectCode);
			}
			Map<String, String> valueMap = new HashMap<String, String>(2);
			valueMap.put(ENVIRONMENT_NAME, environments);
			valueMap.put(IMPORT_SQL, importSQL);
			ActionType nodeStart = ActionType.START_SERVER;
			BufferedReader reader = runtimeManager.performAction(project, nodeStart, valueMap, null);
			String line;
			line = reader.readLine();
			while (!line.startsWith("[INFO] server started") && !line.startsWith("[INFO] server startup failed")) {
				line = reader.readLine();
			}
			waitForTime(2);
			readLogFile(project, "");

			tempConnectionAlive = DiagnoseUtil.isConnectionAlive(serverProtocol, serverHost, serverPort);
			getHttpSession().setAttribute(projectCode + SESSION_NODEJS_SERVER_PROTOCOL_VALUE, serverProtocol);
			getHttpSession().setAttribute(projectCode + SESSION_NODEJS_SERVER_HOST_VALUE, serverHost);
			getHttpSession().setAttribute(projectCode + SESSION_NODEJS_SERVER_PORT_VALUE,
					new Integer(serverPort).toString());
			getHttpSession().setAttribute(projectCode + SESSION_NODEJS_SERVER_STATUS, tempConnectionAlive);
			getHttpSession().setAttribute(projectCode + SESSION_ENV_NAME, environments);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.runAgainstSource()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(new PhrescoException("Server Startup Failed"), "NodeJS run against source");
		}
		return APP_ENVIRONMENT_READER;
	}

	public String restartNodeJSServer() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.restartNodeJSServer()");
		stopNodeJSServer();
		startNodeJSServer();
		return APP_ENVIRONMENT_READER;

	}

	public String startNodeJSServer() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.startNodeJSServer()");

		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			String importSql = (String) getHttpSession().getAttribute(projectCode + SESSION_NODEJS_IMPORTSQL_VALUE);
			environments = (String) getHttpSession()
					.getAttribute(project.getProjectInfo().getCode() + SESSION_ENV_NAME);
			if (debugEnabled) {
				S_LOGGER.debug("startNodeJSServer Environment name " + environments);
			}
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			Map<String, String> valueMap = new HashMap<String, String>(2);
			valueMap.put(ENVIRONMENT_NAME, environments);
			valueMap.put(IMPORT_SQL, importSql);
			ActionType nodeStart = ActionType.START_SERVER;
			BufferedReader reader = runtimeManager.performAction(project, nodeStart, valueMap, null);
			String line;
			line = reader.readLine();
			while (!line.startsWith("[INFO] server started") && !line.startsWith("[INFO] server startup failed")) {
				line = reader.readLine();
			}
			readLogFile(project, "");
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.startNodeJSServer()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Start nodejs server");
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return APP_ENVIRONMENT_READER;
	}

	public String stopNodeJSServer() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.stopNodeJSServer()");

		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(projectCode);
			builder.append(File.separator);
			builder.append(FOLDER_DOT_PHRESCO);
			builder.append(File.separator);
			builder.append(RUN_AGS_ENV_FILE);
			File envFile = new File(builder.toString());
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			ActionType nodeStop = ActionType.STOP_SERVER;
			BufferedReader reader = runtimeManager.performAction(project, nodeStop, null, null);
			String line;
			line = reader.readLine();
			while (!line.startsWith("[INFO] BUILD SUCCESS")) {
				line = reader.readLine();
			}
			readLogFile(project, "");
			if (envFile.exists()) {
				envFile.delete();
			}
			getHttpSession().removeAttribute(project.getProjectInfo().getCode() + SESSION_NODEJS_SERVER_STATUS);
			getHttpSession().removeAttribute(project.getProjectInfo().getCode() + SESSION_NODEJS_IMPORTSQL_VALUE);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_PROJECT, project);

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.stopNodeJSServer()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Stop nodejs server");
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return APP_ENVIRONMENT_READER;
	}

	// Second parameter is used to specify this method should return only string
	// to view method
	public String readLogFile(Project project, String fromNodejs) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(project.getProjectInfo().getCode());
		builder.append(File.separator);
		builder.append(DO_NOT_CHECKIN_DIR);
		builder.append(File.separator);
		builder.append(LOG_DIR);
		builder.append(File.separator);
		builder.append(LOG_FILE);
		BufferedReader reader = null;
		StringBuffer contents = new StringBuffer();
		try {
			File file = new File(builder.toString());
			// It executed when file not exist and view method called
			if (!file.exists() && fromNodejs.equals(READ_LOG_VIEW)) {
				return "";
			}
			// It executed when file exist and view method called
			if (file.exists() && fromNodejs.equals(READ_LOG_VIEW)) {
				reader = new BufferedReader(new FileReader(file));
				String text = null;
				while ((text = reader.readLine()) != null) {
					contents.append(text).append(System.getProperty(LINE_SEPERATOR));
				}
				return contents.toString();
			}
			// It executed when file not exist and return reader
			if (!file.exists()) {
				StringReader sb = new StringReader("Server startup failed");
				reader = new BufferedReader(sb);
				// getHttpSession().setAttribute(REQ_READER, reader);
				getHttpSession().setAttribute(projectCode + REQ_READ_LOG_FILE, reader);
				getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
				getHttpRequest().setAttribute(REQ_TEST_TYPE, REQ_READ_LOG_FILE);
			}
			// It executed when file existence and return reader
			if (file.exists()) {
				reader = new BufferedReader(new FileReader(file));
				getHttpSession().setAttribute(projectCode + REQ_READ_LOG_FILE, reader);
				getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
				getHttpRequest().setAttribute(REQ_TEST_TYPE, REQ_READ_LOG_FILE);
			}

		} catch (FileNotFoundException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.readLogFile()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		} catch (IOException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.readLogFile()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return null;
	}

	public String runAgainstSource() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.javaRunAgainstSource()");
		try {
			if (StringUtils.isNotEmpty(importSql)) {
				configureSqlExecution();
			}
			String importSQL = (String) getHttpRequest().getParameter(IMPORT_SQL);
			handleRunAgainstSrc(environments, importSQL);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.javaRunAgainstSource()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Java run against source");
		}
		return APP_ENVIRONMENT_READER;
	}

	private void handleRunAgainstSrc(String environments, String importSQL) {
		String serverHost = "";
		String serverProtocol = "";
		int serverPort = 0;
		BufferedReader fileReader = null;
		String line = "";
		try {
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			getHttpSession().setAttribute(projectCode + IMPORT_SQL, importSQL);
			Map<String, String> javaMap = new HashMap<String, String>(2);
			List<SettingsInfo> serverDetails = administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER,
					projectCode, environments);
			for (SettingsInfo settingsInfo : serverDetails) {
				serverHost = settingsInfo.getPropertyInfo(Constants.SERVER_HOST).getValue();
				serverProtocol = settingsInfo.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
				serverPort = Integer.parseInt(settingsInfo.getPropertyInfo(Constants.SERVER_PORT).getValue());
				break;
			}

			try {
				compileProject();
				fileReader = new BufferedReader(new FileReader(getLogFilePath()));
				line = fileReader.readLine();
				while (line != null && !line.startsWith("[INFO] BUILD FAILURE")) {
					line = fileReader.readLine();
				}
			} finally {
				if (fileReader != null) {
					fileReader.close();
				}
			}

			if (line != null && line.startsWith("[INFO] BUILD FAILURE")) {
				getHttpSession().setAttribute(projectCode + REQ_JAVA_START,
						new BufferedReader(new FileReader(getLogFilePath())));
			} else {
				// TODO: delete the server.log and create empty server.log file
				deleteLogFile();
				javaMap.put(ENVIRONMENT_NAME, environments);
				javaMap.put(IMPORT_SQL, importSQL);
				ActionType serverStart = ActionType.START_SERVER;
				BufferedReader reader = runtimeManager.performAction(project, serverStart, javaMap, null);
				getHttpSession().setAttribute(projectCode + REQ_JAVA_START, reader);
			}
			boolean connectionAlive = DiagnoseUtil.isConnectionAlive(serverProtocol, serverHost, serverPort);
			getHttpSession().setAttribute(projectCode + SESSION_JAVA_SERVER_PROTOCOL_VALUE, serverProtocol);
			getHttpSession().setAttribute(projectCode + SESSION_JAVA_SERVER_HOST_VALUE, serverHost);
			getHttpSession().setAttribute(projectCode + SESSION_JAVA_SERVER_PORT_VALUE,new Integer(serverPort).toString());
			getHttpSession().setAttribute(projectCode + SESSION_JAVA_SERVER_STATUS, connectionAlive);
			getHttpSession().setAttribute(projectCode + SESSION_ENV_NAME, environments);
			getHttpSession().setAttribute(projectCode + IMPORT_SQL, importSQL);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, REQ_JAVA_START);
		} catch (Exception e) {
			e.printStackTrace();
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.javaRunAgainstSource()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Java run against source");
		}
	}

	private BufferedReader compileProject() {
		BufferedReader reader = null;
		try {
			Commandline cl = new Commandline("mvn clean compile");
			String projectDir = Utility.getProjectHome() + projectCode;
			cl.setWorkingDirectory(projectDir);
			Process process = cl.execute();
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			writeLog(reader);
		} catch (CommandLineException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.installProject()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Java run against source");
		}
		return reader;
	}

	private void writeLog(BufferedReader in) {
		String line = null;
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			String logFolderPath = getLogFolderPath();
			File logfolder = new File(logFolderPath);
			if (!logfolder.exists()) {
				logfolder.mkdir();
			}
			fstream = new FileWriter(getLogFilePath());
			out = new BufferedWriter(fstream);
			while ((line = in.readLine()) != null) {
				out.write(line + "\n");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteLogFile() throws IOException {
		File logFile = new File(getLogFilePath());
		if (logFile.isFile() && logFile.exists()) {
			boolean delete = logFile.delete();
		}
		if (!logFile.exists()) {
			logFile.createNewFile();
		}

	}

	private String getLogFolderPath() {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(projectCode);
		builder.append(File.separator);
		builder.append(DO_NOT_CHECKIN_DIR);
		builder.append(File.separator);
		builder.append(LOG_DIR);
		return builder.toString();
	}

	private String getLogFilePath() {
		StringBuilder builder = new StringBuilder(getLogFolderPath());
		builder.append(File.separator);
		builder.append(LOG_FILE);
		return builder.toString();
	}

	public String restartServer() {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.restartServer()");
		stopServer();
		waitForTime(10);
		startServer();
		return APP_ENVIRONMENT_READER;
	}

	public String startServer() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Build.startNodeJSServer()");
		}
		try {
			environments = (String) getHttpSession().getAttribute(projectCode + SESSION_ENV_NAME);
			String importSql = (String) getHttpSession().getAttribute(projectCode + IMPORT_SQL);
			handleRunAgainstSrc(environments, importSql);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.javaStartServer()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Java start server");
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return APP_ENVIRONMENT_READER;
	}

	public String stopServer() {
		handleJavaStop(false);
		return APP_ENVIRONMENT_READER;
	}

	private void handleJavaStop(boolean readData) {
		if (debugEnabled)
			S_LOGGER.debug("Entering Method Build.javaStopServer()");
		try {
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			environments = (String) getHttpSession().getAttribute(projectCode + SESSION_ENV_NAME);
			if (environments == null) {
				environments = readRunAgainstInfo(projectCode);
			}
			Map<String, String> javaMap = new HashMap<String, String>(2);
			javaMap.put(ENVIRONMENT_NAME, environments);
			ActionType serverStop = ActionType.STOP_SERVER;
			BufferedReader reader = runtimeManager.performAction(project, serverStop, javaMap, null);
			if (readData) {
				String line = null;
				while ((line = reader.readLine()) != null) {
				}
			}
			getHttpSession().removeAttribute(project.getProjectInfo().getCode() + SESSION_JAVA_SERVER_STATUS);
			getHttpSession().removeAttribute(project.getProjectInfo().getCode() + IMPORT_SQL);
			getHttpSession().setAttribute(projectCode + REQ_JAVA_STOP, reader);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, REQ_JAVA_STOP);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.javaStopServer()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Java stop server");
		}
	}

	public String javaReadLogFile() throws InterruptedException {
		String logMsg = "";
		BufferedReader input = null;
		try {
			File logFile = null;
			File dir = new File(javaLogFileDir());
			if (dir.isDirectory()) {
				for (File child : dir.listFiles()) {
					if (child.getName().startsWith(CATALINA_FILE_START_NAME)) {
						logFile = child;
						input = new BufferedReader(new FileReader(logFile));
						String line = null;
						while ((line = input.readLine()) != null) {
							logMsg = logMsg + line + "<br/>";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				if (debugEnabled) {
					S_LOGGER.error("Entered into catch block of Build.javaReadLogFile()"
							+ FrameworkUtil.getStackTraceAsString(e));
				}
			}
		}
		return logMsg;
	}

	public String javaLogFileDir() {
		StringBuilder builder = null;
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			builder = new StringBuilder(Utility.getProjectHome());
			Project project = administrator.getProject(projectCode);
			builder.append(project.getProjectInfo().getCode());
			builder.append(File.separator);
			builder.append(DO_NOT_CHECKIN_DIR);
			builder.append(File.separator);
			builder.append(TARGET_DIR);
			builder.append(File.separator);
			builder.append(TOMCAT_DIR);
			builder.append(File.separator);
			builder.append(TOMCAT_LOGS_DIR);
			builder.append(File.separator);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.javaLogFileDir()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return builder.toString();
	}

	public File getLogFile(File dir) {
		waitForTime(1);
		File javaReadLogFile = null;
		for (File child : dir.listFiles()) {
			if (child.getName().startsWith(CATALINA_FILE_START_NAME)) {
				waitForTime(5);
				javaReadLogFile = child;
			}
		}
		return javaReadLogFile;
	}

	public void deleteLogFile(File dir) {
		for (File child : dir.listFiles()) {
			child.delete();
		}
		dir.delete();
	}

	public void waitForTime(int waitSec) {
		long startTime = 0;
		startTime = new Date().getTime();
		while (new Date().getTime() < startTime + waitSec * 1000) {
			// Dont do anything for some seconds. It waits till the log is
			// written to file
		}
	}

	private String readRunAgainstInfo(String projectCode) throws PhrescoException {
		String env = null;
		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(projectCode);
			builder.append(File.separator);
			builder.append(FOLDER_DOT_PHRESCO);
			builder.append(File.separator);
			builder.append(RUN_AGS_ENV_FILE);
			File envFile = new File(builder.toString());
			if (envFile.exists()) {
				reader = new BufferedReader(new FileReader(builder.toString()));
				env = reader.readLine();
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}

		return env;
	}

	private void deleteNodejsLogFile(String projectcode) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(projectcode);
		builder.append(File.separator);
		builder.append(DO_NOT_CHECKIN_DIR);
		builder.append(File.separator);
		builder.append(LOG_DIR);
		builder.append(File.separator);
		builder.append(LOG_FILE);
		File nodejsLogFile = new File(builder.toString());
		if (nodejsLogFile.exists()) {
			nodejsLogFile.delete();
		}
	}

	private void getValueFromJavaStdAlonePom() throws PhrescoException {
		try {
			File file = new File(Utility.getProjectHome() + File.separator + projectCode + File.separator + POM_FILE);
			PomProcessor pomProcessor = new PomProcessor(file);
			String finalName = pomProcessor.getFinalName();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName(JAVA_POM_MANIFEST);
			String mainClassValue = "";
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node node = nodeList.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element mainClassElement = (Element) node;
					mainClassValue = mainClassElement.getElementsByTagName(JAVA_POM_MAINCLASS).item(0).getTextContent();
					break;
				}
			}
			getHttpRequest().setAttribute(FINAL_NAME, finalName);
			getHttpRequest().setAttribute(MAIN_CLASS_VALUE, mainClassValue);

		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		}
	}

	public String advancedBuildSettings() {
		S_LOGGER.debug("Entering Method Build.advancedBuildSettings()");
		AndroidProfile androidProfile = null;
		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(projectCode);
			builder.append(File.separatorChar);
			builder.append(POM_XML);
			File pomPath = new File(builder.toString());
			AndroidPomProcessor processor = new AndroidPomProcessor(pomPath);
			if (pomPath.exists() && processor.hasSigning()) {
				String signingProfileid = processor.getSigningProfile();
				androidProfile = processor.getProfileElement(signingProfileid);
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Build.advancedBuildSettings()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		getHttpRequest().setAttribute("projectCode", projectCode);
		getHttpRequest().setAttribute(REQ_ANDROID_PROFILE_DET, androidProfile);
		getHttpRequest().setAttribute(REQ_FROM_TAB, REQ_FROM_TAB_DEPLOY);
		return SUCCESS;
	}

	public String createAndroidProfile() throws IOException {
		S_LOGGER.debug("Entering Method Build.createAndroidProfile()");
		boolean hasSigning = false;
		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(projectCode);
			builder.append(File.separatorChar);
			builder.append(POM_XML);
			File pomPath = new File(builder.toString());

			AndroidPomProcessor processor = new AndroidPomProcessor(pomPath);
			hasSigning = processor.hasSigning();
			String profileId = PROFILE_ID;
			String defaultGoal = GOAL_INSTALL;
			Plugin plugin = new Plugin();
			plugin.setGroupId(ANDROID_PROFILE_PLUGIN_GROUP_ID);
			plugin.setArtifactId(ANDROID_PROFILE_PLUGIN_ARTIFACT_ID);
			plugin.setVersion(ANDROID_PROFILE_PLUGIN_VERSION);

			PluginExecution execution = new PluginExecution();
			execution.setId(ANDROID_EXECUTION_ID);
			Goals goal = new Goals();
			goal.getGoal().add(GOAL_SIGN);
			execution.setGoals(goal);
			execution.setPhase(PHASE_PACKAGE);
			execution.setInherited(TRUE);

			AndroidProfile androidProfile = new AndroidProfile();
			androidProfile.setKeystore(keystore);
			androidProfile.setStorepass(storepass);
			androidProfile.setKeypass(keypass);
			androidProfile.setAlias(alias);
			androidProfile.setVerbose(true);
			androidProfile.setVerify(true);

			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			List<Element> executionConfig = new ArrayList<Element>();
			executionConfig.add(doc.createElement(ELEMENT_ARCHIVE_DIR));
			Element removeExistSignature = doc.createElement(ELEMENT_REMOVE_EXIST_SIGN);
			Element includeElement = doc.createElement(ELEMENT_INCLUDES);
			Element doNotCheckInBuildInclude = doc.createElement(ELEMENT_INCLUDE);
			doNotCheckInBuildInclude.setTextContent(ELEMENT_BUILD);
			Element doNotCheckinTargetInclude = doc.createElement(ELEMENT_INCLUDE);
			doNotCheckinTargetInclude.setTextContent(ELEMENT_TARGET);
			includeElement.appendChild(doNotCheckInBuildInclude);
			includeElement.appendChild(doNotCheckinTargetInclude);
			executionConfig.add(includeElement);
			removeExistSignature.setTextContent(TRUE);
			executionConfig.add(removeExistSignature);

			// verboss
			Element verbos = doc.createElement(ELEMENT_VERBOS);
			verbos.setTextContent(TRUE);
			executionConfig.add(verbos);
			// verify
			Element verify = doc.createElement(ELEMENT_VERIFY);
			verbos.setTextContent(TRUE);
			executionConfig.add(verify);

			Configuration configValues = new Configuration();
			configValues.getAny().addAll(executionConfig);
			execution.setConfiguration(configValues);
			List<Element> additionalConfigs = new ArrayList<Element>();
			processor.setProfile(profileId, defaultGoal, plugin, androidProfile, execution, null,
					additionalConfigs);
			processor.save();
			profileCreationStatus = true;
			if (hasSigning) {
				profileCreationMessage = getText(PROFILE_UPDATE_SUCCESS);
			} else {
				profileCreationMessage = getText(PROFILE_CREATE_SUCCESS);
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Build.createAndroidProfile()"
					+ FrameworkUtil.getStackTraceAsString(e));
			profileCreationStatus = false;
			if (hasSigning) {
				profileCreationMessage = getText(PROFILE_UPDATE_ERROR);
			} else {
				profileCreationMessage = getText(PROFILE_CREATE_ERROR);
			}
		}
		return SUCCESS;
	}

	public String getSqlDatabases() {
		String dbtype = "";
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			databases = new ArrayList<String>();
			List<SettingsInfo> databaseDetails = administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_DB,
					projectCode, environments);
			if (CollectionUtils.isNotEmpty(databaseDetails)) {
				for (SettingsInfo databasedetail : databaseDetails) {
					dbtype = databasedetail.getPropertyInfo(Constants.DB_TYPE).getValue();
					if (!databases.contains(dbtype)) {
						databases.add(dbtype);
					}
				}
			}
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of  Build.configSQL()" + FrameworkUtil.getStackTraceAsString(e));
		}
		return SUCCESS;
	}

	public String fetchSQLFiles() {
		String dbtype = null;
		String dbversion = null;
		String path = null;
		String sqlFileName = null;
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			sqlFiles = new ArrayList<String>();
			String techId = project.getProjectInfo().getTechnology().getId();
			String selectedDb = getHttpRequest().getParameter("selectedDb");
			String sqlPath = sqlFolderPathMap.get(techId);
			List<SettingsInfo> databaseDetails = administrator.getSettingsInfos( Constants.SETTINGS_TEMPLATE_DB,
					projectCode, environments);
			for (SettingsInfo databasedetail : databaseDetails) {
				dbtype = databasedetail.getPropertyInfo(Constants.DB_TYPE).getValue();
				if (selectedDb.equals(dbtype)) { 
					dbversion = databasedetail.getPropertyInfo(Constants.DB_VERSION).getValue();
					File[] dbSqlFiles = new File(Utility.getProjectHome() + projectCode + sqlPath + selectedDb + File.separator + dbversion).listFiles();
					for (int i = 0; i < dbSqlFiles.length; i++) {
						 sqlFileName = dbSqlFiles[i].getName();
						path = sqlPath + selectedDb + FILE_SEPARATOR +  dbversion + "#SEP#" +  sqlFileName ;
						sqlFiles.add(path);
					}
				}
			}
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of  Build.getSQLFiles()" + FrameworkUtil.getStackTraceAsString(e));
		}
		return SUCCESS;
	}

	private static void initDbPathMap() {
		sqlFolderPathMap.put(TechnologyTypes.PHP, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.PHP_DRUPAL6, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.PHP_DRUPAL7, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.HTML5_WIDGET, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.JAVA_WEBSERVICE, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.WORDPRESS, "/source/sql/");
	}

	public void configureSqlExecution() {
		Map<String, List<String>> dbsWithSqlFiles = new HashMap<String, List<String>>();
		String[] dbWithSqlFiles = DbWithSqlFiles.split("#SEP#");
		for (String dbWithSqlFile : dbWithSqlFiles) {
			String[] sqlFiles = dbWithSqlFile.split("#VSEP#");
			String[] sqlFileWithName = sqlFiles[1].split("#NAME#");
			if (dbsWithSqlFiles.containsKey(sqlFiles[0])) {
				List<String> sqlFilesList = dbsWithSqlFiles.get(sqlFiles[0]);
				sqlFilesList.add(sqlFileWithName[0]);
				dbsWithSqlFiles.put(sqlFiles[0], sqlFilesList);
			} else {
				List<String> sqlFilesList = new ArrayList<String>();
				sqlFilesList.add(sqlFileWithName[0]);
				dbsWithSqlFiles.put(sqlFiles[0], sqlFilesList);
			}
		}
		FileWriter writer = null;
		try {
			File sqlInfoFilePath = new File(Utility.getProjectHome() + projectCode + Constants.JSON_PATH);
			String json = new Gson().toJson(dbsWithSqlFiles);
			writer = new FileWriter(new File(sqlInfoFilePath.getPath()));
			writer.write(json);
		} catch (IOException e) {
			S_LOGGER.error("Entered into catch block of  Build.configureSqlExecution()"
					+ FrameworkUtil.getStackTraceAsString(e));
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				S_LOGGER.error("Entered into catch block of  Build.configureSqlExecution()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
	}

	public String getShowSettings() {
		return showSettings;
	}

	public void setShowSettings(String showSettings) {
		this.showSettings = showSettings;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebservice() {
		return webservice;
	}

	public void setWebservice(String webservice) {
		this.webservice = webservice;
	}

	public String getImportSql() {
		return importSql;
	}

	public void setImportSql(String importSql) {
		this.importSql = importSql;
	}

	public String getShowError() {
		return showError;
	}

	public void setShowError(String showError) {
		this.showError = showError;
	}

	public String getConnectionAlive() {
		return connectionAlive;
	}

	public void setConnectionAlive(String connectionAlive) {
		this.connectionAlive = connectionAlive;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getSdk() {
		return sdk;
	}

	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getEnvironments() {
		return environments;
	}

	public void setEnvironments(String environments) {
		this.environments = environments;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getProguard() {
		return proguard;
	}

	public void setProguard(String proguard) {
		this.proguard = proguard;
	}

	public String getHideLog() {
		return hideLog;
	}

	public void setHideLog(String hideLog) {
		this.hideLog = hideLog;
	}

	public String getProjectModule() {
		return projectModule;
	}

	public void setProjectModule(String projectModule) {
		this.projectModule = projectModule;
	}

	public String getDeployTo() {
		return deployTo;
	}

	public void setDeployTo(String deployTo) {
		this.deployTo = deployTo;
	}

	public String getSkipTest() {
		return skipTest;
	}

	public void setSkipTest(String skipTest) {
		this.skipTest = skipTest;
	}

	public String getShowDebug() {
		return showDebug;
	}

	public void setShowDebug(String showDebug) {
		this.showDebug = showDebug;
	}

	public String getUserBuildName() {
		return userBuildName;
	}

	public void setUserBuildName(String userBuildName) {
		this.userBuildName = userBuildName;
	}

	public String getUserBuildNumber() {
		return userBuildNumber;
	}

	public void setUserBuildNumber(String userBuildNumber) {
		this.userBuildNumber = userBuildNumber;
	}

	public String getMainClassName() {
		return mainClassName;
	}

	public void setMainClassName(String mainClassName) {
		this.mainClassName = mainClassName;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;

	}

	public String getKeystore() {
		return keystore;
	}

	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}

	public String getStorepass() {
		return storepass;
	}

	public void setStorepass(String storepass) {
		this.storepass = storepass;
	}

	public String getKeypass() {
		return keypass;
	}

	public void setKeypass(String keypass) {
		this.keypass = keypass;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isProfileCreationStatus() {
		return profileCreationStatus;
	}

	public void setProfileCreationStatus(boolean profileCreationStatus) {
		this.profileCreationStatus = profileCreationStatus;
	}

	public String getProfileCreationMessage() {
		return profileCreationMessage;
	}

	public void setProfileCreationMessage(String profileCreationMessage) {
		this.profileCreationMessage = profileCreationMessage;
	}

	public String getProfileAvailable() {
		return profileAvailable;
	}

	public void setProfileAvailable(String profileAvailable) {
		this.profileAvailable = profileAvailable;
	}

	public String getSigning() {
		return signing;
	}

	public void setSigning(String signing) {
		this.signing = signing;
	}

	public List<String> getSqlFiles() {
		return sqlFiles;
	}

	public void setSqlFiles(List<String> sqlFiles) {
		this.sqlFiles = sqlFiles;
	}

	public List<String> getDatabases() {
		return databases;
	}

	public void setDatabases(List<String> databases) {
		this.databases = databases;
	}

	public String getDbWithSqlFiles() {
		return DbWithSqlFiles;
	}

	public void setDbWithSqlFiles(String dbWithSqlFiles) {
		DbWithSqlFiles = dbWithSqlFiles;
	}

}