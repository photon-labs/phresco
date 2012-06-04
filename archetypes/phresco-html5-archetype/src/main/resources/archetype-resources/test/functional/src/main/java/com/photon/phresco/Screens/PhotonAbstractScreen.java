package com.photon.phresco.Screens;

import java.io.IOException;

public class PhotonAbstractScreen extends AbstractBaseScreen {

	// public PhrescoUiConstantsXml phrescoXml;

	protected PhotonAbstractScreen() {

	}

	protected PhotonAbstractScreen(String host, int port, String browser,
			String url, String speed, String context) throws IOException,
			Exception {
		super(host, port, browser, url, speed, context);
	}

}
