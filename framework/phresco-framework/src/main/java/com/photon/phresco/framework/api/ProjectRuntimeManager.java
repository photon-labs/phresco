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
