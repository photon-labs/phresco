package org.codehaus.mojo.javascript;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * Configures plugins required by the javascript-maven-plugin plugin and adds them to the current project.
 * 
 * @author Ben Jones
 * @author Christopher Hunt
 */
public class Configurer
    extends AbstractMavenLifecycleParticipant
{
    /** Injected html resource folder. */
    private String htmlResourceDir;

    /** Injected html file patterns to exclude given that the js import plugin takes care of copying these. */
    private String htmlResourceExcludes;

    /** Injected html test resource folder. */
    private String htmlTestResourceDir;

    /** Injected html test file patterns to exclude given that the js import plugin takes care of copying these. */
    private String htmlTestResourceExcludes;

    /** Injected js resource folder. */
    private String jsResourceDir;

    /** Injected js file patterns to exclude given that the js import plugin takes care of copying these. */
    private String jsResourceExcludes;

    /** Injected js test resource folder. */
    private String jsTestResourceDir;

    /** Injected js test file patterns to exclude given that the js import plugin takes care of copying these. */
    private String jsTestResourceExcludes;

    /** Injected jetty-maven-plugin */
    private Plugin jettyPlugin;

    /** Injected jetty 'context path' configuration variable */
    private String jettyContextPath;

    /** Injected jetty 'resource bases' configuration variables */
    private List<String> jettyResourceBases;

    /** Injected maven-compiler-plugin */
    private Plugin mavenCompilerPlugin;

    /** Injected maven-surefire-plugin */
    private Plugin mavenSurefirePlugin;

    /** Injected maven-surefire-plugin */
    private Plugin mavenAssemblyPlugin;

    /** Injected maven-site-plugin */
    private Plugin mavenSitePlugin;

    /** Injected maven site 'exclude modules' configuration variable */
    private String mavenSiteExcludeModules;

    /** Injected maven site plugin 'jstools group' configuration variable */
    private String mavenSiteJstoolsGroup;

    /** Injected maven site plugin 'jstools artifact' configuration variable */
    private String mavenSiteJstoolsArtifact;

    /** Injected maven site plugin 'jstools version' configuration variable */
    private String mavenSiteJstoolsVersion;

    /** Injected jstools 'jsDir' configuration variables */
    private String mavenSiteJstoolsReportsJsDir;

    /** Injected jstools 'reports' configuration variables */
    private List<String> mavenSiteJstoolsReports;

    /** Injected Almond dependency for AMD builds */
    private Dependency almondDependency;

    /** Injected QUnit dependency for testing */
    private Dependency qunitDependency;

    /** Injected test runner for testing */
    private Dependency jstestrunnerJUnitDependency;

    /** Injected utility for configuring maven projects */
    private MavenProjectHelper projectHelper;

    /** Injected handler for js artifacts */
    private ArtifactHandler jsArtifactHandler;

    /** Injected manager of artifact handlers */
    private ArtifactHandlerManager artifactHandlerManager;

    @Override
    /** {@inheritDoc} */
    public void afterProjectsRead( MavenSession session )
        throws MavenExecutionException
    {
        // Ensure our artifact handler is registered.
        Map<String, ArtifactHandler> handlers = new HashMap<String, ArtifactHandler>( 1 );
        handlers.put( "js", jsArtifactHandler );
        artifactHandlerManager.addHandlers( handlers );

        // For multi-module builds, we need to iterate through all projects and only apply
        // our custom configuration to projects which are using this extension
        List<MavenProject> projects = session.getProjects();
        for ( MavenProject project : projects )
        {
            if ( project.getExtensionArtifactMap().containsKey( "org.codehaus.mojo:javascript-maven-plugin" ) )
            {
                // Create a set of plugins for easy existance tests.
                List<Plugin> plugins = project.getBuildPlugins();
                Set<Plugin> projectSpecifiedPlugins = buildSpecifiedPlugins( project );

                // For each plugin we want to associate with the current project, check if it exists in the project's
                // the user has defined (not maven itself). If it has not been supplied by the user then remove it
                // (because Maven may have put it there) and configure it.
                if ( !projectSpecifiedPlugins.contains( jettyPlugin ) )
                {
                    plugins.remove( jettyPlugin );
                    configureJetty( project );
                }
                if ( !projectSpecifiedPlugins.contains( mavenCompilerPlugin ) )
                {
                    plugins.remove( mavenCompilerPlugin );
                    configureMavenCompiler( project );
                }
                if ( !projectSpecifiedPlugins.contains( mavenSurefirePlugin ) )
                {
                    plugins.remove( mavenSurefirePlugin );
                    configureMavenSurefire( project );
                }
//                if ( !projectSpecifiedPlugins.contains( mavenSitePlugin ) )
//                {
//                    plugins.remove( mavenSitePlugin );
//                    configureMavenSite( project );
//                }

                // Add our required resources.
                addResources( project );

                // Add dependencies that js projects will need always.
                addDependencies( project );

                // Add artifact/attachments if there is no assembly plugin.
                if ( !projectSpecifiedPlugins.contains( mavenAssemblyPlugin ) )
                {
                    addArtifacts( project );
                }

            }
        }

        // Perform anything "normal".
        super.afterProjectsRead( session );
    }

    /**
     * Build a plugin list of those only relating to our project or parent projects i.e. not those introduced by Maven
     * itself.
     * 
     * @param project the project to work with.
     * @return the list of plugins excluding those defined by Maven.
     */
    protected Set<Plugin> buildSpecifiedPlugins( MavenProject project )
    {
        Set<Plugin> plugins = new HashSet<Plugin>( project.getBuildPlugins().size() );
        MavenProject currentProject = project;
        while ( currentProject != null )
        {
            Build build = currentProject.getOriginalModel().getBuild();
            if ( build != null )
            {
                plugins.addAll( build.getPlugins() );
            }
            currentProject = currentProject.getParent();
        }
        return plugins;
    }

    /**
     * Configure the maven-site-plugin with the configuration parameters injected into this class from components.xml
     * and add the plugin to the current project.
     */
    private void configureMavenSite( MavenProject project )
    {
        // Construct the Xpp3Dom structure for configuration
        Xpp3Dom configuration = new Xpp3Dom( "configuration" );

        Xpp3Dom excludeModules = new Xpp3Dom( "excludeModules" );
        excludeModules.setValue( mavenSiteExcludeModules );
        configuration.addChild( excludeModules );

        Xpp3Dom reportPlugins = new Xpp3Dom( "reportPlugins" );
        configuration.addChild( reportPlugins );

        Xpp3Dom plugin = new Xpp3Dom( "plugin" );
        reportPlugins.addChild( plugin );

        Xpp3Dom groupId = new Xpp3Dom( "groupId" );
        groupId.setValue( mavenSiteJstoolsGroup );
        plugin.addChild( groupId );

        Xpp3Dom artifactId = new Xpp3Dom( "artifactId" );
        artifactId.setValue( mavenSiteJstoolsArtifact );
        plugin.addChild( artifactId );

        Xpp3Dom version = new Xpp3Dom( "version" );
        version.setValue( mavenSiteJstoolsVersion );
        plugin.addChild( version );

        Xpp3Dom pluginConfiguration = new Xpp3Dom( "configuration" );
        plugin.addChild( pluginConfiguration );

        Xpp3Dom jsDir = new Xpp3Dom( "jsDir" );
        jsDir.setValue( mavenSiteJstoolsReportsJsDir );
        pluginConfiguration.addChild( jsDir );

        Xpp3Dom reportSets = new Xpp3Dom( "reportSets" );
        pluginConfiguration.addChild( reportSets );

        Xpp3Dom reportSet = new Xpp3Dom( "reportSet" );
        reportSets.addChild( reportSet );

        if ( mavenSiteJstoolsReports.size() > 0 )
        {
            Xpp3Dom reports = new Xpp3Dom( "reports" );
            for ( String reportValue : mavenSiteJstoolsReports )
            {
                Xpp3Dom report = new Xpp3Dom( "report" );
                report.setValue( reportValue );
                reports.addChild( report );
            }
            reportSet.addChild( reports );
        }

        // Set the configuration on the plugin
        mavenSitePlugin.setConfiguration( configuration );

        // Construct plugin executions
        List<PluginExecution> pluginExecutions = new ArrayList<PluginExecution>();

        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.addGoal( "site" );
        pluginExecution.setPhase( "site" );
        pluginExecution.setId( "default-site" );
        pluginExecution.setConfiguration( configuration );
        pluginExecutions.add( pluginExecution );

        mavenSitePlugin.setExecutions( pluginExecutions );

        // Add the plugin to the current project
        project.getBuildPlugins().add( mavenSitePlugin );
    }

    /**
     * Configure the maven-surefire-plugin with the configuration parameters injected into this class from
     * components.xml and add the plugin to the current project.
     */
    private void configureMavenSurefire( MavenProject project )
    {
        // Construct plugin executions
        List<PluginExecution> pluginExecutions = new ArrayList<PluginExecution>();

        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.addGoal( "test" );
        pluginExecutions.add( pluginExecution );

        mavenSurefirePlugin.setExecutions( pluginExecutions );

        // Add the plugin to the current project
        project.getBuildPlugins().add( mavenSurefirePlugin );
    }

    /**
     * Add the maven-compiler-plugin to the current project.
     */
    private void configureMavenCompiler( MavenProject project )
    {
        // Construct plugin executions
        List<PluginExecution> pluginExecutions = new ArrayList<PluginExecution>();

        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.setId( "default-testCompile" );
        pluginExecution.setPhase( "test-compile" );
        pluginExecution.addGoal( "testCompile" );
        pluginExecutions.add( pluginExecution );

        PluginExecution compileExecution = new PluginExecution();
        compileExecution.setId( "default-compile" );
        compileExecution.setPhase( "compile" );
        compileExecution.addGoal( "compile" );
        pluginExecutions.add( compileExecution );
        mavenCompilerPlugin.setExecutions( pluginExecutions );

        project.getBuildPlugins().add( mavenCompilerPlugin );
    }

    /**
     * Configure the jetty-maven-plugin with the configuration parameters injected into this class from components.xml
     * and add the plugin to the current project.
     */
    private void configureJetty( MavenProject project )
    {
        // Construct the Xpp3Dom structure for configuration
        Xpp3Dom configuration = new Xpp3Dom( "configuration" );
        Xpp3Dom webAppConfig = new Xpp3Dom( "webAppConfig" );
        configuration.addChild( webAppConfig );

        Xpp3Dom contextPath = new Xpp3Dom( "contextPath" );
        contextPath.setValue( jettyContextPath );
        webAppConfig.addChild( contextPath );

        if ( jettyResourceBases.size() > 0 )
        {
            Xpp3Dom resourceBases = new Xpp3Dom( "resourceBases" );
            for ( String resourceBaseValue : jettyResourceBases )
            {
                Xpp3Dom resourceBase = new Xpp3Dom( "resourceBases" );
                resourceBase.setValue( resourceBaseValue );
                resourceBases.addChild( resourceBase );
            }
            webAppConfig.addChild( resourceBases );
        }

        // Set the configuration on the plugin
        jettyPlugin.setConfiguration( configuration );
        // Add the plugin to the current project
        project.getBuildPlugins().add( jettyPlugin );
    }

    /**
     * Add the standard resource patterns for a JS project. In essence we prevent the resource plugin from considering
     * html and js files when copying as the JS Import Maven Plugin takes care of copying (and filtering) these (it has
     * to).
     * 
     * @param project the project to add resources to.
     */
    private void addResources( MavenProject project )
    {
        projectHelper.addResource( project, htmlResourceDir, null,
                                   Arrays.asList( new String[] { htmlResourceExcludes } ) );

        projectHelper.addTestResource( project, htmlTestResourceDir, null,
                                       Arrays.asList( new String[] { htmlTestResourceExcludes } ) );

        projectHelper.addResource( project, jsResourceDir, null, Arrays.asList( new String[] { jsResourceExcludes } ) );

        projectHelper.addTestResource( project, jsTestResourceDir, null,
                                       Arrays.asList( new String[] { jsTestResourceExcludes } ) );
    }

    /**
     * Add dependencies for things that support the building of JS applications.
     * 
     * @param project the project to add to.
     */
    private void addDependencies( MavenProject project )
    {
        List<Dependency> dependencies = project.getDependencies();

        // Build collection of dependencies to allow for efficient lookup
        Map<String, Dependency> dependencyMap = new HashMap<String, Dependency>( dependencies.size() );
        for ( Dependency dependency : dependencies )
        {
            dependencyMap.put( dependency.getGroupId() + ":" + dependency.getArtifactId(), dependency );
        }

        // Add dependencies if they aren't already defined in the project
        if ( !dependencyMap.containsKey( almondDependency.getGroupId() + ":" + almondDependency.getArtifactId() ) )
        {
            dependencies.add( almondDependency );
        }

        if ( !dependencyMap.containsKey( qunitDependency.getGroupId() + ":" + qunitDependency.getArtifactId() ) )
        {
            qunitDependency.setScope( "test" );
            dependencies.add( qunitDependency );
        }

        if ( !dependencyMap.containsKey( jstestrunnerJUnitDependency.getGroupId() + ":"
            + jstestrunnerJUnitDependency.getArtifactId() ) )
        {
            jstestrunnerJUnitDependency.setScope( "test" );
            dependencies.add( jstestrunnerJUnitDependency );
        }
    }

    /**
     * Add in default artifact and artifact attachments in target/min/classes/*.js
     * 
     * @param project the Maven project to add artifact/attachments to.
     */
    private void addArtifacts( MavenProject project )
    {
        File minTargetFolder =
            new File( project.getBasedir(), "target" + File.separator + "min" + File.separator + "classes" );

        // Note that the Java compiler plugin will override this artifact file assignment. However by default JS
        // projects do not bind the compiler goal other than for conducting tests.
        project.getArtifact().setFile( new File( minTargetFolder, "1.js" ) );

        projectHelper.attachArtifact( project, new File( minTargetFolder, "1-min.js" ), "min" );
    }
}
