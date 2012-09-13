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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.util.PluginConstants;

/**
 * Goal which cleans the target
 * 
 * @goal clean
 * 
 */
public class DotNetClean extends AbstractMojo implements PluginConstants {

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
	 * The Dotnet target folder.
	 * 
	 * @parameter expression="/do_not_checkin/target"
	 * @required
	 */
	protected String targetDirStr;

	@Override
	public void execute() throws MojoExecutionException {
		File targetDir = new File(baseDir + targetDirStr);
		if (targetDir.exists()) {
			deleteDir(targetDir);
		}
	}

	private void deleteDir(File dir) throws MojoExecutionException {
		try {
			getLog().info("Deleting : " + dir.getPath());
			FileUtils.deleteDirectory(dir);
			getLog().info("Target Folder Deleted Successfully");
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
