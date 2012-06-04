package com.photon.phresco.plugins;

import java.io.File;

public class DrupalCompileException extends Exception {

	private static final long serialVersionUID = 1L;
	private final File phpFile;
	private final String phpErrorMessage;
	public static final int ERROR = 0;
	public static final int WARNING = 1;
	private final String commandString;

	public DrupalCompileException(String commandString, int errorType,
			File phpFile, String phpErrorMessage) {
		this.commandString = commandString;
		this.phpFile = phpFile;
		this.phpErrorMessage = phpErrorMessage;
	}

	public String getMessage() {
		return "\n" + phpErrorMessage + " in\nFile:\n "
				+ phpFile.toString() + "\nCommand:\n " + commandString
				+ "+\n";
	}
}


