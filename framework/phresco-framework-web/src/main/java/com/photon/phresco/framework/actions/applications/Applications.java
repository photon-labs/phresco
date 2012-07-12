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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.SVNAccessor;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.api.ValidationResult;
import com.photon.phresco.framework.commons.ApplicationsUtil;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.framework.impl.ClientHelper;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.model.WebService;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import org.apache.commons.codec.binary.Base64;

public class Applications extends FrameworkBaseAction {
	private static final long serialVersionUID = -4282767788002019870L;

	private static final Logger S_LOGGER = Logger.getLogger(Applications.class);
	private String projectCode = null;
	private String fromPage = null;
	private String repositoryUrl = null;
	private String userName = null;
	private String password = null;
	private String revision = null;
	private String revisionVal = null;
	private String globalValidationStatus = null;
	private List<String> pilotModules = null;
	private List<String> pilotJSLibs = null;
	private String showSettings = null;
	private List<String> settingsEnv = null;
	private List<String> versions = null;
	private String selectedVersions = null;
	private String selectedAttrType = null;
	private String selectedParamName = null;
	private String hiddenFieldValue = null;
	private String divTobeUpdated = null;
	boolean hasError = false;
	private String envError = "";
	private List<String> techVersions = null;
	private boolean hasConfiguration = false;
	private String configServerNames = null;
	private String configDbNames = null;
	private boolean svnImport = false;
	private String svnImportMsg = null;
	List<String> deletableDbs = new ArrayList<String>();
	private String fromTab = null;
	private String fileType = null;
	private String fileorfolder = null;
	//svn info
	private String credential = null;
	
	public String list() {
		long start = System.currentTimeMillis();
		S_LOGGER.debug("Entering Method  Applications.list()");
		try {
			Map<String, Object> sessionMap = ActionContext.getContext()
					.getSession();
			sessionMap.remove(SESSION_SELECTED_INFO);
			sessionMap.remove(SESSION_SELECTED_MODULES);
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
			getHttpSession().removeAttribute(projectCode);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.list()"
					+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Listing projects");
		}
		String discover = discover();
		long end = System.currentTimeMillis();
		S_LOGGER.debug("Total Time : " + (end - start));
		return discover;
	}

	public String applicationDetails() {
		S_LOGGER.debug("Entering Method  Applications.addApplication()");

		getHttpRequest().setAttribute(REQ_FROM_PAGE,
				getHttpRequest().getParameter(REQ_FROM_PAGE));
		if (projectCode != null && !StringUtils.isEmpty(projectCode)) {
			try {
				getHttpSession().removeAttribute(projectCode);
				ProjectAdministrator administrator = PhrescoFrameworkFactory
						.getProjectAdministrator();
				if (projectCode != null) {
					ProjectInfo projectInfo = administrator.getProject(
							projectCode).getProjectInfo();
					S_LOGGER.debug("project info value"+ projectInfo.toString());
					getHttpRequest().setAttribute(REQ_PROJECT_INFO, projectInfo);
				}
				if (StringUtils.isNotEmpty(fromPage)) {
					getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
				}
			} catch (Exception e) {
				S_LOGGER.error("Entered into catch block of Applications.addApplication()"
						+ FrameworkUtil.getStackTraceAsString(e));
				new LogErrorReport(e, REQ_TITLE_ADD_APPLICATION);
			}
		}
		return APP_APPLICATION_DETAILS;
	}

	public String appInfo() {
		S_LOGGER.debug("Entering Method  Applications.add()");

		try {
			if (StringUtils.isNotEmpty(fromPage)) {
				getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
			}
 			FrameworkUtil.setAppInfoDependents(getHttpRequest());
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			ProjectInfo projectInfo = null;
			if (FEATURES.equals(fromTab)) {
				projectInfo = (ProjectInfo) getHttpSession().getAttribute(projectCode);
			}
			if (StringUtils.isNotEmpty(fromPage) && projectInfo == null) {
				projectInfo = administrator.getProject(projectCode).getProjectInfo();
				getHttpSession().setAttribute(projectCode, projectInfo);
			}
			
			getHttpRequest().setAttribute(REQ_TEMP_SELECTED_PILOT_PROJ, getHttpRequest().getParameter(REQ_SELECTED_PILOT_PROJ));
			String[] modules = getHttpRequest().getParameterValues(REQ_SELECTEDMODULES);
			if (modules != null && modules.length > 0) {
				Map<String, String> mapModules = ApplicationsUtil
						.getIdAndVersionAsMap(getHttpRequest(), modules);
				getHttpRequest().setAttribute(REQ_TEMP_SELECTEDMODULES,
						mapModules);
			}

			String[] jsLibs = getHttpRequest().getParameterValues(
					REQ_SELECTED_JSLIBS);
			if (jsLibs != null && jsLibs.length > 0) {
				Map<String, String> mapJsLib = ApplicationsUtil
						.getIdAndVersionAsMap(getHttpRequest(), jsLibs);
				getHttpRequest().setAttribute(REQ_TEMP_SELECTED_JSLIBS,
						mapJsLib);
			}
		} catch (ClientHandlerException e) {
			S_LOGGER.error("Entered into catch block of Applications.appInfo()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, REQ_TITLE_ADD_APPLICATION);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.appInfo()"
						+ FrameworkUtil.getStackTraceAsString(e));
			addActionError(e.getLocalizedMessage());
			new LogErrorReport(e, REQ_TITLE_ADD_APPLICATION);
		}
		getHttpRequest().setAttribute(REQ_CONFIG_SERVER_NAMES, configServerNames);
        getHttpRequest().setAttribute(REQ_CONFIG_DB_NAMES, configDbNames);
        
		return APP_APPINFO;
	}

