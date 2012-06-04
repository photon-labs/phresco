/*
 * ###
 * Xcodebuild Command-Line Wrapper
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
package com.photon.phresco.plugins.xcode.utils;

import java.io.*; 

public class SdkVerifier { 

	
	public static boolean isAvailable(String version) throws IOException, InterruptedException {
		Process p=Runtime.getRuntime().exec("xcodebuild -showsdks"); 
		p.waitFor(); 
		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
		String line=reader.readLine(); 
		while(line!=null) {
			if(line.contains(version)) {
				return true;
			}
			line=reader.readLine(); 
		} 
		return false;
	}
	
	public static StringBuffer listSdks() throws IOException, InterruptedException {

		Process p=Runtime.getRuntime().exec("xcodebuild -showsdks"); 
		p.waitFor(); 
		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
		StringBuffer buffer = new StringBuffer();

		String text = "";
		String aux = "";

		while ((aux = reader.readLine()) != null) {

			if(aux.contains("-sdk")) {
				String searchableString = aux;
				String keyword = "-sdk";
				int ind = searchableString.indexOf(keyword);
				String value = searchableString.substring(ind + 5);
				buffer.append(value);
			}

			text += aux;

		}           

		return buffer;

	} 

}