/*
 * ###
 * PHR_android-hybrid-hw
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.hybrid.activity;


import android.os.Bundle;

import com.phonegap.DroidGap;
import com.photon.phresco.hybrid.config.EnvConstuctor;
import com.photon.phresco.hybrid.logger.PhrescoLogger;
import com.photon.phresco.hybrid.utility.Utility;

public class MainActivity extends DroidGap {
	
//	public static String webserviceURL;
//	private static final String WEBSERVICE_CONFIG_NAME = "res_service";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	initApplicationEnvironment();
    	/*
    	 * Important
    	 * If this application expects data from outside(from internet) remove the following comments 
    	 */
//    	buildEnvData();
    	
    	/* if(!ConnectivityMessaging.checkNetworkConnectivity(this)){
			ConnectivityMessaging.showNetworkConectivityAlert(this);
		}
		else if(!ConnectivityMessaging.checkURLStatus(Constants.getWebContextURL() + Constants.getHomeURL())){
			ConnectivityMessaging.showServiceAlert(this);
		} */
        super.loadUrl("file:///android_asset/www/index.html");
    }
    
    /**
	 * Create the required folder structures on external storage Read the device
	 * information
	 */
	private void initApplicationEnvironment() {

		try {
			// Delete existing log file, when application starts, so the log
			// file doesn't consume more place on external device
			Utility.deleteLogFile();

			// Create all the directories required for this application
			Utility.createRequiredDirectory();

			// Get the device information
			Utility.getDeviceInfo();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG
					+ " initApplicationEnvironment -  Exception "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
	/*
	 * Important
	 * If this application expects data from outside(from internet) remove the following comments 
	 */
//	Below code is used for reading web service URL from Phresco configuration menu
//	private void buildEnvData() {
//		EnvConstuctor envConstuctor = new EnvConstuctor(getResources());
//		webserviceURL = envConstuctor.getWebServiceURL(WEBSERVICE_CONFIG_NAME);
//		
//	}
}