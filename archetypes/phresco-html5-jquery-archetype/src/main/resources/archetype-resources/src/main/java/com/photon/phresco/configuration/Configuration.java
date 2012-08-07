/*
 * ###
 * Archetype - phresco-html5-jquery-archetype
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
package com.photon.phresco.configuration;

import java.util.Properties;

public class Configuration {
	
	private String name;
	private String desc;
	private String type;
	private String envName;
	private Properties properties;
	String appliesTo;

	
	public Configuration(String name, String desc, String envName, String type, Properties properties, String configAppliesTo) {
		super();
		this.name = name;
		this.desc = desc;
		this.envName = envName;
		this.type = type;
		this.appliesTo = configAppliesTo;
		this.properties = properties;
	}
	
	public Configuration(String name, String desc, String type, Properties properties, String configAppliesTo) {
		this (name, desc, null, type, properties, configAppliesTo);
	}
	
	public Configuration(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setAppliesTo(String configAppliesTo) {
		this.appliesTo = configAppliesTo;
	}
	
	public String getAppliesTo() {
		return appliesTo;
	}

	@Override
	public String toString() {
		return "Configuration [name=" + name + ", type=" + type
				+ ", properties=" + properties + "]";
	}
	
}
