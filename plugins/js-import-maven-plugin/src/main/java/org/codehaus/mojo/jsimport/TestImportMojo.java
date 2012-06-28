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
 * Test goal implementation of the import mojo.
 * 
 * @goal test-import-js
 * @requiresDependencyResolution test
 * @phase process-test-sources
 */
public class TestImportMojo
    extends AbstractImportMojo
{
    /**
     * The test source folder.
     * 
     * @parameter default-value="${basedir}/src/test/qunit/js"
     * @required
     */
    private File sourceJsFolder;

    /**
     * The project target folder.
     * 
     * @parameter default-value="${project.build.testOutputDirectory}"
     * @required
     */
    private File targetFolder;

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
        doExecute( sourceJsFolder, targetFolder, workFolder, Scope.TEST );
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
     * @param sourceJsFolder set property.
     */
    public void setSourceJsFolder( File sourceJsFolder )
    {
        this.sourceJsFolder = sourceJsFolder;
    }

    /**
     * @param targetJsFolder set property.
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
