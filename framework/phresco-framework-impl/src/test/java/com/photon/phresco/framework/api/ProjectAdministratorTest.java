/*
 * ###
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.api;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.BuildInfo;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class ProjectAdministratorTest implements FrameworkConstants {
	private ProjectAdministrator administrator = null;
	private static final String SETTINGS_NAME = "TestSettings";

	@Before
	public void setUp() throws Exception {
		administrator = PhrescoFrameworkFactory.getProjectAdministrator();
	}

	@After
	public void tearDown() throws Exception {
	    if (administrator != null) {
	        administrator = null;
	    }
	}

//	//@Test
//	public void testCreateProject() throws PhrescoException {
//        int initialSize = getProjectsCount();
//        administrator.createProject(createProjectInfo(), null);
//        int finalSize = getProjectsCount();
//        assertEquals(initialSize + 1, finalSize);
//
//        administrator.deleteProject(Collections.singletonList("Test"));
//    }
//
//	//@Test
//	public void testGetProject() throws PhrescoException {
//	    administrator.createProject(createProjectInfo(), null);
//	    Project project = administrator.getProject("Test");
//	    assertNotNull(project);
//
//	    administrator.deleteProject(Collections.singletonList("Test"));
//	}
//
//	//@Test
//	public void testDeleteProject() throws PhrescoException {
//        administrator.createProject(createProjectInfo(), null);
//
//        int initialSize = getProjectsCount();
//        administrator.deleteProject(Collections.singletonList("Test"));
//        int finalSize = getProjectsCount();
//
//        assertEquals(initialSize - 1, finalSize);
//    }

    @Test
	public void createEnvironment() throws PhrescoException {
    	List<Environment> totalEnvironments = administrator.getEnvironments();
    	
    	int initialSize = 0;
    	if (CollectionUtils.isNotEmpty(totalEnvironments)) {
        	initialSize = totalEnvironments.size();
    	}
    	
		List<Environment> environments = new ArrayList<Environment>();
    	environments.add(new Environment("testEnvironment", "description", false));
    	administrator.createEnvironments(environments);
        
    	List<Environment> totalEnvironment = administrator.getEnvironments();
        int finalSize = totalEnvironment.size();
        assertEquals(initialSize + 1, finalSize);
	}

    @Test
	public void createSetting() throws PhrescoException {
		List<SettingsInfo> settingsInfos = administrator.getSettingsInfos();
		int initialSize = 0;
    	if (CollectionUtils.isNotEmpty(settingsInfos)) {
        	initialSize = settingsInfos.size();
    	}
    	
    	SettingsTemplate selectedSettingTemplate = administrator.getSettingsTemplate("Server");
	    SettingsInfo settingsInfo = new SettingsInfo("TestServer", "serverCreation", "Server");
    	List<PropertyInfo> propertyInfoList = new ArrayList<PropertyInfo>(10);
        List<PropertyTemplate> propertyTemplates = selectedSettingTemplate.getProperties();
        propertyInfoList.add(new PropertyInfo("protocol", "http"));
        propertyInfoList.add(new PropertyInfo("host", "localhost"));
        propertyInfoList.add(new PropertyInfo("port", "80"));
        propertyInfoList.add(new PropertyInfo("username", "root"));
        propertyInfoList.add(new PropertyInfo("password", "root"));
        propertyInfoList.add(new PropertyInfo("deploy_dir", "wamp"));
        propertyInfoList.add(new PropertyInfo("type", "server11"));
        propertyInfoList.add(new PropertyInfo("context", "Testcontext"));
        settingsInfo.setAppliesTo(Arrays.asList("tech-phpdru6"));
        settingsInfo.setPropertyInfos(propertyInfoList);
        administrator.createSetting(settingsInfo, "testEnvironment");
        
	    List<SettingsInfo> afterSettingsInfo = administrator.getSettingsInfos();
        int finalSize = afterSettingsInfo.size();
        assertEquals(initialSize + 1, finalSize);
	}
    
    @Test
    public void updateSetting() throws PhrescoException {
        SettingsTemplate selectedSettingTemplate = administrator.getSettingsTemplate("Server");
        SettingsInfo settingsInfo = new SettingsInfo("SampleServer", "serverCreation", "Server");
    	List<PropertyInfo> propertyInfoList = new ArrayList<PropertyInfo>(10);
        List<PropertyTemplate> propertyTemplates = selectedSettingTemplate.getProperties();
        propertyInfoList.add(new PropertyInfo("protocol", "http"));
        propertyInfoList.add(new PropertyInfo("host", "localhost"));
        propertyInfoList.add(new PropertyInfo("port", "80"));
        propertyInfoList.add(new PropertyInfo("username", "root"));
        propertyInfoList.add(new PropertyInfo("password", "root"));
        propertyInfoList.add(new PropertyInfo("deploy_dir", "wamp"));
        propertyInfoList.add(new PropertyInfo("type", "server11"));
        propertyInfoList.add(new PropertyInfo("context", "Testcontext"));
        settingsInfo.setAppliesTo(Arrays.asList("tech-phpdru6"));
        settingsInfo.setPropertyInfos(propertyInfoList);
        administrator.updateSetting("testEnvironment", "TestServer", settingsInfo);
    }
    
	//@Test
	public void testFrameworkConfiguration() throws PhrescoException {
		FrameworkConfiguration frameworkConfiguration = PhrescoFrameworkFactory.getFrameworkConfig();
		String serverPath = frameworkConfiguration.getServerPath();
		System.out.println("path = " + serverPath);
	}

	//@Test
	public void testSettingsTemplate() throws PhrescoException {
	    List<SettingsTemplate> templates = administrator.getSettingsTemplates();
	    assertNotNull("Settings Template list is empty", templates);
	}

	@Test
	public void testGetSettingsInfos() throws PhrescoException {
	    List<SettingsInfo> settingsInfos = administrator.getSettingsInfos("testEnvironment");
	}

	@Test
	public void testGetSettingsInfo() throws PhrescoException {
	    SettingsInfo settingsInfo = administrator.getSettingsInfo("SampleServer", "testEnvironment");
	    assertNotNull("Settings Info not with name", settingsInfo);
	}

	@Test
	public void testDeleteSettingsInfos() throws PhrescoException {
	    List<SettingsInfo> settingsInfos = administrator.getSettingsInfos();
	    int initialSize = settingsInfos.size();
	    Map<String, List<String>> deleteConfigs = new HashMap<String , List<String>>();
	    List<String> configNames = new ArrayList<String>(2);
	    configNames.add("SampleServer");
	    deleteConfigs.put("testEnvironment", configNames);
	    administrator.deleteSettingsInfos(deleteConfigs);
	    List<SettingsInfo> afterSettingsInfo = administrator.getSettingsInfos();
        int finalSize = afterSettingsInfo.size();
        assertEquals(initialSize - 1, finalSize);
	}

    @Test
	public void deleteEnvironment() throws PhrescoException {
    	List<Environment> totalEnvironments = administrator.getEnvironments();
    	int initialSize = 0;
    	if (CollectionUtils.isNotEmpty(totalEnvironments)) {
        	initialSize = totalEnvironments.size();
    	}
		List<String> environments = new ArrayList<String>();
		environments.add("testEnvironment");
    	administrator.deleteEnvironments(environments);
    	List<Environment> totalEnvironment = administrator.getEnvironments();
        int finalSize = totalEnvironment.size();
        assertEquals(initialSize - 1, finalSize);
	}
    
	private int getProjectsCount() throws PhrescoException {
        List<Project> projects = administrator.discover(Collections.singletonList(new File(Utility.getProjectHome())));
        return (projects != null) ? projects.size() : 0;
    }

    private ProjectInfo createProjectInfo() {
        ProjectInfo info = new ProjectInfo();
        info.setName("TestProject");
        info.setDescription("Test Project using Junit");
        info.setCode("Test");
        info.setVersion("1.0.0");
        Technology technology = new Technology();
        technology.setId("tech-phpdru7");
        technology.setName("Drupal7");

        //TODO:FIX the tests
//        Module module = new Module("mod_block_7.0", "Block", "7.0");
//        technology.setModules(Collections.singletonList(module));
        info.setTechnology(technology);

        return info;
    }

    //@Test
    public void testDeleteBuildInfos() throws PhrescoException {
        Project project = administrator.getProject("PHR_Drupal7");
        List<BuildInfo> buildInfos = administrator.getBuildInfos(project);
        administrator.deleteBuildInfos(project, new int[]{3});

        List<BuildInfo> after = administrator.getBuildInfos(project);
        System.out.println("Done");
    }

    //@Test
    public void testVideoInfos() throws PhrescoException {
        List<VideoInfo> videoInfos = administrator.getVideoInfos();
        System.out.println(videoInfos);
    }

    @Test
    public void testValidation() throws PhrescoException {
    	List<ValidationResult> validate = administrator.validate();
    	System.out.println(validate);
    }
}
