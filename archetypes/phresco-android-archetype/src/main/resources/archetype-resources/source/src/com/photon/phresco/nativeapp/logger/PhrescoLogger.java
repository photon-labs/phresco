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
 * Classname: PhrescoLogger
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.photon.phresco.nativeapp.utility.Constants;

/**
 * Base class to create the application logs
 * 
 * @author viral_b
 * 
 */
public class PhrescoLogger {

	private static Logger logger = Logger.getLogger("Phresco_R2");
	private static FileHandler fh;

	private static String exception = "exception";

	protected PhrescoLogger() {
	}

	static {
		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler(Constants.LOG_FOLDER_PATH + Constants.LOG_FILE, true);
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException e) {
			logger.log(Level.WARNING, exception, e);
		} catch (IOException e) {
			logger.log(Level.WARNING, exception, e);
		}
	}

	/**
	 * Logs a message of the specified level with the supplied Throwable object.
	 * The message is then transmitted to all subscribed handlers.
	 * 
	 * @param l
	 * @param str
	 * @param thrown
	 */
	public static void log(Level l, String str, Throwable thrown) {
		try {
			logger.log(l, str, thrown);
		} catch (Exception ex) {
			logger.log(l, exception, ex);
		}
	}

	/**
	 * Logs a message of the specified level. The message is transmitted to all
	 * subscribed handlers.
	 * 
	 * @param l
	 * @param str
	 */
	public static void log(Level l, String str) {
		try {
			logger.log(l, str);
		} catch (Exception ex) {
			logger.log(l, exception, ex);
		}
	}

	/**
	 * Logs a message of level Level.INFO; the message is transmitted to all
	 * subscribed handlers.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		try {
			logger.info(msg);
		} catch (Exception ex) {
			logger.log(Level.WARNING, exception, ex);
		}
	}

	/**
	 * Logs a message of the WARNING level with the supplied Throwable object.
	 * The message is then transmitted to all subscribed handlers.
	 * 
	 * @param th
	 */
	public static void warning(Throwable th) {
		try {
			logger.log(Level.WARNING, ": exception", th);
		} catch (Exception ex) {
			logger.log(Level.WARNING, ": exception", ex);
		}
	}
}