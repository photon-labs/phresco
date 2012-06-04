package com.photon.phresco.plugins;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.util.PluginConstants;

/**
 * Goal which builds the Java WebApp
 * 
 * @goal stop
 * 
 */

public class NodeJSStop extends AbstractMojo implements PluginConstants {

	/**
	 * @parameter expression="${project.basedir}/source" required="true"
	 * @readonly
	 */
	protected File sourceDirectory;

	/**
	 * The Maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * @parameter expression="${project.basedir}" required="true"
	 * @readonly
	 */
	protected File baseDir;

	public void execute() throws MojoExecutionException, MojoFailureException {

		stopNodeJS();

	}

	private void stopNodeJS() throws MojoExecutionException {
		ByteArrayInputStream is = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		FileWriter fileWriter = null;
		String result = "";
		if (System.getProperty(NODE_OS_NAME).startsWith(NODE_WINDOWS)) {
			stopNodeJSInWindows();
		} else if (System.getProperty(NODE_OS_NAME).startsWith("Mac")) {
			stopNodeJSInMac();
		} else {
			stopNodeJSInUnix();
		}
		result = "Server Stopped Successfully...";
		is = new ByteArrayInputStream(result.getBytes());
		isr = new InputStreamReader(is);
		reader = new BufferedReader(isr);
		try {
			fileWriter = new FileWriter(baseDir.getPath() + NODE_LOG_FILE_DIRECTORY + NODE_LOG_FILE, false);
			LogWriter writer = new LogWriter();
			writer.writeLog(reader, fileWriter);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (Exception e) {
				throw new MojoExecutionException(e.getMessage());
			}
		}
	}

	private void stopNodeJSInWindows() throws MojoExecutionException {
		try {
			Process p = Runtime.getRuntime().exec("cmd /X /C taskkill /F /IM node.exe");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
	
	private void stopNodeJSInMac() throws MojoExecutionException {
		try {
			Commandline cl = new Commandline("killall");
			String[] args1 = { "node" };
			cl.addArguments(args1);
			cl.setWorkingDirectory(sourceDirectory);
			Process execute;
			execute = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(execute.getErrorStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

	private void stopNodeJSInUnix() throws MojoExecutionException {
		try {
			Commandline cl = new Commandline(NODE_UNIX_PROCESS_KILL_CMD);
			String[] args1 = { "node" };
			cl.addArguments(args1);
			cl.setWorkingDirectory(sourceDirectory);
			Process execute;
			execute = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(execute.getErrorStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}

		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
	
}
