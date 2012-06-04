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
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.Technology;

/**
 * POM updater essentially updates the pom.xml of the project based on modules
 * attached to the given {@link Technology} object.
 * 
 * The groupId for the dependency calculated as "modules."+ {@link Technology}
 * .getTechId()+".files")
 * 
 * artifactId as {@link TupleBean}.getName()
 * 
 * version as {@link TupleBean}.getVersion()
 * 
 * @author arunachalam_l
 * 
 */
public class POMUpdater {
	private static final Logger S_LOGGER = Logger.getLogger(POMUpdater.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	/**
	 * @param path
	 *            file path where the pom.xml present.
	 * @param technology
	 * @throws PhrescoException
	 */
	public static void updatePOM(File path, Technology technology) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method POMUpdater.updatePOM(File path, Technology technology)");
		}
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("updatePOM() File path"+path.getPath());
			}
			SAXBuilder builder = new SAXBuilder();
			File pom = new File(path, "pom.xml");
			if(!pom.exists()) {
				return;
			}
			Document doc = builder.build(pom);

			Element rootNode = doc.getRootElement();
			Element dependencies = getDependenciesNode(rootNode);
			if(technology.getModules() != null){
				for (ModuleGroup bean : technology.getModules()) {
					Element dependency = createDependencyNode(bean, technology, rootNode.getNamespace());
					dependencies.addContent(dependency);
				}
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(pom));
		} catch (JDOMException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * @param bean
	 * @param technology
	 * @param namespace
	 * @return
	 */
	private static Element createDependencyNode(ModuleGroup bean, Technology technology, Namespace namespace) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method POMUpdater.createDependencyNode(TupleBean bean, Technology technology, Namespace namespace)");
		}
		Element dependency = new Element("dependency", namespace);
		Element group = new Element("groupId", namespace);
		group.setText(getModulePath(technology));
		Element artifactId = new Element("artifactId", namespace);
		artifactId.setText(bean.getId());
		Element version = new Element("version", namespace);
		Module moduleVersion = bean.getVersions().get(0);
		version.setText(moduleVersion.getVersion());
		dependency.addContent(group);
		dependency.addContent(artifactId);
		dependency.addContent(version);
		return dependency;
	}

	private static String getModulePath(Technology technology) {
		return "modules." + technology.getId() + ".files";
	}

	/**
	 * @param rootNode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Element getDependenciesNode(Element rootNode) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method POMUpdater.getDependenciesNode(Element rootNode)");
		}
		Element dependencies = rootNode.getChild("dependencies");
		// sometime, this doesn't work. So as workaround this stint.
		if (dependencies == null) {
			List children = rootNode.getChildren();
			for (Object object : children) {
				if ((object instanceof Element) && ((Element) object).getName().equals("dependencies")) {
					dependencies = (Element) object;
					break;
				}
			}
		}
		return dependencies;
	}
}
