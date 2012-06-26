package com.photon.phresco.Screens;

import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

public class PhrescoWelcomePage extends WebDriverAbstractBaseScreen {

	public PhrescoUiConstantsXml phrescoConst;
	public WebDriverBaseScreen element;
	public PhrescoWelcomePage(PhrescoUiConstantsXml phrescoConst){
		this.phrescoConst=phrescoConst;
		
	}
	
	public void goToPhrescoHomePage(String methodName) throws Exception {
		element=getXpathWebElement(phrescoConst.GOTOHOME_PAGE);
		waitForElementPresent(phrescoConst.GOTOHOME_PAGE,methodName);
		element.click();
		element=getXpathWebElement(phrescoConst.APPLICATIONS_TAB);
		waitForElementPresent(phrescoConst.APPLICATIONS_TAB,methodName);
		
	}

	public ApplicationsScreen clickOnApplicationsTab(String methodName) throws Exception {
		element=getXpathWebElement(phrescoConst.APPLICATIONS_TAB);
		waitForElementPresent(phrescoConst.APPLICATIONS_TAB,methodName);
		element.click();
		waitForElementPresent(phrescoConst.ADD_APPLICATION_BUTTON,methodName);
		return new ApplicationsScreen(phrescoConst);
	}

	public void clickOnSettingsTab() throws ScreenException {

	}

	public void clickOnHelpTab() throws ScreenException {

	}
    public void CleanUp(String methodName) throws Exception{
    	
    	deleteAllCookies();
    	element=getXpathWebElement(phrescoConst.APPINFO_SELECTALL);
		waitForElementPresent(phrescoConst.APPINFO_SELECTALL,methodName);
		element.click();
		element=getXpathWebElement(phrescoConst.APPINFO_DELETE);
		element.click();
		element=getXpathWebElement(phrescoConst.APPINFO_CONFIRM_OK);
		element.click();
		waitForElementPresent(phrescoConst.APPINFO_NOPROJSAVAILABLE_MSG,methodName);
		//waitForElementPresent(phrescoConst.APPINFO_DELETION_SUCCESSMSG);
		
	}

}
