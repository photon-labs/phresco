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
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class PropertyTemplate implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String key;
	private I18NString name;
	private String type;
	private boolean projectSpecific;
	private boolean isRequired;
	private List<String> possibleValues;
	private I18NString description;
	private int settingsId;
	private String iName;
	private String iDesc;
	private List<PropertyTemplate> propertyTemplates;
	
	/**
	 * @return the i18Name
	 */
	public String getIName() {
		return iName;
	}

	/**
	 * @param i18Name the i18Name to set
	 */
	public void setIName(String i18Name) {
		this.iName = i18Name;
	}

	/**
	 * @return the i18Desc
	 */
	public String getIDesc() {
		return iDesc;
	}

	/**
	 * @return the iName
	 */
	public String getiName() {
		return iName;
	}

	/**
	 * @param iName the iName to set
	 */
	public void setiName(String iName) {
		this.iName = iName;
	}

	/**
	 * @return the iDesc
	 */
	public String getiDesc() {
		return iDesc;
	}

	/**
	 * @param iDesc the iDesc to set
	 */
	public void setiDesc(String iDesc) {
		this.iDesc = iDesc;
	}

	/**
	 * @param i18Desc the i18Desc to set
	 */
	public void setIDesc(String i18Desc) {
		this.iDesc = i18Desc;
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
	 * @return the settingsId
	 */
	public int getSettingsId() {
		return settingsId;
	}

	/**
	 * @param settingsId the settingsId to set
	 */
	public void setSettingsId(int settingsId) {
		this.settingsId = settingsId;
	}

	public PropertyTemplate() {
		super();
	}

	public PropertyTemplate(String key, String type) {
		super();
		this.key = key;
		this.type = type;
	}

	public PropertyTemplate(String key, String type, boolean projectSpecific,
			boolean isRequired) {
		super();
		this.key = key;
		this.type = type;
		this.projectSpecific = projectSpecific;
		this.isRequired = isRequired;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public I18NString getName() {
		return name;
	}

	public void setName(I18NString name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isProjectSpecific() {
		return projectSpecific;
	}

	public void setProjectSpecific(boolean projectSpecific) {
		this.projectSpecific = projectSpecific;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	public I18NString getDescription() {
		return description;
	}

	public void setDescription(I18NString label) {
		this.description = label;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public boolean getIsRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public List<PropertyTemplate> getpropertyTemplates() {
		return propertyTemplates;
	}

	public void setpropertyTemplates(List<PropertyTemplate> propertyTemplates) {
		this.propertyTemplates = propertyTemplates;
	}

	@Override
	public String toString() {
		return "PropertyTemplate [key=" + key + ", name=" + name + ", type="
				+ type + ", projectSpecific=" + projectSpecific
				+ ", isRequired=" + isRequired + ", possibleValues="
				+ possibleValues + ", description=" + description + "]";
	}

}
