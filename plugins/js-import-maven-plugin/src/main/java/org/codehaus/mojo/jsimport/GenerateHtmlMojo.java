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
 * Main goal implementation of the generate html mojo.
 * 
 * @goal generate-html
 * @requiresDependencyResolution compile
 * @phase generate-resources
 */
public class GenerateHtmlMojo
    extends AbstractGenerateHtmlMojo
{
    /**
     * The JavaScript source folder.
     * 
     * @parameter default-value="${basedir}/src/main/js"
     * @required
     */
    private File sourceJsFolder;

    /**
     * The HTML resource folder.
     * 
     * @parameter default-value="${basedir}/src/main/resources"
     * @required
     */
    private File resourceHtmlFolder;

    /**
     * The project target folder.
     * 
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File targetFolder;

    /**
     * The folder where test files are created that assist with multiple invocations e.g. caches.
     * 
     * @parameter default-value="${project.build.directory}/ImportMojo/main"
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
        doExecute( sourceJsFolder, sourceJsFolder, resourceHtmlFolder, targetFolder, targetFolder, workFolder,
                   Scope.COMPILE );
    }

    /**
     * @return property.
     */
    public File getJsSourceFolder()
    {
        return sourceJsFolder;
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
     * @param aSourceJsFolder set property.
     */
    public void setJsSourceFolder( File aSourceJsFolder )
    {
        this.sourceJsFolder = aSourceJsFolder;
    }

    /**
     * @param resourceHtmlFolder set property.
     */
    public void setResourceHtmlFolder( File resourceHtmlFolder )
    {
        this.resourceHtmlFolder = resourceHtmlFolder;
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
