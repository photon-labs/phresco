package com.photon.phresco.selenium.util;

import java.io.File;

public class GetCurrentDir {

	public static String targetDirectory = "/target" + "/surefire-reports" + "/ScreenShots";
	public static String currentdir;
	public static String screenShotFolder;
	public static File fileName;
	public static String targetPath;

	public static String getCurrentDirectory() throws Exception {
		currentdir = System.getProperty("user.dir");
		screenShotFolder = currentdir + targetDirectory;
		fileName = new File(screenShotFolder);
		if(fileName.exists()){
			System.out.println("folder structure "+fileName+"exists");	
			}else{
				fileName.mkdir();
				System.out.println("folder structure "+fileName+"created");
			}
		targetPath = fileName.getAbsolutePath();
		return targetPath;
	}

}