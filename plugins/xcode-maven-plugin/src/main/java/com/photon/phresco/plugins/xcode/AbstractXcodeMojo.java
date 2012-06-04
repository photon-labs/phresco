package com.photon.phresco.plugins.xcode;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

public abstract class AbstractXcodeMojo extends AbstractMojo {
	/**
     * Location of the xcodebuild executable.
     *
     * @parameter expression="${xcodebuild}" default-value="/usr/bin/xcodebuild"
     */
    protected File xcodeCommandLine;

    /**
     * Location of the xcrun executable.
     *
     * @parameter expression="${xcrun}" default-value="/usr/bin/xcrun"
     */

    protected File xcrunCommandLine;
    
    /**
     * Location of the plutil executable.
     *
     * @parameter expression="${plutil}" default-value="/usr/bin/plutil"
     */
    protected File plutilCommandLine;

    /**
     * Location of the xcodebuild executable.
     *
     * @parameter expression="${xcodebuild}" default-value="/usr/local/bin/ios-sim"
     */
    protected File iosSimCommandLine;

    /**
     * Location of the lcov executable.
     *
     * @parameter expression="${lcov}" default-value="/usr/local/bin/lcov"
     */
    protected File lcovCommandLine;

    /**
     * Location of the genhtml executable.
     *
     * @parameter expression="${genhtml}" default-value="/usr/local/bin/genhtml"
     */
    protected File genHtmlCommandLine;

    /**
     * Project Name
     *
     * @parameter
     */
    protected File xcodeProject;

    /**
     * Target to be built
     *
     * @parameter
     */
    protected String xcodeTarget;

    /**
     * Target to be built
     *
     * @parameter
     */
    protected String xcodeSdk;

    /**
     * Configuration to be built
     *
     * @parameter
     */
    protected String xcodeConfiguration;

    /**
     * Info Plist of the app
     *
     * @parameter
     */
    protected File infoPlist;

    /**
     * Info Plist of the app
     *
     * @parameter
     */
    protected String bundleIdentifierSuffix;

    /**
     * Info Plist of the app
     *
     * @parameter
     */
    protected String bundleDisplayNameSuffix;

    /**
     * Info Plist of the app
     *
     * @parameter
     */
    protected Boolean bundleVersionFromGit;

    /**
     * ProvisioningProfile to sign the app
     *
     * @parameter
     */
    protected String provisioningProfile;

    /**
     * CodeSignIdentity to sign the app
     *
     * @parameter
     */
    protected String codeSignIdentity;

    /**
     * App Name
     *
     * @parameter expression="${application.name}"
     */
    protected String appName;

    /**
     * Optional Coverage App Name for static libraries
     *
     * @parameter
     */
    protected String coverageAppName;

    /**
     * App Name
     *
     * @parameter
     */
    protected String testDevice;

    /**
     * App Name
     *
     * @parameter default-value="False"
     */
    protected Boolean testNoAutoExit;

    /**
     * App Name
     *
     * @parameter default-value="False"
     */
    protected Boolean teamCityLog;

    /**
     * App Name
     *
     * @parameter default-value="False"
     */
    protected Boolean executeGHUnitTests;

    /**
     * App Name
     *
     * @parameter default-value="False"
     */
    protected Boolean generateCoverageReport;


    /**
     * Coverage Target
     *
     * @parameter
     */
    protected String coverageTarget;

    /**
     * The Maven Project Object
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project;

    /**
     * @parameter expression="${basedir}"
     */
    protected String basedir;

    /**
     * Build directory.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    protected File buildDirectory;
	

}
