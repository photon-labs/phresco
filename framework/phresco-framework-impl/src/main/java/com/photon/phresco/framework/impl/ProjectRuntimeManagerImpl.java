/*
 * ###
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.actions.IPhoneFunctionalCommand;
import com.photon.phresco.framework.actions.IphoneBuildAndUnitTest;
import com.photon.phresco.framework.actions.IphoneCodeValidate;
import com.photon.phresco.framework.actions.MobileCommand;
import com.photon.phresco.framework.actions.Sonar;
import com.photon.phresco.framework.actions.AndroidPerfCommand;
import com.photon.phresco.framework.actions.IphoneIpa;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.CallBack;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectRuntimeManager;
import com.photon.phresco.model.EnvironmentInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;

public class ProjectRuntimeManagerImpl implements ProjectRuntimeManager {
	private static final Logger S_LOGGER = Logger.getLogger(ProjectRuntimeManagerImpl.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	private static Map<String, String> pluginMap = new HashMap<String, String>();
	
	static {
	    pluginMap.put(TechnologyTypes.PHP_DRUPAL7, Constants.MVN_PLUGIN_DRUPAL_ID);
	    pluginMap.put(TechnologyTypes.PHP_DRUPAL6, Constants.MVN_PLUGIN_DRUPAL_ID);
	    pluginMap.put(TechnologyTypes.PHP, Constants.MVN_PLUGIN_PHP_ID);
	    pluginMap.put(TechnologyTypes.ANDROID_HYBRID, Constants.MVN_PLUGIN_ANDROID_ID);
	    pluginMap.put(TechnologyTypes.ANDROID_NATIVE, Constants.MVN_PLUGIN_ANDROID_ID);
	    pluginMap.put(TechnologyTypes.JAVA_WEBSERVICE, Constants.MVN_PLUGIN_JAVA_ID);
	    pluginMap.put(TechnologyTypes.HTML5_WIDGET, Constants.MVN_PLUGIN_JAVA_ID);
	    pluginMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, Constants.MVN_PLUGIN_JAVA_ID);
	    pluginMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, Constants.MVN_PLUGIN_NODEJS_ID);
	    pluginMap.put(TechnologyTypes.HTML5, Constants.MVN_PLUGIN_JAVA_ID);
	    pluginMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, Constants.MVN_PLUGIN_JAVA_ID);
	    pluginMap.put(TechnologyTypes.SHAREPOINT,Constants.MVN_PLUGIN_SHAREPOINT_ID);
	    pluginMap.put(TechnologyTypes.IPHONE_NATIVE,Constants.MVN_PLUGIN_IPHONE_ID);
	    pluginMap.put(TechnologyTypes.IPHONE_HYBRID,Constants.MVN_PLUGIN_IPHONE_ID);
	    pluginMap.put(TechnologyTypes.DOT_NET, Constants.MVN_PLUGIN_SHAREPOINT_ID);
	    pluginMap.put(TechnologyTypes.WORDPRESS, Constants.MVN_PLUGIN_WORDPRESS_ID);
	    pluginMap.put(TechnologyTypes.JAVA_STANDALONE, Constants.MVN_PLUGIN_JAVA_ID);
	}
	
	/**
	 * This method is to build the given project
	 */
	public void build(Project project) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectRuntimeManagerImpl.build(Project project)");
		}
		Commandline cl = new Commandline("mvn com.photon.phresco.plugin:php:1.0-SNAPSHOT:deploy");

        cl.setWorkingDirectory("C:\\office\\work\\phresco\\framework");
		try {
			Process p = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line = null;
	        while ((line = in.readLine()) != null) {                   
	      	  System.out.println(line);
	        }
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	
	/**
	 * This method is to deploy the given project
	 * 
	 * @return execution stream will be returned
	 */
	public BufferedReader deploy(Project project, EnvironmentInfo info) throws PhrescoException {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectRuntimeManagerImpl.deploy(Project project, EnvironmentInfo info)");
		}
		StringBuilder builder = new StringBuilder(Constants.MVN_COMMAND);
		builder.append(Constants.SPACE);
		builder.append(Constants.MVN_PLUGIN_DRUPAL_ID);
		builder.append(Constants.STR_COLON);
		builder.append(Constants.MVN_GOAL_DEPLOY);
		builder.append(Constants.SPACE);
		if (DebugEnabled) {
			S_LOGGER.debug("deploy() SettingsInformation = " +info.getSettingsInfos());
		}
		if (info == null || CollectionUtils.isEmpty(info.getSettingsInfos())) {
		    throw new PhrescoException("Deployment information should not be empty");
		}
		
		List<SettingsInfo> settingsInfos = info.getSettingsInfos();
		
		SettingsInfo serverSettingsInfo = getServerSettingsInfo(settingsInfos);		
		builder.append(FrameworkConstants.DEPLOY_SERVER_PARAM);
        builder.append(FrameworkConstants.KEY_QUOTES + serverSettingsInfo.getName() + FrameworkConstants.KEY_QUOTES);
        builder.append(Constants.SPACE);
        
        SettingsInfo dbSettingsInfo = getDBSettingsInfo(settingsInfos);
		builder.append(FrameworkConstants.DEPLOY_DATABASE_PARAM);
        builder.append(FrameworkConstants.KEY_QUOTES + dbSettingsInfo.getName() + FrameworkConstants.KEY_QUOTES);
        if (DebugEnabled) {
			S_LOGGER.debug("deploy() Builder Information = " +builder.toString());
		}
		Commandline cl = new Commandline(builder.toString());
		cl.setWorkingDirectory(Utility.getProjectHome() + project.getProjectInfo().getCode());
		if (DebugEnabled) {
				S_LOGGER.debug("deploy() Working Directiory = " +cl.getWorkingDirectory());
		}
		
		Process p;
		try {
			p = cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}
		
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}


    private SettingsInfo getServerSettingsInfo(List<SettingsInfo> settingsInfos) {
        for (SettingsInfo settingsInfo : settingsInfos) {
            if (Constants.SETTINGS_TEMPLATE_SERVER.equals(settingsInfo.getType())) {
                return settingsInfo;
            }
        }
        
        return null;
    }
    
    private SettingsInfo getDBSettingsInfo(List<SettingsInfo> settingsInfos) {
        for (SettingsInfo settingsInfo : settingsInfos) {
            if (Constants.SETTINGS_TEMPLATE_DB.equals(settingsInfo.getType())) {
                return settingsInfo;
            }
        }
        
        return null;
    }

    @Override
    public BufferedReader performAction(Project project, ActionType action, Map<String, String> paramsMap, CallBack callBack) throws PhrescoException {
        //TODO: This needs to be refactored
    	if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectRuntimeManagerImpl.performAction(" +
					"Project project, ActionType action, Map<String, String> paramsMap,CallBack callBack)");
			S_LOGGER.debug("performAction() ProjectInformation = "+project.getProjectInfo());
		}
    	StringBuilder command = action.getCommand();
    	if (action.getCommand() == null) {
    		command = buildMavenCommand(project, action, paramsMap);
    	}
    	
    	if (action instanceof MobileCommand || action instanceof AndroidPerfCommand || action instanceof Sonar || action instanceof IphoneIpa || action instanceof IPhoneFunctionalCommand || action instanceof IphoneBuildAndUnitTest  || action instanceof IphoneCodeValidate) {
    		command.append(" " + buildMavenArgCommand(action, paramsMap));
    	}
    	return executeMavenCommand(project, action, command);
    }

    private BufferedReader executeMavenCommand(Project project, ActionType action, StringBuilder command) throws PhrescoException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ProjectRuntimeManagerImpl.executeMavenCommand(Project project, ActionType action, StringBuilder command)");
    		S_LOGGER.debug("executeMavenCommand() Project Code = " + project.getProjectInfo().getCode());
    		S_LOGGER.debug("executeMavenCommand() Command = " + command.toString());
    		S_LOGGER.debug("executeMavenCommand() ActionType Name = " + action.getName());
    		S_LOGGER.debug("executeMavenCommand() ActionType Working directory = " + action.getWorkingDirectory());
		}
		Commandline cl = new Commandline(command.toString());
        String workingDirectory = action.getWorkingDirectory();
        if (StringUtils.isNotEmpty(workingDirectory)) {
            cl.setWorkingDirectory(workingDirectory);
        } else {
            cl.setWorkingDirectory(Utility.getProjectHome() + project.getProjectInfo().getCode());
        }
        try {
            Process process = cl.execute();
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (CommandLineException e) {
            throw new PhrescoException(e);
        }
    }

    public StringBuilder buildMavenCommand(Project project, ActionType actionType, Map<String, String> paramsMap) {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ProjectRuntimeManagerImpl.buildMavenCommand(Project project, ActionType actionType, Map<String, String> paramsMap)");
		}
        StringBuilder builder = new StringBuilder(Constants.MVN_COMMAND);
        builder.append(Constants.SPACE);
    	if (DebugEnabled) {
    		S_LOGGER.debug("buildMavenCommand() Project Code = " + project.getProjectInfo().getCode());
		}
        String techId = pluginMap.get(project.getProjectInfo().getTechnology().getId());
        if (StringUtils.isNotEmpty(techId)) {
            builder.append(techId);
        }
        
        builder.append(actionType.getName());
        builder.append(Constants.SPACE);
        builder.append(buildMavenArgCommand(actionType, paramsMap));
        
        return builder;
    }
    
    public StringBuilder buildMavenArgCommand(ActionType actionType, Map<String, String> paramsMap) {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ProjectRuntimeManagerImpl.buildMavenArgCommand(ActionType actionType, Map<String, String> paramsMap)");
		}

    	StringBuilder builder = new StringBuilder();
        if (actionType.canHideLog()) {
            builder.append("-q");
            builder.append(Constants.SPACE);
        }
        
        if (actionType.canShowError()) {
            builder.append("-e");
            builder.append(Constants.SPACE);
        }
        
        if (actionType.canShowDebug()) {
            builder.append("-X");
            builder.append(Constants.SPACE);
        }
        
        if (actionType.canSkipTest()) {
            builder.append("-DskipTests=true");
            builder.append(Constants.SPACE);
        } else {
        	builder.append("-DskipTests=false");
        	builder.append(Constants.SPACE);
        }

        if (StringUtils.isNotEmpty(actionType.getProfileId())) {
            builder.append("-P");
            builder.append(actionType.getProfileId());
            builder.append(Constants.SPACE);
        }
        
        if (paramsMap == null || paramsMap.isEmpty()) {
            return builder;
        }
        
        Set<String> keys = paramsMap.keySet();
        for (String key : keys) {
            String value = paramsMap.get(key);
            builder.append(Constants.STR_MINUSD);
            builder.append(key);
            builder.append(Constants.STR_EQUAL);
            builder.append(Constants.STR_DOUBLE_QUOTES);
            builder.append(value);
            builder.append(Constants.STR_DOUBLE_QUOTES);
            builder.append(Constants.SPACE);
        }
        
        if (DebugEnabled) {
        	S_LOGGER.debug("buildMavenArgCommand() Maven Arguments =" + builder);
		}
        return builder;
    }

    @Override
    public BufferedReader setupCI(Project project) throws PhrescoException {
        List<String> commands = new ArrayList<String>();
        commands.add(FrameworkConstants.MVN_INSTALL_COMMAND);
        return executeJenkinsProcess(commands);
    }

    @Override
    public BufferedReader startJenkins(Project project) throws PhrescoException {
        List<String> commands = new ArrayList<String>();
        commands.add(FrameworkConstants.MVN_JENKINS_START);
        return executeJenkinsProcess(commands);
    }

    @Override
    public BufferedReader stopJenkins(Project project) throws PhrescoException {
        List<String> commands = new ArrayList<String>();
        commands.add(FrameworkConstants.MVN_JENKINS_STOP);
        return executeJenkinsProcess(commands);
    }
    
    private void executeMavenCommand() {
        Commandline cl = new Commandline("mvn t7:run-forked");
        cl.setWorkingDirectory("C:\\download\\workspace\\tools\\jenkins");
        try {
            Process p = cl.execute();
            writeConsole(p);
        } catch (CommandLineException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void writeConsole(Process process) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
//            if (line.contains("Starting Tomcat")) {
//                System.out.println("Process stopped");
//                process.destroy();
//            }
        }
        
        in.close();
    }

    private BufferedReader executeJenkinsProcess(List<String> commands) throws PhrescoException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(new File("C:\\download\\workspace\\tools\\jenkins"));
        try {
            Process process = processBuilder.start();
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
    }
    
    public static void main(String[] args) {
        ProjectRuntimeManagerImpl manager = new ProjectRuntimeManagerImpl();
        manager.executeMavenCommand();
    }
}
