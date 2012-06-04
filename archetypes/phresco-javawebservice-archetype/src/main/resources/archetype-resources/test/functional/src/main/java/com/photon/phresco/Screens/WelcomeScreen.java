package com.photon.phresco.Screens;

import java.io.IOException;

import com.photon.phresco.selenium.report.Reporter;
import com.photon.phresco.uiconstants.PhrescoUiConstants;

public class WelcomeScreen extends PhotonAbstractScreen {
	PhrescoUiConstants phrsc = new PhrescoUiConstants();

	public WelcomeScreen(String host, int port, String browser, String url,
			String speed, Reporter reporter) throws InterruptedException,
			IOException, Exception {
		super(host, port, browser, url, speed, reporter);
		
		waitForTextPresent(phrsc.TEXTCAPTURED);

	}

}
