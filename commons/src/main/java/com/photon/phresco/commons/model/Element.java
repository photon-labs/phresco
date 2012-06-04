package com.photon.phresco.commons.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public abstract class Element {

	public enum Type {
		CUSTOMER, USER, ROLE, PERMISSION
	}
	
	String id;
	String name;
	String description;
	Date creationDate = new Date();
	
	protected Element() {
		super();
	}
	
	public Element(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Element(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
