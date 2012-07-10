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
package com.photon.phresco.commons;

import java.io.File;


public interface FrameworkConstants {

    /*
     * Common constants
     */
	String FEATURES = "features"; 
    int JOB_STATUS_NOTOK = -1;
    String FOLDER_DOT_PHRESCO = ".phresco";
    String PHRESCO_HOME = "PHRESCO_HOME";
    String USER_HOME = "user.home";
    String PROJECTS_HOME = "projects";
    String ARCHIVE_FORMAT = ".zip";
    String SETTINGS_INFO_FILE_NAME = "settings.xml";
    String CONFIGURATION_INFO_FILE_NAME = "phresco-env-config.xml";
    String BUILD_INFO_FILE_NAME = "build.info";
    String PROJECT_INFO = "project.info";
    
    String DO_NOT_CHECKIN_DIR = "do_not_checkin";
    String BUILD_DIR = DO_NOT_CHECKIN_DIR + File.separator + "build";
    String CHECKIN_DIR = "do_not_checkin";
    String BUILD_PATH = "build";
    String WORKSPACE_DIR = "workspace";
    String SPACE = " ";
    String POM_XML = "pom.xml";
    String STATIC_ANALYSIS_REPORT = "static-analysis-report";
    String INDEX_HTML = "index.html";
	String WAR = "war";
	String PATH_SUREFIRE_REPORTS = "/target/surefire-reports";
    
    String JENKINS_HOME = "JENKINS_HOME";
    String CI_JOB_INFO_NAME = "cijob.info";
    String CI_JOB_JSON_BUILDS = "builds";
    String CI_JOB_BUILD_RESULT = "result";
    String CI_JOB_BUILD_ID = "id";
    String CI_JOB_BUILD_TIME_STAMP = "timestamp";
    String CI_JOB_BUILD_ARTIFACTS = "artifacts";
    String CI_JOB_BUILD_ARTIFACT = "artifact";
    String CI_JOB_BUILD_DOWNLOAD_PATH = "relativePath";
    String CI_CREATE_JOB_COMMAND = "create-job";
    String CI_UPDATE_JOB_COMMAND = "update-job";
    String CI_BUILD_JOB_COMMAND = "build";
    String CI_BUILD_DELETE_COMMAND = "delete-builds";
    String CI_JOB_DELETE_COMMAND = "delete-job";
    String CI_RELOAD_CONFIGS = "reload-configuration";
    String CI_APPEND_JOBS = "appendJobs";
    String CI_CREATE_NEW_JOBS = "createNewJobs";
    String CI_JENKINS_ALIVE = "jenkinsAlive";
    String CI_BUILD_JENKINS_ALIVE = "buildJenkinsAlive";
    String CI_BUILD_IS_IN_PROGRESS = "isBuildInProgress";
    String CI_NO_OF_JOBS_IN_PROGRESS = "noOfJobsIsInProgress";
    String CI_BUILD_TRIGGERED_FROM_UI = "buildTriggeredFromUI";
    String CI_PROFILE = "-Pci clean ";
    String TIMER_TRIGGER = "TimerTrigger";
    String COLON = ":";
    String HTTP_PROTOCOL = "http";
    String PROTOCOL_POSTFIX = "://";
    String FORWARD_SLASH = "/";
    String LOCALHOST = "localhost";
    String JENKINS_START = "JenkinsStart";
    String JENKINS_STOP = "JenkinsStop";
    String NODE_START = "start";
    String NODE_STOP = "stop";
    String START_SERVER = "start";
    String STOP_SERVER = "stop";
    String NODEJS_SERVER_NAME = "serverName";
    String NODEJS_DB_NAME = "databaseName";
    String NODEJS_IMPORT_VALUE = "importSql";
    String SERVER_PROTOCOL = "server.protocol";
    String SERVER_HOST = "server.host";
    String SERVER_PORT = "server.port";
    String NODEJS_LOG_DIR = "log";
    String RUN_AGS_ENV_FILE = "runagainstsource.info";
    String NODEJS_LOG_FILE = "nodejsLog.log";
    String NODEJS_ERROR_LOG_FILE = "nodejserrorLog.log";
    String JAVA_LOG_FILE = "javaLog.log";
    String LINE_SEPERATOR = "line.separator";
    String READ_LOG_VIEW = "view";
    String READ_ERROR_LOG_VIEW = "errorLogView";
    String PATH = "path";
    String IMPORT_SQL = "importSql";
    String ADDITIONAL_CONTEXT_PATH = "additional_context";
    String TOMCAT_DIR ="tomcat";
    String TOMCAT_LOGS_DIR = "logs";
    String CATALINA_FILE_START_NAME ="catalina";
    String IMPORT_PROPERTY ="importsql.property";
    String TEMP_FOLDER ="temp";
    String NODEJS_RUN_AGAINST = "nodeJS_runAgnSrc";
    String JAVA_RUN_AGAINST = "runAgnSrc";
	
