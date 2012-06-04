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
