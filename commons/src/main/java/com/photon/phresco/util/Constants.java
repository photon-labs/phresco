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
package com.photon.phresco.util;

public interface Constants {

	int STATUS_OPEN = 0;
	int STATUS_EXPIRED = 1;
	int STATUS_LOCKED = 2;
	int STATUS_DELETED = 3;

    String SPACE = " ";
    String REM_DELIMETER = "REM";
    String PROJECT_FOLDER = "project";
    String OSNAME  = "os.name";
    String WINDOWS = "Windows";

    String PHRESCO_HOME = "PHRESCO_HOME";
    String USER_HOME = "user.home";
    String JAVA_TMP_DIR = "java.io.tmpdir";
    String PROJECTS_HOME = "projects";
    String PROJECTS_WORKSPACE = "workspace";
    String PROJECTS_TEMP = "temp";
    String ARCHIVE_HOME = "workspace/archive";
    String PROJECT_INFO_FILE = "project.info";
    String TOOLS_DIR = "tools";
    String JENKINS_DIR = "jenkins";
    String PLUGIN_DIR = "plugins";
    String JENKINS_HOME = "JENKINS_HOME";
    String TYPE_TOMCAT	= "Apache Tomcat";
	String TYPE_JBOSS = "JBoss";
	String TYPE_WEBLOGIC ="WebLogic";
    
    // Constants for Maven
    String MVN_COMMAND = "mvn";
    String MVN_ARCHETYPE = "archetype";
    String MVN_GOAL_GENERATE = "generate";
    String MVN_GOAL_PACKAGE = "package";
    String MVN_PLUGIN_PHP_ID = "php:";
    String MVN_PLUGIN_DRUPAL_ID = "drupal:";
    String MVN_PLUGIN_ANDROID_ID = "android:";
    String MVN_PLUGIN_JAVA_ID = "java:";
    String MVN_PLUGIN_NODEJS_ID = "nodejs:";
    String MVN_PLUGIN_SHAREPOINT_ID = "sharepoint:";
    String MVN_GOAL_DEPLOY = "deploy";
    String MVN_PLUGIN_IPHONE_ID = "xcode:";
    String MVN_PLUGIN_WORDPRESS_ID = "wordpress:";
    String MVN_PLUGIN_WINDOWS_PHONE_ID = "windows-phone:";
    
    //Constants for Authentication Token
	String AUTH_TOKEN = "auth_token";

    // Constants for Server
    String SETTINGS_TEMPLATE_SERVER = "Server";
    String SETTINGS_TEMPLATE_BROWSER = "Browser";
    String SERVER_PROTOCOL = "protocol";
    String SERVER_HOST = "host";
    String SERVER_PORT = "port";
    String SERVER_ADMIN_USERNAME = "admin_username";
    String SERVER_ADMIN_PASSWORD = "admin_password";
    String SERVER_DEPLOY_DIR = "deploy_dir";
    String SERVER_TYPE = "type";
    String SERVER_CONTEXT = "context";
    String SERVER_VERSION = "version";
    String SERVER_REMOTE_DEPLOYMENT = "remoteDeployment";

    // Constants for Database
    String DB_PROTOCOL="http";
    String SETTINGS_TEMPLATE_DB = "Database";
    String DB_NAME = "dbname";
    String DB_HOST = "host";
    String DB_PORT = "port";
    String DB_USERNAME = "username";
    String DB_PASSWORD = "password";
    String DB_TYPE = "type";
    String DB_DRIVER = "driver";
    String DB_TABLE_PREFIX = "table_prefix";
    String DB_VERSION = "version";

    // Constants for Email
    String SETTINGS_TEMPLATE_EMAIL = "Email";
    String EMAIL_HOST = "host";
    String EMAIL_PORT = "port";
    String EMAIL_USER = "username";
    String EMAIL_PASSWORD = "password";

    // Constants for Web Service
    String SETTINGS_TEMPLATE_WEBSERVICE = "WebService";
    String WEB_SERVICE_PROTOCOL = "protocol";
    String WEB_SERVICE_HOST = "host";
    String WEB_SERVICE_PORT = "port";
    String WEB_SERVICE_CONTEXT = "context";
    String WEB_SERVICE_CONFIG_URL="configUrl";
    String WEB_SERVICE_USERNAME = "username";
    String WEB_SERVICE_PASSWORD = "password";

    String BROWSER_NAME = "browser.name";
    
    //Common contants
    String STR_MINUSD = "-D";
    String STR_COLON = ":";
    String STR_EQUAL = "=";
    String STR_DOUBLE_QUOTES = "\"";

