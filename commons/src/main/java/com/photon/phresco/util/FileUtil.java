package com.photon.phresco.util;

import java.io.File;

public final class FileUtil {
	
	private FileUtil(){
		
	}
	public static boolean delete(File file) {
		boolean deleteStatus = false;
		if (file.isDirectory()) {
			deleteStatus = deleteDir(file);
		} else {
			deleteStatus = file.delete();
		}
		return deleteStatus;
	}

	private static boolean deleteDir(File dir) {
		if (dir!= null && dir.isDirectory() && dir.exists()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

}