	String JAVA_POM_MANIFEST = "manifest";
	String JAVA_POM_MAINCLASS = "mainClass";
	String FINAL_NAME = "finalName";
	String MAIN_CLASS_VALUE ="mainClassValue";
	String JARNAME = "jarName";
    String MAINCLASSNAME = "mainClassName";
    String ENVIRONMENT_NAME = "environmentName";
    String MODULE_NAME = "moduleName";
    String SHOW_ERROR = "showerror";
    String HIDE_LOG = "hidelog";
	String SHOW_DEBUG = "showdebug";
    String SKIP_TEST = "skiptest";
    String DEPLOY = "deploy";
    String BUILD_INFO_ENVS = "buildInfoEnvs";
    String DEPLOY_IMPORT_SQL = "importSql";
    String DEPLOY_SERVER_PARAM = "serverName";
    String DEPLOY_DATABASE_PARAM = "databaseName";
    String DEPLOY_EMAIL_PARAM = "emailName";
    String DEPLOY_WEBSERVICE_PARAM = "webServiceName";
    String TEST_PARAM = "test=";
    String TEST_PARAM_VALUE = "AllTest";
    String ALL_TEST_SUITES = "All";
    String KEY_QUOTES = "\"";
    String FROM_PAGE = "edit";
    String DEPLOY_BUILD_NAME = "buildName";
    String IPHONE_BUILD_NAME = "application.path";
    String IPHONE_SIMULATOR_VERSION = "simulator.version";
    String IPHONE_SCHEMA_PARAM = "scheme";
    String DEPLOY_LOCATION = "deployLocation";
    String DEPLOY_CONTEXT = "context";
    String DEPLOY_SERVERNAME="serverName";
    String DEPLOY_ANDROID_DEVICE_MODE = "android.device";
    String DEPLOY_ANDROID_EMULATOR_AVD = "android.emulator.avd";
    String DEVICE_DEPLOY = "device.deploy";
    String UNIT_TEST = "unittest";
    String TRUE = "true";
    String FALSE = "false";
    String NODE_JS_ID = "tech-nodejs-webservice";
    String TEST_DIR = "test";
    String TARGET_DIR = "target";
    String JMETER_REPORTS = "jmeter-reports";
    String FUNCTIONAL = "functional";
    String UNIT = "unit";
    String LOAD = "load";
    String PERFORMACE = "performance";
    String WEBSERVICE = "WebService";
    String WEBSERVICES_DIR = "WebServices";
    String ANDROID_PERFORMACE = "androidPerformance";
    String CI_SETUP = "CISetup";
    String CI_START = "CIStart";
    String CI_STOP = "CIStop";
    String TEST_DIRECTORY = "dir_type";
    String MVN_TEST_COMMAND = "mvn clean test";
    String MVN_SHAREPOINT_NUNIT_TEST_COMMAND = "mvn sharepoint:test";
    String MVN_INSTALL_COMMAND = "mvn install -DskipTests=true";
    String MVN_MOBILE_INSTALL_COMMAND = "mvn install ";
	String MVN_ANDROID_PERF_COMMAND = "mvn install ";
    String MVN_SELENIUM_STOP_COMMAND = "mvn selenium:stop-server";
    String MVN_SONAR_COMMAND = "mvn sonar:sonar";
    String MVN_JENKINS_START = "mvn t7:run-forked";
    String MVN_JENKINS_STOP = "mvn t7:stop-forked";
    String MVN_IPHONE_IPA_COMMAND = "mvn xcode:ipaBuilder";
    String MVN_IPHONE_FUNCTIONAL_COMMAND = "mvn xcode:instruments"; // IPhone functional test command
    String MVN_IPHONE_NATIVE_UNITTEST = "mvn xcode:xcodebuild"; // IPhone unit test command
    String MVN_IPHONE_CODE_VALIDATE = "mvn xcode:codevalidate"; // IPhone code validate command
    String MVN_SITE_COMMAND = "mvn site";
    String PHRESCO_FILE_SERVER_PORT_NO = "phresco.file.server.port";
    String APPLICATIONS = "applications";
    String SETTINGS = "settings";
    String FILE_EXTENSION_XML = "xml";
    String SEMI_COLON = ";";
    String SQUARE_CLOSE = "]";
    String COMMA = ",";
    String SQUARE_OPEN = "[";
    String GRAPH_SUMMARY_DATA = "var summaryData = ";
    String GRAPH_VOLUME_DATA = "var volumeData = ";
    String GRAPH_DATA = "var priceData = ";
    String SCRIPT_END = "</script>";
    String SCRIPT_START = "<script>";
    String GRAPH_JSON = "var jsonData = [";
    String IPHONE_SDK = "sdk";
    String IPHONE_CONFIGURATION = "configuration";
    String IPHONE_TARGET_NAME = "targetName";
    String IPHONE_PLISTFILE = "plistfile";
    String ENCRYPT = "encrypt";
    String IPHONE_XCODEPROJ = "/source/Phresco.xcodeproj";
    String SOURCE_DIR = "source";
    String IPHONE_XCODE_PROJ_EXTN = "xcodeproj";
    String ANDROID_DEVICE = "device";
    String ANDROID_DEVICE_LIST = "deviceList";
    String ANDROID_PROGUARD_SKIP = "android.proguard.skip";
    String PROFILE_ID = "sign";
    String GOAL_INSTALL = "install";
    String GOAL_SIGN = "sign";
    String ANDROID_PROFILE_PLUGIN_GROUP_ID = "org.apache.maven.plugins";
    String ANDROID_PROFILE_PLUGIN_ARTIFACT_ID = "maven-jarsigner-plugin";
    String ANDROID_PROFILE_PLUGIN_VERSION = "1.2";
    String PHASE_PACKAGE = "package";
    String ANDROID_EXECUTION_ID = "signing";
    String ELEMENT_ARCHIVE_DIR = "archiveDirectory";
    String ELEMENT_REMOVE_EXIST_SIGN = "removeExistingSignatures";
    String ELEMENT_INCLUDES = "includes";
    String ELEMENT_INCLUDE = "include";
    String ELEMENT_BUILD = "do_not_checkin/build/*.apk";
    String ELEMENT_TARGET = "do_not_checkin/target/*.apk";
    String ELEMENT_VERBOS = "verbose";
    String ELEMENT_VERIFY = "verify";
    
    String POST = "POST";
    String GET = "GET";
	String SKIPTESTS = "skipTests";
	String PROFILE = "profile";
	String CAN_CREATE_IPA = "canCreateIpa";
	String DEPLOY_TO_DEVICE = "deviceDeploy";
    String BUILD_NAME = "buildName";
	String BUILD_NUMBER = "buildNumber";
    String JFORUM_PARAMETER_URL = "/jforum.page?action=validateLogin&module=user";
    String JFORUM_USERNAME = "&username=";
    String JFORUM_PASSWORD = "&password=";
    String PUSH_SERVER_CONTEXT = "Push Your Server Context XML Here";
    
    
    String ADMIN_CONFIG_JFORUM_PATH = "phresco.forum.url";

