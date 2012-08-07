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
package com.photon.phresco.service.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.TechnologyTypes;

public class DependencyPropertiesGenerator {

	String version = null;

	private Properties properties = new Properties();

	public DependencyPropertiesGenerator(String version) {
		this.version = version;
	}

	public void generate() throws IOException {
		// Adding apptype config
		properties.setProperty("apptype.config", "/config/apptype/" + version
				+ "/apptype-" + version + ".xml");

		// Adding Technology specific configuration
		for (String techId : TechnologyTypes.ALL) {
			properties.setProperty("module." + techId, "/modules/" + techId
					+ "/" + version + "/" + techId + "-" + version + ".xml");
			properties.setProperty("platform." + techId, "/platforms/" + techId
					+ "/" + version + "/" + techId + "-" + version + ".xml");
			properties.setProperty("database." + techId, "/databases/" + techId
					+ "/" + version + "/" + techId + "-" + version + ".xml");
			properties.setProperty("appserver." + techId, "/appservers/"
					+ techId + "/" + version + "/" + techId + "-" + version
					+ ".xml");
			properties.setProperty("editor." + techId, "/editors/" + techId
					+ "/" + version + "/" + techId + "-" + version + ".xml");
			properties.setProperty("pilots." + techId, "/pilots/" + techId
					+ "/" + version + "/" + techId + "-" + version + ".pilots");
			properties.setProperty("pilots.url." + techId, "/pilots/" + techId
					+ "/" + version + "/" + techId + "-" + version + ".zip");
		}

		// Adding JSLibraries
		generateJsLibrary(version);
	}

	private void generateJsLibrary(String version) {
		properties.setProperty("jslibrary.tech-php",
				"/jslibraries/jslibraries/" + version + "/jslibraries-"
						+ version + ".xml");
		properties.setProperty("jslibrary.tech-html5-widget",
				"/jslibraries/jslibraries/" + version + "/jslibraries-"
						+ version + ".xml");
		properties.setProperty("jslibrary.tech-html5",
				"/jslibraries/jslibraries/" + version + "/jslibraries-"
						+ version + ".xml");
		properties.setProperty("jslibrary.tech-phpdru7",
				"/jslibraries/jslibraries/" + version + "/jslibraries-"
						+ version + ".xml");
		properties.setProperty("jslibrary.tech-html5-mobile-widget",
				"/jslibraries/jslibraries/" + version + "/jslibraries-"
						+ version + ".xml");
	}

	private  void writeFile(File file) throws PhrescoException{
		try{
		generate();
		properties.store(new FileOutputStream(file), "Dependency file");
		}catch (Exception e) {
			throw new PhrescoException();
		}

	}
	public static void main(String[] args) throws PhrescoException {
		DependencyPropertiesGenerator gen = new DependencyPropertiesGenerator("0.3.24000");
		gen.publish();
	}

	public void publish() throws PhrescoException {
		File file = new File("D:/dependency.properties");
		DependencyPropertiesGenerator generator = new DependencyPropertiesGenerator("0.3.24000");
		generator.writeFile(file);
		 PhrescoServerFactory.initialize();
		RepositoryManager repositoryManager = PhrescoServerFactory.getRepositoryManager();
		ArtifactInfo info = new ArtifactInfo("config.","dependency", "", "properties", "1.0.0");
		//repositoryManager.addArtifact(info, file);
	}

}
