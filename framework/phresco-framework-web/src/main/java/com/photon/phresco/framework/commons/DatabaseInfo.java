package com.photon.phresco.framework.commons;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatabaseInfo {
	private String name;
	private String username;
	private String password;
	private String host;
	private String port;
	private String type;


	public DatabaseInfo() {

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabasename() {
		return name;
	}

	public void setDatabasename(String databasename) {
		this.name = databasename;
	}

	public String getDatabaseType() {
		return type;
	}

	public void setDatabaseType(String databaseType) {
		this.type = databaseType;
	}
}
