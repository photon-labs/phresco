package com.photon.phresco.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminConfigInfo implements Serializable {

	private static final long serialVersionUID = 8809204276913498651L;
	private String id;
	private String key;
	private String value;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AdminConfigInfo() {

	}

	public AdminConfigInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public AdminConfigInfo(String key, String value, String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
