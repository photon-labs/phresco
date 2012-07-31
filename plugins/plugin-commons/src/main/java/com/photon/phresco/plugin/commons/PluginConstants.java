/*
 * ###
 * Phresco Commons
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.plugin.commons;

public interface PluginConstants {
	
	//Plugin Common Constants
	 String DOT_PHRESCO_FOLDER 			= ".phresco";
	 String PROJECT_FOLDER 				= "source";
	 String DO_NOT_CHECKIN_FOLDER  		= "/do_not_checkin";
	 String TARGET_DIRECTORY			= DO_NOT_CHECKIN_FOLDER + "/target";
	 String BUILD_DIRECTORY				= DO_NOT_CHECKIN_FOLDER + "/build";
	 String BUILD_INFO_FILE 			= "/build.info";
     String TEMP_DIR		 			= "/temp";
     String PROJECT_CODE	 			= "PHR";
     String DEPLOY_LOCATION				= "com.phresco.server.deploy.location";
     String SUCCESS		 				= "SUCCESS";
     String FAILURE		 				= "FAILURE";
     String STR_SPACE 					= " ";
     String STR_UNDERSCORE				= "_";
     String PROJECT_INFO_FILE			= "project.info";
     String FORWARD_SLASH               = "/";
     String CONFIG_FILE					= "phresco-env-config.xml";
     String SETTINGS_FILE				= "settings.xml";
	
	//Constants for Maven commands
     String MVN_CMD 					= "mvn";
     String MVN_PHASE_VALIDATE			= "validate";
     String MVN_PHASE_INITIALIZE		= "initialize";
     String MVN_PHASE_CLEAN				= "clean";
     String MVN_PHASE_PACKAGE			= "package";
	 String MVN_PHASE_VERIFY			= "verify";
	 String MVN_PHASE_VALDATE			= "validate";
	 String MVN_PHASE_INSTALL			= "install";
	 String JAVA_LIB_PATH				= "java.library.path";
    
	// Constants for Php maven plugin
	 
	 String PHP_SOURCE_DIR				= "/source";
     String PHP_TEST_CONFIG_FILE 		= "/test/functional/src/test/php/phresco/tests/" + CONFIG_FILE;
     String PHP_SOURCE_CONFIG_FILE 		= "/source/config/" + CONFIG_FILE;
	 String PHP_SQL_DIR				    = "/source/sql/";
	 String PHP_SQL_FILE				= "/site.sql";
    
    //Constants for Drupal Plugin
     String DRUPAL_SOURCE_DIR			= "/source/sites";
     String DRUPAL_TEST_CONFIG_FILE 	= "/test/functional/src/test/php/phresco/tests/" + CONFIG_FILE;
     String DRUPAL_SOURCE_CONFIG_FILE	= "/source/sites/default/config/" + CONFIG_FILE;
     String DRUPAL_SQL_DIR				= "/source/sql/";
	 String DRUPAL_SQL_FILE				= "/site.sql";
     String BINARIES_DIR				=  DO_NOT_CHECKIN_FOLDER+"/binaries";
    
    //Constants for Java Plugin
     String MVN_HOME 					= "maven.home";
     String MVN_EXE_PATH				= "\\bin\\mvn.bat";
     String SKIP_TESTS					= "-DskipTests=true";
     String JAVA_TEST_CONFIG_FILE 		= "/test/functional/src/main/resources/" + CONFIG_FILE;
	 String JAVA_CONFIG_FILE 			= "/src/main/resources/" + CONFIG_FILE;
	 String JAVA_WEBAPP_CONFIG_FILE 	= "/src/main/webapp/WEB-INF/resources/" + CONFIG_FILE;
	 String JAVA_SERVER_CONFIG_FILE		= "/serverconfig.properties";
	 String JAVA_CMD					= "java";
	 String JAR_PATH					= "/lib/driver/";
	 String JAVA_JAR_CMD				= "-jar";
	 String JAVA_DO_NOT_CHECKIN_FOLDER  = "/do_not_checkin";
	 String JAVA_LOG_FILE_DIRECTORY		= "/log";
	 String TYPE_TOMCAT					= "Apache Tomcat";
	 String TYPE_JBOSS					= "JBoss";
	 String TYPE_WEBLOGIC 				= "WebLogic";
	 String PROP_SERVER_PORT			= "server.port";
	 String PROP_SERVER_SHUTDOWN_PORT	= "server.shutdown.port";
	 String JAVA_LOG_FILE				= "/javaLog.log";
	 String JAVA_SQL_DIR				= "/src/sql/";
	 String JAVA_SQL_FILE				= "/site.sql";
	 String JAVA_POM_ARCHIVE			= "archive";
	 String JAVA_POM_MANIFEST			= "manifest";
	 String JAVA_POM_MAINCLASS			= "mainClass";
	 String JAVA_POM_ADD_PATH 			= "addClasspath";
	 String JAR_PLUGIN_GROUPID			="org.apache.maven.plugins";
	 String JAR_PLUGIN_ARTIFACT_ID		= "maven-jar-plugin";
	 String TOMCAT_GOAL					= "org.codehaus.mojo:tomcat-maven-plugin:1.1:redeploy";
	 String WEBLOGIC_GOAL				= "com.oracle.weblogic:weblogic-maven-plugin:12.1.1.0:redeploy";
	 String JBOSS_GOAL					= "cargo:redeploy";
	 //String T7_START_GOAL				= "t7:run-forked";
	 //String T7_STOP_GOAL				= "t7:stop-forked";
	 String SERVER_PORT					= "-Dserver.port=";
	 String SERVER_HOST					= "-Dserver.host=";
	 String SERVER_USERNAME				= "-Dserver.username=";
	 String SERVER_PASSWORD				= "-Dserver.password=";
	 String SERVER_SHUTDOWN_PORT		= "-Dserver.shutdown.port=";
	 String JAVA_TOMCAT_RUN             = "tomcat:run";
	 String JAVA_UNIX_PROCESS_KILL_CMD	= "kill -9 ";
	
	//Constants for NodeJs Plugin
	 String NODE_EXE_PATH				= "\\node.exe";
	 String NODEJS_DIR_NAME				= "NodeJS";
	 String NODE_CONFIG_FILE 			= "/source/public/resources/" + CONFIG_FILE;
	 String NODE_CMD		 			= "node";
	 String NODE_SERVER_FILE			= "server.js";
	 String NODE_ENV_FILE				= "runagainstsource.info";
	 String NODE_SQL_DIR				= "/source/sql/";
	 String NODE_SQL_FILE 				= "/site.sql";
	
	//Constants for SharePoint Plugin
	 String STSADM_PATH					= "\\Program Files\\Common Files\\Microsoft Shared\\Web Server Extensions\\12\\BIN\\STSADM.EXE";
	 String SP_DIR_NAME					= "system32";
	 String SHAREPOINT_STSADM 			= "stsadm";
	 String SHAREPOINT_RESTORE 			= " restore";
	 String SHAREPOINT_ADDSOLUTION 		= "addsolution";
	 String SHAREPOINT_DEPLOYSOLUTION 	= "deploysolution";
	 String SHAREPOINT_WSP_CONFIG_FILE 	= "/WSPBuilder.exe.config";
	 String SHAREPOINT_SOLUTION_PATH	= "SolutionPath";
	 String SHAREPOINT_OUTPUT_PATH 		= "OutputPath";
	 String SHAREPOINT_WSPNAME 			= "WSPName";
	 String SHAREPOINT_APPSETTINGS 		= "appSettings";
	 String SHAREPOINT_KEY 				= "key";
	 String SHAREPOINT_VALUE 			= "value";
	 String SHAREPOINT_ADD 				= "add";
	 String SHAREPOINT_STR_O 			= "-o";
	 String SHAREPOINT_STR_URL 			= "url";
	 String SHAREPOINT_STR_FILENAME 	= "filename";
	 String SHAREPOINT_STR_NAME 		= "name";
	 String SHAREPOINT_STR_IMMEDIATE 	= "immediate";
	 String SHAREPOINT_STR_ALLOWACDEP 	= "allowgacdeployment";
	 String SHAREPOINT_STR_OVERWRITE 	= "overwrite";
	 String SHAREPOINT_STR_COLON 		= ":";
	 String SHAREPOINT_STR_DOUBLESLASH 	= "//";
	 String SHAREPOINT_STR_BACKSLASH 	= "/";
	 String SHAREPOINT_STR_HYPEN 		= "-";
	 String SHAREPOINT_STR_DOUBLEQUOTES = "\"";
	 
	//Constants for WordPress Plugin
	//Constants for Drupal Plugin
     String WORDPRESS_TEST_CONFIG_FILE 	 = "/source/wordpress/config/" + CONFIG_FILE;
     String APACHE_DEFAULT_PORT			 = "80";	
     String WORDPRESS_UPDATE_TABLE       = "update wp_options set option_value='";
     String WORDPRESS_UPDATE_WHERE       = "' where option_name = 'siteurl'";  
     String WORDPRESS_UPDATE_HOME_WHERE  = "' where option_name = 'home'";  
     String WORDPRESS_SQL_DIR			 = "/source/sql/";
	 String WORDPRESS_SQL_FILE			 = "/site.sql";
	 
	 String OS_NAME						= "os.name";
	 String WINDOWS_PLATFORM			= "Windows";
	 String LOG_FILE_DIRECTORY			=  DO_NOT_CHECKIN_FOLDER + "/log";
	 String SERVER_LOG_FILE				= "/server.log";
     
}
