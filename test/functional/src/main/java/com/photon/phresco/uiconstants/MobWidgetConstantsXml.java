package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class MobWidgetConstantsXml {
	private ReadXMLFile readXml;
	
	public   String APPINFO_HTML5MobileWidget_NAME_VALUE="appInfoMobileWidgetNameValue";
	public   String APPINFO_MOBILEWIDGET_NONE_NAME_VALUE="appInfoMobileWidgetNoneNameValue";
	public   String APPINFO_HTML5MobileWidget_DESCRIPTION_VALUE="appInfoMobileWidgetDescValue";
	public   String HTML5MobileWidget =	"appInfoTechMobappMobileWidget";		
	public   String WEBAPP_HTML5MobileWidget_CLICK="appInfoTechMobappMobileWidgetClick";
	public   String CREATEDPROJECT_HTML5MOBILEWIDGET = "createdMobileWidgetProject";
    public   String MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE = "MobileWidgetServerNameValue";
    public   String MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE = "MobileWidgetServerDescValue";
	public   String MOBILEWIDGET_GENERATEBUILD_MOBILEWIDGET_SERVER = "MobileWidgetBuildGenBuildMobileWidgetServer";
	//public   String MOBILEWIDGET_WEBSERV_CONFIGURATION_NAME_VALUE = "MobileWidgetWebServNameValue";
	//public   String MOBILEWIDGET_WEBSERVICE_CONFIGURATION_DESCRIPTION_VALUE = "MobileWidgetWebServcDescValue";
	public   String MOBILEWIDGET_GENERATEBUILD_MOBILEWIDGET_WEBSERVICE= "MobWidgBuildGenBuildMobWidgWebServ";
	
	public MobWidgetConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadMobileWidgetConstants();
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
