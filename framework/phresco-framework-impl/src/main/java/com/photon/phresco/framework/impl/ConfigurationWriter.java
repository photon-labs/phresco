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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.configuration.ConfigWriter;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.SettingsInfo;

public class ConfigurationWriter extends ConfigWriter {

	private ConfigurationReader reader = null;

	/**
	 * Constructor of ConfigWriter
	 * @param reader
	 * @throws Exception 
	 */
	public ConfigurationWriter(ConfigurationReader reader, boolean newFile) throws Exception {
		super(reader, newFile);
		this.reader = reader;
	}
	
	/**
	 * creates the environment
	 * @param selectedEnvs
	 */
	public void createEnvironment(List<Environment> envs) {
		for (Environment env : envs) {
			Element envNode = getDocument().createElement("environment");
			envNode.setAttribute("name", env.getName());
			envNode.setAttribute("desc", env.getDesc());
			envNode.setAttribute("default", Boolean.toString(env.isDefaultEnv()));
			getRootElement().appendChild(envNode);
		}
	}
	
	/**
	 * Delete the environments
	 * @param selectedEnvs
	 * @throws Exception 
	 */
	public void deleteEnvironments(List<String> envNames) throws Exception {
		for (String envName : envNames) {
			String xpath = getXpathEnv(envName).toString();
			Element envNode = (Element) getNode(xpath);
			envNode.getParentNode().removeChild(envNode);
			if (!reader.canDelete(envNode)) {
				throw new Exception(envNode.getAttribute("name") + " can be deleted");
			}
		}
	}
	
	/**
	 * Read the Environments to create the configurations
	 * @param selectedEnvStr
	 * @throws PhrescoException
	 */
	public void createConfiguration(String selectedEnvStr, SettingsInfo settingsInfo) throws PhrescoException {
		String[] envs = selectedEnvStr.split(",");
		for (String envName : envs) {
			Element environment = reader.getEnvironment(envName);
			createConfiguration(settingsInfo, environment, false);
		}
	}
	
	/**
	 * Delete the selected configurations of the given environments 
	 * @param configNames
	 * @throws XPathExpressionException
	 * @throws PhrescoException 
	 */
	public void deleteConfigurations(Map<String, List<String>> configurations) throws XPathExpressionException, PhrescoException {
		Set<String> keySet = configurations.keySet();
		for (String envName : keySet) {
			List<String> configNames = configurations.get(envName);
			deleteConfiguration(envName, configNames);
		}
	}
	
	public void updateConfiguration(String envName, String oldConfigName, SettingsInfo settingsInfo) throws XPathExpressionException, PhrescoException {
		Node environment = getNode(getXpathEnv(envName).toString());
		if (environment == null) {
			throw new PhrescoException("Environmnet not found to delete the Configuration");
		}
		Node oldConfigNode = getNode(getXpathConfigByEnv(envName, oldConfigName));
		if (oldConfigNode == null) {
			throw new PhrescoException("Configuration not found to delete");
		}
		
		Element configElement = createConfigElement(settingsInfo);
		environment.replaceChild(configElement, oldConfigNode);
	}
	
	public void updateTestConfiguration(SettingsInfo settingsInfo, String browser, String resultConfigXml, String resolution) throws PhrescoException {
	    try {
	        Node configNode = getNode("environment");
	        Node node = getNode("environment/" + settingsInfo.getType());
	        Node browserNode = getNode("environment/Browser");
	        Node resolutionNode = getNode("environment/resolution");
	        
	        if (node != null) {
	            configNode.removeChild(node);
	        }
	        
	        if (browserNode != null) {
	        	browserNode.setTextContent(browser);
	        } else {
	        	Element browserEle = getDocument().createElement("Browser");
	        	browserEle.setTextContent(browser);
	        	configNode.appendChild(browserEle);
	        }
	        if (resolution != null) {
		        if (resolutionNode !=  null ) {
		        	resolutionNode.setTextContent(resolution);
		        } else {
		        	Element resolutiontag = getDocument().createElement("resolution");
		        	resolutiontag.setTextContent(resolution);
		        	configNode.appendChild(resolutiontag);
		        }
	        }
	        configNode.appendChild(createConfigElement(settingsInfo));
        } catch (Exception e) {
            throw new PhrescoException("Configuration not found to delete");
        }
    }
	
