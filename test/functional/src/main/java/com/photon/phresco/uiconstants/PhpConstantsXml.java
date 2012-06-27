package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class PhpConstantsXml {
	private ReadXMLFile readXml;
	
	/*public   String CONFIGURATION_PHP_SERVER_DEPLOYDIR="PHOTON_PHRESCO_HOME_PAGE_PHP_CONFIGURATION_SERVER_DEPLOYDIR";*/
	public   String APPINFO_PHP_NAME_VALUE="appInfoPhpNameValue";
	public   String APPINFO_PHP_NONEPROJ_NAME_VALUE="appInfoPhpNoneNameValue";
	public   String APPINFO_PHP_DESCRIPTION_VALUE="appInfoPhpDescriptionValue";
	public   String APPINFO_PHP_PHPBLOG="appInfophpPhpBlog";
	public   String PHP_CONFIGURATION_NAME_SERVER="phpConfigurationNameServer";
	/*public   String PHP_SERVERCON= "PHOTON_PHRESCO_APPLICATION_PAGE_PHP_BUILD_GENERATE_BUILD_SERVERCON";*/
	public   String PHP_SERVERCONFIG= "phpGenBuildServerconfig";
	public   String	PHP_DBCONFIG="phpGenBuildDbconfig";
	public   String PHP_CONFIGURATION_DESCRIPTION_SERVER="phpConfigServerValue";
	public   String PHP_CONFIGURATION_SERVER_DEPLOYDIR="phpConfigServerDeployDir";
	public   String PHP_CONFIGURATION_SERVER_CONTEXT="phpConfigServerContext";		
	public   String PHP_CONFIGURATION_DB_NAME="phpConfigDbName";
	public   String PHP_CONFIGURATION_DB_DESCRIPTION="phpConfigDbDescription";
	public   String PHP_SETTINGS_DB_USERNAME="phpConfigDbUsername";
	//public   String PHP_SETTINGS_DB_PASSWORD="phpConfigDbPassword";
	public   String PHP_SETTINGS_DB_NAME="phpSettingsDbName";		
	/*public   String PHP_SETTINGS_DATABASE_NAME="PHOTON_PHRESCO_HOME_PAGE_SETTINGS_DATABASE_NAME";*/  
	public   String PHP="appInfoTechWebappPhp";
	public   String PHP_CLICK="appInfoTechWebappPhpClick";
	public   String PHPPROJECT="phpCreatedproject";
	public   String SERVER_HOST_DESCRIPTIONS="phpServerConfigHost";
	public   String PHP_SERVER_PORT="phpServerConfigPort";
	public   String CONFIGURATION_PHP_PORT="phpConfigPort";
	public   String SERVERCONFIG = "phpGenBuildServerConfig";
	public   String SERVERCONFIG_CLICK="phpGenBuildServerConfigClick";
	public   String DBCONFIG = "phpGenBuildDbConfig";
	public   String DBCONFIG_CLICK="phpGenBuildDbConfigClick";

	public PhpConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadPhpConstants();
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
