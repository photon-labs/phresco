package com.photon.phresco.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private String id;
	private List<String> roles;

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public List<String> getRoles() {
		return roles;
	}

	@XmlElement
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}