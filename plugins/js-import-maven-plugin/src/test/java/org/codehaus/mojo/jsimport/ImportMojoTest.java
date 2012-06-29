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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the import mojo.
 * 
 * @author huntc
 */
public class ImportMojoTest
{

    /**
     * The mojo to test.
     */
    private ImportMojo importMojo;

    /**
     * Set up a our mojo for testing.
     */
    @Before
    public void setUpMojo()
    {
        importMojo = new ImportMojo();

        // Inject dependencies into mojo.
        List<Dependency> dependencies = new ArrayList<Dependency>( 2 );

        Dependency dependency = new Dependency();
        dependency.setArtifactId( "jquery" );
        dependency.setGroupId( "org.jquery" );
        dependency.setType( "js" );
        dependency.setVersion( "1.4.2" );
        dependencies.add( dependency );

        dependency = new Dependency();
        dependency.setArtifactId( "jquery-ui" );
        dependency.setGroupId( "com.jqueryui" );
        dependency.setType( "js" );
        dependency.setVersion( "1.8.4.custom.min" );
        dependencies.add( dependency );

        importMojo.setDependencies( dependencies );

        // Set up the artifact factory and its associated resolvers.
        ArtifactFactory artifactFactory = mock( ArtifactFactory.class );
        Artifact jQueryArtifact = mock( Artifact.class );
        File jQueryFile = new File( "file:" + File.separator + "//jquery-1.4.2.js" );
        when( jQueryArtifact.getFile() ).thenReturn( jQueryFile );
        when( artifactFactory.createArtifactWithClassifier( //
        "org.jquery", "jquery", "1.4.2", "js", null ) )//
        .thenReturn( jQueryArtifact );
        Artifact jQueryUIArtifact = mock( Artifact.class );
        File jQueryUIFile = new File( "file:" + File.separator + "//jqueryui-1.8.4.custom.min.js" );
        when( jQueryUIArtifact.getFile() ).thenReturn( jQueryUIFile );
        when( artifactFactory.createArtifactWithClassifier( //
        "com.jqueryui", "jquery-ui", "1.8.4.custom.min", "js", null ) )//
        .thenReturn( jQueryUIArtifact );
        importMojo.setArtifactFactory( artifactFactory );

        ArtifactResolver resolver = mock( ArtifactResolver.class );
        importMojo.setResolver( resolver );

        importMojo.setIncludes( Arrays.asList( new String[] { "js" } ) );

        importMojo.setAssumeABrowser( true );
    }