    String CI_JDK_HOME_XML = "config.xml";
    String CI_MAVEN_HOME_XML = "hudson.tasks.Maven.xml";
    String CI_CREDENTIAL_XML = "hudson.scm.SubversionSCM.xml";
    String CI_MAILER_XML = "hudson.tasks.Mailer.xml";
    String CI_MAIL_EXT_PLUGIN = "email-ext.hpi";
    String CI_HUDSONURL = "hudsonUrl";
    String CI = "ci";
    
    /***
     * Environment Constants
     * 
     */
    String ENVIRONMENTS = "environments";
    String SETTINGS_FROM_TAB = "fromTab";
    String ENVIRONMENT_VALUES = "envs";  
    String DELETABLE_ENVS = "deletableEnvs";
    /*
     * Session Constants
     * Ex: SESSION_XXX
     */
    String SESSION_SELECTED_INFO = "selectedInfo";
    String SESSION_SELECTED_MODULES = "selectedModules";
    String SESSION_APPLICATION_TYPES = "ApplicationTypes";
    String SESSION_APPLICATION_TYPE = "ApplicationType";
    String SESSION_SELECTED_TECHNOLOGY = "SelectedTechnology";
    String SESSION_PROJECT = "project";
    String SESSION_TECHNOLOGY_MODULES = "technologyModules";
    String SESSION_PROPERTY_INFO_LIST = "propertyInfoList";
    String SESSION_SETTINGS_TEMPLATES = "SettingsTemplates";
    String SESSION_SELECTED_CONFIG_INFO = "selectedConfigurationInfo";
    String SESSION_OLD_NAME = "oldName";
    String SESSION_GRAPH_SCRIPT = "graphScript";
    String SESSION_NODEJS_SERVER_NAME = "_serverName";
    String SESSION_NODEJS_DB_NAME = "_databaseName";
    String SESSION_NODEJS_SERVER_STATUS = "_NodeJSServerStatus";
    String SESSION_JAVA_SERVER_STATUS = "_javaServerStatus";
    String SESSION_NODEJS_IMPORTSQL_VALUE = "_importSqlValue";
    String SESSION_NODEJS_SERVER_PORT_VALUE ="NodeServerPort";
    String SESSION_NODEJS_SERVER_HOST_VALUE ="NodeServerHost";
    String SESSION_NODEJS_SERVER_PROTOCOL_VALUE ="NodeServerProtocol";
    String SESSION_JAVA_SERVER_PORT_VALUE ="JavaServerPort";
    String SESSION_JAVA_SERVER_HOST_VALUE ="JavaServerHost";
    String SESSION_JAVA_SERVER_PROTOCOL_VALUE ="JavaServerProtocol";
    String SESSION_ENV_NAME = "environment";
    /*
     * Request Constants
     * Ex: REQ_XXX
     */
    String REQ_PROJECT_INFO	= "projectInfo";
	String REQ_TITLE_ADD_APPLICATION = "Add ApPlication";
    String REQ_BUILD_FROM = "from";
    String REQ_NODEJS_SERVER_LOG = "NodeJsServerLog";
    String REQ_PROJECT = "project";
    String REQ_PROJECT_CODE = "projectCode";
    String REQ_PROJECT_LOCATION = "projectLocation";
    String REQ_SELECTED_TAB = "selectedTab";
    String REQ_SELECTED_MENU = "selectedHeader";
    String REQ_PROJECTS = "Projects";
    String REQ_TOTAL_THROUGHPUT = "totalThroughput";
    String REQ_TOTAL_STD_DEV = "totalStdDev";
    String REQ_TECHNOLOGY = "technology";
    String REQ_SELECTED_TECHNOLOGY = "selectedTechnology";
    String REQ_NAME = "name";
    String REQ_CODE = "code";
    String REQ_TYPE = "type";
    String REQ_DESCRIPTION = "description";
    String REQ_APPLICATION = "application";
    String REQ_ADD_APPLICATION = "addApplication";
    String REQ_EDIT_APPLICATION = "addApplication";
    String REQ_FRAMEWORK = "framework";
    String REQ_PLATFORM = "platform";
    String REQ_APPLICATIONSERVER = "applicationserver";
    String REQ_DATABASE = "database";
    String REQ_EDITOR = "editor";
    String REQ_SELECTED_JSLIBS = "selectedJsLibs";
    String REQ_TEMP_SELECTED_JSLIBS = "tempSelectedJsLibs";
    String REQ_SELECTEDMODULES = "selectedModules";
    String REQ_SELECTED_PILOT_PROJ = "pilotProject";
    String REQ_TEMP_SELECTEDMODULES = "selectedFeatures";
    String REQ_TEMP_SELECTED_PILOT_PROJ = "selectedPilotProj";
    String REQ_PILOTS_IDS = "pilotsIds";
    String REQ_ALREADY_SELECTED_MODULES = "alreadySelectedModules";
    String REQ_ALREADY_SELECTED_JSLIBS = "alreadySelectedJsLibs";
    String REQ_ALL_JS_LIBS = "allJsLibs";
    String REQ_PILOTS_NAMES = "pilotsNames";
    String REQ_PILOT_PROJECT_INFO = "pilotProjectInfo";
    String REQ_QUALITY = "quality";
    String REQ_TEST_SUITE = "quality";
    String REQ_ERROR_TESTSUITE = "qualityError";
    String REQ_ERROR_DATA = "dataError";
    String REQ_TEST_RESULT = "testResult";
    String REQ_CONFIGURATION = "configuration";
    String REQ_SETTINGS_TEMPLATES = "SettingsTemplates";
    String REQ_SERVER_SETTINGS = "serverSettings";
    String REQ_DATABASE_SETTINGS = "databaseSettings";
    String REQ_EMAIL_SETTINGS = "emailSettings";
    String REQ_WEBSERVICE_SETTINGS = "webServiceSettings";
    String REQ_SERVER_CONFIGS = "serverConfigs";
    String REQ_DATABASE_CONFIGS = "databaseConfigs";
    String REQ_EMAIL_CONFIGS = "emailConfigs";
    String REQ_WEBSERVICE_CONFIGS = "webServiceConfigs";
    String REQ_DEPLOY_DATABASE = "database";
    String REQ_DEPLOY_SERVER = "server";
    String REQ_TEST_AGAINST = "testAgainst";
    String REQ_TEST_BUILD_ID = "buildId";
    String REQ_DEPLOY_READER = "DeployReader";
    String REQ_READER = "Reader";
    String REQ_SONAR_PATH = "SonarPath";
    String REQ_CONFIG_INFO = "configInfo";
    String REQ_OLD_NAME = "oldName";
    String REQ_ERROR = "error";
    String REQ_SELECTED_ITEMS = "check";
    String REQ_SELECTED_BUILDS = "selectedBuilds";
    String REQ_SELECTED_BUILDS_LIST = "builds";
    String REQ_SELECTED_JOBS_LIST = "Jobs";
    String REQ_SELECTED_ALL_BUILDS_LIST = "allBuilds";
    String REQ_SETTINGS = "settings";
    String REQ_BUILD = "build";
    String REQ_RELEASE = "release";
    String REQ_SELECTED_APP_TYPE = "selectedAppType";
    String REQ_SETTINGS_INFO = "settingsInfo";
    String REQ_CURRENT_SETTINGS_TEMPLATE = "CurrentTemplate";
    String REQ_ALL_TECHNOLOGIES = "allTechnologies";
    String REQ_SETTINGS_NAME = "settingName";
    String REQ_CODE_PREFIX = "codePrefix";
    String REQ_FROM_PAGE = "fromPage";
    String REQ_APPLICATION_TYPE = "applicationType";
    String REQ_OLD_CONFIG_NAME = "configName";
    String REQ_TEST_TYPE = "testType";
    String REQ_SELECTEDPROJECTS = "selectedProjects";
    String REQ_BUILD_NUMBER = "build-number";
    String REQ_BUILD_WARNING = "buildWarning";
    String REQ_DEPLOY_BUILD_NUMBER = "buildNumber";
    String REQ_ANDROID_PROFILE_DET = "androidProfileDetails";
    String REQ_ANDROID_HAS_SIGNING = "hasSigning";
    String REQ_DEPLOY_IPHONE_SIMULATOR_VERSION= "simulatorVersion";
    String REQ_HIDE_DEPLOY_TO_SIMULATOR = "deployToSimulatorStatus";
	String REQ_HIDE_DEPLOY_TO_DEVICE = "deployToDeviceStatus";
    String REQ_VIDEO = "video";
    String REQ_VIDEO_INFOS = "videoInfos";
    String REQ_VIDEO_TYPES = "videoTypes";
    String REQ_SERVER_URL = "serverUrl";
    String REQ_TEST_BROWSER = "browser";
    String REQ_TEST_TYPE_SELECTED = "testTypeSelected";
    String REQ_TEST_UNIT = "unit";
    String REQ_PROJECT_MODULES = "projectModules";
    String REQ_TEST_FUNCTIONAL = "functional";
    String REQ_TEST_PERFORMANCE = "performance";
    String REQ_TEST_LOAD = "load";
    String REQ_SHOW_SETTINGS = "showSettings";
    String REQ_TEST_BUILD_INFOS = "buildInfos";
    String REQ_TEST_SERVERS = "servers";
    String REQ_TESTSUITE_NAME = "TestSuite";
    String REQ_TESTCASES = "TestCases";
    String REQ_TESTSUITE_FAILURES = "Failures";
    String REQ_TESTSUITE_ERRORS = "Errs";
    String REQ_TESTSUITE_TESTS = "Tests";
    String REQ_ALL_TESTSUITE_MAP = "AllTestsMap";
    String REQ_TESTSUITES_CALL = "SettingInfoAndTestSuite";
    String REQ_LOGIN_ERROR = "loginErrors";
    String REQ_CORE_MODULES = "coreModules";
    String REQ_CUSTOM_MODULES = "customModules";
    String REQ_APPTYPE = "appType";
    String REQ_JMETER_REPORT_FILES = "JMeterReports";
    String REQ_ANDROID_DEVICE = "device";
    String REQ_IPHONE_DEVICE = "device";
    String REQ_IPHONE_SIMULATOR = "simulator";
    String REQ_ANDROID_DEFAULT = "default";
    String REQ_FROM_TAB = "fromTab";
    String REQ_FROM_TAB_TEST = "Test";
    String REQ_FROM_TAB_DEPLOY = "Deploy";
    String REQ_TEST_BROWSERS = "browsers";
    String REQ_PILOT_JSLIBS = "dependentJsLibraries";
    String REQ_TEST_SHOW_THROUGHPUT_GRAPH = "throughPut";
    String REQ_TEST_SHOW_RESPONSE_TIME_GRAPH = "responseTime";
    String REQ_TEST_SHOW_MIN_RESPONSE_GRAPH = "minResponseTime";
    String REQ_TEST_SHOW_MAX_RESPONSE_GRAPH = "maxResponseTime";
    String REQ_GRAPH_DATA = "GRAPH_DATA";
    String REQ_GRAPH_LABEL = "GRAPH_LABEL";
    String REQ_GRAPH_ALL_DATA = "GRAPH_ALL_DATA";
    String REQ_SHOW_GRAPH = "showGraphFor";
    String REQ_TEST_SHOW_ALL_GRAPH = "all";
    String REQ_ACTION="action";
    String REQ_BUILD_TEST="build";
    String REQ_DEPLOY = "deploy";
    String REQ_ANDROID_CONN_DEVICES = "connAndroidDevices";
    String REQ_SERVERS = "servers";
    String REQ_DATABASES = "databases";
    String REQ_WEBSERVICES = "webservices";
    String REQ_EMAIL_SUPPORTED = "emailSupported";
    String REQ_BUILD_STATUS = "buildStatus";
    String REQ_ENV_SERVER_SETTINGS = "serverSettings";
    String REQ_ENV_DATABASE_SETTINGS = "databaseSettings";
    String REQ_ENV_EMAIL_SETTINGS = "emailSettings";
    String REQ_ENV_WEBSERVICE_SETTINGS = "webServiceSettings";
    String REQ_APPLIES_TO = "appliesto";
    String REQ_SHOW_WELCOME = "showWelcome";
    String REQ_USER_INFO = "userInfo";
    String REQ_TEST_RESULT_FILE = "testResultFile";
	String REQ_TOTAL_BUILDS_SIZE = "totalBuildsSize";
	String REQ_CURRENTENV = "currentEnv"; 
	String REQ_TEMPLIST_SELECTEDSERVERS = "tempListSelectedServers";
	String REQ_TEMPLIST_SELECTEDDATABASES  = "tempListSelectedDatabases";
    String REQ_LISTSELECTED_SERVERIDS  = "listSelectedServerIds";
    String REQ_LISTSELECTED_DATABASEIDS = "listSelectedDatabaseIds";
    String REQ_LISTSELECTED_VERSIONS = "listSelectedVersions";
    String REQ_HEADER = "header";
    String REQ_HEADER_TYPE = "headerType";
    String REQ_FROM	= "from";
    String REQ_ATTRNAME	= "attrName";
    String SETTINGS_PARAMS = "SETTINGS_PARAMS";
    String REQ_KEY_SUCCESS_EMAILS = "successEmails";
    String REQ_KEY_FAILURE_EMAILS = "failureEmails";
    String REQ_SERVER_LOG = "serverLog";
    