	public String applicationType() {
		
		S_LOGGER.debug("Entering Method  Applications.applicationType()");
		String appType = getHttpRequest().getParameter(REQ_APPLICATION_TYPE);
		try {
			ApplicationType applicationType = ApplicationsUtil
					.getApplicationType(getHttpRequest(), appType);
			getHttpRequest().setAttribute(SESSION_APPLICATION_TYPE,
					applicationType);
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			Project project = null;
			if (StringUtils.isNotEmpty(projectCode)) {
				project = administrator.getProject(projectCode);
			}
			if (project != null) {
				ProjectInfo projectInfo = project.getProjectInfo();
				getHttpRequest().setAttribute(REQ_PROJECT_INFO, projectInfo);
			}
			getHttpRequest().setAttribute(REQ_SELECTED_JSLIBS,
					getHttpRequest().getParameter(REQ_SELECTED_JSLIBS));
			getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.applicationType()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Getting Application Type");
		}
		return APP_TYPE;
	}

	public String technology() {
		
		S_LOGGER.debug("Entering Method  Applications.technology()");
		try {
			String selectedTechnology = getHttpRequest().getParameter(REQ_TECHNOLOGY);
			String appType = getHttpRequest().getParameter(REQ_APPLICATION_TYPE);
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = null;
			if (StringUtils.isNotEmpty(projectCode)) {
				project = administrator.getProject(projectCode);
			}
			if (project != null) {
				ProjectInfo projectInfo = project.getProjectInfo();
				S_LOGGER.debug("project info value"	+ projectInfo.toString());
				getHttpRequest().setAttribute(REQ_PROJECT_INFO, projectInfo);
			}
			ApplicationType applicationType = ApplicationsUtil.getApplicationType(getHttpRequest(), appType);
			Technology techonology = applicationType.getTechonology(selectedTechnology);
			List<Server> servers = techonology.getServers();
			List<Database> databases = techonology.getDatabases();
			S_LOGGER.debug("Selected technology" + techonology.toString());
			//This attribute for Pilot Project combo box
	    	getHttpRequest().setAttribute(REQ_PILOTS_NAMES, ApplicationsUtil.getPilotNames(techonology.getId()));
	    	getHttpRequest().setAttribute(REQ_PILOT_PROJECT_INFO, ApplicationsUtil.getPilotProjectInfo(techonology.getId()));
			getHttpRequest().setAttribute(SESSION_SELECTED_TECHNOLOGY, techonology);
			getHttpRequest().setAttribute(REQ_APPLICATION_TYPE, appType);
			getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
			getHttpRequest().setAttribute("servers", servers);
			getHttpRequest().setAttribute("databases", databases);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Applications.technology()"	+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Getting technology");
		}

