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

public class LibraryTech {

	 private int id;
     private int libid;
     private String libTechDesc;
     public LibraryTech () {

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
	 * @return the libid
	 */
	public int getLibid() {
		return libid;
	}
	/**
	 * @param libid the libid to set
	 */
	public void setLibid(int libid) {
		this.libid = libid;
	}
	/**
	 * @return the libTechDesc
	 */
	public String getLibTechDesc() {
		return libTechDesc;
	}
	/**
	 * @param libTechDesc the libTechDesc to set
	 */
	public void setLibTechDesc(String libTechDesc) {
		this.libTechDesc = libTechDesc;
	}
	public LibraryTech(int id, int libid, String libTechDesc) {
		super();
		this.id = id;
		this.libid = libid;
		this.libTechDesc = libTechDesc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LibraryTech [id=" + id + ", libid=" + libid + ", libTechDesc="
				+ libTechDesc + "]";
	}

}
