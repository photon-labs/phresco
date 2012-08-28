package com.photon.phresco.framework.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.CIJob;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.api.ServiceManager;

public class CIManagerImplTest implements FrameworkConstants{

	private ServiceManager serviceManager = null;
	private CIManagerImpl ciManager = null;
	final String resourceName = "gitHubScm.xml";
	private Document document_ = null;
    private Element root_ = null;
    private String SvnType = "git"; //clonedWorkspace
    
	@Before
	public void setUp() throws Exception {
		try {
			System.out.println("before1111111111111111 !!!!!!!!!");
//			serviceManager = PhrescoFrameworkFactory.getServiceManager();
			ciManager = new CIManagerImpl();
			System.out.println("Initialized!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Tear down!!!!!");
//	    if (serviceManager != null) {
//	        serviceManager = null;
//	    }
	}

	//@Test
	public void getConfigPath() throws IOException {
		InputStream inStream = null;
		OutputStream ps = null;
		try {
//            String configPath = serviceManager.getCiConfigPath(SvnType);
			String configPath = "http://172.16.18.178:8080/nexus/content/groups/public/config/ci/git/config/0.3/config-0.3.xml";
            URL configUrl = new URL(configPath);
            // print the file
//            BufferedReader in = new BufferedReader(
//            new InputStreamReader(configUrl.openStream()));

//            String inputLine;
//            while ((inputLine = in.readLine()) != null)
//                System.out.println(inputLine);
//            in.close();
            
            
            System.out.println("configPath =======> " + configPath);
            ConfigProcessor processor = new ConfigProcessor(new URL(configPath));
            CIJob job = createJob();
            CIJob job1 =  updateWithCollabNetFileRelease(job);
            
            CIJob job2 = updateWithClonnedWorkspace(job1);
            
            CIJob job3 = updateWithCloneTheWorkspace(job2);
            
            CIJob job4 = updateWithBuildOtherProjects(job3);
            // Operation
            ciManager.customizeNodes(processor, job4);
            //success
//            processor.deleteNodesAtXpath();
//            processor.changeAttributeValue();
//            processor.insertNodesAtXpath();
            
            //Conver to file
            File dest = new File("/Users/kaleeswaran/Desktop/IphoneConfig.xml");
            InputStream configAsStream = processor.getConfigAsStream();
            ciManager.streamToFile(dest, configAsStream) ;
            System.out.println("configPath =======> " + configPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				inStream.close();
			}

			if (ps != null) {
				ps.close();
			}
		}
	}

	
	public void generateXml() {
		String filePath = "http://172.16.18.178:8080/nexus/content/groups/public/config/ci/git/config/0.3/config-0.3.xml";
//		testXmlGeneration(filePath);
	}
	
	//@Test
	public void testXmlGeneration() {
        try {
        	System.out.println("kkkkkkkkkkkkkk");
        	String url = "http://172.16.18.178:8080/nexus/content/groups/public/config/ci/git/config/0.3/config-0.3.xml";
        	SAXBuilder builder = new SAXBuilder();
			document_ = builder.build(url);
			root_ = document_.getRootElement();
			System.out.println("test pomxml updation ............");
//			org.jdom.Element element = new Element("hudson.plugins.collabnet.filerelease.CNFileRelease");
//			element.addContent(createElement("override__auth", "true"));
//			element.addContent(createElement("url", "http://outside.out.com:8080/ce-soap50/services/CollabNet?wsdl"));
//			element.addContent(createElement("username", "kaleeswaran14"));
//			element.addContent(createElement("password", "U3VyZXNoQDEyMw=="));
//			element.addContent(createElement("project", "project"));
//			element.addContent(createElement("rpackage", "PackageRed"));
//			element.addContent(createElement("release", "release"));
//			element.addContent(createElement("overwrite", "false"));
//			element.addContent(createElement("file__patterns", null).addContent(createElement("hudson.plugins.collabnet.documentuploader.FilePattern", "do_not_checkin/build/*.zip")));
//			
//	    	XPath xpath = XPath.newInstance("publishers");
			
			org.jdom.Element element = new Element("rootPOM").addContent("kaleesPom.xml");
//			XPath xpath = XPath.newInstance("maven2-moduleset");
//	        xpath.addNamespace(root_.getNamespace());
//	        Element pullisherNode = (Element) xpath.selectSingleNode(root_);
//	        System.out.println("pullisherNode =====> " + pullisherNode);
	        root_.addContent(element); 
	        
	        
			File dest = new File("/Users/kaleeswaran/Desktop/gitHubConfig.xml");
            InputStream configAsStream = getConfigAsStream();
            ciManager.streamToFile(dest, configAsStream) ;
            System.out.println("new value added =======> " + url);
		} catch (Exception e) {
			e.printStackTrace();
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
    
	private CIJob createJob() {
		CIJob job = new CIJob();
		job.setSvnUrl("KaleesUrl");
		job.setScheduleExpression("@@@@@@@@@@@@");
		List<String> triggers = new ArrayList<String>();
		triggers.add(TIMER_TRIGGER);
		triggers.add("kalees_triggers");
		job.setTriggers(triggers);
		job.setMvnCommand("mvn kalees:kalees");
		Map<String, String> emails = new HashMap<String, String>(2);
		emails.put(REQ_KEY_SUCCESS_EMAILS, "muthu success!!!");
		emails.put(REQ_KEY_FAILURE_EMAILS, "muthu failure!!!");
		job.setEmail(emails);
		// need to pass as clonned workspace
		job.setRepoType(SvnType);
		job.setBranch("kalees-boston");
		return job;
	}

	private CIJob updateWithCollabNetFileRelease(CIJob job) {
		job.setEnableBuildRelease(true);
		job.setCollabNetURL("http://CollabNet Url");
		job.setCollabNetusername("CollabNet username");
		job.setCollabNetpassword("U3VyZXNoQDEyMw==");
		job.setCollabNetProject("CollabNet project");
		job.setCollabNetPackage("CollabNet package!!!!");
		job.setCollabNetRelease("CollabNet File Release");
		boolean overwriteFiles = false;
		job.setCollabNetoverWriteFiles(overwriteFiles);
		return job;
	}
	
	// use clonned scm
	private CIJob updateWithClonnedWorkspace(CIJob job) {
		job.setUsedClonnedWorkspace("ClonnedJobkalees");
		return job;
	}
	
	private CIJob updateWithCloneTheWorkspace(CIJob job) {
		job.setCloneWorkspace(true);
		return job;
	}
	
	private CIJob updateWithBuildOtherProjects(CIJob job) {
		job.setDownStreamProject("kalees_DownstreamProject");
		return job;
	}
}
