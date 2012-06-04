package com.photon.phresco.commons.model;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class Permission extends Element {

	public Permission() {
		super();
	}
	
	public Permission(String id, String name, String description) {
		super(id, name, description);
	}

	public Permission(String name, String description) {
		super(name, description);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Permission [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append("]");
		return builder.toString();
	}
	
}