    String REQ_EXISTING_JOB = "existingJob";
    String REQ_EXISTING_JOBS = "existingJobs";
    String REQ_CRON_BY = "cronBy";
    String REQ_HOURS = "hours";
    String REQ_MINUTES = "minutes";
    String REQ_MONTH = "month";
    String REQ_DAY = "day";
    String REQ_SCHEDULE_EVERY = "every";
    String REQ_CRON_BY_DAILY = "Daily";
    String REQ_CRON_BY_WEEKLY = "Weekly";
    String REQ_CRON_BY_MONTHLY = "Monthly";
    String REQ_CRON_BY_WEEK = "week";
    
    String REQ_CRON_EXPRESSION = "cronExpressions";
    String REQ_CRON_DATES = "cronDates";
    String REQ_JOB_NAME = "jobName";
    String REQ_SERVER_STATUS_URL = "url";
    String REQ_VERSION_INFO = "version";
    String REQ_CI_BUILD_PROGRESS = "buildInProgress";
    
    String REQ_CURRENT_VERSION = "currentVersion";
    String REQ_LATEST_VERSION = "latestVersion";
    String REQ_UPDATED_MESSAGE = "updatedMessage";
    String REQ_JFORUM_URL = "JforumUrl";
	String REQ_USER_NAME = "username";
	String REQ_PASSWORD = "password";
	
