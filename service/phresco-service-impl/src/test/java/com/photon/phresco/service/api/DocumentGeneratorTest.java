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
package com.photon.phresco.service.api;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.util.Constants;

public class DocumentGeneratorTest extends AbstractPhrescoTest {

    @Before
    public void setUp() throws PhrescoException {
        super.setUp();
    }

    @After
    public void tearDown() throws PhrescoException {
    }

    @Test
    public final void testGenerate() {
//		fail("Not yet implemented"); // TODO

        ProjectInfo info = new ProjectInfo();
        info.setName("DocTest");
        info.setDescription("PHP Project demostrating the dynamic generation of documents.\n This also has various modules and libraries to help developer to write their piece of software little quicker and in standard way. ");
        Technology technology = new Technology("tech-php", "PHP");

        info.setTechnology(technology);
        try {
            PhrescoServerFactory.initialize();
        } catch (PhrescoException e1) {
            fail("Exception caught....at testGenerate" + e1.getMessage());
        }
        DocumentGenerator docgen = PhrescoServerFactory.getDocumentGenerator();
        String docTempFolder = System.getProperty(Constants.JAVA_TMP_DIR)+File.separator+"phresco";
        File file = new File(docTempFolder);
        if(file.exists()) {
            file.mkdirs();
        }
//		System.out.println(docTempFolder);
        try {
            docgen.generate(info, file);
        } catch (PhrescoException e) {
        } finally {
            removeFiles();
        }
    }

    private static void removeFiles() {
        String docTempFolder = System.getProperty(Constants.JAVA_TMP_DIR)+File.separator+"phresco";
        File file = new File(docTempFolder);
        FileUtils.deleteQuietly(file);
    }

    @Test
    public final void testGenerateWithNoDocuments(){
        ProjectInfo info = new ProjectInfo();
        info.setName("DocTest123");
        info.setVersion("1.0");
        info.setDescription("PHP Project demostrating the dynamic generation of documents.\n This also has various modules and libraries to help developer to write their piece of software little quicker and in standard way. ");
        Technology technology = new Technology("tech-phpdru7", "Drupal7");
        info.setTechnology(technology);
        RepositoryManager repoManager = PhrescoServerFactory.getRepositoryManager();
        try {
            PhrescoServerFactory.initialize();
            List<ProjectInfo> pilotProjects = repoManager.getPilotProjects(info.getTechnology().getId());
            for (ProjectInfo projectInfo : pilotProjects) {
                 List<ModuleGroup> modules = projectInfo.getTechnology().getModules();
                 List<ModuleGroup> modules2 = technology.getModules();
                 if(CollectionUtils.isEmpty(modules2)){
                     modules2 = new ArrayList<ModuleGroup>(8);
                     technology.setModules(modules2);
                 }
                 modules2.addAll(modules);
            }
        } catch (PhrescoException e1) {
            fail("Exception caught....at testGenerateWithNoDocuments" + e1.getMessage());		}
        DocumentGenerator docgen = PhrescoServerFactory.getDocumentGenerator();
        String docTempFolder = System.getProperty(Constants.JAVA_TMP_DIR)+File.separator+"phresco";
        File file = new File(docTempFolder);
        if(file.exists()) {
            file.mkdirs();
        }
        try {
            docgen.generate(info, file);
            System.out.println("file generated at "+file.toString());
        } catch (PhrescoException e) {
        } finally {
            removeFiles();
        }
    }

}
