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
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.TechnologyTypes;

/*the class JavawebServicePilotProject*/
public class JavaWebServicePilotProject implements IPilotProject {

	private static final String JAVA_BUILD_FILE = "java-webservice\\eShop\\src\\build.xml";
	private RepositoryManager repManager = null;

	/* new JavawebservicePilotProject instantiated */
	public JavaWebServicePilotProject(RepositoryManager repoManager) {
		this.repManager = repoManager;
	}

	/* command line execution */
	public void buildPilotProject() throws PhrescoException {
		try {
			Commandline cl = new Commandline("ant -buildfile "
					+ PilotProjectManager.PILOT_BASE + JAVA_BUILD_FILE );
			cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}

	}

	/* publish JavawebservicePilotProject to desired path */
	public void publishPilotProject() throws PhrescoException {
			ArtifactInfo info = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.JAVA_WEBSERVICE, "", "zip", "0.1");
			File artifact = new File(PilotProjectManager.PILOT_BASE + "java-webservice\\eshop\\target\\eshop-0.1.zip");
			repManager.addArtifact(info, artifact);
		}

	/* Javawebservice PilotProject json data creation */
	public void publishMetaData() throws PhrescoException {
		ProjectInfo info = new ProjectInfo();
		info.setName("EshopJavaWebservice");
		info.setCode("PHTN_PILOT_eShopping");
		info.setDescription("This is the pilot project demonstrating several features of JavaWebService.");
		info.setApplication("apptype-webapp");
		
		Technology tech = new Technology();
		tech.setId(TechnologyTypes.JAVA_WEBSERVICE);
		tech.setName("JAVAWEBSERVICE");
		info.setPilotProjectUrls(new String[]{"/pilots/tech-java-webservice/0.1/tech-java-webservice-0.1.zip"});
		Gson gson = new Gson();
		String json = gson.toJson(info);
		FileWriter write;
		String tempFileName = "javawebserviceJson.pilots";
		File tempFile = new File(tempFileName);
		try {
			write = new FileWriter(tempFile);
			write.write(json);
			write.close();
			ArtifactInfo aInfo = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.JAVA_WEBSERVICE, "", "pilots", "0.1");
			File artifact = new File(tempFileName);
			repManager.addArtifact(aInfo, artifact);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if(tempFile !=null && tempFile.exists()) {
				tempFile.delete();
			}
		}
	}

}
