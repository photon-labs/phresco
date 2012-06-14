package com.photon.phresco.Screens;

import java.io.IOException;



import com.photon.phresco.uiconstants.PhrescoUiConstants;





public class MenuScreen extends WebDriverAbstractBaseScreen{
	PhrescoUiConstants phrsc = new PhrescoUiConstants();
    public MenuScreen() throws Exception {
    	
    	isTextPresent(phrsc.TEXTCAPTURED);
    	
    }
    
 }

