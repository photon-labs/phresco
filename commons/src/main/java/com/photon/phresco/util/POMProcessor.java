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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.photon.phresco.service.pom.POMConstants;

/**
 * @author arunachalam_l
 * 
 */
public class POMProcessor {

	/**
	 * 
	 */
	private final File pomFile;

	/**
	 * 
	 */
	private final Document document;

	/**
	 * 
	 */
	private final Element rootNode;

	/**
	 * @param pomFile
	 * @throws JDOMException
	 * @throws IOException
	 */
	public POMProcessor(File pomFile) throws JDOMException, IOException {
		super();
		this.pomFile = pomFile;
		if (pomFile == null) {
			throw new IllegalArgumentException("pom file should not be null");

		}
		if (!pomFile.exists()) {
			throw new FileNotFoundException("File doesn't exist");
		}

		SAXBuilder builder = new SAXBuilder();
		document = builder.build(pomFile);
		rootNode = document.getRootElement();
	}

	/**
	 * @throws IOException
	 */
	/* new file */
	public void completeProcess() throws IOException {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(document, new FileWriter(pomFile));
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @param versionId
	 * @param type
	 * @param scope
	 */
	/* Method to add dependencies */
	public void addDependency(String groupId, String artifactId, String versionId, String type, String scope) {
		Element dependencies = getNode(rootNode, POMConstants.DEPENDENCIES);
		if (dependencies == null) {
			// create dependency node
			dependencies = createNode(rootNode, POMConstants.DEPENDENCIES);
		}
		Namespace namespace = rootNode.getNamespace();
		Element dependency = new Element(POMConstants.DEPENDENCY, namespace);
		Element group = new Element(POMConstants.GROUP_ID, namespace);
		group.setText(groupId);
		dependency.addContent(group);

		Element artifact = new Element(POMConstants.ARTIFACT_ID, namespace);
		artifact.setText(artifactId);
		dependency.addContent(artifact);

		Element version = new Element(POMConstants.VERSION, namespace);
		version.setText(versionId);
		dependency.addContent(version);

		if (StringUtils.isNotEmpty(type)) {
			Element typeNode = new Element(POMConstants.TYPE, namespace);
			typeNode.setText(type);
			dependency.addContent(typeNode);
		}

		if (StringUtils.isNotEmpty(scope)) {
			Element scopeNode = new Element(POMConstants.SCOPE, namespace);
			scopeNode.setText(POMConstants.PROVIDED);
			dependency.addContent(scopeNode);
		}
		dependencies.addContent(dependency);
	}

	/* Method to remove all the dependencies */
	public void removeAllDependencies() {
		Element dependencies = getNode(rootNode, POMConstants.DEPENDENCIES);
		if (dependencies != null) {
			rootNode.removeContent(dependencies);
		}
	}

	/* Method to remove dependencies */
	@SuppressWarnings("rawtypes")
	public void removeDependency(String groupId, String artifactId) {
		Element dependencies = getNode(rootNode, POMConstants.DEPENDENCIES);
		if (dependencies != null) {
			List children = dependencies.getChildren(POMConstants.DEPENDENCY, dependencies.getNamespace());
			for (Object object : children) {
				// document.getRootElement().getChild("").removeChild("address");
				String groupName = ((Element) object).getChildText(POMConstants.GROUP_ID, dependencies.getNamespace());
				if (groupName.equals(groupId)) {
					String artifactName = ((Element) object).getChildText(POMConstants.ARTIFACT_ID, dependencies.getNamespace());
					if (artifactName.equals(artifactId)) {
						dependencies.removeContent((Element) object);
						updateParent(dependencies);
						return;
					}
				}
			}
		}
	}

	/* Method to remove empty tag */
	private static void updateParent(Element element) {
		if (element.getChildren().size() == 0) {
			element.getParent().removeContent(element);
			return;
		}
	}

	/**
	 * @param rootNode2
	 * @param nodeName
	 * @return
	 */
	/* create nodes */
	private static Element createNode(Element rootNode2, String nodeName) {
		Element node = new Element(nodeName, rootNode2.getNamespace());
		rootNode2.addContent(node);
		return node;
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @param versionId
	 */
	/* add dependencies */
	public void addDependency(String groupId, String artifactId, String versionId) {
		addDependency(groupId, artifactId, versionId, "", "");
	}

	/**
	 * @param rootNode
	 * @return
	 */
	/* get the node */
	@SuppressWarnings("rawtypes")
	public static Element getNode(Element rootNode, String nodeName) {
		Element dependencies = rootNode.getChild(nodeName, rootNode.getNamespace());
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

	/* add repository element */
	public void addRepository(String repositoryId, String repositoryUrl) {
		Element repositories = getNode(rootNode, POMConstants.REPOSITORIES);
		if (repositories == null) {
			// create Repository node
			repositories = createNode(rootNode, POMConstants.REPOSITORIES);
		}
		Namespace namespace = rootNode.getNamespace();
		Element repository = new Element(POMConstants.REPOSITORY, namespace);
		Element Id = new Element(POMConstants.ID, namespace);
		Id.setText(repositoryId);
		repository.addContent(Id);

		Element url = new Element(POMConstants.URL, namespace);
		url.setText(repositoryUrl);
		repository.addContent(url);

		repositories.addContent(repository);
	}

	/* remove repository element */
	public void removeRepository(String groupId, String artifactId) {
		Element repositories = getNode(rootNode, POMConstants.REPOSITORIES);
		if (repositories != null) {
			@SuppressWarnings("rawtypes")
			List children = repositories.getChildren(POMConstants.REPOSITORY, repositories.getNamespace());
			for (Object object : children) {
				String idName = ((Element) object).getChildText(POMConstants.ID, repositories.getNamespace());
				if (idName.equals(groupId)) {
					String urlName = ((Element) object).getChildText(POMConstants.URL, repositories.getNamespace());
					if (urlName.equals(artifactId)) {
						repositories.removeContent((Element) object);
						updateParent(repositories);
						return;
					}
				}
			}
		}
	}

	/* set/modify dependency's version */
	@SuppressWarnings("rawtypes")
	public void SetVersion(String groupId, String artifactId, String newVersion) {
		Element dependencies = getNode(rootNode, POMConstants.DEPENDENCIES);
		if (dependencies != null) {
			List children = dependencies.getChildren(POMConstants.DEPENDENCY, dependencies.getNamespace());
			for (Object object : children) {
				Element dependent = (Element) object;
				String groupName = dependent.getChildText(POMConstants.GROUP_ID, dependencies.getNamespace());
				if (groupName.equals(groupId)) {
					String artifactName = dependent.getChildText(POMConstants.ARTIFACT_ID, dependencies.getNamespace());
					if (artifactName.equals(artifactId)) {
						Element version = getNode(dependent, POMConstants.VERSION);
						if (version == null) {
							// create version node
							version = createNode(dependent, POMConstants.VERSION);
							version.setText(newVersion);
						}
					}
				}
			}

		}
	}

	/* create pluginRepository */
	public void pluginRepository(String repositoryId, String repositoryUrl, String repositoryName) {
		Element pluginRepositories = getNode(rootNode, POMConstants.PLUGIN_REPOSITORIES);
		if (pluginRepositories == null) {
			// create pluginRepository node
			pluginRepositories = createNode(rootNode, POMConstants.PLUGIN_REPOSITORIES);
		}
		Namespace namespace = rootNode.getNamespace();
		Element pluginRepository = new Element(POMConstants.PLUGIN_REPOSITORY, namespace);
		Element Id = new Element(POMConstants.ID, namespace);
		Id.setText(repositoryId);
		pluginRepository.addContent(Id);

		Element url = new Element(POMConstants.URL, namespace);
		url.setText(repositoryUrl);
		pluginRepository.addContent(url);

		Element name = new Element(POMConstants.NAME, namespace);
		name.setText(repositoryName);
		pluginRepository.addContent(name);

		pluginRepositories.addContent(pluginRepository);

	}

	/* removes pluginRepository */
	public void removepluginRepository(String groupId, String artifactId) {
		Element pluginRepositories = getNode(rootNode, POMConstants.PLUGIN_REPOSITORIES);
		if (pluginRepositories != null) {
			@SuppressWarnings("rawtypes")
			List children = pluginRepositories.getChildren(POMConstants.PLUGIN_REPOSITORY, pluginRepositories.getNamespace());
			for (Object object : children) {
				String idName = ((Element) object).getChildText(POMConstants.ID, pluginRepositories.getNamespace());
				if (idName.equals(groupId)) {
					String urlName = ((Element) object).getChildText(POMConstants.URL, pluginRepositories.getNamespace());
					if (urlName.equals(artifactId)) {
						pluginRepositories.removeContent((Element) object);
						updateParent(pluginRepositories);
						return;
					}
				}
			}
		}
	}

}
