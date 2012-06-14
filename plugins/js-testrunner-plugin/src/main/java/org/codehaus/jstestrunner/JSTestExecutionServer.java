/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package org.codehaus.jstestrunner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

/**
 * An execution server executes JavaScript tests represented by a list of urls.
 */
public class JSTestExecutionServer implements TestResultProducer {

	private static Logger logger = Logger.getLogger(JSTestExecutionServer.class
			.getName());

	/**
	 * Copy the test runner we have to the test folder and make it available to
	 * multiple invocations of the runner.
	 */
	private File testRunnerFile;
	private final Lock testRunnerFileLock = new ReentrantLock();

	/**
	 * The location of the test runner file that the execution will use.
	 */
	private String testRunnerFilePath;

	/**
	 * The formatted command to perform.
	 */
	private String commandPattern;

	/**
	 * The process that was started.
	 */
	private Process process;

	/**
	 * The urls relating to the test.
	 */
	private List<URL> urls;
	
	/**
	 * The logger attached to the process being run.
	 */
	private ProcessLogger processLogger;

	/**
	 * Make a copy of the js file that will drive the execution engine. Copies
	 * are performed in a synchronised fashion and only if it has not been
	 * copied before.
	 * 
	 * @throws IOException
	 *             if there is a problem copying the file.
	 */
	protected void copyTestRunnerFileIfNotExists() throws IOException {

		assert testRunnerFilePath != null;

		String testRunnerFilename = "run-qunit.js";

		testRunnerFileLock.lock();
		try {
			testRunnerFile = new File(testRunnerFilePath, testRunnerFilename);
			if (!testRunnerFile.exists()) {
				// Create any intermediate folders and create the file.
				new File(testRunnerFilePath).mkdirs();
				testRunnerFile.createNewFile();

				// Provide an absolute path to the script that actually runs the
				// test on the test executor.
				InputStream is = JSTestExecutionServer.class
						.getResourceAsStream("/org/codehaus/jstestrunner/"
								+ testRunnerFilename);
				assert is != null;

				BufferedInputStream bis = new BufferedInputStream(is);
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(testRunnerFile));
					try {
						int c;
						while ((c = bis.read()) != -1) {
							bos.write(c);
						}
					} finally {
						bos.close();
					}
				} finally {
					bis.close();
				}
			}
		} finally {
			testRunnerFileLock.unlock();
		}
	}

	/**
	 * Get the command string to use.
	 * 
	 * @return the command string as an array of args.
	 * @throws IOException
	 *             if the target bootstrap js file that drives the tests cannot
	 *             be created.
	 */
	protected String[] getCommandArgs() throws IOException {

		assert commandPattern != null && testRunnerFile != null && urls != null;

		// Parse the command pattern and break it out into a list of args taking
		// into consideration anything within double quote chars.

		List<String> args = new ArrayList<String>();
		StringReader s = new StringReader(commandPattern);
		int c;
		StringBuilder sb = new StringBuilder();
		boolean inDoubleQuotes = false;
		boolean inSingleQuotes = false;
		while ((c = s.read()) != -1) {
			if (c == '"' && !inSingleQuotes) {
				if (inDoubleQuotes) {
					args.add(sb.toString());
					sb = new StringBuilder();
				}
				inDoubleQuotes = !inDoubleQuotes;
			} else if (c == '\'' && !inDoubleQuotes) {
				if (inSingleQuotes) {
					args.add(sb.toString());
					sb = new StringBuilder();
				}
				inSingleQuotes = !inSingleQuotes;
			} else if (!inDoubleQuotes && !inSingleQuotes) {
				if (c == ' ') {
					if (sb.length() > 0) {
						args.add(sb.toString());
						sb = new StringBuilder();
					}
				} else {
					sb.append((char) c);
				}
			} else {
				// In quotes
				sb.append((char) c);
			}
		}
		if (sb.length() > 0) {
			args.add(sb.toString());
		}

		// Put the test runner file into a format for formatting.
		String testRunnerAbsoluteFilePath = testRunnerFile.getAbsolutePath();

		// Convert the list of urls to a command line representation (csv).
		sb = new StringBuilder();
		for (URL url : urls) {
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append(url.toString());
		}
		String testUrls = sb.toString();

		// Format each argument.
		String[] formattedArgs = new String[args.size()];
		int i = 0;
		for (String arg : args) {
			formattedArgs[i++] = String.format(arg, testRunnerAbsoluteFilePath,
					testUrls);
		}

		if (logger.isLoggable(Level.FINEST)) {
			logger.log(Level.FINEST,
					"Args to use: " + Arrays.toString(formattedArgs));
		}

		return formattedArgs;
	}

	public String getCommandPattern() {
		return commandPattern;
	}

	public String getTestRunnerFilePath() {
		return testRunnerFilePath;
	}

	public List<URL> getUrls() {
		return urls;
	}

	/**
	 * Tests whether the executor is still running.
	 * 
	 * @return true if it is.
	 */
	public boolean isAvailable() {
		if (process != null) {
			try {
				process.exitValue();
				return false;
			} catch (IllegalThreadStateException e) {
				return true;
			}
		} else {
			return false;
		}
	}

	@Resource
	public void setCommandPattern(String commandPattern) {
		this.commandPattern = commandPattern;
	}

	@Resource
	public void setTestRunnerFilePath(String testRunnerFilePath) {
		this.testRunnerFilePath = testRunnerFilePath;
	}

	@Resource
	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}

	/**
	 * Start the execution.
	 * 
	 * @throws IOException
	 *             if something goes wrong.
	 */
	public void start() throws IOException {			
		// Ensure that the bootstrap file is available for execution from the
		// file system.
		copyTestRunnerFileIfNotExists();
		
		// Get the command args and execute them, merging STDOUT and STDERR
		ProcessBuilder builder = new ProcessBuilder(getCommandArgs());
		builder.redirectErrorStream(true);
		try {
			process = builder.start();
		} catch (IOException e) {
			throw new IOException(
			"The phantomjs executable cannot be launched from the path or from the value of"
					+ " the org.codehaus.jstestrunner.commandPattern property."
					+ " See http://js-testrunner.codehaus.org/usage.html for instructions."
					+ " Original exception: " + e.toString());
		}

		// Use a ProcessLogger to print all output to System.out
		processLogger = new ProcessLogger(process);
		processLogger.start();
	}

	/**
	 * Stop the execution.
	 */
	public void stop() {
		if (process != null) {
			process.destroy();
			
			// Wait for the process to exit; if it's the last process we want the
			// ProcessLogger to have a chance to log the exit value, if we are in
			// FINE level
			if (logger.isLoggable(Level.FINE) && processLogger.isAlive()) {
				try {
					processLogger.join();
				} catch (InterruptedException e) {
					logger.log(Level.WARNING, "Exception waiting for process logger to exit: " + e.toString());
				}
			}
		}
	}

}