    //Action constants
    String TEST_FUNCTIONAL = "Functional";
    
    String SITE_SQL = "site.sql";
	String DB_MYSQL   = "mysql";
	String JSON_PATH = "/.phresco/sqlfile.json";
	
	//constants for server version
	
	String WEBLOGIC_12c = "12c(12.1.1)";
	String WEBLOGIC_11gR1 = "11gR1(10.3.6)";
	String WEBLOGIC_12c_PLUGIN_VERSION = "12.1.1.0";
	String WEBLOGIC_11gr1c_PLUGIN_VERSION = "10.3.6.0";
	
    /*
     * Quality report property keys
     */
    /* java test configuration */
    String PATH_JAVA_FUNCTIONAL_TEST = "java.functional.test";
    String PATH_JAVA_FUNCTIONAL_TEST_REPORT = "java.functional.test.report";
    String PATH_JAVA_UNIT_TEST = "java.unit.test";
    String PATH_JAVA_UNIT_TEST_REPORT = "java.unit.test.report";
    String PATH_JAVA_PERFORMANCE_TEST = "java.performance.test";
    String PATH_JAVA_PERFORMANCE_TEST_REPORT = "java.performance.test.report";
    String PATH_JAVA_LOAD_TEST = "java.load.test";
    String PATH_JAVA_LOAD_TEST_REPORT = "java.load.test.report";
    String PATH_JAVA_FUNCTIONAL_ADAPT = "java.functional.adapt.config";
    
    /* html5 test configuration */
    String PATH_HTML5_FUNCTIONAL_TEST = "html5.functional.test";
    String PATH_HTML5_FUNCTIONAL_TEST_REPORT = "html5.functional.test.report";
    String PATH_HTML5_UNIT_TEST = "html5.unit.test";
    String PATH_HTML5_UNIT_TEST_REPORT = "html5.unit.test.report";
    String PATH_HTML5_PERFORMANCE_TEST = "html5.performance.test";
    String PATH_HTML5_PERFORMANCE_TEST_REPORT= "html5.performance.test.report";
    String PATH_HTML5_LOAD_TEST = "html5.load.test";
    String PATH_HTML5_LOAD_TEST_REPORT = "html5.load.test.report";
    String PATH_HTML5_FUNCTIONAL_ADAPT = "html5.functional.adapt.config";
    
    /* sharepoint test configuration */
    String PATH_SHAREPOINT_FUNCTIONAL_TEST = "sharepoint.functional.test";
    String PATH_SHAREPOINT_FUNCTIONAL_TEST_REPORT = "sharepoint.functional.test.report";
    String PATH_SHAREPOINT_UNIT_TEST = "sharepoint.unit.test";
    String PATH_SHAREPOINT_UNIT_TEST_REPORT = "sharepoint.unit.test.report";
    String PATH_SHAREPOINT_PERFORMANCE_TEST = "sharepoint.performance.test";
    String PATH_SHAREPOINT_PERFORMANCE_TEST_REPORT = "sharepoint.performance.test.report";
    String PATH_SHAREPOINT_LOAD_TEST = "sharepoint.load.test";
    String PATH_SHAREPOINT_LOAD_TEST_REPORT = "sharepoint.load.test.report";
    String PATH_SHAREPOINT_FUNCTIONAL_ADAPT = "sharepoint.functional.adapt.config";
    
    /* php test configuration */
    String PATH_PHP_FUNCTIONAL_TEST = "php.functional.test";
    String PATH_PHP_FUNCTIONAL_TEST_REPORT = "php.functional.test.report";
    String PATH_PHP_UNIT_TEST = "php.unit.test";
    String PATH_PHP_UNIT_TEST_REPORT = "php.unit.test.report";
    String PATH_PHP_PERFORMANCE_TEST = "php.performance.test";
    String PATH_PHP_PERFORMANCE_TEST_REPORT = "php.performance.test.report";
    String PATH_PHP_LOAD_TEST = "php.load.test";
    String PATH_PHP_LOAD_TEST_REPORT = "php.load.test.report";
    String PATH_PHP_FUNCTIONAL_ADAPT = "php.functional.adapt.config";
   
