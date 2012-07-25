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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.util.Constants;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class ServerPluginUtil {

	protected void addServerPlugin(ProjectInfo info, File path) throws PhrescoException {
		List<Server> servers = info.getTechnology().getServers();
		if (CollectionUtils.isEmpty(servers)) {
			return;
		}

		for (Server server : servers) {
			List<String> versions = server.getVersions();
			for (String version : versions) {
				if (server.getName().contains(Constants.TYPE_WEBLOGIC) && (version.equals("12c(12.1.1)"))) {
					addWebLogicPlugin(path);
				}
			}
		}
	}

	private void addWebLogicPlugin(File pomFile) throws PhrescoException {
		try {
			PomProcessor pomProcessor = new PomProcessor(pomFile);
			pomProcessor.addPlugin("com.oracle.weblogic", "weblogic-maven-plugin", "12.1.1.0");
			List<Element> configList = new ArrayList<Element>();
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element adminUrl = doc.createElement("adminurl");
			adminUrl.setTextContent("t3://${server.host}:${server.port}");
			Element user = doc.createElement("user");
			user.setTextContent("${server.username}");
			Element password = doc.createElement("password");
			password.setTextContent("${server.password}");
			Element upload = doc.createElement("upload");
			upload.setTextContent("true");
			Element action = doc.createElement("action");
			action.setTextContent("deploy");
			Element remote = doc.createElement("remote");
			remote.setTextContent("true");
			Element verbose = doc.createElement("verbose");
			verbose.setTextContent("false");
			Element source = doc.createElement("source");
			source.setTextContent("${project.basedir}/do_not_checkin/build/temp/${project.build.finalName}.war");
			Element name = doc.createElement("name");
			name.setTextContent("${project.build.finalName}");
			Element argLineElem =doc.createElement("argLine");
			argLineElem.setTextContent("-Xmx512m");

			configList.add(adminUrl);
			configList.add(user);
			configList.add(password);
			configList.add(upload);
			configList.add(action);
			configList.add(remote);
			configList.add(verbose);
			configList.add(source);
			configList.add(name);
			configList.add(argLineElem);

			pomProcessor.addConfiguration("com.oracle.weblogic", "weblogic-maven-plugin", configList);
			pomProcessor.save();

		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	public void deletePluginFromPom(File path) throws PhrescoException {
		try {
			PomProcessor pomprocessor = new PomProcessor(path);
			pomprocessor.deletePlugin("com.oracle.weblogic", "weblogic-maven-plugin");
			pomprocessor.save();
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
}