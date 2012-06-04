package com.photon.phresco.framework.impl;

import java.io.Serializable;

import com.photon.phresco.framework.api.Project;
import com.photon.phresco.model.ProjectInfo;

class ProjectImpl implements Project, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProjectInfo projectInfo;
	
	/*public ProjectImpl() {
		projectInfo = new ProjectInfo();
	}*/
	
	public ProjectImpl(ProjectInfo info) {
		projectInfo = info;
	}
	
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}
}
