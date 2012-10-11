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
package com.photon.phresco.commons;

import java.util.List;
import java.util.Map;

public class CIJob {
    private String name;
    private String svnUrl;
    private String userName;
    private String password;
    private Map<String, String> email;
    private String scheduleType;
    private String scheduleExpression;
    private String mvnCommand;
    private String senderEmailId;
    private String senderEmailPassword;
    private String jenkinsUrl;
    private String jenkinsPort;
    private List<String> triggers;
    private String repoType;
    private String branch;
  //collabNet implementation
    private boolean enableBuildRelease = false;
    private String collabNetURL = "";
    private String collabNetusername = "";
    private String collabNetpassword = "";
    private String collabNetProject = "";
    private String collabNetPackage = "";
    private String collabNetRelease = "";
    private boolean collabNetoverWriteFiles = false;
    
    //  CI  automation
    // whether to clone the current jobs workspace for further reference
    private boolean cloneWorkspace = false;
    //when to call is optional - can be added
    // Down stream projects
    private String downStreamProject = "";
    // Whether this job is used cloned workspace or normal svn
    private String usedClonnedWorkspace = "";
    private String operation = "";// Operation like(Build, deploy, test)
    
    //Extra information for deploy and test
    private String browser = "";
    private String environment = "";
    private String importSql = "";
    
    //iphone specific data
    private String deployTo = "";
    private String simulatorVersion = "";
    private String iphoneSerialNumber = "";
    private String target = "";
    private String iphoneSdk = "";
    private String mode = "";
    
    // android specific data
    private String androidVersion = "";
    private String device = "";
    private String androidSerialNumber = "";
    private String proguard = "";
    private String signing = "";
    
    private String pomLocation = "";
    
    //java Stanalone tech info
    private String jarName = "";
    private String mainClassName = "";
    private boolean enablePostBuildStep = false;
    private String resolution = "";
    
    public CIJob() {
        super();
    }
    
    public CIJob(String name, String svnUrl, String userName, String password) {
        super();
        this.name = name;
        this.svnUrl = svnUrl;
        this.userName = userName;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSvnUrl() {
        return svnUrl;
    }
    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleExpression() {
        return scheduleExpression;
    }

    public void setScheduleExpression(String scheduleExpression) {
        this.scheduleExpression = scheduleExpression;
    }

	public Map<String, String> getEmail() {
		return email;
	}

	public void setEmail(Map<String, String> email) {
		this.email = email;
	}

	public String getMvnCommand() {
		return mvnCommand;
	}

	public void setMvnCommand(String mvnCommand) {
		this.mvnCommand = mvnCommand;
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

	public String getJenkinsUrl() {
		return jenkinsUrl;
	}

	public void setJenkinsUrl(String jenkinsUrl) {
		this.jenkinsUrl = jenkinsUrl;
	}

	public String getJenkinsPort() {
		return jenkinsPort;
	}

	public void setJenkinsPort(String jenkinsPort) {
		this.jenkinsPort = jenkinsPort;
	}

	public List<String> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<String> triggers) {
		this.triggers = triggers;
	}

	public String getRepoType() {
		return repoType;
	}

	public void setRepoType(String repoType) {
		this.repoType = repoType;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
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

	public boolean isCloneWorkspace() {
		return cloneWorkspace;
	}

	public void setCloneWorkspace(boolean cloneWorkspace) {
		this.cloneWorkspace = cloneWorkspace;
	}

	public String getDownStreamProject() {
		return downStreamProject;
	}

	public void setDownStreamProject(String downStreamProject) {
		this.downStreamProject = downStreamProject;
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
	
	public String getIphoneSerialNumber() {
		return iphoneSerialNumber;
	}

	public void setIphoneSerialNumber(String iphoneSerialNumber) {
		this.iphoneSerialNumber = iphoneSerialNumber;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getAndroidSerialNumber() {
		return androidSerialNumber;
	}

	public void setAndroidSerialNumber(String androidSerialNumber) {
		this.androidSerialNumber = androidSerialNumber;
	}

	public String getProguard() {
		return proguard;
	}

	public void setProguard(String proguard) {
		this.proguard = proguard;
	}

	public String getSigning() {
		return signing;
	}

	public void setSigning(String signing) {
		this.signing = signing;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIphoneSdk() {
		return iphoneSdk;
	}

	public void setIphoneSdk(String iphoneSdk) {
		this.iphoneSdk = iphoneSdk;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPomLocation() {
		return pomLocation;
	}

	public void setPomLocation(String pomLocation) {
		this.pomLocation = pomLocation;
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

	public boolean isEnablePostBuildStep() {
		return enablePostBuildStep;
	}

	public void setEnablePostBuildStep(boolean enablePostBuildStep) {
		this.enablePostBuildStep = enablePostBuildStep;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
}
