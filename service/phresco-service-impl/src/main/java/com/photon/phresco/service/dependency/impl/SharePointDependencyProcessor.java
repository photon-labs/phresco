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
package com.photon.phresco.service.dependency.impl;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.service.api.RepositoryManager;

/**
 * Dependency handler for Sharepoint
 * @author sathishkumar_dh
 *
 */
public class SharePointDependencyProcessor extends AbstractDependencyProcessor {
	private static final Logger S_LOGGER = Logger.getLogger(JWSDependencyProcessor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	

	public SharePointDependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}

	@Override
	protected String getModulePathKey() {
		return "sharepoint.modules.path";
	}
	
	@Override
	protected void extractModules(File path, List<ModuleGroup> modules) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method SharePointDependencyProcessor.extractModules(File path, List<TupleBean> modules)");
		}
		super.extractModules(path, modules);
		
		System.out.println("Sharepoint point : "+path);
		
		constructSolutionFile(path, modules);
	}

	private void constructSolutionFile(File path, List<ModuleGroup> modules) {
		//TODO: Construct solution file with the selected features. 
		//To do that concat the all the feature solutionid.txt file in the config file. 
	}
	
}
