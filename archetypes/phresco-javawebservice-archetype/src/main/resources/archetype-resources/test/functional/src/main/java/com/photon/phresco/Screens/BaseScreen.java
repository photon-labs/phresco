package com.photon.phresco.Screens;

import java.awt.AWTException;
import java.io.IOException;

import com.photon.phresco.selenium.report.Reporter;
import com.photon.phresco.selenium.util.ScreenActionFailedException;
import com.photon.phresco.selenium.util.ScreenshottingSelenium;
import com.photon.phresco.uiconstants.PhrescoUiConstants;

public class BaseScreen {

	public static ScreenshottingSelenium selenium;

	public BaseScreen() {

	}

	public BaseScreen(String url, String browser, String speed,
			Reporter reporter) throws AWTException, IOException,
			ScreenActionFailedException {
		selenium = new ScreenshottingSelenium("localhost", 4444, browser, url,
				reporter);
		selenium.start();
		selenium.setSpeed(speed);
		selenium.open("/");
	}

	public static void initialize(String browser, Reporter reporter,
			String speed, String url)
			throws com.photon.phresco.selenium.util.ScreenActionFailedException {
		selenium = new ScreenshottingSelenium("localhost", 4444, browser, url,
				reporter);
		selenium.start();
		selenium.setSpeed(speed);
		PhrescoUiConstants uiConstants = new PhrescoUiConstants();
		selenium.open(uiConstants.CONTEXT);
		selenium.waitForPageToLoad("30000");
	}

	public static ScreenshottingSelenium getSeleniumInstance(String browser,
			Reporter reporter, String speed, String url)
			throws ScreenActionFailedException {
		if (selenium == null) {
			selenium = new ScreenshottingSelenium("localhost", 4444, browser,
					url, reporter);
		}

		return selenium;
	}

}
