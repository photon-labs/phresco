package com.photon.phresco.plugins;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import com.photon.phresco.plugin.commons.PluginConstants;
import com.photon.phresco.plugin.commons.PluginUtils;


/**
 * Goal which Starts Selenium Web Driver
 *
 * @goal startwebdriver
 * 
 */
public class PhpWebDriverStart extends AbstractMojo implements  PluginConstants {

	/**
	 * @parameter expression="${project.basedir}" required="true"
	 * @readonly
	 */
	protected File baseDir;

	public void execute() throws MojoExecutionException {
		jarExecution();
	}

	private void jarExecution() throws MojoExecutionException {
		try {
			String jarFileName = "";
			File jarFilePath = new File(baseDir.getPath() + JAR_PATH);
			File[] listOfFiles = jarFilePath.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					jarFileName = listOfFiles[i].getName();
				}
			}
			getLog().info("Starting Selenium Server....");
			ProcessBuilder pb = new ProcessBuilder();
			pb.redirectErrorStream(true);
			List<String> commands = pb.command();
			commands.add(JAVA_CMD);
			commands.add(JAVA_JAR_CMD);
			commands.add(jarFileName);
			pb.directory(jarFilePath);
			Process process = pb.start();
			getLog().info("Selenium Server started...");
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}




