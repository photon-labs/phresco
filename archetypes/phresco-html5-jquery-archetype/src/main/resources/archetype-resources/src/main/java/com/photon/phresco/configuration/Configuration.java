package com.photon.phresco.configuration;

import java.util.Properties;

public class Configuration {
	
	private String name;
	private String desc;
	private String type;
	private Properties properties;
	private String appliesTo;

	public Configuration(String name, String desc, String type, Properties properties, String configAppliesTo) {
		super();
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.appliesTo = configAppliesTo;
		this.properties = properties;
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

	@Override
	public String toString() {
		return "Configuration [name=" + name + ", type=" + type
				+ ", properties=" + properties + "]";
	}
	
}
