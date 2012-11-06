/*
 * ###
 * Xcodebuild Command-Line Wrapper
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.plugins.xcode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * APP deploy
 * @goal codevalidate
 */

public class CodeValidation extends AbstractXcodeMojo{

	private static final String SOURCE_BUILD = "/source/build";
	private static String MAKE_DIR_LOC = "do_not_checkin/static-analysis-report/";
	private static final String DO_NOT_CHECKIN = "/do_not_checkin";
	private static final String TARGET = "/target";
	private static final String report = "static-analysis-report";
	private static final String PACKAGING_XCODE_WORLSAPCE = "xcode-workspace";
	
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

	/**
	 * Build directory.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File buildDirectory;
	
	/**
	 * @parameter expression="${projectType}" default-value="xcode"
	 */
	private String projectType;
	
	int exitValue = 0; 
			
	File reportingDir =  null;
	
	public void execute() throws MojoExecutionException {		
		getLog().info("Code Validation started...");
		try {
			// For each target , we are creating separate folder
			MAKE_DIR_LOC = MAKE_DIR_LOC + scheme + File.separator + "make";
			getLog().info("make dir location ..." + MAKE_DIR_LOC);
			
			reportingDir = new File(baseDir, DO_NOT_CHECKIN + File.separator + report + File.separator + scheme);
			getLog().info("reportingDir location ..." + reportingDir.getAbsolutePath());
			
			//delete build dir created build
			getLog().info("Build directory deletion ...");
			File buildDir = new File(baseDir, SOURCE_BUILD);
			getLog().info("Build directory loc ..." + buildDir.getCanonicalPath());
			
			//delete target folder, which is inside do_not_checkin folder
			File donotCheckinTargetDir = new File(baseDir, DO_NOT_CHECKIN + TARGET);
			getLog().info("Target directory loc ..." + donotCheckinTargetDir.getCanonicalPath());
			if (donotCheckinTargetDir.exists()) {
				getLog().info("Target directory exist going to delete ..." + donotCheckinTargetDir.getCanonicalPath());
				FileUtils.deleteQuietly(donotCheckinTargetDir);
			}
			
			getLog().info("Do not check in report dir creation ...");
			if (!reportingDir.exists()) {
				getLog().info("Do not check in report dir created ...");
				reportingDir.mkdirs();
			}
			
			ProcessBuilder pb = new ProcessBuilder(check);
			// Include errors in output
			pb.redirectErrorStream(true);

			List<String> commands = pb.command();

			commands.add("-o");
			//specify the folder here to generate it... do not start with /
			commands.add(MAKE_DIR_LOC);
			commands.add("xcodebuild");
			
			if (PACKAGING_XCODE_WORLSAPCE.equals(projectType)) {
				commands.add("-scheme");
			} else {
				commands.add("-target");
			}
			commands.add(scheme);
			
			if (PACKAGING_XCODE_WORLSAPCE.equals(projectType)) {
				commands.add("-workspace");
			} else {
				commands.add("-project");
			}
			
			commands.add(xcodeProject);
			commands.add("build");
			
			commands.add("OBJROOT=" + buildDirectory);
			commands.add("SYMROOT=" + buildDirectory);
			commands.add("DSTROOT=" + buildDirectory);
			
			getLog().info("List of commands" + pb.command());
			// pb.command().add("install");
			pb.directory(new File(basedir));
			getLog().info("Code Validation basedir..." + new File(basedir).getCanonicalPath());
			Process child = pb.start();

			// Consume subprocess output and write to stdout for debugging
			InputStream is = new BufferedInputStream(child.getInputStream());
			int singleByte = 0;
			while ((singleByte = is.read()) != -1) {
				// output.write(buffer, 0, bytesRead);
				System.out.write(singleByte);
			}

			child.waitFor();
			exitValue = child.exitValue();
			getLog().info("Exit Value: " + exitValue);
			
			getLog().info("Cleaning existing files from dir to place newly generated files !!! ");
			if (reportingDir.exists()) {
				//clean all the file inside folder
				deleteFilesAlone(reportingDir);
			}
			
			if (exitValue != 0) {
				throw new MojoExecutionException("Compilation error occured. Resolve the error(s) and try again!");
			}

		} catch (IOException e) {
			getLog().error("An IOException occured.");
			throw new MojoExecutionException("An IOException occured", e);
		} catch (InterruptedException e) {
			getLog().error("The clean process was been interrupted.");
			throw new MojoExecutionException("The clean process was been interrupted", e);
		} catch (MojoExecutionException e) {
			getLog().error("Compilation error occured. Resolve the error(s) and try again!.");
			throw e;
		} finally {
			getLog().info("report creation started ");
			createreport(exitValue);
			getLog().info("Report generation completed!!!");
		}
	}


