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
import java.io.File;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.photon.phresco.commons.CIJob;
import com.photon.phresco.commons.CIJobStatus;
import com.photon.phresco.commons.CIPasswordScrambler;
import com.photon.phresco.commons.FrameworkConstants;
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
import com.photon.phresco.model.CIBuild;
import com.photon.phresco.util.AndroidConstants;
import com.photon.phresco.util.IosSdkUtil;
import com.photon.phresco.util.IosSdkUtil.MacSdkType;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.photon.phresco.util.XCodeConstants;

public class CI extends FrameworkBaseAction implements FrameworkConstants {

	private static final String FIRST_BUILD = "1";
	private static final long serialVersionUID = -2040671011555139339L;
	private static final Logger S_LOGGER = Logger.getLogger(CI.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();

	private String name = null;
	private String svnurl = null;
	private String username = null;
	private String password = null;
	private String[] emails = null;
	private String successEmailIds = null;
	private String failureEmailIds = null;
	private String schedule = null;
	private String cronExpression = null;
	private String projectCode = null;
	private String showSettings = null;
	private List<String> serverSettings = null;
	private List<String> dbSettings = null;
	private List<String> websrvcSettings = null;
	private List<String> emailSettings = null;
	private String database = null;
	private String server = null;
	private String email = null;
	private String webservice = null;
	private String buildDownloadUrl = null;
	private InputStream fileInputStream;
	private String fileName = "";
	private String senderEmailId = null;
	private String senderEmailPassword = null;
	private int totalBuildSize;
	boolean buildInProgress = false;

	private String environment = null;
	private String sdk = null;
	private String mode = null;
	private String androidVersion = null;
	private String signing = null;

	private String target = "";
	private String proguard = null;
	private List<String> triggers = null;
	private String buildNumber = null;
	CIJob job = null;
	private String oldJobName = null;
	private int numberOfJobsInProgress = 0;
	private String downloadJobName = null;
	private String svnType = null;
	private String branch = null;
	private String localJenkinsAlive = "";

	// collabNet implementation
	private boolean enableBuildRelease = false;
	private String collabNetURL = "";
	private String collabNetusername = "";
	private String collabNetpassword = "";
	private String collabNetProject = "";
	private String collabNetPackage = "";
	private String collabNetRelease = "";
	private boolean collabNetoverWriteFiles = false;

	// Test automation in jenkins
	private String usedClonnedWorkspace = "";
	private String operation = "";
	private String upstreamProject = "";
	private String downstreamProject = "";
	// whether we want to clone this workspace
	private boolean cloneWorkspace = false;

	// Extra build, deplot and test info
	private String importSql = "";
	private String deployTo = "";
	private String simulatorVersion = "";
	private String serialNumber = "";
	private String browser = "";
	private String device = "";

	// java Standalone tech info
	private String jarName = "";
	private String mainClassName = "";

	public String ci() {
		S_LOGGER.debug("Entering Method CI.ci()");
		try {
			boolean jenkinsAlive = false;
			// Other methods like build() will call this after triggered build,
			// it will set trigger_from_ui=true, we need to set value when this
			// variable is empty
			// UI didnt trigger anybuild from here
			String buildTriggeredVal = (String) getHttpRequest().getAttribute(
					CI_BUILD_TRIGGERED_FROM_UI);
			S_LOGGER.debug("buildTriggeredVal " + buildTriggeredVal);
			if (StringUtils.isEmpty(buildTriggeredVal)) {
				S_LOGGER.debug("Build triggered set to false ");
				getHttpRequest()
						.setAttribute(CI_BUILD_TRIGGERED_FROM_UI, FALSE);
			}
			S_LOGGER.debug("buildTriggeredVal in Ci "
					+ getHttpRequest().getAttribute(CI_BUILD_TRIGGERED_FROM_UI));
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = (Project) administrator.getProject(projectCode);
			getHttpRequest().setAttribute(REQ_PROJECT, project);
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
			getHttpRequest().setAttribute(CI_JENKINS_ALIVE, jenkinsAlive);

			jenkinsAlive = DiagnoseUtil.isConnectionAlive(HTTP_PROTOCOL,
					LOCALHOST,
					Integer.parseInt(getPortNo(Utility.getJenkinsHome())));
			S_LOGGER.debug("jenkins Alive " + jenkinsAlive);
			getHttpRequest().setAttribute(CI_JENKINS_ALIVE, jenkinsAlive);
			S_LOGGER.debug("get jobs called in CI");
			List<CIJob> existingJobs = administrator.getJobs(project);
			S_LOGGER.debug("Return jobs got in CI!!!!!!!!!");
			Map<String, List<CIBuild>> ciJobsAndBuilds = new HashMap<String, List<CIBuild>>();
			if (existingJobs != null) {
				for (CIJob ciJob : existingJobs) {
					boolean buildJenkinsAlive = false;
					boolean isJobCreatingBuild = false;
					int noOfJobsIsInProgress = 0;
					List<CIBuild> builds = null;
					buildInProgress = false;
					buildJenkinsAlive = DiagnoseUtil.isConnectionAlive(
							HTTP_PROTOCOL, ciJob.getJenkinsUrl(),
							Integer.parseInt(ciJob.getJenkinsPort()));
					isJobCreatingBuild = administrator
							.isJobCreatingBuild(ciJob);
					S_LOGGER.debug("ciJob.getName() ====> " + ciJob.getName());
					S_LOGGER.debug("ciJob.getName() alive  ====> "
							+ buildJenkinsAlive);
					S_LOGGER.debug("ciJob.getName() isJobCreatingBuild  ====> "
							+ isJobCreatingBuild);
					getHttpRequest().setAttribute(
							CI_BUILD_JENKINS_ALIVE + ciJob.getName(),
							buildJenkinsAlive);
					getHttpRequest().setAttribute(
							CI_BUILD_IS_IN_PROGRESS + ciJob.getName(),
							isJobCreatingBuild);
					if (buildJenkinsAlive == true) {
						builds = administrator.getBuilds(ciJob);
					}
					S_LOGGER.debug("ciJob.getName() builds ====> " + builds);
					ciJobsAndBuilds.put(ciJob.getName(), builds);
				}
			}
			getHttpRequest().setAttribute(REQ_EXISTING_JOBS, ciJobsAndBuilds);
			numberOfJobsIsInProgress();
			S_LOGGER.debug("numberOfJobsInProgress " + numberOfJobsInProgress);
			getHttpRequest().setAttribute(CI_NO_OF_JOBS_IN_PROGRESS,
					numberOfJobsInProgress);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.ci()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return APP_CI;
	}

	public String configure() {
		S_LOGGER.debug("Entering Method  CI.configure()");
		try {
			String[] selectedJobsName = getHttpRequest().getParameterValues(
					REQ_SELECTED_JOBS_LIST);
			String jobName = "";
			if (!ArrayUtils.isEmpty(selectedJobsName)) {
				jobName = selectedJobsName[0];
			}
			S_LOGGER.debug("selectedJobs for configuration " + jobName);

			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			// Get environment info
			List<Environment> environments = administrator
					.getEnvironments(project);
			getHttpRequest().setAttribute(REQ_ENVIRONMENTS, environments);
			getHttpRequest().setAttribute(REQ_PROJECT, project);
			// Get xcode targets
			String technology = project.getProjectInfo().getTechnology()
					.getId();
			if (TechnologyTypes.IPHONES.contains(technology)) {
				List<PBXNativeTarget> xcodeConfigs = ApplicationsUtil
						.getXcodeConfiguration(projectCode);
				getHttpRequest().setAttribute(REQ_XCODE_CONFIGS, xcodeConfigs);
				// get list of sdks
				List<String> iphoneSdks = IosSdkUtil
						.getMacSdks(MacSdkType.iphoneos);
				iphoneSdks.addAll(IosSdkUtil
						.getMacSdks(MacSdkType.iphonesimulator));
				iphoneSdks.addAll(IosSdkUtil.getMacSdks(MacSdkType.macosx));
				getHttpRequest().setAttribute(REQ_IPHONE_SDKS, iphoneSdks);
				// get list of sdk versions
				List<String> iphoneSimulatorSdks = IosSdkUtil
						.getMacSdksVersions(MacSdkType.iphonesimulator);
				getHttpRequest().setAttribute(REQ_IPHONE_SIMULATOR_SDKS,
						iphoneSimulatorSdks);
			}
			// get functional test browsers
			Quality qty = new Quality();
			qty.getFunctionalTestBrowsers(technology);
			List<String> clonedWorkspaces = null;
			List<String> existingJobsNames = null;
			CIJob existJob = administrator.getJob(project, jobName);
			List<CIJob> existJobs = administrator.getJobs(project);
			if (CollectionUtils.isNotEmpty(existJobs)) {
				clonedWorkspaces = new ArrayList<String>(existJobs.size());
				existingJobsNames = new ArrayList<String>(existJobs.size());
				for (CIJob ciJob : existJobs) {
					S_LOGGER.debug("Exist jobs size ... " + existJobs.size());
					if (ciJob.isCloneWorkspace()) {
						S_LOGGER.debug("Cloned names .... " + ciJob.getName());
						clonedWorkspaces.add(ciJob.getName());
					}
					S_LOGGER.debug("existJob names in code .... "
							+ existJob);
					existingJobsNames.add(ciJob.getName());
				}
			}
			if (existJob != null
					&& StringUtils.isNotEmpty(existJob.getCollabNetpassword())) {
				existJob.setCollabNetpassword(CIPasswordScrambler.unmask(existJob.getCollabNetpassword()));
			}
			if(existJob != null) {
				existJob.setPassword(CIPasswordScrambler.unmask(existJob.getPassword()));
			}
			getHttpRequest().setAttribute(REQ_EXISTING_JOB, existJob);
			getHttpRequest().setAttribute(REQ_EXISTING_JOBS_NAMES,
					existingJobsNames);
			getHttpRequest().setAttribute(REQ_EXISTING_CLONNED_JOBS,
					clonedWorkspaces);
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.configure()"
					+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "CI configuration clicked");
		}
		return APP_CI_CONFIGURE;
	}

	public String setup() {
		S_LOGGER.debug("Entering Method  CI.setup()");
		try {
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory
					.getProjectRuntimeManager();
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			ActionType actionType = ActionType.JENKINS_SETUP;
			actionType.setWorkingDirectory(Utility.getJenkinsHome());
			if (debugEnabled) {
				S_LOGGER.debug("Jenkins Home "
						+ Utility.getJenkinsHome().toString());
			}

			// Here we have to place two files in jenkins_home environment
			// variable location
			administrator.getJdkHomeXml();
			administrator.getMavenHomeXml();
			// place email ext plugin in plugin folder
			administrator.getEmailExtPlugin();
			BufferedReader reader = runtimeManager.performAction(project,
					actionType, null, null);
			getHttpSession().setAttribute(projectCode + CI_SETUP, reader);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, CI_SETUP);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.setup()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return APP_ENVIRONMENT_READER;
	}

	public String save() {
		S_LOGGER.debug("clonedWorkspace ... " + svnType);
		S_LOGGER.debug("usedClonnedWorkspace " + usedClonnedWorkspace);
		S_LOGGER.debug("operation " + operation);
		S_LOGGER.debug("upstreamProject " + upstreamProject);
		S_LOGGER.debug("downstreamProject  " + downstreamProject);
		S_LOGGER.debug("cloneWorkspace " + cloneWorkspace);
		return doUpdateSave(CI_CREATE_JOB_COMMAND);
	}

	public String update() {
		S_LOGGER.debug("Entering Method  CI.update()");
		return doUpdateSave(CI_UPDATE_JOB_COMMAND);
	}

	public String doUpdateSave(String jobType) {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  CI.doUpdateSave()");
		}
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			String technology = project.getProjectInfo().getTechnology()
					.getId();
			CIJob existJob = null;
			if (StringUtils.isNotEmpty(oldJobName)) {
				existJob = administrator.getJob(project, oldJobName);
			}
			if (existJob == null) {
				existJob = new CIJob();
			}
			InetAddress thisIp = InetAddress.getLocalHost();
			existJob.setName(name);
			existJob.setSvnUrl(svnurl);
			existJob.setUserName(username);
			existJob.setPassword(CIPasswordScrambler.mask(password));
			existJob.setJenkinsUrl(thisIp.getHostAddress());
			existJob.setJenkinsPort(getPortNo(Utility.getJenkinsHome()));
			existJob.setTriggers(triggers);
			Map<String, String> emails = new HashMap<String, String>(2);
			emails.put(REQ_KEY_SUCCESS_EMAILS, successEmailIds);
			emails.put(REQ_KEY_FAILURE_EMAILS, failureEmailIds);

			String mvncmd = "";
			if (BUILD.equals(operation)) {
				S_LOGGER.debug("Build operation!!!!!!");
				mvncmd = getBuildCommand(project, technology);
			} else if (DEPLOY.equals(operation)) {
				S_LOGGER.debug("Deploy operation!!!!!!");
				mvncmd = getDeployCommand(project, technology);
			} else if (FUNCTIONAL_TEST.equals(operation)) {
				S_LOGGER.debug("Functional test operation!!!!!");
				mvncmd = getFunctionalTestCommand(project, technology);
				if (!(TechnologyTypes.IPHONES.contains(technology) || TechnologyTypes.ANDROIDS
						.contains(technology))) {
					mvncmd = CI_FUNCTIONAL_ADAPT + mvncmd;
				}
			}
			if (!"clonedWorkspace".equals(svnType) && BUILD.equals(operation)) {
				mvncmd = CI_PROFILE + mvncmd;
			}
			S_LOGGER.debug("mvn command" + mvncmd);
			existJob.setMvnCommand(mvncmd);
			existJob.setEmail(emails);
			existJob.setScheduleType(schedule);
			existJob.setScheduleExpression(cronExpression);
			existJob.setSenderEmailId(senderEmailId);
			existJob.setSenderEmailPassword(senderEmailPassword);
			existJob.setBranch(branch);
			existJob.setRepoType(svnType);
			// collabNet file release plugin imple
			existJob.setEnableBuildRelease(enableBuildRelease);
			existJob.setCollabNetURL(collabNetURL);
			existJob.setCollabNetusername(collabNetusername);
			existJob.setCollabNetpassword(CIPasswordScrambler.mask(collabNetpassword));
			existJob.setCollabNetProject(collabNetProject);
			existJob.setCollabNetPackage(collabNetPackage);
			existJob.setCollabNetRelease(collabNetRelease);
			existJob.setCollabNetoverWriteFiles(collabNetoverWriteFiles);

			// Automate values
			existJob.setCloneWorkspace(cloneWorkspace);
			existJob.setUsedClonnedWorkspace(usedClonnedWorkspace);
			existJob.setDownStreamProject(downstreamProject);
			existJob.setOperation(operation);

			// extra info
			S_LOGGER.debug("deployTo =====> " + deployTo);
			S_LOGGER.debug("simulatorVersion =====> " + simulatorVersion);
			S_LOGGER.debug("serialNumber =====> " + serialNumber);
			S_LOGGER.debug("browser =====> " + browser);
			existJob.setImportSql(importSql);
			existJob.setBrowser(browser);
			existJob.setEnvironment(environment);

			String funcitonalTestDir = "";
			if (FUNCTIONAL_TEST.equals(operation)) {
				FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
				funcitonalTestDir = frameworkUtil
						.getFuncitonalTestDir(technology);
				if (StringUtils.isNotEmpty(funcitonalTestDir)) {
					// removing / value , which not valid
					funcitonalTestDir = funcitonalTestDir.substring(1)
							+ File.separator;
				}
			}
			S_LOGGER.debug("funcitonalTestDir ======> " + funcitonalTestDir);
			if (TechnologyTypes.IPHONES.contains(technology)) {
				// iphone specific info
				existJob.setDeployTo(deployTo);
				existJob.setSimulatorVersion(simulatorVersion);
				existJob.setTarget(target);
				existJob.setIphoneSdk(sdk);
				existJob.setMode(mode);
				existJob.setIphoneSerialNumber(serialNumber);
			} else if (TechnologyTypes.ANDROIDS.contains(technology)) {
				existJob.setAndroidVersion(androidVersion);
				existJob.setDevice(device);
				existJob.setAndroidSerialNumber(serialNumber);
				existJob.setProguard(proguard);
				existJob.setSigning(signing);
				existJob.setAndroidSerialNumber(serialNumber);
			} else if (TechnologyTypes.JAVA_STANDALONE.contains(technology)) {
				existJob.setJarName(jarName);
				existJob.setMainClassName(mainClassName);
				existJob.setEnablePostBuildStep(true);
			}

			existJob.setPomLocation(funcitonalTestDir + POM_XML);

			if (CI_CREATE_JOB_COMMAND.equals(jobType)) {
				administrator.createJob(project, existJob);
				addActionMessage(getText(SUCCESS_JOB));
			} else if (CI_UPDATE_JOB_COMMAND.equals(jobType)) {
				administrator.updateJob(project, existJob);
				addActionMessage(getText(SUCCESS_UPDATE));
			}

			restartJenkins(); // TODO: reload config

			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.doUpdateSave()"
					+ FrameworkUtil.getStackTraceAsString(e));
			addActionMessage(getText(CI_SAVE_UPDATE_FAILED,
					e.getLocalizedMessage()));
		}

		return ci();

	}

	private String getBuildCommand(Project project, String technology)
			throws PhrescoException {
		Map<String, String> settingsInfoMap = new HashMap<String, String>(2);
		ActionType actionType = null;
		// For web technologies
		if (StringUtils.isNotEmpty(environment)) {
			settingsInfoMap.put(ENVIRONMENT_NAME, environment);
		}

		// For android technologies
		if (StringUtils.isNotEmpty(androidVersion)) {
			settingsInfoMap.put(AndroidConstants.ANDROID_VERSION_MVN_PARAM,
					androidVersion);
		}

		settingsInfoMap.put(BUILD_NAME, CI_BUILD_NAME);
		settingsInfoMap.put(BUILD_NUMBER, FIRST_BUILD);
		
		// For iphone technoloies
		if (TechnologyTypes.IPHONES.contains(technology)) {
			settingsInfoMap.put(IPHONE_SDK, sdk);
			settingsInfoMap.put(IPHONE_CONFIGURATION, mode);
			settingsInfoMap.put(IPHONE_TARGET_NAME, target);
			if (TechnologyTypes.IPHONE_HYBRID.equals(technology)) {
				settingsInfoMap.put(IPHONE_PLISTFILE,
						XCodeConstants.HYBRID_PLIST);
				settingsInfoMap.put(ENCRYPT, FALSE);
			} else if (TechnologyTypes.IPHONE_NATIVE.equals(technology)) {
				settingsInfoMap.put(IPHONE_PLISTFILE,
						XCodeConstants.NATIVE_PLIST);
				settingsInfoMap.put(ENCRYPT, TRUE);
			}
		}

		// For java stanalone
		if (TechnologyTypes.JAVA_STANDALONE.contains(technology)) {
			settingsInfoMap.put(JAR_NAME, jarName);
			settingsInfoMap.put(MAIN_CLASS_NAME, mainClassName);
		}
		// For mobile technologies
		if (TechnologyTypes.ANDROIDS.contains(technology)) {
			if (StringUtils.isEmpty(proguard)) {
				// if the checkbox is selected value should be set to false
				// otherwise true
				proguard = TRUE;
			}
			settingsInfoMap.put(ANDROID_PROGUARD_SKIP, proguard);
			actionType = ActionType.MOBILE_COMMON_COMMAND;
			if (StringUtils.isNotEmpty(signing)) {
				actionType.setProfileId(PROFILE_ID);
			}
		} else if (TechnologyTypes.IPHONES.contains(technology)) {
			actionType = ActionType.IPHONE_BUILD_UNIT_TEST;
		} else {
			actionType = ActionType.BUILD;
		}
		return getMVNCommand(project, settingsInfoMap, actionType);
	}

	private String getDeployCommand(Project project, String technology)
			throws PhrescoException {
		Map<String, String> valuesMap = new HashMap<String, String>(2);
		ActionType actionType = null;
		String techId = project.getProjectInfo().getTechnology().getId();

		if (TechnologyTypes.IPHONES.contains(techId)) {
			valuesMap.put(TRIGGER_SIMULATOR, FALSE);
			valuesMap.put(BUILD_NAME, CI_BUILD_NAME + IPHONE_FORMAT);
			valuesMap.put(BUILD_NUMBER, FIRST_BUILD);
			// if deploy to device is selected we have to pass device deploy
			// param as additional param
			if (StringUtils.isNotEmpty(deployTo)
					&& deployTo.equals(REQ_IPHONE_SIMULATOR)) {
				valuesMap.put(IPHONE_SIMULATOR_VERSION, simulatorVersion);
			} else {
				valuesMap.put(DEVICE_DEPLOY, TRUE);
			}
		} else if (TechnologyTypes.ANDROIDS.contains(techId)) {
			valuesMap.put(BUILD_NAME, CI_BUILD_NAME + ANDROID_FORMAT);
			valuesMap.put(BUILD_NUMBER, FIRST_BUILD);
		} else {
			valuesMap.put(BUILD_NAME, CI_BUILD_NAME + ARCHIVE_FORMAT);
			valuesMap.put(BUILD_NUMBER, FIRST_BUILD);
		}

		if (!(TechnologyTypes.IPHONES.contains(techId)
				|| TechnologyTypes.ANDROIDS.contains(techId) || TechnologyTypes.SHAREPOINT
					.equals(techId)) && StringUtils.isNotEmpty(importSql)) {
			// valuesMap.put(DEPLOY_IMPORT_SQL, importSql);
		}

		if (StringUtils.isNotEmpty(environment)
				&& !(TechnologyTypes.IPHONES.contains(techId))
				&& !(TechnologyTypes.ANDROIDS.contains(techId))) {
			valuesMap.put(ENVIRONMENT_NAME, environment);
		}

		if (TechnologyTypes.ANDROIDS.contains(technology)) {
			String device = getHttpRequest().getParameter(REQ_ANDROID_DEVICE);
			if (device.equals(SERIAL_NUMBER)) {
				device = serialNumber;
			}
			if (debugEnabled) {
				S_LOGGER.debug("To be deployed Android device name" + device);
			}
			valuesMap.put(DEPLOY_ANDROID_DEVICE_MODE, device);
			valuesMap.put(DEPLOY_ANDROID_EMULATOR_AVD, REQ_ANDROID_DEFAULT);
		}
		actionType = ActionType.DEPLOY;

		return getMVNCommand(project, valuesMap, actionType);
	}

	private String getMVNCommand(Project project,
			Map<String, String> valuesMap, ActionType actionType)
			throws PhrescoException {
		ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory
				.getProjectRuntimeManager();
		String mvncmd = "";
		StringBuilder command = actionType.getCommand();
		StringBuilder buildMavenCommand = new StringBuilder();
		if (command == null) {
			buildMavenCommand = runtimeManager.buildMavenCommand(project,
					actionType, valuesMap);
		} else {
			buildMavenCommand.append(command);
			buildMavenCommand.append(SPACE);
			buildMavenCommand.append(runtimeManager.buildMavenArgCommand(
					actionType, valuesMap));
		}
		mvncmd = buildMavenCommand.toString().substring(4).trim();
		return mvncmd;
	}

	private String getFunctionalTestCommand(Project project, String technology)
			throws PhrescoException {
		ActionType actionType = null;
		Map<String, String> settingsInfoMap = new HashMap<String, String>(2);
		if (TechnologyTypes.ANDROIDS.contains(technology)) {
			String device = getHttpRequest().getParameter(REQ_ANDROID_DEVICE);
			if (device.equals(SERIAL_NUMBER)) {
				device = serialNumber;
			}
			S_LOGGER.debug("Android device name " + device);
			settingsInfoMap.put(DEPLOY_ANDROID_DEVICE_MODE, device);
			settingsInfoMap.put(DEPLOY_ANDROID_EMULATOR_AVD,
					REQ_ANDROID_DEFAULT);
			actionType = ActionType.ANDROID_TEST_COMMAND;
		} else if (TechnologyTypes.IPHONES.equals(technology)) {
			settingsInfoMap.put(BUILD_NAME, CI_BUILD_NAME + IPHONE_FORMAT);
			settingsInfoMap.put(BUILD_NUMBER, FIRST_BUILD);
			actionType = ActionType.IPHONE_FUNCTIONAL_COMMAND;
		} else if (TechnologyTypes.IPHONE_HYBRID.equals(technology)) {
			settingsInfoMap = null;
			actionType = ActionType.TEST;
		} else {
			// settingsInfoMap.put(TEST, TEST_PARAM_VALUE);
			if (TechnologyTypes.SHAREPOINT.equals(project.getProjectInfo()
					.getTechnology().getId())
					|| TechnologyTypes.DOT_NET.equals(project.getProjectInfo()
							.getTechnology().getId())) {
				actionType = ActionType.SHAREPOINT_NUNIT_TEST;
			} else {
				actionType = ActionType.TEST;
			}
		}
		if (!TechnologyTypes.ANDROIDS.contains(technology)
				&& !TechnologyTypes.IPHONES.contains(technology)
				&& !TechnologyTypes.BLACKBERRY_HYBRID.equals(technology)
				&& !TechnologyTypes.SHAREPOINT.equals(technology)
				&& !TechnologyTypes.DOT_NET.equals(technology)
				&& !TechnologyTypes.JAVA_STANDALONE.equals(technology)) {
			settingsInfoMap.put(ENVIRONMENT_NAME, environment);
			settingsInfoMap.put(BROWSER, browser);
		}
		return getMVNCommand(project, settingsInfoMap, actionType);
	}

	public String build() {
		S_LOGGER.debug("Entering Method  CI.build()");
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			String[] selectedJobs = getHttpRequest().getParameterValues(
					REQ_SELECTED_JOBS_LIST);

			CIJobStatus ciJobStatus = administrator.buildJobs(project,
					Arrays.asList(selectedJobs));
			if (ciJobStatus.getCode() == JOB_STATUS_NOTOK) {
				S_LOGGER.debug("Jenkins build job status code "
						+ ciJobStatus.getCode());
				addActionError(getText(ciJobStatus.getMessage()));
			} else {
				S_LOGGER.debug("Jenkins build job status code "
						+ ciJobStatus.getCode());
				addActionMessage(getText(ciJobStatus.getMessage()));
			}
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
			getHttpRequest().setAttribute(CI_BUILD_TRIGGERED_FROM_UI, TRUE);
		} catch (Exception e) {
			getHttpRequest().setAttribute(CI_BUILD_TRIGGERED_FROM_UI, FALSE);
			S_LOGGER.error("Entered into catch block of CI.build()"
					+ FrameworkUtil.getStackTraceAsString(e));
			addActionMessage(getText(CI_BUILD_FAILED, e.getLocalizedMessage()));
		}
		return ci();
	}

	public String getTotalBuilds() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  CI.getTotalBuilds()");
		}
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			totalBuildSize = administrator.getTotalBuilds(project); // when
																	// getting
																	// all the
																	// builds ,
																	// it ll try
																	// to get
																	// all build
																	// status,
																	// so it ll
																	// return
																	// -1.
		} catch (Exception e) {
			totalBuildSize = -1;
		}
		return "success";
	}

	public String deleteCIBuild() {
		S_LOGGER.debug("Entering Method  CI.deleteCIBuild()");
		String[] selectedBuilds = getHttpRequest().getParameterValues(
				REQ_SELECTED_BUILDS_LIST);
		S_LOGGER.debug("selectedBuilds " + selectedBuilds.toString());
		// job name and builds
		Map<String, List<String>> buildsTobeDeleted = new HashMap<String, List<String>>();
		for (String build : selectedBuilds) {
			S_LOGGER.debug("Build" + build);
			String[] split = build.split(",");
			S_LOGGER.debug("split " + split[0]);
			List<String> buildNumbers = new ArrayList<String>();
			if (buildsTobeDeleted.containsKey(split[0])) {
				List<String> listBuildNos = buildsTobeDeleted.get(split[0]);
				listBuildNos.add(split[1]);
				buildsTobeDeleted.put(split[0], listBuildNos);
			} else {
				buildNumbers.add(split[1]);
				buildsTobeDeleted.put(split[0], buildNumbers);
			}

		}

		Iterator iterator = buildsTobeDeleted.keySet().iterator();
		while (iterator.hasNext()) {
			String jobName = iterator.next().toString();
			List<String> versions = buildsTobeDeleted.get(jobName);
			S_LOGGER.debug("jobName " + jobName + " builds " + versions);
		}
		try {
			ProjectAdministrator administrator = getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			CIJobStatus ciJobStatus = administrator.deleteCIBuild(project,
					buildsTobeDeleted);
			if (ciJobStatus.getCode() == JOB_STATUS_NOTOK) {
				S_LOGGER.debug("Jenkins delete build status code "
						+ ciJobStatus.getCode());
				addActionError(getText(ciJobStatus.getMessage()));
			} else {
				S_LOGGER.debug("Jenkins delete build status code "
						+ ciJobStatus.getCode());
				addActionMessage(getText(ciJobStatus.getMessage()));
			}
			getHttpRequest().setAttribute("project", project);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.deleteCIBuild()"
					+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Build delete");
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return ci();
	}

	public String deleteCIJob() {
		S_LOGGER.debug("Entering Method  CI.deleteCIJob()");
		String[] selectedJobs = getHttpRequest().getParameterValues(
				REQ_SELECTED_JOBS_LIST);
		S_LOGGER.debug("delete selectedJobs " + selectedJobs);
		try {
			ProjectAdministrator administrator = getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			CIJobStatus ciJobStatus = administrator.deleteCIJobs(project,
					Arrays.asList(selectedJobs));
			if (ciJobStatus.getCode() == JOB_STATUS_NOTOK) {
				S_LOGGER.debug("Jenkins delete job status code "
						+ ciJobStatus.getCode());
				addActionError(getText(ciJobStatus.getMessage()));
			} else {
				S_LOGGER.debug("Jenkins delete job status code "
						+ ciJobStatus.getCode());
				addActionMessage(getText(ciJobStatus.getMessage()));
			}
			getHttpRequest().setAttribute("project", project);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.deleteCIJob()"
					+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Job delete");
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		return ci();
	}

	public String startJenkins() {
		S_LOGGER.debug("Entering Method  CI.startJenkins()");
		try {
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory
					.getProjectRuntimeManager();
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			ActionType actionType = ActionType.JENKINS_START;
			actionType.setWorkingDirectory(Utility.getJenkinsHome());
			S_LOGGER.debug("Jenkins Home "
					+ Utility.getJenkinsHome().toString());
			BufferedReader reader = runtimeManager.performAction(project,
					actionType, null, null);

			getHttpSession().setAttribute(projectCode + CI_START, reader);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, CI_START);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.startJenkins()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return APP_ENVIRONMENT_READER;
	}

	public String stopJenkins() {
		S_LOGGER.debug("Entering Method  CI.stopJenkins()");
		try {
			ProjectRuntimeManager runtimeManager = PhrescoFrameworkFactory
					.getProjectRuntimeManager();
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			ActionType actionType = ActionType.JENKINS_STOP;
			actionType.setWorkingDirectory(Utility.getJenkinsHome());
			S_LOGGER.debug("Jenkins Home "
					+ Utility.getJenkinsHome().toString());
			BufferedReader reader = runtimeManager.performAction(project,
					actionType, null, null);

			getHttpSession().setAttribute(projectCode + CI_STOP, reader);
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
			getHttpRequest().setAttribute(REQ_TEST_TYPE, CI_STOP);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.stopJenkins()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return APP_ENVIRONMENT_READER;
	}

	public String restartJenkins() {
		S_LOGGER.debug("Entering Method  CI.restartJenkins()");
		try {
			stopJenkins();
			S_LOGGER.debug("stopJenkins method called");
			BufferedReader reader = (BufferedReader) getHttpSession()
					.getAttribute(projectCode + CI_STOP);
			String line;
			line = reader.readLine();
			while (!line.startsWith("[INFO] BUILD SUCCESS")) {
				line = reader.readLine();
				if (debugEnabled) {
					S_LOGGER.debug("Restart Stop Console : " + line);
				}
			}
			waitForTime(5);

			startJenkins();
			if (debugEnabled) {
				S_LOGGER.debug("startJenkins method called");
			}

			BufferedReader reader1 = (BufferedReader) getHttpSession()
					.getAttribute(projectCode + CI_START);
			String line1;
			line1 = reader1.readLine();
			while (!line1.startsWith("[INFO] BUILD SUCCESS")) {
				line1 = reader1.readLine();
				if (debugEnabled) {
					S_LOGGER.debug("Restart Start Console : " + line);
				}
			}
			waitForTime(2);

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.restartJenkins()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
		}

		return "restarted";
	}

	public void waitForTime(int waitSec) {
		S_LOGGER.debug("waitForTime : " + waitSec);
		long startTime = 0;
		startTime = new Date().getTime();
		while (new Date().getTime() < startTime + waitSec * 1000) {
			// Dont do anything for some seconds. It waits till the log is
			// written to file
		}
		S_LOGGER.debug("Return from waitForTime ");
	}

	public String cronValidation() {
		S_LOGGER.debug("Entering Method  CI.cronValidation()");
		try {
			HttpServletRequest request = getHttpRequest();
			String cronBy = request.getParameter(REQ_CRON_BY);
			String jobName = "Job Name";
			String cronExpression = "";
			Date[] dates = null;

			if (REQ_CRON_BY_DAILY.equals(cronBy)) {
				String hours = request.getParameter(REQ_HOURS);
				String minutes = request.getParameter(REQ_MINUTES);
				String every = request.getParameter(REQ_SCHEDULE_EVERY);

				if ("false".equals(every)) {
					if ("*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 * * * * ?";
					} else if ("*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " 0 * * ?";
					} else if (!"*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 0 " + hours + " * * ?";
					} else if (!"*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " " + hours
								+ " * * ?";
					}
				} else {
					if ("*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 * * * * ?";
					} else if ("*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + "*/" + minutes + " * * * ?"; // 0
																				// replace
																				// with
																				// *
					} else if (!"*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 0 " + "*/" + hours + " * * ?"; // 0
																			// replace
																			// with
																			// *
					} else if (!"*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " */" + hours
								+ " * * ?"; // 0 replace with *
					}
				}
				dates = testCronExpression(cronExpression);

			} else if (REQ_CRON_BY_WEEKLY.equals(cronBy)) {
				String hours = request.getParameter(REQ_HOURS);
				String minutes = request.getParameter(REQ_MINUTES);
				String week = request.getParameter(REQ_CRON_BY_WEEK);
				hours = ("*".equals(hours)) ? "0" : hours;
				minutes = ("*".equals(minutes)) ? "0" : minutes;
				cronExpression = "0 " + minutes + " " + hours + " ? * " + week;
				dates = testCronExpression(cronExpression);

			} else if (REQ_CRON_BY_MONTHLY.equals(cronBy)) {
				String hours = request.getParameter(REQ_HOURS);
				String minutes = request.getParameter(REQ_MINUTES);
				String month = request.getParameter(REQ_MONTH);
				String day = request.getParameter(REQ_DAY);
				hours = ("*".equals(hours)) ? "0" : hours;
				minutes = ("*".equals(minutes)) ? "0" : minutes;
				cronExpression = "0 " + minutes + " " + hours + " " + day + " "
						+ month + " ?";
				dates = testCronExpression(cronExpression);
			}

			if (dates != null) {
				cronExpression = cronExpression.replace('?', '*');
				cronExpression = cronExpression.substring(2);
				request.setAttribute(REQ_CRON_EXPRESSION, cronExpression);
				request.setAttribute(REQ_CRON_DATES, dates);
				request.setAttribute(REQ_JOB_NAME, jobName);
				return "cronValidation";
			}

		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of CI.cronValidation()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			// addActionError(getText("Cron Expression failed to validate"));
		}
		return "cronValidation";
	}

	public Date[] testCronExpression(String expression) throws ParseException {
		Date[] dates = null;
		try {
			S_LOGGER.debug("Entering Method  CI.testCronExpression(String expression)");
			S_LOGGER.debug("testCronExpression() Expression = " + expression);
			final CronExpression cronExpression = new CronExpression(expression);
			final Date nextValidDate1 = cronExpression
					.getNextValidTimeAfter(new Date());
			final Date nextValidDate2 = cronExpression
					.getNextValidTimeAfter(nextValidDate1);
			final Date nextValidDate3 = cronExpression
					.getNextValidTimeAfter(nextValidDate2);
			final Date nextValidDate4 = cronExpression
					.getNextValidTimeAfter(nextValidDate3);
			dates = new Date[] { nextValidDate1, nextValidDate2,
					nextValidDate3, nextValidDate4 };
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.testCronExpression()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return dates;
	}

	public static String getPortNo(String path) throws Exception {
		S_LOGGER.debug("Entering Method CI.getPortNo()");
		String portNo = "";
		try {
			Document document = ApplicationsUtil.getDocument(new File(path
					+ File.separator + "pom.xml"));
			String portNoNode = "/project/build/plugins/plugin/configuration/tomcatHttpPort";
			NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(
					document, portNoNode);
			portNo = nodelist.item(0).getTextContent();
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.getPortNo()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return portNo;
	}

	public String CIBuildDownload() {
		S_LOGGER.debug("Entering Method CI.CIBuildDownload()");
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			CIJob existJob = administrator.getJob(project, downloadJobName);
			// Get it from web path
			URL url = new URL(buildDownloadUrl);
			S_LOGGER.debug("Entering Method CI.CIBuildDownload() buildDownloadUrl "
					+ buildDownloadUrl);
			fileInputStream = url.openStream();
			fileName = existJob.getName();
			return SUCCESS;
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.CIBuildDownload()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return SUCCESS;
	}

	public String buildProgress() {
		S_LOGGER.debug("Entering Method CI.buildProgress()");
		try {
			buildInProgress = false;
			String[] selectedJobs = getHttpRequest().getParameterValues(
					REQ_SELECTED_JOBS_LIST);
			S_LOGGER.debug("build progress jobs monitoring");
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			CIJob existJob = administrator.getJob(project);
			if (existJob != null) {
				boolean buildJenkinsAlive = false;
				buildJenkinsAlive = DiagnoseUtil.isConnectionAlive("http",
						existJob.getJenkinsUrl(),
						Integer.parseInt(existJob.getJenkinsPort()));
				S_LOGGER.debug("Build jenkins alive " + buildJenkinsAlive);
				if (buildJenkinsAlive == true
						&& administrator.getProgressInBuild(project) > 0) {
					buildInProgress = true;
				}
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.buildProgress()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return SUCCESS;
	}

	public String numberOfJobsIsInProgress() {
		S_LOGGER.debug("Entering Method CI.numberOfJobsIsInProgress()");
		try {
			S_LOGGER.debug("numberOfJobsIsInProgress jobs monitoring");
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			List<CIJob> jobs = administrator.getJobs(project);
			if (CollectionUtils.isNotEmpty(jobs)) {
				for (CIJob ciJob : jobs) {
					boolean jobCreatingBuild = administrator
							.isJobCreatingBuild(ciJob);
					if (jobCreatingBuild) {
						numberOfJobsInProgress++;
					}
				}
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of CI.buildProgress()"
					+ FrameworkUtil.getStackTraceAsString(e));
		}
		return SUCCESS;
	}

	public String localJenkinsLocalAlive() {
		S_LOGGER.debug("Entering Method CI.localJenkinsLocalAlive()");
		try {
			URL url = new URL(HTTP_PROTOCOL + PROTOCOL_POSTFIX + LOCALHOST
					+ COLON
					+ Integer.parseInt(getPortNo(Utility.getJenkinsHome()))
					+ FORWARD_SLASH + CI);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int code = httpConnection.getResponseCode();
			localJenkinsAlive = code + "";
			S_LOGGER.debug("localJenkinsAlive => " + localJenkinsAlive);
		} catch (ConnectException e) {
			localJenkinsAlive = "404";
			S_LOGGER.debug("localJenkinsAlive => " + localJenkinsAlive);
		} catch (Exception e) {
			localJenkinsAlive = "404";
			S_LOGGER.debug("localJenkinsAlive => " + localJenkinsAlive);
		}
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSvnurl() {
		return svnurl;
	}

	public void setSvnurl(String svnurl) {
		this.svnurl = svnurl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] email) {
		this.emails = email;
	}

	public String getSuccessEmailIds() {
		return successEmailIds;
	}

	public void setSuccessEmailIds(String successEmailId) {
		this.successEmailIds = successEmailId;
	}

	public String getFailureEmailIds() {
		return failureEmailIds;
	}

	public void setFailureEmailIds(String failureEmailId) {
		this.failureEmailIds = failureEmailId;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getShowSettings() {
		return showSettings;
	}

	public void setShowSettings(String showSettings) {
		this.showSettings = showSettings;
	}

	public List<String> getServerSettings() {
		return serverSettings;
	}

	public void setServerSettings(List<String> serverSettings) {
		this.serverSettings = serverSettings;
	}

	public List<String> getDbSettings() {
		return dbSettings;
	}

	public void setDbSettings(List<String> dbSettings) {
		this.dbSettings = dbSettings;
	}

	public List<String> getWebsrvcSettings() {
		return websrvcSettings;
	}

	public void setWebsrvcSettings(List<String> websrvcSettings) {
		this.websrvcSettings = websrvcSettings;
	}

	public List<String> getEmailSettings() {
		return emailSettings;
	}

	public void setEmailSettings(List<String> emailSettings) {
		this.emailSettings = emailSettings;
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

	public String getBuildDownloadUrl() {
		return buildDownloadUrl;
	}

	public void setBuildDownloadUrl(String buildDownloadUrl) {
		this.buildDownloadUrl = buildDownloadUrl;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSenderEmailId() {
		return senderEmailId;
	}

	public void setSenderEmailId(String senderEmailId) {
		this.senderEmailId = senderEmailId;
	}

	public String getSenderEmailPassword() {
		return senderEmailPassword;
	}

	public void setSenderEmailPassword(String senderEmailPassword) {
		this.senderEmailPassword = senderEmailPassword;
	}

	public int getTotalBuildSize() {
		return totalBuildSize;
	}

	public void setTotalBuildSize(int totalBuildSize) {
		this.totalBuildSize = totalBuildSize;
	}

	public boolean isBuildInProgress() {
		return buildInProgress;
	}

	public void setBuildInProgress(boolean buildInProgress) {
		this.buildInProgress = buildInProgress;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
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

	public List<String> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<String> triggers) {
		this.triggers = triggers;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getOldJobName() {
		return oldJobName;
	}

	public void setOldJobName(String oldJobName) {
		this.oldJobName = oldJobName;
	}

	public int getNumberOfJobsInProgress() {
		return numberOfJobsInProgress;
	}

	public void setNumberOfJobsInProgress(int numberOfJobsInProgress) {
		this.numberOfJobsInProgress = numberOfJobsInProgress;
	}

	public String getDownloadJobName() {
		return downloadJobName;
	}

	public void setDownloadJobName(String downloadJobName) {
		this.downloadJobName = downloadJobName;
	}

	public String getSigning() {
		return signing;
	}

	public void setSigning(String signing) {
		this.signing = signing;
	}

	public String getSvnType() {
		return svnType;
	}

	public void setSvnType(String svnType) {
		this.svnType = svnType;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getLocalJenkinsAlive() {
		return localJenkinsAlive;
	}

	public void setLocalJenkinsAlive(String localJenkinsAlive) {
		this.localJenkinsAlive = localJenkinsAlive;
	}

	public boolean isEnableBuildRelease() {
		return enableBuildRelease;
	}

	public void setEnableBuildRelease(boolean enableBuildRelease) {
		this.enableBuildRelease = enableBuildRelease;
	}

	public String getCollabNetURL() {
		return collabNetURL;
	}

	public void setCollabNetURL(String collabNetURL) {
		this.collabNetURL = collabNetURL;
	}

	public String getCollabNetusername() {
		return collabNetusername;
	}

	public void setCollabNetusername(String collabNetusername) {
		this.collabNetusername = collabNetusername;
	}

	public String getCollabNetpassword() {
		return collabNetpassword;
	}

	public void setCollabNetpassword(String collabNetpassword) {
		this.collabNetpassword = collabNetpassword;
	}

	public String getCollabNetProject() {
		return collabNetProject;
	}

	public void setCollabNetProject(String collabNetProject) {
		this.collabNetProject = collabNetProject;
	}

	public String getCollabNetPackage() {
		return collabNetPackage;
	}

	public void setCollabNetPackage(String collabNetPackage) {
		this.collabNetPackage = collabNetPackage;
	}

	public String getCollabNetRelease() {
		return collabNetRelease;
	}

	public void setCollabNetRelease(String collabNetRelease) {
		this.collabNetRelease = collabNetRelease;
	}

	public boolean isCollabNetoverWriteFiles() {
		return collabNetoverWriteFiles;
	}

	public void setCollabNetoverWriteFiles(boolean collabNetoverWriteFiles) {
		this.collabNetoverWriteFiles = collabNetoverWriteFiles;
	}

	public String getUsedClonnedWorkspace() {
		return usedClonnedWorkspace;
	}

	public void setUsedClonnedWorkspace(String usedClonnedWorkspace) {
		this.usedClonnedWorkspace = usedClonnedWorkspace;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUpstreamProject() {
		return upstreamProject;
	}

	public void setUpstreamProject(String upstreamProject) {
		this.upstreamProject = upstreamProject;
	}

	public String getDownstreamProject() {
		return downstreamProject;
	}

	public void setDownstreamProject(String downstreamProject) {
		this.downstreamProject = downstreamProject;
	}

	public boolean isCloneWorkspace() {
		return cloneWorkspace;
	}

	public void setCloneWorkspace(boolean cloneWorkspace) {
		this.cloneWorkspace = cloneWorkspace;
	}

	public String getImportSql() {
		return importSql;
	}

	public void setImportSql(String importSql) {
		this.importSql = importSql;
	}

	public String getDeployTo() {
		return deployTo;
	}

	public void setDeployTo(String deployTo) {
		this.deployTo = deployTo;
	}

	public String getSimulatorVersion() {
		return simulatorVersion;
	}

	public void setSimulatorVersion(String simulatorVersion) {
		this.simulatorVersion = simulatorVersion;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public String getMainClassName() {
		return mainClassName;
	}

	public void setMainClassName(String mainClassName) {
		this.mainClassName = mainClassName;
	}
}
