package com.photon.phresco.tools.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public final class ConfigurationFactory {

	//envname, env dom element
	private static final Map<String, Element> ENV_MAP = new HashMap<String, Element>();
	
	private static ConfigurationFactory FACTORY = null;
	
	private static String defaultEnvironment = null;
	
	private ConfigurationFactory(File configXML) {
		initXML(configXML);
	}
	
	private void initXML(File configXML) {
		InputStream xmlStream = null;
		try {
			if (configXML == null) {
				xmlStream = this.getClass().getClassLoader().getResourceAsStream("config.xml");
			} else {
				xmlStream = new FileInputStream(configXML);
			}
			parseXML(xmlStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				xmlStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void parseXML(InputStream xmlStream) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(xmlStream);
			parseDocument(document);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseDocument(Document document) {
		//get the root element
		Element environments = document.getRootElement();

		//get a nodelist of elements
		List environmentList = environments.getChildren("environment");
		for(int i = 0 ; i < environmentList.size();i++) {

			//get the environment element
			Element environment = (Element) environmentList.get(i);
			String envName = environment.getAttributeValue("name");
			
			String attribute = environment.getAttributeValue("default");
			if (attribute.equalsIgnoreCase("true")) {
				defaultEnvironment = envName;
			}

			//add environment element to map
			ENV_MAP.put(envName, environment);
		}
	}

	public static final ConfigurationFactory getInstance(File configXML) {
		if (FACTORY == null) {
			FACTORY = new ConfigurationFactory(configXML);
		}
		return FACTORY;
	}
	
	private Element getEnvironment(String envName) {
		return ENV_MAP.get(envName);
	}
	
	private List<Configuration> getConfigByEnv(String envName) {
		List<Configuration> configurations = new ArrayList<Configuration>();
		Element environment = getEnvironment(envName);
		List configList = environment.getChildren();
		for (int i = 0; i < configList.size(); i++) {
			Element configNode = (Element) configList.get(i);
			String configType = configNode.getName();
			String configName = configNode.getAttributeValue("name");
			Properties properties = getProperties(configNode);
			Configuration config = new Configuration(configName, configType, properties);
			configurations.add(config);
		}
		return configurations;
	}
	
	private List<Configuration> getConfigByType(String envName, String configType) {
		List<Configuration> configByTypeList = new ArrayList<Configuration>();
		List<Configuration> configList = getConfigByEnv(envName);
		for (Configuration configuration : configList) {
			if (configuration.getType().equals(configType)) {
				configByTypeList.add(configuration);
			}
		}
		return configByTypeList;
	}
	
	private List<Configuration> getConfigByName(String envName, String configType, String configName) {
		List<Configuration> configByNameList = new ArrayList<Configuration>();
		List<Configuration> configByTypeList = getConfigByType(envName, configType);
		for (Configuration configuration : configByTypeList) {
			if (configuration.getName().equals(configName)) {
				configByNameList.add(configuration);
			}
		}
		return configByNameList;
	}
	
	public List<Configuration> getConfiguration(String envName, String configType, String configName) {
		if (StringUtils.isNotEmpty(envName) && StringUtils.isNotEmpty(configType) &&
				StringUtils.isNotEmpty(configName)) {
			return getConfigByName(envName, configType, configName);
			
		} else if (StringUtils.isNotEmpty(envName) && StringUtils.isNotEmpty(configType) &&
				StringUtils.isEmpty(configName)) {
			return getConfigByType(envName, configType);
			
		} else if (StringUtils.isNotEmpty(envName) && StringUtils.isEmpty(configType) &&
				StringUtils.isEmpty(configName)) {
			return getConfigByEnv(envName);
			
		} else {
			return null; //if all the argument is null or empty then return null
		}
	}
	
	private Properties getProperties(Element configNode) {
		Properties props = new Properties();
		List propNodes = configNode.getChildren();
		for(int i = 0 ; i < propNodes.size(); i++) {
			//get the environment element
			Element propNode = (Element) propNodes.get(i);
			String propName = propNode.getName();
			String propValue = getTextValue(propNode);

			props.put(propName, propValue);
		}
		
		return props;
	}
	
	private String getTextValue(Element ele) {
		return ele.getValue();
	}
	
	public List<Configuration> getDefaultEnv() {
		return getConfigByEnv(defaultEnvironment);
	}
	
	public String getDefaultEnvName() {
		return defaultEnvironment;
	}
	
	public void saveXml(Document doc, File sourceConfigXML) {
		try {
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(sourceConfigXML));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