	/**
	 * Created the configuration xml of selected environment using File object
	 * @param configXmlPath
	 * @param selectedEnvStr
	 * @throws Exception
	 */
	public void saveXml(File configXmlPath) throws Exception {
		super.writeXml(new FileOutputStream(configXmlPath));
	}
	
	private void deleteConfiguration(String envName, List<String> configNames) throws XPathExpressionException, PhrescoException {
		Node environment = getNode(getXpathEnv(envName).toString());
		if (environment == null) {
			throw new PhrescoException("Environmnet not found to delete the Configuration");
		}
		for (String configName : configNames) {
			String delete = "/environments/environment[@name='testEnvironment']/*[@name='TestServer']";
			Node configNode = getNode(getXpathConfigByEnv(envName, configName));
			if (configNode == null) {
				throw new PhrescoException("Configuration not found to delete");
			}
			environment.removeChild(configNode);
			
		}
	}
	
	private String getXpathConfigByEnv(String envName, String configName) {
		StringBuilder expBuilder = getXpathEnv(envName);
		expBuilder.append("/*[@name='");
		expBuilder.append(configName);
		expBuilder.append("']");
		return expBuilder.toString();
	}
	
	private StringBuilder getXpathEnv(String envName) {
		StringBuilder expBuilder = new StringBuilder();
		expBuilder.append("/environments/environment[@name='"); 
		expBuilder.append(envName);
		expBuilder.append("']");	
		return expBuilder;
	}
	
	
	private Node getNode(String xpath) throws XPathExpressionException {
		XPathExpression xPathExpression = getXPath().compile(xpath);
		return (Node) xPathExpression.evaluate(reader.getDocument(), XPathConstants.NODE);
	}
	
	private XPath getXPath() {
	    XPathFactory xPathFactory = XPathFactory.newInstance();
	    return xPathFactory.newXPath();	
	}
	
	/**
	 * Create the Configuration element of selected Environments
	 * @param configList
	 * @param envName
	 * @param defaultEnv
	 * @throws PhrescoException
	 */
	private void createConfiguration(SettingsInfo settingsInfo, Element envNode, boolean defaultEnv) throws PhrescoException {
		Element configNode = createConfigElement(settingsInfo);
		envNode.appendChild(configNode);
		getRootElement().appendChild(envNode);
	}

	private Element createConfigElement(SettingsInfo settingsInfo) {
		Element configNode = getDocument().createElement(settingsInfo.getType());
		configNode.setAttribute("name", settingsInfo.getName());
		configNode.setAttribute("desc", settingsInfo.getDescription());
		List<String> appliesTo = settingsInfo.getAppliesTo();
		if (CollectionUtils.isNotEmpty(appliesTo)) {
			String appliesToAsStr = getAppliesToAsStr(appliesTo);
			configNode.setAttribute("appliesTo", appliesToAsStr);
		}
		createProperties(configNode, settingsInfo.getPropertyInfos());
		return configNode;
	}
	
	private String getAppliesToAsStr(List<String> appliesTo) {
		String appliesToStr = "";
		for (String applies : appliesTo) {
			appliesToStr += applies + ",";
		}
		return appliesToStr.substring(0, appliesToStr.length() - 1);
	}
	
	/**
	 * create the properties to the configuration element
	 * @param configNode
	 * @param propertyInfos
	 */
	private void createProperties(Element configNode, List<PropertyInfo> propertyInfos) {
		for (PropertyInfo propertyInfo : propertyInfos) {
			String key = propertyInfo.getKey();
			String value = propertyInfo.getValue();
			Element propNode = getDocument().createElement(key);
			propNode.setTextContent(value);
			configNode.appendChild(propNode);
		}
	}
	
	public void setDefaultEnvironment(String environmentName) throws PhrescoException {
		try {
			NodeList environmentList = getDocument().getElementsByTagName("environment");
			for (int i = 0; i < environmentList.getLength(); i++) {
				Element environment = (Element) environmentList.item(i);
				String envName = environment.getAttribute("name");
				String[] envs = environmentName.split(",");
				for (String envsName : envs) {
					if (envsName.equals(envName)) {
						environment.setAttribute("default", "true");
					} else {
						environment.setAttribute("default", "false");
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
}
