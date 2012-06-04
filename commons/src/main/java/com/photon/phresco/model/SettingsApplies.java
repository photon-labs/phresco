package com.photon.phresco.model;

import java.io.Serializable;

public class SettingsApplies implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int settingsid;
	private String description;
	public SettingsApplies () {

	}
	public SettingsApplies(int id, int settingsid, String description) {
		super();
		this.id = id;
		this.settingsid = settingsid;
		this.description = description;
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
	 * @return the settingsid
	 */
	public int getSettingsid() {
		return settingsid;
	}
	/**
	 * @param settingsid the settingsid to set
	 */
	public void setSettingsid(int settingsid) {
		this.settingsid = settingsid;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


}
