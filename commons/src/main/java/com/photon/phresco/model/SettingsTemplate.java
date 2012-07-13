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

import com.photon.phresco.util.SizeConstants;

@SuppressWarnings("restriction")
@XmlRootElement
public class SettingsTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    //Database, Server, Email
    private String type;
    //List of properties available for the template
    private List<PropertyTemplate> properties = new ArrayList<PropertyTemplate>(SizeConstants.SIZE_PROPERTIES_MAP);
    //List of technology ids
    private List<String> appliesTo;
    private String customerId;
    public SettingsTemplate() {
        super();
    }

    public SettingsTemplate(String type, List<PropertyTemplate> properties,
            List<String> appliesTo) {
        this.type = type;
        this.properties = properties;
        this.appliesTo = appliesTo;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropertyTemplate> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyTemplate> properties) {
        this.properties = properties;
    }

    public List<String> getAppliesTo() {
        return appliesTo;
    }

    public void setAppliesTo(List<String> appliesTo) {
        this.appliesTo = appliesTo;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "SettingsTemplate [id=" + id + ", type=" + type
				+ ", properties=" + properties + ", appliesTo=" + appliesTo
				+ ", customerId=" + customerId + "]";
	}

   

}
