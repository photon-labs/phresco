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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;

/**
 * Processes an artifact and makes available one or more files depending on whether the artifact points to a js file or
 * a "-www.zip" file respectively.
 * 
 * @author Christopher Hunt
 */
public class JsFileArtifactHandler
{
    private List<File> files = new ArrayList<File>();;

    private File expansionFolder;

    /**
     * @param artifact the artifact to work with. It can either be for a JS file or a zip file with a classifier of
     *            "www".
     * @param targetFolder the folder to merge non js files into.
     * @param workFolder the folder to store any meta data that will help us do things like avoiding unnecessary unzips
     *            (given that it has been done before etc.).
     * @throws IOException if something goes wrong, particularly in the course of zip file expansion.
     */
    public JsFileArtifactHandler( Artifact artifact, File targetFolder, File workFolder )
        throws IOException
    {
        if ( artifact.getType().equals( "js" ) )
        {
            files = new ArrayList<File>( 1 );
            files.add( artifact.getFile() );
        }
        else
        {
            assert artifact.getType().equals( "zip" ) && artifact.getClassifier().equals( "www" );
            File wwwZipFile = artifact.getFile();
            files = expandWwwZipIntoTargetFolder( artifact, wwwZipFile, targetFolder, workFolder );
        }
    }

    private List<File> expandWwwZipIntoTargetFolder( Artifact artifact, File wwwZipFile, File targetFolder,
                                                     File workFolder )
        throws IOException
    {
        List<File> jsFiles = new ArrayList<File>();

        // FIXME: Need to consider scope here i.e. don't create a compile www-zip file for a test run.
        expansionFolder = new File( workFolder, "www-zip" + File.separator + wwwZipFile.getName() );

        // Don't expand if it is already expanded.
        if ( wwwZipFile.lastModified() > expansionFolder.lastModified() )
        {
            String gavPath =
                artifact.getGroupId().replace( '.', File.separatorChar ) + File.separator + artifact.getArtifactId()
                    + File.separator + artifact.getVersion();
            File jsExpansionFolder = new File( expansionFolder, gavPath );

            FileInputStream fis = new FileInputStream( wwwZipFile );
            try
            {
                ZipInputStream zis = new ZipInputStream( new BufferedInputStream( fis ) );
                ZipEntry entry;
                boolean firstEntryProcessed = false;
                int rootFolderPrefixPosn = 0;
                while ( ( entry = zis.getNextEntry() ) != null )
                {
                    // Any non-js files are simply copied into the target folder. js files are copied to a temporary
                    // folder as something else needs to put them into the target folder (there may be processing a js
                    // file along the way). However we ignore minified files when inflating as by convention, we don't
                    // want them as part of the project - projects can minify later.

                    String entryName = entry.getName().substring( rootFolderPrefixPosn );
                    File entryFile = null;
                    if ( !entryName.endsWith( "js" ) )
                    {
                        if ( !entry.isDirectory() )
                        {
                            entryFile = new File( targetFolder, entryName );
                        }
                        else if ( !firstEntryProcessed )
                        {
                            // Its a directory and it is the first thing in the zip file. We don't want to bother with
                            // this directory when creating files as we're more interested in merging it into the
                            // existing target folder.
                            rootFolderPrefixPosn = entryName.length();
                        }
                    }
                    else if ( !entryName.endsWith( "-min.js" ) )
                    {
                        entryFile = new File( jsExpansionFolder, entryName );
                        jsFiles.add( entryFile );
                    }

                    // If we have something interesting to inflate.
                    if ( entryFile != null )
                    {
                        entryFile.getParentFile().mkdirs();
                        FileOutputStream fos = new FileOutputStream( entryFile );
                        BufferedOutputStream dest = null;
                        try
                        {
                            int count;
                            final int bufferSize = 2048;
                            byte data[] = new byte[bufferSize];
                            dest = new BufferedOutputStream( fos, bufferSize );
                            while ( ( count = zis.read( data, 0, bufferSize ) ) != -1 )
                            {
                                dest.write( data, 0, count );
                            }
                            dest.flush();
                        }
                        finally
                        {
                            dest.close();
                        }
                    }

                    firstEntryProcessed = true;
                }
            }
            finally
            {
                fis.close();
            }

            // Override the directory's mod time as that will equal the time it was compressed initially. We're not
            // interested in that - we're interested to learn whether the source zip file is newer that the directory we
            // expand into.
            expansionFolder.setLastModified( wwwZipFile.lastModified() );
        }
        else
        {
            // Nothing changed. Just return a list of files that were previously expanded.
            Collection<File> existingFiles = FileUtils.listFiles( expansionFolder, new String[] { "js" }, true );
            for ( File file : existingFiles )
            {
                if ( !file.getName().endsWith( "-min.js" ) )
                {
                    jsFiles.add( file );
                }
            }
        }

        return jsFiles;
    }

    /**
     * @return a list of the files that were found associated with the artifact.
     */
    public List<File> getFiles()
    {
        return files;
    }

    /**
     * @return the location where the files have been expanded to or null otherwise.
     */
    public File getExpansionFolder()
    {
        return expansionFolder;
    }

}
