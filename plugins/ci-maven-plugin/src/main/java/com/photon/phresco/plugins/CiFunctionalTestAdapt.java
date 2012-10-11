package com.photon.phresco.plugins;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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
 */

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.util.FrameworkUtil;
import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

/**
 * Goal which touches a timestamp file.
 *
 * @goal adapt
 * 
 */
public class CiFunctionalTestAdapt extends AbstractMojo implements PluginConstants {
	
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
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;
	
	/**
	 * @parameter expression="${browser}" required="true"
	 */
	protected String browser;
	
	/**
	 * @parameter expression="${resolution}" default-value="1024*768"
	 */
	protected String resolution;
	
    public void execute() throws MojoExecutionException {
    	getLog().info("Adapting the project for functional test execution....");
    	adaptFuntionalTest();
    }
    
    public void adaptFuntionalTest() throws MojoExecutionException {
    	try {
    		File currentDirectory = new File(".");
    		//two level higher
  		  	File currentDirPath = new File(currentDirectory.getCanonicalPath());
  		  	getLog().info("currentDirPath...." + currentDirPath);
  		  	String parentpath = new File(currentDirPath.getParent()).getParent();
  		  	getLog().info("Root param pom path parentpath...." + parentpath);
    		ProjectAdministrator projectAdministrator = PhrescoFrameworkFactory.getProjectAdministrator();
    		Project currentProject = projectAdministrator.getProjectByWorkspace(new File(parentpath));
    		getLog().info("currentProject ...." + currentProject);
    		String techId = currentProject.getProjectInfo().getTechnology().getId();
    		getLog().info("techId ...." + techId);
    		FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
    		String funcitonalAdaptDir = frameworkUtil.getFuncitonalAdaptDir(techId);
    		
			File functionalAdaptConfigXML = new File(parentpath + funcitonalAdaptDir);
			getLog().info("functionalAdaptConfigXML cananical path ...." + functionalAdaptConfigXML.getCanonicalPath());
			if (TechnologyTypes.JAVA_STANDALONE.contains(techId)) {	// java stanalone build test alone
				//special handling
				StringBuilder builder = new StringBuilder(parentpath);
        		File systemPath = new File(builder.toString() + File.separator + POM_XML);
        		PomProcessor pomprocessor = new PomProcessor(systemPath);
        		String jarName = pomprocessor.getFinalName();
        		builder.append(project.getBuild().getDirectory());
        		builder.append(File.separator);
        		builder.append(jarName);
        		builder.append(".jar");
        		String jarLocation = builder.toString();
        		getLog().info("jarLocation ...." + jarLocation);
        		getLog().info("currentDirectory.getPath() ...." + currentDirectory.getPath());
        		builder = new StringBuilder(currentDirectory.getPath());          // Adding Location of JAR as Dependency in pom.xml
        		getLog().info("funcitonalTestDir ...." + builder.toString());
            	systemPath = new File(builder.toString() + File.separator + POM_XML);
            	getLog().info("systemPath ...." + systemPath);
	        	pomprocessor = new PomProcessor(systemPath);
	        	pomprocessor.addDependency(JAVA_STANDALONE, JAVA_STANDALONE, DEPENDENCY_VERSION, SYSTEM, null, jarLocation);
	        	pomprocessor.save();
	        	getLog().info("java standalone dependency added in pom.xml");
    		} else {
    			overwriteConfigFile(currentProject, environmentName, browser, functionalAdaptConfigXML.getCanonicalPath(), resolution);
    		}
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
    }
    
    public void overwriteConfigFile(Project currentProject, String environmentName, String browser, String functionalConfigPath, String resolution) throws MojoExecutionException {
    	getLog().info("overwriteConfigFile ...." + environmentName);
    	getLog().info("browser ...." + browser);
    	getLog().info("functionalConfigPath ...." + functionalConfigPath);
    	try {
        	File file = new File(functionalConfigPath);
        	getLog().info("file exists....." + file.exists());
        	if(file.exists()){
        		getLog().info("file exists ...." + file.getCanonicalPath());
        		ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
        		administrator.updateTestConfiguration(currentProject, environmentName, browser, functionalConfigPath, resolution);
        	} 
        } catch (Exception e) {
        	throw new MojoExecutionException(e.getMessage(), e);
        }
    }
    
}
