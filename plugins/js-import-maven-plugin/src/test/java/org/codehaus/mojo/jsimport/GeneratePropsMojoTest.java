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
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFileFilterRequest;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.codehaus.mojo.jsimport.AbstractGenerateHtmlMojo.Scope;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * Test out property generation.
 * 
 * @author huntc
 */
public class GeneratePropsMojoTest
{

    /**
     * The root folder where we perform all of our tests.
     */
    private File tempFolder;

    /**
     * The source JS folder.
     */
    private File sourceJsFolder;

    /**
     * The resource HTML folder.
     */
    private File resourceHtmlFolder;

    /**
     * The target folder.
     */
    private File targetFolder;

    /**
     * The target folder.
     */
    private File workFolder;

    /**
     * The local repo folder.
     */
    private File localRepoFolder;

    /**
     * The mojo to test.
     */
    private GenerateHtmlMojo generateHtmlMojo;

    /**
     * Set up a our mojo for all of its tests.
     * 
     * @throws MojoExecutionException if something goes wrong.
     * @throws IOException if something goes wrong.
     */
    @Before
    public void setUpMojo()
        throws MojoExecutionException, IOException
    {
        // Form the folders we require.
        tempFolder = new File( System.getProperty( "java.io.tmpdir" ), "test" + UUID.randomUUID().toString() );

        sourceJsFolder = new File( tempFolder, "src/main/js" );
        sourceJsFolder.mkdirs();
        resourceHtmlFolder = new File( tempFolder, "src/main/resource" );
        resourceHtmlFolder.mkdirs();
        new File( resourceHtmlFolder, "a.html" ).createNewFile();
        new File( resourceHtmlFolder, "b.htm" ).createNewFile();
        targetFolder = new File( tempFolder, "target/classes" );
        targetFolder.mkdirs();
        workFolder = new File( tempFolder, "target/ImportMojo/main" );
        localRepoFolder = new File( tempFolder, "target/.m2" );
        localRepoFolder.mkdirs();

        // Persist a file dependency graph and create the files we require.
        final int numFileDependencies = 3;
        Map<String, LinkedHashSet<String>> fileDependencies =
            new HashMap<String, LinkedHashSet<String>>( numFileDependencies );

        final int numImportFiles = 4;
        LinkedHashSet<String> importFiles = new LinkedHashSet<String>( numImportFiles );

        File file = new File( localRepoFolder, "b.js" );
        file.createNewFile();
        importFiles.add( file.getAbsolutePath() );

        file = new File( sourceJsFolder, "c.js" );
        file.createNewFile();
        importFiles.add( file.getAbsolutePath() );

        file = new File( sourceJsFolder, "a.js" );
        file.createNewFile();

        fileDependencies.put( file.getAbsolutePath(), importFiles );

        importFiles = new LinkedHashSet<String>( 2 );

        file = new File( localRepoFolder, "b.js" );
        file.createNewFile();
        importFiles.add( file.getAbsolutePath() );

        file = new File( sourceJsFolder, "a.js" );
        file.createNewFile();
        importFiles.add( file.getAbsolutePath() );

        file = new File( sourceJsFolder, "d.js" );
        file.createNewFile();
        fileDependencies.put( file.getAbsolutePath(), importFiles );

        importFiles = new LinkedHashSet<String>( 0 );
        file = new File( sourceJsFolder, "c.js" );
        file.createNewFile();
        fileDependencies.put( file.getAbsolutePath(), importFiles );

        Map<String, String> assignedVars = new HashMap<String, String>( 0 );

        FileDependencyPersistanceUtil.writeFileDependencyGraph( workFolder, fileDependencies, assignedVars );

        // Mock some Maven classes that we will use.
        ArtifactRepository localRepository = mock( ArtifactRepository.class );
        when( localRepository.getBasedir() ).thenReturn( localRepoFolder.getAbsolutePath() );

        // Mock the build context and its scanner.
        BuildContext buildContext = mock( BuildContext.class );
        Scanner scanner = mock( Scanner.class );
        when( buildContext.newScanner( (File) anyObject(), eq( true ) ) ).thenReturn( scanner );
        String[] includedFiles = { "a.html", "b.html" };
        when( scanner.getIncludedFiles() ).thenReturn( includedFiles );

        // Mock the file filter.
        MavenFileFilter mavenFileFilter = mock( MavenFileFilter.class );

        // Create the mojo.
        generateHtmlMojo = new GenerateHtmlMojo();
        generateHtmlMojo.setJsSourceFolder( sourceJsFolder );
        generateHtmlMojo.setLocalRepository( localRepository );
        generateHtmlMojo.setTargetFolder( targetFolder );
        generateHtmlMojo.setTargetJsPath( "js" );
        generateHtmlMojo.setWorkFolder( workFolder );
        generateHtmlMojo.setBuildContext( buildContext );
        generateHtmlMojo.setIncludes( Arrays.asList( new String[] { "*.html,*.htm" } ) );
        generateHtmlMojo.setMavenFileFilter( mavenFileFilter );
        generateHtmlMojo.setProject( mock( MavenProject.class ) );

    }

