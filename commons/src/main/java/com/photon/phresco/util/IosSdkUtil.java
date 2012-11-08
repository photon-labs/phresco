/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class IosSdkUtil {

	private static final String SDK = "-sdk";
	private static final String XCODEBUILD_SHOWSDKS = "xcodebuild -showsdks";

	// types to be passed
	public enum MacSdkType { macosx, iphoneos, iphonesimulator };
	
	public static List<String> getMacSdks(MacSdkType type) {
		List<String> sdks = new ArrayList<String>();
		try {
			Process p=Runtime.getRuntime().exec(XCODEBUILD_SHOWSDKS); 
			p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			// List of sdks
			
			String text = "";
			String aux = "";
			while ((aux = reader.readLine()) != null) {
				if (aux.contains(SDK)) {
					String searchableString = aux;
					String keyword = SDK;
					int ind = searchableString.indexOf(keyword);
					String sdk = searchableString.substring(ind + 5);
					if (sdk.contains(type.toString())) {
						  sdks.add(sdk);
					} 
				}
				text += aux;
			}
		} catch(Exception e) {
			return sdks;
		}
		return Lists.reverse(sdks);
	}

	public static List<String> getMacSdksVersions(MacSdkType type) {
		List<String> sdks = null;
		try {
			Process p=Runtime.getRuntime().exec(XCODEBUILD_SHOWSDKS); 
			p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			// List of sdks
			sdks = new ArrayList<String>();
			String text = "";
			String aux = "";
			while ((aux = reader.readLine()) != null) {
				if (aux.contains(SDK)) {
					String searchableString = aux;
					String keyword = SDK;
					int ind = searchableString.indexOf(keyword);
					String sdk = searchableString.substring(ind + 5);
					if (sdk.contains(type.toString())) {
						Pattern pattern = Pattern.compile("[0-9]+.[0-9]*|[0-9]+");
						Matcher m = pattern.matcher(sdk);
						while (m.find()) {
						  sdks.add(m.group());
						}
					} 
				}
				text += aux;
			}
		} catch(Exception e) {
			return sdks;
		}
		return sdks;
	}
	
	public static boolean isAvailable(String sdk, MacSdkType type) {
		try {
			List<String> macSdks = getMacSdks(type);
			if (macSdks.contains(sdk)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
