/*
 * ###
 * Phresco Service Client
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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

package com.photon.phresco.service.client.impl;

import java.util.ArrayList;
import java.util.List;

import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhCacheManager {

	/**
	 * The CacheManager provides us access to individual Cache instances
	 */
	private static final CacheManager cacheManager = new CacheManager();

	private Ehcache appTypeCache;

	public EhCacheManager() {
		// Load cache:
		appTypeCache = cacheManager.getEhcache("apptypes");
	}

	public void addAppInfo(String id, List<?> appTypes) {
		// Create an EHCache Element 
		Element element = new Element(id, appTypes);

		// Add the element to the cache
		appTypeCache.put(element);
	}

	public List<ApplicationType> getAppInfo(String id) {
		// Retrieve the element that contains the requested appType
		Element element = appTypeCache.get(id);
		if (element != null) {
			// Get the value out of the element and cast it to a appType
			return (List<ApplicationType>)element.getValue();
		}

		// We don't have the object in the cache so return null
		return new ArrayList<ApplicationType>(1);
	}
	
	public List<Technology> getArcheInfo(String id) {
		Element element = appTypeCache.get(id);
		if (element != null) {
			return (List<Technology>)element.getValue();
		}
		return new ArrayList<Technology>(1);
	}
	
	public List<DownloadInfo> getDownloadInfo(String id) {
		Element element = appTypeCache.get(id);
		if (element != null) {
			return (List<DownloadInfo>)element.getValue();
		}
		return new ArrayList<DownloadInfo>(1);
	}
	
	public List<ProjectInfo> getPilotProjects(String id) {
		Element element = appTypeCache.get(id);
		if (element != null) {
			return (List<ProjectInfo>)element.getValue();
		}
		return new ArrayList<ProjectInfo>(1);
	}
	
	public List<ModuleGroup> getModuleGroups(String id) {
		Element element = appTypeCache.get(id);
		if (element != null) {
			return (List<ModuleGroup>)element.getValue();
		}
		return new ArrayList<ModuleGroup>(1);
	}
}