/*
 * ###
 * drupal-maven-plugin Maven Mojo
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
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


