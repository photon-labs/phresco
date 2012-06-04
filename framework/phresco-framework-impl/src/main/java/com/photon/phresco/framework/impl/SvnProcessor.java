package com.photon.phresco.framework.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class SvnProcessor {
    private static final Logger S_LOGGER = Logger.getLogger(SvnProcessor.class);
    private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
    private Document document_ = null;
    private Element root_ = null;
    
    private static final String SVN_CONFIG = "http://172.16.18.174:9090/nexus/service/local/repositories/dev-binaries/content/config/ci/credential/1.0/credential-1.0.xml";
    private static String FINAL_PATH = "C:\\download\\workspace\\tools\\jenkins\\data\\jobs\\jenkinsdemo";
    
    public SvnProcessor(URL credentialUrl) throws JDOMException, IOException {
    	if (debugEnabled) {
    		S_LOGGER.debug("SvnProcessor constructor : " + credentialUrl);
    	}
        SAXBuilder builder = new SAXBuilder();       
        document_ = builder.build(credentialUrl);
        root_ = document_.getRootElement();
    }
    
    public SvnProcessor(InputStream credentialXml) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();       
        document_ = builder.build(credentialXml);
        root_ = document_.getRootElement();
    }
    
    public SvnProcessor(File csvFile) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        if (debugEnabled) {
           S_LOGGER.debug("SvnProcessor constructor : " + csvFile);
        }
        
        document_ = builder.build(csvFile);
        root_ = document_.getRootElement();
    }
    
    public void changeNodeValue(String nodePath, String nodeValue) throws JDOMException {
        if (debugEnabled) {
           S_LOGGER.debug("Entering Method ConfigProcessor.changeNodeValue: " + nodePath + ", " + nodeValue);
        }
        
        XPath xpath = XPath.newInstance(nodePath);
        xpath.addNamespace(root_.getNamespace());
        Element scmNode = (Element) xpath.selectSingleNode(root_);
        scmNode.setText(nodeValue);
    }
    
    public InputStream getConfigAsStream() throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xmlOutput.output(document_, outputStream);
        
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    public void writeStream(File path) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        xmlOutput.output(document_, writer);
    }
    
    public static void main(String[] args) {
        try {
            SvnProcessor processor = new SvnProcessor(new URL(SVN_CONFIG));
            processor.changeNodeValue("credentials/entry//userName", "Bharat");
            processor.changeNodeValue("credentials/entry//password", "dummy");
            processor.writeStream(new File(FINAL_PATH + File.separator + "hudson.scm.SubversionSCM.xml"));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
