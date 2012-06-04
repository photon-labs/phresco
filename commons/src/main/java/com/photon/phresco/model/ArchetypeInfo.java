/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.model;

public class ArchetypeInfo {

	private static final long serialVersionUID = 1L;
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArchetypeInfo(String id, String groupid, String artifactid,
			String version, String project_groupid) {
		super();
		this.id = id;
		this.groupid = groupid;
		this.artifactid = artifactid;
		this.version = version;
		this.project_groupid = project_groupid;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getArtifactid() {
		return artifactid;
	}
	public void setArtifactid(String artifactid) {
		this.artifactid = artifactid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getProject_groupid() {
		return project_groupid;
	}
	public void setProject_groupid(String project_groupid) {
		this.project_groupid = project_groupid;
	}
	String groupid;
	String artifactid;
	String version;
	String project_groupid;

}