	String REQ_TEST_RESULT_FILE_NAMES = "testResultFileNames";
	String REQ_SELECTED_TEST_RESULT_FILE = "selectedTestResultFileName";
	String REQ_CORE_MODULE = "core";
	String REQ_CUSTOM_MODULE = "custom";
	String REQ_JSLIB_MODULE = "jsLib";
	
	String REQ_IMPORT_SQL ="importSqlPro";
	String REQ_TITLE_EXCEPTION = "Exception";
	String REQ_TITLE_ERROR = "Error";	
	String REQ_START_NODE_JS = "StartNodeJS";
	String REQ_READ_LOG_FILE = "readLogFile";
	String REQ_JAVA_READ_LOG_FILE = "javaReadLogFile";
	String REQ_JAVA_START = "javaStart";
	String REQ_JAVA_STOP = "javaStop";
	
	String REQ_EXTERNAL_FEATURES = "External Features";
	String REQ_CUSTOM_FEATURES = "Custom Features";
	String REQ_JS_LIBS = "JS Libraries";
	String REQ_FEATURES_FIRST_MDL_CAT = "firstModuleCat";
	String REQ_FEATURES_SECOND_MDL_CAT = "secondModuleCat";
	String REQ_FEATURES_LEFT_MODULES = "leftModules";
	String REQ_FEATURES_RIGHT_MODULES = "rightModules";
	String REQ_FEATURES_MODULE_CAT = "moduleCat";
	String REQ_FEATURES_MODULES = "modules";
	String REQ_ENVIRONMENTS = "environments";
    String REQ_LOG_REPORT = "logReport";
    String REQ_PROJECT_INFO_SERVERS = "projectInfoServers";
    String REQ_PROJECT_INFO_DATABASES = "projectInfoDatabases";
	
	String REQ_TESTCASE_NAME = "testCaseName";
	String SCREENSHOT_DIR = "screenshots";
	String IMG_PNG_TYPE = "png";
	String CONTENT_TYPE = "image/png";
	String CONTENT_DISPOSITION = "attachment";
	String DOT = ".";
	String SERIAL_NUMBER = "serialNumber";
	String REQ_XCODE_CONFIGS = "xCodeConfigs";
	String REQ_IPHONE_SDKS = "iphoneSdks";
	String REQ_IPHONE_SIMULATOR_SDKS = "iphoneSimulatorSdks";
	String MAC_OS_SDKS = "macosx";
	String MAC_DEVICE_SDKS = "iphoneos";
	String MAC_SIMULATOR_SDKS = "iphonesimulator";
	
