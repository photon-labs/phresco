package com.photon.phresco.model;

import java.io.Serializable;

public class PilotUrls implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int id;
    int ProjectInfoid;
    String urls;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotUrls [id=" + id + ", ProjectInfoid=" + ProjectInfoid
				+ ", urls=" + urls + "]";
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the projectInfoid
	 */
	public int getProjectInfoid() {
		return ProjectInfoid;
	}
	/**
	 * @param projectInfoid the projectInfoid to set
	 */
	public void setProjectInfoid(int projectInfoid) {
		ProjectInfoid = projectInfoid;
	}
	/**
	 * @return the urls
	 */
	public String getUrls() {
		return urls;
	}
	/**
	 * @param urls the urls to set
	 */
	public void setUrls(String urls) {
		this.urls = urls;
	}
	PilotUrls () {

	}

}