    /* drupal test configuration */
    String PATH_DRUPAL_FUNCTIONAL_TEST = "drupal.functional.test";
    String PATH_DRUPAL_FUNCTIONAL_TEST_REPORT = "drupal.functional.test.report";
    String PATH_DRUPAL_UNIT_TEST = "drupal.unit.test";
    String PATH_DRUPAL_UNIT_TEST_REPORT = "drupal.unit.test.report";
    String PATH_DRUPAL_PERFORMANCE_TEST = "drupal.performance.test";
    String PATH_DRUPAL_PERFORMANCE_TEST_REPORT = "drupal.performance.test.report";
    String PATH_DRUPAL_LOAD_TEST = "drupal.load.test";
    String PATH_DRUPAL_LOAD_TEST_REPORT = "drupal.load.test.report";
    String PATH_DRUPAL_FUNCTIONAL_ADAPT = "drupal.functional.adapt.config";
    
    /* php web service test configuration */
    String PATH_PHP_WEBSERVICE_FUNCTIONAL_TEST = "php.webservice.functional.test";
    String PATH_PHP_WEBSERVICE_FUNCTIONAL_TEST_REPORT = "php.webservice.functional.test.report";
    String PATH_PHP_WEBSERVICE_UNIT_TEST = "php.webservice.unit.test";
    String PATH_PHP_WEBSERVICE_UNIT_TEST_REPORT = "php.webservice.unit.test.report";
    String PATH_PHP_WEBSERVICE_PERFORMANCE_TEST = "php.webservice.performance.test";
    String PATH_PHP_WEBSERVICE_PERFORMANCE_TEST_REPORT = "php.webservice.performance.test.report";
    String PATH_PHP_WEBSERVICE_LOAD_TEST = "php.webservice.load.test";
    String PATH_PHP_WEBSERVICE_LOAD_TEST_REPORT = "php.webservice.load.test.report";
    String PATH_PHP_WEBSERVICE_FUNCTIONAL_ADAPT = "php.webservice.functional.adapt.config";
    
    /* nodejs test configuration */
    String PATH_NODEJS_FUNCTIONAL_TEST = "nodejs.functional.test";
    String PATH_NODEJS_FUNCTIONAL_TEST_REPORT = "nodejs.functional.test.report";
    String PATH_NODEJS_UNIT_TEST = "nodejs.unit.test";
    String PATH_NODEJS_UNIT_TEST_REPORT = "nodejs.unit.test.report";
    String PATH_NODEJS_PERFORMANCE_TEST = "nodejs.performance.test";
    String PATH_NODEJS_PERFORMANCE_TEST_REPORT = "nodejs.performance.test.report";
    String PATH_NODEJS_LOAD_TEST = "nodejs.load.test";
    String PATH_NODEJS_LOAD_TEST_REPORT = "nodejs.load.test.report";
    String PATH_NODEJS_FUNCTIONAL_ADAPT = "nodejs.functional.adapt.config";
    
    /* nodejs web service test configuration */
    String PATH_NODEJS_WEBSERVICE_FUNCTIONAL_TEST = "nodejs.webservice.functional.test";
    String PATH_NODEJS_WEBSERVICE_FUNCTIONAL_TEST_REPORT = "nodejs.webservice.functional.test.report";
    String PATH_NODEJS_WEBSERVICE_UNIT_TEST = "nodejs.webservice.unit.test";
    String PATH_NODEJS_WEBSERVICE_UNIT_TEST_REPORT = "nodejs.webservice.unit.test.report";
    String PATH_NODEJS_WEBSERVICE_PERFORMANCE_TEST = "nodejs.webservice.performance.test";
    String PATH_NODEJS_WEBSERVICE_PERFORMANCE_TEST_REPORT = "nodejs.webservice.performance.test.report";
    String PATH_NODEJS_WEBSERVICE_LOAD_TEST = "nodejs.webservice.load.test";
    String PATH_NODEJS_WEBSERVICE_LOAD_TEST_REPORT = "nodejs.webservice.load.test.report";
    String PATH_NODEJS_WEBSERVICE_FUNCTIONAL_ADAPT = "nodejs.webservice.functional.adapt.config";
    
    /* android test configuration */
    String PATH_ANDROID_FUNCTIONAL_TEST = "android.functional.test";
    String PATH_ANDROID_FUNCTIONAL_TEST_REPORT = "android.functional.test.report";
    String PATH_ANDROID_UNIT_TEST = "android.unit.test";
    String PATH_ANDROID_UNIT_TEST_REPORT = "android.unit.test.report";
    String PATH_ANDROID_PERFORMANCE_TEST = "android.performance.test";
    String PATH_ANDROID_PERFORMANCE_TEST_REPORT = "android.performance.test.report";
    String PATH_ANDROID_LOAD_TEST = "android.load.test";
    String PATH_ANDROID_LOAD_TEST_REPORT = "android.load.test.report";
    String PATH_ANDROID_FUNCTIONAL_ADAPT = "android.functional.adapt.config";
    
