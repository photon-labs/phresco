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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.xml.sax.InputSource;

import com.photon.phresco.commons.FrameworkConstants;

public class ConfigProcessor implements FrameworkConstants {
	private static final Logger S_LOGGER = Logger.getLogger(ConfigProcessor.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	private Document document_ = null;
    private Element root_ = null;
    
    public static final String CONFIG_PATH = "http://172.16.18.178:8080/nexus/content/groups/public//config/ci/config/0.2/config-0.2.xml";
    
    public ConfigProcessor(URL url) throws JDOMException, IOException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.ConfigProcessor(URL url)");
		}
        SAXBuilder builder = new SAXBuilder();
        if (DebugEnabled) {
        	S_LOGGER.debug("ConfigProcessor(() URL = "+ url);
		}
        document_ = builder.build(url);
        root_ = document_.getRootElement();
    }
    
    public void changeNodeValue(String nodePath, String nodeValue) throws JDOMException {
    	if (DebugEnabled) {
    		S_LOGGER.debug("Entering Method ConfigProcessor.changeNodeValue(String nodePath, String nodeValue)");
		}
    	if (DebugEnabled) {
    		S_LOGGER.debug("changeNodeValue() NodePath ="+ nodePath);
		}
    	XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        
        Element scmNode = (Element) xpath.selectSingleNode(root_);
        if (DebugEnabled) {
        	S_LOGGER.debug("changeNodeValue() NodeValue ="+ nodeValue);
		}
        scmNode.setText(nodeValue);
    }
    
    public void createTriggers(String nodePath, List<String> triggers, String cronExpression) throws JDOMException {
    	XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        Element triggerNode = (Element) xpath.selectSingleNode(root_);
        triggerNode.removeContent();
    	for (String trigger : triggers) {
			if(TIMER_TRIGGER.equals(trigger)) {
				triggerNode.addContent(createElement("hudson.triggers.TimerTrigger", null).addContent(createElement("spec", cronExpression)));
			} else {
				triggerNode.addContent(createElement("hudson.triggers.SCMTrigger", null).addContent(createElement("spec", cronExpression)));
			}
		}
    }
    
    public static Element createElement(String nodeName, String NodeValue) {
    	org.jdom.Element element = new Element(nodeName);
    	if (NodeValue != null) {
    		element.addContent(NodeValue);
    	}
    	return element;
    }
    
    public InputStream getConfigAsStream() throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xmlOutput.output(document_, outputStream);
        
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    public static void main(String[] args) {
        try {
            ConfigProcessor processor = new ConfigProcessor(new URL(CONFIG_PATH));
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
