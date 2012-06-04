package com.photon.phresco.model;

import java.io.Serializable;

public class PilotTechnology implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String techid;
	private String name;
	private int projectInfoid;
	/**
	 * @return the projectInfoid
	 */
	public int getProjectInfoid() {
		return projectInfoid;
	}
	/**
	 * @param projectInfoid the projectInfoid to set
	 */
	public void setProjectInfoid(int projectInfoid) {
		this.projectInfoid = projectInfoid;
	}
	public PilotTechnology (){

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
	 * @return the techid
	 */
	public String getTechid() {
		return techid;
	}
	/**
	 * @param techid the techid to set
	 */
	public void setTechid(String techid) {
		this.techid = techid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechnology [id=" + id + ", techid=" + techid + ", name="
				+ name + "]";
	}
	public PilotTechnology(int id, String techid, String name) {
		super();
		this.id = id;
		this.techid = techid;
		this.name = name;
	}
}