	String REQ_SERVER_DOWNLOAD_INFO = "serverDownloadInfos";
	String REQ_DB_DOWNLOAD_INFO = "dbDownloadInfos";
	String REQ_EDITOR_DOWNLOAD_INFO = "editorDownloadInfos";
	String REQ_TOOLS_DOWNLOAD_INFO = "toolsDownloadInfos";
	
	String REQ_CONFIG_SERVER_NAMES = "configServerNames";
	String REQ_CONFIG_DB_NAMES = "configDbNames";

    /*
     * REST url
     */
    String PHRESCO_SERVER_URL = "phresco.server.url";
    String PHRESCO_CODE_PREFIX = "phresco.project.code";
    String PHRESCO_FUNCTIONAL_TESTSUITE_PATH = "phresco.quality.functional.testsuite.path";
    String PHRESCO_REPORTS_PATH = "phresco.quality.reports.path";
    String PHRESCO_CACHE_ENABLED = "phresco.framework.cache.enabled";
    String PHRESCO_SONAR_REPORT_PATH = "phresco.code.sonar.reports.path";
    String PHRESCO_SONAR_URL = "phresco.code.sonar.url";
    String PHRESCO_CI_JENKINS_URL = "phresco.ci.jenkins.url";
    String PHRESCO_JFORUM_URL = "phresco.JForum.url";
    
    String REST_APPS_PATH = "/apps";
    String REST_REPORTS = "/reports";
    String REST_APPS_UPDATE_PATH = "/apps/update";
    String REST_APPS_UPDATEPOM_PATH= "/apps/updatepom";
    String REST_APPS_UPDATEDOC_PATH = "/apps/updatedocs";
    String REST_DOWNLOADS_PATH = "/downloads";
    String REST_PILOT_PATH = "/pilots";
    String REST_SETTINGS_PATH = "/settings";
    String REST_VIDEOS_PATH = "/homepagevideos";
    String REST_LOGIN_PATH = "/login";
    String REST_CI_CONFIG_PATH = "/repo/ci/config";
    String REST_CI_SVN_PATH = "/repo/ci/svn";
    String REST_LOG_PATH = "/admin/log";
    String REST_ADMIN_CONFIG_PATH = "/admin/config";
    String REST_CREDENTIAL_PATH = "/repo/ci/credentialsxml";
    String REST_CI_JDK_HOME = "/repo/ci/javahomexml";
    String REST_CI_MAVEN_HOME = "/repo/ci/mavenhomexml";
    String REST_CI_MAILER_HOME = "/repo/ci/mailxml";
    String REST_CI_MAIL_PLUGIN = "/repo/ci/emailext";
    String REST_ENVE_PATH = "/settings/env";
    String REST_SERVERS_PATH = "/servers";
    String REST_DATABASE_PATH = "/component/databases";

    String ATTR_TIME 	= "time";
    String ATTR_TESTS 	= "tests";
    String ATTR_NAME 	= "name";
    String ATTR_FILE = "file";
    String ATTR_FAILURES = "failures";
    String ATTR_ERRORS = "errors";
    String ATTR_ASSERTIONS = "assertions";
    String ATTR_CLASS = "class";
    String ATTR_CLASSNAME = "classname";
    String ATTR_LINE = "line";
    String ATTR_TYPE = "type";
    String ATTR_RESULT = "result";
    String ATTR_JM_THREAD_NAME = "tn";
    String ATTR_JM_LABEL = "lb";
    String ATTR_JM_SUCCESS_FLAG = "s";
    String ATTR_JM_TIMESTAMP = "ts";
    String ATTR_JM_LATENCY_TIME = "lt";
    String ATTR_JM_TIME = "t";
    String ATTR_JM_BYTES = "by";
    String ATTR_JM_RESPONSE_CODE = "rc";
    String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
    String ATTR_ID = "id";
    
    String ELEMENT_FAILURE = "failure";
    String ELEMENT_ERROR = "error";

    String NAME_FILTER_PREFIX = "[@name='";
    String NAME_FILTER_SUFIX = "']";
    String XPATH_SINGLE_TESTSUITE = "/testsuites/testsuite";
    String XPATH_MULTIPLE_TESTSUITE = "/testsuites/testsuite/testsuite";
    String XPATH_TESTCASE = "/testcase";
    String XPATH_TEST_RESULT = "/testResults/*";
    String XPATH_TESTSUTE_TESTCASE = "/testsuite/testcase";
    
    /*
     *
     */
    String ADMIN_FIELD_PASSWORD = "admin_password";
    String PASSWORD = "password";

    /*
     * Message keys
     */
    String MSG_PROJECT_INFORMATION_EMPTY = "Project information should not be empty";
    String MSG_FILE_PATH_EMPTY = "File path should not be empty";
    
    /*
     * CI Message keys
     */
    String CI_BUILD_STARTED = "Build started in jenkins";
    String CI_BUILD_STARTING_ERROR = "Error while starting build in jenkins";
    
    String CI_SAVE_UPDATE_FAILED = "Save Or Updation failed.";
    String CI_BUILD_FAILED = "Build triggered failed.";
    String CI_ONE_JOB_REQUIRED = "Select only one job at a time for configuration";
    String CI_BUILD_LOADED_SHORTLY = "Builds will be loaded shortly";
    String CI_NO_JOBS_AVAILABLE = "No Job (s) Available";
    
