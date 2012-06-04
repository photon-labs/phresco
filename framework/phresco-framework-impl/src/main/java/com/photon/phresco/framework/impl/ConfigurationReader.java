/*
 * ###
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;

public class ConfigurationReader extends ConfigReader {

	public ConfigurationReader(File configFile) throws Exception {
		super(configFile);
	}
	
	public Document getDocument() {
		return super.getDocument();
	}
	
	public List<Environment> getEnvironments() {
		Collection<Element> enviroments = super.getEnviroments().values();
		List<Environment> envs = new ArrayList<Environment>(enviroments.size());
		for (Element envElement : enviroments) {
			String envName = envElement.getAttribute("name");
			String envDesc = envElement.getAttribute("desc");
			String defaultEnv = envElement.getAttribute("default");
			Environment environment = new Environment(envName, envDesc, Boolean.parseBoolean(defaultEnv));
			environment.setDelete(canDelete(envElement));
			envs.add(environment);
		}
		return envs;
	}
	
	public Collection<String> getEnvironmentNames() {
		return super.getEnviroments().keySet();
	}
	
	public boolean canDelete(Element envElement) {
		if (!envElement.hasChildNodes() || (envElement.getChildNodes().getLength() == 1 &&
				envElement.getChildNodes().item(0).getNodeType() == Element.TEXT_NODE)) {
			return true;
		}
		return false;
	}
	
	
	public Element getEnvironment(String envName) {
		return super.getEnviroments().get(envName);
	}

	public List<Configuration> getConfigurations() {
		Set<String> envNames = getEnviroments().keySet();
		List<Configuration> configs = new ArrayList<Configuration>(10);
		for (String envName : envNames) {
			Collection<Configuration> configByEnv = getConfigByEnv(envName);
			configs.addAll(configByEnv);
		}
		return configs;
	}

	public List<String> getConfigNameByEnv(String envName, String configName) throws XPathExpressionException {
		List<Configuration> configurations = super.getConfigByEnv(envName);
		List<String> configNames = new ArrayList<String>(configurations.size());
		for (Configuration configuration : configurations) {
			configNames.add(configuration.getName());
		}
		return configNames;
	}
	
	public static void main(String a[]) {
		try {
			File configXml = new File("/Users/bharatkumarradha/Desktop/config.xml");
			ConfigurationReader reader = new ConfigurationReader(configXml);
			List<Environment> environments = reader.getEnvironments();
			for (Environment environment : environments) {
				System.out.println("Name = " + environment.getName() + " delete = " + environment.canDelete());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