		return APP_TECHNOLOGY;
	}
	
	public String techVersions() {
		try {
			String appType = getHttpRequest().getParameter(REQ_APPLICATION_TYPE);
			String selectedTechnology = getHttpRequest().getParameter(REQ_TECHNOLOGY);
			ApplicationType applicationType = ApplicationsUtil.getApplicationType(getHttpRequest(), appType);
			Technology techonology = applicationType.getTechonology(selectedTechnology);
			techVersions = techonology.getVersions();
		} catch(Exception e) {
			S_LOGGER.error("Entered into catch block of  Applications.techVersions()"
							+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Getting technology versions");
		}
		
		return SUCCESS;
	}

	public String previous() throws PhrescoException {
		S_LOGGER.debug("Entered previous()");

		try {
			HttpServletRequest request = getHttpRequest();
			getHttpRequest().setAttribute("projectCode", projectCode);

			ProjectInfo projectInfo = null;
			if (projectCode != null) {
				projectInfo = (ProjectInfo) getHttpSession().getAttribute(
						projectCode);

				getHttpRequest().setAttribute(REQ_TEMP_SELECTED_PILOT_PROJ, getHttpRequest().getParameter(REQ_SELECTED_PILOT_PROJ));
				String[] modules = getHttpRequest().getParameterValues(
						REQ_SELECTEDMODULES);
				if (modules != null && modules.length > 0) {
					Map<String, String> mapModules = ApplicationsUtil
							.getIdAndVersionAsMap(getHttpRequest(), modules);
					getHttpRequest().setAttribute(REQ_TEMP_SELECTEDMODULES,
							mapModules);
				}

				String[] jsLibs = getHttpRequest().getParameterValues(
						REQ_SELECTED_JSLIBS);
				if (jsLibs != null && jsLibs.length > 0) {
					Map<String, String> mapJsLib = ApplicationsUtil
							.getIdAndVersionAsMap(request, jsLibs);
					getHttpRequest().setAttribute(REQ_TEMP_SELECTED_JSLIBS,
							mapJsLib);
				}
			}
			getHttpSession().setAttribute(projectCode, projectInfo);
			FrameworkUtil.setAppInfoDependents(getHttpRequest());
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Applications.previous()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "When previous button is clicked");
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		getHttpRequest().setAttribute(REQ_CONFIG_SERVER_NAMES, configServerNames);
        getHttpRequest().setAttribute(REQ_CONFIG_DB_NAMES, configDbNames);

        return APP_APPINFO;
	}

	public String save() throws PhrescoException {
		S_LOGGER.debug("Entering Method Applications.save()");

		ProjectInfo projectInfo = (ProjectInfo) getHttpSession().getAttribute(
				projectCode);
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			setFeatures(administrator, projectInfo);
			S_LOGGER.debug("Going to create project, Project info values "
						+ projectInfo.toString());
			UserInfo userInfo = (UserInfo) getHttpSession().getAttribute(REQ_USER_INFO);
			administrator.createProject(projectInfo, null, userInfo);
			addActionMessage(getText(SUCCESS_PROJECT,
					Collections.singletonList(projectInfo.getName())));
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Applications.save()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Save Project");
		}
		getHttpSession().removeAttribute(projectCode);
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		
		return discover();
	}

	public String update() throws PhrescoException {
		S_LOGGER.debug("Entering Method  Applications.update()");
		
		BufferedReader reader = null;
		ProjectInfo projectInfo = (ProjectInfo) getHttpSession().getAttribute(projectCode);
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			setFeatures(administrator, projectInfo);
			ProjectInfo originalinfo = projectInfo.clone();
			File projectPath = new File(Utility.getProjectHome(), projectInfo.getCode() + File.separator + FOLDER_DOT_PHRESCO + File.separator
					+ PROJECT_INFO);
			try {
				reader = new BufferedReader(new FileReader(projectPath));
			} catch (FileNotFoundException e) {
				throw new PhrescoException(e);

			}
			List<ModuleGroup> modules = projectInfo.getTechnology().getModules();
			List<ModuleGroup> jsLibraries = projectInfo.getTechnology().getJsLibraries();
			if (modules == null) {
				projectInfo.getTechnology().setModules(null);
			}
		
			if (jsLibraries == null) {
				projectInfo.getTechnology().setJsLibraries(null);
			}
			try {
				ProjectInfo tempprojectInfo = administrator.getProject(projectCode).getProjectInfo();
				List<Database> newDatabases = projectInfo.getTechnology().getDatabases();
				List<String> newDbNames = new ArrayList<String>();
				if (CollectionUtils.isNotEmpty(newDatabases) && newDatabases != null) {
					for (Database newDatabase : newDatabases) {
						newDbNames.add(newDatabase.getName());
					}
				}
				
				List<Database> projectInfoDbs = tempprojectInfo.getTechnology().getDatabases();
				List<String> projectInfoDbNames = new ArrayList<String>();
				if (CollectionUtils.isNotEmpty(projectInfoDbs) && projectInfoDbs != null) {
					for (Database projectInfoDb : projectInfoDbs) {
						projectInfoDbNames.add(projectInfoDb.getName());
					}
				}
				
				if (CollectionUtils.isNotEmpty(projectInfoDbNames) && projectInfoDbNames != null) {
					for (String projectInfoDbName : projectInfoDbNames) {
						if (!newDbNames.contains(projectInfoDbName)) {
							deletableDbs.add(projectInfoDbName);
						} else {
							for (Database newDatabase : newDatabases) {
								for (Database projectInfoDb : projectInfoDbs) {
									if (newDatabase.getName().equals(projectInfoDb.getName())) {
										List<String> newDbVersions = newDatabase.getVersions();
										List<String> projectInfoDbVersions = projectInfoDb.getVersions();
										compareVersions(projectInfoDb.getName(), projectInfoDbVersions, newDbVersions);
									}
								}
							}
						}
					}
				}
				
				administrator.deleteSqlFolder(deletableDbs, projectInfo);
				UserInfo userInfo = (UserInfo) getHttpSession().getAttribute(REQ_USER_INFO);
				administrator.updateProject(projectInfo, originalinfo, projectPath,userInfo);
				removeConfiguration();
				addActionMessage(getText(UPDATE_PROJECT,Collections.singletonList(projectInfo.getName())));
			} catch (Exception e) {
				throw new PhrescoException(e);
			}

		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of  Applications.update()" + FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Update Project");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
		getHttpSession().removeAttribute(projectCode);
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		
		return discover();
	}
	
	private void compareVersions(String dbName, List<String> projectInfoDbVersions, List<String> newDbVersions) {
		for (String projectInfoDbVersion : projectInfoDbVersions) {
			if (newDbVersions.contains(projectInfoDbVersion)) {
				
			}
			else {
				deletableDbs.add(dbName + "/" + projectInfoDbVersion.trim());
			}
		}
	}
	
	private void setFeatures(ProjectAdministrator administrator,
			ProjectInfo projectInfo) throws PhrescoException {
		S_LOGGER.debug("Entering Method  Applications.setFeatures()");
		
		String module = getHttpRequest().getParameter(REQ_SELECTEDMODULES);
		String[] modules = module.split(",");

		ApplicationType applicationType = administrator
		.getApplicationType(projectInfo.getApplication());
		if (S_LOGGER.isDebugEnabled()) {
			S_LOGGER.debug("Application Type object value "
					+ applicationType.toString());
		}

		if (modules != null) {
			List<ModuleGroup> allModules = applicationType.getTechonology(
					projectInfo.getTechnology().getId()).getModules();
			List<ModuleGroup> selectedModules = ApplicationsUtil.getSelectedTuples(getHttpRequest(), allModules, modules);
			projectInfo.getTechnology().setModules(selectedModules);
		}

		String jsLib = getHttpRequest().getParameter(REQ_SELECTED_JSLIBS);
		String[] jsLibs = jsLib.split(",");
		if (jsLibs != null) {
			List<ModuleGroup> allModules = applicationType.getTechonology(
					projectInfo.getTechnology().getId()).getJsLibraries();
			List<ModuleGroup> selectedModules = ApplicationsUtil
			.getSelectedTuples(getHttpRequest(), allModules, jsLibs);
			projectInfo.getTechnology().setJsLibraries(selectedModules);
		}
	}

	public String edit() {
		S_LOGGER.debug("Entering Method  Applications.edit()");
		
		try {
			FrameworkConfiguration configuration = null;
			configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			Client client = ClientHelper.createClient();
			WebResource resource = client.resource(configuration
					.getServerPath() + FrameworkConstants.REST_APPS_PATH);
			Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);
			GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>() {
			};
			List<ApplicationType> applicationTypes = builder.get(genericType);
			if (S_LOGGER.isDebugEnabled()) {
				S_LOGGER.debug("Application Types received from rest service");
				if (applicationTypes != null) {
					for (ApplicationType applicationType : applicationTypes) {
						S_LOGGER.debug("Application Type value"
								+ applicationType.toString());
					}
				}
			}
			getHttpRequest().setAttribute(REQ_FROM_PAGE, FROM_PAGE);
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Applications.edit()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Project edit");
		}
		
		return APP_APPLICATION;
	}

	public String delete() {
		S_LOGGER.debug("Entering Method  Applications.delete()");
		
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext
					.getContext().get(ServletActionContext.HTTP_REQUEST);
			String selectedProjects[] = request
					.getParameterValues(REQ_SELECTEDPROJECTS);

			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			List<String> projectCodes = new ArrayList<String>();
			if (selectedProjects != null) {
				for (String selctedProject : selectedProjects) {
					projectCodes.add(selctedProject);
				}
			}

			administrator.deleteProject(projectCodes);
			addActionMessage(SUCCESS_PROJECT_DELETE);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.delete()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Project delete");
		}
		
		return list();
	}

	public String importApplication() {
		S_LOGGER.debug("Entering Method  Applications.importApplication()");
		
		try {
			File checkOutDir = new File(Utility.getProjectHome());
			/*
			 * byte[] decodedBytes = Base64.decodeBase64(password); password =
			 * new String(decodedBytes);
			 */
			if (StringUtils.isEmpty(credential)) {
				String decryptedPass = new String(Base64.decodeBase64(password));
				password = decryptedPass;
			}

			SVNAccessor svnAccessor = new SVNAccessor(repositoryUrl, userName, password);
			S_LOGGER.debug("Import Application repository Url"
						+ repositoryUrl + " Username " + userName);
			revision = !"HEAD".equals(revision) ? revisionVal : revision;
			svnAccessor.checkout(checkOutDir, revision, true);
			svnImport = true;
			svnImportMsg = getText(IMPORT_SUCCESS_PROJECT);
	    } catch(SVNAuthenticationException e){
	         S_LOGGER.error("Entered into catch block of Applications.importApplication()"
						+ FrameworkUtil.getStackTraceAsString(e));
	         svnImport = false;
	         svnImportMsg = getText(INVALID_CREDENTIALS);
	    } catch(SVNException e){
	    	S_LOGGER.error("Entered into catch block of Applications.importApplication()"
					+ FrameworkUtil.getStackTraceAsString(e)); 
	    	svnImport = false;
	    	if(e.getMessage().indexOf(SVN_FAILED) != -1) {
	    		svnImportMsg = getText(INVALID_URL);
	    	} else if(e.getMessage().indexOf(SVN_INTERNAL) != -1) {
	    		svnImportMsg = getText(INVALID_REVISION);
	    	} else {
	    		svnImportMsg = getText(INVALID_FOLDER);
	    	}
	    } catch(PhrescoException e){
	    	S_LOGGER.error("Entered into catch block of Applications.importApplication()"
					+ FrameworkUtil.getStackTraceAsString(e));
	    	svnImport = false;
	    	svnImportMsg = getText(PROJECT_ALREADY);
	    } catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.importApplication()"
					+ FrameworkUtil.getStackTraceAsString(e)); 
			svnImport = false;
			svnImportMsg = getText(IMPORT_PROJECT_FAIL);
		}
	    
		return SUCCESS;
	}

	public String importFromSvn() {
		return APP_IMPORT_FROM_SVN;
	}

	public String validateFramework() {
		S_LOGGER.debug("Entering Method  Applications.validateFramework()");
		
		try {
			getHttpRequest().setAttribute(VALIDATE_FROM, VALIDATE_FRAMEWORK);
			// From home when clicking applications , it becomes true
			String validateInBg = getHttpRequest().getParameter(VALIDATE_IN_BG);
			if (validateInBg.equals("true") && getHttpSession().getAttribute(SESSION_FRMK_VLDT_RSLT) != null) {
				setGlobalValidationStatus((String) getHttpSession().getAttribute(SESSION_FRMK_VLDT_STATUS));
				return Action.SUCCESS;
			} else {
				getHttpSession().removeAttribute(SESSION_FRMK_VLDT_STATUS);
				getHttpSession().removeAttribute(SESSION_FRMK_VLDT_RSLT);
				ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
				List<ValidationResult> validationResults = administrator.validate();
				String validationStatus = null;
				for (ValidationResult validationResult : validationResults) {
					validationStatus = validationResult.getStatus().toString();
					if (validationStatus == "ERROR") {
						getHttpSession().setAttribute(SESSION_FRMK_VLDT_STATUS,	"ERROR");
					}
				}
				getHttpSession().setAttribute(SESSION_FRMK_VLDT_RSLT, validationResults);
				if (validateInBg.equals("true")) {
					setGlobalValidationStatus(validationStatus);
					return Action.SUCCESS;
				}
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.validateFramework()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Validating framework");
		}
		
		return APP_VALIDATE_FRAMEWORK;
	}

	public String showFrameworkValidationResult() {
		S_LOGGER.debug("Entering Method  Applications.showFrameworkValidationResult()");
		getHttpRequest().setAttribute(VALIDATE_FROM, VALIDATE_FRAMEWORK);
		
		return APP_SHOW_FRAMEWORK_VLDT_RSLT;
	}

	public String validateProject() { 
		S_LOGGER.debug("Entering Method  Applications.validateProject()");
		
		try {
			getHttpRequest().setAttribute(VALIDATE_FROM, VALIDATE_PROJECT);
			String validateInBg = getHttpRequest().getParameter(VALIDATE_IN_BG);
			if (getHttpSession().getAttribute(projectCode + SESSION_PRJT_VLDT_RSLT) != null	&& "true".equals(validateInBg)) {
				setGlobalValidationStatus((String) getHttpSession().getAttribute(projectCode + SESSION_PRJT_VLDT_STATUS));
				return Action.SUCCESS;
			} else {
				getHttpSession().removeAttribute(projectCode + SESSION_PRJT_VLDT_RSLT);
				getHttpSession().removeAttribute(projectCode + SESSION_PRJT_VLDT_STATUS);
				ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
				String validationStatus = null;
				if (!StringUtils.isEmpty(projectCode)) {
					Project project = administrator.getProject(projectCode);
					List<ValidationResult> validationResults = administrator.validate(project);
					for (ValidationResult validationResult : validationResults) {
						validationStatus = validationResult.getStatus().toString();
						if (validationStatus == "ERROR") {
							getHttpSession().setAttribute(projectCode + SESSION_PRJT_VLDT_STATUS, "ERROR");
						}
					}
					getHttpSession().setAttribute(projectCode + SESSION_PRJT_VLDT_RSLT,	validationResults);
				}
				
				if (validateInBg.equals("true")) {
					setGlobalValidationStatus(validationStatus);
					return Action.SUCCESS;
				}
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.validateProject()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Validating project");
		}
		
		return APP_VALIDATE_PROJECT;
	}

	public String showProjectValidationResult() {
		S_LOGGER.debug("Entering Method  Applications.showProjectValidationResult()");
		
		getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
		getHttpRequest().setAttribute(VALIDATE_FROM, VALIDATE_PROJECT);
		
		return APP_SHOW_PROJECT_VLDT_RSLT;
	}

	public String discover() {
		S_LOGGER.debug("Entering Method  Applications.discover()");
		
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			List<Project> projects = administrator.discover(Collections
					.singletonList(new File(Utility.getProjectHome())));
			HttpServletRequest request = (HttpServletRequest) ActionContext
					.getContext().get(ServletActionContext.HTTP_REQUEST);
			request.setAttribute(REQ_PROJECTS, projects);
			// add session
			Map<String, Object> sessionMap = ActionContext.getContext()
					.getSession();
			sessionMap.put(REQ_PROJECTS, projects);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.discover()"
						+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Discovering projects");
			// return APP_LIST;
		}
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		
		return APP_LIST;
	}
	
	public String showSettings() {
		S_LOGGER.debug("entered Applications.showSettings()");
		
		try {
			if (showSettings != null && Boolean.valueOf(showSettings)) {
				settingsEnv = getEnvironmentNames();
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.showSettings()"
						+ FrameworkUtil.getStackTraceAsString(e));
		}
		
		return SUCCESS;
	}
	
	private List<String> getEnvironmentNames() {
		List<String> names = new ArrayList<String>(5);
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			List<Environment> environments = administrator.getEnvironments();
			if (CollectionUtils.isNotEmpty(environments)) {
				for (Environment environment : environments) {
					names.add(environment.getName());
				}
			}
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.getEnvironmentNames()"
						+ FrameworkUtil.getStackTraceAsString(e));
		}
		
		return names;
	}

	public String openAttrPopup() throws PhrescoException {
		try {
			String techId = getHttpRequest().getParameter("techId");
			String appType = getHttpRequest().getParameter(REQ_APPLICATION_TYPE);
			String type = getHttpRequest().getParameter(ATTR_TYPE);
			String from = getHttpRequest().getParameter(REQ_FROM);
			getHttpRequest().setAttribute(REQ_FROM, getHttpRequest().getParameter(REQ_FROM));
			ApplicationType applicationType = null;
			applicationType = ApplicationsUtil.getApplicationType(getHttpRequest(), appType);
			Technology techonology = applicationType.getTechonology(techId);
			String attrName = null;
			if (Constants.SETTINGS_TEMPLATE_SERVER.equals(type)) {
				List<Integer> listSelectedServerIds = null;
				List<Server> servers = techonology.getServers();
				if(StringUtils.isEmpty(from)) {
					List<String> listSelectedServers = null;
					List<String> listSelectedServerNames = null;
					String selectedServers = getHttpRequest().getParameter("selectedServers");
					if (StringUtils.isNotEmpty(selectedServers)) {
						listSelectedServerNames = new ArrayList<String>();
						listSelectedServers = new ArrayList<String>(Arrays.asList(selectedServers.split("#SEP#")));
						for (String listSelectedServer : listSelectedServers) {
							String[] split = listSelectedServer.split("#VSEP#");
							listSelectedServerNames.add(split[0].trim());
						}
						listSelectedServerIds = new ArrayList<Integer>(2);
						for (Server server : servers) {
							if(listSelectedServerNames.contains(server.getName())) {
								listSelectedServerIds.add(server.getId());
							}
						}
					}
					getHttpRequest().setAttribute("listSelectedServerIds", listSelectedServerIds);
					getHttpRequest().setAttribute(REQ_HEADER_TYPE, "Select");
				} else {
					attrName = getHttpRequest().getParameter("attrName");
					String selectedVersions = getHttpRequest().getParameter("selectedVersions");
					selectedVersions = selectedVersions.replaceAll(" ", "");
					List<String> listSelectedVersions = new ArrayList<String>(Arrays.asList(selectedVersions.split(",")));
					listSelectedServerIds = new ArrayList<Integer>(2);
					for (Server server : servers) {
						String serverName = server.getName().trim();
						serverName = serverName.replaceAll("\\s+", "");
						if(serverName.equals(attrName)) {
							listSelectedServerIds.add(server.getId());
						}
					}
					getHttpRequest().setAttribute("listSelectedServerIds", listSelectedServerIds);
					getHttpRequest().setAttribute(REQ_LISTSELECTED_VERSIONS, listSelectedVersions);
					getHttpRequest().setAttribute(REQ_HEADER_TYPE, "Edit");
				}
				getHttpRequest().setAttribute("servers", servers);
			}
			if (Constants.SETTINGS_TEMPLATE_DB.equals(type)) {
				List<Integer> listSelectedDatabaseIds = null;
				List<Database> databases = techonology.getDatabases();
				if(StringUtils.isEmpty(from)) {
					List<String> listSelectedDbs = null;
					List<String> listSelectedDbNames = null;
					List<Integer> listSelectedDbIds = null;
					String selectedDatabases = getHttpRequest().getParameter("selectedDatabases");
					if (StringUtils.isNotEmpty(selectedDatabases)) {
						listSelectedDbNames = new ArrayList<String>();
						listSelectedDbs = new ArrayList<String>(Arrays.asList(selectedDatabases.split("#SEP#")));
						for (String listSelectedDb : listSelectedDbs) {
							String[] split = listSelectedDb.split("#VSEP#");
							listSelectedDbNames.add(split[0].trim());
						}
						listSelectedDbIds = new ArrayList<Integer>(2);
						for (Database database : databases) {
							if(listSelectedDbNames.contains(database.getName())) {
								listSelectedDbIds.add(database.getId());
							}
						}
					}
					getHttpRequest().setAttribute("listSelectedDatabaseIds", listSelectedDbIds);
					getHttpRequest().setAttribute(REQ_HEADER_TYPE, "Select");
				} else {
					attrName = getHttpRequest().getParameter("attrName");
					ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
					if (StringUtils.isNotEmpty(projectCode)) {
						Project project = administrator.getProject(projectCode);
						if (project != null) {
							ProjectInfo projectInfo = project.getProjectInfo();
							List<Database> projectInfoDbs = projectInfo.getTechnology().getDatabases();
							List<String> projectInfoDbVersions = new ArrayList<String>();
							StringBuilder sb = new StringBuilder();
							for (Database projectInfoDb : projectInfoDbs) {
								if (projectInfoDb.getName().equals(attrName)) {
									projectInfoDbVersions.addAll(projectInfoDb.getVersions());
								}
							}
							if (projectInfoDbVersions != null) {
								for (String projectInfoDbVersion : projectInfoDbVersions) {
									sb.append(projectInfoDbVersion);
									sb.append(",");
								}

							}
							if (StringUtils.isNotEmpty(sb.toString())) {
								getHttpRequest().setAttribute("projectInfoDbVersions", sb.toString().substring(0, sb.length() - 1));
							}
						}
					}
					String selectedVersions = getHttpRequest().getParameter("selectedVersions");
					selectedVersions = selectedVersions.replaceAll(" ", "");
					List<String> listSelectedVersions = new ArrayList<String>(Arrays.asList(selectedVersions.split(",")));
					listSelectedDatabaseIds = new ArrayList<Integer>(2);
					for (Database database : databases) {
						String databaseName = database.getName().trim();
						databaseName = databaseName.replaceAll("\\s+", "");
						if(databaseName.equals(attrName)) {
							listSelectedDatabaseIds.add(database.getId());
						}
					}
					getHttpRequest().setAttribute("listSelectedDatabaseIds", listSelectedDatabaseIds);
					getHttpRequest().setAttribute(REQ_LISTSELECTED_VERSIONS, listSelectedVersions);
					getHttpRequest().setAttribute(REQ_HEADER_TYPE, "Edit");
				}
				getHttpRequest().setAttribute("databases", databases);
			}
			
			getHttpRequest().setAttribute("attrName", attrName);
			getHttpRequest().setAttribute("header", type);
			getHttpRequest().setAttribute(REQ_FROM, from);
			getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Applications.openAttrPopup()"
							+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "Getting server and database");
		}
		
		return "openAttrPopup";
	}
	
	public String allVersions() throws PhrescoException {
		versions = new ArrayList<String>(2);
		String techId = getHttpRequest().getParameter("techId");
		String appType = getHttpRequest().getParameter(REQ_APPLICATION_TYPE);
		String type = getHttpRequest().getParameter("type");
		
		int selectedId = Integer.parseInt(getHttpRequest().getParameter("selectedId"));
		ApplicationType applicationType = ApplicationsUtil.getApplicationType(getHttpRequest(), appType);
		Technology selectedTechnology = applicationType.getTechonology(techId);
		
		if ("Server".equals(type)) {
			List<Server> servers = selectedTechnology.getServers();
			for (Server server : servers) {
				if(server.getId() == selectedId) {
					versions = server.getVersions();
				}
			}
		}
		if ("Database".equals(type)) {
			List<Database> databases = selectedTechnology.getDatabases();
			for (Database database : databases) {
				if(database.getId() == selectedId) {
					versions.addAll(database.getVersions());
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String addDetails() throws PhrescoException {
		String type = getHttpRequest().getParameter("type");
		setSelectedParamName(getHttpRequest().getParameter("paramName"));
		divTobeUpdated = getHttpRequest().getParameter("divTobeUpdated");
		if ("Server".equals(type)) {
			String[] selectedServerVersions = getHttpRequest().getParameterValues("serverVersion");
			selectedVersions = convertToCommaDelimited(selectedServerVersions);
		} else {
			String[] selectedDbVersions = getHttpRequest().getParameterValues("databaseVersion");
			selectedVersions = convertToCommaDelimited(selectedDbVersions);
		}
		setSelectedAttrType(type);
		
		return SUCCESS;
	}
	
	public static String convertToCommaDelimited(String[] list) {
		StringBuffer retString = new StringBuffer("");
		for (int i = 0; list != null && i < list.length; i++) {
			retString.append(list[i]);
			if (i < list.length - 1) {
				retString.append(','+" ");
			}
		}
		
		return retString.toString();
	}
	
	public String checkForRespectiveConfig() throws PhrescoException {
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			Map<String, List<String>> deleteConfigs = new HashMap<String , List<String>>();
			List<String> configNames = new ArrayList<String>();
			List<SettingsInfo> configurations = administrator.configurations(selectedAttrType, project);
			if (CollectionUtils.isNotEmpty(configurations)) {
				for (SettingsInfo config : configurations) {
					deleteConfigs.clear();
					configNames.clear();
					PropertyInfo serverType = config.getPropertyInfo(Constants.SERVER_TYPE);
					if (serverType.getValue().equalsIgnoreCase(selectedParamName)) {
						setHasConfiguration(true);
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		
		return SUCCESS;
	}
	
	private void removeConfiguration() throws PhrescoException {
		try {
			if (StringUtils.isNotEmpty(configServerNames)) {
				deleteConfigurations("Server", configServerNames.substring(0, configServerNames.length() - 1));
			}
			if (StringUtils.isNotEmpty(configDbNames)) {
				deleteConfigurations("Database", configDbNames.substring(0, configDbNames.length() - 1));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	private void deleteConfigurations (String type, String configName) throws PhrescoException {
		try {
		    String [] items = configName.split(",");
		    List<String> deleteConfigNames = Arrays.asList(items);
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			Map<String, List<String>> deleteConfigs = new HashMap<String , List<String>>();
			List<String> configNames = new ArrayList<String>();
			List<SettingsInfo> configurations = administrator.configurations(type, project);
			if (CollectionUtils.isNotEmpty(configurations)) {
				for (SettingsInfo config : configurations) {
					deleteConfigs.clear();
					configNames.clear();
					PropertyInfo serverType = config.getPropertyInfo(Constants.SERVER_TYPE);
					for (String deleteConfigName : deleteConfigNames) {
						if (serverType.getValue().equalsIgnoreCase(deleteConfigName)) {
							configNames.add(config.getName());
							deleteConfigs.put(config.getEnvName(), configNames);
							administrator.deleteConfigurations(deleteConfigs, project);
						}
					}
				}
			}
		} catch(Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	public String checkForConfiguration() throws PhrescoException {
		try {
			boolean isError = false;
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			Technology technology = project.getProjectInfo().getTechnology();
			
			List<Server> servers = technology.getServers();
			List<Database> databases = technology.getDatabases();
			List<WebService> webservices = technology.getWebservices();
			boolean emailSupported = technology.isEmailSupported();

			String envs = getHttpRequest().getParameter(ENVIRONMENTS);
			if(StringUtils.isEmpty(envs)){
				setHasError(false);
				return SUCCESS;
			}
			String[] envArr = envs.split(",");
			List<String> unAvailableTypes = new ArrayList<String>();
			String from = getHttpRequest().getParameter(REQ_FROM);
			for (String envName : envArr) {
				if (NODEJS_RUN_AGAINST.equals(from) || JAVA_RUN_AGAINST.equals(from)) {
					if (CollectionUtils.isEmpty(administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER, projectCode, envName))) {
						setEnvError(getText(ERROR_NO_CONFIG));
						setHasError(true);
						return SUCCESS;
					}
				}
				
				if (servers != null && CollectionUtils.isNotEmpty(servers) && CollectionUtils.isEmpty(administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER, projectCode, envName))) {
					isError = true;
					unAvailableTypes.add("Server");
				}
				
				if (databases != null && CollectionUtils.isNotEmpty(databases) && CollectionUtils.isEmpty(administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_DB, projectCode, envName))) {
					isError = true;
					unAvailableTypes.add("Database");
 				}
				
				if (webservices != null && CollectionUtils.isNotEmpty(webservices) && CollectionUtils.isEmpty(administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_WEBSERVICE, projectCode, envName))) {
					isError = true;
					unAvailableTypes.add("WebService");
				}
				
				if (emailSupported && CollectionUtils.isEmpty(administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_EMAIL, projectCode, envName))) {
					isError = true;
					unAvailableTypes.add("E-mail");
				}
				
				if (isError) {
					String csvUnAvailableTypes = "";
					if (CollectionUtils.isNotEmpty(unAvailableTypes)) {
						csvUnAvailableTypes = StringUtils.join(unAvailableTypes.toArray(), ",");
					}
					setEnvError(csvUnAvailableTypes + " Configurations not available for " + envName + " environment");
					setHasError(true);
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		
		return SUCCESS;
	}
	
	public String checkForConfigType() throws PhrescoException {
		try{
			String envs = getHttpRequest().getParameter(ENVIRONMENTS);
			if(StringUtils.isEmpty(envs)){
				setHasError(false);
				return SUCCESS;
			}
			String type = getHttpRequest().getParameter("type");
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			if(CollectionUtils.isEmpty(administrator.getSettingsInfos(type, projectCode, envs))) {
				setEnvError(getText(ERROR_ENV_CONFIG, Collections.singletonList(type)));
				setHasError(true);
			}
		} catch(Exception e) {
			throw new PhrescoException(e);
		}
		
		return SUCCESS;
	}
	
	public String browse() {
		S_LOGGER.debug("Entering Method  Applications.browse()");
		try {

			getHttpRequest().setAttribute(FILE_TYPES, fileType);
			getHttpRequest().setAttribute(FILE_BROWSE, fileorfolder);
			String projectLocation = Utility.getProjectHome() + projectCode;
			getHttpRequest().setAttribute(REQ_PROJECT_LOCATION, projectLocation.replace(File.separator, FORWARD_SLASH));
			getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of  Applications.browse()"	+ FrameworkUtil.getStackTraceAsString(e));
			new LogErrorReport(e, "File Browse");
		}
		return SUCCESS;
	}
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getRepourl() {
		return repositoryUrl;
	}

	public void setRepourl(String repourl) {
		this.repositoryUrl = repourl;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRevisionVal() {
		return revisionVal;
	}

	public void setRevisionVal(String revisionVal) {
		this.revisionVal = revisionVal;
	}

	public String getShowSettings() {
		return showSettings;
	}

	public void setShowSettings(String showSettings) {
		this.showSettings = showSettings;
	}

	public String getGlobalValidationStatus() {
		return globalValidationStatus;
	}

	public void setGlobalValidationStatus(String globalValidationStatus) {
		this.globalValidationStatus = globalValidationStatus;
	}
	
	public List<String> getPilotModules() {
		return pilotModules;
	}

	public void setPilotModules(List<String> pilotModules) {
		this.pilotModules = pilotModules;
	}
	
	public List<String> getPilotJSLibs() {
		return pilotJSLibs;
	}

	public void setPilotJSLibs(List<String> pilotJSLibs) {
		this.pilotJSLibs = pilotJSLibs;
	}
	
	public List<String> getVersions() {
		return versions;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
	
	public String getSelectedVersions() {
		return selectedVersions;
	}

	public void setSelectedVersions(String selectedVersions) {
		this.selectedVersions = selectedVersions;
	}
	
	public String getSelectedAttrType() {
		return selectedAttrType;
	}

	public void setSelectedAttrType(String selectedAttrType) {
		this.selectedAttrType = selectedAttrType;
	}

	public String getSelectedParamName() {
		return selectedParamName;
	}

	public void setSelectedParamName(String selectedParamName) {
		this.selectedParamName = selectedParamName;
	}
	
	public String getHiddenFieldValue() {
		return hiddenFieldValue;
	}

	public void setHiddenFieldValue(String hiddenFieldValue) {
		this.hiddenFieldValue = hiddenFieldValue;
	}
	
	public String getDivTobeUpdated() {
		return divTobeUpdated;
	}

	public void setDivTobeUpdated(String divTobeUpdated) {
		this.divTobeUpdated = divTobeUpdated;
	}
	
	public List<String> getSettingsEnv() {
		return settingsEnv;
	}

	public void setSettingsEnv(List<String> settingsEnv) {
		this.settingsEnv = settingsEnv;
	}
	
	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	public String getEnvError() {
		return envError;
	}

	public void setEnvError(String envError) {
		this.envError = envError;
	}
	
	public List<String> getTechVersions() {
		return techVersions;
	}

	public void setTechVersions(List<String> techVersions) {
		this.techVersions = techVersions;
	}
	
	public boolean isHasConfiguration() {
		return hasConfiguration;
	}

	public void setHasConfiguration(boolean hasConfiguration) {
		this.hasConfiguration = hasConfiguration;
	}
	
	public String getConfigServerNames() {
		return configServerNames;
	}

	public void setConfigServerNames(String configServerNames) {
		this.configServerNames = configServerNames;
	}

	public String getConfigDbNames() {
		return configDbNames;
	}

	public void setConfigDbNames(String configDbNames) {
		this.configDbNames = configDbNames;
	}

	public boolean isSvnImport() {
		return svnImport;
	}

	public void setSvnImport(boolean svnImport) {
		this.svnImport = svnImport;
	}

	public String getSvnImportMsg() {
		return svnImportMsg;
	}

	public void setSvnImportMsg(String svnImportMsg) {
		this.svnImportMsg = svnImportMsg;
	}

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileorfolder() {
		return fileorfolder;
	}

	public void setFileorfolder(String fileorfolder) {
		this.fileorfolder = fileorfolder;
	}

}