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

import java.util.Arrays;
import java.util.List;

/**
 * Technology constants.
 *
 * @author arunachalam_l
 */
public interface TechnologyTypes {

    /**
     * Technology PHP
     */
    String PHP = "tech-php";
    
    /**
     * Technology PHP with Drupal6
     */
    String PHP_DRUPAL6 = "tech-phpdru6";
    
    /**
     * Technology PHP with Drupal7
     */
    String PHP_DRUPAL7 = "tech-phpdru7";

    /**
     * Technology Android native
     */
    String ANDROID_NATIVE = "tech-android-native";

    /**
     * Technology Android Hybrid
     */
    String ANDROID_HYBRID = "tech-android-hybrid";

    /**
     * Technology Android Web
     */
    String ANDROID_WEB = "tech-android-web";

	/**
     * Technology Android_Library
     */
    String ANDROID_LIBRARY = "tech-android-library";
	
    /*
     * All Android Technologies
     */
    List<String> ANDROIDS = Arrays.asList(ANDROID_NATIVE, ANDROID_HYBRID, ANDROID_WEB, ANDROID_LIBRARY);

    /**
     * Technology iPhone Native
     */
    String IPHONE_NATIVE = "tech-iphone-native";

    /**
     * Technology iPhone Hybrid
     */
    String IPHONE_HYBRID = "tech-iphone-hybrid";

    /**
     * Technology iPhone Web
     */
    String IPHONE_WEB = "tech-iphone-web";

    /*
     * All iPhone Technologies
     */
    List<String> IPHONES = Arrays.asList(IPHONE_NATIVE, IPHONE_HYBRID, IPHONE_WEB);


    /**
     * Technology Share point
     */
    String SHAREPOINT = "tech-sharepoint";

    /**
     * Technology Blackberry
     */
    String BLACKBERRY = "tech-blackberry";

    /**
     * Technology Java
     */
    String JAVA = "tech-java";

    /**
     * Technology Node JS
     */
    String NODE_JS = "tech-nodejs";

    /**
     * Technology HTML 5
     */
    String HTML5 = "tech-html5";

    /**
     * Technology HTML 5
     */
    String HTML5_MOBILE_WIDGET = "tech-html5-mobile-widget";

    /**
     * Technology HTML mobile widget
     */
    String HTML5_WIDGET = "tech-html5-widget";
    
    /**
     * Technology HTML mobile widget
     */
    String HTML5_MULTICHANNEL_JQUERY_WIDGET = "tech-html5-jquery-widget";
    
	/**
     * Technology HTML Jquery mobile widget
     */
    String HTML5_JQUERY_MOBILE_WIDGET = "tech-html5-jquery-mobile-widget";
	
    /**
     * Technology Java Web Service
     */
    String JAVA_WEBSERVICE = "tech-java-webservice";

    /**
     * Technology Node JS Web Service
     */
    String NODE_JS_WEBSERVICE = "tech-nodejs-webservice";

    /**
     * Technology Node JS Web Service
     */
    String PHP_WEBSERVICE = "tech-php-webservice";
    
    /**
     * Technology .Net
     */
    String DOT_NET = "tech-dotnet";
    
    /**
     * Technology SiteCore
     */
    String SITE_CORE = "tech-sitecore";
    
    /**
     * Technology wordpress
     */
    String WORDPRESS = "tech-wordpress";
    
    /**
     * Technology Java Standalone
     */
    String JAVA_STANDALONE = "tech-java-standalone";
    
    /**
     * Technology WIN_METRO
     */
    String WIN_METRO = "tech-win-metro";
    
    /**
     * All Technologies
     */
    List<String> ALL = Arrays.asList(PHP, PHP_DRUPAL7,PHP_DRUPAL6, ANDROID_NATIVE,ANDROID_HYBRID, ANDROID_LIBRARY, SHAREPOINT,JAVA_WEBSERVICE,NODE_JS_WEBSERVICE,HTML5_MOBILE_WIDGET,HTML5_WIDGET, DOT_NET, WORDPRESS, JAVA_STANDALONE, WIN_METRO);

    String ALL_TECHS = "all";

    /**
     * All Mobile Technologies
     */
    List<String> MOBILES = Arrays.asList(ANDROID_NATIVE, ANDROID_HYBRID, ANDROID_WEB, ANDROID_LIBRARY, IPHONE_NATIVE, IPHONE_HYBRID, IPHONE_WEB, WIN_METRO);
}