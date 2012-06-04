package com.photon.phresco.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.photon.phresco.util.Credentials;

@SuppressWarnings("restriction")
@XmlRootElement
public class UserInfo {
	
	private String userName;
	private Credentials credentials;
	private String displayName;	
	private String mail;

	private boolean phrescoEnabled;
	private List<String> roles;	
	private List<String> permissions;
	private List<String> customerNames;
	

	public UserInfo() {
		//for jaxb
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	

	public boolean isPhrescoEnabled() {
		return phrescoEnabled;
	}

	public void setPhrescoEnabled(boolean phrescoEnabled) {
		this.phrescoEnabled = phrescoEnabled;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * @return the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 
	 * @param customerName
	 *            the customerName to set
	 */
	public List<String> getCustomerNames() {
		return customerNames;
	}

	/**
	 * 
	 * @return customerName
	 */
	public void setCustomerNames(List<String> customerNames) {
		this.customerNames = customerNames;
	}

}
