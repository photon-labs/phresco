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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.jslint4java.Issue;
import com.googlecode.jslint4java.JSLint;
import com.googlecode.jslint4java.JSLintResult;

/**
 * Test linting.
 * 
 * @author huntc
 */
public class JSLintMojoTest
{
    /**
     * The mojo to test.
     */
    private JSLintMojo jsLintMojo;

    /**
     * Set up a our mojo for testing.
     */
    @Before
    public void setUpMojo()
    {
        jsLintMojo = new JSLintMojo();
    }

    /**
     * Set up the mojo for execution. Sensible linting defaults are used. The source and target folders are relative to
     * our target/testClasses path. We also set up JSLint so that it requires variables to be defined. This will permit
     * some expectations on JSLint when parsing badTest.js in the resources folder.
     * 
     * @throws URISyntaxException if something goes wrong.
     * @throws IOException
     */
    private void setUpMojoForExecution()
        throws URISyntaxException, IOException
    {
        Log log = mock( Log.class );
        jsLintMojo.setLog( log );

        jsLintMojo.setIncludes( Arrays.asList( new String[] { "*.js" } ) );
        jsLintMojo.setExcludes( Arrays.asList( new String[] { "somethingtoexclude.js" } ) );

        File sourceJsFolder = new File( JSLintMojoTest.class.getResource( "test.js" ).toURI() ).getParentFile();
        jsLintMojo.setSourceJsFolder( sourceJsFolder );

        File workFolder = new File( System.getProperty( "java.io.tmpdir" ), "jslint-mojo" );
        jsLintMojo.setWorkFolder( workFolder );

        jsLintMojo.setEncoding( "UTF-8" );

        jsLintMojo.setADsafe( false );
        jsLintMojo.setAllowOneVarStatementPerFunction( false );
        jsLintMojo.setAssumeABrowser( false );
        jsLintMojo.setAssumeAYahooWidget( false );
        jsLintMojo.setAssumeConsoleAlertEtc( false );
        jsLintMojo.setAssumeRhino( false );
        jsLintMojo.setAssumeWindows( false );
        jsLintMojo.setDisallowBitwiseOperators( false );
        jsLintMojo.setDisallowDanglingUnderbarInIdentifiers( false );
        jsLintMojo.setDisallowIncrAndDecr( false );
        jsLintMojo.setDisallowInsecureCharsInRegExp( false );
        jsLintMojo.setDisallowUndefinedVariables( true );
        jsLintMojo.setFailOnIssues( false );
        final int maximumNumberOfErrors = 50;
        jsLintMojo.setMaximumNumberOfErrors( maximumNumberOfErrors );
        jsLintMojo.setPredefinedVars( "" );
        jsLintMojo.setRequireInitialCapsForConstructors( false );
        jsLintMojo.setRequireUseStrict( false );
        jsLintMojo.setSafeSubset( false );
        jsLintMojo.setStopOnFirstError( false );
        jsLintMojo.setStrictWhiteSpace( false );
        jsLintMojo.setStrictWhiteSpaceIndentation( 4 );
        jsLintMojo.setTolerateCSSWorkarounds( false );
        jsLintMojo.setTolerateContinuation( false );
        jsLintMojo.setTolerateDebuggerStatements( false );
        jsLintMojo.setTolerateES5Syntax( false );
        jsLintMojo.setTolerateEval( false );
        jsLintMojo.setTolerateHTMLCase( false );
        jsLintMojo.setTolerateHTMLEventHandlers( false );
        jsLintMojo.setTolerateHTMLFragments( false );
        jsLintMojo.setTolerateInefficientSubscripting( false );
        jsLintMojo.setTolerateUnfilteredForIn( false );
    }

    /**
     * Clean up.
     */
    @After
    public void tearDownMojo()
    {
        // Do a good job of cleaning up after ourselves.
        File workFolder = jsLintMojo.getWorkFolder();
        if ( workFolder != null )
        {
            new File( workFolder, "lastModified" ).delete();
            workFolder.delete();
        }
    }

    /**
     * Test execution for all files. The lint problem should be reported as a warning given that we're not expecting the
     * mojo to halt on lint issues.
     * 
     * @throws URISyntaxException is something goes wrong.
     * @throws MojoExecutionException should not occur either.
     * @throws IOException should not occur.
     */
    @Test
    public void testExecution()
        throws URISyntaxException, MojoExecutionException, IOException
    {
        setUpMojoForExecution();

        jsLintMojo.execute();

        Log log = jsLintMojo.getLog();
        verify( log ).info( "Parsing: badTest.js" );
        verify( log ).info( "Parsing: test.js" );
        verify( log ).warn( "badTest.js:2:1:'a' has not been fully defined yet." );
    }

