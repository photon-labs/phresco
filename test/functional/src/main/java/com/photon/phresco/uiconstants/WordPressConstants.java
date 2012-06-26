package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class WordPressConstants {
	private ReadXMLFile readXml; 
	
	public 	 String APPINFO_WORDPRESS_NAME_VALUE="appInfoWordPressNameValue";
	public   String APPINFO_WORDPRESS_NONEPROJ_NAME_VALUE="appInfoWordPressNoneNameValue";
	public   String APPINFO_WORDPRESS_DESCRIPTION_VALUE="appInfoWordPressDescValue"; 
	public   String WEBAPP_WORDPRESS_CLICK="appInfoTechWebappWordPressClick";
	public   String CREATEDPROJECT_WORDPRESS = "createdWordPressProject";
	public   String WORDPRESS_WEBSERVICE_CONFIGURATION_NAME_VALUE = "WordPressWebServNameValue";
	public   String WORDPRESS_WEBSERVICE_CONFIGURATION_DESCRIPTION_VALUE = "WordPressWebServDescValue";
    public   String WORDPRESS_WEBSERVICE_CONFIGURATION_TYPE="WordPressWebServTypeValue";
    public   String WORDPRESS_WEBSERVICE_CONFIGURATION_TYPE_CLICK= "WordPressWebServTypeClick";
    public   String WORDPRESS_WEBSERVICE_CONFIGURATION_HOST_NAME_VALUE="WordPressWebServHostValue";
    public   String WORDPRESS_WEBSERVICE_CONFIGURATION_PORT_VALUE="WordPressWebServPortValue";
    public   String WORDPRESS_WEBSERVICE_CONFIGURATION_CONTEXT_VALUE="WordPressWebServContextValue";
    public   String WORDPRESS_GENERATE_BUILD_WEBSERVICE ="WordPressGenBuildWebServ";
    public   String WORDPRESS_GENERATE_BUILD_WEBSERVICE_CLICK="WordPressGenBuildWebServClick";
    public   String GENERATE_BUILD_SDK="WordPressGenBuildSDK";  
    public   String WORDPRESS_GENERATE_BUILD_SIMULATOR ="WordPressGenBuildSDKSimul";
    public   String WORDPRESS_GENERATE_BUILD_SIMULATOR_CLICK="WordPressGenBuildSDKSimulClick";
    public   String WORDPRESS_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK="WordPressDeploySimulator";

	public WordPressConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadWordPressConstants();
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
	}

}
