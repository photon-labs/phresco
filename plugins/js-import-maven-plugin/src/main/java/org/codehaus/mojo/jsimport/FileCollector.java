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
import java.util.Arrays;
import java.util.List;

import org.codehaus.plexus.util.Scanner;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * Given a set of includes and excludes, collect a list of files.
 */
public class FileCollector
{
    /** */
    private BuildContext buildContext;

    /** */
    private final String[] defaultIncludes;

    /** */
    private final String[] defaultExcludes;

    /**
     * @param buildContext the context to use for obtaining the scanner.
     * @param defaultIncludes the array of patterns to include by default.
     * @param defaultExcludes the array of patterns to exclude by default.
     */
    FileCollector( BuildContext buildContext, String[] defaultIncludes, String[] defaultExcludes )
    {
        this.buildContext = buildContext;
        this.defaultIncludes = defaultIncludes;
        this.defaultExcludes = defaultExcludes;
    }

    /**
     * Perform the collection given the source folder and any overrides of the includes and excludes.
     * 
     * @param sourceFolder the source folder.
     * @param includes if not null and not empty then these will override any defaults.
     * @param excludes if not null and not empty then these will override any defaults.
     * @return the collected files.
     */
    public List<String> collectPaths( File sourceFolder, List<String> includes, List<String> excludes )
    {
        Scanner scanner = buildContext.newScanner( sourceFolder, true );

        String[] includesArray = getPatternsOrDefault( includes, defaultIncludes );
        scanner.setIncludes( includesArray );

        String[] excludesArray = getPatternsOrDefault( excludes, defaultExcludes );
        scanner.setExcludes( excludesArray );

        scanner.scan();

        return Arrays.asList( scanner.getIncludedFiles() );
    }

    /**
     * @return property.
     */
    public BuildContext getBuildContext()
    {
        return buildContext;
    }

    private String[] getPatternsOrDefault( List<String> patterns, String[] defaultPatterns )
    {
        if ( patterns == null || patterns.isEmpty() )
        {
            return defaultPatterns;
        }
        else
        {
            return patterns.toArray( new String[patterns.size()] );
        }
    }

    /**
     * @param buildContext property.
     */
    public void setBuildContext( BuildContext buildContext )
    {
        this.buildContext = buildContext;
    }

}