    /*
     * Create Project keys
     */
    String SUCCESS_PROJECT = "Project {0} created successfully";
    String UPDATE_PROJECT = "Project {0} updated successfully";
    String FAILURE_PROJECT = "Project {0} creation failed";
    String IMPORT_SUCCESS_PROJECT = "Project imported successfully";
    String BUILD_WARNING_MESSAGE = "Build might take few minutes to generate.Do not do any action or refresh";     
    /*
     * Delete Project keys
     */
    String SUCCESS_PROJECT_DELETE = "Projects deleted successfully";
    String FAILURE_PROJECT_DELETE = "Projects deletion failed";
    /*
     * Project Import keys
     */
    String IMPORT_PROJECT_FAIL ="import.project.fail";
    String INVALID_CREDENTIALS = "import.invalid.credential";
    String INVALID_FOLDER = "import.invalid.folder";
    String PROJECT_ALREADY = "import.project.already";
    String SVN_FAILED ="failed";
    String SVN_INTERNAL ="Internal";
    String INVALID_URL = "import.invalid.url";
    String INVALID_REVISION = "import.invalid.revision";
    /*
     * Delete Build keys
     */
    String SUCCESS_BUILD_DELETE = "build.delete.success";
    String FAILURE_BUILD_DELETE = "build.delete.fail";
    String PROFILE_CREATE_SUCCESS = "profile.create.success";
    String PROFILE_UPDATE_SUCCESS = "profile.update.success";
    String PROFILE_CREATE_ERROR = "profile.create.error";
    String PROFILE_UPDATE_ERROR = "profile.update.error";
    String PROFILE_CREATE_MSG = "Create a profile";

    /*
     * Code keys
     */
    String FAILURE_CODE_REVIEW = "Code review is not available";
	String SONAR_NOT_STARTED = "sonar.not.started";
    
    /*
     * Application Redirection keys
     */
    String APPLICATION_PROJECT = "project";

    /*
     * Application Environment keys
     */
    String PATH_APPLICATION_ENVIRONMENT_DEPLOY = "deploy";
    /*
     * Create Configuration keys
     */
    String SUCCESS_CONFIGURATION = "Configuration {0} created successfully";
    String FAILURE_CONFIGURATION = "Configuration {0} creation failed";
    String CONFIG_ALREADY_EXIST = "Configuration type already created for this environment";
    String SETTINGS_ALREADY_EXIST = "Settings type already created for this environment";
    String NO_CONFIG_TYPE = "configuration.type.info";
    /*
     * Create Environment keys
     */
    String CREATE_SUCCESS_ENVIRONMENT = "environment.create.success";
    String CREATE_FAILURE_ENVIRONMENT = "environment.create.fail";
    
    /*
     * Update Environment keys
     */
    String UPDATE_ENVIRONMENT = "environment.update.success";
    
    /*
     * Delete Environment keys
     */
    String DELETE_ENVIRONMENT = "environment.deleted.success";
    /*
     * Create Setting keys
     */
    String SUCCESS_SERVER = "Server {0} created successfully";
    String SUCCESS_DATABASE = "Database {0} created successfully";
    String SUCCESS_WEBSERVICE = "Webservice {0} created successfully";
    String SUCCESS_EMAIL = "Email {0} created successfully";
    String FAILURE_SETTING = "Setting {0} creation failed";
   
    /*
     * Check Box Info keys
     */
    String HIDE_LOG_MSG ="Only Error Messages will be displayed";
    String EXEC_SQL_MSG ="Existing DB with the same name will be overwritten";
    
    /*
     * Quality Info keys
     */
    String MSG_IPHONE_DEPLOY ="Project will be deployed to device, which is connected first.";
    
    /*
     * login keys
     */
    String ERROR_LOGIN = "login.error.message";
    String ERROR_LOGIN_INVALID = "login.error.invalid";
    String ERROR_LOGIN_ACCESS_DENIED = "login.error.access.denied.message";
    String ERROR_EXCEPTION = "login.exception.message";
    String SUCCESS_LOGOUT = "logout.success.message";

    /*
     * login keys
     */
    String WELCOME_SHOW = "welcome.show";


    /*
     * Delete Setting keys
     */
    String SUCCESS_SETTING_DELETE = "Settings deleted successfully";
    String FAILURE_SETTING_DELETE = "Settings deletion failed";

    String SERVER_UPDATE_SUCCESS = "server.update.success";
    String DATABASE_UPDATE_SUCCESS = "database.update.success";
    String WEBSERVICE_UPDATE_SUCCESS = "webservice.update.success";
    String EMAIL_UPDATE_SUCCESS = "email.update.success";
    
    /*
     * Delete Configuration keys
     */
    String SUCCESS_CONFIG_DELETE = "config.delete.success";
    String FAILURE_CONFIG_DELETE = "config.delete.fail";

    String ERROR_ENV_DUPLICATE = "Environment {0} already exists";
    String ERROR_DUPLICATE_ENV_IN_SETTINGS = "Environment {0} already exists in global settings";
    String ERROR_NO_CONFIG = "environment.config.not.available";
    String ERROR_NAME = "Name is missing";
    String ERROR_CODE = "Code is missing";
    String ERROR_ENV = "Environment is missing";
    String ERROR_ENV_REMOVE = "Environment {0} is already in use";
    String ERROR_ENVS_REMOVE = "Environment(s) {0} are already in use";
    String ERROR_TECHNOLOGY = "Technology is missing";
    String ERROR_DUPLICATE_NAME = "Name already exists";
    String ERROR_DUPLICATE_NAME_IN_SETTINGS = "Name already exists in global settings";
    String ERROR_DUPLICATE_NAME_IN_CONFIGURATIONS = "Name already exists in configurations of the {0} project";
    String ERROR_INVALID_NAME = "Invalid Name";
    String ERROR_SELECT = "Select database and server";
    String ERROR_TEST_SUITE = "Test result is not available for this project";
    String ERROR_TEST_CASE = "Test case is not available for this project";
    String ERROR_MANDATORY_FIELDS = "Some mandatory values are not filled";
    String ERROR_SETTINGS = "ERROR_SETTINGS";
    String ERROR_UNIT_TEST = "Unit test not yet executed";
    String ERROR_FUNCTIONAL_TEST = "Functional test not yet executed";
    String ERROR_PERFORMANCE_TEST = "Performance test not yet executed";
    String ERROR_LOAD_TEST = "Load test not yet executed";
    String ERROR_ENV_CONFIG = "{0} configuration is not available for the selected environment";
    String ERROR_ANDROID_DATA = "Data is not available";
    String ERROR_PORT = "Invalid Port Number";
    String ERROR_EMAIL = "Enter Valid Email";
    
