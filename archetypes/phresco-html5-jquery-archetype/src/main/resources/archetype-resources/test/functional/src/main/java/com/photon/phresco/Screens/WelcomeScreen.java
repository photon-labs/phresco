package com.photon.phresco.Screens;

import java.io.IOException;

import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.UIConstants;

public class WelcomeScreen extends PhotonAbstractScreen {
	public UIConstants phrsc;

	public WelcomeScreen(String selectedBrowser, String applicationURL,
			String context,PhrescoUiConstants phrescoUiConstants) throws InterruptedException, IOException, Exception {
		super(selectedBrowser, applicationURL, context,phrescoUiConstants);

	}

}
