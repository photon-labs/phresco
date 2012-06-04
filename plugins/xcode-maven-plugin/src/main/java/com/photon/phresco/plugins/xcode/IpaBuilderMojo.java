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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.zip.ZipArchiver;

import com.photon.phresco.plugins.xcode.utils.CommandExecutor;
import com.photon.phresco.plugins.xcode.utils.ExecutionException;

/**
 * Package IPA File
 * 
 * @goal ipaBuilder
 * @phase package
 * @requiresDependencyResolution compile
 */
public class IpaBuilderMojo extends AbstractXcodeMojo {
	/**
	 * @parameter expression="${app.path}"
	 */
	private String appFileName;

	/**
	 * @parameter expression="${build.name}"
	 */
	private String buildname;

	/**
	 * The java sources directory.
	 * 
	 * @parameter default-value="${project.basedir}"
	 * 
	 * @readonly
	 */
	protected File baseDir;
	private static final String DO_NOT_CHECKIN_BUILD = "/do_not_checkin/build";
	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * Execute the ipa package.
	 */
	public void execute() throws MojoExecutionException {
		if (!xcrunCommandLine.exists()) {
			throw new MojoExecutionException("Invalid path for xcrun: "
					+ xcrunCommandLine.getAbsolutePath());
		}
		if (appName == null) {
			throw new MojoExecutionException("AppName must be defined.");
		}

		CommandExecutor executor = CommandExecutor.Factory
				.createDefaultCommmandExecutor();
		executor.setLogger(this.getLog());
		List<String> commands = new ArrayList<String>();
		commands.add("-sdk");
		commands.add("iphoneos");
		commands.add("PackageApplication");
		commands.add("-v");
		commands.add(appFileName);
		commands.add("-o");
		File appDirector = new File(appFileName).getParentFile();
		File ipaFile = new File(appDirector, appName + ".ipa");
		commands.add(ipaFile.getAbsolutePath());

		getLog().info(
				xcrunCommandLine.getAbsolutePath() + " " + commands.toString());

		try {
			executor.executeCommand(xcrunCommandLine.getAbsolutePath(),
					commands, true);

		} catch (ExecutionException e) {
			getLog().error(executor.getStandardOut());
			getLog().error(executor.getStandardError());
			throw new MojoExecutionException("Error while executing: ", e);
		}

		File baseFolder = new File(baseDir + DO_NOT_CHECKIN_BUILD, buildname);

		File[] listFiles = baseFolder.listFiles();
		File dSYM = null;
		for (File file2 : listFiles) {
			String path = file2.getPath();
			if (path.endsWith(".app.dSYM")) {
				dSYM = new File(path);
			}
		}

		ZipArchiver zipArchiver = new ZipArchiver();
		zipArchiver.addDirectory(dSYM);
		File deliverableZip = new File(baseDir + DO_NOT_CHECKIN_BUILD
				+ File.separator + buildname, appName + ".app.dSYM.zip");
		zipArchiver.setDestFile(deliverableZip);
		try {
			zipArchiver.createArchive();
		} catch (ArchiverException e) {
			getLog().error("Error in creating archive ", e);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
