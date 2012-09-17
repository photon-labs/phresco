package com.photon.phresco.Screens;

import java.io.IOException;

import com.photon.phresco.uiconstants.PhrescoUiConstants;


public class WelcomeScreen extends PhotonAbstractScreen {
	private static PhrescoUiConstants phrescoUIConstants;
	
	public WelcomeScreen(String selectedBrowser, String applicationURL,
			String applicationContext)
			throws InterruptedException, IOException, Exception {
		super(selectedBrowser, applicationURL, applicationContext);

	}
	
	public void javaWebserviceNone() throws InterruptedException, IOException, Exception {
		phrescoUIConstants = new PhrescoUiConstants();
		isTextPresent(phrescoUIConstants.ELEMENT);
		Thread.sleep(2000);
	
	}
}
