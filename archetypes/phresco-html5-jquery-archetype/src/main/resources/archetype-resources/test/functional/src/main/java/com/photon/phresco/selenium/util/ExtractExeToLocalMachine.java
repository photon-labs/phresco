package com.photon.phresco.selenium.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ExtractExeToLocalMachine {

	public static void main(String[] args) throws Exception {
		extractFileToFolder("chooseFile.exe", "C:/Windows/system32");
	}

	public static File extractToTempDir(final String filename) {
		final String tmpDir = System.getProperty("java.io.tmpdir");
		if (tmpDir == null) {
			throw new RuntimeException("java.io.tmpdir is null");
		}
		final File file = new File(tmpDir, filename);
		if (file.exists()) {
			file.delete();
		}
		try {
			extractFileToFolder(filename, tmpDir);
		} catch (Throwable t) {
			throw new RuntimeException("Failed to extract " + filename + " to "
					+ tmpDir, t);
		}
		return file;
	}

	public static void extractFileToFolder(String fileToExtract,
			String targetFolder) throws URISyntaxException, ZipException,
			IOException {
		final URI jarURI;
		final URI exe;
		
		jarURI = getJarURI();
		exe = getFile(jarURI, fileToExtract, targetFolder);
		System.out.println(exe);
	}

	private static URI getJarURI() throws URISyntaxException {
		final ProtectionDomain domain;
		final CodeSource source;
		final URL url;
		final URI uri;

		domain = ExtractExeToLocalMachine.class.getProtectionDomain();
		source = domain.getCodeSource();
		url = source.getLocation();
		uri = url.toURI();
		return (uri);
	}

	private static URI getFile(final URI jarURI, final String fileName,
			final String targetFolder) throws ZipException, IOException {
		final File location;
		final URI fileURI;

		location = new File(jarURI);

		// not in a JAR, just return the path on disk
		if (location.isDirectory()) {
			fileURI = URI.create(jarURI.toString() + fileName);
		} else {
			final ZipFile zipFile;
			zipFile = new ZipFile(location);
			try {
				fileURI = extract(zipFile, fileName, targetFolder);
			} finally {
				zipFile.close();
			}
		}

		return (fileURI);
	}

	public static URI extract(final ZipFile zipFile, final String fileName,
			final String targetFolder) throws IOException {
		final File tempFile;
		final ZipEntry entry;
		final InputStream zipStream;
		OutputStream fileStream;

		tempFile = new File(targetFolder + File.separator + fileName);
		// tempFile.deleteOnExit();
		entry = zipFile.getEntry(fileName);

		if (entry == null) {
			throw new FileNotFoundException("cannot find file: " + fileName
					+ " in archive: " + zipFile.getName());
		}

		zipStream = zipFile.getInputStream(entry);
		fileStream = null;

		try {
			final byte[] buf;
			int i;

			fileStream = new FileOutputStream(tempFile);
			buf = new byte[1024];
			i = 0;

			while ((i = zipStream.read(buf)) != -1) {
				fileStream.write(buf, 0, i);
			}
		} finally {
			close(zipStream);
			close(fileStream);
		}

		return (tempFile.toURI());
	}

	private static void close(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}