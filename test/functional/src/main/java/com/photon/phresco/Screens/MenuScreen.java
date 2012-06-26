package com.photon.phresco.Screens;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

public class MenuScreen extends WebDriverAbstractBaseScreen {

	PhrescoUiConstantsXml phrsc;

	private Log log = LogFactory.getLog(getClass());
	public WebDriverBaseScreen element;

	public MenuScreen(PhrescoUiConstantsXml phrsc) throws Exception {
		this.phrsc = phrsc;
	}

	public void gotoApplicationScreen() throws Exception{
		element = getXpathWebElement(phrsc.APPLICATIONS_TAB);
		element.click();
		element = getXpathWebElement(phrsc.ADD_APPLICATION_BUTTON);	
		waitForElementPresent(phrsc.ADD_APPLICATION_BUTTON);
	}
    public void gotoSettingsScreen(){
		
	}
	public void gotoHelpScreen(){
		
	}
}
