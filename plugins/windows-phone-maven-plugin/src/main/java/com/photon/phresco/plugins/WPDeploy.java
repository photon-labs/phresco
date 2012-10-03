/*
 * ###
 * windows-phone-maven-plugin Maven Mojo
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.plugins.model.WP8PackageInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.PluginUtils;
import com.photon.phresco.util.Utility;

/**
 * Goal which deploys the Java WebApp to a server
 * 
 * @goal deploy
 * 
 */
public class WPDeploy extends AbstractMojo implements PluginConstants {

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
	 * @parameter expression="${buildNumber}" required="true"
	 */
	protected String buildNumber;

	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;
	
	/**
	 * @parameter expression="${target}" required="true"
	 */
	protected String target;
	
	/**
	 * @parameter expression="${type}" required="true"
	 * default-value="wp8"
	 * @readonly
	 */
	protected String type;
	
	private String sourceDirectory = "\\source";
	private File buildFile;
	private File tempDir;
	private File buildDir;
	private File temp;
	private File rootDir;
	private File[] solutionFile;
	private WP8PackageInfo packageInfo;
	

	public void execute() throws MojoExecutionException {
		init();
		extractBuild();
		if (type.equalsIgnoreCase("wp8")) {
			deployWp8Package();
		} else {
			deployWp7Package();
		}
	}

	private void init() throws MojoExecutionException {
		try {

			if (StringUtils.isEmpty(buildNumber) || StringUtils.isEmpty(environmentName) || StringUtils.isEmpty(type)) {
				callUsage();
			}
			if(type.equalsIgnoreCase("wp8")) {
				getSolutionFile();
				packageInfo = new WP8PackageInfo(rootDir);
			}
			
			PluginUtils pu = new PluginUtils();
			BuildInfo buildInfo = pu.getBuildInfo(Integer.parseInt(buildNumber));
			getLog().info("Build Name " + buildInfo);
			
			buildDir = new File(baseDir.getPath() + BUILD_DIRECTORY);
			buildFile = new File(buildDir.getPath() + File.separator + buildInfo.getBuildName());
			tempDir = new File(buildDir.getPath() + File.separator + buildFile.getName().substring(0, buildFile.getName().length() - 4));
			tempDir.mkdirs();
			temp = new File(tempDir.getPath());	
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Deploy Goal");
		getLog().info(
				"mvn windows-phone:deploy -DbuildNumber=\"Build Number\""
						+ " -DenvironmentName=\"Multivalued evnironment names\"" 
						+ " -Dtype=\"Windows Phone platform\"");
		throw new MojoExecutionException("Invalid Usage. Please see the Usage of Deploy Goal");
	}
	
	private void getSolutionFile() throws MojoExecutionException {
		try {
			// Get .sln file from the source folder
			File sourceDir = new File(baseDir.getPath() + sourceDirectory);
			solutionFile = sourceDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(WP_SLN);
				}
			});
			
			// Get the source/src/<ProjectRoot> folder
			rootDir = new File(baseDir.getPath() + sourceDirectory + File.separator + WP_PROJECT_ROOT);
			rootDir = new File(baseDir.getPath() + sourceDirectory + File.separator + "src" + File.separator + WP_PROJECT_ROOT);
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
	
	private void extractBuild() throws MojoExecutionException {
		try {
			ArchiveUtil.extractArchive(buildFile.getPath(), tempDir.getPath(), ArchiveType.ZIP);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		} 
	}

