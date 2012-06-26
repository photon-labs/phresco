package com.photon.phresco.preconditions;

import java.io.IOException;

import org.junit.Test;


import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

import junit.framework.TestCase;

public class CleanUP extends TestCase{
	private PhrescoUiConstantsXml phrsc;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	private int SELENIUM_PORT;
	
	 @Test
	    public void testCleanUp() throws InterruptedException, IOException, Exception{
	    	    		
	    		   
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
	    			assertNotNull(wel);
	    			loginObject = new LoginScreen(phrsc);
	    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage();
	    			                   phrescoHome.goToPhrescoHomePage();
				                       phrescoHome.clickOnApplicationsTab();
				                       phrescoHome.CleanUp();
				                       
					
	    }

		public void setUp() throws Exception {
			phrsc = new PhrescoUiConstantsXml();
		}

		public void tearDown() {
			clean();
		}

		private void clean() {
			wel.closeBrowser();
		}


}
