/*
 * ###
 * Framework Web Archive
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
package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.filter.FileListFilter;
import com.photon.phresco.framework.model.PerformanceTestResult;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.TechnologyTypes;

public class QualityUtil {

	static String host = null;
	static String port = null;
	static String protocol = null;
	static String serverContext = null;

	private static String configFileName = "/tests/phresco-env-config.csv";
	private static String buildFileName = "/build.xml";

    private static final Logger S_LOGGER = Logger.getLogger(QualityUtil.class);
    private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();

	public static void adaptTestConfig(String testDirPath, SettingsInfo serverSettings) throws PhrescoException {
		FileWriter out = null;
		try {
			File configFile = new File(testDirPath + configFileName);
			if (!configFile.exists()) {
				return;
			}
			out = new FileWriter(configFile);

			getSettingInfo(serverSettings);

			out.write(host + FrameworkConstants.COMMA);
			out.write(port + FrameworkConstants.COMMA);
			out.write(protocol + FrameworkConstants.COMMA);
			out.write(serverContext);
			out.flush();
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	public static void getSettingInfo(SettingsInfo serverSettings) {
		String type = serverSettings.getType();
		if (type.equals("Server")) {
			host = serverSettings.getPropertyInfo(Constants.SERVER_HOST).getValue();
			port = serverSettings.getPropertyInfo(Constants.SERVER_PORT).getValue();
			protocol = serverSettings.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
			serverContext = serverSettings.getPropertyInfo(Constants.SERVER_CONTEXT).getValue();
		}

		if (type.equals("WebService")) {
			host = serverSettings.getPropertyInfo(Constants.WEB_SERVICE_HOST).getValue();
			port = serverSettings.getPropertyInfo(Constants.WEB_SERVICE_PORT).getValue();
			protocol = serverSettings.getPropertyInfo(Constants.WEB_SERVICE_PROTOCOL).getValue();
			serverContext = serverSettings.getPropertyInfo(Constants.WEB_SERVICE_CONTEXT).getValue();
		}

		if (type.equals("Database")) {
			host = serverSettings.getPropertyInfo(Constants.DB_HOST).getValue();
			port = serverSettings.getPropertyInfo(Constants.DB_PORT).getValue();
			protocol = serverSettings.getPropertyInfo(Constants.DB_PROTOCOL).getValue();
			serverContext = "/";
		}

	}

	public static void adaptPerformanceJmx(String jmxFileLocation, List<String> name, List<String> context,
				List<String> contextType, List<String> contextPostData, List<String> encodingType, int noOfUsers,
				int rampUpPeriod, int loopCount, Map<String, String> headersMap) throws Exception {
		File jmxFile = null;
        File jmxDir = new File(jmxFileLocation + "/tests");
        if(jmxDir.isDirectory()){
        	FilenameFilter filter = new FileListFilter("", "jmx");
            File[] jmxFiles = jmxDir.listFiles(filter);
            jmxFile = jmxFiles[0];
        }

		Document document = ApplicationsUtil.getDocument(jmxFile);
		appendThreadProperties(document, noOfUsers, rampUpPeriod, loopCount);
		NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(document, "jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy");
		if (nodelist != null && nodelist.getLength() > 0) {
			NodeList headerManagerNodelist = org.apache.xpath.XPathAPI.selectNodeList(document, "jmeterTestPlan/hashTree/hashTree/hashTree/HeaderManager");
			
			Node hashTree = nodelist.item(0).getParentNode();
			hashTree = removeAllChilds(hashTree);
			hashTree.setTextContent(null);
			
			if (headerManagerNodelist != null && headerManagerNodelist.getLength() > 0) {
				for(int i = 0; i < headerManagerNodelist.getLength(); i++) {
					hashTree.appendChild(headerManagerNodelist.item(i));
				}
				hashTree.appendChild(document.createElement("hashTree"));
			}

			if (MapUtils.isNotEmpty(headersMap)) {
				NodeList headerMngrNodelist = org.apache.xpath.XPathAPI.selectNodeList(document, "jmeterTestPlan/hashTree/hashTree/hashTree/HeaderManager/collectionProp");
				if (headerMngrNodelist != null && headerMngrNodelist.getLength() > 0) {
					createHeaderElementProp(document, headersMap, headerMngrNodelist.item(0));
				} else {
					Node appendHeaderManager = appendHeaderManager(document, headersMap);
					hashTree.appendChild(appendHeaderManager);
					hashTree.appendChild(document.createElement("hashTree"));
				}
			}
			
			for (int j = 0; j < name.size(); j++) {
				Node appendHttpSamplerProxy = appendHttpSamplerProxy(document, hashTree, name.get(j), "${context}/" + context.get(j), contextType.get(j), contextPostData.get(j), encodingType.get(j));
				hashTree.appendChild(appendHttpSamplerProxy);
				hashTree.appendChild(document.createElement("hashTree"));
			}
		}
		saveDocument(jmxFile, document);
	}
	
	public static void adaptDBPerformanceJmx(String jmxFileLocation, List<String> name, String dataSource, List<String> queryType, List<String> query, int noOfUsers, int rampUpPeriod, int loopCount, String dbUrl, String driver, String userName, String passWord ) throws Exception {
		File jmxFile = null;
        File jmxDir = new File(jmxFileLocation + "/tests");
        if(jmxDir.isDirectory()){
        	FilenameFilter filter = new FileListFilter("", "jmx");
            File[] jmxFiles = jmxDir.listFiles(filter);
            jmxFile = jmxFiles[0];
        }

		Document document = ApplicationsUtil.getDocument(jmxFile);
		appendThreadProperties(document, noOfUsers, rampUpPeriod, loopCount);
		appendJdbcDataSrc(document, dataSource, dbUrl, driver, userName, passWord);
		NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(document, "jmeterTestPlan/hashTree/hashTree/hashTree/JDBCSampler");
		if (nodelist != null && nodelist.getLength() > 0) {
			Node hashTree = nodelist.item(0).getParentNode();
			hashTree = removeAllChilds(hashTree);
			hashTree.setTextContent(null);

			for(int j = 0; j < name.size(); j++) {
				Node appendJdbcSampler = appendJdbcSampler(document, hashTree, name.get(j), dataSource, queryType.get(j), query.get(j));
				hashTree.appendChild(appendJdbcSampler);
				hashTree.appendChild(document.createElement("hashTree"));
			}
		}
		saveDocument(jmxFile, document);
	}

	public static void adaptLoadJmx(String jmxFileLocation, int noOfUsers, int rampUpPeriod, int loopCount, Map<String, String> headersMap) throws Exception {
        File jmxFile = null;
        File jmxDir = new File(jmxFileLocation + "/tests");
        if(jmxDir.isDirectory()){
            FilenameFilter filter = new FileListFilter("", "jmx");
            File[] jmxFiles = jmxDir.listFiles(filter);
            jmxFile = jmxFiles[0];
        }
        Document document = ApplicationsUtil.getDocument(jmxFile);
        appendThreadProperties(document, noOfUsers, rampUpPeriod, loopCount);
        if (MapUtils.isNotEmpty(headersMap)) {
        	NodeList hashTree = org.apache.xpath.XPathAPI.selectNodeList(document, "jmeterTestPlan/hashTree/hashTree/hashTree");
			NodeList headerMngrNodelist = org.apache.xpath.XPathAPI.selectNodeList(document, "jmeterTestPlan/hashTree/hashTree/hashTree/HeaderManager/collectionProp");
			if (headerMngrNodelist != null && headerMngrNodelist.getLength() > 0) {
				createHeaderElementProp(document, headersMap, headerMngrNodelist.item(0));
			} else {
				Node appendHeaderManager = appendHeaderManager(document, headersMap);
				hashTree.item(0).appendChild(appendHeaderManager);
				hashTree.item(0).appendChild(document.createElement("hashTree"));
			}
		}
        saveDocument(jmxFile, document);
    }

	private static Node removeAllChilds(Node hashTree) {
		NodeList childNodes = hashTree.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			hashTree.removeChild(childNodes.item(i));
		}
		return hashTree;
	}
	
	private static void appendThreadProperties(Document document, int noOfUsers, int rampUpPeriod, int loopCount) throws Exception {
		String loopNode = "jmeterTestPlan/hashTree/hashTree/ThreadGroup/*/stringProp[@name='LoopController.loops']";
		String threadNode = "jmeterTestPlan/hashTree/hashTree/ThreadGroup/stringProp[@name='ThreadGroup.num_threads']";
		String rampNode = "jmeterTestPlan/hashTree/hashTree/ThreadGroup/stringProp[@name='ThreadGroup.ramp_time']";
		appendTextContent(document, loopNode, ""+loopCount);
		appendTextContent(document, threadNode, ""+noOfUsers);
		appendTextContent(document, rampNode, ""+rampUpPeriod);
	}
	
	private static void appendJdbcDataSrc(Document document, String dataSrc, String dbUrl, String driver,String userName,String passWord) throws Exception {
		String dataSource = "jmeterTestPlan/hashTree/hashTree/JDBCDataSource/stringProp[@name='dataSource']";
		String url = "jmeterTestPlan/hashTree/hashTree/JDBCDataSource/stringProp[@name='dbUrl']";
		String driverName = "jmeterTestPlan/hashTree/hashTree/JDBCDataSource/stringProp[@name='driver']";
		String pwd = "jmeterTestPlan/hashTree/hashTree/JDBCDataSource/stringProp[@name='password']";
		String user = "jmeterTestPlan/hashTree/hashTree/JDBCDataSource/stringProp[@name='username']";
		appendTextContent(document, dataSource, ""+dataSrc);
		appendTextContent(document, url, ""+dbUrl);
		appendTextContent(document, driverName, ""+driver);
		appendTextContent(document, pwd, ""+passWord);
		appendTextContent(document, user, ""+userName);
	}

	private static void appendTextContent(Document document, String element, String textContent) throws Exception {
		NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(document, element);

		for (int i = 0; i < nodelist.getLength(); i++) {
			Node stringProp = nodelist.item(i);
			stringProp.setTextContent(textContent);
		}
	}

	private static Node appendHttpSamplerProxy(Document document, Node hashTree, String name, String context, String contextType, String contextPostData, String encodingType) {
		Node httpSamplerProxy = document.createElement("HTTPSamplerProxy");
		String contentEncoding = null;
		if(contextType.equals(FrameworkConstants.POST)) {
			contentEncoding = encodingType;
		}
		
		NamedNodeMap attributes = httpSamplerProxy.getAttributes();
		attributes.setNamedItem(createAttribute(document, "guiclass", "HttpTestSampleGui"));
		attributes.setNamedItem(createAttribute(document, "testclass", "HTTPSamplerProxy"));
		attributes.setNamedItem(createAttribute(document, "testname", name)); //url name
		attributes.setNamedItem(createAttribute(document, "enabled", "true"));

		appendElementProp(document, httpSamplerProxy, contextType, contextPostData);

		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.domain", null);
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.port", null);
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.connect_timeout", null);
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.response_timeout", null);
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.protocol", null);
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.contentEncoding", contentEncoding);
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.path", context); // server url
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.method", contextType);

		appendTypeProp(document, httpSamplerProxy, "boolProp", "HTTPSampler.follow_redirects", "false");
		appendTypeProp(document, httpSamplerProxy, "boolProp", "HTTPSampler.auto_redirects", "true");
		appendTypeProp(document, httpSamplerProxy, "boolProp", "HTTPSampler.use_keepalive", "true");
		appendTypeProp(document, httpSamplerProxy, "boolProp", "HTTPSampler.DO_MULTIPART_POST", "false");

		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.implementation", "Java");
		appendTypeProp(document, httpSamplerProxy, "boolProp", "HTTPSampler.monitor", "false");
		appendTypeProp(document, httpSamplerProxy, "stringProp", "HTTPSampler.embedded_url_re", null);

		return httpSamplerProxy;
	}
	
	private static Node appendJdbcSampler(Document document, Node hashTree, String name, String dataSource, String queryType, String query) {
		Node jdbcSampler = document.createElement("JDBCSampler");

		NamedNodeMap attributes = jdbcSampler.getAttributes();
		attributes.setNamedItem(createAttribute(document, "guiclass", "TestBeanGUI"));
		attributes.setNamedItem(createAttribute(document, "testclass", "JDBCSampler"));
		attributes.setNamedItem(createAttribute(document, "testname", name)); //url name
		attributes.setNamedItem(createAttribute(document, "enabled", "true"));

		appendTypeProp(document, jdbcSampler, "stringProp", "dataSource", dataSource);
		appendTypeProp(document, jdbcSampler, "stringProp", "queryType", queryType);
		appendTypeProp(document, jdbcSampler, "stringProp", "query", query);
		appendTypeProp(document, jdbcSampler, "stringProp", "queryArguments", null);
		appendTypeProp(document, jdbcSampler, "stringProp", "queryArgumentsTypes", null);
		appendTypeProp(document, jdbcSampler, "stringProp", "variableNames", null);
		appendTypeProp(document, jdbcSampler, "stringProp", "resultVariable", null); 

		return jdbcSampler;
	}

	private static Node appendHeaderManager(Document document, Map<String, String> headersMap) {
		Node headerManager = document.createElement("HeaderManager");
		NamedNodeMap attributes = headerManager.getAttributes();
		attributes.setNamedItem(createAttribute(document, "guiclass", "HeaderPanel"));
		attributes.setNamedItem(createAttribute(document, "testclass", "HeaderManager"));
		attributes.setNamedItem(createAttribute(document, "testname", "HTTP Header Manager"));
		attributes.setNamedItem(createAttribute(document, "enabled", "true"));
		appendHeaderManagerCollectionProp(document, headerManager, headersMap);
		return headerManager;
	}

	private static void appendHeaderManagerCollectionProp(Document document, Node elementProp, Map<String, String> headersMap) {
		Node collectionProp = document.createElement("collectionProp");
		NamedNodeMap attributes = collectionProp.getAttributes();
		attributes.setNamedItem(createAttribute(document, "name", "HeaderManager.headers"));
		createHeaderElementProp(document, headersMap, collectionProp);
		elementProp.setTextContent(null);
		elementProp.appendChild(collectionProp);
	}

	private static void createHeaderElementProp(Document document,
			Map<String, String> headersMap, Node collectionProp) {
		for (Map.Entry<String, String> entry : headersMap.entrySet()) {
			Node subElementProp = document.createElement("elementProp");
			NamedNodeMap subElementAttributes = subElementProp.getAttributes();
			subElementAttributes.setNamedItem(createAttribute(document, "name", ""));
			subElementAttributes.setNamedItem(createAttribute(document, "elementType", "Header"));
			collectionProp.appendChild(subElementProp);
			appendTypeProp(document, subElementProp, "stringProp", "Header.name", entry.getKey());
			appendTypeProp(document, subElementProp, "stringProp", "Header.value", entry.getValue());
		}
	}
	
	private static void appendElementProp(Document document, Node parentNode, String contextType, String contextPostData) { // eleme prop
		Node elementProp = document.createElement("elementProp");
		NamedNodeMap attributes = elementProp.getAttributes();

		attributes.setNamedItem(createAttribute(document, "name", "HTTPsampler.Arguments"));
		attributes.setNamedItem(createAttribute(document, "elementType", "Arguments"));
		attributes.setNamedItem(createAttribute(document, "guiclass", "HTTPArgumentsPanel"));
		attributes.setNamedItem(createAttribute(document, "testclass", "Arguments"));
		attributes.setNamedItem(createAttribute(document, "testname", "User Defined Variables"));
		attributes.setNamedItem(createAttribute(document, "enabled", "true"));
		appendCollectionProp(document, elementProp, contextType, contextPostData);

		//parentNode.setTextContent(null);
		parentNode.appendChild(elementProp);
	}
	
	private static void appendCollectionProp(Document document, Node elementProp, String contextType, String contextPostData) { // collection append in prop
		String argumentValue = null;
		if(contextType.equals(FrameworkConstants.POST)) {
			argumentValue = contextPostData;
		}
		Node collectionProp = document.createElement("collectionProp");
		NamedNodeMap attributes = collectionProp.getAttributes();
		attributes.setNamedItem(createAttribute(document, "name", "Arguments.arguments"));

		Node subElementProp = document.createElement("elementProp");
		NamedNodeMap subElementAttributes = subElementProp.getAttributes();
		subElementAttributes.setNamedItem(createAttribute(document, "name", ""));
		subElementAttributes.setNamedItem(createAttribute(document, "elementType", "HTTPArgument"));
		collectionProp.appendChild(subElementProp);
		appendTypeProp(document, subElementProp, "boolProp", "HTTPArgument.always_encode", "false");
		appendTypeProp(document, subElementProp, "stringProp", "Argument.value", argumentValue);
		appendTypeProp(document, subElementProp, "stringProp", "Argument.metadata", "=");
		appendTypeProp(document, subElementProp, "boolProp", "HTTPArgument.use_equals", "true");

		elementProp.setTextContent(null);
		elementProp.appendChild(collectionProp);
	}

	private static void appendTypeProp(Document document, Node parentProp, String tag, String nameAttr, String textContent) {
		Node typeProp = document.createElement(tag);
		NamedNodeMap attributes = typeProp.getAttributes();
		attributes.setNamedItem(createAttribute(document, "name", nameAttr));
		typeProp.setTextContent(textContent);
		parentProp.appendChild(typeProp);
	}

	private static Attr createAttribute(Document document, String attrName, String attrValue) {
		Attr attr = document.createAttribute(attrName);
		attr.setValue(attrValue);
		return attr;
	}

	private static void saveDocument(File file, Document doc) throws Exception {

		TransformerFactory factory1 = TransformerFactory.newInstance();
		Transformer transformer = factory1.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		String content = writer.toString();
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(content);
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	public static Map<String, String> getDeviceNames(Document document)  throws Exception {
		NodeList nodeList = org.apache.xpath.XPathAPI.selectNodeList(document, "/*/*");
		Map<String, String> deviceList = new LinkedHashMap<String, String>(100);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NamedNodeMap nameNodeMap = node.getAttributes();
			String deviceId = "";
			String deviceName = "";
			Node idAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_ID);
			deviceId = idAttr.getNodeValue();
			Node nameAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_NAME);
			deviceName = nameAttr.getNodeValue();
			deviceList.put(deviceId, deviceName);
		}
		return deviceList;
	}

	public static Map<String, PerformanceTestResult> getPerformanceReport(Document document, HttpServletRequest request, String techId, String deviceId) throws Exception {  // deviceid is the tag name for android
//		Document document = getDocument(new File(filePath));
		String xpath = "/*/*";	// For other technologies
		String device = "*";
		if(StringUtils.isNotEmpty(deviceId)) {
			device = "deviceInfo[@id='" + deviceId + "']";
		}
		if(TechnologyTypes.ANDROIDS.contains(techId)) {
			xpath = "/*/" + device + "/*";
		}
		NodeList nodeList = org.apache.xpath.XPathAPI.selectNodeList(document, xpath);
		Map<String, PerformanceTestResult> results = new LinkedHashMap<String, PerformanceTestResult>(100);
		double maxTs = 0;
		double minTs = 0;
		int lastTime = 0;
		int noOfSamples = nodeList.getLength();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NamedNodeMap nameNodeMap = node.getAttributes();
			Node timeAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_TIME);
			int time = Integer.parseInt(timeAttr.getNodeValue());
			Node bytesAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_BYTES);
			int bytes = Integer.parseInt(bytesAttr.getNodeValue());
			Node successFlagAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_SUCCESS_FLAG);
			boolean success = Boolean.parseBoolean(successFlagAttr.getNodeValue()) ? true : false ;
			Node labelAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_LABEL);
			String label = labelAttr.getNodeValue();
			Node timeStampAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_TIMESTAMP);
			double timeStamp = Long.parseLong(timeStampAttr.getNodeValue());
			boolean firstEntry = false;

			PerformanceTestResult performanceTestResult = results.get(label);
			if (performanceTestResult == null) {
				performanceTestResult = new PerformanceTestResult();
				firstEntry = true;
			} else {
				firstEntry = false;
			}

			performanceTestResult.setLabel(label.trim());
			performanceTestResult.setNoOfSamples(performanceTestResult.getNoOfSamples() + 1);
			performanceTestResult.getTimes().add(time);
			performanceTestResult.setTotalTime(performanceTestResult.getTotalTime() + time);
			performanceTestResult.setTotalBytes(performanceTestResult.getTotalBytes() + bytes);

			if (time < performanceTestResult.getMin() || firstEntry) {
				performanceTestResult.setMin(time);
			}

			if (time > performanceTestResult.getMax()) {
				performanceTestResult.setMax(time);
			}

			// Error calculation
			if (!success) {
				performanceTestResult.setErr(performanceTestResult.getErr() + 1);
			}

			// Throughput calculation

			if (timeStamp >= performanceTestResult.getMaxTs()) {
				performanceTestResult.setMaxTs(timeStamp);
				performanceTestResult.setLastTime(time);
			}

			if(i == 0 || (performanceTestResult.getMaxTs() > maxTs)) {
				maxTs = performanceTestResult.getMaxTs();
				lastTime = performanceTestResult.getLastTime();
			}

			if (timeStamp < performanceTestResult.getMinTs() || firstEntry) {
				performanceTestResult.setMinTs(timeStamp);
			}

			if(i == 0 ) {
				minTs = performanceTestResult.getMinTs();
			} else if(performanceTestResult.getMinTs() < minTs) {
				minTs = performanceTestResult.getMinTs();
			}

			Double calThroughPut = new Double(performanceTestResult.getNoOfSamples());
			calThroughPut = calThroughPut / (performanceTestResult.getMaxTs() + performanceTestResult.getLastTime() -
					performanceTestResult.getMinTs());
			double throughPut = calThroughPut * 1000;

			performanceTestResult.setThroughtPut(throughPut);

			results.put(label, performanceTestResult);
		}
		// Total Throughput calculation
		double totalThroughput = (noOfSamples /((maxTs + lastTime) - minTs)) * 1000;
		request.setAttribute(FrameworkConstants.REQ_TOTAL_THROUGHPUT, totalThroughput);
		setStdDevToResults(results, request);
		return results;
	}

	private static void setStdDevToResults(Map<String, PerformanceTestResult> results,  HttpServletRequest request) {
		Set<String> keySet = results.keySet();
		long xBar = 0;  		//XBar Calculation
		long sumOfTime = 0;
		int totalSamples = 0;
		double sumMean = 0;
		List<Integer> allTimes = new ArrayList<Integer>();
		for (String key : keySet) {
			PerformanceTestResult performanceTestResult = results.get(key);
			// calculation of average time
			double avg = performanceTestResult.getTotalTime() / performanceTestResult.getNoOfSamples();
			sumOfTime = sumOfTime + performanceTestResult.getTotalTime();
			totalSamples = totalSamples + performanceTestResult.getNoOfSamples();
			performanceTestResult.setAvg(avg);

			// calculation of average bytes
			double avgBytes = (double) performanceTestResult.getTotalBytes() / performanceTestResult.getNoOfSamples();
			performanceTestResult.setAvgBytes(Math.round(avgBytes));
			// KB/Sec calculation
			Double calKbPerSec = new Double(performanceTestResult.getThroughtPut());
			calKbPerSec = calKbPerSec * (((double) avgBytes) / 1024)   ;
			performanceTestResult.setKbPerSec(calKbPerSec);

			// Std.Dev calculation
			List<Integer> times = performanceTestResult.getTimes();
			allTimes.addAll(times);
			Double totalMean = new Double(0);
			for (Integer time : times) {
				totalMean += Math.pow(time - avg, 2);
			}
			performanceTestResult.setStdDev((float) Math.sqrt(totalMean / performanceTestResult.getNoOfSamples()));

			performanceTestResult.setErr((performanceTestResult.getErr() / performanceTestResult.getNoOfSamples()) * 100);
		}

		//Total Std.Dev calculation
		xBar = sumOfTime / totalSamples;
		for (Integer time : allTimes) {
			sumMean += Math.pow(time - xBar, 2);
		}
		double stdDev = Math.sqrt(sumMean / totalSamples);
		request.setAttribute(FrameworkConstants.REQ_TOTAL_STD_DEV, stdDev);
	}

	public static void changeTestName(String performancePath, String testName) throws Exception {
		File buildPathXml = null;
            S_LOGGER.debug("Entering Method QualityUtil.testSuite() performance path and TestName " + performancePath + " " + testName);
		buildPathXml = new File(performancePath + buildFileName);
		Document document = ApplicationsUtil.getDocument(buildPathXml);
		String fileNameNode = "project/target[@name='init']/property[@name='jmeter.result.file']";
		NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(document, fileNameNode);
		if (nodelist != null && nodelist.getLength() > 0) {
			Node stringProp = nodelist.item(0);
			NamedNodeMap attributes = stringProp.getAttributes();
			Node valueAttr = attributes.getNamedItem("value");
			String valueAttrTxt = valueAttr.getTextContent();
				S_LOGGER.debug("Load test xml name " + valueAttrTxt.substring(0, valueAttrTxt.indexOf("/") + 1).concat(testName + ".xml"));
			valueAttr.setTextContent(valueAttrTxt.substring(0, valueAttrTxt.indexOf("/") + 1).concat(testName + ".xml"));
		}
		saveDocument(buildPathXml, document);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortResultFile(File[] children){
            S_LOGGER.debug("Entering Method QualityUtil.sortResultFile() for quality tab" + children);
		Arrays.sort( children, new Comparator() {
        	public int compare(Object o1, Object o2) {
            	if (((File)o1).lastModified() > ((File)o2).lastModified()) {
            		return -1;
            	} else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
            		return +1;
            	} else {
            		return 0;
            	}
        	}
        });
	}

	public ArrayList<String> getConnAndroidDevices(String command) throws Exception {
		S_LOGGER.debug("Entering Method QualityUtil.getConnAndroidDevices() for quality tab");
		ArrayList<String> output = new ArrayList<String>();
		try {
			String s = null;
			Process p = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			int cnt = 0;
			while ((s = stdInput.readLine()) != null) {
				if (cnt > 0) {
					s = s.substring(0, s.indexOf("\t") + 1);
					output.add(s.trim());
				}
				cnt++;
			}
			stdInput.close();
			cnt = 0;
			while ((s = stdError.readLine()) != null) {
				if (cnt > 0) {
					s = s.substring(0, s.indexOf("\t") + 1);
					output.add(s.trim());
				}
				cnt++;
			}
			stdError.close();
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Quality.getConnAndroidDevices()"+ FrameworkUtil.getStackTraceAsString(e));
		}
		output.remove(output.size() - 1);
		return output;
	}

	}

