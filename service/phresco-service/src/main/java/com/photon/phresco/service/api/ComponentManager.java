package com.photon.phresco.service.api;

import java.util.List;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AppType;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Modules;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Settings;
import com.photon.phresco.model.Technology;


public interface ComponentManager {

	List<ApplicationType> findAppTypes() throws PhrescoException;

	void createAppTypes(List<ApplicationType> appTypes);

	List<ApplicationType> updateAppTypes(List<ApplicationType> appTypes);

	List<Settings> findSettings();

	void createCustomers(List<Settings> settings);

	void createConfigTemplates(List<Settings> settings);

	List<Settings> updateCustomers(List<Settings> settings);

	List<Modules> findModules();

	void createModules(List<com.photon.phresco.model.Modules> modules);

	List<com.photon.phresco.model.Modules> updateModules(List<Modules> modules);

	List<ProjectInfo> findPilots();

	void createPilots(List<ProjectInfo> projectInfos);

	List<ProjectInfo> updatePilots(List<ProjectInfo> projectInfos);

	List<Technology> findTechnologies();

	void createTechnologies(List<Technology> technologies);

	List<Technology> updateTechnologies(List<Technology> technologies);

	List<Settings> updateConfigTemplates(List<Settings> settings);




}
