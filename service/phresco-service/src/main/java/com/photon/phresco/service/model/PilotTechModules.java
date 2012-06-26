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

import java.io.Serializable;

public class PilotTechModules implements Serializable  {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int id;
    String mgid;
    String name;
    int PilotTechid;

    //List<Module> versions
    public PilotTechModules (){

    }
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public PilotTechModules(int id, String mgid, String name, int pilotTechid) {
		super();
		this.id = id;
		this.mgid = mgid;
		this.name = name;
		PilotTechid = pilotTechid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechModules [id=" + id + ", mgid=" + mgid + ", name="
				+ name + ", PilotTechid=" + PilotTechid + "]";
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the mgid
	 */
	public String getMgid() {
		return mgid;
	}
	/**
	 * @param mgid the mgid to set
	 */
	public void setMgid(String mgid) {
		this.mgid = mgid;
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
	/**
	 * @return the pilotTechid
	 */
	public int getPilotTechid() {
		return PilotTechid;
	}
	/**
	 * @param pilotTechid the pilotTechid to set
	 */
	public void setPilotTechid(int pilotTechid) {
		PilotTechid = pilotTechid;
	}
}
