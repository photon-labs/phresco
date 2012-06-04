/*
 * ###
 * Phresco Framework
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
package com.photon.phresco.framework.api;

import java.io.BufferedReader;
import java.util.Map;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.EnvironmentInfo;


public interface ProjectRuntimeManager {
	
	void build(Project project) throws PhrescoException;
	BufferedReader deploy(Project project, EnvironmentInfo info) throws PhrescoException;
	
	BufferedReader performAction(Project project, ActionType action, Map<String, String> param, CallBack callBack) throws PhrescoException;
	
	BufferedReader setupCI(Project project) throws PhrescoException;
	
	BufferedReader startJenkins(Project project) throws PhrescoException;
	
	BufferedReader stopJenkins(Project project) throws PhrescoException;
	
	StringBuilder buildMavenCommand(Project project, ActionType actionType, Map<String, String> paramsMap) throws PhrescoException;
	
	StringBuilder buildMavenArgCommand(ActionType actionType, Map<String, String> paramsMap) throws PhrescoException;
	
//	BuildInfo configure(Project project) throws PhrescoException;

}
