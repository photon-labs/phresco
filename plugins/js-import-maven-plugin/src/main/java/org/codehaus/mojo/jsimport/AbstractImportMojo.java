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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.resolver.filter.AndArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.artifact.resolver.filter.TypeArtifactFilter;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * Mojo for resolving dependencies either declared using an @import javadoc statement or by declaration of uninitialised
 * variables.
 */
public abstract class AbstractImportMojo
    extends AbstractMojo
{
    /**
     * Small structure that permits Artifacts to exist as keys in a hash.
     */
    private class ArtifactId
    {
        private final String groupId;

        private final String artifactId;

        public ArtifactId( String groupId, String artifactId )
        {
            this.groupId = groupId;
            this.artifactId = artifactId;
        }

        @Override
        public boolean equals( Object obj )
        {
            if ( this == obj )
            {
                return true;
            }
            if ( obj == null )
            {
                return false;
            }
            if ( !( obj instanceof ArtifactId ) )
            {
                return false;
            }
            ArtifactId other = (ArtifactId) obj;
            if ( !getOuterType().equals( other.getOuterType() ) )
            {
                return false;
            }
            if ( artifactId == null )
            {
                if ( other.artifactId != null )
                {
                    return false;
                }
            }
            else if ( !artifactId.equals( other.artifactId ) )
            {
                return false;
            }
            if ( groupId == null )
            {
                if ( other.groupId != null )
                {
                    return false;
                }
            }
            else if ( !groupId.equals( other.groupId ) )
            {
                return false;
            }
            return true;
        }

        private AbstractImportMojo getOuterType()
        {
            return AbstractImportMojo.this;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ( ( artifactId == null ) ? 0 : artifactId.hashCode() );
            result = prime * result + ( ( groupId == null ) ? 0 : groupId.hashCode() );
            return result;
        }

    };

    /**
     * The current project scope.
     */
    protected enum Scope
    {
        /** */
        COMPILE,
        /** */
        TEST
    }

    /**
     * The project.
     * 
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The set of dependencies required by the project.
     * 
     * @parameter default-value="${project.dependencies}"
     * @required
     * @readonly
     */
    private List<Dependency> dependencies;

    /**
     * The local repository.
     * 
     * @parameter default-value="${localRepository}"
     * @required
     * @readonly
     */
    private ArtifactRepository localRepository;

    /**
     * The remote repositories.
     * 
     * @parameter default-value="${project.remoteArtifactRepositories}"
     * @required
     * @readonly
     */
    private List<?> remoteRepositories;

    /**
     * Files to include. Defaults to "**\/*.js".
     * 
     * @parameter
     */
    private List<String> includes;

    /**
     * Files to exclude. Nothing is excluded by default.
     * 
     * @parameter
     */
    private List<String> excludes;

    /**
     * true if the standard browser globals should be predefined. @see http://www.jslint.com/lint.html#browser TODO:
     * Provide the other JSLint "assume" options.
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean assumeABrowser;

    /**
     * The project's artifact factory.
     * 
     * @component
     */
    private ArtifactFactory artifactFactory;

    /**
     * The project's meta data source.
     * 
     * @component
     */
    private ArtifactMetadataSource artifactMetadataSource;

    /**
     * For determining the best versions of a set of artifacts.
     * 
     * @component
     */
    private ArtifactCollector artifactCollector;

    /**
     * The project's artifact resolver.
     * 
     * @component
     */
    private ArtifactResolver resolver;

    /**
     * A map of symbols to the script resource that they are defined in.
     */
    private final Map<String, String> fileAssignedGlobals = new HashMap<String, String>();

    /**
     * A map of symbols to the script resource that they are defined in from a compile scope perspective.
     */
    private final Map<String, String> compileFileAssignedGlobals = new HashMap<String, String>();

    /**
     * A map of symbols to the script resource that they are declared (but not initialised) in. This map is updated once
     * per run for all of the parsed js files that are processed. For example if there are no files to process given
     * that non have been updated since the last run then this map will be empty. If only one file was processed then
     * this map will contain unassigned globals just for that one file. This approach results in
     * processSourceFilesForUnassignedSymbolDeclarations to run efficiently as it is driven by this map.
     */
    private final Map<String, Set<String>> fileUnassignedGlobals = new HashMap<String, Set<String>>();

    /**
     * A graph of filenames and their dependencies.
     */
    private final Map<String, LinkedHashSet<String>> fileDependencies = new HashMap<String, LinkedHashSet<String>>();

    /**
     * The build context so that we can tell Maven certain files have changed if required.
     * 
     * @component
     */
    private BuildContext buildContext;

    /**
     * Build dependency graph from the source files.
     * 
     * @param fileDependencyGraphModificationTime the time the graph read in was updated. Used for comparing file times.
     * @param sourceJsFolder Where the source JS files live.
     * @param targetFolder Where the target files live.
     * @param processedFiles the files that have been processed as a consequence of this method.
     * @return true if the graph has been updated by this method.
     * @throws MojoExecutionException if something goes wrong.
     */
    private boolean buildDependencyGraphForChangedSourceFiles( long fileDependencyGraphModificationTime,
                                                               File sourceJsFolder, File targetFolder,
                                                               LinkedHashSet<File> processedFiles )
        throws MojoExecutionException
    {
        File targetJsFolder = new File( targetFolder, "js" );

        boolean fileDependencyGraphUpdated = false;

        FileCollector fileCollector = new FileCollector( buildContext, new String[] { "**/*.js" }, new String[] {} );
        for ( String source : fileCollector.collectPaths( sourceJsFolder, includes, excludes ) )
        {
            File sourceFile = new File( sourceJsFolder, source );
            if ( processFileForImportsAndSymbols( sourceJsFolder, targetJsFolder, sourceFile,
                                                  fileDependencyGraphModificationTime, null ) )
            {
                processedFiles.add( sourceFile );

                getLog().info( "Processed: " + source );

                fileDependencyGraphUpdated = true;
            }
        }

        return fileDependencyGraphUpdated;
    }

    /**
     * Build up the dependency graph and global symbol table by parsing the project's dependencies.
     * 
     * @param scope compile or test.
     * @param fileDependencyGraphModificationTime the time that the dependency graph was updated. Used for file time
     *            comparisons to check the age of them.
     * @param processedFiles an insert-ordered set of files that have been processed.
     * @param targetFolder Where the target files live.
     * @param workFolder Where we can create some long lived information that may be useful to subsequent builds.
     * @param compileWorkFolder Ditto but in the case of testing it points to where the compile working folder is.
     * @return true if the dependency graph has been updated.
     * @throws MojoExecutionException if something bad happens.
     */
    private boolean buildDependencyGraphForDependencies( Scope scope, long fileDependencyGraphModificationTime,
                                                         LinkedHashSet<File> processedFiles, File targetFolder,
                                                         File workFolder, File compileWorkFolder )
        throws MojoExecutionException
    {
        File targetJsFolder = new File( targetFolder, "js" );

        boolean fileDependencyGraphUpdated = false;

        // Determine how we need to filter things both for direct filtering and transitive filtering.

        String scopeStr = ( scope == Scope.COMPILE ? Artifact.SCOPE_COMPILE : Artifact.SCOPE_TEST );

        AndArtifactFilter jsArtifactFilter = new AndArtifactFilter();
        jsArtifactFilter.add( new ScopeArtifactFilter( scopeStr ) );
        jsArtifactFilter.add( new TypeArtifactFilter( "js" ) );

        AndArtifactFilter wwwZipArtifactFilter = new AndArtifactFilter();
        wwwZipArtifactFilter.add( new ScopeArtifactFilter( scopeStr ) );
        wwwZipArtifactFilter.add( new TypeArtifactFilter( "zip" ) );
        wwwZipArtifactFilter.add( new ArtifactFilter()
        {
            public boolean include( Artifact artifact )
            {
                return artifact.hasClassifier() && artifact.getClassifier().equals( "www" );
            }
        } );

        // Determine the artifacts to resolve and associate their transitive dependencies.

        Map<Artifact, LinkedHashSet<Artifact>> directArtifactWithTransitives =
            new HashMap<Artifact, LinkedHashSet<Artifact>>( dependencies.size() );

        Set<Artifact> directArtifacts = new HashSet<Artifact>( dependencies.size() );
        LinkedHashSet<Artifact> transitiveArtifacts = new LinkedHashSet<Artifact>();

        for ( Dependency dependency : dependencies )
        {
            // Process imports and symbols of this dependencies' transitives
            // first.
            Artifact directArtifact =
                artifactFactory.createDependencyArtifact( dependency.getGroupId(), dependency.getArtifactId(),
                                                          VersionRange.createFromVersion( dependency.getVersion() ),
                                                          dependency.getType(), dependency.getClassifier(),
                                                          dependency.getScope() );

            if ( !jsArtifactFilter.include( directArtifact ) && !wwwZipArtifactFilter.include( directArtifact ) )
            {
                continue;
            }

            Set<Artifact> artifactsToResolve = new HashSet<Artifact>( 1 );
            artifactsToResolve.add( directArtifact );

            ArtifactResolutionResult result;
            try
            {
                result =
                    resolver.resolveTransitively( artifactsToResolve, project.getArtifact(), remoteRepositories,
                                                  localRepository, artifactMetadataSource );
            }
            catch ( ArtifactResolutionException e )
            {
                throw new MojoExecutionException( "Problem resolving dependencies", e );
            }
            catch ( ArtifactNotFoundException e )
            {
                throw new MojoExecutionException( "Problem resolving dependencies", e );
            }

            // Associate the transitive dependencies with the direct dependency and aggregate all transitives for
            // collection later.

            LinkedHashSet<Artifact> directTransitiveArtifacts =
                new LinkedHashSet<Artifact>( result.getArtifacts().size() );
            for ( Object o : result.getArtifacts() )
            {
                Artifact resolvedArtifact = (Artifact) o;
                if ( jsArtifactFilter.include( resolvedArtifact ) && //
                    !resolvedArtifact.equals( directArtifact ) )
                {
                    directTransitiveArtifacts.add( resolvedArtifact );
                }
            }

            directArtifacts.add( directArtifact );
            transitiveArtifacts.addAll( directTransitiveArtifacts );
            directArtifactWithTransitives.put( directArtifact, directTransitiveArtifacts );
        }

        // Resolve the best versions of the transitives to use by asking Maven to collect them.

        Set<Artifact> collectedArtifacts = new HashSet<Artifact>( directArtifacts.size() + transitiveArtifacts.size() );
        Map<ArtifactId, Artifact> indexedCollectedDependencies =
            new HashMap<ArtifactId, Artifact>( collectedArtifacts.size() );
        try
        {
            // Note that we must pass an insert-order set into the collector. The collector appears to assume that order
            // is significant, even though it is undocumented.
            LinkedHashSet<Artifact> collectableArtifacts = new LinkedHashSet<Artifact>( directArtifacts );
            collectableArtifacts.addAll( transitiveArtifacts );

            ArtifactResolutionResult resolutionResult =
                artifactCollector.collect( collectableArtifacts, project.getArtifact(), localRepository,
                                           remoteRepositories, artifactMetadataSource, null, //
                                           Collections.EMPTY_LIST );
            for ( Object o : resolutionResult.getArtifacts() )
            {
                Artifact collectedArtifact = (Artifact) o;
                collectedArtifacts.add( collectedArtifact );

                // Build up an index of of collected transitive dependencies so that we can we refer back to them as we
                // process the direct dependencies.
                ArtifactId collectedArtifactId =
                    new ArtifactId( collectedArtifact.getGroupId(), collectedArtifact.getArtifactId() );
                indexedCollectedDependencies.put( collectedArtifactId, collectedArtifact );
            }

            if ( getLog().isDebugEnabled() )
            {
                getLog().debug( "Dependencies collected: " + collectedArtifacts.toString() );
            }
        }
        catch ( ArtifactResolutionException e )
        {
            throw new MojoExecutionException( "Cannot collect dependencies", e );
        }

        // Now go through direct artifacts and process their transitives.

        LocalRepositoryCollector localRepositoryCollector =
            new LocalRepositoryCollector( project, localRepository, new File[] {} );

        for ( Entry<Artifact, LinkedHashSet<Artifact>> entry : directArtifactWithTransitives.entrySet() )
        {
            Artifact directArtifact = entry.getKey();
            LinkedHashSet<Artifact> directArtifactTransitives = entry.getValue();

            LinkedHashSet<String> transitivesAsImports = new LinkedHashSet<String>( directArtifactTransitives.size() );

            for ( Object o : directArtifactTransitives )
            {
                Artifact directTransitiveArtifact = (Artifact) o;

                // Get the transitive artifact that Maven decided was the best to use.

                ArtifactId directTransitiveArtifactId =
                    new ArtifactId( directTransitiveArtifact.getGroupId(), directTransitiveArtifact.getArtifactId() );
                Artifact transitiveArtifact = indexedCollectedDependencies.get( directTransitiveArtifactId );

                List<File> transitiveArtifactFiles =
                    getArtifactFiles( transitiveArtifact, targetFolder, workFolder, compileWorkFolder,
                                      localRepositoryCollector );

                // Only process this dependency if we've not done so
                // already.
                for ( File transitiveArtifactFile : transitiveArtifactFiles )
                {
                    if ( !processedFiles.contains( transitiveArtifactFile ) )
                    {
                        String localRepository =
                            localRepositoryCollector.findLocalRepository( transitiveArtifactFile.getAbsolutePath() );
                        if ( localRepository != null )
                        {
                            if ( processFileForImportsAndSymbols( new File( localRepository ), targetJsFolder,
                                                                  transitiveArtifactFile,
                                                                  fileDependencyGraphModificationTime,
                                                                  directArtifactTransitives ) )
                            {

                                processedFiles.add( transitiveArtifactFile );

                                fileDependencyGraphUpdated = true;
                            }
                        }
                        else
                        {
                            throw new MojoExecutionException(
                                                              "Problem determining local repository for transitive file: "
                                                                  + transitiveArtifactFile );
                        }
                    }

                    // Add transitives to the artifacts set of dependencies -
                    // as if they were @import statements themselves.
                    transitivesAsImports.add( transitiveArtifactFile.getPath() );
                }
            }

            // Now deal with the pom specified dependency.
            List<File> artifactFiles =
                getArtifactFiles( directArtifact, targetFolder, workFolder, compileWorkFolder, localRepositoryCollector );
            for ( File artifactFile : artifactFiles )
            {
                String artifactPath = artifactFile.getAbsolutePath();

                // Process imports and symbols of this dependency if we've not
                // already done so.
                if ( !processedFiles.contains( artifactFile ) )
                {
                    String localRepository =
                        localRepositoryCollector.findLocalRepository( artifactFile.getAbsolutePath() );
                    if ( localRepository != null )
                    {
                        if ( processFileForImportsAndSymbols( new File( localRepository ), targetJsFolder,
                                                              artifactFile, fileDependencyGraphModificationTime, null ) )
                        {
                            processedFiles.add( artifactFile );

                            fileDependencyGraphUpdated = true;
                        }
                    }
                    else
                    {
                        throw new MojoExecutionException( "Problem determining local repository for file: "
                            + artifactFile );
                    }
                }

                // Add in our transitives to the dependency graph if they're not
                // already there.
                LinkedHashSet<String> existingImports = fileDependencies.get( artifactPath );
                if ( existingImports.addAll( transitivesAsImports ) )
                {
                    if ( getLog().isDebugEnabled() )
                    {
                        getLog().debug( "Using transitives as import: " + transitivesAsImports + " for file: "
                                            + artifactPath );
                    }
                    fileDependencyGraphUpdated = true;
                }
            }

        }

        return fileDependencyGraphUpdated;
    }

    /**
     * Convenience method for interacting with a JsFileArtifactHandler object.
     * 
     * @param artifact the artifact to use.
     * @param targetFolder the target folder.
     * @param workFolder the work folder.
     * @param compileWorkFolder Ditto but in the case of test scope this will point to the work folder for compile
     *            artifacts.
     * @param localRepositoryCollector the repository collector.
     * @return the list of files associated with the artifact.
     * @throws MojoExecutionException if something goes wrong.
     */
    private List<File> getArtifactFiles( Artifact artifact, File targetFolder, File workFolder, File compileWorkFolder,
                                         LocalRepositoryCollector localRepositoryCollector )
        throws MojoExecutionException
    {
        JsFileArtifactHandler handler;
        try
        {
            boolean scopeCompile;
            if ( artifact.getScope() == null )
            {
                scopeCompile = true;
            }
            else if ( artifact.getScope().equals( Artifact.SCOPE_COMPILE ) )
            {
                scopeCompile = true;
            }
            else
            {
                scopeCompile = false;
            }
            handler = new JsFileArtifactHandler( artifact, targetFolder, scopeCompile ? compileWorkFolder : workFolder );
            File expansionFolder = handler.getExpansionFolder();
            if ( expansionFolder != null )
            {
                localRepositoryCollector.addLocalRepositoryPath( expansionFolder.getAbsolutePath() );
            }
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Cannot get js files from transitive artifact", e );
        }
        return handler.getFiles();
    }

    /**
     * Perform the goal of this mojo.
     * 
     * @param sourceJsFolder the folder where the source js files reside.
     * @param targetFolder the folder where the target files reside.
     * @param workFolder the folder where our work files can be found.
     * @param scope scope the scope of the dependencies we are to search for.
     * @throws MojoExecutionException if there is an execution failure.
     */
    protected void doExecute( File sourceJsFolder, File targetFolder, File workFolder, Scope scope )
        throws MojoExecutionException
    {
        // Load in any existing dependency graph - we only build what we need
        // to.
        long fileDependencyGraphModificationTime =
            FileDependencyPersistanceUtil.readFileDependencyGraph( workFolder, fileDependencies, fileAssignedGlobals );

        // Build hashes of the dependency graph so that we can quickly compare whether they have changed.
        int fileDependencyGraphHashCode = fileDependencies.hashCode();
        int fileAssignedGlobalsHashCode = fileAssignedGlobals.hashCode();

        // If we are in test scope then also load in the compile scoped dependency information as we need to resolve
        // against this also.
        File compileWorkFolder;
        if ( scope == Scope.TEST )
        {
            compileWorkFolder = new File( workFolder.getParentFile(), "main" );
            Map<String, LinkedHashSet<String>> compileFileDependencies = new HashMap<String, LinkedHashSet<String>>();
            FileDependencyPersistanceUtil.readFileDependencyGraph( compileWorkFolder, compileFileDependencies, //
                                                                   compileFileAssignedGlobals );
        }
        else
        {
            compileWorkFolder = workFolder;
        }

        // Keep a flag to signal whether the graph has been updated.
        boolean fileDependencyGraphUpdated;

        // Clear the dependency graph if it is invalid.
        if ( !isDependencyGraphValid() )
        {
            fileDependencies.clear();
            fileAssignedGlobals.clear();
            fileDependencyGraphModificationTime = 0L;
            fileDependencyGraphUpdated = true;
        }
        else
        {
            fileDependencyGraphUpdated = false;
        }

        // Build dependency graph and symbol table against each js file declared
        // as a dependency.

        LinkedHashSet<File> processedFiles = new LinkedHashSet<File>();
        if ( buildDependencyGraphForDependencies( scope, fileDependencyGraphModificationTime, processedFiles,
                                                  targetFolder, workFolder, compileWorkFolder ) )
        {
            fileDependencyGraphUpdated = true;
        }

        // Process all of our JS files and build their dependency
        // graphs and symbol tables.
        if ( buildDependencyGraphForChangedSourceFiles( fileDependencyGraphModificationTime, sourceJsFolder,
                                                        targetFolder, processedFiles ) )
        {
            fileDependencyGraphUpdated = true;
        }

        // Given that we now have all of the symbols mapped by file we
        // now need to go through our artifacts (dependencies and source files)
        // again looking for those that reference them. We add to the file
        // dependencies as a result.
        processSourceFilesForUnassignedSymbolDeclarations();

        // We have have a complete dependency graph. We will now persist the
        // graph so that other phases can utilise it (if things have truly changed).
        if ( fileDependencyGraphUpdated && ( fileDependencies.hashCode() != fileDependencyGraphHashCode || //
            fileAssignedGlobals.hashCode() != fileAssignedGlobalsHashCode ) )
        {
            FileDependencyPersistanceUtil.writeFileDependencyGraph( workFolder, fileDependencies, fileAssignedGlobals );
        }
    }

    /**
     * @return property.
     */
    public org.apache.maven.artifact.factory.ArtifactFactory getArtifactFactory()
    {
        return artifactFactory;
    }

    /**
     * @return property.
     */
    public ArtifactMetadataSource getArtifactMetadataSource()
    {
        return artifactMetadataSource;
    }

    /**
     * @return property.
     */
    public List<Dependency> getDependencies()
    {
        return dependencies;
    }

    /**
     * @return property.
     */
    public List<String> getExcludes()
    {
        return excludes;
    }

    /**
     * @return property.
     */
    public Map<String, String> getFileAssignedGlobals()
    {
        return fileAssignedGlobals;
    }

    /**
     * @return property.
     */
    public Map<String, LinkedHashSet<String>> getFileDependencies()
    {
        return fileDependencies;
    }

    /**
     * @return property.
     */
    public Map<String, Set<String>> getFileUnassignedGlobals()
    {
        return fileUnassignedGlobals;
    }

    /**
     * @return property.
     */
    public List<String> getIncludes()
    {
        return includes;
    }

    /**
     * @return property.
     */
    public ArtifactRepository getLocalRepository()
    {
        return localRepository;
    }

    /**
     * @return property.
     */
    public MavenProject getProject()
    {
        return project;
    }

    /**
     * @return property.
     */
    public List<?> getRemoteRepositories()
    {
        return remoteRepositories;
    }

    /**
     * @return property.
     */
    public ArtifactResolver getResolver()
    {
        return resolver;
    }

    /**
     * @return property.
     */
    public boolean isAssumeABrowser()
    {
        return assumeABrowser;
    }

    /**
     * Check the integrity of the dependency graph. Essentially if any files that were there in a previous run but are
     * no longer there then we must rebuild the graph.
     */
    private boolean isDependencyGraphValid()
    {
        boolean valid = true;

        for ( String fileDependencyFilename : fileDependencies.keySet() )
        {
            File fileDependencyFile = new File( fileDependencyFilename );
            if ( !fileDependencyFile.exists() )
            {
                if ( getLog().isDebugEnabled() )
                {
                    getLog().debug( "Found a file that has been removed so rebuilding graph. File: "
                                        + fileDependencyFilename );
                }

                valid = false;
                break;
            }
        }

        return valid;
    }

    /**
     * Match a group and artifact against our list of dependencies.
     * 
     * @param groupId the group id.
     * @param artifactId the artifact id.
     * @return the dependency or null if one cannot be found.
     */
    private Dependency matchDirectDependency( String groupId, String artifactId )
    {
        Dependency dependencyFound = null;
        for ( Dependency dependency : dependencies )
        {
            if ( dependency.getGroupId().equalsIgnoreCase( groupId )
                && dependency.getArtifactId().equalsIgnoreCase( artifactId )
                && dependency.getType().equalsIgnoreCase( "js" ) )
            {
                dependencyFound = dependency;
                break;
            }
        }

        return dependencyFound;
    }

    /**
     * Find a dependency in our set of transitive dependencies.
     * 
     * @param groupId the group to match.
     * @param artifactId the artifact to match.
     * @param transitiveArtifacts artifacts to match against.
     * @return an artifact that has been matched or null if none can be found.
     */
    private Artifact matchTransitiveDependency( String groupId, String artifactId, Set<?> transitiveArtifacts )
    {
        Artifact artifactFound = null;
        for ( Object transitiveArtifactObject : transitiveArtifacts )
        {
            Artifact transitiveArtifact = (Artifact) transitiveArtifactObject;
            if ( transitiveArtifact.getGroupId().equalsIgnoreCase( groupId )
                && transitiveArtifact.getArtifactId().equalsIgnoreCase( artifactId )
                && transitiveArtifact.getType().equalsIgnoreCase( "js" ) )
            {
                artifactFound = transitiveArtifact;
                break;
            }
        }

        return artifactFound;
    }

    /**
     * Process a file for import declarations and for the symbols used.
     * 
     * @param sourceFolder the base directory of the file being processed.
     * @param targetFolder where to write files to.
     * @param sourceFile the file to process.
     * @param fileDependencyGraphModificationTime the last time the dependency graph was updated or 0 if we do not have
     *            one.
     * @param transitiveArtifacts any transititive artifacts to match imports against, or null if no matching is to be
     *            done.
     * @return true if processing occurred.
     * @throws MojoExecutionException if something goes wrong.
     */
    protected boolean processFileForImportsAndSymbols( File sourceFolder, File targetFolder, File sourceFile,
                                                       long fileDependencyGraphModificationTime,
                                                       Set<?> transitiveArtifacts )
        throws MojoExecutionException
    {
        URI sourceFileRelUri = sourceFolder.toURI().relativize( sourceFile.toURI() );
        File targetFile = new File( targetFolder, sourceFileRelUri.toString() );
        String sourceFilePath = sourceFile.getAbsolutePath();

        // Quickly jump out if this particular artifact has not been updated
        // recently, or we don't have an entry for it in our dependency graph. The latter can happen if we build a multi
        // module project from the parent folder and then build a specific module from its own folder. File dependencies
        // in these scenarios can come from the module's target folder or the local m2 repo respectively.
        if ( sourceFile.lastModified() <= fileDependencyGraphModificationTime
            && fileDependencies.containsKey( sourceFilePath ) )
        {
            if ( getLog().isDebugEnabled() )
            {
                getLog().debug( "Skipping unchanged JS file: " + sourceFileRelUri );
            }
            return false;
        }

        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "Parsing JS file: " + sourceFileRelUri );
        }

        try
        {
            // Tokenise the JS file resulting in collections of assigned and
            // unassigned globals, and import statements.
            CharStream cs = new ANTLRFileStream( sourceFilePath );

            ECMAScriptLexer lexer = new ECMAScriptLexer( cs );
            lexer.setSourceFile( sourceFileRelUri, sourceFile.getName() );

            CommonTokenStream tokenStream = new CommonTokenStream();
            tokenStream.setTokenSource( lexer );
            writeTokenStream( cs, tokenStream, targetFile );

            if ( getLog().isDebugEnabled() )
            {
                getLog().debug( "Assigned globals: " + lexer.getAssignedGlobalVars().toString() );
                getLog().debug( "Unassigned globals: " + lexer.getUnassignedGlobalVars().toString() );
                getLog().debug( "Imports: " + lexer.getImportGavs().toString() );
            }

            // For each assigned variable map it against the file we're dealing
            // with for later reference. An assigned variable indicates that
            // unassigned declarations of the same variable want this file
            // imported.
            for ( String assignedGlobalVar : lexer.getAssignedGlobalVars() )
            {
                fileAssignedGlobals.put( assignedGlobalVar, sourceFilePath );
            }

            // For each unassigned variable map it against the file we're
            // dealing with for later reference. An unassigned variable
            // indicates that we want to import a file where the variable is
            // assigned.
            Set<String> vars = new HashSet<String>( lexer.getUnassignedGlobalVars() );
            if ( assumeABrowser )
            {
                // If we assume a browser then take out all of the ones declared for it. Otherwise we'll be looking for
                // dependencies that do not belong to the project.
                vars.removeAll( Arrays.asList( new String[] { "clearInterval", "clearTimeout", "document", "exports",
                    "event", "frames", "history", "Image", "location", "module", "name", "navigator", "Option",
                    "parent", "require", "screen", "setInterval", "setTimeout", "window", "XMLHttpRequest" } ) );
            }
            fileUnassignedGlobals.put( sourceFilePath, vars );

            // For each import found resolve its file name and then note it as a
            // dependency of this particular js file. We update any existing
            // dependency edges if they exist given that we've determined this
            // part of the graph needs re-construction.
            LinkedHashSet<String> importedDependencies = new LinkedHashSet<String>( lexer.getImportGavs().size() );
            fileDependencies.put( sourceFilePath, importedDependencies );

            for ( ECMAScriptLexer.GAV importGav : lexer.getImportGavs() )
            {
                Artifact artifactFound;
                Dependency dependencyFound = matchDirectDependency( importGav.groupId, importGav.artifactId );
                if ( dependencyFound == null )
                {
                    if ( transitiveArtifacts != null )
                    {
                        artifactFound =
                            matchTransitiveDependency( importGav.groupId, importGav.artifactId, transitiveArtifacts );
                    }
                    else
                    {
                        artifactFound = null;
                    }
                    if ( artifactFound == null )
                    {
                        getLog().error( "Dependency not found: " + importGav.groupId + ":" + importGav.artifactId );
                        throw new MojoExecutionException( "Build stopping given dependency issue." );
                    }
                }
                else
                {
                    artifactFound = resolveArtifact( dependencyFound );
                }

                /**
                 * Store the dependency as an edge against our dependency graph.
                 */
                importedDependencies.add( artifactFound.getFile().getPath() );

                if ( getLog().isDebugEnabled() )
                {
                    getLog().debug( "Found import: " + importGav.groupId + ":" + importGav.artifactId + " ("
                                        + artifactFound.getFile().getName() + ") for file: " + sourceFileRelUri );
                }
            }

        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Problem opening file: " + sourceFileRelUri, e );
        }

        return true;
    }

    /**
     * Go through all of unassigned globals and enhance the file dependencies collection given the file that they are
     * declared in.
     * 
     * @throws MojoExecutionException if something goes wrong.
     */
    protected void processSourceFilesForUnassignedSymbolDeclarations()
        throws MojoExecutionException
    {

        // For all of the js files containing unassigned vars...
        Set<Entry<String, Set<String>>> entrySet = fileUnassignedGlobals.entrySet();
        for ( Entry<String, Set<String>> entry : entrySet )
        {

            // For each of the unassigned vars...
            String variableDeclFile = entry.getKey();
            for ( String variableName : entry.getValue() )
            {
                // Resolve the file that contains the var's assignment and throw
                // an exception if it cannot be found.
                String variableAssignedFile = fileAssignedGlobals.get( variableName );
                if ( variableAssignedFile == null && compileFileAssignedGlobals != null )
                {
                    variableAssignedFile = compileFileAssignedGlobals.get( variableName );
                }

                // We've tried pretty hard, but we can't find a dependency. Time to barf.
                if ( variableAssignedFile == null )
                {
                    getLog().error( "Dependency not found: " + variableName + " in file: " + variableDeclFile );
                    throw new MojoExecutionException( "Build stopping given dependency issue." );

                }

                // Enhance the declaring file's graph of dependencies.
                LinkedHashSet<String> variableDeclFileImports = fileDependencies.get( variableDeclFile );
                if ( variableDeclFileImports == null )
                {
                    variableDeclFileImports = new LinkedHashSet<String>();
                    fileDependencies.put( variableDeclFile, variableDeclFileImports );
                }

                variableDeclFileImports.add( variableAssignedFile );
            }
        }
    }

    /**
     * Resolve an artifact given a dependency.
     * 
     * @param dependency the dependency to resolve.
     * @return the artifact.
     * @throws MojoExecutionException if the dependency cannot be resolved.
     */
    private Artifact resolveArtifact( Dependency dependency )
        throws MojoExecutionException
    {
        Artifact artifact =
            artifactFactory.createArtifactWithClassifier( dependency.getGroupId(), dependency.getArtifactId(),
                                                          dependency.getVersion(), dependency.getType(),
                                                          dependency.getClassifier() );
        try
        {
            resolver.resolve( artifact, remoteRepositories, localRepository );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new MojoExecutionException( dependency.toString(), e );
        }
        catch ( ArtifactNotFoundException e )
        {
            throw new MojoExecutionException( dependency.toString(), e );
        }
        return artifact;
    }

    /**
     * @param artifactFactory set property.
     */
    public void setArtifactFactory( ArtifactFactory artifactFactory )
    {
        this.artifactFactory = artifactFactory;
    }

    /**
     * @param artifactMetadataSource set property.
     */
    public void setArtifactMetadataSource( ArtifactMetadataSource artifactMetadataSource )
    {
        this.artifactMetadataSource = artifactMetadataSource;
    }

    /**
     * @param assumeABrowser set property.
     */
    public void setAssumeABrowser( boolean assumeABrowser )
    {
        this.assumeABrowser = assumeABrowser;
    }

    /**
     * @param dependencies set property.
     */
    public void setDependencies( List<Dependency> dependencies )
    {
        this.dependencies = dependencies;
    }

    /**
     * @param excludes set property.
     */
    public void setExcludes( List<String> excludes )
    {
        this.excludes = excludes;
    }

    /**
     * @param includes set property.
     */
    public void setIncludes( List<String> includes )
    {
        this.includes = includes;
    }

    /**
     * @param localRepository set property.
     */
    public void setLocalRepository( ArtifactRepository localRepository )
    {
        this.localRepository = localRepository;
    }

    /**
     * @param project set property.
     */
    public void setProject( MavenProject project )
    {
        this.project = project;
    }

    /**
     * @param remoteRepositories set property.
     */
    public void setRemoteRepositories( List<?> remoteRepositories )
    {
        this.remoteRepositories = remoteRepositories;
    }

    /**
     * @param resolver set property.
     */
    public void setResolver( ArtifactResolver resolver )
    {
        this.resolver = resolver;
    }

    private void writeTokenStream( CharStream cs, CommonTokenStream tokenStream, File outputFile )
        throws IOException
    {
        OutputStream os = new BufferedOutputStream( FileUtils.openOutputStream( outputFile ) );
        try
        {
            List<?> tokens = tokenStream.getTokens();
            cs.seek( 0 );
            for ( Object tokenObject : tokens )
            {
                CommonToken token = (CommonToken) tokenObject;
                if ( token.getType() == ECMAScriptLexer.MODULE_DECL || token.getType() == ECMAScriptLexer.REQUIRE_DECL )
                {
                    int startIndex = token.getStartIndex();
                    while ( cs.index() < startIndex )
                    {
                        int streamChar = cs.LA( 1 );
                        if ( streamChar == CharStream.EOF )
                        {
                            break;
                        }
                        os.write( streamChar );
                        cs.consume();
                    }

                    CharacterIterator iter = new StringCharacterIterator( token.getText() );
                    for ( char tokenChar = iter.first(); tokenChar != CharacterIterator.DONE; tokenChar = iter.next() )
                    {
                        os.write( tokenChar );
                    }

                    cs.seek( token.getStopIndex() + 1 );
                }
            }

            int streamChar;
            while ( ( streamChar = cs.LA( 1 ) ) != CharStream.EOF )
            {
                os.write( streamChar );
                cs.consume();
            }
        }
        finally
        {
            os.close();
        }
    }
}
