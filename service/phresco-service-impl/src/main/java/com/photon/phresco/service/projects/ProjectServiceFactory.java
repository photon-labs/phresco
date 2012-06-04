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
package com.photon.phresco.service.projects;

import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.ProjectService;

public class ProjectServiceFactory {

//	private static Map<String, ProjectService> projectServiceCache = new HashMap<String, ProjectService>();
	
	private static ProjectService _serverInstance = null;

	private ProjectServiceFactory(){
		//prevent instantiation
	}
	
	public static synchronized ProjectService getProjectService(ProjectInfo projectInfo) {
		assert projectInfo != null;
		//irrespective of Technology return the DefaultProjectService. Change this when new Project Service is required.
		if (_serverInstance == null) {
			_serverInstance = new DefaultProjectService();
		}
		return _serverInstance;
	}
	public static synchronized ProjectService getNewProjectService(ProjectInfo projectInfo) {
		return new DefaultProjectService();
	}
}