    /**
     * Test obtaining the global variable declarations for a given file.
     * 
     * @throws MojoExecutionException if something terrible goes wrong.
     * @throws URISyntaxException if something terrible goes wrong.
     */
    @Test
    public void testGetGlobalsForFile()
        throws MojoExecutionException, URISyntaxException
    {
        File jsFile = new File( ImportMojo.class.getResource( "globals.js" ).toURI() );

        File jsTargetDir = new File( System.getProperty( "java.io.tmpdir" ), ImportMojo.class.getName() );
        jsTargetDir.deleteOnExit();

        assertTrue( //
        importMojo.processFileForImportsAndSymbols( jsFile.getParentFile(), jsTargetDir, jsFile, 0, null ) );

        Map<String, String> fileAssignedGlobals = importMojo.getFileAssignedGlobals();

        final int expectedAssignedGlobals = 13;
        assertEquals( expectedAssignedGlobals, fileAssignedGlobals.size() );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "b" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "Controller" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "D" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "F" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "j" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "l" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "m" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "n" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "o" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "explicitModule" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "explicitModule2" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "globals" ) );
        assertEquals( jsFile.getPath(), fileAssignedGlobals.get( "nodeps" ) );

        Map<String, Set<String>> fileUnassignedGlobals = importMojo.getFileUnassignedGlobals();

        final int expectedUnassignedGlobals = 1;
        assertEquals( expectedUnassignedGlobals, fileUnassignedGlobals.size() );
        Set<String> vars = fileUnassignedGlobals.get( jsFile.getPath() );
        assertEquals( 6, vars.size() );
        assertTrue( vars.contains( "a" ) );
        assertTrue( vars.contains( "c" ) );
        assertTrue( vars.contains( "j" ) );
        assertTrue( vars.contains( "a/someOtherModule" ) );
        assertTrue( vars.contains( "a/someOtherModule2" ) );
        assertTrue( vars.contains( "a/someOtherModule3" ) );
    }

    /**
     * Test that import declarations build what we expect to see in a dependency graph.
     * 
     * @throws IOException file issues.
     * @throws MojoExecutionException Problem with execution.
     * @throws URISyntaxException test fails.
     */
    @Test
    public void testGetSuccessfulImports()
        throws MojoExecutionException, IOException, URISyntaxException
    {

        File jsFile = new File( ImportMojo.class.getResource( "goodImport.js" ).toURI() );

        File jsTargetDir = new File( System.getProperty( "java.io.tmpdir" ), ImportMojo.class.getName() );
        jsTargetDir.deleteOnExit();

        assertTrue( importMojo.processFileForImportsAndSymbols( jsFile.getParentFile(), jsTargetDir, jsFile, 0, null ) );

        Map<String, LinkedHashSet<String>> fileDependencies = importMojo.getFileDependencies();
        assertEquals( 1, fileDependencies.size() );

        Set<String> fileDependencySet = fileDependencies.get( jsFile.getPath() );
        assertEquals( 2, fileDependencySet.size() );

        assertTrue( fileDependencySet.contains( "file:" + File.separator + "jquery-1.4.2.js" ) );
        assertTrue( fileDependencySet.contains( "file:" + File.separator + "jqueryui-1.8.4.custom.min.js" ) );
    }

    /**
     * Test that import declarations build what we expect to see in a dependency graph using transitive dependencies.
     * 
     * @throws IOException file issues.
     * @throws MojoExecutionException Problem with execution.
     * @throws URISyntaxException test fails.
     */
    @Test
    public void testGetSuccessfulTransitiveImports()
        throws MojoExecutionException, IOException, URISyntaxException
    {

        File jsFile = new File( ImportMojo.class.getResource( "goodTransitiveImport.js" ).toURI() );

        Set<Artifact> transitiveArtifacts = new HashSet<Artifact>( 1 );
        Artifact transitiveArtifact = mock( Artifact.class );
        when( transitiveArtifact.getGroupId() ).thenReturn( "com.classactionpl.somegroup" );
        when( transitiveArtifact.getArtifactId() ).thenReturn( "someartifact" );
        when( transitiveArtifact.getType() ).thenReturn( "js" );
        File transitiveArtifactFile = new File( "file:" + File.separator + "//somefile.js" );
        when( transitiveArtifact.getFile() ).thenReturn( transitiveArtifactFile );
        transitiveArtifacts.add( transitiveArtifact );

        File jsTargetDir = new File( System.getProperty( "java.io.tmpdir" ), ImportMojo.class.getName() );
        jsTargetDir.deleteOnExit();

        assertTrue( importMojo.processFileForImportsAndSymbols( jsFile.getParentFile(), jsTargetDir, jsFile, 0,
                                                                transitiveArtifacts ) );

        Map<String, LinkedHashSet<String>> fileDependencies = importMojo.getFileDependencies();
        assertEquals( 1, fileDependencies.size() );

        Set<String> fileDependencySet = fileDependencies.get( jsFile.getPath() );
        assertEquals( 1, fileDependencySet.size() );

        assertTrue( fileDependencySet.contains( "file:" + File.separator + "somefile.js" ) );
    }

    /**
     * Test the declaration of an unknown import against direct dependencies and transitives.
     * 
     * @throws IOException file issues.
     * @throws URISyntaxException test fails.
     */
    @Test
    public void testGetUnsuccessfulImports()
        throws IOException, URISyntaxException
    {

        File jsFile = new File( ImportMojo.class.getResource( "badImport.js" ).toURI() );

        Set<Artifact> transitiveArtifacts = new HashSet<Artifact>( 1 );
        Artifact transitiveArtifact = mock( Artifact.class );
        when( transitiveArtifact.getGroupId() ).thenReturn( "com.classactionpl.somegroup" );
        when( transitiveArtifact.getArtifactId() ).thenReturn( "someartifact" );
        when( transitiveArtifact.getType() ).thenReturn( "js" );
        File transitiveArtifactFile = new File( "file:" + File.separator + "//somefile.js" );
        when( transitiveArtifact.getFile() ).thenReturn( transitiveArtifactFile );
        transitiveArtifacts.add( transitiveArtifact );

        File jsTargetDir = new File( System.getProperty( "java.io.tmpdir" ), ImportMojo.class.getName() );
        jsTargetDir.deleteOnExit();

        try
        {
            importMojo.processFileForImportsAndSymbols( jsFile.getParentFile(), jsTargetDir, jsFile, 0,
                                                        transitiveArtifacts );

            fail( "Should not get here" );

        }
        catch ( MojoExecutionException e )
        {
            assertEquals( "Build stopping given dependency issue.", e.getMessage() );
        }

        Map<String, LinkedHashSet<String>> fileDependencies = importMojo.getFileDependencies();
        assertEquals( 1, fileDependencies.size() );

        Set<String> fileDependencySet = fileDependencies.get( jsFile.getPath() );
        assertEquals( 0, fileDependencySet.size() );

    }

    /**
     * Test for the successful resolution of unassigned globals.
     * 
     * @throws MojoExecutionException should not happen.
     */
    @Test
    public void testSuccessfulProcessSourceFilesForSymbolDeclarations()
        throws MojoExecutionException
    {

        Map<String, String> fileAssignedGlobals = importMojo.getFileAssignedGlobals();
        fileAssignedGlobals.put( "a", "file:" + File.separator + "declfile1.js" );
        fileAssignedGlobals.put( "b", "file:" + File.separator + "declfile2.js" );
        fileAssignedGlobals.put( "c", "file:" + File.separator + "declfile3.js" );

        Map<String, Set<String>> fileUnassignedGlobals = importMojo.getFileUnassignedGlobals();

        Set<String> globals = new HashSet<String>( 2 );
        fileUnassignedGlobals.put( "file:" + File.separator + "importfile1.js", globals );
        globals.add( "a" );
        globals.add( "b" );

        globals = new HashSet<String>( 2 );
        fileUnassignedGlobals.put( "file:" + File.separator + "importfile2.js", globals );
        globals.add( "b" );
        globals.add( "c" );

        importMojo.processSourceFilesForUnassignedSymbolDeclarations();

        Map<String, LinkedHashSet<String>> fileDependencies = importMojo.getFileDependencies();
        assertEquals( 2, fileDependencies.size() );

        Set<String> imports = fileDependencies.get( "file:" + File.separator + "importfile1.js" );
        assertEquals( 2, imports.size() );
        assertTrue( imports.contains( "file:" + File.separator + "declfile1.js" ) );
        assertTrue( imports.contains( "file:" + File.separator + "declfile2.js" ) );

        imports = fileDependencies.get( "file:" + File.separator + "importfile2.js" );
        assertEquals( 2, imports.size() );
        assertTrue( imports.contains( "file:" + File.separator + "declfile2.js" ) );
        assertTrue( imports.contains( "file:" + File.separator + "declfile3.js" ) );
    }

    /**
     * Test for the unsuccessful resolution of unassigned globals.
     */
    @Test
    public void testUnsuccessfulProcessSourceFilesForSymbolDeclarations()
    {

        Map<String, Set<String>> fileUnassignedGlobals = importMojo.getFileUnassignedGlobals();

        Set<String> globals = new HashSet<String>( 2 );
        fileUnassignedGlobals.put( "file:" + File.separator + "importfile1.js", globals );
        globals.add( "a" );

        try
        {
            importMojo.processSourceFilesForUnassignedSymbolDeclarations();
            fail( "Should not get here." );
        }
        catch ( MojoExecutionException e )
        {
            Map<String, LinkedHashSet<String>> fileDependencies = importMojo.getFileDependencies();
            assertEquals( 0, fileDependencies.size() );
        }

    }
}
