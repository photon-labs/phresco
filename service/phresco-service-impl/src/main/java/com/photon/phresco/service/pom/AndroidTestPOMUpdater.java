/*
 * ###
 * Phresco Service Implemenation
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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.pom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.photon.phresco.exception.PhrescoException;

public class AndroidTestPOMUpdater {
	private static final Logger S_LOGGER= Logger.getLogger(AndroidTestPOMUpdater.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	/**
	 * @param path
	 *            file path where the pom.xml present.
	 * @throws PhrescoException
	 */
	public static void updatePOM(File path) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method AndroidTestPOMUpdater.updatePOM(File path)");
			S_LOGGER.debug("updatePOM File Path=" + path.getPath());
		}
		try {
			File testFunctionalPom = new File(path, "/test/functional/pom.xml");
			File projectPom = new File(path, "pom.xml");
			File testUnitPom = new File(path, "/test/unit/pom.xml");
			File testPerfPom = new File(path, "/test/performance/pom.xml");
			
			updateTestPom(testFunctionalPom, projectPom);
			updateTestPom(testUnitPom, projectPom);
			updateTestPom(testPerfPom, projectPom);

		} catch (JDOMException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * @param testPom
	 * @param projectPom
	 * @throws JDOMException
	 * @throws IOException
	 */
	private static void updateTestPom(File testPom, File projectPom) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		
		if(!testPom.exists()) {
			return;
		}
		Document projectDoc = builder.build(projectPom);
		Document testDoc = builder.build(testPom);

		Element projectRootNode = projectDoc.getRootElement();
		Element testRootNode = testDoc.getRootElement();
		Element group = getNode(projectRootNode, POMConstants.GROUP_ID);
		Element artifact = getNode(projectRootNode, POMConstants.ARTIFACT_ID);
		Element version = getNode(projectRootNode, POMConstants.VERSION);

		Element dependencies = POMUpdater.getDependenciesNode(testRootNode);

		Element jarDependency = createDependencyNode(group.getText(), artifact.getText(), version.getText(), POMConstants.JAR, testRootNode.getNamespace());
		Element apkDependency = createDependencyNode(group.getText(), artifact.getText(), version.getText(), POMConstants.APK, testRootNode.getNamespace());
		dependencies.addContent(apkDependency);
		dependencies.addContent(jarDependency);


		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(testDoc, new FileWriter(testPom));
	}

	private static Element createDependencyNode(String group, String artifact, String version, String type, Namespace namespace) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method AndroidTestPOMUpdater.createDependencyNode(String group, String artifact, String version, String type, Namespace namespace)");
		}
		Element dependency = new Element(POMConstants.DEPENDENCY, namespace);
		Element groupId = new Element(POMConstants.GROUP_ID, namespace);
		groupId.setText(group);
		Element artifactId = new Element(POMConstants.ARTIFACT_ID, namespace);
		artifactId.setText(artifact);
		Element versionId = new Element(POMConstants.VERSION, namespace);
		versionId.setText(version);
		Element typeNode = new Element(POMConstants.TYPE, namespace);
		typeNode.setText(type);
		Element scope = new Element(POMConstants.SCOPE, namespace);
		scope.setText(POMConstants.PROVIDED);

		dependency.addContent(groupId);
		dependency.addContent(artifactId);
		dependency.addContent(versionId);
		dependency.addContent(typeNode);
		dependency.addContent(scope);

		return dependency;
	}

	/**
	 * @param rootNode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Element getNode(Element rootNode, String nodeName) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method AndroidTestPOMUpdater.getNode(Element rootNode, String nodeName)");
		}
		Element dependencies = rootNode.getChild(nodeName);
		// sometime, this doesn't work. So as workaround this stint.
		if (dependencies == null) {
			List children = rootNode.getChildren();
			for (Object object : children) {
				if ((object instanceof Element) && ((Element) object).getName().equals(nodeName)) {
					dependencies = (Element) object;
					break;
				}
			}
		}
		return dependencies;
	}

}
