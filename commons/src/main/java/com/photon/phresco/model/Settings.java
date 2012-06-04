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
import java.util.ArrayList;
import java.util.List;

import com.photon.phresco.util.SizeConstants;

public class Settings implements Serializable {
	private int id;
    //Database, Server, Email
    private String type;
    //List of properties available for the template
    private List<PropertyTemplate> properties = new ArrayList<PropertyTemplate>(SizeConstants.SIZE_PROPERTIES_MAP);
    //List of technology ids
    private List<String> appliesTo;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Settings [id=" + id + ", type=" + type + ", properties="
				+ properties + ", appliesTo=" + appliesTo + "]";
	}
	public Settings (){

	}
	public Settings(int id, String type, List<PropertyTemplate> properties,
			List<String> appliesTo) {
		super();
		this.id = id;
		this.type = type;
		this.properties = properties;
		this.appliesTo = appliesTo;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the properties
	 */
	public List<PropertyTemplate> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<PropertyTemplate> properties) {
		this.properties = properties;
	}
	/**
	 * @return the appliesTo
	 */
	public List<String> getAppliesTo() {
		return appliesTo;
	}
	/**
	 * @param appliesTo the appliesTo to set
	 */
	public void setAppliesTo(List<String> appliesTo) {
		this.appliesTo = appliesTo;
	}

}
