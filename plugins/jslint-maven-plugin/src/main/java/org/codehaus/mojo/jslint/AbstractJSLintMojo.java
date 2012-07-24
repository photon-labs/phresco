package org.codehaus.mojo.jslint;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.mojo.jslint.FilteredDirectoryScanner.Filter;
import org.codehaus.plexus.util.DirectoryScanner;

import com.googlecode.jslint4java.Issue;
import com.googlecode.jslint4java.JSLint;
import com.googlecode.jslint4java.JSLintBuilder;
import com.googlecode.jslint4java.JSLintResult;
import com.googlecode.jslint4java.Option;

/**
 * Mojo to invoke JSLint verification on JS sources.
 * 
 * @author huntc
 */
public abstract class AbstractJSLintMojo
    extends AbstractMojo
{
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
     * The encoding to use for the JS files.
     * 
     * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
     */
    private String encoding;

    /**
     * Stop the build if things go wrong according to JSLint.
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean failOnIssues;

    /**
     * true if ADsafe rules should be enforced. @see http://www.ADsafe.org/.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean adsafe;

    /**
     * true if bitwise operators should not be allowed. @see http://www.jslint.com/lint.html#bitwise
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean disallowBitwiseOperators;

    /**
     * true if the standard browser globals should be predefined. @see http://www.jslint.com/lint.html#browser
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean assumeABrowser;

    /**
     * true if Initial Caps must be used with constructor functions. @see http://www.jslint.com/lint.html#new
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean requireInitialCapsForConstructors;

    /**
     * true if CSS workarounds should be tolerated. @see http://www.jslint.com/lint.html#css
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateCSSWorkarounds;

    /**
     * true if debugger statements should be allowed. Set this option to false before going into production.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateDebuggerStatements;

    /**
     * true if browser globals that are useful in development should be predefined. @see
     * morehttp://www.jslint.com/lint.html#devel
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean assumeConsoleAlertEtc;

    /**
     * true if the continuation statement should be tolerated.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateContinuation;

    /**
     * true if ES5 syntax should be allowed.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateES5Syntax;

    /**
     * true if eval should be allowed. @see http://www.jslint.com/lint.html#evil
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateEval;

    /**
     * true if unfiltered for in statements should be allowed. @see http://www.jslint.com/lint.html#forin
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean tolerateUnfilteredForIn;

    /**
     * true if HTML fragments should be allowed. @see http://www.jslint.com/lint.html#html
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateHTMLFragments;

    /**
     * true if the ES5 "use strict"; pragma is required. Do not use this option carelessly.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean strictWhiteSpace;

    /**
     * The number of spaces used for indentation.
     * 
     * @parameter default-value="4"
     * @required
     */
    private Integer strictWhiteSpaceIndentation;

    /**
     * The maximum length of a source line.
     * 
     * @parameter
     */
    private Integer maximumLengthOfLine;

    /**
     * The maximum number of warnings reported.
     * 
     * @parameter default-value="50"
     * @required
     */
    private Integer maximumNumberOfErrors;

    /**
     * true if upper case HTML should be allowed.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateHTMLCase;

    /**
     * true if names should be checked for initial or trailing underbars.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean disallowDanglingUnderbarInIdentifiers;

    /**
     * true if HTML event handlers should be allowed. @see http://www.jslint.com/lint.html#html
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateHTMLEventHandlers;

    /**
     * true if only one var statement per function should be allowed. @see http://www.jslint.com/lint.html#scope
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean allowOneVarStatementPerFunction;

    /**
     * true if the scan should stop on first error.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean stopOnFirstError;

    /**
     * true if ++ and -- should not be allowed. @see http://www.jslint.com/lint.html#inc
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean disallowIncrAndDecr;

    /**
     * An array of strings, the names of predefined global variables. predef is used with the option object, but not
     * with the jslint comment. Use the var statement to declare global variables in a script file.
     * 
     * @parameter default-value="false"
     * @required
     */
    private String predefinedVars;

    /**
     * true if . and [^...] should not be allowed in RegExp literals. These forms should not be used when validating in
     * secure applications.
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean disallowInsecureCharsInRegExp;

    /**
     * true if the Rhino environment globals should be predefined. @see http://www.jslint.com/lint.html#rhino
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean assumeRhino;

    /**
     * true if the safe subset rules are enforced. These rules are used by <a href="http://www.ADsafe.org/">ADsafe</a>.
     * It enforces the safe subset rules but not the widget structure rules.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean safeSubset;

    /**
     * true if the ES5 "use strict"; pragma is required. Do not use this option carelessly.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean requireUseStrict;

    /**
     * true if subscript notation may be used for expressions better expressed in dot notation.
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean tolerateInefficientSubscripting;

    /**
     * true if variables must be declared before used. @see http://www.jslint.com/lint.html#undefined
     * 
     * @parameter default-value="true"
     * @required
     */
    private boolean disallowUndefinedVariables;

    /**
     * true if the <a href="http://widgets.yahoo.com/gallery/view.php?widget=37484">Yahoo Widgets</a> globals should be
     * predefined. @see http://www.jslint.com/lint.html#widget
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean assumeAYahooWidget;

    /**
     * true if the Windows globals should be predefined. @see http://www.jslint.com/lint.html#windows
     * 
     * @parameter default-value="false"
     * @required
     */
    private boolean assumeWindows;

    /**
     * The linter to use.
     */
    private JSLint jsLint;

    private List<Issue> checkFileForIssues( File sourceFile )
        throws MojoExecutionException
    {
        try
        {
            Reader fileReader = new InputStreamReader( new FileInputStream( sourceFile ), encoding );
            BufferedReader bufferedFileReader = new BufferedReader( fileReader );
            try
            {
                JSLintResult result = jsLint.lint( sourceFile.getName(), bufferedFileReader );
                return result.getIssues();
            }
            finally
            {
                bufferedFileReader.close();
            }
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Problem while parsing file: " + sourceFile.getName(), e );
        }
    }

    /**
     * Execute the MOJO.
     * 
     * @throws MojoExecutionException if something unexpected occurs.
     */
    public void execute()
        throws MojoExecutionException
    {
        // Get the modification time of the last file we previously linted (if available).
        File lastModifiedFile = new File( getWorkFolder(), "lastModified" );

        long modifiedSinceTime = lastModifiedFile.lastModified();

        // Collect JSLint issues and also note the latest modification time of the file that we process.
        initJSLint();
        List<Issue> issues = new ArrayList<Issue>();
        long latestModificationTime = 0L;
        for ( String pathOfJsFile : getPathsOfJsFiles( modifiedSinceTime ) )
        {
            getLog().info( "Parsing: " + pathOfJsFile );

            File sourceFolder = getSourceJsFolder();
            File jsFile = new File( sourceFolder, pathOfJsFile );

            List<Issue> issuesOfFile = checkFileForIssues( jsFile );
            issues.addAll( issuesOfFile );

            long lastModified = jsFile.lastModified();
            if ( lastModified > latestModificationTime )
            {
                latestModificationTime = lastModified;
            }
        }

        // Report issues
        for ( Issue issue : issues )
        {
            if ( failOnIssues )
            {
                getLog().error( issue.toString() );
            }
            else
            {
                getLog().warn( issue.toString() );
            }
        }

        // Optionally fail on issues.
        if ( failOnIssues && !issues.isEmpty() )
        {
            throw new MojoExecutionException( "Issues found in project." );
        }

        // Write out the latest modification time we have for a JS file that we have linted so that next time, we only
        // look at what we need to look at.
        if ( latestModificationTime > modifiedSinceTime )
        {
            try
            {
                getWorkFolder().mkdirs();
                if ( !lastModifiedFile.exists() )
                {
                    lastModifiedFile.createNewFile();
                }
                lastModifiedFile.setLastModified( latestModificationTime );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Cannot write to working folder.", e );
            }
        }
    }

    /**
     * @return the list of excludes by default.
     */
    protected String[] getDefaultExcludes()
    {
//        return new String[0];
        return new String[] { "*.min.js", "**/*.min.js" };
    }

    /**
     * @return the list of includes by default.
     */
    protected String[] getDefaultIncludes()
    {
        return new String[] { "**/*.js" };
    }

    /**
     * @return property.
     */
    public String getEncoding()
    {
        return encoding;
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
    public JSLint getJsLint()
    {
        return jsLint;
    }

    /**
     * @return property.
     */
    public Integer getMaximumLengthOfLine()
    {
        return maximumLengthOfLine;
    }

    /**
     * @return property.
     */
    public Integer getMaximumNumberOfErrors()
    {
        return maximumNumberOfErrors;
    }

    /**
     * Return the paths of JS files that have been modified since the modifiedSince time.
     * 
     * @param modifiedSince the cutoff time. All files that have a modification date beyond this time will be returned
     *            subject to exclusion patterns.
     * @return the array of file paths that are found.
     */
    private String[] getPathsOfJsFiles( final long modifiedSince )
    {
        final File sourceFolder = getSourceJsFolder();

        DirectoryScanner scanner = new FilteredDirectoryScanner( new Filter()
        {
            public boolean apply( String pathname )
            {
                // Only allow this to be included if the file has been modified since the modification date we are
                // interested in.
                File sourceFile = new File( sourceFolder, pathname );
                long lastModified = sourceFile.lastModified();
                return ( lastModified > modifiedSince );
            }
        } );

        scanner.setBasedir( sourceFolder );

        String[] includesArray = getPatternsOrDefault( includes, getDefaultIncludes() );
        scanner.setIncludes( includesArray );

        String[] excludesArray = getPatternsOrDefault( excludes, getDefaultExcludes() );
        scanner.setExcludes( excludesArray );

        scanner.scan();

        return scanner.getIncludedFiles();
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
     * @return property.
     */
    public String getPredefinedVars()
    {
        return predefinedVars;
    }

    /**
     * Subclasses implement this method to obtain the folder representing js source files.
     * 
     * @return a file object representing a folder where the js source is to be searched from.
     */
    protected abstract File getSourceJsFolder();

    /**
     * @return property.
     */
    public int getStrictWhiteSpaceIndentation()
    {
        return strictWhiteSpaceIndentation;
    }

    /**
     * Subclasses implement this method to obtain the work folder where target related files can be read and stored.
     * 
     * @return the work folder.
     */
    protected abstract File getWorkFolder();

    /**
     * Initialise the JSLint environment.
     * 
     * @throws MojoExecutionException if the init fails.
     */
    protected void initJSLint()
        throws MojoExecutionException
    {
        jsLint = new JSLintBuilder().fromDefault();

        // Set up options.
        if ( adsafe )
        {
            jsLint.addOption( Option.ADSAFE );
        }
        if ( disallowBitwiseOperators )
        {
            jsLint.addOption( Option.BITWISE );
        }
        if ( assumeABrowser )
        {
            jsLint.addOption( Option.BROWSER );
        }
        if ( tolerateHTMLCase )
        {
            jsLint.addOption( Option.CAP );
        }
        if ( tolerateContinuation )
        {
            jsLint.addOption( Option.CONTINUE );
        }
        if ( tolerateCSSWorkarounds )
        {
            jsLint.addOption( Option.CSS );
        }
        if ( tolerateDebuggerStatements )
        {
            jsLint.addOption( Option.DEBUG );
        }
        if ( assumeConsoleAlertEtc )
        {
            jsLint.addOption( Option.DEVEL );
        }
        if ( tolerateES5Syntax )
        {
            jsLint.addOption( Option.ES5 );
        }
        if ( tolerateEval )
        {
            jsLint.addOption( Option.EVIL );
        }
        if ( tolerateUnfilteredForIn )
        {
            jsLint.addOption( Option.FORIN );
        }
        if ( tolerateHTMLFragments )
        {
            jsLint.addOption( Option.FRAGMENT );
        }
        jsLint.addOption( Option.MAXERR, maximumNumberOfErrors.toString() );
        if ( maximumLengthOfLine != null )
        {
            jsLint.addOption( Option.MAXLEN, maximumLengthOfLine.toString() );
        }
        if ( requireInitialCapsForConstructors )
        {
            jsLint.addOption( Option.NEWCAP );
        }
        if ( disallowDanglingUnderbarInIdentifiers )
        {
            jsLint.addOption( Option.NOMEN );
        }
        if ( tolerateHTMLEventHandlers )
        {
            jsLint.addOption( Option.ON );
        }
        if ( allowOneVarStatementPerFunction )
        {
            jsLint.addOption( Option.ONEVAR );
        }
        if ( stopOnFirstError )
        {
            jsLint.addOption( Option.PASSFAIL );
        }
        if ( disallowIncrAndDecr )
        {
            jsLint.addOption( Option.PLUSPLUS );
        }
        jsLint.addOption( Option.PREDEF, predefinedVars );
        if ( disallowInsecureCharsInRegExp )
        {
            jsLint.addOption( Option.REGEXP );
        }
        if ( assumeRhino )
        {
            jsLint.addOption( Option.RHINO );
        }
        if ( safeSubset )
        {
            jsLint.addOption( Option.SAFE );
        }
        if ( requireUseStrict )
        {
            jsLint.addOption( Option.STRICT );
        }
        if ( tolerateInefficientSubscripting )
        {
            jsLint.addOption( Option.SUB );
        }
        if ( disallowUndefinedVariables )
        {
            jsLint.addOption( Option.UNDEF );
        }
        if ( strictWhiteSpace )
        {
            jsLint.addOption( Option.WHITE );
            jsLint.addOption( Option.INDENT, strictWhiteSpaceIndentation.toString() );
        }
        if ( assumeAYahooWidget )
        {
            jsLint.addOption( Option.WIDGET );
        }
        if ( assumeWindows )
        {
            jsLint.addOption( Option.WINDOWS );
        }
    }

    /**
     * @return property.
     */
    public boolean isADsafe()
    {
        return adsafe;
    }

    /**
     * @return property.
     */
    public boolean isAllowOneVarStatementPerFunction()
    {
        return allowOneVarStatementPerFunction;
    }

    /**
     * @return property.
     */
    public boolean isAssumeABrowser()
    {
        return assumeABrowser;
    }

    /**
     * @return property.
     */
    public boolean isAssumeAYahooWidget()
    {
        return assumeAYahooWidget;
    }

    /**
     * @return property.
     */
    public boolean isAssumeConsoleAlertEtc()
    {
        return assumeConsoleAlertEtc;
    }

    /**
     * @return property.
     */
    public boolean isAssumeRhino()
    {
        return assumeRhino;
    }

    /**
     * @return property.
     */
    public boolean isAssumeWindows()
    {
        return assumeWindows;
    }

    /**
     * @return property.
     */
    public boolean isDisallowBitwiseOperators()
    {
        return disallowBitwiseOperators;
    }

    /**
     * @return property.
     */
    public boolean isDisallowDanglingUnderbarInIdentifiers()
    {
        return disallowDanglingUnderbarInIdentifiers;
    }

    /**
     * @return property.
     */
    public boolean isDisallowIncrAndDecr()
    {
        return disallowIncrAndDecr;
    }

    /**
     * @return property.
     */
    public boolean isDisallowInsecureCharsInRegExp()
    {
        return disallowInsecureCharsInRegExp;
    }

    /**
     * @return property.
     */
    public boolean isDisallowUndefinedVariables()
    {
        return disallowUndefinedVariables;
    }

    /**
     * @return property.
     */
    public boolean isFailOnIssues()
    {
        return failOnIssues;
    }

    /**
     * @return property.
     */
    public boolean isRequireInitialCapsForConstructors()
    {
        return requireInitialCapsForConstructors;
    }

    /**
     * @return property.
     */
    public boolean isRequireUseStrict()
    {
        return requireUseStrict;
    }

    /**
     * @return property.
     */
    public boolean isSafeSubset()
    {
        return safeSubset;
    }

    /**
     * @return property.
     */
    public boolean isStopOnFirstError()
    {
        return stopOnFirstError;
    }

    /**
     * @return property.
     */
    public boolean isStrictWhiteSpace()
    {
        return strictWhiteSpace;
    }

    /**
     * @return property.
     */
    public boolean isTolerateContinuation()
    {
        return tolerateContinuation;
    }

    /**
     * @return property.
     */
    public boolean isTolerateCSSWorkarounds()
    {
        return tolerateCSSWorkarounds;
    }

    /**
     * @return property.
     */
    public boolean isTolerateDebuggerStatements()
    {
        return tolerateDebuggerStatements;
    }

    /**
     * @return property.
     */
    public boolean isTolerateES5Syntax()
    {
        return tolerateES5Syntax;
    }

    /**
     * @return property.
     */
    public boolean isTolerateEval()
    {
        return tolerateEval;
    }

    /**
     * @return property.
     */
    public boolean isTolerateHTMLCase()
    {
        return tolerateHTMLCase;
    }

    /**
     * @return property.
     */
    public boolean isTolerateHTMLEventHandlers()
    {
        return tolerateHTMLEventHandlers;
    }

    /**
     * @return property.
     */
    public boolean isTolerateHTMLFragments()
    {
        return tolerateHTMLFragments;
    }

    /**
     * @return property.
     */
    public boolean isTolerateInefficientSubscripting()
    {
        return tolerateInefficientSubscripting;
    }

    /**
     * @return property.
     */
    public boolean isTolerateUnfilteredForIn()
    {
        return tolerateUnfilteredForIn;
    }

    /**
     * @param aDsafe set property.
     */
    public void setADsafe( boolean aDsafe )
    {
        adsafe = aDsafe;
    }

    /**
     * @param allowOneVarStatementPerFunction set property.
     */
    public void setAllowOneVarStatementPerFunction( boolean allowOneVarStatementPerFunction )
    {
        this.allowOneVarStatementPerFunction = allowOneVarStatementPerFunction;
    }

    /**
     * @param assumeABrowser set property.
     */
    public void setAssumeABrowser( boolean assumeABrowser )
    {
        this.assumeABrowser = assumeABrowser;
    }

    /**
     * @param assumeAYahooWidget set property.
     */
    public void setAssumeAYahooWidget( boolean assumeAYahooWidget )
    {
        this.assumeAYahooWidget = assumeAYahooWidget;
    }

    /**
     * @param assumeConsoleAlertEtc set property.
     */
    public void setAssumeConsoleAlertEtc( boolean assumeConsoleAlertEtc )
    {
        this.assumeConsoleAlertEtc = assumeConsoleAlertEtc;
    }

    /**
     * @param assumeRhino set property.
     */
    public void setAssumeRhino( boolean assumeRhino )
    {
        this.assumeRhino = assumeRhino;
    }

    /**
     * @param assumeWindows set property.
     */
    public void setAssumeWindows( boolean assumeWindows )
    {
        this.assumeWindows = assumeWindows;
    }

    /**
     * @param disallowBitwiseOperators set property.
     */
    public void setDisallowBitwiseOperators( boolean disallowBitwiseOperators )
    {
        this.disallowBitwiseOperators = disallowBitwiseOperators;
    }

    /**
     * @param disallowDanglingUnderbarInIdentifiers set property.
     */
    public void setDisallowDanglingUnderbarInIdentifiers( boolean disallowDanglingUnderbarInIdentifiers )
    {
        this.disallowDanglingUnderbarInIdentifiers = disallowDanglingUnderbarInIdentifiers;
    }

    /**
     * @param disallowIncrAndDecr set property.
     */
    public void setDisallowIncrAndDecr( boolean disallowIncrAndDecr )
    {
        this.disallowIncrAndDecr = disallowIncrAndDecr;
    }

    /**
     * @param disallowInsecureCharsInRegExp set property.
     */
    public void setDisallowInsecureCharsInRegExp( boolean disallowInsecureCharsInRegExp )
    {
        this.disallowInsecureCharsInRegExp = disallowInsecureCharsInRegExp;
    }

    /**
     * @param disallowUndefinedVariables set property.
     */
    public void setDisallowUndefinedVariables( boolean disallowUndefinedVariables )
    {
        this.disallowUndefinedVariables = disallowUndefinedVariables;
    }

    /**
     * @param encoding set property
     */
    public void setEncoding( String encoding )
    {
        this.encoding = encoding;
    }

    /**
     * @param excludes set property.
     */
    public void setExcludes( List<String> excludes )
    {
        this.excludes = excludes;
    }

    /**
     * @param failOnIssues set property.
     */
    public void setFailOnIssues( boolean failOnIssues )
    {
        this.failOnIssues = failOnIssues;
    }

    /**
     * @param includes set property.
     */
    public void setIncludes( List<String> includes )
    {
        this.includes = includes;
    }

    /**
     * @param maximumLengthOfLine set property.
     */
    public void setMaximumLengthOfLine( Integer maximumLengthOfLine )
    {
        this.maximumLengthOfLine = maximumLengthOfLine;
    }

    /**
     * @param maximumNumberOfErrors set property.
     */
    public void setMaximumNumberOfErrors( Integer maximumNumberOfErrors )
    {
        this.maximumNumberOfErrors = maximumNumberOfErrors;
    }

    /**
     * @param predefinedVars set property.
     */
    public void setPredefinedVars( String predefinedVars )
    {
        this.predefinedVars = predefinedVars;
    }

    /**
     * @param requireInitialCapsForConstructors set property.
     */
    public void setRequireInitialCapsForConstructors( boolean requireInitialCapsForConstructors )
    {
        this.requireInitialCapsForConstructors = requireInitialCapsForConstructors;
    }

    /**
     * @param requireUseStrict set property.
     */
    public void setRequireUseStrict( boolean requireUseStrict )
    {
        this.requireUseStrict = requireUseStrict;
    }

    /**
     * @param safeSubset set property.
     */
    public void setSafeSubset( boolean safeSubset )
    {
        this.safeSubset = safeSubset;
    }

    /**
     * Subclasses implement this method to provide the folder representing js source files.
     * 
     * @param folder a file object representing a folder where the js source is to be searched from.
     */
    protected abstract void setSourceJsFolder( File folder );

    /**
     * @param stopOnFirstError set property.
     */
    public void setStopOnFirstError( boolean stopOnFirstError )
    {
        this.stopOnFirstError = stopOnFirstError;
    }

    /**
     * @param strictWhiteSpace set property.
     */
    public void setStrictWhiteSpace( boolean strictWhiteSpace )
    {
        this.strictWhiteSpace = strictWhiteSpace;
    }

    /**
     * @param strictWhiteSpaceIndentation set property.
     */
    public void setStrictWhiteSpaceIndentation( Integer strictWhiteSpaceIndentation )
    {
        this.strictWhiteSpaceIndentation = strictWhiteSpaceIndentation;
    }

    /**
     * @param tolerateContinuation set property.
     */
    public void setTolerateContinuation( boolean tolerateContinuation )
    {
        this.tolerateContinuation = tolerateContinuation;
    }

    /**
     * @param tolerateCSSWorkarounds set property.
     */
    public void setTolerateCSSWorkarounds( boolean tolerateCSSWorkarounds )
    {
        this.tolerateCSSWorkarounds = tolerateCSSWorkarounds;
    }

    /**
     * @param tolerateDebuggerStatements set property.
     */
    public void setTolerateDebuggerStatements( boolean tolerateDebuggerStatements )
    {
        this.tolerateDebuggerStatements = tolerateDebuggerStatements;
    }

    /**
     * @param tolerateES5Syntax set property.
     */
    public void setTolerateES5Syntax( boolean tolerateES5Syntax )
    {
        this.tolerateES5Syntax = tolerateES5Syntax;
    }

    /**
     * @param tolerateEval set property.
     */
    public void setTolerateEval( boolean tolerateEval )
    {
        this.tolerateEval = tolerateEval;
    }

    /**
     * @param tolerateHTMLCase set property.
     */
    public void setTolerateHTMLCase( boolean tolerateHTMLCase )
    {
        this.tolerateHTMLCase = tolerateHTMLCase;
    }

    /**
     * @param tolerateHTMLEventHandlers set property.
     */
    public void setTolerateHTMLEventHandlers( boolean tolerateHTMLEventHandlers )
    {
        this.tolerateHTMLEventHandlers = tolerateHTMLEventHandlers;
    }

    /**
     * @param tolerateHTMLFragments set property.
     */
    public void setTolerateHTMLFragments( boolean tolerateHTMLFragments )
    {
        this.tolerateHTMLFragments = tolerateHTMLFragments;
    }

    /**
     * @param tolerateInefficientSubscripting set property.
     */
    public void setTolerateInefficientSubscripting( boolean tolerateInefficientSubscripting )
    {
        this.tolerateInefficientSubscripting = tolerateInefficientSubscripting;
    }

    /**
     * @param tolerateUnfilteredForIn set property.
     */
    public void setTolerateUnfilteredForIn( boolean tolerateUnfilteredForIn )
    {
        this.tolerateUnfilteredForIn = tolerateUnfilteredForIn;
    }

    /**
     * Subclasses implement this method to set the work folder where target related files can be read and stored.
     * 
     * @param workFolder the work folder.
     */
    protected abstract void setWorkFolder( File workFolder );
}
