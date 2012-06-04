package com.photon.phresco.plugins.xcode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.photon.phresco.plugins.xcode.utils.CommandExecutor;
import com.photon.phresco.plugins.xcode.utils.ExecutionException;
import com.photon.phresco.plugins.xcode.utils.XcodeUtil;
import com.photon.phresco.util.FileUtil;

/**
 * APP deploy
 * @goal codevalidate
 */

public class CodeValidation extends AbstractXcodeMojo{

	private static final String DO_NOT_CHECKIN = "/do_not_checkin";

	/**
	 * @parameter  
	 */
	private String check = "scan-build";

	/**
	 * The java sources directory.
	 * 
	 * @parameter default-value="${project.basedir}"
	 * 
	 * @readonly
	 */
	protected File baseDir;

	/**
	 * Project Name
	 * 
	 * @parameter
	 */
	private String xcodeProject;

	/**
	 * @parameter expression="${scheme}" 
	 */
	private String scheme;

	public void execute() throws MojoExecutionException {		

		try {
			ProcessBuilder pb = new ProcessBuilder(check);
			// Include errors in output
			pb.redirectErrorStream(true);

			List<String> commands = pb.command();

			commands.add("-o");
			commands.add("make");
			commands.add("xcodebuild");
			commands.add("-scheme");
			commands.add(scheme);
			commands.add("-project");
			commands.add(xcodeProject);
			commands.add("build");
			getLog().info("List of commands" + pb.command());
			// pb.command().add("install");
			pb.directory(new File(basedir));
			Process child = pb.start();

			// Consume subprocess output and write to stdout for debugging
			InputStream is = new BufferedInputStream(child.getInputStream());
			int singleByte = 0;
			while ((singleByte = is.read()) != -1) {
				// output.write(buffer, 0, bytesRead);
				System.out.write(singleByte);
			}

			child.waitFor();
			int exitValue = child.exitValue();
			getLog().info("Exit Value: " + exitValue);
			if (exitValue != 0) {
				throw new MojoExecutionException("Compilation error occured. Resolve the error(s) and try again!");
			}

		} catch (IOException e) {
			getLog().error("An IOException occured.");
			throw new MojoExecutionException("An IOException occured", e);
		} catch (InterruptedException e) {
			getLog().error("The clean process was been interrupted.");
			throw new MojoExecutionException("The clean process was been interrupted", e);
		}
		createreport();
	}


	private void createreport() throws MojoExecutionException {
		File outputFile = getReport();
		if (outputFile == null) {
			throw new MojoExecutionException("Report not generated");
		} else {
			try {
				String report = "static-analysis-report";
				File baseFolder = new File(baseDir + DO_NOT_CHECKIN, report);
				if (!baseFolder.exists()) {
					baseFolder.mkdirs();
				}
				File destFile = new File(baseFolder, outputFile.getName());
				getLog().info("Destination file " + destFile.getAbsolutePath());
				File[] listFiles = outputFile.listFiles();
				for (File file : listFiles) {
					XcodeUtil.copyFiles(file, baseFolder);
				}
				//				XcodeUtil.copyFolder(outputFile, destFile);

				getLog().info("copied to..." + destFile.getName());
				//appFileName = destFile.getAbsolutePath();

				FileUtils.deleteDirectory(outputFile);
			} catch (IOException e) {
				throw new MojoExecutionException("Error in writing output..." + e.getLocalizedMessage());
			}
		}	

	}


	private File getReport() {
		File baseFolder = new File(baseDir, "make");
		File[] files = baseFolder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.getName().endsWith("1")) {
				return file;
			}
		}
		return null;
	}
}	

