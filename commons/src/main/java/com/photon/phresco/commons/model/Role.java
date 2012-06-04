package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class Role extends Element {

	List<Permission> permissions;

	public Role() {
		super();
	}
	
	public Role(String id, String name, String description) {
		super(id, name, description);
	}

	public Role(String name, String description) {
		super(name, description);
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> roles) {
		this.permissions = roles;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [id=");
		builder.append(id);
		builder.append(", permissions=");
		builder.append(permissions);
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