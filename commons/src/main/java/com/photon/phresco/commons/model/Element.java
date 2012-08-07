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
 * 
 * @author kumar_s
 */
package com.photon.phresco.commons.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public abstract class Element {

	public enum Type {
		CUSTOMER, USER, ROLE, PERMISSION
	}

	private String id;
	private String name;
	private String description;
	Date creationDate;

	protected Element() {
		super();
	}

	protected Element(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	protected Element(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String toString() {
	    return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("creationDate", creationDate)
                .toString();
	}
}