    /* iphone test configuration */
    String PATH_IPHONE_FUNCTIONAL_TEST = "iphone.functional.test";
    String PATH_IPHONE_FUNCTIONAL_TEST_REPORT = "iphone.functional.test.report";
    String PATH_IPHONE_UNIT_TEST = "iphone.unit.test";
    String PATH_IPHONE_UNIT_TEST_REPORT = "iphone.unit.test.report";
    String PATH_IPHONE_PERFORMANCE_TEST = "iphone.performance.test";
    String PATH_IPHONE_PERFORMANCE_TEST_REPORT = "iphone.performance.test.report";
    String PATH_IPHONE_LOAD_TEST = "iphone.load.test";
    String PATH_IPHONE_LOAD_TEST_REPORT = "iphone.load.test.report";
    String PATH_IPHONE_FUNCTIONAL_ADAPT = "iphone.functional.adapt.config";
    
    /* iphone test configuration */
    String PATH_IPHONE_HYBRID_FUNCTIONAL_TEST = "iphone.hybrid.functional.test";
    String PATH_IPHONE_HYBRID_FUNCTIONAL_TEST_REPORT = "iphone.hybrid.functional.test.report";
    String PATH_IPHONE_HYBRID_UNIT_TEST = "iphone.hybrid.unit.test";
    String PATH_IPHONE_HYBRID_UNIT_TEST_REPORT = "iphone.hybrid.unit.test.report";
    String PATH_IPHONE_HYBRID_PERFORMANCE_TEST = "iphone.hybrid.performance.test";
    String PATH_IPHONE_HYBRID_PERFORMANCE_TEST_REPORT = "iphone.hybrid.performance.test.report";
    String PATH_IPHONE_HYBRID_LOAD_TEST = "iphone.hybrid.load.test";
    String PATH_IPHONE_HYBRID_LOAD_TEST_REPORT = "iphone.hybrid.load.test.report";
    String PATH_IPHONE_HYBRID_FUNCTIONAL_ADAPT = "iphone.hybrid.functional.adapt.config";
    
    /* blackberry test configuration */
    String PATH_BLACKBERRY_FUNCTIONAL_TEST = "blackberry.functional.test";
    String PATH_BLACKBERRY_FUNCTIONAL_TEST_REPORT = "blackberry.functional.test.report";
    String PATH_BLACKBERRY_UNIT_TEST = "blackberry.unit.test";
    String PATH_BLACKBERRY_UNIT_TEST_REPORT = "blackberry.unit.test.report";
    String PATH_BLACKBERRY_PERFORMANCE_TEST = "blackberry.performance.test";
    String PATH_BLACKBERRY_PERFORMANCE_TEST_REPORT = "blackberry.performance.test.report";
    String PATH_BLACKBERRY_LOAD_TEST = "blackberry.load.test";
    String PATH_BLACKBERRY_LOAD_TEST_REPORT = "blackberry.load.test.report";
    String PATH_BLACKBERRY_FUNCTIONAL_ADAPT = "blackberry.functional.adapt.config";
    
    /* java web service test configuration */
    String PATH_JAVA_WEBSERVICE_FUNCTIONAL_TEST = "java.webservice.functional.test";
    String PATH_JAVA_WEBSERVICE_FUNCTIONAL_TEST_REPORT = "java.webservice.functional.test.report";
    String PATH_JAVA_WEBSERVICE_UNIT_TEST = "java.webservice.unit.test";
    String PATH_JAVA_WEBSERVICE_UNIT_TEST_REPORT = "java.webservice.unit.test.report";
    String PATH_JAVA_WEBSERVICE_PERFORMANCE_TEST = "java.webservice.performance.test";
    String PATH_JAVA_WEBSERVICE_PERFORMANCE_TEST_REPORT = "java.webservice.performance.test.report";
    String PATH_JAVA_WEBSERVICE_LOAD_TEST = "java.webservice.load.test";
    String PATH_JAVA_WEBSERVICE_LOAD_TEST_REPORT = "java.webservice.load.test.report";
    String PATH_JAVA_WEBSERVICE_FUNCTIONAL_ADAPT = "java.webservice.functional.adapt.config";
    
