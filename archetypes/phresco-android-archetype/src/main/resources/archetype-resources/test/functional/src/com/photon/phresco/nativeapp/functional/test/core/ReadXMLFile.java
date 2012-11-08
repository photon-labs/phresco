package com.photon.phresco.nativeapp.functional.test.core;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.photon.phresco.nativeapp.functional.test.testcases.TestException;

public class ReadXMLFile {

	private static Element eElement;

	public ReadXMLFile() throws TestException {

	}

	public void loadPhrescoConstansts(InputStream inputStream)
			throws TestException {

		try {
			// File fXmlFile = new File(inputStream);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputStream);
			NodeList nList = doc.getElementsByTagName("environment");

			System.out.println("--------Node list-->" + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadUserInfoConstants(InputStream inputStream)
			throws TestException {
		loadPhrescoConstansts(inputStream);

	}

	public String getValue(String elementName) {

		NodeList nlList = eElement.getElementsByTagName(elementName).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue.getNodeValue() == null) {

		}

		return nValue.getNodeValue();
	}

}