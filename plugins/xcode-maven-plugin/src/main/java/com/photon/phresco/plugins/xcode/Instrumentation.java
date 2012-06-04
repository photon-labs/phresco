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
/*******************************************************************************
 * Copyright (c)  2012 Photon infotech.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 *  Contributors:
 *  	  Photon infotech - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.plugins.xcode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
/**
 * APP instrumentation
 * @goal instruments
 */
public class Instrumentation extends AbstractXcodeMojo {
	/**
	 * @parameter experssion="${command}" default-value="instruments"
	 */
	private String command;
	
	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;
	
	/**
	 * @parameter expression="${template}" default-value="/Developer/Platforms/iPhoneOS.platform/Developer/Library/Instruments/PlugIns/AutomationInstrument.bundle/Contents/Resources/Automation.tracetemplate"
	 */
	private String template;
	
	/**
	 * This should be either device or template
	 * @parameter expression="${deviceid}"
	 */
	private String deviceid;
	
	/**
	 * @parameter expression="${pid}"
	 */
	private String pid;
	
	/**
	 * @parameter expression="${verbose}" default-value="false"
	 */
	private boolean verbose;
	
	/**
	 * @parameter expression="${application.path}"
	 */
	private String appPath;
	
	/**
	 * @parameter expression="${script.name}" default-value="test/functional/tests/TestSuite.js"
	 */
	private String script;
	
	/**
	 * @parameter 
	 */
	private String outputFolder;
	
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
			getLog().info("Instrumentation command" + command);
			
		    try {
				outputFolder = project.getBasedir().getAbsolutePath();
				File f = new File(outputFolder);
				File files[] = f.listFiles();
				for(File file : files) {
					if(file.getName().startsWith("Run 1" ) || file.getName().endsWith(".trace")) {
						FileUtils.deleteDirectory(file);		
					}
				}
			} catch (IOException e) {
				getLog().error(e);
			}
			
			Runnable runnable = new Runnable() {
				public void run() {
					ProcessBuilder pb = new ProcessBuilder(command);
					//device takes the highest priority
					if(StringUtils.isNotBlank(deviceid)) {
						pb.command().add("-w");
						pb.command().add(deviceid);
					}
					pb.command().add("-t");
					pb.command().add(template);

					if(StringUtils.isNotBlank(appPath)) {
						pb.command().add(appPath);
					} else {
						getLog().error("Application should not be empty");
					}
					if(StringUtils.isNotBlank(script)) {
						pb.command().add("-e");
						pb.command().add("UIASCRIPT");
						String scriptPath = project.getBasedir().getAbsolutePath()+File.separator+script;
						pb.command().add(scriptPath);
					} else {
						getLog().error("script is empty");
					}
					
					pb.command().add("-e");
					pb.command().add("UIARESULTSPATH");					
					pb.command().add(outputFolder);
					
					// Include errors in output
					pb.redirectErrorStream(true);

					getLog().info("List of commands"+pb.command());
					Process child;
					try {
						child = pb.start();
						// Consume subprocess output and write to stdout for debugging
						InputStream is = new BufferedInputStream(child.getInputStream());
						int singleByte = 0;
						while ((singleByte = is.read()) != -1) {
							System.out.write(singleByte);
						}
					} catch (IOException e) {
						getLog().error(e);
					}
										
				}
			};
			
			Thread t = new Thread(runnable, "iPhoneSimulator");
			t.start();
			getLog().info("Thread started");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
