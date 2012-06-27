package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class iPhoneConstantsXml {
	private ReadXMLFile readXml; 
	
	public 	 String APPINFO_iPHONENATIVE_NAME_VALUE="appInfoiPhoneNativeNameValue";
	public   String APPINFO_iPHONENATIVE_NONEPROJ_NAME_VALUE="appInfoiPhoneNativeNoneNameValue";
	public   String APPINFO_iPHONENATIVE_DESCRIPTION_VALUE="appInfoiPhoneNativeDescValue"; 
	public   String MOBILEAPP_iPHONENATIVE_CLICK="appInfoTechMobappiPhoneNativeClick";
	public   String CREATEDPROJECT_iPHONENATIVE = "creatediPhoneNativeProject";
	public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_NAME_VALUE = "iPhoneNativeWebServNameValue";
	public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_DESCRIPTION_VALUE = "iPhoneNativeWebServDescValue";
    public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_TYPE="iPhoneNativeWebServTypeValue";
    public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_TYPE_CLICK= "iPhoneNativeWebServTypeClick";
    public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_HOST_NAME_VALUE="iPhoneNativeWebServHostValue";
    public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_PORT_VALUE="iPhoneNativeWebServPortValue";
    public   String iPHONENATIVE_WEBSERVICE_CONFIGURATION_CONTEXT_VALUE="iPhoneNativeWebServContextValue";
    public   String iPHONENATIVE_GENERATE_BUILD_WEBSERVICE ="iPhoneNativeGenBuildWebServ";
    public   String iPHONENATIVE_GENERATE_BUILD_WEBSERVICE_CLICK="iPhoneNativeGenBuildWebServClick";
    public   String GENERATE_BUILD_SDK="iPhoneNativeGenBuildSDK";  
    public   String iPHONENATIVE_GENERATE_BUILD_SIMULATOR ="iPhoneNativeGenBuildSDKSimul";
    public   String iPHONENATIVE_GENERATE_BUILD_SIMULATOR_CLICK="iPhoneNativeGenBuildSDKSimulClick";
    public   String iPHONENATIVE_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK="iPhoneNativeDeploySimulator";
    public   String APPINFO_iPHONEHYBRID_NAME_VALUE="appInfoiPhoneHybridNameValue";
    public   String APPINFO_iPHONEHYBRID_NONE_NAME_VALUE="appInfoiPhoneHybridNoneNameValue";
	public   String APPINFO_iPHONEHYBRID_DESCRIPTION_VALUE="appInfoiPhoneNativeDescValue"; 
	public   String iPHONEHYBRID="appInfoTechMobappiPhoneHybrid";
	public   String MOBILEAPP_iPHONEHYBRID_CLICK="appInfoTechMobappiPhoneHybridClick";
	public   String MOBILEAPP_SHOPPINGCART="appInfoTechMobappiPhoneHybridShoppinCart";
	public   String CREATEDPROJECT_iPHONEHYBRID = "creatediPhoneHybridProject";

	public iPhoneConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadiPhoneConstants();
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
