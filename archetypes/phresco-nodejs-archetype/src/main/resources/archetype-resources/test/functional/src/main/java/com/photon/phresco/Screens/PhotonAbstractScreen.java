package com.photon.phresco.Screens;

import java.io.IOException;

import com.photon.phresco.selenium.util.ScreenException;

public class PhotonAbstractScreen extends WebDriverAbstractBaseScreen {

	// public PhrescoUiConstantsXml phrescoXml;

	protected PhotonAbstractScreen()throws ScreenException {

	}

	protected PhotonAbstractScreen(String host, int port, String browser,
			String url, String speed, String contextName) throws IOException,
			Exception {
		super(host, port, browser, url, speed, contextName);
	}

}
