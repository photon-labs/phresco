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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProcessLogger; handles output from a process and either discards it or
 * logs it to the supplied logger. This is used to interact with the test runner
 * processes.
 * 
 * @author Ben Jones
 */
public class ProcessLogger extends Thread {
	/** The ProcessLogger Logger for internal log statements */
	private static Logger logger = Logger.getLogger(ProcessLogger.class.getName());
	/** The process being monitored */
	private Process process;

	/**
	 * Construct a ProcessLogger to handle output from a process. 
	 * @param process The process to handle output for.
	 */
	ProcessLogger(Process process) {
		this.process = process;
	}

	/**
	 * Thread.run method which causes the ProcessLogger to read all lines from 
	 * process.getInputStream() and log them.
	 */
	public void run() {		
		InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		try {
			String line;
			// Read every line of input
			while ((line = bufferedReader.readLine()) != null) {
				// Print all output to the console
				System.out.println(line);
			}			
			
		} catch (IOException e) {
			logger.log(Level.WARNING, e.toString());
			
		} finally {
			// Close the input reader
			try {
				bufferedReader.close();
			} catch(IOException e) {
				logger.log(Level.WARNING, e.toString());
			}
		}
		
		// If we are logging at FINE level, wait for the exit code
		if (logger.isLoggable(Level.FINE)) {
			try {
				int exitVal = process.waitFor();
				logger.log(Level.FINE, "Process exitValue: " + exitVal);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING,
						"Problem waiting for completion." + e.toString());
			}
		}
	}
	
}