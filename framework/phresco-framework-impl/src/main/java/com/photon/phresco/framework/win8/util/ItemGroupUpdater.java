package com.photon.phresco.framework.win8.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;


public class ItemGroupUpdater implements FrameworkConstants {

	/**
	 * 
	 * @param args
	 * @throws PhrescoException 
	 */
	public static void update(ProjectInfo info, File path) throws PhrescoException {
		try {
			path = new File(path + File.separator + SOURCE_DIR + File.separator + info.getName() + File.separator + info.getName()+ PROJECT_FILE);
			List<ModuleGroup> modules = info.getTechnology().getModules();
			if(!path.exists() && modules == null) {
				return;
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			List<Node> itemGroup = getItemGroup(doc);
		    updateContent(doc, modules, itemGroup, CONTENT);
			boolean referenceCheck = referenceCheck(doc);
			if (referenceCheck) {
				updateItemGroups(doc,modules);
			} else {
				createNewItemGroup(doc,modules);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(path.toURI().getPath());
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (TransformerConfigurationException e) {
			throw new PhrescoException(e);
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		} 
	}

	private static void createNewItemGroup(Document doc, List<ModuleGroup> modules) {
		NodeList projects = doc.getElementsByTagName(PROJECT);
		Element itemGroup = doc.createElement(ITEMGROUP);
		for (int i = 0; i < projects.getLength(); i++) {
			Element project = (Element) projects.item(i);
			for (ModuleGroup module : modules) {
				Element reference = doc.createElement(REFERENCE);
				reference.setAttribute(INCLUDE , module.getName());
				Element hintPath = doc.createElement(HINTPATH);
				hintPath.setTextContent(DOUBLE_DOT + COMMON + File.separator + module.getName()+ DLL);
				reference.appendChild(hintPath);
				itemGroup.appendChild(reference);
			}
			project.appendChild(itemGroup);
		}
	}
	
	private static void updateItemGroups(Document doc, List<ModuleGroup> module) {
	   List<Node> itemGroup = getItemGroup(doc);
	   updateContent(doc, module, itemGroup, REFERENCE);
	}

	private static void updateContent(Document doc, List<ModuleGroup> modules,	List<Node> itemGroup, String elementName) {
		for (Node node : itemGroup) {
			NodeList childNodes = node.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node item = childNodes.item(j);
				if (item.getNodeName().equals(elementName)) {
					Node parentNode = item.getParentNode();
					for (ModuleGroup module : modules) {
						Element content = doc.createElement(elementName);
						if (elementName.equalsIgnoreCase(REFERENCE)) {
							content.setAttribute(INCLUDE, LIBS + module.getName()+ DLL);
							Element hintPath = doc.createElement(HINTPATH);
							hintPath.setTextContent(LIBS + module.getName()+ DLL);
							content.appendChild(hintPath);
						} else {
							content.setAttribute(INCLUDE, LIBS + module.getName()+ DLL);
						}
						parentNode.appendChild(content);
					}
					break;
				}
			}
		}
	}
	
	private static List<Node> getItemGroup(Document doc) {
		NodeList projects = doc.getElementsByTagName(PROJECT);
		List<Node> itemGroupList = new ArrayList<Node>();
		for (int i = 0; i < projects.getLength(); i++) {
			Element project = (Element) projects.item(i);
			NodeList itemGroups = project.getElementsByTagName(ITEMGROUP);
			for (int j = 0; j < itemGroups.getLength(); j++) {
				Element itemGroup = (Element) itemGroups.item(j);
				itemGroupList.add(itemGroup);
			}
		}
		return itemGroupList;
	}
	
	private static boolean referenceCheck(Document doc) {
		NodeList project = doc.getElementsByTagName(PROJECT);
		Boolean flag = false;
		for (int i = 0; i < project.getLength(); i++) {
			Element PROEJCT = (Element) project.item(i);
			NodeList ITEMGROUPs = PROEJCT.getElementsByTagName(ITEMGROUP);
			for (int j = 0; j < ITEMGROUPs.getLength(); j++) {
				Element ITEMGROUP = (Element) ITEMGROUPs.item(j);
				NodeList references = ITEMGROUP.getElementsByTagName(REFERENCE);
				for (int k = 0; k < references.getLength(); k++) {
					Element reference = (Element) references.item(k);
					if (reference.getTagName().equalsIgnoreCase(REFERENCE)) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}
}
