package com.photon.phresco.framework.commons.filter;

import java.io.File;
import java.io.FilenameFilter;

public class FileListFilter implements FilenameFilter {

	private String name;
	private String extension;

	public FileListFilter(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}

	public boolean accept(File directory, String filename) {
		boolean fileOK = true;

		if (name != null) {
			fileOK &= filename.startsWith(name);
		}

		if (extension != null) {
			fileOK &= filename.endsWith('.' + extension);
		}
		return fileOK;
	}
}
