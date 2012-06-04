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

public class LibrayDocs {
	private int id;
	private int libraryid;

	public LibrayDocs () {

	}
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LibrayDocs [id=" + id + ", libraryid=" + libraryid + "]";
	}
	public LibrayDocs(int id, int libraryid) {
		super();
		this.id = id;
		this.libraryid = libraryid;
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
	 * @return the libraryid
	 */
	public int getLibraryid() {
		return libraryid;
	}
	/**
	 * @param libraryid the libraryid to set
	 */
	public void setLibraryid(int libraryid) {
		this.libraryid = libraryid;
	}

}
