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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

/**
 * Test persistence utils.
 * 
 * @author huntc
 */
public class FileDependencyPersistanceTest
{
    /**
     * Test the reading of our dependency graph when it doesn't exist.
     * 
     * @throws MojoExecutionException should not happen.
     * @throws IOException should not happen.
     */
    @Test
    public void testNoReadFileDependencyGraph()
        throws MojoExecutionException, IOException
    {

        File workFolder = new File( System.getProperty( "java.io.tmpdir" ) );
        workFolder.mkdirs();
        File file = new File( workFolder, "FileDependency.avro" );
        file.delete();

        assertEquals( 0L, FileDependencyPersistanceUtil.readFileDependencyGraph( workFolder, null, null ) );
    }

    /**
     * Test the writing of our dependency graph.
     * 
     * @throws MojoExecutionException should not happen.
     * @throws IOException should not happen.
     */
    @Test
    public void testWriteAndReadFileDependencyGraph()
        throws MojoExecutionException, IOException
    {

        Map<String, LinkedHashSet<String>> fileDependencies = new HashMap<String, LinkedHashSet<String>>( 2 );

        File workFolder = new File( System.getProperty( "java.io.tmpdir" ) );
        workFolder.mkdirs();

        LinkedHashSet<String> imports = new LinkedHashSet<String>( 2 );
        imports.add( "import1" );
        imports.add( "import2" );
        fileDependencies.put( "filename1", imports );
        imports = new LinkedHashSet<String>( 1 );
        imports.add( "import3" );
        fileDependencies.put( "filename2", imports );

        final int numAssignedVars = 3;
        Map<String, String> assignedVars = new HashMap<String, String>( numAssignedVars );
        assignedVars.put( "a", "filename1" );
        assignedVars.put( "b", "filename1" );
        assignedVars.put( "c", "filename3" );

        FileDependencyPersistanceUtil.writeFileDependencyGraph( workFolder, fileDependencies, assignedVars );

        // Now clear the mojo's graph so that we can test reading it back
        // properly.
        Map<String, Set<String>> writtenFileDependencies = new HashMap<String, Set<String>>( fileDependencies );
        fileDependencies.clear();

        Map<String, String> writtenAssignedVars = new HashMap<String, String>( assignedVars );
        assignedVars.clear();

        long modificationTime =
            FileDependencyPersistanceUtil.readFileDependencyGraph( workFolder, fileDependencies, assignedVars );

        File file = new File( workFolder, "FileDependency.avro" );
        assertEquals( file.lastModified(), modificationTime );

        assertEquals( writtenFileDependencies, fileDependencies );
        assertEquals( writtenAssignedVars, assignedVars );
    }
}
