package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class User extends Element {

	String loginId;
	String email;
	String firstName;
	String lastName;
	int status;
	List<Role> roles;

	public User() {
		super();
	}
	
	public User(String id, String name, String description) {
		super(id, name, description);
	}

	public User(String name, String description) {
		super(name, description);
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [status=");
		builder.append(status);
		builder.append(", roles=");
		builder.append(roles);
		builder.append(", id=");
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