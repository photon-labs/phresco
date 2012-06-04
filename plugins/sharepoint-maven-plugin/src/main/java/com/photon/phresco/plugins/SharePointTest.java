/*
 * ###
 * sharepoint-maven-plugin Maven Mojo
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.Commandline;

/**
 * Goal which cleans the target
 * 
 * @goal test
 * 
 */
public class SharePointTest extends AbstractMojo {

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
	
	//Need to move to the constants file
	private static String NUNIT_CMD = "nunit-console.exe";
	private static String STR_SPACE = " ";
	private static String NUNIT_REPORT_LOCATION = "/target/nunit-report/";
	private static String TEST_FILE_LOC = "/AllTest/bin/Release/";
	private static String TEST_FILE_NAME = "AllTest.dll";

	public void execute() throws MojoExecutionException {
		init();
		fetchNUnit();
		executeTest();
	}
	
	private void init() {
		File temp = new File(baseDir.getPath() + NUNIT_REPORT_LOCATION);
		if (!temp.exists()) {
			temp.mkdirs();
		}
	}
	
	private  void fetchNUnit() throws MojoExecutionException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("mvn");
			sb.append(STR_SPACE);
			sb.append("validate");

			Commandline cl = new Commandline(sb.toString());
			Process p = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
            }
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private void executeTest() throws MojoExecutionException {
		getLog().info("-----------------------------------------");
		getLog().info("T E S T S");
		getLog().info("-----------------------------------------");
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(NUNIT_CMD);
			sb.append(STR_SPACE);
			sb.append("\"");
			sb.append(baseDir.getPath() + TEST_FILE_LOC);
			sb.append(TEST_FILE_NAME);
			sb.append("\"");
			
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(baseDir.getPath() + NUNIT_REPORT_LOCATION );
			getLog().info("Working Directory..."+baseDir.getPath() + NUNIT_REPORT_LOCATION );
			Process execute = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(execute.getInputStream()));
			String line = null;
            while ((line = in.readLine()) != null) {
            }
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
}
