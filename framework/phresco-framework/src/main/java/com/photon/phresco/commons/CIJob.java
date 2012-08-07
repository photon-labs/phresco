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
}
