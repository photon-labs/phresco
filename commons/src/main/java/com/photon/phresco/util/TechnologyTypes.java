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

    /*
     * All Android Technologies
     */
    List<String> ANDROIDS = Arrays.asList(ANDROID_NATIVE, ANDROID_HYBRID, ANDROID_WEB);

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
     * Technology wordpress
     */
    String WORDPRESS = "tech-wordpress";
    
    /**
     * Technology Java Standalone
     */
    String JAVA_STANDALONE = "tech-java-standalone";

    /**
     * All Technologies
     */
    List<String> ALL = Arrays.asList(PHP, PHP_DRUPAL7,PHP_DRUPAL6, ANDROID_NATIVE,ANDROID_HYBRID,SHAREPOINT,JAVA_WEBSERVICE,NODE_JS_WEBSERVICE,HTML5_MOBILE_WIDGET,HTML5_WIDGET, DOT_NET, WORDPRESS, JAVA_STANDALONE);

    String ALL_TECHS = "all";

}