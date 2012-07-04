
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.framework.impl.EnvironmentComparator;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.I18NString;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.TechnologyTypes;

public class Configurations extends FrameworkBaseAction {
    private static final long serialVersionUID = -4883865658298200459L;
    
    private static final Logger S_LOGGER = Logger.getLogger(Configurations.class);
    private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
    private String configName = null;
    private String description = null;
    private String oldName = null;
    private String[] appliesto = null;
    private String projectCode = null;
    private String nameError = null;
    private String typeError = null;
    private String portError = null;
    private String dynamicError = "";
    private boolean isValidated = false;
    private String envName = null;
    private String configType = null;
    private String oldConfigType = null;
	private String envError = null;
	private String emailError = null;
	private String remoteDeployment = null;
    


	// Environemnt delete
    private boolean isEnvDeleteSuceess = true;
    private String envDeleteMsg = null;
    private List<String> projectInfoVersions = null;
    
	public String list() {
        if (S_LOGGER.isDebugEnabled()) {
        	S_LOGGER.debug("Configuration.list() entered");
        }
        
		Project project = null;
    	try {
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            project = administrator.getProject(projectCode);
            List<Environment> environments = administrator.getEnvironments(project);
            getHttpRequest().setAttribute(ENVIRONMENTS, environments);
            List<SettingsInfo> configurations = administrator.configurations(project);
            for (SettingsInfo configuration : configurations) {
            	List<PropertyInfo> propertyInfos = configuration.getPropertyInfos();
            	for (PropertyInfo propertyInfo : propertyInfos) {
					propertyInfo.getValue();
				}
            }
            getHttpRequest().setAttribute(REQ_CONFIGURATION, configurations);
        } catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.list()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations list");
        }
        
