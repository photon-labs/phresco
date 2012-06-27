package com.photon.phresco.uiconstants;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.photon.phresco.selenium.util.ScreenException;


public class ReadXMLFile {

	private static Element eElement;
	private Log log = LogFactory.getLog(getClass());
	private static final String phrsc = "./src/main/resources/PhrescoConstants.xml";
	private static final String androidNativeConst = "./src/main/resources/AndroidNativeConstants.xml";
	private static final String androidHybridConst = "./src/main/resources/AndroidHybridConstants.xml";
	private static final String drupal = "./src/main/resources/Drupal7Constants.xml";
	private static final String php ="./src/main/resources/PhpConstants.xml";
	private static final String sharepoint = "./src/main/resources/SharepointConstants.xml";
	private static final String iPhone = "./src/main/resources/iPhoneConstants.xml";
	private static final String jws = "./src/main/resources/jwsConstants.xml";
	private static final String mobwidg = "./src/main/resources/MobileWidgetConstants.xml";
	private static final String nodejs = "./src/main/resources/NodeJSConstants.xml";
	private static final String yui ="./src/main/resources/YUIWidgetConstants.xml";
	private static final String drupal6="./src/main/resources/Drupal6Constants.xml";
	private static final String jquery ="./src/main/resources/JqueryWidget.xml";
	private static final String wordpress="./src/main/resources/WordPressConstants.xml";
	private static final String DotNet="./src/main/resources/DotNet.xml";
	public ReadXMLFile() throws ScreenException {
		log.info("@ReadXMLFile Constructor::loading *****PhrescoUIConstants******");
		loadPhrescoConstansts(phrsc);
	}

	public void loadPhrescoConstansts(String properties) throws ScreenException {

		try {
			File fXmlFile = new File(properties);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			/*System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());*/
			NodeList nList = doc.getElementsByTagName("phresco");
			System.out.println("-----------------------");

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
	
	public void loadAndroidNativeConstants()throws ScreenException{
		loadPhrescoConstansts(androidNativeConst);
	}
	public void loadAndroidHybridConstants()throws ScreenException{
		loadPhrescoConstansts(androidHybridConst);
	}
	public void loadDrupal7Constants()throws ScreenException{
		loadPhrescoConstansts(drupal);
	}
	public void loadPhpConstants()throws ScreenException{
		loadPhrescoConstansts(php);
	}
	public void loadSharepointConstants() throws ScreenException {
		loadPhrescoConstansts(sharepoint);
	}
	public void loadiPhoneConstants() throws ScreenException {
		loadPhrescoConstansts(iPhone);	
	}
    public void loadJavaWebServConstants() throws ScreenException {
    	loadPhrescoConstansts(jws);	
	}
    public void loadMobileWidgetConstants() throws ScreenException {
    	loadPhrescoConstansts(mobwidg);
	}
    public void loadNodejsConstants() throws ScreenException {
    	loadPhrescoConstansts(nodejs);
	}
    public void loadYuiConstants() throws ScreenException {
    	loadPhrescoConstansts(yui);
	}
    public void loadDrupal6Constants() throws ScreenException {
    	loadPhrescoConstansts(drupal6);
		
	}
	public void loadJqueryWidgetConstants() throws ScreenException {
		loadPhrescoConstansts(jquery);
		
	}
	public void loadWordPressConstants() throws ScreenException {
		loadPhrescoConstansts(wordpress);
	}
	public void loadDotNetConstants() throws ScreenException {
		loadPhrescoConstansts(DotNet);
		
	}

	
	public String getValue(String elementName) {

		NodeList nlList = eElement.getElementsByTagName(elementName).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);		
		if (nValue.getNodeValue() == null) {
			throw new NullArgumentException("***The element value is zero***");
		} 

		return nValue.getNodeValue();
	}
		

}