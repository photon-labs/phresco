package com.photon.phresco.Screens;

import java.io.IOException;

public class PhotonAbstractScreen extends BaseScreen {

	// public PhrescoUiConstantsXml phrescoXml;

	protected PhotonAbstractScreen() {

	}

	protected PhotonAbstractScreen(String selectedBrowser, String applicationURL, String context) throws IOException,
			Exception {
		super(selectedBrowser, applicationURL, context);
	}

}