        getHttpRequest().setAttribute(REQ_PROJECT, project);
        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return APP_LIST;
    }
    
    public String add() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.add()");
    	}

        getHttpSession().removeAttribute(REQ_CONFIG_INFO);
        try {
            ProjectAdministrator administrator = getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            List<SettingsTemplate> settingsTemplates = administrator.getSettingsTemplates();
            getHttpSession().setAttribute(SESSION_SETTINGS_TEMPLATES, settingsTemplates);
            List<Environment> environments = administrator.getEnvironments(project);
            getHttpRequest().setAttribute(ENVIRONMENTS, environments);
            Collections.sort(environments, new EnvironmentComparator());
            getHttpSession().removeAttribute(ERROR_SETTINGS);
            getHttpRequest().setAttribute(REQ_PROJECT, project);
        } catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.add()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations add");
        }
        
        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return APP_CONFIG_ADD;
    }
    
    public String save() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.save()");
		}  
        try {
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            if(!validate(administrator, null)) {
                isValidated = true;
                return Action.SUCCESS;
            }
            SettingsTemplate selectedSettingTemplate = administrator.getSettingsTemplate(configType);
            List<PropertyInfo> propertyInfoList = new ArrayList<PropertyInfo>();
            List<PropertyTemplate> propertyTemplates = selectedSettingTemplate.getProperties();
            String key = null;
            String value = null;
            for (PropertyTemplate propertyTemplate : propertyTemplates) {
            	if (propertyTemplate.getKey().equals(Constants.SETTINGS_TEMPLATE_SERVER) || propertyTemplate.getKey().equals(Constants.SETTINGS_TEMPLATE_DB)) {
            		List<PropertyTemplate> compPropertyTemplates = propertyTemplate.getpropertyTemplates();
            		for (PropertyTemplate compPropertyTemplate : compPropertyTemplates) {
            			key = compPropertyTemplate.getKey();
            			value = getHttpRequest().getParameter(key);
            			if (propertyTemplate.getKey().equals(Constants.SETTINGS_TEMPLATE_DB) && key.equals("type")) {
            				value = value.trim().toLowerCase();
            				propertyInfoList.add(new PropertyInfo(key, value.trim()));
            			} else {
            				propertyInfoList.add(new PropertyInfo(key, value.trim()));
            			}
					}
            	} else {
            		key = propertyTemplate.getKey();
            		value = getHttpRequest().getParameter(key);
            		 if(key.equals("remoteDeployment") && value == null){
                     		value="false";
                     }
                    value = value.trim();
					if(key.equals(ADDITIONAL_CONTEXT_PATH)){
                    	String addcontext = value;
                    	if(!addcontext.startsWith("/")) {
                    		value = "/" + value;
                    	}
                    }
                    propertyInfoList.add(new PropertyInfo(propertyTemplate.getKey(), value));
            	}
                if (S_LOGGER.isDebugEnabled()) {
                	S_LOGGER.debug("Configuration.save() key " + propertyTemplate.getKey() + "and Value is " + value);
                }
            }
            SettingsInfo settingsInfo = new SettingsInfo(configName, description, configType);
            settingsInfo.setAppliesTo(Arrays.asList(project.getProjectInfo().getTechnology().getId()));
            settingsInfo.setPropertyInfos(propertyInfoList);
            getHttpRequest().setAttribute(REQ_CONFIG_INFO, settingsInfo);
            getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
            getHttpRequest().setAttribute(REQ_PROJECT, project);
            String[] environments = getHttpRequest().getParameterValues(ENVIRONMENTS);

            StringBuilder sb = new StringBuilder();
            for (String envs : environments) { 
                if (sb.length() > 0) sb.append(',');
                sb.append(envs);
            }
            administrator.createConfiguration(settingsInfo, sb.toString(), project);
            if (SERVER.equals(configType)){
				addActionMessage(getText(SUCCESS_SERVER, Collections.singletonList(configName)));
			}
			else if (DATABASE.equals(configType)) {
				addActionMessage(getText(SUCCESS_DATABASE, Collections.singletonList(configName)));
			}
			else if (WEBSERVICE.equals(configType)) {
				addActionMessage(getText(SUCCESS_WEBSERVICE, Collections.singletonList(configName)));
			}
			else {
				addActionMessage(getText(SUCCESS_EMAIL, Collections.singletonList(configName)));
			}
            getHttpSession().removeAttribute(ERROR_SETTINGS);
        } catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.save()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations save");
        }
        
        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return list();
    }
    
    public String createEnvironment() {
    	try {
            String[] split = null;
            ProjectAdministrator administrator = PhrescoFrameworkFactory
                    .getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            String envs = getHttpRequest().getParameter(ENVIRONMENT_VALUES);
            String selectedItems = getHttpRequest().getParameter("deletableEnvs");
            if(StringUtils.isNotEmpty(selectedItems)){
            	deleteEnvironment(selectedItems);
    	    }
            
            List<Environment> environments = new ArrayList<Environment>();
            if (StringUtils.isNotEmpty(envs)) {
                List<String> listSelectedEnvs = new ArrayList<String>(
                        Arrays.asList(envs.split("#SEP#")));
                for (String listSelectedEnv : listSelectedEnvs) {
                    try {
                        split = listSelectedEnv.split("#DSEP#");
                        environments.add(new Environment(split[0], split[1],
                                false));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        environments.add(new Environment(split[0], "", false));
                    }
                }
            }
	    	administrator.createEnvironments(project, environments, false);
	    	
			if(StringUtils.isNotEmpty(selectedItems) && CollectionUtils.isNotEmpty(environments)) {
				addActionMessage(getText(UPDATE_ENVIRONMENT));
			} else if(StringUtils.isNotEmpty(selectedItems) && CollectionUtils.isEmpty(environments)){
				addActionMessage(getText(DELETE_ENVIRONMENT));
			} else if(CollectionUtils.isNotEmpty(environments) && StringUtils.isEmpty(selectedItems)) {
				addActionMessage(getText(CREATE_SUCCESS_ENVIRONMENT));
			}
    	} catch(Exception e) {
    		if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of Configurations.createEnvironment()" + FrameworkUtil.getStackTraceAsString(e));
     		}
    		addActionMessage(getText(CREATE_FAILURE_ENVIRONMENT));
    	}
    	return list();
    }
    
    public String deleteEnvironment(String selectedItems) {
    	try {
    		ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
	    	List<String> envNames = Arrays.asList(selectedItems.split(","));
	    	List<String> deletableEnvs = new ArrayList<String>();
	    	for (String envName : envNames) {
				// Check if configurations are already exist
				List<SettingsInfo> configurations = administrator.configurationsByEnvName(envName, project);
                if (CollectionUtils.isEmpty(configurations)) {
                	deletableEnvs.add(envName);
                }
			}
	    	if (isEnvDeleteSuceess == true) {
	    		// Delete Environment
	    		administrator.deleteEnvironments(deletableEnvs, project);
	    	}
    	} catch(Exception e) {
    		if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of Configurations.deleteEnvironment()" + FrameworkUtil.getStackTraceAsString(e));
     		}
    	}
    	return SUCCESS;
    }
    
    public String checkForRemove(){
		try {
    		ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
    		Project project = administrator.getProject(projectCode);
    		String removeItems = getHttpRequest().getParameter(DELETABLE_ENVS);
    		List<String> unDeletableEnvs = new ArrayList<String>();
	    	List<String> envs = Arrays.asList(removeItems.split(","));
	    	for (String env : envs) {
				// Check if configurations are already exist
				List<SettingsInfo> configurations = administrator.configurationsByEnvName(env, project);
                if (CollectionUtils.isNotEmpty(configurations)) {
                	unDeletableEnvs.add(env);
                	if(unDeletableEnvs.size() > 1){
                		setEnvError(getText(ERROR_ENVS_REMOVE, Collections.singletonList(unDeletableEnvs)));
                	} else{
                		setEnvError(getText(ERROR_ENV_REMOVE, Collections.singletonList(unDeletableEnvs)));
                	}
                }
			}
    	} catch(Exception e) {
                S_LOGGER.error("Entered into catch block of Configurations.checkForRemove()" + FrameworkUtil.getStackTraceAsString(e));
    	}
		return SUCCESS;
	}
    
    public String checkDuplicateEnv() throws PhrescoException {
        try {
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            String envs = getHttpRequest().getParameter(ENVIRONMENT_VALUES);
            Collection<String> availableConfigEnvs = administrator.getEnvNames(project);
            for (String env : availableConfigEnvs) {
                if(env.equalsIgnoreCase(envs)) {
                    setEnvError(getText(ERROR_ENV_DUPLICATE, Collections.singletonList(envs)));
                }
            }
            availableConfigEnvs = administrator.getEnvNames();
            for (String env : availableConfigEnvs) {
                if(env.equalsIgnoreCase(envs)){
                    setEnvError(getText(ERROR_DUPLICATE_NAME_IN_SETTINGS, Collections.singletonList(envs)));
                }
            }

        } catch(Exception e) {
            throw new PhrescoException(e);
        }
        return SUCCESS;
    }
    
    private boolean validate(ProjectAdministrator administrator, String fromPage) throws PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.validate(ProjectAdministrator administrator, String fromPage)");
    		S_LOGGER.debug("validate() Frompage = " + fromPage);
		}
    	boolean validate = true;
    	String[] environments = getHttpRequest().getParameterValues(ENVIRONMENTS);
    	
    	if (StringUtils.isEmpty(configType)) {
    		setTypeError(getText(NO_CONFIG_TYPE));
    		return false;
    	}
    	
    	if (StringUtils.isEmpty(configName.trim())) {
	   		setNameError(ERROR_NAME);
	   		validate = false;
	   	}
    	
    	if (ArrayUtils.isEmpty(environments)) {
	   		setEnvError(ERROR_ENV);
	   		return false;
	   	}
    	
    	Project project = administrator.getProject(projectCode);
    	if(StringUtils.isNotEmpty(configName) && !configName.equals(oldName)) {
    		for (String environment : environments) {
    			List<SettingsInfo> configurations = administrator.configurationsByEnvName(environment, project);
        		for (SettingsInfo configuration : configurations) {
					if(configName.trim().equalsIgnoreCase(configuration.getName())) {
						setNameError(ERROR_DUPLICATE_NAME);
						validate = false;
					}
				}
    		}
    	}
    	
    	if (StringUtils.isEmpty(fromPage) || (StringUtils.isNotEmpty(fromPage) && !configType.equals(oldConfigType))) {
		    if (configType.equals(Constants.SETTINGS_TEMPLATE_SERVER) || configType.equals(Constants.SETTINGS_TEMPLATE_EMAIL)) {
		        for (String env : environments) {
		            List<SettingsInfo> settingsinfos = administrator.configurations(project, env, configType, oldName);
		            if(CollectionUtils.isNotEmpty( settingsinfos)) {
		                setTypeError(CONFIG_ALREADY_EXIST);
		                validate = false;
		            }
		        }
	    	}
    	}
	
    	SettingsTemplate selectedSettingTemplate = administrator.getSettingsTemplate(configType);
    	
    	boolean serverTypeValidation = false;
    	for (PropertyTemplate propertyTemplate : selectedSettingTemplate.getProperties()) {
    		String key = null;
    		String value = null;
    		if (propertyTemplate.getKey().equals("Server") || propertyTemplate.getKey().equals("Database")) {
        		List<PropertyTemplate> compPropertyTemplates = propertyTemplate.getpropertyTemplates();
        		for (PropertyTemplate compPropertyTemplate : compPropertyTemplates) {
        			key = compPropertyTemplate.getKey();
        			value = getHttpRequest().getParameter(key);
        			 //If nodeJs server selected , there should not be valition for deploy dir.
                    if ("type".equals(key) && "NodeJS".equals(value)) {
                    	serverTypeValidation = true;
                    }
				}
        	} else {
        		key = propertyTemplate.getKey();
        	}
    	    value = getHttpRequest().getParameter(key);
            boolean isRequired = propertyTemplate.isRequired();
            String techId = project.getProjectInfo().getTechnology().getId();
            if (serverTypeValidation && "deploy_dir".equals(key) || TechnologyTypes.ANDROIDS.contains(techId)) {
            	isRequired = false;
            }
            // validation for UserName & Password for RemoteDeployment
            boolean remoteDeply = Boolean.parseBoolean(remoteDeployment);
            if(remoteDeply){
                if ("admin_username".equals(key) || "admin_password".equals(key)) {
                	isRequired = true;
                }
                if("deploy_dir".equals(key)){
                	isRequired = false;
                }
            }
            
            if(isRequired == true && StringUtils.isEmpty(value.trim())){
            	I18NString i18NString = propertyTemplate.getName();
                String field = i18NString.get("en-US").getValue();
                dynamicError += propertyTemplate.getKey() + ":" + field + " is empty" + ",";
            }
        }
    	
	   	if (StringUtils.isNotEmpty(dynamicError)) {
	        dynamicError = dynamicError.substring(0, dynamicError.length() - 1);
	        setDynamicError(dynamicError);
	        validate = false;
	   	}
	   	
	   	if (StringUtils.isNotEmpty(getHttpRequest().getParameter("port"))) {
		   	int value = Integer.parseInt(getHttpRequest().getParameter("port"));
		   	if (validate && (value < 1 || value > 65535)) {
			   	setPortError(ERROR_PORT);
			   	validate = false;
		   	}
	   	}
	   	
	   	if (StringUtils.isNotEmpty(getHttpRequest().getParameter("emailid"))) {
	   		String value = getHttpRequest().getParameter("emailid");
	   		Pattern p=Pattern.compile("[a-zA-Z]*[0-9]*@[a-zA-Z]*.[a-zA-Z]*");
	   		Matcher m=p.matcher(value);
	   		boolean b=m.matches();
	   		if(b==false)
	   		{
	   			setEmailError(ERROR_EMAIL);
	   			validate = false;
	   		}
	   	}

	   	return validate;
    }
    
    public String edit() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.edit()");
    	}
        try {
            ProjectAdministrator administrator = getProjectAdministrator();
            List<SettingsTemplate> settingsTemplates = administrator.getSettingsTemplates();
            getHttpSession().setAttribute(SESSION_SETTINGS_TEMPLATES, settingsTemplates);
            Project project = administrator.getProject(projectCode);
            SettingsInfo selectedConfigInfo = administrator.configuration(oldName, getEnvName(), project);

        	if (debugEnabled) {
        		S_LOGGER.debug("Configurations.edit() old name" + oldName);
        	}

        	List<Environment> environments = administrator.getEnvironments(project);
            getHttpRequest().setAttribute(ENVIRONMENTS, environments);
            getHttpRequest().setAttribute(SESSION_OLD_NAME, oldName);
            getHttpRequest().setAttribute(REQ_CONFIG_INFO, selectedConfigInfo);
            getHttpRequest().setAttribute(REQ_FROM_PAGE, FROM_PAGE);
            getHttpRequest().setAttribute(REQ_PROJECT, project);
            getHttpSession().removeAttribute(ERROR_SETTINGS);
            getHttpRequest().setAttribute("currentEnv", envName);
        } catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.edit()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations edit");
        }

        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return APP_CONFIG_EDIT;
    }
    
    public String update() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.update()");
		}  
        try {
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            SettingsTemplate selectedSettingTemplate = administrator.getSettingsTemplate(configType);
            List<PropertyInfo> propertyInfoList = new ArrayList<PropertyInfo>();
            List<PropertyTemplate> propertyTemplates = selectedSettingTemplate.getProperties();
            String key = null;
            String value = null;
            for (PropertyTemplate propertyTemplate : propertyTemplates) {
            	if (propertyTemplate.getKey().equals(Constants.SETTINGS_TEMPLATE_SERVER) || propertyTemplate.getKey().equals(Constants.SETTINGS_TEMPLATE_DB)) {
            		List<PropertyTemplate> compPropertyTemplates = propertyTemplate.getpropertyTemplates();
            		for (PropertyTemplate compPropertyTemplate : compPropertyTemplates) {
            			key = compPropertyTemplate.getKey();
            			value = getHttpRequest().getParameter(key);
            			if (propertyTemplate.getKey().equals(Constants.SETTINGS_TEMPLATE_DB) && key.equals("type")) {
            				value = value.trim().toLowerCase();
            				propertyInfoList.add(new PropertyInfo(key, value.trim()));
            			} else {
            				propertyInfoList.add(new PropertyInfo(key, value.trim()));
            			}
					}
            	} else {
	                value = getHttpRequest().getParameter(propertyTemplate.getKey());
   	                if(propertyTemplate.getKey().equals("remoteDeployment") && value == null){
                        		value="false";
                        }
	                value = value.trim();
	                propertyInfoList.add(new PropertyInfo(propertyTemplate.getKey(), value));
            	}
            }
            SettingsInfo settingsInfo = new SettingsInfo(configName, description, configType);
            settingsInfo.setPropertyInfos(propertyInfoList);
            settingsInfo.setAppliesTo(appliesto != null ? Arrays.asList(appliesto):null);
        	if (debugEnabled) {
        		S_LOGGER.debug("Settings Info object value" + settingsInfo.toString());
        	}
            getHttpRequest().setAttribute(REQ_CONFIG_INFO, settingsInfo);
            getHttpRequest().setAttribute(REQ_PROJECT, project);
            if(!validate(administrator, FROM_PAGE)) {
            	isValidated = true;
            	getHttpRequest().setAttribute(REQ_FROM_PAGE, FROM_PAGE);
            	getHttpRequest().setAttribute(REQ_OLD_NAME, oldName);
            	return Action.SUCCESS;
            }
            String environment = getHttpRequest().getParameter(ENVIRONMENTS);
            administrator.updateConfiguration(environment, oldName, settingsInfo, project);
            addActionMessage("Configuration updated successfully");

        } catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.update()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations update");
        }
        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return list();
    }
    
    public String delete() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.delete()");
		}  
    	
    	try {
            ProjectAdministrator administrator = getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            addActionMessage(getText(SUCCESS_CONFIG_DELETE));
            getHttpRequest().setAttribute(REQ_PROJECT, project);
            List<String> configurationNames = new ArrayList<String>();
            String[] selectedNames = getHttpRequest().getParameterValues(REQ_SELECTED_ITEMS);
            Map<String, List<String>> deleteConfigs = new HashMap<String , List<String>>();
        
            for(int i = 0; i < selectedNames.length; i++){
            	String[] split = selectedNames[i].split(",");
            	String envName = split[0];
            	List<String> configNames = deleteConfigs.get(envName);
            	if (configNames == null) {
            		configNames = new ArrayList<String>(3);
            	}
            	configNames.add(split[1]);
            	deleteConfigs.put(envName, configNames);
            }
            administrator.deleteConfigurations(deleteConfigs, project);
        for (String name : selectedNames) {
            configurationNames.add(name);
        }
        
        } catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.delete()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations delete");
        }
        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return list();
    }
    
    public String configurationsType() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Settings.settingsType()");
		}
		try {
			String projectCode = (String)getHttpRequest().getParameter(REQ_PROJECT_CODE);
			String oldName = (String)getHttpRequest().getParameter(REQ_OLD_NAME);
			ProjectAdministrator administrator = getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			SettingsTemplate settingsTemplate = administrator.getSettingsTemplate(configType);
			if(Constants.SETTINGS_TEMPLATE_SERVER.equals(configType)) {
				List<Server> projectInfoServers = project.getProjectInfo().getTechnology().getServers();
				getHttpRequest().setAttribute(REQ_PROJECT_INFO_SERVERS, projectInfoServers);
			}
			if(Constants.SETTINGS_TEMPLATE_DB.equals(configType)) {
				List<Database> projectInfoDatabases = project.getProjectInfo().getTechnology().getDatabases();
				getHttpRequest().setAttribute(REQ_PROJECT_INFO_DATABASES, projectInfoDatabases);
			}
			
			SettingsInfo selectedConfigInfo = null;
			if(StringUtils.isNotEmpty(oldName)) {
				selectedConfigInfo = administrator.configuration(oldName, getEnvName(), project);
			} else {
				selectedConfigInfo = (SettingsInfo)getHttpRequest().getAttribute(REQ_CONFIG_INFO);
			}

			getHttpRequest().setAttribute(REQ_PROJECT, project);
			getHttpRequest().setAttribute(REQ_CURRENT_SETTINGS_TEMPLATE, settingsTemplate);
			getHttpRequest().setAttribute(REQ_ALL_TECHNOLOGIES, administrator.getAllTechnologies());
            getHttpRequest().setAttribute(SESSION_OLD_NAME, oldName);
            getHttpRequest().setAttribute(REQ_CONFIG_INFO, selectedConfigInfo);
            getHttpRequest().setAttribute(REQ_FROM_PAGE, FROM_PAGE);
		} catch (Exception e) {
        	if (debugEnabled) {
               S_LOGGER.error("Entered into catch block of Configurations.configurationsType()" + FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Configurations type");
		}
		return SETTINGS_TYPE;
	}
    
    public String openEnvironmentPopup() {
    	ProjectAdministrator administrator;
		try {
			administrator = getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			List<Environment> enviroments = administrator.getEnvironments(project);
			getHttpRequest().setAttribute(ENVIRONMENTS, enviroments);
		} catch (PhrescoException e) {
			
		} catch (Exception e) {
		}
    	return APP_ENVIRONMENT;
    }
    
    public String fetchProjectInfoVersions() {
    	try {
	    	String configType = getHttpRequest().getParameter("configType");
	    	String name = getHttpRequest().getParameter("name");
	    	ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
	    	Project project = administrator.getProject(projectCode);
	    	Technology technology = project.getProjectInfo().getTechnology();
	    	if ("Server".equals(configType)) {
	    		List<Server> servers = technology.getServers();
	    		if (servers != null && CollectionUtils.isNotEmpty(servers)) {
	    			for (Server server : servers) {
						if (server.getName().equals(name)) {
							setProjectInfoVersions(server.getVersions());
						}
					}
	    		}
	    	}
	    	if ("Database".equals(configType)) {
	    		List<Database> databases = technology.getDatabases();
	    		if (databases != null && CollectionUtils.isNotEmpty(databases)) {
	    			for (Database database : databases) {
						if (database.getName().equals(name)) {
							setProjectInfoVersions(database.getVersions());
						}
					}
	    		}
	    	}
    	} catch (Exception e) {
    		
    	}
    	return SUCCESS;
    }
    
    public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getDescription() {
   		return description;
     
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

	public String[] getAppliesto() {
		return appliesto;
	}

	public void setAppliesto(String[] appliesto) {
		this.appliesto = appliesto;
	}
	
	public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    
    public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}
	
	public String getTypeError() {
        return typeError;
    }

    public void setTypeError(String typeError) {
        this.typeError = typeError;
    }
	
	public String getDynamicError() {
		return dynamicError;
	}

	public void setDynamicError(String dynamicError) {
		this.dynamicError = dynamicError;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	
	public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }
    
    public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}
	
	public String getEnvError() {
		return envError;
	}

	public void setEnvError(String envError) {
		this.envError = envError;
	}

	public boolean isEnvDeleteSuceess() {
		return isEnvDeleteSuceess;
	}

	public void setEnvDeleteSuceess(boolean isEnvDeleteSuceess) {
		this.isEnvDeleteSuceess = isEnvDeleteSuceess;
	}

	public String getEnvDeleteMsg() {
		return envDeleteMsg;
	}

	public void setEnvDeleteMsg(String envDeleteMsg) {
		this.envDeleteMsg = envDeleteMsg;
	}
	
	public List<String> getProjectInfoVersions() {
		return projectInfoVersions;
	}

	public void setProjectInfoVersions(List<String> projectInfoVersions) {
		this.projectInfoVersions = projectInfoVersions;
	}
	
	public String getOldConfigType() {
		return oldConfigType;
	}

	public void setOldConfigType(String oldConfigType) {
		this.oldConfigType = oldConfigType;
	}
	
	public String getPortError() {
		return portError;
	}

	public void setPortError(String portError) {
		this.portError = portError;
	}
	
	public String getRemoteDeployment() {
		return remoteDeployment;
	}

	public void setRemoteDeployment(String remoteDeployment) {
		this.remoteDeployment = remoteDeployment;
	}
	

	public String getEmailError() {
		return emailError;
	}

	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}

}