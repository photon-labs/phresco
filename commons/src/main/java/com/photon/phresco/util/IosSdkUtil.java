package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.IOException;
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

	public static List<String> getMacSdks(MacSdkType type)  throws IOException {
		List<String> sdks = new ArrayList<String>();
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

	public static List<String> getMacSdksVersions(MacSdkType type)  throws IOException {
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
}
