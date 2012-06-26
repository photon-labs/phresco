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
package com.photon.phresco.service.model;

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