	private void deployWp7Package() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Deploying project ...");
			
			
			// Get .xap file from the extracted contents
			File[] xapFile = tempDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".xap");
				}
			});
			
			
			// wptools.exe -target:emulator -xap:WindowsPhoneApplication1.xap -install
			StringBuilder sb = new StringBuilder();
			sb.append(WP7_WPTOOLS_PATH);
			sb.append(WP7_TARGET);
			sb.append(WP_STR_COLON);
			sb.append(target);
			sb.append(STR_SPACE);
			
			sb.append(WP7_XAP_FILE);
			sb.append(WP_STR_COLON);
			sb.append(xapFile[0].getName());
			sb.append(STR_SPACE);
			
			sb.append(WP7_INSTALL);
			
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(tempDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} finally {
			Utility.closeStream(in);
		}
	}
	
	private void deployWp8Package() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			getLog().info("Deploying project ...");
			
			if (packageAlreadyInstalled()) {
				getLog().info("Package already installed... Uninstalling.");
				uninstallExistingPackage();
			}
			// Get .ps1 file from the extracted contents
			File[] appxFile = tempDir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
					return name.endsWith(".appx");
				}
			});
			
			/* DONT REMOVE FOLLOWING COMMENT BLOCK */
			/*BufferedReader br = null;
			BufferedWriter bw = null;
			File path = new File(tempDir.getPath() + File.separator + ps1File[0].getName());
			File tempFile = new File(tempDir.getPath() + File.separator + "tmp" + ps1File[0].getName().substring(ps1File[0].getName().length() - 4));
			File newFile = new File(tempDir.getPath() + File.separator + ps1File[0].getName());
			try {
				br = new BufferedReader(new FileReader(path));
				bw = new BufferedWriter(new FileWriter(tempFile));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.trim().contains("[switch]$Force = $false")) {
						line = line.replace("[switch]$Force = $false", "[switch]$Force = $true");
					}
					bw.write(line + "\n");
				}
			} catch (Exception e) {
				return;
			} finally {
				br.close();
				bw.close();
			}
			path.delete();
			tempFile.renameTo(newFile);*/
			
			// PowerShell .\Add-AppDevPackage.ps1
			StringBuilder sb = new StringBuilder();
			sb.append(WP_POWERSHELL_PATH);
			sb.append(WP_ADD_APPX_PACKAGE);
			sb.append(WP_STR_DOT);
			sb.append(WINDOWS_STR_BACKSLASH);
			sb.append(appxFile[0].getName());
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(tempDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} finally {
			Utility.closeStream(in);
		}
	}
	
	private boolean packageAlreadyInstalled() throws MojoExecutionException {
		boolean isPackageInstalled = false;
		BufferedReader in = null;
		try {
			String packageName = packageInfo.getPackageName();
			
			// PowerShell (Get-AppxPackage -Name <packageName>).count
			StringBuilder sb = new StringBuilder();
			sb.append(WP_POWERSHELL_PATH);
			sb.append("(" + WP_GET_APPX_PACKAGE +"-Name " + packageName + ").count");
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(tempDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			String tempVar = null;
			while ((line = in.readLine()) != null) {
				if (tempVar == null) {
					tempVar = line;
				}
			}
			if (tempVar != null) {
				if(Integer.parseInt(tempVar.trim()) > 0) {			
					isPackageInstalled = true;
				}
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
		return isPackageInstalled;
		
	}
	
	private void uninstallExistingPackage() throws MojoExecutionException {
		BufferedReader in = null;
		try {
			String packageFullName = getPackageFullName();
			// PowerShell Remove-AppxPackage <packageFullName>
			
			StringBuilder sb = new StringBuilder();
			sb.append(WP_POWERSHELL_PATH);
			sb.append(WP_REMOVE_APPX_PACKAGE + packageFullName);
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(tempDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			
			while ((line = in.readLine()) != null) {				
			}
			
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}		
	}
	
	private String getPackageFullName() throws MojoExecutionException {
		String packageFullName = null;
		BufferedReader in = null;
		try {
			String packageName = packageInfo.getPackageName();
			
			// PowerShell (Get-AppxPackage -Name <packageName>).packagefullname
			StringBuilder sb = new StringBuilder();
			sb.append(WP_POWERSHELL_PATH);
			sb.append("(" + WP_GET_APPX_PACKAGE + "-Name " + packageName + ").packagefullname");
			
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(tempDir);
			Process process = cl.execute();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (packageFullName == null) {
					packageFullName = line;
				}		
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
		return packageFullName;		
	}
}
