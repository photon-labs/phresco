package com.photon.phresco.model;

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
