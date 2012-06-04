/*
 * ###
 * Phresco Service Tools
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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.jaxb.ArchetypeInfo;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.TechnologyTypes;

public class SetupDataPublisher {

    private static final Logger S_LOGGER = Logger.getLogger(SetupDataPublisher.class);
       RepositoryManager repManager = null;

    public static final List<String> TECHNOLOGIES = new ArrayList<String>(16);
    private File rootDir;

    private void initTechnologies() {
        TECHNOLOGIES.add(TechnologyTypes.PHP);
        TECHNOLOGIES.add(TechnologyTypes.PHP_DRUPAL7);
        TECHNOLOGIES.add(TechnologyTypes.SHAREPOINT);
        TECHNOLOGIES.add(TechnologyTypes.ANDROID_NATIVE);
        TECHNOLOGIES.add(TechnologyTypes.IPHONE_NATIVE);
        TECHNOLOGIES.add(TechnologyTypes.NODE_JS_WEBSERVICE);
    }

    public SetupDataPublisher(File rootDir) throws PhrescoException {
        this.rootDir = rootDir;
        PhrescoServerFactory.initialize();
        this.repManager = PhrescoServerFactory.getRepositoryManager();
        initTechnologies();
    }

    private void publishAll() throws PhrescoException {
//        defineDependencyConfig();
//        defineApptype();
//        defineJSLibraries();
//        defineConfigurationData();
        for (String tech : TECHNOLOGIES) {
//            defineDatabases(tech);
            defineModules(tech);
//            defineEditor(tech);
//            defineLibraries(tech);
//            definePlatforms(tech);
//            defineAppServers(tech);
//			defineArchetype(tech);
        }
    }

    private void defineDatabases(String tech) throws PhrescoException {
        addArtifacts(tech, "databases");
    }

    private void defineAppServers(String tech) throws PhrescoException {
        addArtifacts(tech, "appservers");
    }

    private void definePlatforms(String tech) throws PhrescoException {
        addArtifacts(tech, "platforms");
    }

    private void defineLibraries(String tech) throws PhrescoException {
        addArtifacts(tech, "lib");
    }

    private void defineEditor(String tech) throws PhrescoException {
        addArtifacts(tech, "editors");
    }

    private void defineModules(String tech) throws PhrescoException {
        addArtifacts(tech, "modules");
    }

    private void addArtifacts(String tech, String type) throws PhrescoException {
        ArtifactInfo info = new ArtifactInfo(type, tech, "", "xml", "0.1");
        File artifact = new File(rootDir, type + "/" + tech + ".xml");
        repManager.addArtifact(info, artifact);
        System.out.println(type + " for " + tech + " defined successfully");

        if (S_LOGGER.isDebugEnabled()) {
            S_LOGGER.info(type + " for " + tech + " defined successfully");
        }
    }

    private void defineJSLibraries() throws PhrescoException {
    }

    private void defineApptype() throws PhrescoException {
        ArtifactInfo info = new ArtifactInfo("config", "apptype", "", "xml",
                "0.1");
        File artifact = new File(rootDir, "config/apptype.xml");
        repManager.addArtifact(info, artifact);
        if (S_LOGGER.isDebugEnabled()) {
            S_LOGGER.info("Application type defined successfully");
        }
    }

    private void defineDependencyConfig() throws PhrescoException {
        ArtifactInfo info = new ArtifactInfo("config", "dependency", "",
                "properties", "0.1");
        File artifact = new File(rootDir, "config/dependency.properties");
        repManager.addArtifact(info, artifact);
        if (S_LOGGER.isDebugEnabled()) {
            S_LOGGER.info("Dependency configuration defined successfully");
        }
    }

    private void defineConfigurationData() throws PhrescoException {
        ArtifactInfo info = new ArtifactInfo("config", "settings", "", "json",
                "0.1");
        File artifact = new File(rootDir, "config/settings-0.1.json");
        repManager.addArtifact(info, artifact);
        if (S_LOGGER.isDebugEnabled()) {
            S_LOGGER.info("configuration data  defined successfully");
        }
    }

    

    public static void main(String[] args) throws PhrescoException {
//        if (args == null || args.length != 4) {
//            throw new PhrescoException("Invalid usage: Provide inputDir, repoURL, username and password");
//        }

//        File toolsHome = new File(args[0]);
//        File inputRootDir = new File(toolsHome, "files");
//        File repoDir = new File(toolsHome, "repo");

        File toolsHome = new File("D:\\work\\projects\\phresco\\source\\phresco-source\\trunk\\service\\delivery\\tools\\");
        File inputRootDir = new File(toolsHome, "files");
        File repoDir = new File(toolsHome, "repo");

//        SetupDataGenerator dataGen = new SetupDataGenerator(inputRootDir, repoDir);
//        dataGen.generateAll();
        SetupDataPublisher publisher = new SetupDataPublisher(repoDir);
        publisher.publishAll();
    }

}
