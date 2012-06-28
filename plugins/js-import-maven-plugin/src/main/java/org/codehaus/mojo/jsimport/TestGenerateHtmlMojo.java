package org.codehaus.mojo.jsimport;

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

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Test goal implementation of the generate html mojo.
 * 
 * @goal test-generate-html
 * @requiresDependencyResolution test
 * @phase generate-test-resources
 */
public class TestGenerateHtmlMojo
    extends AbstractGenerateHtmlMojo
{
    /**
     * The JavaScript test source folder.
     * 
     * @parameter default-value="${basedir}/src/test/qunit/js"
     * @required
     */
    private File sourceJsFolder;

    /**
     * The JavaScript main source folder.
     * 
     * @parameter default-value="${basedir}/src/main/js"
     * @required
     */
    private File mainSourceJsFolder;

    /**
     * The HTML test resource folder.
     * 
     * @parameter default-value="${basedir}/src/test/qunit/resources"
     * @required
     */
    private File resourceHtmlFolder;

    /**
     * The project target folder.
     * 
     * @parameter default-value="${project.build.testOutputDirectory}"
     * @required
     */
    private File targetFolder;

    /**
     * The main project target folder.
     * 
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File mainTargetFolder;

    /**
     * The folder where test files are created that assist with multiple invocations e.g. caches.
     * 
     * @parameter default-value="${project.build.directory}/ImportMojo/test"
     * @required
     */
    private File workFolder;

    /**
     * Perform the goal of this mojo.
     * 
     * @throws MojoExecutionException if there is an execution failure.
     */
    public void execute()
        throws MojoExecutionException
    {
        doExecute( sourceJsFolder, mainSourceJsFolder, resourceHtmlFolder, targetFolder, mainTargetFolder, workFolder,
                   Scope.TEST );
    }

    /**
     * @return property.
     */
    public File getMainSourceJsFolder()
    {
        return mainSourceJsFolder;
    }

    /** @return property. */
    public File getMainTargetFolder()
    {
        return mainTargetFolder;
    }

    /**
     * @return property.
     */
    public File getResourceHtmlFolder()
    {
        return resourceHtmlFolder;
    }

    /**
     * @return property.
     */
    public File getSourceJsFolder()
    {
        return sourceJsFolder;
    }

    /**
     * @return property.
     */
    public File getTargetFolder()
    {
        return targetFolder;
    }

    /**
     * @return property.
     */
    public File getWorkFolder()
    {
        return workFolder;
    }

    /**
     * @param mainSourceJsFolder set property.
     */
    public void setMainSourceJsFolder( File mainSourceJsFolder )
    {
        this.mainSourceJsFolder = mainSourceJsFolder;
    }

    /**
     * @param mainTargetFolder set property.
     */
    public void setMainTargetFolder( File mainTargetFolder )
    {
        this.mainTargetFolder = mainTargetFolder;
    }

    /**
     * @param resourceHtmlFolder set property.
     */
    public void setResourceHtmlFolder( File resourceHtmlFolder )
    {
        this.resourceHtmlFolder = resourceHtmlFolder;
    }

    /**
     * @param sourceFolder set property.
     */
    public void setSourceJsFolder( File sourceFolder )
    {
        this.sourceJsFolder = sourceFolder;
    }

    /**
     * @param targetFolder set property.
     */
    public void setTargetFolder( File targetFolder )
    {
        this.targetFolder = targetFolder;
    }

    /**
     * @param workFolder set property.
     */
    public void setWorkFolder( File workFolder )
    {
        this.workFolder = workFolder;
    }

}
