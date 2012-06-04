package com.photon.phresco.logger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerTest {
	// Always use the classname, this way you can refactor
	private final static Logger LOGGER = Logger.getLogger(LoggerTest.class
			.getName());

	public void writeLog() {
		// Set the LogLevel to Severe, only severe Messages will be written
		LOGGER.setLevel(Level.SEVERE);
		LOGGER.severe("Info Log");
		LOGGER.warning("Info Log");
		LOGGER.info("Info Log");
		LOGGER.finest("Really not important");

		// Set the LogLevel to Info, severe, warning and info will be written
		// Finest is still not written
		LOGGER.setLevel(Level.INFO);
		LOGGER.severe("Info Log");
		LOGGER.warning("Info Log");
		LOGGER.info("Info Log");
		LOGGER.finest("Really not important");
	}

	public static void main(String[] args) {
		LoggerTest logger = new LoggerTest();
		try {
			PhrescoLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
		logger.writeLog();
	}
}
