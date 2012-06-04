package com.photon.phresco.Screens;

import java.io.IOException;

import com.photon.phresco.selenium.report.Reporter;
import com.photon.phresco.uiconstants.PhrescoUiConstants;

public class PhotonAbstractScreen extends AbstractBaseScreen {

	public PhrescoUiConstants PhotonUIConstants;

	protected PhotonAbstractScreen() {

	}

	protected PhotonAbstractScreen(String host, int port, String browser,
			String url, String speed, Reporter reporter) throws IOException,
			Exception {
		super(host, port, browser, url, speed, reporter);
	}

}
