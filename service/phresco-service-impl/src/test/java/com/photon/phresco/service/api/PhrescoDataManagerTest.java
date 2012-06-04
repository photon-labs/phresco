/*
 * ###
 * Phresco Service Implemenation
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
package com.photon.phresco.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.service.data.api.PhrescoDataManager;

public class PhrescoDataManagerTest {

	private static final DocumentationType HELP_TEXT = null;
	static PhrescoDataManager dataManager = null;

	@BeforeClass
	public static void initialize() throws PhrescoException {
		PhrescoServerFactory.initialize();
		dataManager = PhrescoServerFactory.getPhrescoDataManager();
	}
	//@Test
    public void testAddProjectInfo ()throws PhrescoException {
    	List <ProjectInfo> projectInfos = new ArrayList<ProjectInfo> (8);
    	ProjectInfo projectInfo = new ProjectInfo ();
    	projectInfo.setApplication("Login");
    	projectInfo.setCode("PHP");
    	projectInfo.setDescription("Login-web-application");
    	projectInfo.setName("Login");
    	projectInfo.setVersion("1.0");
    	projectInfo.setId("LOGIN");

    	projectInfos.add(projectInfo);
    	dataManager.addProjectInfo(projectInfo);
    }
    //@Test
    public void testGetProjectInfo ()throws PhrescoException {

    	List <ProjectInfo> projectInfos = new ArrayList <ProjectInfo> (8);
    	projectInfos = dataManager.getProjectInfo () ;
    }

	//@Test
	public void testAddTecnologies ()throws PhrescoException {

		List <Technology> technologies = new ArrayList<Technology> ();
		Technology tech = new Technology();
		tech.setName(" Web Services");
		tech.setId("tech-webservices");
		tech.setAppTypeId("MOBILE");
		technologies.add(tech);
		dataManager.addTechnologies (technologies);

	}

	//@Test
	public void testGetDocumentation () throws PhrescoException {

		List <Documentation> documentation = dataManager.getDocumentations();
	}
	//@Test
	public void testAddDocumentation () throws PhrescoException {

		List <Documentation> documentations = new ArrayList <Documentation> ();
		Documentation doc = new Documentation("docid22", "docurl22",
				"doccontent22");
		dataManager.addDocumentations (documentations);

	}

	//@Test
	public void testGetTechnologies ()throws PhrescoException {
		List <Technology> technology = dataManager.getTechnologies();
	}

	//@Test
	public void testAddApplicationTypes() throws PhrescoException {
		List<ApplicationType> applicationTypes = new ArrayList<ApplicationType>(3);
		List<Technology> technologies = new ArrayList<Technology>();
		Technology tech = new Technology();
		tech.setName("HTML5 Multi-channel widgets");
		tech.setId("tech-html5");
		technologies.add(tech);
		ApplicationType web = new ApplicationType("Web", "Web Application", technologies);
		web.setId("web-tech");
		applicationTypes.add(web);
		dataManager.addApplicationTypes(applicationTypes);

	}

	//@Test
	public void testGetApplicationTypes() throws PhrescoException {

		List<ApplicationType> applicationTypes = dataManager.getApplicationTypes();
		String appId = "";
		for (ApplicationType applicationType:applicationTypes ){
			appId = applicationType.getId();
			List<Technology> technologies = dataManager.getTechnologies(appId);
		}
		}


	//@Test
	public void testGetModules () throws PhrescoException {
		List <Module> modules = dataManager.getModule ();
	}
	//@Test
	public void testAddModule () throws PhrescoException {
//		List <ModuleGroup> moduleGroups = new ArrayList <ModuleGroup> (8);
//		List <Module> modules = new ArrayList <Module> ();
//		List <Documentation> docs = new ArrayList <Documentation> ();
//		Documentation doc = new Documentation("docid11", "docurl11",
//				"doccontent11");
//		docs.add(doc);
//		ModuleGroup moduleGroup = new ModuleGroup("id11","sample11","phresco11",
//				"phresco11","server11","vend11",true ,true,docs,modules);
//		moduleGroups.add(moduleGroup);
//		Module module = new Module("moduleid11", "modversion11", "modurl11",
//				"modscope11",moduleGroups );
//		modules.add(module);
//		dataManager.addModules (modules);
	}

	//@Test
	public void testGetModuleGroups() throws PhrescoException {
		List<ModuleGroup> moduleGroups = dataManager.getModuleGroup();
		}

	//@Test
	public void testAddVideoInfo() throws PhrescoException{
		List<VideoInfo> videoInfo = new ArrayList<VideoInfo>(3);
		List<VideoType> types = new ArrayList<VideoType>();
		VideoType videoType = new VideoType("mp4", "mp4", "src");
		videoType.setId("typeid");
		types.add(videoType);
		VideoInfo info = new VideoInfo("photon", "photon.jpg", "src", "phresco", "sample", types);
		info.setId("infoid");
		videoInfo.add(info);
		dataManager.addVideos(videoInfo);
	}

	//@Test
	public void testGetVideoInfo() throws PhrescoException {
		List<VideoInfo> videoInfo = dataManager.getVideoInfos();
	}

	//@Test
	public void testAddSettings() throws PhrescoException{
		List<SettingsInfo> settingsInfo = new ArrayList<SettingsInfo>();
		List<PropertyInfo> propertyInfo = new ArrayList<PropertyInfo>();
		List<String> appliesTo = new ArrayList<String>();
		appliesTo.add("php");
		PropertyInfo property = new PropertyInfo("key", "value");
		property.setId("id");
		propertyInfo.add(property);
		SettingsInfo settings = new SettingsInfo("wamp", "server for php", "wamp" ,propertyInfo,appliesTo);
		settings.setSettingId("setId");
		settingsInfo.add(settings);

		dataManager.addSettingsInfo(settingsInfo);
	}

	//@Test
	public void testGetSettingsInfo() throws PhrescoException {
		List<SettingsInfo> settingsInfo = dataManager.getSettings();
	}

	@Test
	public void testAddAdminConfig() throws PhrescoException{
		List<AdminConfigInfo> adminInfos = new ArrayList<AdminConfigInfo>();
		HashMap <String,String> test = new HashMap <String,String>();
		Hashtable testHash = new Hashtable  ();
		test.put("h", "h");
		test.put("h", "h");
		testHash.put("h", "h");
		testHash.put("h", "h");
		System.out.println (test);
		System.out.println (testHash);
		AdminConfigInfo configInfo = new AdminConfigInfo("test", "password_test","admin Login details");
		configInfo.setId("adminid_test");
		adminInfos.add(configInfo);
		dataManager.addAdminConfigInfo(adminInfos);
	}

	//@Test
	public void testGetAdminConfigInfo() throws PhrescoException {
		List<AdminConfigInfo> adminConfigInfos = new ArrayList<AdminConfigInfo>();
		adminConfigInfos = dataManager.getAdminConfigInfo();
	}

	//@Test
	public void testAddDownloadInfo() throws PhrescoException{
		List<DownloadInfo> downloadinfos = new ArrayList<DownloadInfo>();
		String[] appTo = {"php"};
		String[] platform = {"os"};
		DownloadInfo downloadInfo = new DownloadInfo("id", "name", "version", "download", "type", appTo, platform);
		downloadinfos.add(downloadInfo);
		dataManager.addDownloadInfo(downloadinfos);
	}

	//@Test
	public void getDownloadInfo() throws PhrescoException{
		List<DownloadInfo> download = dataManager.getDownloads();
	}

	//@Test
	public void testAddModuleGroup() throws PhrescoException{
//		List<ModuleGroup> moduleGroups = new ArrayList<ModuleGroup>();
//		List<Documentation> docs = new ArrayList<Documentation>();
//		Documentation doc = new Documentation("docid", "docurl","doccontent");
//		docs.add(doc);
//		List<Module> modules = new ArrayList<Module>();
//		List<ModuleGroup> dependent = new ArrayList<ModuleGroup>();
//		Module module = new Module("moduleid", "modversion", "modurl", "modscope",dependent );
//		modules.add(module);
//		ModuleGroup moduleGroup = new ModuleGroup("id","sample","phresco","phresco","server","vend",true ,true,docs,modules);
//		moduleGroups.add(moduleGroup);
//		dataManager.addModuleGroup(moduleGroups);
	}

}