	private void createreport(int reportExitValue) throws MojoExecutionException {
		File makeReportDir = new File(baseDir, MAKE_DIR_LOC);
		File outputFile = getReport(makeReportDir);
		getLog().info("creating report for output file " + outputFile);
		
		// when there is no file inside make dir, it returns null
		if (outputFile == null && reportExitValue == 0) { // when report is not generated and code is zero for no violance project
			getLog().info("No issues found");
			generateNoViolationHtml(reportingDir);
		} else if (outputFile == null && reportExitValue != 0) { // when report is not generated and code is not zero, certificate file like issue occurs
			throw new MojoExecutionException("Report not generated");
		} else if (outputFile != null) { // when the report is generated, code value may be 0(phresco) or 65(walgreens).. Report is having issues
			try {
				if (outputFile.exists()) {
					File[] listFiles = outputFile.listFiles();
					getLog().info("No of Files to be copied " + listFiles.length);
				}
				FileUtils.copyDirectory(outputFile, reportingDir);
				FileUtils.deleteDirectory(makeReportDir); //delete make dir
			} catch (IOException e) {
				throw new MojoExecutionException("Error in writing output..." + e.getLocalizedMessage());
			}
		}	
	}

	private void deleteFilesAlone(File reportDir) {
		File[] listFiles = reportDir.listFiles();
		for (File repFile : listFiles) {
			if (repFile.isFile()) {
				repFile.delete();
			}
		}
	}
	
	private File getReport(File makeReportDir) {
		File[] files = makeReportDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.getName().endsWith("1")) {
				return file;
			}
		}
		return null;
	}
	
	private boolean generateNoViolationHtml(File reportFolder) {
		getLog().info("There is no code violation in project ");
		try {
			OutputStream fos = null;
			StringBuffer sb = new StringBuffer();
	        sb.append("<html>");
	        sb.append("<body>");
	        sb.append("<h1>"); 
	        sb.append(baseDir.getName() + "- scan-build results");
	        sb.append("</h1>");
	        sb.append("<table>");
	        sb.append("<tr>");
	        sb.append("<th>");
	        sb.append("Working Directory:");
	        sb.append("</th>");
	        sb.append("<td>");
	        sb.append(baseDir.getAbsolutePath());
	        sb.append("</td>");
	        sb.append("</tr>");
	        sb.append("</table>");
	        sb.append("<h2>Bug Summary</h2>");
	        sb.append("There are no violations in the code");
	        sb.append("<br/>");
	        sb.append("<p>Results in this analysis run are based on <b>checkers</b>.</p>");
	        sb.append("</body>");
	        sb.append("</html>");
	        String indexHtml = sb.toString();
	        
	        if(!reportFolder.exists()) {
	        	reportFolder.mkdirs();
	        }
	        File indexPath = new File(reportFolder, "index.html");
	        fos = new FileOutputStream(indexPath);
	        fos.write(indexHtml.getBytes());
		} catch (Exception e) {
			getLog().info("Report generation failure on index.html");
			return false;
		}
		return true;

	}
}	

