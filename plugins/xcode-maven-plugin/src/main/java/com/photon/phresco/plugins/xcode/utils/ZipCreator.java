package com.photon.phresco.plugins.xcode.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author arunachalam
 *
 */
public class ZipCreator {
    File baseDir;

    public ZipCreator(File baseDir) {
        this.baseDir = baseDir;
    }

    public void createZipFromDirectory(File sourceDirectory, File outputPath) throws IOException {

        zipDir(outputPath.getAbsolutePath(), sourceDirectory.getPath());
    }

    private void zipDir(String zipFileName, String dir) throws IOException {
        File dirObj = new File(dir);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        addDir(dirObj, out);
        out.close();
    }

    private void addDir(File dirObj, ZipOutputStream out) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[1024];

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addDir(files[i], out);
                continue;
            }
            FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
            out.putNextEntry(new ZipEntry(files[i].getAbsolutePath().substring(baseDir.getAbsolutePath().length())));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
    }
}