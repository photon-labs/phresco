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
package com.photon.phresco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.photon.phresco.commons.model.Element;
import com.photon.phresco.util.SizeConstants;

@XmlRootElement
public class ApplicationType extends Element implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//List of technologies supported for the application type. [Web - PHP, PHP with Drupal]
	private List<Technology> technologies = new ArrayList<Technology>(SizeConstants.SIZE_TECHNOLOGIES_MAP);
	
	private boolean system;
	private String customerId;
	public ApplicationType() {
		super();
	}

	public ApplicationType(String name, String description) {
		super();
		this.name = name;
		this.description = description;

	}

	public ApplicationType(String name, List<Technology> technologies) {
		super();
		this.name = name;
		this.technologies = technologies;
	}

	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

	public Technology getTechonology(String id) {
		if (technologies == null || technologies.size() == 0) {
			return null;
		}

		for (Technology technology : technologies) {
			if (technology.getId().equals(id)) {
				return technology;
			}
		}

		return null;
	}

	public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "ApplicationType [technologies=" + technologies + ", system="
				+ system + ", customerId=" + customerId + "]";
	}

    
}