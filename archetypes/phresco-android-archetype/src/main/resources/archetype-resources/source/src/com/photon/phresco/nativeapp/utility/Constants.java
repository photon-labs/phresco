/*
 * ###
 * PHR_AndroidHybrid
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

/*
 * Classname: Constants
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.utility;


/**
 * Constants that will be used through out the device application
 * @author viral_b
 *
 */
public class Constants {
	// Constant variables

	protected Constants() {}

	public static final String APP_FOLDER_PATH = "/sdcard/phonegap/";
	public static final String LOG_FOLDER_PATH = Constants.APP_FOLDER_PATH + "log/";
	public static final String LOG_FILE = "PhoneGap.log";
	public static final String PHRESCO_ENV_CONFIG = "phresco-env-config.xml";

	private static String webContextURL = "";
	private static String homeURL = "";

	/**
	 * @return the webContextURL
	 */
	public static String getWebContextURL() {
		return webContextURL;
	}
	/**
	 * @param webContextURL the webContextURL to set
	 */
	public static void setWebContextURL(String webContextURL) {
		Constants.webContextURL = webContextURL;
	}
	/**
	 * @return the homeURL
	 */
	public static String getHomeURL() {
		return homeURL;
	}
	/**
	 * @param homeURL the homeURL to set
	 */
	public static void setHomeURL(String homeURL) {
		Constants.homeURL = homeURL;
	}
}