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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;

public class LocalRepositoryCollectorTest
{

    private MavenProject project;

    private ArtifactRepository localRepository;

    private String localRepositoryBaseDir;

    private String artifactId;

    @Before
    public void setUp()
    {
        project = mock( MavenProject.class );
        localRepository = mock( ArtifactRepository.class );

        localRepositoryBaseDir = "a/b/c";
        when( localRepository.getBasedir() ).thenReturn( localRepositoryBaseDir );
        artifactId = "artifactId";
        when( project.getArtifactId() ).thenReturn( artifactId );
    }

    @Test
    public void testNoParentProject()
    {
        File wwwZipBase = mock( File.class );
        File wwwZipFile = mock( File.class );

        when( project.getParent() ).thenReturn( null );

        when( wwwZipBase.listFiles() ).thenReturn( new File[] { wwwZipFile } );
        when( wwwZipFile.isDirectory() ).thenReturn( true );
        when( wwwZipFile.getAbsolutePath() ).thenReturn( "d/e/f" );

        LocalRepositoryCollector collector =
            new LocalRepositoryCollector( project, localRepository, new File[] { wwwZipBase } );

        verify( project, times( 1 ) ).getArtifactId();
        verify( project, times( 1 ) ).getParent();

        String localRepositoryFound = collector.findLocalRepository( "a/b/c/d" );
        assertEquals( localRepositoryBaseDir, localRepositoryFound );

        localRepositoryFound = collector.findLocalRepository( "d/e/f/z" );
        assertEquals( "d/e/f", localRepositoryFound );

        localRepositoryFound = collector.findLocalRepository( "a/b/d/d" );
        assertNull( localRepositoryFound );
    }

    @Test
    public void testWithParentProject()
    {
        MavenProject parentProject = mock( MavenProject.class );
        when( project.getParent() ).thenReturn( parentProject );
        List<String> modules = new ArrayList<String>( 1 );
        modules.add( artifactId );
        when( parentProject.getModules() ).thenReturn( modules );
        when( parentProject.getArtifactId() ).thenReturn( "anotherArtifactId" );
        String parentLocalRepositoryBaseDir = "x/y/z";
        File parentLocalRepositoryBaseDirFile = mock( File.class );
        when( parentLocalRepositoryBaseDirFile.getAbsolutePath() ).thenReturn( parentLocalRepositoryBaseDir );
        when( parentProject.getBasedir() ).thenReturn( parentLocalRepositoryBaseDirFile );
        when( parentProject.getParent() ).thenReturn( null );

        LocalRepositoryCollector collector = new LocalRepositoryCollector( project, localRepository, new File[] {} );

        verify( parentProject, times( 1 ) ).getModules();
        verify( parentProject, times( 1 ) ).getArtifactId();
        verify( parentProject, times( 1 ) ).getBasedir();
        verify( parentProject, times( 1 ) ).getParent();
        verify( parentLocalRepositoryBaseDirFile, times( 1 ) ).getAbsolutePath();

        String localRepositoryFound = collector.findLocalRepository( "x/y/z/d" );

        assertEquals( parentLocalRepositoryBaseDir, localRepositoryFound );
    }
}
