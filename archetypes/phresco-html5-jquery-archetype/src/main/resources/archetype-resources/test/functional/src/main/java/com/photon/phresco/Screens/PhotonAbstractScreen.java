package com.photon.phresco.Screens;

import java.io.IOException;

import com.photon.phresco.uiconstants.PhrescoUiConstants;

public class PhotonAbstractScreen extends BaseScreen {

	protected PhotonAbstractScreen() {

	}

	protected PhotonAbstractScreen(String selectedBrowser,
			String applicationURL, String context,
			PhrescoUiConstants phrescoUiConstants) throws IOException,
			Exception {
		super(selectedBrowser, applicationURL, context, phrescoUiConstants);
	}

}
