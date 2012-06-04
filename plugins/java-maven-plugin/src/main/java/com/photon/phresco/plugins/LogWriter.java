package com.photon.phresco.plugins;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {

	public void writeLog(BufferedReader in, FileWriter fstream) {
		String line = null;
		BufferedWriter out = new BufferedWriter(fstream);
		try {
			while ((line = in.readLine()) != null) {
				out.write(line + "\n");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

