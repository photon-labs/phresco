package com.photon.phresco.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;

public final class Utility implements Constants {

    private static String systemTemp = null;

    private Utility(){
        //prevent instantiation outside
    }
    public static boolean isEmpty(String str) {
//		return (str == null || str.trim().length() == 0);
        return StringUtils.isEmpty(str);
    }

    /**
     * Closes inputsrteam.
     * @param inputStream
     */
    public static void closeStream(InputStream inputStream){
        try {
            if(inputStream!=null){
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //FIXME: should be logged.
        }
    }


    /**
     * Closes inputsrteam.
     * @param inputStream
     */
    public static void closeStream(Reader inputStream){
        try {
            if(inputStream!=null){
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //FIXME: should be logged.
        }
    }

    /**
     * Closes output streams
     * @param outputStream
     */
    public static void closeStream(OutputStream outputStream){
        try {
            if(outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //FIXME: should be logged.
        }
    }

    public static String getPhrescoHome() {
        String phrescoHome = System.getenv(PHRESCO_HOME);
        if (phrescoHome == null) {
            phrescoHome = System.getProperty(USER_HOME);
        }
        StringBuilder sb = new StringBuilder(); 
		sb.append(File.separator);
		sb.append("bin");
		sb.append(File.separator);
		sb.append("..");
		int index = phrescoHome.lastIndexOf(sb.toString());
		if (index != -1) {
			phrescoHome = phrescoHome.substring(0, index);
		}
        FileUtils.mkdir(phrescoHome);
        return phrescoHome;
    }

    public static String getProjectHome() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(PROJECTS_HOME);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getPhrescoTemp() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(PROJECTS_TEMP);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getArchiveHome() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(ARCHIVE_HOME);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getSystemTemp() {
        if (systemTemp == null) {
            systemTemp = System.getProperty(JAVA_TMP_DIR);
        }

        return systemTemp;
    }
    
    public static String getJenkinsHome() {
    	String phrescoHome = Utility.getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(Constants.PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(TOOLS_DIR);
        builder.append(File.separator);
        builder.append(JENKINS_DIR);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }
    
    public static String getJenkinsHomePluginDir() {
    	String jenkinsDataHome = System.getenv(JENKINS_HOME);
        StringBuilder builder = new StringBuilder(jenkinsDataHome);
        builder.append(File.separator);
        builder.append(PLUGIN_DIR);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }
    
}
