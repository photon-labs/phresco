/*
 * ###
 * Phresco Framework
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
package com.photon.phresco.framework.validators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.ValidationResult;
import com.photon.phresco.framework.api.Validator;
import com.photon.phresco.util.Utility;

public class ArchetypeValidator implements Validator, FrameworkConstants {

	DocumentBuilder dBuilder = null;

	public ArchetypeValidator() throws PhrescoException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (Exception e) {
			throw new PhrescoException();
		}
	}

	public List<ValidationResult> validate(String projectCode) throws PhrescoException {
		
		File projectDir = new File(Utility.getProjectHome(), projectCode);
		File file = new File(projectDir+ File.separator + "pom.xml");
		if(!file.exists()) {
			List<ValidationResult> results = new ArrayList<ValidationResult>(16);
			ValidationResult result = new ValidationResult(ValidationResult.Status.ERROR,  " POM File is missing");
			results.add(result);
			return results;
		}
		else {
		FileInputStream fstream = null;
		BufferedReader br = null;
		try {
			// access the metadata file
			File archeTypeMetadataFile = new File(projectDir, ARCHETYPE_METADATA);
			// read the excludefile
			File excludeFile = new File(projectDir, EXCLUDEFILE);
			List<String> excludeValuesList = new ArrayList<String>(32);
			List<String> includeValuesList = new ArrayList<String>(128);
			if (excludeFile.exists()) {
				fstream = new FileInputStream(excludeFile);
			
				// Get the object of DataInputStream
				br = new BufferedReader(new InputStreamReader(fstream));
				String strLine;
				
				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {
					excludeValuesList.add(strLine);
				}
			}
			// get the list of fileset nodes
			if (archeTypeMetadataFile.exists()) {
				NodeList fileSetNodes = getFileSetNodes(archeTypeMetadataFile);
				// for every fileset, check if all the directories in includes exist in the project directory
				int length = fileSetNodes.getLength();
				for (int i = 0; i < length; i++) {
					Node item = fileSetNodes.item(i);
					validateFileSet(projectDir, item, excludeValuesList, includeValuesList);
				}
			}

			return validateFiles(projectDir, includeValuesList);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (fstream != null) {
					fstream.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
		}
	}

	private List<ValidationResult> validateFiles(File projectDir, List<String> includeValuesList) {
		File dir = null;
		List<ValidationResult> results = new ArrayList<ValidationResult>(16);
		for (String value : includeValuesList) {
			if (value.contains("**/*")) {
				value = value.substring(0, value.lastIndexOf("**/*"));
				dir = new File(projectDir, value + "/");
			} else {
				dir = new File(projectDir, value + "/");
			}

			if (!dir.exists()) {
				results.add(new ValidationResult(ValidationResult.Status.ERROR, dir.getPath() + " is missing"));
			}
		}
		return results;
	}

	private void validateFileSet(File projectDir, Node item, List<String> excludeValuesList,
			List<String> includeValuesList) {
		NodeList childNodes = item.getChildNodes();
		int length = childNodes.getLength();
		Node includesNode = null;
		Node dirNode = null;

		for (int i = 0; i < length; i++) {
			Node childNode = childNodes.item(i);
			if ("directory".equals(childNode.getNodeName())) {
				dirNode = childNode;
			} else if ("includes".equals(childNode.getNodeName())) {
				includesNode = childNode;
			}
		}

		// includesNode
		NodeList includeNodeList = includesNode.getChildNodes();
		int includeLength = includeNodeList.getLength();
		for (int j = 0; j < includeLength; j++) {
			Node includeNode = includeNodeList.item(j);
			if ("include".equals(includeNode.getNodeName())) {
				String path = getFullFilePath(dirNode.getTextContent(), includeNode.getTextContent());
				if (!excludeValuesList.contains(path)) {
					includeValuesList.add(path);
				}
			}
		}
	}

	private String getFullFilePath(String dirName, String includeFileName) {
		StringBuffer buff = new StringBuffer(1024);
		buff.append(dirName);
		if (!dirName.endsWith("/")) {
			buff.append("/");
		}
		buff.append(includeFileName);		
		return buff.toString();
	}

	private NodeList getFileSetNodes(File archeTypeMetadataFile) throws SAXException, IOException {
		Document doc = dBuilder.parse(archeTypeMetadataFile);
		return doc.getElementsByTagName("fileSet");
	}

	@Override
	public List<String> getAppliesTo() throws PhrescoException {
		return Collections.EMPTY_LIST;
	}
}
