package com.photon.phresco.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Server implements Serializable {
	
	int id;
	String name;
	List<String> versions;
	String description;
	
	public Server() {
		super();
	}

	public Server(int id, String name, List<String> versions, String description) {
		this.id = id;
		this.name = name;
		this.versions = versions;
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
	
	public List<String> getVersions() {
		return versions;
	}
	
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", name=" + name + ", versions=" + versions
				+ ", description=" + description + "]";
	}
	
}
