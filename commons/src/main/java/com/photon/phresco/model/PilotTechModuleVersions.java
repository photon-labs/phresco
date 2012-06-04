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

import java.io.Serializable;

public class PilotTechModuleVersions implements Serializable  {
	 /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	int id;
    String Version;
    int pilotTechModulesid;

	public PilotTechModuleVersions(int id, String version,
			int pilotTechModulesid) {
		super();
		this.id = id;
		Version = version;
		this.pilotTechModulesid = pilotTechModulesid;
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
	public PilotTechModuleVersions (){

	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechModuleVersions [id=" + id + ", Version=" + Version
				+ ", pilotTechModulesid=" + pilotTechModulesid + "]";
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return Version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		Version = version;
	}
	/**
	 * @return the pilotTechModulesid
	 */
	public int getPilotTechModulesid() {
		return pilotTechModulesid;
	}
	/**
	 * @param pilotTechModulesid the pilotTechModulesid to set
	 */
	public void setPilotTechModulesid(int pilotTechModulesid) {
		this.pilotTechModulesid = pilotTechModulesid;
	}
}
