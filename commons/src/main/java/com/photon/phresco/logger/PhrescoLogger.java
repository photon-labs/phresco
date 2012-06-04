package com.photon.phresco.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class PhrescoLogger {
	
	private PhrescoLogger(){
		//since this is utility class, objects need not created.
	}

	private static FileHandler fileTxt;
	private static SimpleFormatter formatterTxt;

	private static FileHandler fileHTML;
	private static Formatter formatterHTML;

	public static void setup() throws IOException {
		// Create Logger
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.INFO);
		fileTxt = new FileHandler("Logging.txt");
		fileHTML = new FileHandler("Logging.html");

		// Create txt Formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);

		// Create HTML Formatter
		formatterHTML = new CRMHtmlFormatter();
		fileHTML.setFormatter(formatterHTML);
		logger.addHandler(fileHTML);
	}
}
