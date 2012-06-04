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

	
    
    
}
