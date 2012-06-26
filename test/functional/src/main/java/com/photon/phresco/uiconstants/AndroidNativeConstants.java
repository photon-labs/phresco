package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class AndroidNativeConstants {
	private ReadXMLFile readXml;

	public 	 String APPINFO_ANDROIDNATIVE_NAME_VALUE="appInfoAndroidNativeNameValue";
	public 	 String APPINFO_ANDROIDNATIVE_NONE_NAME_VALUE="appInfoAndroidNativeNoneNameValue";
	public   String APPINFO_ANDROIDNATIVE_DESCRIPTION_VALUE="appInfoAndroidNativeDescValue"; 
	public   String MOBILEAPP_ANDROIDNATIVE_CLICK="appInfoTechMobappAndroidNativeClick";
	public   String CREATEDPROJECT_ANDROIDNATIVE = "createdAndroidNativeProject";
	/*public   String ANDROIDNATIVE="appInfoTechMobappAndroidNative";*/
	//public   String MOBILEAPP_ESHOP="appInfoTechMobappAndroidNativeShoppinCart";
	
	public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_NAME_VALUE = "AndroidNativeWebServNameValue";
	public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_DESCRIPTION_VALUE = "AndroidNativeWebServDescValue";
    public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_TYPE="AndroidNativeWebServTypeValue";
    public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_TYPE_CLICK= "AndroidNativeWebServTypeClick";
    public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_HOST_NAME_VALUE="AndroidNativeWebServHostValue";
    public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_PORT_VALUE="AndroidNativeWebServPortValue";
    public   String ANDROIDNATIVE_WEBSERVICE_CONFIGURATION_CONTEXT_VALUE="AndroidNativeWebServContextValue";
    public   String ANDROIDNATIVE_GENERATE_BUILD_WEBSERVICE ="AndroidNativeGenBuildWebServ";
    public   String ANDROIDNATIVE_GENERATE_BUILD_WEBSERVICE_CLICK="AndroidNativeGenBuildWebServClick";
    public   String GENERATE_BUILD_SDK="AndroidNativeGenBuildSDK";  
    public   String ANDROIDNATIVE_GENERATE_BUILD_SIMULATOR ="AndroidNativeGenBuildSDKSimul";
    public   String ANDROIDNATIVE_GENERATE_BUILD_SIMULATOR_CLICK="AndroidNativeGenBuildSDKSimulClick";
    public   String ANDROIDNATIVE_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK="AndroidNativeDeploySimulator";
	
	public AndroidNativeConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadAndroidNativeConstants();
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
