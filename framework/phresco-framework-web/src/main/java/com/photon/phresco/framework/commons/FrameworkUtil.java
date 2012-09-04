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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class FrameworkUtil implements FrameworkConstants {

    private static FrameworkUtil frameworkUtil = null;
    private Map<String, String> unitTestMap = new HashMap<String, String>(8);
    private Map<String, String> unitReportMap = new HashMap<String, String>(8);
    private Map<String, String> funcationTestMap = new HashMap<String, String>(8);
    private Map<String, String> funcationAdaptMap = new HashMap<String, String>(8);
    private Map<String, String> funcationReportMap = new HashMap<String, String>(8);
    private Map<String, String> performanceTestMap = new HashMap<String, String>(8);
    private Map<String, String> performanceReportMap = new HashMap<String, String>(8);
    private Map<String, String> loadTestMap = new HashMap<String, String>(8);
    private Map<String, String> loadReportMap = new HashMap<String, String>(8);
    private Map<String, String> unitTestSuitePathMap = new HashMap<String, String>(8);
    private Map<String, String> functionalTestSuitePathMap = new HashMap<String, String>(8);
    private Map<String, String> testCasePathMap = new HashMap<String, String>(8);
    private Properties qualityReportsProp;
    private String fileName = "quality-report.properties";
   
    private FrameworkUtil() throws PhrescoException {
        InputStream stream = null;
        stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        qualityReportsProp = new Properties();
        try {
            qualityReportsProp.load(stream);
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
        initUnitTestMap();
        initUnitReportMap();
        initFunctionalTestMap();
        initFunctionalAdaptMap();
        initFunctionalReportMap();
        initPerformanceTestMap();
        initPerformanceReportMap();
        initLoadTestMap();
        initLoadReportMap();
        initUnitTestSuitePathMap();
        initFunctionalTestSuitePathMap();
        initTestCasePathMap();
    }
    
	public static FrameworkUtil getInstance() throws PhrescoException {
        if (frameworkUtil == null) {
            frameworkUtil = new FrameworkUtil();
        }
        return frameworkUtil;
    }
    
    private void initUnitTestMap() {
        unitTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.HTML5, PATH_HTML5_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.PHP, PATH_PHP_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_UNIT_TEST);
        unitTestMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_UNIT_TEST);
    }

    private void initUnitReportMap() {
        unitReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.HTML5, PATH_HTML5_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.PHP, PATH_PHP_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_UNIT_TEST_REPORT);
        unitReportMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_UNIT_TEST_REPORT);
    }

    private void initFunctionalTestMap() {
        funcationTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.HTML5, PATH_HTML5_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.PHP, PATH_PHP_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_HYBRID_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_FUNCTIONAL_TEST);
        funcationTestMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_FUNCTIONAL_TEST);
    }
    
    private void initFunctionalAdaptMap() {
        funcationAdaptMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.HTML5, PATH_HTML5_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.PHP, PATH_PHP_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_FUNCTIONAL_ADAPT);
        funcationAdaptMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_FUNCTIONAL_ADAPT);
    } 

    private void initFunctionalReportMap() {
        funcationReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.HTML5, PATH_HTML5_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.PHP, PATH_PHP_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_HYBRID_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_FUNCTIONAL_TEST_REPORT);
        funcationReportMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_FUNCTIONAL_TEST_REPORT);
    }
    
    private void initPerformanceTestMap() {
        performanceTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.HTML5, PATH_HTML5_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.PHP, PATH_PHP_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_PERFORMANCE_TEST);
        performanceTestMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_PERFORMANCE_TEST);
    }

    private void initPerformanceReportMap() {
        performanceReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.HTML5, PATH_HTML5_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.PHP, PATH_PHP_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_PERFORMANCE_TEST_REPORT);
        performanceReportMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_PERFORMANCE_TEST_REPORT);
    }
    
    private void initLoadTestMap() {
        loadTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.HTML5, PATH_HTML5_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.PHP, PATH_PHP_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_WEBSERVICE_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_LOAD_TEST);
        loadTestMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_LOAD_TEST);
    }

    private void initLoadReportMap() {
        loadReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.HTML5_WIDGET, PATH_HTML5_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, PATH_HTML5_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.HTML5, PATH_HTML5_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.SHAREPOINT, PATH_SHAREPOINT_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.PHP, PATH_PHP_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.PHP_DRUPAL6, PATH_DRUPAL_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.PHP_DRUPAL7, PATH_DRUPAL_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.PHP_WEBSERVICE, PATH_PHP_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, PATH_NODEJS_WEBSERVICE_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.NODE_JS, PATH_NODEJS_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.ANDROID_HYBRID, PATH_ANDROID_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.ANDROID_NATIVE, PATH_ANDROID_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.ANDROID_WEB, PATH_ANDROID_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.IPHONE_HYBRID, PATH_IPHONE_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.IPHONE_NATIVE, PATH_IPHONE_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.IPHONE_WEB, PATH_IPHONE_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.BLACKBERRY, PATH_BLACKBERRY_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.JAVA_WEBSERVICE, PATH_JAVA_WEBSERVICE_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.DOT_NET, PATH_SHAREPOINT_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.WORDPRESS, PATH_DRUPAL_LOAD_TEST_REPORT);
        loadReportMap.put(TechnologyTypes.JAVA_STANDALONE, PATH_JAVA_WEBSERVICE_LOAD_TEST_REPORT);
        
    }

    private void initUnitTestSuitePathMap() {
    	unitTestSuitePathMap.put(TechnologyTypes.JAVA, XPATH_JAVA_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.HTML5_WIDGET, XPATH_HTML5_WIDGET_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, XPATH_HTML5_WIDGET_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.HTML5, XPATH_HTML5_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_MULTICHANNEL_JQUERY_UNIT_TEST_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_JQUERY_MOBILE_WIDGET_UNIT_TEST_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.SHAREPOINT, XPATH_SHAREPOINT_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.PHP, XPATH_PHP_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.PHP_DRUPAL6, XPATH_PHP_DRUPAL6_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.PHP_DRUPAL7, XPATH_PHP_DRUPAL7_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.PHP_WEBSERVICE, XPATH_PHP_WEBSERVICE_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, XPATH_NODE_JS_WEBSERVICE_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.NODE_JS, XPATH_NODE_JS_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.ANDROID_HYBRID, XPATH_ANDROID_HYBRID_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.ANDROID_NATIVE, XPATH_ANDROID_NATIVE_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.ANDROID_WEB, XPATH_ANDROID_WEB_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.IPHONE_HYBRID, XPATH_IPHONE_HYBRID_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.IPHONE_NATIVE, XPATH_IPHONE_NATIVE_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.IPHONE_WEB, XPATH_IPHONE_WEB_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.BLACKBERRY, XPATH_BLACKBERRY_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.JAVA_WEBSERVICE, XPATH_JAVA_WEBSERVICE_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.DOT_NET, XPATH_SHAREPOINT_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.WORDPRESS, XPATH_PHP_WORDPRESS_UNIT_TESTSUITE);
    	unitTestSuitePathMap.put(TechnologyTypes.JAVA_STANDALONE, XPATH_JAVA_WEBSERVICE_UNIT_TESTSUITE);
	}
    
    private void initFunctionalTestSuitePathMap() {
    	functionalTestSuitePathMap.put(TechnologyTypes.JAVA, XPATH_JAVA_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.HTML5_WIDGET, XPATH_HTML5_WIDGET_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, XPATH_HTML5_WIDGET_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.HTML5, XPATH_HTML5_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, PATH_HTML5_MULTICHANNEL_JQUERY_FUNCTIONAL_TEST_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, PATH_HTML5_JQUERY_MOBILE_WIDGET_FUNCTIONAL_TEST_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.SHAREPOINT, XPATH_SHAREPOINT_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.PHP, XPATH_PHP_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.PHP_DRUPAL6, XPATH_PHP_DRUPAL6_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.PHP_DRUPAL7, XPATH_PHP_DRUPAL7_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.PHP_WEBSERVICE, XPATH_PHP_WEBSERVICE_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, XPATH_NODE_JS_WEBSERVICE_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.NODE_JS, XPATH_NODE_JS_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.ANDROID_HYBRID, XPATH_ANDROID_HYBRID_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.ANDROID_NATIVE, XPATH_ANDROID_NATIVE_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.ANDROID_WEB, XPATH_ANDROID_WEB_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.IPHONE_HYBRID, XPATH_IPHONE_HYBRID_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.IPHONE_NATIVE, XPATH_IPHONE_NATIVE_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.IPHONE_WEB, XPATH_IPHONE_WEB_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.BLACKBERRY, XPATH_BLACKBERRY_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.JAVA_WEBSERVICE, XPATH_JAVA_WEBSERVICE_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.DOT_NET, XPATH_SHAREPOINT_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.WORDPRESS, XPATH_PHP_WORDPRESS_FUNCTIONAL_TESTSUITE);
    	functionalTestSuitePathMap.put(TechnologyTypes.JAVA_STANDALONE, XPATH_JAVA_WEBSERVICE_FUNCTIONAL_TESTSUITE);
	}
    
	private void initTestCasePathMap() {
		testCasePathMap.put(TechnologyTypes.JAVA, XPATH_JAVA_TESTCASE);
		testCasePathMap.put(TechnologyTypes.HTML5_WIDGET, XPATH_HTML5_WIDGET_TESTCASE);
		testCasePathMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, XPATH_HTML5_WIDGET_TESTCASE);
        testCasePathMap.put(TechnologyTypes.HTML5, XPATH_HTML5_TESTCASE);
        testCasePathMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, XPATH_HTML5_MULTICHANNEL_JQUERY_TESTCASE);
        testCasePathMap.put(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET, XPATH_HTML5_JQUERY_MOBILE_WIDGET_TESTCASE);
		testCasePathMap.put(TechnologyTypes.SHAREPOINT, XPATH_SHAREPOINT_TESTCASE);
		testCasePathMap.put(TechnologyTypes.PHP, XPATH_PHP_TESTCASE);
		testCasePathMap.put(TechnologyTypes.PHP_DRUPAL6, XPATH_PHP_DRUPAL7_TESTCASE);
		testCasePathMap.put(TechnologyTypes.PHP_DRUPAL7, XPATH_PHP_DRUPAL7_TESTCASE);
		testCasePathMap.put(TechnologyTypes.PHP_WEBSERVICE, XPATH_PHP_WEBSERVICE_TESTCASE);
		testCasePathMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, XPATH_NODE_JS_WEBSERVICE_TESTCASE);
		testCasePathMap.put(TechnologyTypes.NODE_JS, XPATH_NODE_JS_TESTCASE);
		testCasePathMap.put(TechnologyTypes.ANDROID_HYBRID, XPATH_ANDROID_HYBRID_TESTCASE);
		testCasePathMap.put(TechnologyTypes.ANDROID_NATIVE, XPATH_ANDROID_NATIVE_TESTCASE);
		testCasePathMap.put(TechnologyTypes.ANDROID_WEB, XPATH_ANDROID_WEB_TESTCASE);
		testCasePathMap.put(TechnologyTypes.IPHONE_HYBRID, XPATH_IPHONE_HYBRID_TESTCASE);
		testCasePathMap.put(TechnologyTypes.IPHONE_NATIVE, XPATH_IPHONE_NATIVE_TESTCASE);
		testCasePathMap.put(TechnologyTypes.IPHONE_WEB, XPATH_IPHONE_WEB_TESTCASE);
		testCasePathMap.put(TechnologyTypes.BLACKBERRY, XPATH_BLACKBERRY_TESTCASE);
		testCasePathMap.put(TechnologyTypes.JAVA_WEBSERVICE, XPATH_JAVA_WEBSERVICE_TESTCASE);
		testCasePathMap.put(TechnologyTypes.DOT_NET, XPATH_SHAREPOINT_TESTCASE);
		testCasePathMap.put(TechnologyTypes.WORDPRESS, XPATH_PHP_DRUPAL7_TESTCASE);
		testCasePathMap.put(TechnologyTypes.JAVA_STANDALONE, XPATH_JAVA_WEBSERVICE_TESTCASE);
	}
	
    public String getUnitTestDir(String technologyId) {
        String key = unitTestMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getUnitReportDir(String technologyId) {
        String key = unitReportMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getFuncitonalTestDir(String technologyId) {
        String key = funcationTestMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getFuncitonalAdaptDir(String technologyId) {
        String key = funcationAdaptMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getFunctionalReportDir(String technologyId) {
        String key = funcationReportMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getPerformanceTestDir(String technologyId) {
        String key = performanceTestMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getPerformanceReportDir(String technologyId) {
        String key = performanceReportMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
   
    public String getLoadTestDir(String technologyId) {
        String key = loadTestMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getLoadReportDir(String technologyId) {
        String key = loadReportMap.get(technologyId);
        return qualityReportsProp.getProperty(key);
    }
    
    public String getUnitTestSuitePath(String technologyId){
    	String testSuitePath = unitTestSuitePathMap.get(technologyId);
    	return testSuitePath;
    }
    
    public String getFunctionalTestSuitePath(String technologyId){
    	String testSuitePath = functionalTestSuitePathMap.get(technologyId);
    	return testSuitePath;
    }
    
    public String getTestCasePath(String technologyId){
    	String testCasePath = testCasePathMap.get(technologyId);
    	return testCasePath;
    }
    
    public static void setAppInfoDependents(HttpServletRequest request) throws PhrescoException {
        FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
        ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
        request.setAttribute(SESSION_APPLICATION_TYPES, administrator.getApplicationTypes());
        request.setAttribute(REQ_CODE_PREFIX, configuration.getCodePrefix());
    }
    
    public static String getStackTraceAsString(Exception exception) {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    pw.print(" " + SQUARE_OPEN + " ");
	    pw.print(exception.getClass().getName());
	    pw.print(" " + SQUARE_CLOSE + " ");
	    pw.print(exception.getMessage());
	    pw.print(" ");
	    exception.printStackTrace(pw);
	    return sw.toString();
    }
    
    public static String removeFileExtension(String fileName) {
    	fileName = fileName.substring(0, fileName.lastIndexOf('.'));
    	return fileName;
    }
    
    public static float roundFloat(int decimal, double value) {
		BigDecimal roundThroughPut = new BigDecimal(value);
		return roundThroughPut.setScale(decimal, BigDecimal.ROUND_HALF_EVEN).floatValue();
	}
    
    public static String convertToCommaDelimited(String[] list) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }
    
    public static void copyFile(File srcFile, File dstFile) throws PhrescoException {
    	try {
    		if (!dstFile.exists()) {
    			dstFile.getParentFile().mkdirs();
    			dstFile.createNewFile();
    		}
			FileUtils.copyFile(srcFile, dstFile);
		} catch (Exception e) {
			throw new PhrescoException();
		}
    }
    
    public PomProcessor getPomProcessor(String projectCode) throws PhrescoException {
    	try {
    		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    		builder.append(projectCode);
    		builder.append(File.separatorChar);
    		builder.append(POM_XML);
    		File pomPath = new File(builder.toString());
    		return new PomProcessor(pomPath);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    }
}
