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
package com.photon.phresco.service.pilots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.util.TechnologyTypes;

/*the class SharepointPilotProject*/
public class SharepointPilotProject implements IPilotProject {
	private static final String SHAREPOINT_BUILD_FILE = "sharepoint\\ResourceManagement\\build.xml";
	private RepositoryManager repManager = null;

	/* new SharepointPilotProject instantiated */
	public SharepointPilotProject(RepositoryManager repoManager) {
		this.repManager = repoManager;
	}

	/* command line execution */
	public void buildPilotProject() throws PhrescoException {
		try {
			Commandline cl = new Commandline("ant -buildfile " + PilotProjectManager.PILOT_BASE + SHAREPOINT_BUILD_FILE);
			cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}

	}

	/* publish SharepointPilotProject to desired path */
	public void publishPilotProject() throws PhrescoException {
		ArtifactInfo info = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.SHAREPOINT, "", "zip", "0.1");
		File artifact = new File(PilotProjectManager.PILOT_BASE + "\\sharepoint\\ResourceManagement\\build\\ResourceManagement.zip");
		repManager.addArtifact(info, artifact);
	}

	/* Sharepoint pilotProject json data creation */
	public void publishMetaData() throws PhrescoException {

		ProjectInfo info = new ProjectInfo();
		info.setName("PilotSharePoint");
		info.setCode("PHR_PilotSharePoint");
		info.setDescription("");
		info.setApplication("apptype-webapp");

		Technology tech = new Technology();
		tech.setId("tech-sharepoint");
		tech.setName("Sharepoint");

		tech.setFrameworks(null);

		info.setTechnology(tech);
		info.setPilotProjectUrls(new String[] { "/pilots/tech-sharepoint/0.1/tech-sharepoint-0.1.zip" });
		Gson gson = new Gson();

		String json = gson.toJson(info);
		try {
			FileWriter write = new FileWriter("sharepointJson.pilots");
			write.write(json);
			write.close();
			ArtifactInfo aInfo = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.SHAREPOINT, "", "pilots", "0.1");
			File artifact = new File("sharepointJson.pilots");
			repManager.addArtifact(aInfo, artifact);

		} catch (IOException e) {
			throw new PhrescoException(e);
		}

	}

}
