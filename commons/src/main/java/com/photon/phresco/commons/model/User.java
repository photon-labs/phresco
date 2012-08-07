/*
 * ###
\ * Phresco Commons
 *
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 * 
 * @author kumar_s
 */
package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class User extends Element {

	private String loginId;
	private String email;
	private String firstName;
	private String lastName;
	private UserStatus status;
	private List<Role> roles;
	private boolean phrescoEnabled;
	private String displayName;
	private List<Customer> customers;
	private String token;
	
	public enum UserStatus {
	    TYPE_GOLD, TYPE_SILVER, TYPE_BRONZE
    }
	
	public User() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public User(String id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * @param name
	 * @param description
	 */
	public User(String name, String description) {
		super(name, description);
	}
	
	/**
	 * @return
	 */
	public String getLoginId() {
        return loginId;
    }

    /**
     * @param loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    
    /**
     * @return
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param mailId
     */
    public void setEmail(String mailId) {
        this.email = mailId;
    }
    
    /**
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
	/**
	 * @return
	 */
	public UserStatus getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * @return
     */
    public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return
	 */
	public boolean isPhrescoEnabled() {
        return phrescoEnabled;
    }
	
	/**
	 * @param phrescoEnabled
	 */
	public void setPhrescoEnabled(boolean phrescoEnabled) {
        this.phrescoEnabled = phrescoEnabled;
    }
	
    /**
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }
    
	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return
	 */
	public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
	
	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("loginId", loginId)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("status", status)
                .append("roles", roles)
                .append("phrescoEnabled", phrescoEnabled)
                .append("displayName", displayName)
                .append("customers", customers)
                .append("token", token)
                .toString();
    }
}