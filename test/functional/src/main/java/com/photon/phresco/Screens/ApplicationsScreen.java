package com.photon.phresco.Screens;

import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

public class ApplicationsScreen extends WebDriverAbstractBaseScreen {
	private PhrescoUiConstantsXml phrescoConst;
	public WebDriverBaseScreen element;
	

	public ApplicationsScreen(PhrescoUiConstantsXml phrescoConst) {
		this.phrescoConst = phrescoConst;
	}

	public AddApplicationScreen gotoAddApplicationScreen()
			throws Exception {
		element=getXpathWebElement(phrescoConst.ADD_APPLICATION_BUTTON);
		waitForElementPresent(phrescoConst.ADD_APPLICATION_BUTTON);
		element.click();
		waitForElementPresent(phrescoConst.APPINFO_NAME);
		return new AddApplicationScreen(phrescoConst);
	}

	public void gotoImportFromSVN() throws ScreenException {

	}
	
	
	
	
}
