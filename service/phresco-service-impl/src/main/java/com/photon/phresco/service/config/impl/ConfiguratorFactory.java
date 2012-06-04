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
package com.photon.phresco.service.config.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.ApplicationConfigurator;
import com.photon.phresco.util.TechnologyTypes;

/**
 * @author arunachalam_l
 *
 */
public class ConfiguratorFactory {
	private static final Logger S_LOGGER = Logger.getLogger(ConfiguratorFactory.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	/**
	 * Cache for Application Configurators
	 */
	private static Map<String, ApplicationConfigurator> configurators = new HashMap<String, ApplicationConfigurator>();

	private ConfiguratorFactory(){
		//prevent instantiation
	}
	
	public static ApplicationConfigurator getConfigurator(ProjectInfo projectInfo) {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method ConfiguratorFactory.getConfigurator(ProjectInfo projectInfo)");
		}
		if (DebugEnabled) {
			S_LOGGER.debug("getConfigurator() ProjectCode"+projectInfo.getCode());
		}
		assert projectInfo != null;
		Technology technology = projectInfo.getTechnology();
		String techId = technology.getId();
		if (DebugEnabled) {
			S_LOGGER.debug("getConfigurator() TechnologyID="+techId);
		}
		ApplicationConfigurator applicationConfigurator = configurators.get(techId);
		if(applicationConfigurator == null){
			//initialize if defined
			applicationConfigurator = initializeConfigurators(techId);
		}
		return applicationConfigurator;
	}

	/**
	 * 
	 * @param techId
	 * @return {@link ApplicationConfigurator}
	 */
	private static ApplicationConfigurator initializeConfigurators(String techId) {
		if (DebugEnabled) {
			S_LOGGER.debug("Entering Method  ConfiguratorFactory.initializeConfigurators(String techId)");
		}
		if (DebugEnabled) {
			S_LOGGER.debug("initializeConfigurators() TechnologyId="+techId);
		}
		ApplicationConfigurator applicationConfigurator = null;
		if(techId.equalsIgnoreCase(TechnologyTypes.ANDROID_NATIVE)) {
			applicationConfigurator = new AndroidConfigurator();
		} else {
			applicationConfigurator = new BaseApplicationConfigurator();
		}

		configurators.put(techId, applicationConfigurator);
		return applicationConfigurator;
	}

}
