/*
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.testcases;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;
//import static org.testng.AssertJUnit.*;
import org.openqa.selenium.server.SeleniumServer;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.selenium.report.Reporter;
import com.thoughtworks.selenium.Selenium;
import com.photon.phresco.uiconstants.PhrescoUiConstants;

public class AWelcomePage extends TestCase {

	private SeleniumServer serv;
	protected Selenium selenium;

	@Test
	public void testWel() throws InterruptedException, IOException, Exception {
		try {
			PhrescoUiConstants phrsc = new PhrescoUiConstants();
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.SERVER_PORT + "/";
			String BROWSERAppends = "*" + phrsc.BROWSER;
			WelcomeScreen wel = new WelcomeScreen(phrsc.HOST, phrsc.PORT,
					BROWSERAppends, serverURL, phrsc.SPEED, new Reporter());
			assertNotNull(wel);
		} catch (Exception t) {
			t.printStackTrace();
			System.out.println("ScreenCaptured");
			selenium.captureEntirePageScreenshot("\\WelPageFails.png",
					"background=#CCFFDD");
		}
	}

	@Override
	public void setUp() throws Exception {

		serv = new SeleniumServer();
		try {
			serv.start();
		} catch (Exception e) {
			clean();
			throw e;
		}
	}

	@Override
	public void tearDown() {
		clean();
	}

	private void clean() {
		if (serv != null) {
			serv.stop();
		}
		if (selenium != null) {
			selenium.stop();
		}
	}

}