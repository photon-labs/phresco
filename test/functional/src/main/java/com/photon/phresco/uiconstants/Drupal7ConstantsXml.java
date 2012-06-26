package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class Drupal7ConstantsXml {
	private ReadXMLFile readXml;
	
	public String APPINFO_DRUPAL_NAME_VALUE = "appInfoDrupalNameValue";
	public String APPINFO_DRUPALNONE_NAME_VALUE="appInfoDrupalNoneNameValue";
	public String APPINFO_DRUPAL_DESCRIPTION_VALUE = "appInfoDrupalDescValue";
	public String DRUPAL7 = "appInfoTechWebappDrupal7";
	public String DRUPAL7_CLICK = "appInfoTechWebappDrupal7Click";
	public String DRUPALPROJ = "createdDrupalProject";

	public String CONFIGURATIONS_DRUPAL_SERVER_NAME_VALUE = "drupalServerNameValue";
	public String CONFIGURATIONS_DRUPAL_SERVER_DESCRIPTION_VALUE = "drupalServerDescValue";
	public String CONFIGURATIONS_DRUPAL_SERVER_HOST_VALUE = "drupalServerHostValue";
	public String CONFIGURATIONS_DRUPAL_SERVER_PORT_VALUE = "drupalServerPortValue";
	public String CONFIGURATIONS_DRUPAL_SERVER_DEPLOYDIR_VALUE = "drupalServerDeployDirValue";
	public String CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE = "drupalServerTypeValue";
	public String CONFIGURATIONS_DRUPAL_SERVER_CONTEXT_VALUE = "drupalServerContextValue";

	public String CONFIGURATIONS_DRUPAL_DB_NAME_VALUE = "drupalDbNameValue";
	public String CONFIGURATIONS_DRUPAL_DB_DESCRIPTION_VALUE = "drupalDbDescValue";
	public String CONFIGURATIONS_DRUPAL_DB_HOST_VALUE = "drupalDbHostValue";
	public String CONFIGURATIONS_DRUPAL_DB_PORT_VALUE = "drupalDbPortValue";
	public String CONFIGURATIONS_DRUPAL_DB_USERNAME_VALUE = "drupalDbUserNameValue";
//	public String CONFIGURATIONS_DRUPAL_DB_PASSWORD_VALUE = "drupalDbPasswordValue";
	public String CONFIGURATIONS_DRUPAL_DB_DBNAME_VALUE = "drupalDbdbNameValue";
	public String CONFIGURATIONS_DRUPAL_DB_CONTEXT_VALUE = "drupalDbContextValue";

	public String DRUPAL_GENERATEBUILD_DRUPALSERVER = "drupalBuildGenBuildDrupalServer";
	public String DRUPAL_GENERATEBUILD_DRUPALSERVER_CLICK = "drupalBuildGenBuildDrupalServerClick";
	public String DRUPAL_GENERATEBUILD_DRUPALDATABASE = "drupalBuildGenBuildDrupalDb";
	public String DRUPAL_GENERATEBUILD_DRUPALDATABASE_CLICK = "drupalBuildGenBuildDrupalDbClick";
	


	public Drupal7ConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadDrupal7Constants();
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