    /* unit test suite report path */
    String XPATH_PHP_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_PHP_DRUPAL6_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_PHP_DRUPAL7_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_PHP_WORDPRESS_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_ANDROID_NATIVE_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_ANDROID_HYBRID_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_ANDROID_WEB_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_IPHONE_NATIVE_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_IPHONE_HYBRID_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_IPHONE_WEB_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_SHAREPOINT_UNIT_TESTSUITE = "/test-results/test-suite/results/test-suite/results/test-suite/results/test-suite";
    String XPATH_BLACKBERRY_UNIT_TESTSUITE = "/testsuites/testsuite";
    String XPATH_JAVA_UNIT_TESTSUITE = "/testsuite";
    String XPATH_NODE_JS_UNIT_TESTSUITE = "/testsuite";
    String XPATH_HTML5_UNIT_TESTSUITE = "/testsuite";
    String XPATH_HTML5_WIDGET_UNIT_TESTSUITE = "/testsuite";
    String PATH_HTML5_MULTICHANNEL_JQUERY_UNIT_TEST_TESTSUITE = "/testsuite";
    String PATH_HTML5_JQUERY_MOBILE_WIDGET_UNIT_TEST_TESTSUITE = "/testsuite";
    String XPATH_JAVA_WEBSERVICE_UNIT_TESTSUITE = "/testsuite";
    String XPATH_NODE_JS_WEBSERVICE_UNIT_TESTSUITE = "/testsuite";
    String XPATH_PHP_WEBSERVICE_UNIT_TESTSUITE = "/testsuites/testsuite";
    
    /* functional test suite report path */
    String XPATH_PHP_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite/testsuite";
    String XPATH_PHP_DRUPAL6_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite/testsuite";
    String XPATH_PHP_DRUPAL7_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite/testsuite";
    String XPATH_PHP_WORDPRESS_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite/testsuite";
    String XPATH_ANDROID_NATIVE_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite";
    String XPATH_ANDROID_HYBRID_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_ANDROID_WEB_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite";
    String XPATH_IPHONE_NATIVE_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite";
    String XPATH_IPHONE_HYBRID_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_IPHONE_WEB_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite";
    String XPATH_SHAREPOINT_FUNCTIONAL_TESTSUITE = "/test-results/test-suite/results/test-suite/results/test-suite";
    String XPATH_BLACKBERRY_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite";
    String XPATH_JAVA_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_NODE_JS_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_HTML5_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_HTML5_WIDGET_FUNCTIONAL_TESTSUITE = "/testsuite";
    String PATH_HTML5_MULTICHANNEL_JQUERY_FUNCTIONAL_TEST_TESTSUITE = "/testsuite";
    String PATH_HTML5_JQUERY_MOBILE_WIDGET_FUNCTIONAL_TEST_TESTSUITE = "/testsuite";
    String XPATH_JAVA_WEBSERVICE_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_NODE_JS_WEBSERVICE_FUNCTIONAL_TESTSUITE = "/testsuite";
    String XPATH_PHP_WEBSERVICE_FUNCTIONAL_TESTSUITE = "/testsuites/testsuite";
    
    /* test case report path */
    String XPATH_PHP_TESTCASE = "/testcase";
    String XPATH_PHP_DRUPAL7_TESTCASE = "/testcase";
    String XPATH_ANDROID_NATIVE_TESTCASE = "/testcase";
    String XPATH_ANDROID_HYBRID_TESTCASE = "/testcase";
    String XPATH_ANDROID_WEB_TESTCASE = "/testcase";
    String XPATH_IPHONE_NATIVE_TESTCASE = "/testcase";
    String XPATH_IPHONE_HYBRID_TESTCASE = "/testcase";
    String XPATH_IPHONE_WEB_TESTCASE = "/testcase";
    String XPATH_SHAREPOINT_TESTCASE = "/results/test-case";
    String XPATH_BLACKBERRY_TESTCASE = "/testcase";
    String XPATH_JAVA_TESTCASE = "/testcase";
    String XPATH_NODE_JS_TESTCASE = "/testcase";
    String XPATH_HTML5_TESTCASE = "/testcase";
    String XPATH_HTML5_WIDGET_TESTCASE = "/testcase";
    String XPATH_HTML5_MULTICHANNEL_JQUERY_TESTCASE = "/testcase";
    String XPATH_HTML5_JQUERY_MOBILE_WIDGET_TESTCASE = "/testcase";
    String XPATH_JAVA_WEBSERVICE_TESTCASE = "/testcase";
    String XPATH_NODE_JS_WEBSERVICE_TESTCASE = "/testcase";
    String XPATH_PHP_WEBSERVICE_TESTCASE = "/testcase";
    
    String SQUARE_CLOSE = "]";
    String COMMA = ",";
    String SQUARE_OPEN = "[";
}
