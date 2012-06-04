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

/* The Class PhpPilotProject.*/

public class PhpPilotProject implements IPilotProject {

	private static final String PHP_BUILD_FILE = "php\\blog\\build.xml";

	private RepositoryManager repManager = null;

	/* new php pilot project Instantiated */

	public PhpPilotProject(RepositoryManager repoManager) {
		this.repManager = repoManager;
	}

	/* command line execution */
	public void buildPilotProject() throws PhrescoException {
		try {
			Commandline cl = new Commandline("ant -buildfile " + PilotProjectManager.PILOT_BASE + PHP_BUILD_FILE);
			cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}

	}

	/* publish PhpPilotProject to desired path */
	public void publishPilotProject() throws PhrescoException {
		ArtifactInfo info = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.PHP, "", "zip", "0.1");
		File artifact = new File(PilotProjectManager.PILOT_BASE + "/php/blog/build/blog.zip");
		repManager.addArtifact(info, artifact);
	}

	/*PhpPilotProject json data creation */
	public void publishMetaData() throws PhrescoException {
		ProjectInfo info = new ProjectInfo();
		info.setName("Pilot_PHP_Blog");
		info.setCode("PHP_Blog");
		info.setDescription("This is the photon style Blog developed with PHP");
		info.setApplication("apptype-webapp");

		Technology tech = new Technology();
		tech.setId("tech-php");
		tech.setName("PHP");

		tech.setFrameworks(null);

//		List<TupleBean> platforms = new ArrayList<TupleBean>();
//		platforms.add(new TupleBean("plat-Drupal-win32", "Win32", "2.3"));
//		tech.setPlatforms(platforms);
//
//		List<TupleBean> databases = new ArrayList<TupleBean>();
//		databases.add(new TupleBean("database-Drupal-Mysql", "Mysql", "2.0"));
//		tech.setDatabases(databases);
//
//		List<TupleBean> appServers = new ArrayList<TupleBean>();
//		appServers.add(new TupleBean("appserver-Drupal-Apache", "Apache", "3.0"));
//		tech.setAppServers(appServers);
//
//		List<TupleBean> editors = new ArrayList<TupleBean>();
//		editors.add(new TupleBean("editor-Drupal-PHP Edit", "PHP Edit", "2.0"));
//		tech.setEditors(editors);

		info.setTechnology(tech);
		info.setPilotProjectUrls(new String[] { "/pilots/tech-php/0.1/tech-php-0.1.zip" });
		Gson gson = new Gson();

		String json = gson.toJson(info);

		try {
			FileWriter write = new FileWriter("phpJson.pilots");
			write.write(json);
			write.close();
			ArtifactInfo aInfo = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.PHP, "", "pilots", "0.1");
			File artifact = new File("phpJson.pilots");
			repManager.addArtifact(aInfo, artifact);

		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

}
