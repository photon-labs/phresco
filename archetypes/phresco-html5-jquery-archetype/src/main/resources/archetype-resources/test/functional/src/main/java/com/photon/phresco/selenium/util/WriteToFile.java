package com.photon.phresco.selenium.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WriteToFile {

	public static void writeToFile(String content) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File("./PgSrc.txt"));
			writer.println(content);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write output to file");
		}

	}

}
