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

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.RepositoryManager;

public class BlackBerryDependencyProcessor extends AbstractJsLibDependencyProcessor {

	public BlackBerryDependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}


	@Override
	protected String getModulePathKey() {
		return "blackberry.modules.path";
	}
	
	@Override
	public void process(ProjectInfo info, File path, PROCESSTYPE processType) throws PhrescoException {
		 extractJsLibraries(path, info.getTechnology().getJsLibraries());
	}

	@Override
	protected String getJsLibPathKey() {
		return "blackberry.jslib.path";
	}
}
