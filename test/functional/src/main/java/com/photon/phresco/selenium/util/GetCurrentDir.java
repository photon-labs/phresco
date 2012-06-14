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