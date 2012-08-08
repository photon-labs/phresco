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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URL;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import com.photon.phresco.commons.CIJob;
import com.photon.phresco.commons.CIPasswordScrambler;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.trilead.ssh2.crypto.Base64;

public class ConfigProcessor implements FrameworkConstants {
	private static final Logger S_LOGGER = Logger.getLogger(ConfigProcessor.class);
	private static Boolean DebugEnabled = S_LOGGER.isDebugEnabled();
	private Document document_ = null;
    private Element root_ = null;
    
    public static final String CONFIG_PATH = "http://172.16.18.174:9090/nexus/service/local/repositories/dev-binaries/config/ci/config/0.1/config-0.1.xml";
    
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
    
    public void enableCollabNetBuildReleasePlugin(CIJob job) throws PhrescoException {
    	try {
			org.jdom.Element element = new Element(CI_FILE_RELEASE_NODE);
			element.addContent(createElement(CI_FILE_RELEASE_OVERRIDE_AUTH_NODE, TRUE));
			element.addContent(createElement(CI_FILE_RELEASE_URL, job.getCollabNetURL()));
			element.addContent(createElement(CI_FILE_RELEASE_USERNAME, job.getCollabNetusername()));
			element.addContent(createElement(CI_FILE_RELEASE_PASSWORD, encyPassword(CIPasswordScrambler.unmask(job.getCollabNetpassword()))));
			element.addContent(createElement(CI_FILE_RELEASE_PROJECT, job.getCollabNetProject()));
			element.addContent(createElement(CI_FILE_RELEASE_PACKAGE, job.getCollabNetPackage()));
			element.addContent(createElement(CI_FILE_RELEASE_RELEASE, job.getCollabNetRelease()));
			element.addContent(createElement(CI_FILE_RELEASE_OVERWRITE, job.isCollabNetoverWriteFiles()+""));
			element.addContent(createElement(CI_FILE_RELEASE_FILE_PATTERN, null).addContent(createElement(CI_FILE_RELEASE_FILE_PATTERN_NODE, CI_BUILD_EXT)));
			
	    	XPath xpath = XPath.newInstance(CI_FILE_RELEASE_PUBLISHER_NODE);
	        xpath.addNamespace(root_.getNamespace());
	        Element pullisherNode = (Element) xpath.selectSingleNode(root_);
	        pullisherNode.getContent().add(3, element);
    	} catch (Exception e) {
			throw new PhrescoException(e);
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
    
	public String encyPassword(String password) throws PhrescoException {
		String encString = "";
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, getAes128Key(CI_SECRET_KEY));
			encString = new String(Base64.encode(cipher.doFinal((password+CI_ENCRYPT_MAGIC).getBytes(CI_UTF8))));
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return encString;
	}
	
	private static SecretKey getAes128Key(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_ALGO);
            digest.reset();
            digest.update(s.getBytes(CI_UTF8));
            return new SecretKeySpec(digest.digest(),0,128/8, AES_ALGO);
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
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
