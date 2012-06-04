package com.photon.phresco.model;

public class SettingsPropPossibleValues {

	int id;
	int propid;
	int settingsid;
	String possibleValues;
	public SettingsPropPossibleValues () {

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
	 * @return the propid
	 */
	public int getPropid() {
		return propid;
	}
	/**
	 * @param propid the propid to set
	 */
	public void setPropid(int propid) {
		this.propid = propid;
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
	 * @return the possibleValues
	 */
	public String getPossibleValues() {
		return possibleValues;
	}
	/**
	 * @param possibleValues the possibleValues to set
	 */
	public void setPossibleValues(String possibleValues) {
		this.possibleValues = possibleValues;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SettingsPropPossibleValues [id=" + id + ", propid=" + propid
				+ ", settingsid=" + settingsid + ", possibleValues="
				+ possibleValues + "]";
	}
	public SettingsPropPossibleValues(int id, int propid, int settingsid,
			String possibleValues) {
		super();
		this.id = id;
		this.propid = propid;
		this.settingsid = settingsid;
		this.possibleValues = possibleValues;
	}


}
