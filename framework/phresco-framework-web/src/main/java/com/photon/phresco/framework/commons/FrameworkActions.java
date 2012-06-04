/*
 * ###
 * Framework Web Archive
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
package com.photon.phresco.framework.commons;

public interface FrameworkActions {
    /*****************************
     * Home Action path
     * String HOME_XXX
     *****************************/
    String HOME_WELCOME = "WELCOME";
	String HOME_VIEW = "home";
	String HOME_VIDEO = "video";
	
    /*****************************
     * Login Action path
     * String LOGIN_XXX
     *****************************/
    String LOGIN_FAILURE = "failure";
    String LOGIN_SUCCESS = "success";
    
    /*****************************
     * Application Action path
     * String APP_XXX
     *****************************/
	String APP_LIST = "list";
	String APP_ADD_APPLICATION = "add";
	String APP_TYPE = "applicationType";
	String APP_TECHNOLOGY = "technology";
	String APP_PROJECTMODULES = "projectModules";
	String APP_EDIT = "edit";
	String APP_FEATURES = "features";
	String APP_FEATURES_ONE_CLM = "features_oneClm";
	String APP_FEATURES_TWO_CLM = "features_twoClm";
	String APP_FEATURES_THREE_CLM = "features_threeClm";
	String APP_QUALITY = "quality";
	String APP_CODE = "code";
	String APP_CODE_CHECK = "check";
	String APP_BUILD = "build";
	String APP_BUILDS = "builds";
	String APP_GENERATE_BUILD = "generateBuild";
	String APP_ENVIRONMENT = "environment";
	String APP_ENVIRONMENT_READER = "reader";
    String APP_CONFIG_ADD = "addConfiguration";
    String APP_CONFIG_EDIT = "editConfiguration";
    String APP_UNIT_TEST = "unit";
    String APP_FUNCTIONAL_TEST = "functional";
    String APP_PERFORMANCE_TEST = "performance";
	String APP_LOAD_TEST = "load";
	String APP_GENERATE_TEST = "generateTest";
	String APP_GENERATE_UNIT_TEST = "generateUnitTest";
    String APP_TEST_REPORT = "testReport";
	String APP_QUALITY_TESTSUITE = "testSuite";
	String APP_DEPLOY_ANDROID = "deployAndroid";
	String APP_DEPLOY_IPHONE = "deployIphone";
	String APP_TEST_ANDROID = "testAndroid";
	String APP_IMPORT_FROM_SVN = "importFromSvn";
	String APP_CI = "ci";
	String APP_CI_CONFIGURE = "configure";
    String APP_TEST_RESULT = "testResult";
    String APP_GENERATE_JMETER = "generateJmeter";
    String APP_GENERATE_JMETER_PERFORMANCE = "generateJmeterPerformance";
    String APP_GENERATE_JMETER_LOAD = "generateJmeterLoad";
    String APP_DOWNLOAD = "download";
    String APP_APPLICATION_DETAILS = "applicationDetails";
    String APP_APPINFO = "appinfo";
    String APP_APPLICATION = "application";
    String APP_VALIDATE_FRAMEWORK = "validateFramework";
    String APP_VALIDATE_PROJECT = "validateProject";
    String APP_SHOW_FRAMEWORK_VLDT_RSLT = "showFrameworkVldtRslt";
    String APP_SHOW_PROJECT_VLDT_RSLT = "showProjectVldtRslt";
    String APP_SHOW_CODE_VALIDATE_POPUP="showCodeValidatePopUp";

    /*****************************
     * Settings Action path
     * String SETTINGS_XXX
     *****************************/
    
    String SETTINGS_LIST = "list";
    String SETTINGS_ADD = "add";
    String SETTINGS_EDIT = "edit";
    String SETTINGS_TYPE = "type";
    String SETTINGS_EDIT_TYPE = "editType";
    
    /*****************************
     * About Action path
     * String ABOUT_XXX
     *****************************/
    String ABOUT = "about";
    String HELP = "forumindex";
    
}
