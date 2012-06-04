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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.model.PropertyInfo;

public class SettingsInfo {
	
	private String settingId;
	private Integer id;
    private String name;
    private String description;
    private String type;
    private String envName;
	private List<PropertyInfo> propertyInfos;
    private List<String> appliesTo;
    private boolean status;
    
	public SettingsInfo(Configuration config) {
		this.name = config.getName();
		this.description = config.getDesc();
		this.envName = config.getEnvName();
		this.type = config.getType();
		setPropertyInfo(config.getProperties());
		setAppliesTo(config.getAppliesTo());
	}
	
	public SettingsInfo(String name, String description, String type) {
		this.name = name;
		this.description = description;
		this.type = type;
	}
	
	public SettingsInfo(String name, String description, String type, List<PropertyInfo> propertyInfos, List<String> appliesTo) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.propertyInfos = propertyInfos;
		this.appliesTo = appliesTo;
	}
	
	private void setPropertyInfo(Properties properties) {
		Set<Object> keySet = properties.keySet();
		List<PropertyInfo> propInfos = new ArrayList<PropertyInfo>(keySet.size());
		for (Object key : keySet) {
			String value = (String) properties.get(key);
			propInfos.add(new PropertyInfo((String) key, value));
		}
		this.propertyInfos = propInfos;
		
	}
	
	private void setAppliesTo(String appliesTos) {
		String[] split = appliesTos.split(",");
		this.appliesTo = Arrays.asList(split);
	}
	
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public List<PropertyInfo> getPropertyInfos() {
        return propertyInfos;
    }
    
    public void setPropertyInfos(List<PropertyInfo> propertyInfos) {
        this.propertyInfos = propertyInfos;
    }
    
    public PropertyInfo getPropertyInfo(String key) {
        for (PropertyInfo propertyInfo : propertyInfos) {
            if (propertyInfo.getKey().equals(key)) {
                return propertyInfo;
            }
        }
        
        return null;
    }
    
    public List<String> getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(List<String> appliesTo) {
		this.appliesTo = appliesTo;
	}

    public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSettingId() {
		return settingId;
	}

	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}

	@Override
    public String toString() {
        return "SettingsInfo [name=" + name + ", description=" + description
                + ", type=" + type + ", propertyInfos=" + propertyInfos + "]";
    }
}
