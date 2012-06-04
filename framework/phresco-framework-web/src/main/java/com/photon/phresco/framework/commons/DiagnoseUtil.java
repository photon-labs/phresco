package com.photon.phresco.framework.commons;

import java.net.URL;
import java.net.URLConnection;

public class DiagnoseUtil {

	public static boolean isConnectionAlive(String protocol, String host, int port) {
		boolean isAlive = true;
		try {
			URL url = new URL(protocol, host, port, "");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			isAlive = false;
		}
		return isAlive;
	}
}
