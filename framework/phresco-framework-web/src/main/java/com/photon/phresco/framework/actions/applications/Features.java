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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.ApplicationsUtil;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.WebService;

public class Features extends FrameworkBaseAction {
	private static final long serialVersionUID = 6608382760989903186L;
	private static final Logger S_LOGGER = Logger.getLogger(Features.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	private String projectCode = null;
	private String externalCode = null;
	private String fromPage = null;
	private String name = null;
	private String code = null;
	private String groupId = null;
	private String projectVersion = null;
	private String artifactId = null;
	private String description = null;
	private String application = null;
	private String technology = null;
	private List<String> techVersion = null;
	private String nameError = null;
	private String moduleId = null;
	private String version = null;
	private String moduleType = null;
	private String techId = null;
	private String preVersion = null;
	private Collection<String> dependentIds = null;
	private Collection<String> dependentVersions = null;
	private Collection<String> preDependentIds = null;
	private Collection<String> preDependentVersions = null;
	private List<String> pilotModules = null;
	private List<String> pilotJSLibs = null;
	private boolean isValidated = false;
	private String configServerNames = null;
	private String configDbNames = null;
	private String fromTab = null;
	private List<String> defaultModules =  null;

	public String features() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Features.features()");
		}
		String returnPage = APP_FEATURES_ONE_CLM;
		boolean left = false;
		boolean rightBottom = false;
		boolean right = false;

		try {
			ProjectInfo projectInfo = null;
			ProjectAdministrator administrator = PhrescoFrameworkFactory
					.getProjectAdministrator();
			if (validate(administrator) && StringUtils.isEmpty(fromPage)) {
				return Action.SUCCESS;
			}
			if (StringUtils.isEmpty(fromPage)
					&& StringUtils.isNotEmpty(projectCode)) { // previous button
																// clicked
				projectInfo = (ProjectInfo) getHttpSession().getAttribute(
						projectCode);
			} else if (StringUtils.isNotEmpty(fromPage)) { // For edit project
				projectInfo = administrator.getProject(projectCode)
						.getProjectInfo();
				if (description != null) {
					projectInfo.setDescription(description);
				}
				if (externalCode != null) {
					projectInfo.setProjectCode(externalCode);
				}
				if (projectVersion != null) {
					projectInfo.setVersion(projectVersion);
				}
				if (groupId != null) {
					projectInfo.setGroupId(groupId);
				}
				if (artifactId != null) {
					projectInfo.setArtifactId(artifactId);
				}
					
				application = projectInfo.getApplication();
				technology = projectInfo.getTechnology().getId();

				setTechnology(projectInfo, administrator);
			} else { // For creating new project
				projectInfo = new ProjectInfo();
			}
			if (StringUtils.isEmpty(fromPage)) {
				setAppInfos(projectInfo, administrator);
			}

			getHttpRequest().setAttribute(REQ_TEMP_SELECTED_PILOT_PROJ, getHttpRequest().getParameter(REQ_TEMP_SELECTED_PILOT_PROJ));

			String selectedFeatures = getHttpRequest().getParameter(REQ_TEMP_SELECTEDMODULES);
			if (StringUtils.isNotEmpty(selectedFeatures)) {
				Map<String, String> mapFeatures = ApplicationsUtil.stringToMap(selectedFeatures);
				getHttpRequest().setAttribute(REQ_TEMP_SELECTEDMODULES, mapFeatures);
			}

			String selectedJsLibs = getHttpRequest().getParameter(REQ_SELECTED_JSLIBS);
			if (StringUtils.isNotEmpty(selectedJsLibs)) {
				Map<String, String> mapJsLibs = ApplicationsUtil.stringToMap(selectedJsLibs);
				getHttpRequest().setAttribute(REQ_TEMP_SELECTED_JSLIBS, mapJsLibs);
			}

			setFeaturesInRequest(administrator, projectInfo);
			getHttpRequest().setAttribute(REQ_PROJECT_INFO, projectInfo);

			List<ModuleGroup> coreModules = (List<ModuleGroup>) getHttpRequest().getAttribute(REQ_CORE_MODULES);
			List<ModuleGroup> customModules = (List<ModuleGroup>) getHttpRequest().getAttribute(REQ_CUSTOM_MODULES);
			List<ModuleGroup> allJsLibs = (List<ModuleGroup>) getHttpRequest().getAttribute(REQ_ALL_JS_LIBS);

			// Assigning the position of the coreModule
			if (CollectionUtils.isNotEmpty(coreModules)) { // Assigning coreModule to the left position
				left = true;
				getHttpRequest().setAttribute(REQ_FEATURES_FIRST_MDL_CAT, REQ_EXTERNAL_FEATURES);
				getHttpRequest().setAttribute(REQ_FEATURES_LEFT_MODULES, coreModules);
			}

			// Assigning the position of the customModule
			if (!left && CollectionUtils.isNotEmpty(customModules)) { // Assigning customModule to the left position
				left = true;
				getHttpRequest().setAttribute(REQ_FEATURES_FIRST_MDL_CAT, REQ_CUSTOM_FEATURES);
				getHttpRequest().setAttribute(REQ_FEATURES_LEFT_MODULES, customModules);
			} else if (left && CollectionUtils.isNotEmpty(customModules)) { // Assigning customModule to the right bottom position
				right = true;
				getHttpRequest().setAttribute(REQ_FEATURES_SECOND_MDL_CAT, REQ_CUSTOM_FEATURES);
				getHttpRequest().setAttribute(REQ_FEATURES_RIGHT_MODULES, customModules);
			}

			// Assigning the position of the JSLibraries
			if (left && right && CollectionUtils.isNotEmpty(allJsLibs)) { // Assigning JSLibraries to the right bottom position
				rightBottom = true;
			} else if (left && !right && CollectionUtils.isNotEmpty(allJsLibs)) { // Assigning JSLibraries to the right position
				right = true;
				getHttpRequest().setAttribute(REQ_FEATURES_SECOND_MDL_CAT, REQ_JS_LIBS);
				getHttpRequest().setAttribute(REQ_FEATURES_RIGHT_MODULES, allJsLibs);
			} else if (!left && !right && CollectionUtils.isNotEmpty(allJsLibs)) { // Assigning JSLibraries to the left position
				left = true;
				getHttpRequest().setAttribute(REQ_FEATURES_FIRST_MDL_CAT, REQ_JS_LIBS);
				getHttpRequest().setAttribute(REQ_FEATURES_LEFT_MODULES, allJsLibs);
			}

			if (left && right && rightBottom) {
				returnPage = APP_FEATURES_THREE_CLM;
			} else if (left && right && !rightBottom) {
				returnPage = APP_FEATURES_TWO_CLM;
			}
			getHttpRequest().setAttribute(REQ_CONFIG_SERVER_NAMES, configServerNames);
			getHttpRequest().setAttribute(REQ_CONFIG_DB_NAMES, configDbNames);
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			getHttpRequest().setAttribute(REQ_SERVER_URL, configuration.getServerPath());
			
		} catch (PhrescoException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Features.list()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Feature list");
		}
		
