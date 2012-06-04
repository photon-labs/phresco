/*
 * ###
 * drupal-maven-plugin Maven Mojo
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
package com.photon.phresco.plugins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryWalkListener;
import org.codehaus.plexus.util.DirectoryWalker;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;

import com.photon.phresco.util.PluginConstants;

/**
 * Goal which deploys the php project
 * 
 * @goal compile
 * @execute goal="clean"
 */
public class DrupalCompile extends AbstractMojo implements
		DirectoryWalkListener, PluginConstants {

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

	/**
	 * Path to the php executable.
	 * 
	 * @parameter expression="php"
	 * @required
	 */
	protected String phpExe;

	/**
	 * The php source folder.
	 * 
	 * @parameter expression="/source"
	 * @required
	 */
	protected String sourceDirectory;

	/**
	 * The php source folder.
	 * 
	 * @parameter expression="/do_not_checkin/target"
	 * @required
	 */
	protected String targetDirectory;

	private File srcDir;
	private ArrayList<Exception> compilerExceptions = new ArrayList<Exception>();
	private static final ArrayList<String> ERRORIDENTIFIERS = new ArrayList<String>();

	public void execute() throws MojoExecutionException {
		init();
		try {
			executeCompile();
		} catch (MultipleCompileException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
		handleProcesedFile();
	}

	private void init() throws MojoExecutionException {
		try {
			srcDir = new File(baseDir.getPath() + File.separator + sourceDirectory);
			
			if (!srcDir.exists()) {
				getLog().error(srcDir.getName() + " doesnot exists");
				throw new MojoExecutionException(srcDir.getName() + " doesnot exists");
			}

			fillErrorIdentifiers();

		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		} 
	}

	private void fillErrorIdentifiers() {
		ERRORIDENTIFIERS.add("Error");
		ERRORIDENTIFIERS.add("Parse error");
		ERRORIDENTIFIERS.add("Warning");
		ERRORIDENTIFIERS.add("Fatal error");
		ERRORIDENTIFIERS.add("Notice");
	}

	private void executeCompile() throws MultipleCompileException {
		// compile all the php files
		DirectoryWalker walker = new DirectoryWalker();
		getLog().info("Source Directory : " + srcDir.getPath());
		walker.setBaseDir(srcDir);
		walker.addDirectoryWalkListener(this);
		walker.addSCMExcludes();
		walker.scan();
		if (compilerExceptions.size() != 0) {
			throw new MultipleCompileException(compilerExceptions);
		}
	}

	@Override
	public void directoryWalkStarting(File paramFile) {
		debug("Start compiling source folder: " + baseDir.getAbsoluteFile());
	}

	@Override
	public void directoryWalkStep(int percentage, File file) {
		getLog().debug("percentage: " + percentage);
		try {
			if (file.isFile() && file.getName().endsWith(".php")) {
				compilePhpFile(file);
			}
		} catch (Exception e) {
			getLog().debug(e);
			compilerExceptions.add(e);
		}
	}

	@Override
	public void directoryWalkFinished() {
		debug("Compiling has finished.");
	}

	@Override
	public void debug(String message) {
		getLog().debug(message);
	}

	protected final void compilePhpFile(File file)
			throws DrupalCompileException, CommandLineException {
		String commandString = phpExe + " -d include_path=\""
				+ srcDir.getPath() + "\" \"" + file.getAbsolutePath() + "\"";

		getLog().debug("Executing the command : " + commandString);
		final StringBuffer bufferErrBuffer = new StringBuffer();
		final StringBuffer bufferOutBuffer = new StringBuffer();
		Commandline commandLine = new Commandline(commandString);

		CommandLineUtils.executeCommandLine(commandLine, new StreamConsumer() {
			public void consumeLine(String line) {
				getLog().debug("php.out: " + line);
				if (isError(line) == true) {
					bufferErrBuffer.append(line);
				}
				bufferOutBuffer.append(line);
			}
		}, new StreamConsumer() {
			public void consumeLine(String line) {
				getLog().debug("php.err: " + line);
				bufferErrBuffer.append(line);
			}
		});

		if (StringUtils.isNotEmpty(bufferErrBuffer.toString())) {
			getLog().debug(bufferErrBuffer.toString());
			throw new DrupalCompileException(commandString,
					DrupalCompileException.ERROR, file,
					bufferErrBuffer.toString());
		}
	}

	private boolean isError(String line) {
		line = line.trim();
		for (int i = 0; i < ERRORIDENTIFIERS.size(); i++) {
			if (line.startsWith((String) ERRORIDENTIFIERS.get(i) + ":")
					|| line.startsWith("<b>" + (String) ERRORIDENTIFIERS.get(i)
							+ "</b>:")) {
				return true;
			}
		}
		return false;
	}

	private void handleProcesedFile() throws MojoExecutionException {
		try {
			File targetDir = new File(baseDir + File.separator
					+ targetDirectory);
			FileUtils.copyDirectoryStructure(srcDir, targetDir);
		} catch (IOException e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

}