    /*
     * Delete Environment keys
     */
    String SUCCESS_ENVIRONMENT_DELETE = "environment.delete.success";
    String FAILURE_ENVIRONMENT_DELETE = "environment.delete.fail";
    
    /*
     * Browser keys
     */
    String BROWSER_FIREFOX_VALUE = "Firefox";
    String BROWSER_CHROME_VALUE = "Chrome";
    String BROWSER_INTERNET_EXPLORER_VALUE = "Internet Explorer";
    String BROWSER_SAFARI_VALUE = "Safari";
    String BROWSER_OPERA_VALUE = "Opera";
    
    /*
     * Browser keys for windows
     */
    String WIN_BROWSER_FIREFOX_KEY = "firefox";
    String WIN_BROWSER_CHROME_KEY = "googlechrome";
    String WIN_BROWSER_INTERNET_EXPLORER_KEY = "iexplore";
    String WIN_BROWSER_SAFARI_KEY = "safari";
    String WIN_BROWSER_OPERA_KEY = "opera";
    // web driver ie key
    String WIN_BROWSER_WEB_DRIVER_INTERNET_EXPLORER_KEY = "internet explorer";

    
    /*
     * Browser keys for Mac
     */
    String MAC_BROWSER_FIREFOX_KEY = "firefox";
    String MAC_BROWSER_CHROME_KEY = "googlechrome";
    String MAC_BROWSER_SAFARI_KEY = "safari";
    String MAC_BROWSER_OPERA_KEY = "opera";

    /*
     * Browser keys for Linux
     */
    String LINUX_BROWSER_FIREFOX_KEY = "firefox";
    String LINUX_BROWSER_CHROME_KEY = "googlechrome";
    String LINUX_BROWSER_SAFARI_KEY = "safari";
    String LINUX_BROWSER_OPERA_KEY = "opera";
    
    String PROJECT_VERSION = "1.0.0";
	String VERSION = "version";
	String ARTIFACT_ID = "artifactId";
	String FRAMEWORK_GROUP_ID = "com.photon.phresco.framework";
	String FRAMEWORK_ARTIFACT_ID = "phresco-framework-web";
	String DEPENDENCY = "dependency";
	String DEPENDENCIES = "dependencies";
	String POM_FILE = "pom.xml";
	String PROPERTIES = "properties";
	String PROPERTY_VERSION = "phresco.framework.version";
    
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
    String XPATH_JAVA_WEBSERVICE_TESTCASE = "/testcase";
    String XPATH_NODE_JS_WEBSERVICE_TESTCASE = "/testcase";
    String XPATH_PHP_WEBSERVICE_TESTCASE = "/testcase";
    
    /* CI Keys */
    String SUCCESS_JOB = "ci.create.success";
    String FAILURE_JOB = "ci.create.failure";
    String SUCCESS_BUILD = "ci.build.success";
    String FAILURE_BUILD = "ci.build.failure";
    String SUCCESS_UPDATE = "ci.update.success";
    String FAILURE_UPDATE = "ci.update.failure";
    
    /* About Keys */
    String ABOUT_SUCCESS_UPDATE = "abt.update.success";
    String ABOUT_FAILURE_FAILURE = "abt.update.failure";
    
    /* OS Types */
    String OS_NAME= "os.name";
    String OS_ARCH = "os.arch";
    String WINDOWS = "Windows";
    String SERVER = "Server";
    String DATABASE = "Database";
    String WINDOWS7 = "Windows 7";
    String MAC = "Mac";
    String LINUX = "Linux";
    String WINDOWS_CHECK = "win";
    String MAC_CHECK = "mac";
    String LINUX_CHECK = "nux";
    String OS_BIT64 = "64";
    String OS_BIT86 = "86";
    
   /* .phresco folder content */
   String ARCHETYPE_METADATA=".phresco/archetype-metadata.xml";
   String EXCLUDEFILE = ".phresco/excludefiles.txt";
   
   /* RBACK Roles */
   String ENGINEER = "Engineer";
   String RELEASE_ENGINEER = "ReleaseEngineer";
   
   String FILE_SEPARATOR = "/";
   
   /* Validation */
   String VALIDATE_FROM = "validateFrom";
   String VALIDATE_FRAMEWORK = "framework";
   String VALIDATE_PROJECT = "project";
   String VALIDATE_IN_BG = "validateInBg";
   String SESSION_FRMK_VLDT_RSLT  = "frmkVldtRslt";
   String SESSION_FRMK_VLDT_STATUS  = "frmkVldtStatus";
   String SESSION_PRJT_VLDT_RSLT  = "_PrjtVldtRslt";
   String SESSION_PRJT_VLDT_STATUS  = "_PrjtVldtStatus";
   
   /* Code validate */
   String CODE_VALIDATE_PARAM = "sonar.language";
   
   /* Drupal Version */
   String DRUPAL_VERSION = "drupal.version";
   
   /* Site report */
   String REQ_SITE_REPORT = "siteReport";
   String REQ_SITE_REPORT_PATH = "siteReportPath";
   String SITE_TARGET = "target/site";
   String REQ_SITE_REPORTS = "reports";
   String REQ_SITE_SLECTD_REPORTS = "selectedReports";
   String REQ_SITE_SLECTD_REPORTSCATEGORIES = "maven-project-info-reports-plugin";
   
   /* Android Key store file */
   String SIGNING_TYPES="keystore";
  
   /* Unit test report directory */
   String UNIT_TEST_QUNIT_REPORT_DIR = "/do_not_checkin/target/surefire-reports/qunit";
   String UNIT_TEST_JASMINE_REPORT_DIR = "/do_not_checkin/target/jasmine";
  }