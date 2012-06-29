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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericArray;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Responsible for providing various utilities against the persistance of a file dependency graph.
 * 
 * @author huntc
 */
public final class FileDependencyPersistanceUtil
{
    /**
     * The name given to the persistent file dependency graph.
     */
    private static final String FILE_DEPENDENCY_GRAPH_NAME = "FileDependency.avro";

    /**
     * The name given to the persistent file dependency graph's AVRO schema definition.
     */
    private static final String FILE_DEPENDENCY_GRAPH_SCHEMA_NAME = "FileDependency.avpr";

    /**
     * Read in an existing dependency graph and return its modification time. We can then check whether a specific
     * dependency needs to be processed again.
     * 
     * @param workFolder the folder to look for the dependency file in.
     * @param fileDependencies the dependency graph to read.
     * @param fileAssignedGlobals the assigned globals map to read.
     * @return the modification time of the file or 0 if no file exists.
     * @throws MojoExecutionException if we have a problem reading the graph.
     */
    public static long readFileDependencyGraph( File workFolder, Map<String, LinkedHashSet<String>> fileDependencies,
                                                Map<String, String> fileAssignedGlobals )
        throws MojoExecutionException
    {

        long lastModified;

        try
        {
            GenericDatumReader<GenericRecord> genericDatumReader = new GenericDatumReader<GenericRecord>();

            File file = new File( workFolder, FILE_DEPENDENCY_GRAPH_NAME );

            DataFileReader<GenericRecord> dataFileReader = //
                new DataFileReader<GenericRecord>( file, genericDatumReader );
            try
            {
                GenericRecord genericRecord = null;
                while ( dataFileReader.hasNext() )
                {
                    genericRecord = dataFileReader.next( genericRecord );

                    String recordFile = ( (Utf8) genericRecord.get( "file" ) ).toString();

                    GenericData.Array<?> recordImportObjects = (GenericData.Array<?>) genericRecord.get( "imports" );
                    if ( recordImportObjects != null )
                    {
                        LinkedHashSet<String> imports = new LinkedHashSet<String>();
                        for ( Object recordImportObject : recordImportObjects )
                        {
                            imports.add( ( (Utf8) recordImportObject ).toString() );
                        }
                        fileDependencies.put( recordFile, imports );
                    }

                    GenericData.Array<?> recordAssignedVarsObjects =
                        (GenericData.Array<?>) genericRecord.get( "assignedVars" );
                    if ( recordAssignedVarsObjects != null )
                    {
                        for ( Object recordAssignedVarsObject : recordAssignedVarsObjects )
                        {
                            fileAssignedGlobals.put( ( (Utf8) recordAssignedVarsObject ).toString(), recordFile );
                        }
                    }
                }
            }
            finally
            {
                dataFileReader.close();
            }

            lastModified = file.lastModified();

        }
        catch ( FileNotFoundException e )
        {
            lastModified = 0;
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Problem reading file dependency graph.", e );
        }

        return lastModified;
    }

    /**
     * Write out the file dependency graph for processing by other phases.
     * 
     * @param workFolder the folder to write to.
     * @param fileDependencies the dependency graph to write.
     * @param fileAssignedGlobals the assigned globals map to write.
     * @throws MojoExecutionException if we have a problem persisting the graph.
     */
    public static void writeFileDependencyGraph( File workFolder, Map<String, LinkedHashSet<String>> fileDependencies,
                                                 Map<String, String> fileAssignedGlobals )
        throws MojoExecutionException
    {

        try
        {
            workFolder.mkdirs();

            InputStream fileDependencySchemaIs =
                AbstractImportMojo.class.getResourceAsStream( FILE_DEPENDENCY_GRAPH_SCHEMA_NAME );
            Schema fileDependencySchema;
            try
            {
                fileDependencySchema = Schema.parse( fileDependencySchemaIs );
            }
            finally
            {
                fileDependencySchemaIs.close();
            }
            GenericDatumWriter<GenericRecord> genericDatumWriter =
                new GenericDatumWriter<GenericRecord>( fileDependencySchema );
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>( genericDatumWriter );
            try
            {
                dataFileWriter.create( fileDependencySchema, new File( workFolder, FILE_DEPENDENCY_GRAPH_NAME ) );

                // Given that we're driven by a list of files, and that we have
                // both file dependencies and assigned global declarations to
                // store, we build up a new set of files to persist which is the
                // union of the two. We also create a new set of assigned
                // globals given that we are interested in referencing the keys
                // by file and not by var here i.e. fileAssignedGlobal's
                // variable becomes a value and the file it refers to becomes a
                // key.
                Set<String> files = new HashSet<String>( fileDependencies.size() + fileAssignedGlobals.size() );

                files.addAll( fileDependencies.keySet() );

                Map<String, Set<String>> fileAssignedGlobalsByFile =
                    new HashMap<String, Set<String>>( fileAssignedGlobals.size() );
                for ( Map.Entry<String, String> entry : fileAssignedGlobals.entrySet() )
                {
                    String assignedVar = entry.getKey();
                    String file = entry.getValue();
                    Set<String> assignedVars = fileAssignedGlobalsByFile.get( file );
                    if ( assignedVars == null )
                    {
                        assignedVars = new HashSet<String>();
                        fileAssignedGlobalsByFile.put( file, assignedVars );
                    }
                    assignedVars.add( assignedVar );
                }
                files.addAll( fileAssignedGlobalsByFile.keySet() );

                for ( String file : files )
                {
                    GenericRecord genericRecord = new GenericData.Record( fileDependencySchema );

                    LinkedHashSet<String> imports = fileDependencies.get( file );
                    if ( imports != null )
                    {
                        GenericArray<Utf8> genericArray =
                            new GenericData.Array<Utf8>( imports.size(),
                                                         Schema.createArray( Schema.create( Type.STRING ) ) );
                        for ( String importFile : imports )
                        {
                            genericArray.add( new Utf8( importFile ) );
                        }
                        genericRecord.put( "imports", genericArray );
                    }

                    Set<String> assignedVars = fileAssignedGlobalsByFile.get( file );
                    if ( assignedVars != null )
                    {
                        GenericArray<Utf8> genericArray =
                            new GenericData.Array<Utf8>( assignedVars.size(),
                                                         Schema.createArray( Schema.create( Type.STRING ) ) );
                        for ( String assignedVar : assignedVars )
                        {
                            genericArray.add( new Utf8( assignedVar ) );
                        }
                        genericRecord.put( "assignedVars", genericArray );
                    }

                    genericRecord.put( "file", new Utf8( file ) );
                    dataFileWriter.append( genericRecord );
                }
                dataFileWriter.flush();
            }
            finally
            {
                dataFileWriter.close();
            }

        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Problem writing file dependency graph.", e );
        }
    }

    /**
     * Utilities should have no default ctor.
     */
    private FileDependencyPersistanceUtil()
    {
    }
}
