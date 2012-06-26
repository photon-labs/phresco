package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class JavaWebServConstantsXml {

	private ReadXMLFile readXml;
	public String JAVAWEBSERVICE = "appInfoTechWebServJavaWebServ";
	public String WEBSERVICES_JAVAWEBSERVICE_CLICK = "appInfoTechWebServJavaWebServClick";
	public String CREATEDPROJECT_JAVAWEBSERVICE = "createdJavaWebServProject";
	public String JWSESHOPPROJ ="appInfoJavaWebServEshop";
	public String MYJAVAWERSERVICESERVER = "JWSBuildGenBuildJavaWebServServer";
	public String MYJAVAWERSERVICEDB = "JWSBuildGenBuildJavaWebServDb";
	public String MYJAVAWERSERVICESERVER_CLICK = "JWSBuildGenBuildJavaWebServServerClick";
	public String MYJAVAWERSERVICEDB_CLICK = "JWSBuildGenBuildJavaWebServDbClick";
	public String APPINFO_JAVAWEBSERVICE_NAME_VALUE = "appInfoJavaWebServNameValue";
	public String APPINFO_JAVAWEBSERVICE_NONE_NAME_VALUE = "appInfoJavaWebServNoneNameValue";
	public String APPINFO_JAVAWEBSERVICE_DESCRIPTION_VALUE = "appInfoJavaWebServDescValue";
	public String JAVAWEBSERVICE_SERVER_CONFIGURATION_NAME_VALUE = "JavaWebServServerNameValue";
	public String JAVAWEBSERVICE_SERVER_CONFIGURATION_DESCRIPTION_VALUE = "JavaWebServServerDescValue";
	public String JAVAWEBSERVICE_SETTINGS_SERVER_CONTEXT_VALUE = "JavaWebServServerContextValue";
	public String JAVAWEBSERVICE_SETTINGS_HOST_VALUE = "JavaWebServServerHostValue";
	public String JAVAWEBSERVICE_SETTINGS_PORT_VALUE = "JavaWebServServerPortValue";
	public String JAVAWEBSERVICE_SETTINGS_SERVER_DEPLOYDIR_VALUE = "JavaWebServServerDeployDirValue";
	public String JAVAWEBSERVICE_SETTINGS_SERVER_TYPE_VALUE = "JavaWebServServerTypeValue";
	public String JAVAWEBSERVICE_DB_CONFIGURATION_NAME_VALUE = "JavaWebServDbNameValue";
	public String JAVAWEBSERVICE_DB_CONFIGURATION_DESCRIPTION_VALUE = "JavaWebServDbDescValue";
	public String JAVAWEBSERVICE_SETTINGS_DB_CONTEXT_VALUE = "JavaWebServDbContextValue";
	public String JAVAWEBSERVICE_SETTINGS_DB_HOST_VALUE = "JavaWebServDbHostValue";
	public String JAVAWEBSERVICE_SETTINGS_DB_PORT_VALUE = "JavaWebServDbPortValue";
	//public String JAVAWEBSERVICE_SETTINGS_TYPE_DB = "PHOTON_PHRESCO_APPLICATION_PAGE_EDITAPPLICATION_JAVAWEBSERVICE_SETTINGS_TYPE_DB";
	public String JAVAWEBSERVICE_SETTINGS_TYPE_DB_USERNAME = "JavaWebServDbUserNameValue";
	//public String JAVAWEBSERVICE_SETTINGS_TYPE_DB_PASSWORD = "PHOTON_PHRESCO_APPLICATION_PAGE_EDITAPPLICATION_JAVAWEBSERVICE_SETTINGS_TYPE_DB_PASSWORD";
	public String JAVAWEBSERVICE_BUILD_RUNAGAINSTSRC_BTN="JavaWebServBuildRunAgainstSrcButton";
	public String JAVAWEBSERVICE_BUILD_RUN_BTN="JavaWebServBuildRunButton";
	public String JAVAWEBSERVICE_BUILD_STOP_BTN="JavaWebServBuildStopButton";

	
	public JavaWebServConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadJavaWebServConstants();
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
