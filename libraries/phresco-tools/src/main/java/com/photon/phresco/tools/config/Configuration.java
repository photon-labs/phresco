package com.photon.phresco.tools.config;

import java.util.Properties;

public class Configuration {
	
	String name;
	String type;
	
	Properties properties;

	public Configuration(String name, String type, Properties properties) {
		super();
		this.name = name;
		this.type = type;
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Properties getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return "Configuration [name=" + name + ", type=" + type
				+ ", properties=" + properties + "]";
	}
	
}