    /**
     * Test that files are ignored when they've seemingly not been modified since the last time we looked.
     * 
     * @throws URISyntaxException is something goes wrong.
     * @throws MojoExecutionException should not occur either.
     * @throws IOException should not occur.
     */
    @Test
    public void testExecutionWithFilesSkipped()
        throws URISyntaxException, IOException, MojoExecutionException
    {
        setUpMojoForExecution();

        File workFolder = jsLintMojo.getWorkFolder();
        workFolder.mkdirs();
        new File( workFolder, "lastModified" ).createNewFile();

        jsLintMojo.execute();
        Log log = jsLintMojo.getLog();
        verify( log, times( 0 ) ).info( anyString() );
    }

    /**
     * Test execution for all files and stop on error. The lint problem should be reported as an error given that we're
     * not expecting the mojo to halt on lint issues.
     * 
     * @throws URISyntaxException is something goes wrong.
     * @throws MojoExecutionException should not occur either.
     * @throws IOException should not occur.
     */
    @Test
    public void testExecutionWithStoppage()
        throws URISyntaxException, MojoExecutionException, IOException
    {
        setUpMojoForExecution();
        jsLintMojo.setFailOnIssues( true );

        try
        {
            jsLintMojo.execute();
            fail( "Should have failed" );
        }
        catch ( MojoExecutionException e )
        {
            Log log = jsLintMojo.getLog();
            verify( log ).info( "Parsing: badTest.js" );
            verify( log ).info( "Parsing: test.js" );
            verify( log ).error( "badTest.js:2:1:'a' has not been fully defined yet." );
        }
    }

    /**
     * Test the initialisation of JSLint.
     * 
     * @throws URISyntaxException if something goes wrong.
     * @throws MojoExecutionException if something goes wrong with JSLint initialisation.
     */
    @Test
    public void testJSLintInit()
        throws URISyntaxException, MojoExecutionException
    {
        jsLintMojo.setADsafe( true );
        jsLintMojo.setAllowOneVarStatementPerFunction( true );
        jsLintMojo.setAssumeABrowser( true );
        jsLintMojo.setAssumeAYahooWidget( true );
        jsLintMojo.setAssumeConsoleAlertEtc( true );
        jsLintMojo.setAssumeRhino( true );
        jsLintMojo.setAssumeWindows( true );
        jsLintMojo.setDisallowBitwiseOperators( true );
        jsLintMojo.setDisallowDanglingUnderbarInIdentifiers( true );
        jsLintMojo.setDisallowIncrAndDecr( true );
        jsLintMojo.setDisallowInsecureCharsInRegExp( true );
        jsLintMojo.setDisallowUndefinedVariables( true );
        jsLintMojo.setFailOnIssues( true );
        final int maximumLengthOfLine = 80;
        jsLintMojo.setMaximumLengthOfLine( maximumLengthOfLine );
        jsLintMojo.setMaximumNumberOfErrors( 1 );
        jsLintMojo.setPredefinedVars( "a,b" );
        jsLintMojo.setRequireInitialCapsForConstructors( true );
        jsLintMojo.setRequireUseStrict( true );
        jsLintMojo.setSafeSubset( true );
        jsLintMojo.setStopOnFirstError( true );
        jsLintMojo.setStrictWhiteSpace( true );
        jsLintMojo.setStrictWhiteSpaceIndentation( 4 );
        jsLintMojo.setTolerateCSSWorkarounds( true );
        jsLintMojo.setTolerateContinuation( true );
        jsLintMojo.setTolerateDebuggerStatements( true );
        jsLintMojo.setTolerateES5Syntax( true );
        jsLintMojo.setTolerateEval( true );
        jsLintMojo.setTolerateHTMLCase( true );
        jsLintMojo.setTolerateHTMLEventHandlers( true );
        jsLintMojo.setTolerateHTMLFragments( true );
        jsLintMojo.setTolerateInefficientSubscripting( true );
        jsLintMojo.setTolerateUnfilteredForIn( true );

        // The method we want to exercise.
        jsLintMojo.initJSLint();

        /*
         * This isn't a great test - it is tricky to test the code to ensure that all options are set given that the
         * JSLint object gives us no access to the options. All we can do is a weak smoke test to see that the options
         * are set.
         */
        JSLint jsLint = jsLintMojo.getJsLint();
        JSLintResult result = jsLint.lint( "", "" );
        List<Issue> issues = result.getIssues();
        assertEquals( 2, issues.size() );
        assertEquals( ":1:1:Expected '<div>' and instead saw '(end)'.", issues.get( 0 ).toString() );
        assertEquals( ":1:1:Stopping.  (100% scanned).", issues.get( 1 ).toString() );
    }
}
