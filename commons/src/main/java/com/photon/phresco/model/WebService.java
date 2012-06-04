package com.photon.phresco.model;

public class WebService {
	
	int id;
	String name;
	String version;
	String description;
	
	public WebService() {
		super();
	}

	public WebService(int id, String name, String version, String description) {
		this.id = id;
		this.name = name;
		this.version = version;
		this.description = description;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "WebService [id=" + id + ", name=" + name + ", version=" + version
				+ ", description=" + description + "]";
	}
	
}
