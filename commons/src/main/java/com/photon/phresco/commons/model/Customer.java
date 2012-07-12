/*
 * ###
 * Phresco Commons
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
 */
package com.photon.phresco.commons.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer extends Element {

	public static final int TYPE_GOLD = 0;
	public static final int TYPE_SILVER = 1;
	public static final int TYPE_BRONZE = 2;

	private String emailId;
    private String address;
    private String country;
    private String state;
    private String zipcode;
    private String contactNumber;
    private String fax;
    private String helpText;
    private int type;
//	Date validFrom;
//	Date validUpto;
	private String validFrom;
	private String validUpto;
	private String repoURL;
	private int status;
	
	public Customer() {
		super();
	}

	public Customer(String name, String description) {
		super(name, description);
		this.name = name;
		this.description = description;
	}

	public Customer(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String addOn) {
		this.creationDate = addOn;
	}

//	public Date getValidFrom() {
//		return validFrom;
//	}
//
//	public void setValidFrom(Date validFrom) {
//		this.validFrom = validFrom;
//	}
//
//	public Date getValidUpto() {
//		return validUpto;
//	}
//
//	public void setValidUpto(Date validUpto) {
//		this.validUpto = validUpto;
//	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRepoURL() {
		return repoURL;
	}

	public void setRepoURL(String repoURL) {
		this.repoURL = repoURL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}
	
	public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    @Override
    public String toString() {
        return "Customer [emailId=" + emailId + ", address=" + address
                + ", country=" + country + ", state=" + state + ", zipcode="
                + zipcode + ", contactNumber=" + contactNumber + ", fax=" + fax
                + ", helpText=" + helpText + ", type=" + type + ", validFrom="
                + validFrom + ", validUpto=" + validUpto + ", repoURL="
                + repoURL + ", status=" + status + "]";
    }
}