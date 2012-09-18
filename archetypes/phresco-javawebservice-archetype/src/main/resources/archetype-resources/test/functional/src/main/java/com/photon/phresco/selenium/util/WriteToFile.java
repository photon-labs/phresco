/*
 * ###
 * PHR_widget-hw
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
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WriteToFile {

	public static void writeToFile(String content) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File("./PgSrc.txt"));
			writer.println(content);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write output to file");
		}

	}

}
