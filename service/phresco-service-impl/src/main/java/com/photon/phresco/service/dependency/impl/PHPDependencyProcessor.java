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
package com.photon.phresco.service.dependency.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.util.Utility;

/**
 * Dependency handler for PHP projects
 *
 * @author arunachalam_l
 *
 */
public class PHPDependencyProcessor  extends AbstractJsLibDependencyProcessor {
	private static final Logger S_LOGGER = Logger.getLogger(PHPDependencyProcessor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
    /**
     * @param dependencyManager
     */
    public PHPDependencyProcessor(RepositoryManager repoManager) {
        super(repoManager);
    }
    
    @Override
    public void process(ProjectInfo info, File path) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method PHPDependencyProcessor.process(ProjectInfo info, File path)");
    		S_LOGGER.debug("process() Path=" + path.getPath());
		}
    	
    	String modulesPathString=DependencyProcessorMessages.getString(getModulePathKey()); //$NON-NLS-1$
        File modulesPath = new File(path, modulesPathString);
        Technology technology = info.getTechnology();
        File tempPath= new File(Utility.getPhrescoTemp(), UUID.randomUUID().toString()+ "/temp/modules");
        if (isDebugEnabled) {
        	S_LOGGER.debug("process() ProjectCode=" + info.getCode());
        	S_LOGGER.debug("Getting the Temporary File path="+ tempPath.getPath());
		}
        
        tempPath.mkdirs();
        try {
           
            //extract the selected modules into tempPath
            extractModules(tempPath, technology.getModules());
            File[] listFiles = tempPath.listFiles();
            for (File file : listFiles) {
                if(file.isDirectory()){
                    //copy the top level folders to the actual PHP content.
                    FileUtils.copyDirectoryStructure(file, modulesPath);
                }
            }
            
            //copy pilot projects
            if(StringUtils.isNotBlank(info.getPilotProjectName())){
	            List<ProjectInfo> pilotProjects = getRepositoryManager().getPilotProjects(technology.getId());
	            if(CollectionUtils.isEmpty(pilotProjects)){
	                return;
	            }
	
	            for (ProjectInfo projectInfo : pilotProjects) {
	//                extractModules(modulesPath, projectInfo.getTechnology().getModules());
	//				extractLibraries(modulesPath, projectInfo.getTechnology().getLibraries());
	                List<String> urls = projectInfo.getPilotProjectUrls();
	                if(urls != null){
	                    for (String url : urls) {
	                        DependencyUtils.extractFiles(url, path);
	                    }
	                }
	            }
            }
            extractJsLibraries(path, info.getTechnology().getJsLibraries());
            createSqlFolder(info, path);
        } catch (IOException e) {
            throw new PhrescoException(e);
        } finally {
        	  org.apache.commons.io.FileUtils.deleteQuietly(tempPath);
        }
    }

    @Override
	protected String getModulePathKey() {
		return "php.modules.path";
	}
	
	@Override
	protected String getJsLibPathKey() {
		return "php.jslib.path";
	}
}
