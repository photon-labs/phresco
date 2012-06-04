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