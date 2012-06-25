/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.commons;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Properties;

public class PluginProperties extends Properties {

	private static final long serialVersionUID = 1L;

	public void store(OutputStream paramOutputStream, String paramString)
			 throws IOException {
		OutputStreamWriter osw = null;
		BufferedWriter paramBufferedWriter = null;
		try {
			osw = new OutputStreamWriter(paramOutputStream);
			paramBufferedWriter = new BufferedWriter(osw);
			synchronized (this) {
				Enumeration localEnumeration = keys();
				while (localEnumeration.hasMoreElements()) {
					String str1 = (String) localEnumeration.nextElement();
					String str2 = (String) get(str1);
					paramBufferedWriter.write(str1 + "=" + str2);
					paramBufferedWriter.newLine();
				}
			}
			paramBufferedWriter.flush();
		} finally {
			if (osw != null) {
				osw.close();
			}
			if (paramBufferedWriter != null) {
				paramBufferedWriter.close();
			}
		}
	}
}
