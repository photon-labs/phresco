package com.photon.phresco.util;

/**
 * XCode constants
 * @author arunachalam
 *
 */
public interface XCodeConstants {

	/**
	 * Debug configuration build
	 */
	String CONFIGURATION_DEBUG = "Debug";

	/**
	 * Release configuration build
	 */
	String CONFIGURATION_RELEASE = "Release";
	
	/**
	 * SDK id for iphonesimulator 5.1
	 */
	String IPHONE_SIMULATOR_51 = "iphonesimulator5.1";

	/**
	 * SDK id for iphonesimulator 5.0
	 */
	String IPHONE_SIMULATOR_50 = "iphonesimulator5.0";
	
	/**
	 * SDK id for iphone simulator 4.3  
	 */
	String IPHONE_SIMULATOR_43 = "iphonesimulator4.3";
	
	/**
	 * SDK id for iphone simulator  4.0
	 */
	String IPHONE_SIMULATOR_40 = "iphonesimulator4.0";
	
	/**
	 * SDK id for iphone simulator  3.2
	 */
	String IPHONE_SIMULATOR_32 = "iphonesimulator3.2";
	
	/**
	 * SDK id for iphone os 5.1
	 */
	String IPHONE_OS_51 = "iphoneos5.1";
	
	/**
	 * SDK id for iphone os 5.0
	 */
	String IPHONE_OS_50 = "iphoneos5.0";
	
	/**
	 * 
	 */
	String CAN_CREATE_IPA = "canCreateIpa";
	
	/**
	 * 
	 */
	String DEVICE_DEPLOY = "deviceDeploy";
		
	/**
	 * SDK supported by Phresco
	 */
	String[] SUPPORTED_SDKS = new String[]{
			IPHONE_SIMULATOR_51,
			IPHONE_SIMULATOR_50,
			IPHONE_SIMULATOR_43, 
			IPHONE_SIMULATOR_40,
			IPHONE_OS_51,
			IPHONE_OS_50
			};

    /**
	 * 51 simulator version.
	 */
	String IPHONE_SIMULATOR_VERSION_51 = "5.1";
	
	/**
	 * 50 simulator version.
	 */
	String IPHONE_SIMULATOR_VERSION_50 = "5.0";
	
	/**
	 * 
	 */
	String IPHONE_SIMULATOR_VERSION_43 = "4.3";
	
	/**
	 * 40 simulator version
	 */
	String IPHONE_SIMULATOR_VERSION_40 = "4.0";

	/**
	 * Simulator versions supported by Phresco
	 */
	String[] SUPPORTED_SIMULATOR_VERSIONS = new String[]{IPHONE_SIMULATOR_VERSION_51, IPHONE_SIMULATOR_VERSION_50, IPHONE_SIMULATOR_VERSION_43, IPHONE_SIMULATOR_VERSION_40};
	
	String CONFIG_FILE = "phresco-env-config.xml";
	
	/**
	 * Default config file location for iphone native application
	 */
	String NATIVE_PLIST = "/" + CONFIG_FILE;
	
	/**
	 * Default config file location for iphone hybrid application
	 */
	String HYBRID_PLIST = "www/" + CONFIG_FILE;
	
	/**
	 * Default debug simulator build folder
	 */
	String DEBUG_SIMULATOR_FOLDER="Debug-iphonesimulator";
	
	/**
	 * Default release simulator build folder
	 */
	String RELEASE_SIMULATOR_FOLDER="Release-iphonesimulator";
	
	/**
	 * Default debug iphone device build folder
	 */
	String DEBUG_IPHONE_FOLDER="Debug-iphoneos";
	
	/**
	 * Default release iphone device build folder
	 */
	String RELEASE_IPHONE_FOLDER="Release-iphoneos";
	
}