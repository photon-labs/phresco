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
package com.photon.phresco.framework.pom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.pom.POMConstants;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class AndroidTestPOMUpdater {
	private static final Logger S_LOGGER= Logger.getLogger(AndroidTestPOMUpdater.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	/**
	 * @param path
	 *            file path where the pom.xml present.
	 * @return 
	 * @throws PhrescoException
	 * @throws PhrescoPomException 
	 * @throws JAXBException 
	 */
	public static boolean updatePOM(File path) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method AndroidTestPOMUpdater.updatePOM(File path)");
			S_LOGGER.debug("updatePOM File Path=" + path.getPath());
		}
		File testFunctionalPom = new File(path, "/test/functional/pom.xml");
		File projectPom = new File(path, "pom.xml");
		File testUnitPom = new File(path, "/test/unit/pom.xml");
		File testPerfPom = new File(path, "/test/performance/pom.xml");
		boolean functionalTest = updateTestPom(testFunctionalPom, projectPom);
		boolean unitTest = updateTestPom(testUnitPom, projectPom);
		boolean performanceTest = updateTestPom(testPerfPom, projectPom);
		if (Boolean.TRUE.equals(functionalTest) || Boolean.TRUE.equals(unitTest) || Boolean.TRUE.equals(performanceTest)  ) {
			return true;
		} else {
			return false;
		}
	}
		
	/**
	 * @param testPom
	 * @param projectPom
	 * @return 
	 * @throws PhrescoException 
	 * @throws JDOMException
	 * @throws IOException
	 * @throws JAXBException 
	 * @throws PhrescoPomException 
	 */
	private static boolean updateTestPom(File testPom, File projectPom) throws PhrescoException {
		SAXBuilder builder = new SAXBuilder();
		if(!testPom.exists()) {
			return false;
		}
		try {
			Document projectDoc = builder.build(projectPom);
			Element projectRootNode = projectDoc.getRootElement();
			Element group = getNode(projectRootNode, POMConstants.GROUP_ID);
			Element artifact = getNode(projectRootNode, POMConstants.ARTIFACT_ID);
			Element version = getNode(projectRootNode, POMConstants.VERSION);

			PomProcessor processor = new PomProcessor(testPom);
			processor.addDependency(group.getText(), artifact.getText(),  version.getText() , POMConstants.PROVIDED , POMConstants.JAR, "");
			processor.addDependency(group.getText(), artifact.getText(),  version.getText() , POMConstants.PROVIDED , POMConstants.APK, "");
			processor.save();
		
		} catch (JDOMException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return true;
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
