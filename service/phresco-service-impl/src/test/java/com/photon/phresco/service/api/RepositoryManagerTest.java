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

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.sonatype.aether.deployment.DeploymentException;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.model.ArtifactInfo;

public class RepositoryManagerTest {

    @Before
    public void setUp() throws Exception {
        PhrescoServerFactory.initialize();
    }

    @After
    public void tearDown() throws Exception {
    }

//	@Test
    public void testAddArtifact() throws PhrescoException, DeploymentException {
        RepositoryManager repoManager = PhrescoServerFactory.getRepositoryManager();
        System.out.println("RepoMan " + repoManager);
        ArtifactInfo info = new ArtifactInfo("phresco", "test", "", "xml", "0.1");
        File artifact = new File("D:\\work\\projects\\phresco\\src\\trunk\\server\\tools\\repo\\config\\editors\\android-native.xml");
  //      String message = repoManager.addArtifact(info, artifact);
  //      System.out.println("artifact " + message);
    }

//	@Test
    public void testGetArtifactAsString() throws PhrescoException {
        RepositoryManager repoManager = PhrescoServerFactory.getRepositoryManager();
        System.out.println("RepoMan " + repoManager);
        String technologies = repoManager.getArtifactAsString("tech.xml");
        System.out.println(technologies);
    }

//    @Test
    public void testAddModule() throws PhrescoException, DeploymentException {
//        RepositoryManager repoManager = PhrescoServerFactory.getRepositoryManager();
//        System.out.println("RepoMan " + repoManager);
//        Module module = new Module();
////        module.setId("mod_facebook-0.7.3");
////        module.setName("facebook");
////        module.setVersion("0.7.3");
///        File moduleContent = new File("D:\\work\\projects\\phresco\\phresco-projects\\trunk\\files\\android-native\\modules\\DOGRA\\DOGRA.zip");
//        repoManager.addModule(TechnologyTypes.ANDROID_NATIVE, module, moduleContent, null);
//        System.out.println("module uploaded succesfully ");
    }

}
