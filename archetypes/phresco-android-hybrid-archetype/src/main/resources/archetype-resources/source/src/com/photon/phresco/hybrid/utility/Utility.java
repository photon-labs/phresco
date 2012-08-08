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
 * Classname: Utility
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.hybrid.utility;

import java.io.File;

import android.os.Build;

import com.photon.phresco.hybrid.logger.PhrescoLogger;

/**
 * General purpose utility class
 *
 * @author viral_b
 *
 */
public class Utility {
	public static final String TAG = "Utility ********";

	protected Utility() {}

	/**
	 * Creates necessary directories on sdcard
	 */
	public static void createRequiredDirectory() {
		try {
			File f = new File(Constants.LOG_FOLDER_PATH);
			f.mkdirs();
			PhrescoLogger.info(TAG + "log/ Directory created: "
					+ Constants.LOG_FOLDER_PATH);

		} catch (Exception ex) {
			PhrescoLogger.info("createRequiredDirectory() : Exception : "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the directory
	 * @param dirName
	 */
	public static void createDirectory(File dirName) {
		try {
			dirName.mkdirs();
			PhrescoLogger.info(TAG + dirName + "/ Directory created: ");
		} catch (Exception ex) {
			PhrescoLogger.info("createDirectory() -  Exception:  "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Deletes all files and subdirectories under dir. Returns true if all
	 * deletions were successful. If a deletion fails, the method stops
	 * attempting to delete and returns false.
	 *
	 * @param file
	 * @return boolean
	 */

	public static boolean deleteDir(File f) {

		PhrescoLogger.info(TAG + "Inside deleteDir() : " + f.getName());

		if(f.exists() && f.isDirectory()) {
			String[] children = f.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(f, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return f.delete();
	}

	/**
	 * Deletes the log file from log directory on sdcard Returns, if log file is
	 * deleted successfully, false otherwise.
	 *
	 * @return boolean
	 */
	public static boolean deleteLogFile() {
		boolean flag = false;
		File logFile = new File(Constants.LOG_FOLDER_PATH + Constants.LOG_FILE);
		flag = logFile.delete();
		PhrescoLogger.info(TAG + Constants.LOG_FOLDER_PATH + Constants.LOG_FILE
				+ " file deleted = " + flag);
		File logFileLck = new File(Constants.LOG_FOLDER_PATH + Constants.LOG_FILE
				+ ".lck");
		flag = logFileLck.delete();
		PhrescoLogger.info(TAG + Constants.LOG_FOLDER_PATH + Constants.LOG_FILE
				+ ".lck file deleted = " + flag);
		return true;
	}

	/**
	 * Returns the device information
	 */
	public static void getDeviceInfo() {
		PhrescoLogger
				.info("----------------- DEVICE INFORMATION START ------------------------");
		PhrescoLogger.info("BOARD 			: " + Build.BOARD);
		// PhrescoLogger.info("BOOTLOADER 		: " + Build.BOOTLOADER);
		PhrescoLogger.info("BRAND 			: " + Build.BRAND);
		PhrescoLogger.info("CPU_ABI			: " + Build.CPU_ABI);
		// PhrescoLogger.info("CPU_ABI2		: " + Build.CPU_ABI2);
		PhrescoLogger.info("DEVICE 			: " + Build.DEVICE);
		PhrescoLogger.info("DISPLAY 		: " + Build.DISPLAY);
		PhrescoLogger.info("FINGERPRINT 	: " + Build.FINGERPRINT);
		// PhrescoLogger.info("HARDWARE		: " + Build.HARDWARE);
		PhrescoLogger.info("HOST 			: " + Build.HOST);
		PhrescoLogger.info("ID 				: " + Build.ID);
		PhrescoLogger.info("MANUFACTURER	: " + Build.MANUFACTURER);
		PhrescoLogger.info("MODEL 			: " + Build.MODEL);
		PhrescoLogger.info("PRODUCT 		: " + Build.PRODUCT);
		// PhrescoLogger.info("RADIO	 		: " + Build.RADIO);
		// PhrescoLogger.info("SERIAL	 		: " + Build.SERIAL);
		PhrescoLogger.info("TAGS 			: " + Build.TAGS);
		PhrescoLogger.info("TIME 			: " + Build.TIME);
		PhrescoLogger.info("TYPE 			: " + Build.TYPE);
		PhrescoLogger.info("UNKNOWN			: " + Build.UNKNOWN);
		PhrescoLogger.info("USER 			: " + Build.USER);
		PhrescoLogger.info("VERSION CODENAME: " + Build.VERSION.CODENAME);
		PhrescoLogger
				.info("----------------- DEVICE INFORMATION END --------------------------");
	}

}