		return returnPage;
	}

	private void setAppInfos(ProjectInfo projectInfo, ProjectAdministrator administrator) throws PhrescoException {
		HttpServletRequest request = getHttpRequest();
		projectInfo.setName(name);
		projectInfo.setCode(code);
		if (externalCode != null) {
			projectInfo.setProjectCode(externalCode);
		}
		if (groupId != null) {
			projectInfo.setGroupId(groupId);
		}
		if (artifactId != null) {
			projectInfo.setArtifactId(artifactId);
		}
		projectInfo.setVersion(projectVersion);
		projectInfo.setDescription(description);
		projectInfo.setApplication(application);
		String pilotProjectName = getHttpRequest().getParameter(REQ_SELECTED_PILOT_PROJ);
		projectInfo.setPilotProjectName(pilotProjectName);

		setTechnology(projectInfo, administrator);
		FrameworkUtil.setAppInfoDependents(request);
	}

	private void setTechnology(ProjectInfo projectInfo, ProjectAdministrator administrator) throws PhrescoException {
		ProjectInfo tempprojectInfo = null;
		Technology selectedTechnology = administrator.getApplicationType(application).getTechonology(technology);
		Technology technology = new Technology();

		technology.setId(selectedTechnology.getId());
		technology.setName(selectedTechnology.getName());
		if (StringUtils.isEmpty(fromPage)) {
			technology.setVersions(techVersion);
		} else {
			tempprojectInfo = administrator.getProject(projectCode).getProjectInfo();
			List<String> projectInfoTechVersions = new ArrayList<String>();
			List<String> tempPrjtInfoTechVersions = tempprojectInfo.getTechnology().getVersions();
			if (tempPrjtInfoTechVersions != null && CollectionUtils.isNotEmpty(tempPrjtInfoTechVersions)) {
				projectInfoTechVersions.addAll(tempprojectInfo.getTechnology().getVersions());
				technology.setVersions(projectInfoTechVersions);
			}
		}
		
		if (StringUtils.isNotEmpty(fromPage)) {// For project edit
			technology.setJsLibraries(projectInfo.getTechnology()
					.getJsLibraries());
			technology.setModules(projectInfo.getTechnology().getModules());
		}

		List<Server> servers = selectedTechnology.getServers();
		List<Database> databases = selectedTechnology.getDatabases();
		List<WebService> webservices = selectedTechnology.getWebservices();
		
		String selectedServers = getHttpRequest().getParameter("selectedServers");
		String selectedDatabases = getHttpRequest().getParameter("selectedDatabases");
		String[] selectedWebservices = getHttpRequest().getParameterValues(REQ_WEBSERVICES);
		boolean isEmailSupported = false;
		
		if (StringUtils.isNotEmpty(fromTab)) {
			if (selectedServers != null) {
				List<String> listTempSelectedServers = null;
				if (StringUtils.isNotEmpty(selectedServers)) {
					listTempSelectedServers = new ArrayList<String>(
							Arrays.asList(selectedServers.split("#SEP#")));
				}
				technology.setServers(ApplicationsUtil.getSelectedServers(servers,
						listTempSelectedServers));
			}
			
			if (selectedDatabases != null) {
				List<String> listTempSelectedDatabases = null;
				if (StringUtils.isNotEmpty(selectedDatabases)) {
					listTempSelectedDatabases = new ArrayList<String>(
							Arrays.asList(selectedDatabases.split("#SEP#")));
				}
				technology.setDatabases(ApplicationsUtil.getSelectedDatabases(
						databases, listTempSelectedDatabases));
			}
			
			if (selectedWebservices != null) {
				technology.setWebservices(ApplicationsUtil.getSelectedWebservices(
						webservices, ApplicationsUtil.getArrayListFromStrArr(selectedWebservices)));
			}
			
			if (getHttpRequest().getParameter(REQ_EMAIL_SUPPORTED) != null) {
				isEmailSupported = Boolean.parseBoolean(getHttpRequest().getParameter(REQ_EMAIL_SUPPORTED));
			}
			technology.setEmailSupported(isEmailSupported);

		} else {
			if (tempprojectInfo != null) {
				technology.setServers(tempprojectInfo.getTechnology().getServers());
				technology.setDatabases(tempprojectInfo.getTechnology().getDatabases());
				technology.setWebservices(tempprojectInfo.getTechnology().getWebservices());
				technology.setEmailSupported(tempprojectInfo.getTechnology().isEmailSupported());
			}
		}
		
		projectInfo.setTechnology(technology);
	}

    private boolean validate(ProjectAdministrator administrator) throws PhrescoException {
    	isValidated = true;
    	if (StringUtils.isEmpty(name)) {
    		setNameError(ERROR_NAME);
            return true;
        }
    	if (StringUtils.isEmpty(name.trim())) {
    		setNameError(ERROR_INVALID_NAME);
            return true;
        }
        if (administrator.getProject(code) != null) {
        	setNameError(ERROR_DUPLICATE_NAME);
            return true;
        }
        return false;
    }
    
	public void setFeaturesInRequest(ProjectAdministrator administrator,
			ProjectInfo projectInfo) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Features.setFeaturesInRequest()");
		}
		Technology selectedTechnology = projectInfo.getTechnology();
		ApplicationType applicationType = administrator
				.getApplicationType(projectInfo.getApplication());
		Technology techonology = applicationType
				.getTechonology(selectedTechnology.getId());

		getHttpRequest().setAttribute(REQ_ALL_JS_LIBS,
				techonology.getJsLibraries());
		List<ModuleGroup> coreModule = (List<ModuleGroup>) administrator
				.getCoreModules(techonology);
		List<ModuleGroup> customModule = (List<ModuleGroup>) administrator
				.getCustomModules(techonology);
		if (CollectionUtils.isNotEmpty(coreModule)) {
			getHttpRequest().setAttribute(REQ_CORE_MODULES, coreModule);
		}

		if (CollectionUtils.isNotEmpty(customModule)) {
			getHttpRequest().setAttribute(REQ_CUSTOM_MODULES, customModule);
		}

		// This attribute for Pilot Project combo box
		getHttpRequest().setAttribute(REQ_PILOTS_NAMES,
				ApplicationsUtil.getPilotNames(selectedTechnology.getId()));

		if (CollectionUtils.isNotEmpty(selectedTechnology.getModules())) {
			// pilotModules.putAll(ApplicationsUtil.getMapFromModuleGroups(selectedTechnology.getModules()));
			getHttpRequest().setAttribute(
					REQ_ALREADY_SELECTED_MODULES,
					ApplicationsUtil.getMapFromModuleGroups(selectedTechnology
							.getModules()));
		}

		if (CollectionUtils.isNotEmpty(selectedTechnology.getJsLibraries())) {
			getHttpRequest().setAttribute(
					REQ_ALREADY_SELECTED_JSLIBS,
					ApplicationsUtil.getMapFromModuleGroups(selectedTechnology
							.getJsLibraries()));
			// pilotJsLibs.putAll(ApplicationsUtil.getMapFromModuleGroups(selectedTechnology.getJsLibraries()));
		}

		getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
		getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
	}

	public String getPilotProjectModules() {
		String techId = getHttpRequest().getParameter(REQ_TECHNOLOGY);
		pilotModules = new ArrayList<String>(ApplicationsUtil
				.getPilotModuleIds(techId).keySet());
		pilotJSLibs = new ArrayList<String>(ApplicationsUtil.getPilotJsLibIds(
				techId).keySet());
		pilotModules.addAll(pilotJSLibs);
		return SUCCESS;
	}
	
	public String fetchDefaultModules() {
		String techId = getHttpRequest().getParameter(REQ_TECHNOLOGY);
		try {
			defaultModules = new ArrayList<String>();
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Technology technology = administrator.getTechnology(techId);
			List<ModuleGroup> coreModules = (List<ModuleGroup>) administrator.getCoreModules(technology);
			if (CollectionUtils.isNotEmpty(coreModules) && coreModules != null) {
				for (ModuleGroup coreModule : coreModules) {
					if (coreModule.isRequired()) {
						defaultModules.add(coreModule.getId());
					}
				}
			}
			List<ModuleGroup> customModules = (List<ModuleGroup>) administrator.getCustomModules(technology);
			if (CollectionUtils.isNotEmpty(customModules) && customModules != null) {
				for (ModuleGroup customModule : customModules) {
					if (customModule.isRequired()) {
						defaultModules.add(customModule.getId());
					}
				}
			}
			
			List<ModuleGroup> jsLibraries = technology.getJsLibraries();
			if (CollectionUtils.isNotEmpty(jsLibraries) && jsLibraries != null) {
				for (ModuleGroup jsLibrary : jsLibraries) {
					if (jsLibrary.isRequired()) {
						defaultModules.add(jsLibrary.getId());
					}
				}
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of fetchDefaultModules()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Feature fetchDefaultModules");
		}
		
		return SUCCESS;
	}

	public String checkDependency() {
		try {
			List<ModuleGroup> allModules = getAllModule();

			for (ModuleGroup module : allModules) {
				if (module.getId().equals(moduleId)) {
					Module checkedVersion = module.getVersion(version);
					if (StringUtils.isNotEmpty(preVersion)) {
						Module preVerModule = module.getVersion(preVersion);
						preDependentIds = ApplicationsUtil.getIds(preVerModule
								.getDependentModules());
						preDependentVersions = ApplicationsUtil
								.getDependentVersions();
					}
					if (checkedVersion != null) {
						dependentIds = ApplicationsUtil.getIds(checkedVersion
								.getDependentModules());
						dependentVersions = ApplicationsUtil
								.getDependentVersions();
					}
				}
			}

		} catch (PhrescoException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Features.checkDependency()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Feature Select Dependency");
		}
		return SUCCESS;
	}

	public List<ModuleGroup> getAllModule() throws PhrescoException {
		ProjectAdministrator administrator = PhrescoFrameworkFactory
				.getProjectAdministrator();
		Technology technology = administrator.getTechnology(techId);
		if (REQ_CORE_MODULE.equals(moduleType)) {
			return administrator.getCoreModules(technology);
		}

		if (REQ_CUSTOM_MODULE.equals(moduleType)) {
			return administrator.getCustomModules(technology);
		}

		if (REQ_JSLIB_MODULE.equals(moduleType)) {
			return technology.getJsLibraries();
		}

		return null;
	}

	public Collection<String> getDependentIds() {
		return dependentIds;
	}

	public void setDependentIds(Collection<String> dependentIds) {
		this.dependentIds = dependentIds;
	}

	public Collection<String> getDependentVersions() {
		return dependentVersions;
	}

	public void setDependentVersions(Collection<String> dependentVersions) {
		this.dependentVersions = dependentVersions;
	}

	public Collection<String> getPreDependentIds() {
		return preDependentIds;
	}

	public void setPreDependentIds(Collection<String> dependentIds) {
		this.preDependentIds = dependentIds;
	}

	public Collection<String> getPreDependentVersions() {
		return preDependentVersions;
	}

	public void setPreDependentVersions(Collection<String> dependentVersions) {
		this.preDependentVersions = dependentVersions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPreVersion() {
		return preVersion;
	}

	public void setPreVersion(String preVersion) {
		this.preVersion = preVersion;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
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

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public List<String> getTechVersion() {
		return techVersion;
	}

	public void setTechVersion(List<String> techVersion) {
		this.techVersion = techVersion;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	
	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}
	
	public List<String> getDefaultModules() {
		return defaultModules;
	}

	public void setDefaultModules(List<String> defaultModules) {
		this.defaultModules = defaultModules;
	}
}