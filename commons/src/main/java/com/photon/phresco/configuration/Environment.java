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
package com.photon.phresco.configuration;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class Environment {

	String name;
	String desc;
	boolean defaultEnv;
	boolean delete;
	
	public Environment() {
	}
	
	public Environment(String name, String desc, boolean defaultEnv) {
		this.name = name;
		this.desc = desc;
		this.defaultEnv = defaultEnv;
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
	
	public boolean isDefaultEnv() {
		return defaultEnv;
	}
	
	public void setDefaultEnv(boolean defaultEnv) {
		this.defaultEnv = defaultEnv;
	}
	
	public boolean canDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String toString() {
		return "Environment [name=" + name + ", description=" + desc  + 
				", default=" + defaultEnv + "]";
	}
}
