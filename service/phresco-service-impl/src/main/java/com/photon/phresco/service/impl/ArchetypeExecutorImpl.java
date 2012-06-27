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
package com.photon.phresco.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.ArchetypeExecutor;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.jaxb.ArchetypeInfo;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.ServiceConstants;

public class ArchetypeExecutorImpl implements ArchetypeExecutor,
        ServiceConstants, Constants {
    public static final Logger S_LOGGER 			= Logger.getLogger(ArchetypeExecutorImpl.class);
    public static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
    private static final String INTERACTIVE_MODE 	= "false";
    public static final String WINDOWS 			= "Windows";
    private static final String PHRESCO_FOLDER_NAME = "phresco";
    private static final String DOT_PHRESCO_FOLDER 	= "." + PHRESCO_FOLDER_NAME;

    private ServerConfiguration serverConfig = null;

    public ArchetypeExecutorImpl(ServerConfiguration serverConfig) {
        this.serverConfig = serverConfig;
    }

    public File execute(ProjectInfo info) throws PhrescoException {
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ArchetypeExecutorImpl.execute(ProjectInfo info)");
			S_LOGGER.debug("execute() ProjectCode="+info.getCode());
		}
    	String commandString = buildCommandString(info);

        Commandline cl = new Commandline(commandString);
        cl.setWorkingDirectory(getTempFolderPath());

        if (S_LOGGER.isDebugEnabled()) {
            S_LOGGER.debug("command String " +commandString);
        }

        try {
            Process p = cl.execute();

            //the below implementation is required since a new command or shell is forked
            //from the existing running web server command or shell instance
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ( in.readLine() != null) {
            }
            createProjectFolders(info, cl.getWorkingDirectory());
        } catch (CommandLineException e) {
            throw new PhrescoException(e);
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
        return cl.getWorkingDirectory();
    }

    private void createProjectFolders(ProjectInfo info, File file) throws PhrescoException {
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ArchetypeExecutorImpl.createProjectFolders(ProjectInfo info, File file)");
			S_LOGGER.debug("createProjectFolders()  path="+file.getPath());
		}
        //create .phresco folder inside the project
    	if (isDebugEnabled) {
			S_LOGGER.debug("createProjectFolders()  ProjectCode="+info.getCode());
		}
    	File phrescoFolder = new File(file.getPath() + File.separator + info.getCode() + File.separator + DOT_PHRESCO_FOLDER);
        phrescoFolder.mkdirs();
        if (isDebugEnabled) {
			S_LOGGER.info("create .phresco folder inside the project");
		}
       ProjectUtils.writeProjectInfo(info, phrescoFolder);
    }

	private String getTempFolderPath() {
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ArchetypeExecutorImpl.getTempFolderPath()");
		}
    	String tempFolderPath = "";
        String systemTempFolder = System.getProperty(JAVA_TMP_DIR);
        String uuid = UUID.randomUUID().toString();

        // handled the file separator since java.io.tmpdir does not return
        // the last file separator in linux and Mac OS
        if ((systemTempFolder.endsWith(File.separator))) {
            tempFolderPath = systemTempFolder + PHRESCO_FOLDER_NAME;
        } else {
            tempFolderPath = systemTempFolder + File.separator + PHRESCO_FOLDER_NAME;
        }

        tempFolderPath = tempFolderPath + File.separator + uuid;
        File tempFolder = new File(tempFolderPath);
        tempFolder.mkdirs();

        if (isDebugEnabled) {
            S_LOGGER.debug("getTempFolderPath() Temp Folder path= " + tempFolderPath);
        }

        return tempFolderPath;
    }

    private String buildCommandString(ProjectInfo info) throws PhrescoException {
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ArchetypeExecutorImpl.buildCommandString(ProjectInfo info)");
			S_LOGGER.debug("buildCommandString() ProjectCode="+info.getCode());
		}
    	
    	ArchetypeInfo archInfo = PhrescoServerFactory.getRepositoryManager().getArchetype(info);
        if (archInfo == null) {
            throw new PhrescoException("Archetype not defined for " + info.getTechnology().getName());
        }

        // For Thread-Safe,using StringBuffer instead of StringBuilder
        StringBuffer commandStr = new StringBuffer();

        commandStr.append(Constants.MVN_COMMAND).append(STR_BLANK_SPACE)
                .append(Constants.MVN_ARCHETYPE).append(STR_COLON).append(Constants.MVN_GOAL_GENERATE)
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_ARCHETYPEGROUPID).append(STR_EQUALS).append(archInfo.getGroupId())
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_ARCHETYPEARTIFACTID).append(STR_EQUALS).append(archInfo.getArtifactId())
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_ARCHETYPEVERSION).append(STR_EQUALS).append(archInfo.getVersion())
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_GROUPID).append(STR_EQUALS).append(archInfo.getProjectGroupId())
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_ARTIFACTID).append(STR_EQUALS).append(STR_DOUBLE_QUOTES).append(info.getCode()).append(STR_DOUBLE_QUOTES) //artifactId --> project name could have space in between
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_VERSION).append(STR_EQUALS).append(info.getVersion())
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_ARCHETYPEREPOSITORYURL).append(STR_EQUALS).append(serverConfig.getRepositoryURL())
                .append(STR_BLANK_SPACE)
                .append(ARCHETYPE_INTERACTIVEMODE).append(STR_EQUALS).append(INTERACTIVE_MODE);

        return commandStr.toString();
    }

}
