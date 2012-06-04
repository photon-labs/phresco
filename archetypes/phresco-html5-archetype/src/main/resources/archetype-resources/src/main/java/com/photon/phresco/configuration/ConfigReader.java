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
	private static ConfigReader READER = null;
	private static String defaultEnvironment = null;
	
	public static ConfigReader getInstance(File configXML) throws Exception {
		if (READER == null) {
			READER = new ConfigReader(new FileInputStream(configXML));
		}
		return READER;
	}
	
	public static ConfigReader getInstance(InputStream xmlStream) throws Exception {
		if (READER == null) {
			READER = new ConfigReader(xmlStream);
		}
		return READER;
	}

	public String getDefaultEnvName() {
		return defaultEnvironment;
	}
	
	public List<Configuration> getConfigByEnv(String envName) {
		List<Configuration> configurations = new ArrayList<Configuration>();
		Element environment = getEnvironment(envName);
		NodeList configNodes = environment.getChildNodes();
		for (int i = 0; i < configNodes.getLength(); i++) {
			if (configNodes.item(i).getNodeType() !=  Element.TEXT_NODE) {
				Element configNode = (Element) configNodes.item(i);
				String configType = configNode.getNodeName();
				String configName = configNode.getAttribute("name");
				String configDesc = configNode.getAttribute("desc");
				String configAppliesTo = configNode.getAttribute("appliesto");
				Properties properties = getProperties(configNode);
				Configuration config = new Configuration(configName, configDesc, configType, properties, configAppliesTo);
				configurations.add(config);
			}
		}
		return configurations;
	}
	
	/*public List<Configuration> getDefaultEnv() {
		return getConfigByEnv(defaultEnvironment);
	}*/
	
	protected ConfigReader(InputStream xmlStream) throws Exception {
		initXML(xmlStream);
	}
	
	private void initXML(InputStream xmlStream) throws Exception {
		try {
			if (xmlStream == null) {
				xmlStream = this.getClass().getClassLoader().getResourceAsStream("config.xml");
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
	
	private void parseXML(InputStream xmlStream) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document document = builder.parse(xmlStream);
		parseDocument(document);
	}
	
	private void parseDocument(Document document) {
		//get a nodelist of environments
		NodeList environmentList = document.getElementsByTagName("environment");

		for(int i = 0 ; i < environmentList.getLength(); i++) {

			//get the environment element
			Element environment = (Element) environmentList.item(i);
			String envName = environment.getAttribute("name");
			//add environment element to map
			ENV_MAP.put(envName, environment);
		}
	}
	
	protected Map<String, Element> getEnviroments() {
		return ENV_MAP;
	}
	
	protected Element getEnvironment(String envName) {
		return ENV_MAP.get(envName);
	}
	
	protected Properties getProperties(Element configNode) {
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

}
