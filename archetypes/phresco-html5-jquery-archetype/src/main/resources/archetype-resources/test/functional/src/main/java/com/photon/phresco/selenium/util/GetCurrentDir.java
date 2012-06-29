/*
 * ###
 * PHR_jquery-widget-hw
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
package com.photon.phresco.selenium.util;

import java.io.File;

public class GetCurrentDir {

	public static String targetDirectory = "/target" + "/screenshots";
	public static String currentdir;
	public static String screenShotFolder;
	public static File fileName;
	public static String targetPath;

	public static String getCurrentDirectory() throws Exception {
		currentdir = System.getProperty("user.dir");
		screenShotFolder = currentdir + targetDirectory;
		fileName = new File(screenShotFolder);
		if (fileName.mkdir()) {
			System.out.println("--------------Target Folder created--------");
		} else {
			System.out.println("Target Folder is not created");
			throw new ScreenException("Folder not created");
		}
		targetPath = fileName.getAbsolutePath();
		return targetPath;
	}

}