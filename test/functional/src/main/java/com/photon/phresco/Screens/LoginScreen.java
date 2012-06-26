package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

public class LoginScreen extends WebDriverAbstractBaseScreen {
	private PhrescoUiConstantsXml phrsc;
	private Log log = LogFactory.getLog(getClass());
	public WebDriverBaseScreen element;

	public LoginScreen(PhrescoUiConstantsXml phrescoConstant)
			throws InterruptedException, IOException, Exception {
		log.info("@LoginScreen::*****constructor******");
		this.phrsc = phrescoConstant;
	}

	public PhrescoWelcomePage testLoginPage(String methodName) throws Exception {
		try {
			log.info("@testLoginPage::******executing loginpage scenario****");
			
			element=getXpathWebElement(phrsc.USER_NAME_XPATH);
			waitForElementPresent(phrsc.USER_NAME_XPATH,methodName);
			element.type(phrsc.USER_ID);
			
			element=getXpathWebElement(phrsc.PASSWORD_XPATH);
			waitForElementPresent(phrsc.PASSWORD_XPATH,methodName);
			element.type(phrsc.PASSWORD);
			
			element=getXpathWebElement(phrsc.REMEMBER_ME_CHECK);
			waitForElementPresent(phrsc.REMEMBER_ME_CHECK,methodName);
			element.click();

			element=getXpathWebElement(phrsc.LOGIN_BUTTON);
			waitForElementPresent(phrsc.LOGIN_BUTTON,methodName);
			element.click();
			
			waitForTextPresent(phrsc.WELCOME_TO_PHRESCO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PhrescoWelcomePage(phrsc);
		
	}

	
}
