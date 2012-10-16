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
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.util.TechnologyTypes;
import com.phresco.pom.exception.PhrescoPomException;

public class HTML5DependencyProcessor extends AbstractJsLibDependencyProcessor {
	
	private static final Logger S_LOGGER = Logger.getLogger(HTML5DependencyProcessor.class);

	public HTML5DependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}
	
	@Override
	public void process(ProjectInfo info, File path, PROCESSTYPE processType) throws PhrescoException {
		S_LOGGER.debug("Entering Method HTML5DependencyProcessor.process(ProjectInfo info, File path)");
		S_LOGGER.debug("process() Path=" + path.getPath());
		updatePom(path, info.getTechnology().getModules());
		if (info.getTechnology().getId().equals(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET) ||
				info.getTechnology().getId().equals(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET)) {
		    updatePOMWithJsLibs(path, info.getTechnology().getJsLibraries());
		} else {
		    extractJsLibraries(path, info.getTechnology().getJsLibraries());
		}
		createSqlFolder(info, path);
		if (PROCESSTYPE.CREATE.equals(processType)) {
			extractPilots(info, path, info.getTechnology());
		}
		updateTestPom(path);
	}

	protected void updatePom(File path, List<ModuleGroup> modules)	throws PhrescoException {
		if(CollectionUtils.isEmpty(modules)) {
			return;
		}
		try {
			updatePOMWithModules(path, modules);
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			
			throw new PhrescoException(e);
		}
		
	}
	
	@Override
	protected String getJsLibPathKey() {
		return "html5.jslib.path";
	}
}