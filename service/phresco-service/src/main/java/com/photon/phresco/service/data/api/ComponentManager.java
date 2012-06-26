/*
 * ###
 * Phresco Service
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
package com.photon.phresco.service.data.api;

import java.util.List;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AppType;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Settings;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.model.Modules;


public interface ComponentManager {

	List<AppType> findAppTypes() throws PhrescoException;

	void createAppTypes(List<AppType> appTypes);

	List<AppType> updateAppTypes(List<AppType> appTypes);

	List<Settings> findSettings();

	void createCustomers(List<Settings> settings);

	void createConfigTemplates(List<Settings> settings);

	List<Settings> updateCustomers(List<Settings> settings);

	List<Modules> findModules();

	void createModules(List<com.photon.phresco.service.model.Modules> modules);

	List<com.photon.phresco.service.model.Modules> updateModules(List<Modules> modules);

	List<ProjectInfo> findPilots();

	void createPilots(List<ProjectInfo> projectInfos);

	List<ProjectInfo> updatePilots(List<ProjectInfo> projectInfos);

	List<Technology> findTechnologies();

	void createTechnologies(List<Technology> technologies);

	List<Technology> updateTechnologies(List<Technology> technologies);

	List<Settings> updateConfigTemplates(List<Settings> settings);




}