    /**
     * Tidy up after all tests.
     * 
     * @throws IOException if something goes wrong.
     */
    @After
    public void tearDownMojo()
        throws IOException
    {
        FileUtils.deleteDirectory( tempFolder );
    }

    /**
     * Test the successful creation of properties and copying of repo. resources.
     * 
     * @throws MojoExecutionException if something went wrong.
     * @throws MavenFilteringException if something goes wrong.
     */
    @Test
    public void testSuccessfulExecution()
        throws MojoExecutionException, MavenFilteringException
    {
        generateHtmlMojo.doExecute( sourceJsFolder, sourceJsFolder, resourceHtmlFolder, targetFolder, targetFolder,
                                    workFolder, Scope.COMPILE );

        // Test that the expected properties were generated.
        ArgumentCaptor<MavenFileFilterRequest> mavenFileFilterRequestCaptor =
            ArgumentCaptor.forClass( MavenFileFilterRequest.class );
        verify( generateHtmlMojo.getMavenFileFilter(), times( 2 ) ).copyFile( mavenFileFilterRequestCaptor.capture() );
        Properties properties = mavenFileFilterRequestCaptor.getValue().getAdditionalProperties();
        final int expectedPropertyNum = 3;
        assertEquals( expectedPropertyNum, properties.size() );
        assertEquals( "js/b.js\"></script>\n" + "<script type=\"text/javascript\" src=\"js/c.js\"></script>\n"
            + "<script type=\"text/javascript\" src=\"js/a.js", properties.get( "a.js" ) );
        assertEquals( "js/b.js\"></script>\n" + "<script type=\"text/javascript\" src=\"js/c.js\"></script>\n"
            + "<script type=\"text/javascript\" src=\"js/a.js\"></script>\n"
            + "<script type=\"text/javascript\" src=\"js/d.js", properties.get( "d.js" ) );
        assertEquals( "js/c.js", properties.get( "c.js" ) );

        // Given that we've only got one local repo based file we should ensure
        // that it was copied over from the repo into the target folder.
        File[] targetFiles = targetFolder.listFiles();
        assertEquals( 1, targetFiles.length );
        assertEquals( "js", targetFiles[0].getName() );
        targetFiles = targetFiles[0].listFiles();
        assertEquals( new File( targetFolder, "js/b.js" ), targetFiles[0] );

        // / We want to ensure that we try to refresh two files.
        verify( generateHtmlMojo.getBuildContext(), times( 1 ) ).newScanner( resourceHtmlFolder, true );
        verify( generateHtmlMojo.getBuildContext(), times( 2 ) ).refresh( (File) anyObject() );
    }
}
