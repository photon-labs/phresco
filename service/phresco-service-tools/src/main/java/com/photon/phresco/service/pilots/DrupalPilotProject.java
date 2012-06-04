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

/*the class DrupalPilotProject*/
public class DrupalPilotProject implements IPilotProject {

	private static final String PHP_DRUPAL7_BUILD_FILE = "drupal7\\eShop\\build.xml";
	private RepositoryManager repManager = null;
/*new DrupalPilotProject instantiated*/
	public DrupalPilotProject(RepositoryManager repoManager) {
		this.repManager = repoManager;
	}
	/*command line execution*/
	public void buildPilotProject() throws PhrescoException {
		try {
			Commandline cl = new Commandline("ant -buildfile " + PilotProjectManager.PILOT_BASE + PHP_DRUPAL7_BUILD_FILE);
			cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}

	}
	/*publish DrupalPilotProject to desired path*/
	public void publishPilotProject() throws PhrescoException {
		ArtifactInfo info = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.PHP_DRUPAL7, "", "zip", "0.1");
		File artifact = new File(PilotProjectManager.PILOT_BASE + "\\drupal7\\eShop\\build\\eShop.zip");
		repManager.addArtifact(info, artifact);
	}

	/*DrupalPilotProject json data creation*/
	public void publishMetaData() throws PhrescoException {
		ProjectInfo info = new ProjectInfo();
		info.setName("eName");
		info.setCode("PHTN_PILOT_eShop");
		info.setDescription("This is the pilot project demonstrating several features of Drupal7.");
		info.setApplication("apptype-webapp");

		Technology tech = new Technology();
		tech.setId("tech-phpdru7");
		tech.setName("Drupal7");
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
//		editors.add(new TupleBean("1.0", "Dreamweaver", "8.0"));
//		tech.setEditors(editors);
//
//		List<TupleBean> modules = new ArrayList<TupleBean>();
//		modules.add(new TupleBean("mod_chaos_tool_suite_7.x-1.0-rc1", "Chaos tool suite", "7.x-1.0-rc1"));
//		modules.add(new TupleBean("mod_rules_7.x-2.0-rc2", "Rules", "7.x-2.0-rc2"));
//		modules.add(new TupleBean("mod_views_7.x-3.0-rc1", "Views", "7.x-3.0-rc1"));
//		modules.add(new TupleBean("mod_entity_api_7.x-1.0-rc1", "Entity API", "7.x-1.0-rc1"));
//		modules.add(new TupleBean("mod_taxonomy_manager_7.x-1.0-beta2", "Taxonomy Manager", "7.x-1.0-beta2"));
//		modules.add(new TupleBean("mod_taxonomy_subterms__7.x-1.x-dev", "Taxonomy subterms ", "7.x-1.x-dev"));
//		modules.add(new TupleBean("mod_ubercart_7.x-3.0-rc2", "Ubercart", "7.x-3.0-rc2"));
//		modules.add(new TupleBean("mod_shopmenu_7.0", "shopmenu", "7.0"));
//		tech.setModules(modules);
		info.setTechnology(tech);
		info.setPilotProjectUrls(new String[] { "/pilots/tech-phpdru7/0.1/tech-phpdru7-0.1.zip" });
		Gson gson = new Gson();

		String json = gson.toJson(info);
		try {
			FileWriter write = new FileWriter("drupalJson.pilots");
			write.write(json);
			write.close();
			ArtifactInfo aInfo = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.PHP_DRUPAL7, "", "pilots", "0.1");
			File artifact = new File("drupalJson.pilots");
			repManager.addArtifact(aInfo, artifact);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

}
