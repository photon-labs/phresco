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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.DependencyProcessor;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.util.TechnologyTypes;

/**
 * Factory class to get the Dependency processor based on technology selected.
 * @author arunachalam_l
 *
 */
public final class DependencyProcessorFactory {
	private static final Logger S_LOGGER = Logger.getLogger(DependencyProcessorFactory.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
    /**
     * Cache for dependency processors
     */
    private static Map<String, DependencyProcessor> processors = new HashMap<String, DependencyProcessor>();

    /**
     * internal constructor to prevent instantiations.
     */
    private DependencyProcessorFactory(){

    }

    /**
     * Return the {@link DependencyProcessor} processor for the given project technology
     * @param projectInfo project info
     * @return {@link DependencyProcessor}
     */
    public static synchronized DependencyProcessor getDependencyProcessor(ProjectInfo projectInfo){
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyProcessorFactory.getDependencyProcessor(ProjectInfo projectInfo)");
			S_LOGGER.debug("getDependencyProcessor() projectCode="+projectInfo.getCode());
		}
    	assert projectInfo != null;
        Technology technology = projectInfo.getTechnology();
        String techId = technology.getId();
        DependencyProcessor dependencyProcessor = processors.get(techId);
        if(dependencyProcessor == null){
            //initialize if defined
            dependencyProcessor = initializeProcessors(techId);
        }
        return dependencyProcessor;
    }

    /**
     * Creates and cache the {@link DependencyProcessor}
     * @param techId
     * @return
     */
    private static DependencyProcessor initializeProcessors(String techId) {
    	if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyProcessorFactory.initializeProcessors(String techId)");
			S_LOGGER.debug("getDependencyProcessor() TechnologyID="+techId);
		}

    	DependencyProcessor dependencyProcessor = null;
        
    	if(techId.equalsIgnoreCase(TechnologyTypes.PHP)){
            dependencyProcessor = new PHPDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else if(techId.equalsIgnoreCase(TechnologyTypes.PHP_DRUPAL7)||techId.equalsIgnoreCase(TechnologyTypes.PHP_DRUPAL6)) {
            dependencyProcessor = new Drupal7DependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else if(techId.equalsIgnoreCase(TechnologyTypes.ANDROID_NATIVE) || techId.equalsIgnoreCase(TechnologyTypes.ANDROID_HYBRID)) {
            dependencyProcessor = new AndroidDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else if(techId.equalsIgnoreCase(TechnologyTypes.NODE_JS_WEBSERVICE)) {
            dependencyProcessor = new NodeJsWebservicesDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else  if(techId.equalsIgnoreCase(TechnologyTypes.IPHONE_NATIVE) || techId.equalsIgnoreCase(TechnologyTypes.IPHONE_HYBRID)) {
            dependencyProcessor = new IPhoneDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else  if(techId.equalsIgnoreCase(TechnologyTypes.JAVA_WEBSERVICE)) {
            dependencyProcessor = new JWSDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        }else  if(techId.equalsIgnoreCase(TechnologyTypes.JAVA_STANDALONE)) {
            dependencyProcessor = new JWSDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        }  
        else  if(techId.equalsIgnoreCase(TechnologyTypes.HTML5_WIDGET) || techId.equalsIgnoreCase(TechnologyTypes.HTML5_MOBILE_WIDGET)|| techId.equalsIgnoreCase(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET)) {
            dependencyProcessor = new HTML5DependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else  if(techId.equalsIgnoreCase(TechnologyTypes.SHAREPOINT)) {
            dependencyProcessor = new SharePointDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else if(techId.equals(TechnologyTypes.WORDPRESS)){
        	dependencyProcessor = new WordPressDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        } else {
        	dependencyProcessor = new DefaultDependencyProcessor(PhrescoServerFactory.getRepositoryManager());
        }

//        processors.put(techId, dependencyProcessor);
        return dependencyProcessor;
    }
}
