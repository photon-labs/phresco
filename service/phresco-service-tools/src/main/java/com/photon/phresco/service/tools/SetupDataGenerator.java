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

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;

public class SetupDataGenerator {

    private static final Logger S_LOGGER = Logger.getLogger(SetupDataGenerator.class);
    private File outputRootDir;
    private File inputRootDir;

    public SetupDataGenerator(File inputRootDir, File outputRootDir) throws PhrescoException {
        this.outputRootDir = outputRootDir;
        this.inputRootDir = inputRootDir;
    }

    public void generateAll() throws PhrescoException {
    //    generateApptype();
       generateTechnologyData();
     //   generateConfigurationDataGenerator();
    }

    private void generateTechnologyData() throws PhrescoException {
        System.out.println("Generating Technology data ");
//        TechnologyDataGenerator dataGen = new TechnologyDataGenerator(inputRootDir, outputRootDir);
//        dataGen.generateAll();
        System.out.println("Technology data generated successfully");
    }

    private void generateApptype() throws PhrescoException {
        System.out.println("Generating Apptype ");
        File outFile = new File(outputRootDir, "config/apptype.xml");
        ApptypeGenerator gen = new ApptypeGenerator();
//        gen.generateApptypes(outFile);
        System.out.println("Apptype Generated successfully to : " + outFile.toString());

       if(S_LOGGER.isDebugEnabled())
       {
           S_LOGGER.info("Apptype Generated successfully to : " + outFile.toString());
       }
    }

    private void generateConfigurationDataGenerator() throws PhrescoException {
        System.out.println("Generating Config data ");
        File outFile = new File(outputRootDir, "config/settings-0.1.json");
        ConfigurationDataGenerator con = new ConfigurationDataGenerator();
        con.generateConfiguration(outFile);
        System.out.println("Configuration Generated successfully to : " + outFile.toString());

        if(S_LOGGER.isDebugEnabled())
        {
            S_LOGGER.info("Configuration Generated successfully to : " + outFile.toString());
        }
    }

    public static void main(String[] args) throws PhrescoException {
        File toolsHome = new File("D:\\work\\phresco\\agra\\service\\trunk\\phresco-service-runner\\delivery\\tools\\");
        File inputRootDir = new File(toolsHome, "files");
        File outputRootDir = new File(toolsHome, "repo");
        SetupDataGenerator dataGen = new SetupDataGenerator(inputRootDir, outputRootDir);
        dataGen.generateAll();
    }
    
}
