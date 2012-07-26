/*
 * ###
 * Archetype - phresco-html5-archetype
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
package com.photon.phresco.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigReader {
	
	//envname, env dom element
	private static final Map<String, Element> ENV_MAP = new HashMap<String, Element>();
	private static String defaultEnvironment = null;
	private Document document = null;
	private File configFile = null;
	
	/**
	 * ConfigReader single instance created by configuration xml
	 * @param configXML File type
	 * @return
	 * @throws Exception
	 */
	public ConfigReader(File configXML) throws Exception {
		if (configXML.exists()) {
			this.configFile = configXML;
			initXML(new FileInputStream(configXML));
		}
	}
	
	/**
	 * ConfigReader single instance created by configuration xml input stream
	 * @param xmlStream
	 * @return
	 * @throws Exception
	 */
	public ConfigReader(InputStream xmlStream) throws Exception {
		initXML(xmlStream);
	}

	/**
	 * Returns the defaut environment name
	 * @return
	 */
	public String getDefaultEnvName() {
		return defaultEnvironment;
	}
	
	/**
	 * Returns the Configurations of given environments
	 * @param envName - Environment name
	 * @return
	 */
	public List<Configuration> getConfigByEnv(String envName) {
		List<Configuration> configurations = new ArrayList<Configuration>();
		Element environment = getEnvironment(envName);
		if (environment != null) {
			NodeList configNodes = environment.getChildNodes();
			for (int i = 0; i < configNodes.getLength(); i++) {
				if (configNodes.item(i).getNodeType() !=  Element.TEXT_NODE) {
					Element configNode = (Element) configNodes.item(i);
					String configType = configNode.getNodeName();
					String configName = configNode.getAttribute("name");
					String configDesc = configNode.getAttribute("desc");
					String configAppliesTo = configNode.getAttribute("appliesTo");
					Properties properties = getProperties(configNode);
					Configuration config = new Configuration(configName, configDesc, envName, configType, properties, configAppliesTo);
					configurations.add(config);
				}
			}
		}
		return configurations;
	}
	
	/**
	 * Returns the Configurations of given environments by configuration type
	 * @param envName
	 * @param configType
	 * @return
	 */
	public List<Configuration> getConfigurations(String envName, String configType) {
		List<Configuration> configurations = getConfigByEnv(envName);
		List<Configuration> filterConfigs = new ArrayList<Configuration>(configurations.size());
		for (Configuration configuration : configurations) {
			if (configuration.getType().equals(configType)) {
				filterConfigs.add(configuration);
			}	
		}
		return filterConfigs;
	}
	
	/**
	 * loads the configuration xml as input stream
	 * @param xmlStream
	 * @throws Exception
	 */
	protected void initXML(InputStream xmlStream) throws Exception {
		try {
			if (xmlStream == null) {
				xmlStream = this.getClass().getClassLoader().getResourceAsStream("configuration.xml");
			}
			parseXML(xmlStream);
		} finally {
			try {
				xmlStream.close();
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
	}
	
	/**
	 * Creating Dom object to parse the configuration xml 
	 * @param xmlStream
	 * @throws Exception
	 */
	private void parseXML(InputStream xmlStream) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		document = builder.parse(xmlStream);
		parseDocument(document);
	}
	
	/**
	 * parse the configuration xml
	 * @param document
	 */
	private void parseDocument(Document document) {
		//get a nodelist of environments
		NodeList environmentList = document.getElementsByTagName("environment");
		ENV_MAP.clear();
		
		for(int i = 0 ; i < environmentList.getLength(); i++) {

			//get the environment element
			Element environment = (Element) environmentList.item(i);
			String envName = environment.getAttribute("name");
			
			boolean defaultEnv = Boolean.parseBoolean(environment.getAttribute("default"));
			if (defaultEnv) {
				defaultEnvironment = envName;
			}
			//add environment element to map
			ENV_MAP.put(envName, environment);
		}
	}
	
	protected Document getDocument() {
		return document;
	}
	
	/**
	 * return the environments
	 * @return
	 */
	protected Map<String, Element> getEnviroments() {
		return ENV_MAP;
	}
	
	/**
	 * return the environment element for the given Environment name
	 * @param envName
	 * @return
	 */
	protected Element getEnvironment(String envName) {
		return ENV_MAP.get(envName);
	}
	
	/**
	 * return the property of the given configuration
	 * @param configNode
	 * @return
	 */
	private Properties getProperties(Element configNode) {
		Properties props = new Properties();
		NodeList propNodes = configNode.getChildNodes();
		for(int i = 0 ; i < propNodes.getLength(); i++) {
			if (propNodes.item(i).getNodeType() !=  Element.TEXT_NODE) {
				//get the environment element
				Element propNode = (Element) propNodes.item(i);
				String propName = propNode.getNodeName();
				String propValue = propNode.getTextContent();
				props.put(propName, propValue);
			}
		}
		return props;
	}
	
	public File getConfigFile() {
		return configFile;
	}
	
	public String getConfigAsJSON(String envName, String configType) {
		if (envName == null) {
			envName = getDefaultEnvName();
		}
		List<Configuration> configurations = getConfigByEnv(envName);
		String json = "";
		for (Configuration configuration : configurations) {
			if (configuration.getType().equalsIgnoreCase(configType)) {
				com.google.gson.Gson gson = new com.google.gson.Gson();
				Properties properties = configuration.getProperties();
				json = gson.toJson(properties, Properties.class);
			}
		}
		return json;	
	}
	
	public String getConfigAsJSON(String envName, String configType, String configName) {
		String json = "";
		com.google.gson.Gson gson = new com.google.gson.Gson();
		if (envName == null) {
			envName = getDefaultEnvName();
		}
		List<Configuration> configurations = getConfigurations(envName, configType);
		if (configurations != null && configurations.size() > 0 
				&& (configName == null || configName.isEmpty())) {
			Properties properties = configurations.get(0).getProperties();
			json = gson.toJson(properties, Properties.class);
			return json;
		}
		for (Configuration configuration : configurations) {
			if (configuration.getName().equalsIgnoreCase(configName)) {
				Properties properties = configuration.getProperties();
				json = gson.toJson(properties, Properties.class);
				break;
			}
		}
		return json;

	}
}
