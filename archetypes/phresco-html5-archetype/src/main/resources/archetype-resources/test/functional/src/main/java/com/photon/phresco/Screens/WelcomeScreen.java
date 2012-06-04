package com.photon.phresco.Screens;

import java.io.IOException;


import com.photon.phresco.uiconstants.PhrescoUiConstants;




public class WelcomeScreen extends PhotonAbstractScreen {
	private PhrescoUiConstants phrsc;
    public WelcomeScreen(String host, int port, String browser, String url, String speed,String context ) throws InterruptedException,IOException, Exception {
    	super(host, port, browser, url, speed, context);
    	
    	
    
    }
public MenuScreen menuScreen() throws Exception {
    
	return new MenuScreen();
}

}

