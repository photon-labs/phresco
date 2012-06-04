package com.photon.phresco.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/*the class CommonsVersion*/
public class CommonsVersion {
	/* reads the version of corresponding jarfile */
	public static String getManifestInfo() {
		@SuppressWarnings("rawtypes")
		Enumeration resEnum;
		try {
			resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
			while (resEnum.hasMoreElements()) {
				try {
					URL url = (URL) resEnum.nextElement();
					InputStream is = url.openStream();
					if (is != null) {
						Manifest manifest = new Manifest(is);
						Attributes mainAttribs = manifest.getMainAttributes();
						String version = mainAttribs.getValue("Implementation-Version");
						if (version != null) {
							return version;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {

		}
		return null;
	}

	/* prints the implementation version from manifest info */
	public static void main(String[] args) {
		System.out.println("getManifestInfo() " + getManifestInfo());
	}
}
