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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.plugins.xcode.utils.SdkVerifier;
import com.photon.phresco.plugins.xcode.utils.XcodeUtil;
import com.photon.phresco.util.PluginConstants;
/**
 * APP deploy
 * @goal deploy
 * @phase deploy
 */
public class AppDeploy extends AbstractMojo implements PluginConstants {


	/**
	 * command line tool for iphone simulator
	 * @parameter expression="${simulator.home}" default-value="/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator"
	 */
	private String simHome;
	
	/**
	 * 
	 * @parameter experssion="${simulator.deploy.dir}" default-value=""
	 */
	private String appDeployHome;

	/**
	 * @parameter expression ="${action}" default-value="-SimulateApplication"
	 * 
	 * The possible actions are SimulateDevice, SimulateRestart, SessionOnLaunch, currentSDKRoot
	 */
	private String action;
	
	/**
	 * @parameter expression="${simulator.version}" default-value="5.0" 
	 */
	private String simVersion;
	
	/**
	 * @parameter expression="${device.deploy}"
	 */
	private boolean deviceDeploy;
	
	/**
	 * @parameter expression="${application.name}"
	 */
	private String appName;
	
	/**
	 * The maven project.
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
	 * @parameter expression="${buildNumber}" required="true"
	 */
	protected String buildNumber;
	
	/**
	 * @parameter expression ="${triggerSimulator}" default-value="true"
	 */
	private boolean triggerSimulator;
	
	private String appPath;
	
	private File simHomeAppLocation;
	
	private File buildInfoFile;

	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("iphone trigger value " + triggerSimulator);
		try {
			if(!deviceDeploy && !SdkVerifier.isAvailable(simVersion)) {
				throw new MojoExecutionException("Selected version " +simVersion +" is not available!");
			}
		} catch (IOException e2) {
			throw new MojoExecutionException("SDK verification failed!");
		} catch (InterruptedException e2) {
			throw new MojoExecutionException("SDK verification interrupted!");
		}
		//get the correct simhome if xCode 4.3 is installed simhome is in /Application/Xcode.app/Contents
		//Fix for artf462004
		File simHomeFile = new File(simHome);
		if(!simHomeFile.exists()) {
			simHome = "/Applications/Xcode.app/Contents" + simHome; 
		}
		
		getLog().info("Simulator home" + simHome);
		
		if(!deviceDeploy) {
			//copy the files into simulation directory
			String home = System.getProperty("user.home");
			// Have to get app path from build id
			if (StringUtils.isEmpty(buildNumber)) {
				throw new MojoExecutionException("Selected build is not available!");
			}
			getLog().info("Build id is " + buildNumber);
			getLog().info("Project Code " + baseDir.getName());
			
			BuildInfo buildInfo = getBuildInfo(Integer.parseInt(buildNumber));
			getLog().info("Build Name " + buildInfo);
			
			appPath = buildInfo.getBuildName();

			getLog().info("Application.path = " + appPath);
			appName = getAppFileName(appPath);
			getLog().info("Application name = "+ appName);
			String deployHome = "";
			if(StringUtils.isNotBlank(appDeployHome)){
				deployHome = appDeployHome;
			} else {
			deployHome = home + "/Library/Application Support/iPhone Simulator/" + simVersion +"/Applications";
			}
			simHomeAppLocation = new File(deployHome+File.separator+ project.getName()+File.separator+appName);
			if(!simHomeAppLocation.exists()){
				getLog().info("directory created");
				simHomeAppLocation.mkdirs();
			}
			getLog().info("Desired location "+simHomeAppLocation.getAbsolutePath());
			try {
				String alignedPath = alignedPath(appPath);
				getLog().info("path to copy source :" + alignedPath);
				XcodeUtil.copyFolder(new File(alignedPath), simHomeAppLocation);
				getLog().info("copy the application " + appPath +" to "+ simHomeAppLocation);
			} catch (IOException e1) {
				getLog().error("couldn't copy the application " + appPath +" to "+ simHomeAppLocation);
			}
		}
		Runnable runnable = new Runnable() {
			public void run() {

				ProcessBuilder pb;
				if(deviceDeploy){
					pb = new ProcessBuilder("transporter_chief.rb");
					pb.command().add(appPath);
				} else {
					pb = new ProcessBuilder(simHome);
					// Include errors in output
					pb.redirectErrorStream(true);

					pb.command().add(action);
					pb.command().add(simHomeAppLocation+File.separator+appName.substring(0, appName.indexOf('.')));
				}
				//					pb.command().add(appName);
				getLog().info("List of commands"+pb.command());
				Process child;
				try {
//					if(ProcessHelper.isProcessRunning()) {
//						ProcessHelper.killSimulatorProcess();
//					}
					child = pb.start();
					// Consume subprocess output and write to stdout for debugging
					InputStream is = new BufferedInputStream(child.getInputStream());
					int singleByte = 0;
					while ((singleByte = is.read()) != -1) {
						System.out.write(singleByte);
					}
				} catch (IOException e) {
					getLog().error("error occured in launching simulator ");
					getLog().error(e);
				}
			}
		};

		if (triggerSimulator) {
			getLog().info("Triggering simulator started ");
			Thread t = new Thread(runnable, "iPhoneSimulator");
			t.start();
			try {
				t.join(5000);
			} catch (InterruptedException e1) {
				getLog().error("Triggering simulator failed.");
			}
		}

	}

	private String getAppFileName(String appPath2) {
		if(StringUtils.isNotBlank(appPath2)){
			if(appPath2.endsWith("app")) {
				return appPath2.substring(appPath2.lastIndexOf('/')+1);
			} else {
				File folder = new File(appPath2);
				if(folder.exists()) {
					File[] files = folder.listFiles();
					for (int i = 0; i < files.length; i++) {
						File file = files[i];
						if(file.getName().endsWith("app")) {
							return file.getName();
						}
					}
				}
			}
		}
		return appPath2;
		
	}
	
	private String alignedPath(String appPath2) {
		if(StringUtils.isNotBlank(appPath2)){
			if(appPath2.endsWith("app")) {
				return appPath2;
			}	else {
				return appPath2+File.separator+getAppFileName(appPath2);
			}
		}
		return appPath2;
	}
	
	private BuildInfo getBuildInfo(int buildNumber) throws MojoExecutionException {
		ProjectAdministrator administrator;
		try {
			administrator = PhrescoFrameworkFactory.getProjectAdministrator();
		} catch (PhrescoException e) {
			throw new MojoExecutionException("Project administrator object creation error!");
		}
		buildInfoFile = new File(baseDir.getPath() + PluginConstants.BUILD_DIRECTORY + BUILD_INFO_FILE);
		if (!buildInfoFile.exists()) {
			throw new MojoExecutionException("Build info is not available!");
		}
		try {
			List<BuildInfo> buildInfos = administrator.readBuildInfo(buildInfoFile);
			
			 if (CollectionUtils.isEmpty(buildInfos)) {
				 throw new MojoExecutionException("Build info is empty!");
			 }

			 for (BuildInfo buildInfo : buildInfos) {
				 if (buildInfo.getBuildNo() == buildNumber) {
					 return buildInfo;
				 }
			 }

			 throw new MojoExecutionException("Build info is empty!");
		} catch (Exception e) {
			throw new MojoExecutionException(e.getLocalizedMessage());
		}
	}
}
