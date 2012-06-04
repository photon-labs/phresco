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
