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
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.project.MavenProject;

/**
 * Collect a list of local repositories and provide a means of determining which repository a given file belongs to.
 * 
 * @author Christopher Hunt
 */
public class LocalRepositoryCollector
{
    private final List<String> localRepositoryPaths = new ArrayList<String>();

    /**
     * Constructor.
     * 
     * @param project The Maven project to be processed.
     * @param localRepository The local repository being used by Maven.
     * @param additionalFolderBases an array containing the locations of additional base folders to be considered.
     */
    public LocalRepositoryCollector( MavenProject project, //
                                     ArtifactRepository localRepository, File[] additionalFolderBases )
    {
        // The local repo will always be in this list and it is optimal to assume that it will be hit tested the most
        // outside of here.
        localRepositoryPaths.add( localRepository.getBasedir() );

        // Determine the root project

        String projectArtifactId = project.getArtifactId();

        MavenProject parentProject = project.getParent();
        while ( parentProject != null )
        {
            List<?> modules = parentProject.getModules();
            if ( modules.contains( projectArtifactId ) )
            {
                projectArtifactId = parentProject.getArtifactId();
                String parentProjectPath = parentProject.getBasedir().getAbsolutePath();
                localRepositoryPaths.add( parentProjectPath );
            }
            else
            {
                break;
            }

            parentProject = parentProject.getParent();
        }

        // Consider additional folders and their immediate sub folders.

        for ( File additionalFolderBase : additionalFolderBases )
        {
            File[] additionalFolders = additionalFolderBase.listFiles();
            if ( additionalFolders != null )
            {
                for ( File additionalFolder : additionalFolders )
                {
                    if ( additionalFolder.isDirectory() )
                    {
                        localRepositoryPaths.add( additionalFolder.getAbsolutePath() );
                    }
                }
            }
        }
    }

    /**
     * Find a file within our collection of local repositories.
     * 
     * @param filePath the file to be found in a local repo.
     * @return the local repo found that houses the file.
     */
    public String findLocalRepository( String filePath )
    {
        String localRepositoryPathFound = null;
        for ( String localRepositoryPath : localRepositoryPaths )
        {
            if ( filePath.startsWith( localRepositoryPath ) )
            {
                localRepositoryPathFound = localRepositoryPath;
                break;
            }
        }
        return localRepositoryPathFound;
    }

    /**
     * @param path a path to add to the list of paths to be considered as local repos.
     */
    public void addLocalRepositoryPath( String path )
    {
        localRepositoryPaths.add( path );
    }
}
