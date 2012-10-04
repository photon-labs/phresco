/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.ConfigWriter;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;

public class PluginUtils implements PluginConstants {
	
	public void executeUtil(String environmentType, String basedir, File sourceConfigXML) {
		try {
			File currentDirectory = new File(".");
			File configXML = new File(currentDirectory + File.separator + 
			PluginConstants.DOT_PHRESCO_FOLDER + File.separator + PluginConstants.CONFIG_FILE);
			File settingsXML = new File(Utility.getProjectHome() + PluginConstants.SETTINGS_FILE);
			
			ConfigReader reader = new ConfigReader(configXML);
			ConfigWriter writer = new ConfigWriter(reader, true);
			writer.saveXml(sourceConfigXML, environmentType);
			if (settingsXML.exists()) {
				ConfigReader srcReaderToAppend = new ConfigReader(sourceConfigXML);
				
				ConfigReader globalReader = new ConfigReader(settingsXML);
				ConfigWriter globalWriter = new ConfigWriter(globalReader, true);
				globalWriter.saveXml(srcReaderToAppend, environmentType);
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}
	

	public List<String> csvToList(String environmentName) {
		List<String> envs = new ArrayList<String>();
		if (StringUtils.isNotEmpty(environmentName)) {
			String[] temp = environmentName.split(",");
			for (int i = 0; i < temp.length; i++) {
				envs.add(temp[i]);
			}
		}
		return envs;
	}
	
	public void encode(File configFile) throws PhrescoException {
		try {
			String fileToString = FileUtils.fileRead(configFile);
			String content = Base64.encodeBase64String(fileToString.getBytes());
			FileUtils.fileWrite(configFile, content);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	public void encryptConfigFile(String fileName) throws PhrescoException {
		InputStream inputStream = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			inputStream = new FileInputStream(new File(fileName));
			Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			EncryptString encryptstring = new EncryptString();
			encryptstring.Crypto("D4:6E:AC:3F:F0:BE");
			String encryptXmlString = encryptstring.encrypt(stw.toString());
			writeXml(encryptXmlString, fileName);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}  finally {
			Utility.closeStream(inputStream);
		}
	}
	
	private void writeXml(String encrStr, String fileName) throws PhrescoException  {
		DataOutputStream dos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			dos = new DataOutputStream(fos);
			dos.writeBytes(encrStr);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(dos);
			Utility.closeStream(fos);
		}
	}

	public void setDefaultEnvironment(String environmentName, File sourceConfigXML) throws PhrescoException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(sourceConfigXML);
			NodeList environmentList = doc.getElementsByTagName("environment");
			for (int i = 0; i < environmentList.getLength(); i++) {
				Element environment = (Element) environmentList.item(i);
				String envName = environment.getAttribute("name");
				String[] envs = environmentName.split(",");
				for (String envsName : envs) {
					if (envsName.equals(envName)) {
						environment.setAttribute("default", "true");
						// write the content into xml file
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(sourceConfigXML.toURI().getPath());
						transformer.transform(source, result);
					}
				}
			}

		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		}
	}
	
	public BuildInfo getBuildInfo(int buildNumber) throws MojoExecutionException {
		File currentDirectory = new File(".");
		System.out.println("currentDirectory.getPath() in pluginUils is " + currentDirectory.getPath());
		File buildInfoFile = new File(currentDirectory.getPath() + PluginConstants.BUILD_DIRECTORY + BUILD_INFO_FILE);
		if (!buildInfoFile.exists()) {
			throw new MojoExecutionException("Build info is not available!");
		}
		try {
			List<BuildInfo> buildInfos = getBuildInfo(buildInfoFile);
			
			 if (CollectionUtils.isEmpty(buildInfos)) {
				 throw new MojoExecutionException("Build info is empty!");
			 }

			 for (BuildInfo buildInfo : buildInfos) {
				 if (buildInfo.getBuildNo() == buildNumber) {
					 return buildInfo;
				 }
			 }

			 throw new MojoExecutionException("Build info is empty!");
		} catch (Exception e) {
			throw new MojoExecutionException(e.getLocalizedMessage());
		}
	}
	
	 public List<BuildInfo> getBuildInfo(File path) throws IOException {
		 if (!path.exists()) {
			 System.out.println("build info file doesnot exist!!!");
			 return new ArrayList<BuildInfo>(1);
		 }

		 BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		 Gson gson = new Gson();
		 Type type = new TypeToken<List<BuildInfo>>(){}.getType();

		 List<BuildInfo> buildInfos = gson.fromJson(bufferedReader, type);
		 Collections.sort(buildInfos, new BuildInfoComparator());
		 bufferedReader.close();

		 return buildInfos;
	 }
}
