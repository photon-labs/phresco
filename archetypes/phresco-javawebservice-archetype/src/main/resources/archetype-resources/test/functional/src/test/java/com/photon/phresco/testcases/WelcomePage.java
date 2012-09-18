package com.photon.phresco.testcases;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.uiconstants.PhrescoUiConstants;

public class WelcomePage {

	private static PhrescoUiConstants phrescoUIConstants;
	private static WelcomeScreen welcomeScreen;	
	private static String selectedBrowser;

	// private Log log = LogFactory.getLog(getClass());

	@BeforeClass
	public static void setUp() throws Exception {
		try {
			phrescoUIConstants = new PhrescoUiConstants();
			launchingBrowser();			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void launchingBrowser() throws Exception {
		try {
			String applicationURL = phrescoUIConstants.PROTOCOL + "://"
					+ phrescoUIConstants.HOST + ":" + phrescoUIConstants.PORT
					+ "/";
			selectedBrowser = phrescoUIConstants.BROWSER;
			welcomeScreen = new WelcomeScreen(selectedBrowser, applicationURL,
					phrescoUIConstants.CONTEXT);
		} catch (Exception exception) {
			exception.printStackTrace();

		}

	}

	@Test
	public void testWelcomePageScreen() throws InterruptedException,
			IOException, Exception {
		try {
			Assert.assertNotNull(welcomeScreen);
			welcomeScreen.javaWebserviceNone();
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	@AfterClass
	public static void tearDown() {
		welcomeScreen.closeBrowser();
	}

}