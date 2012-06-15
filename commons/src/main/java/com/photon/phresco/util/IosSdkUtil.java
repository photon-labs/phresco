package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IosSdkUtil {

	// types to be passed
	public enum MacSdkType { macosx, iphoneos, iphonesimulator };
	
	public static List<String> getMacSdks(MacSdkType type)  throws Exception {
		List<String> sdks = null;
		try {
			Process p=Runtime.getRuntime().exec("xcodebuild -showsdks"); 
			p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			// List of sdks
			sdks = new ArrayList<String>();
			String text = "";
			String aux = "";
			while ((aux = reader.readLine()) != null) {
				if (aux.contains("-sdk")) {
					String searchableString = aux;
					String keyword = "-sdk";
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
		return sdks;
	}

	public static List<String> getMacSdksVersions(MacSdkType type)  throws Exception {
		List<String> sdks = null;
		try {
			Process p=Runtime.getRuntime().exec("xcodebuild -showsdks"); 
			p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			// List of sdks
			sdks = new ArrayList<String>();
			String text = "";
			String aux = "";
			while ((aux = reader.readLine()) != null) {
				if (aux.contains("-sdk")) {
					String searchableString = aux;
					String keyword = "-sdk";
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
}
