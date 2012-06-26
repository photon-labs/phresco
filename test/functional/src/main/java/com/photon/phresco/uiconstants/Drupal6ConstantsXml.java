package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class Drupal6ConstantsXml {
	private ReadXMLFile readXml;
	
	public String APPINFO_DRUPAL6_NAME_VALUE = "appInfodrupal6NameValue";
	public String APPINFO_DRUPAL6_DESCRIPTION_VALUE = "appInfodrupal6DescValue";
	public String DRUPAL6 = "appInfoTechWebappdrupal6";
	public String DRUPAL6_CLICK = "appInfoTechWebappdrupal6Click";
	public String drupal6PROJ = "createddrupal6Project";

	public String CONFIGURATIONS_DRUPAL6_SERVER_NAME_VALUE = "drupal6ServerNameValue";
	public String CONFIGURATIONS_DRUPAL6_SERVER_DESCRIPTION_VALUE = "drupal6ServerDescValue";
	public String CONFIGURATIONS_DRUPAL6_SERVER_HOST_VALUE = "drupal6ServerHostValue";
	public String CONFIGURATIONS_DRUPAL6_SERVER_PORT_VALUE = "drupal6ServerPortValue";
	public String CONFIGURATIONS_DRUPAL6_SERVER_DEPLOYDIR_VALUE = "drupal6ServerDeployDirValue";
	public String CONFIGURATIONS_DRUPAL6_SERVER_TYPE_VALUE = "drupal6ServerTypeValue";
	public String CONFIGURATIONS_DRUPAL6_SERVER_CONTEXT_VALUE = "drupal6ServerContextValue";

	public String CONFIGURATIONS_DRUPAL6_DB_NAME_VALUE = "drupal6DbNameValue";
	public String CONFIGURATIONS_DRUPAL6_DB_DESCRIPTION_VALUE = "drupal6DbDescValue";
	public String CONFIGURATIONS_DRUPAL6_DB_HOST_VALUE = "drupal6DbHostValue";
	public String CONFIGURATIONS_DRUPAL6_DB_PORT_VALUE = "drupal6DbPortValue";
	public String CONFIGURATIONS_DRUPAL6_DB_USERNAME_VALUE = "drupal6DbUserNameValue";
//	public String CONFIGURATIONS_DRUPAL6_DB_PASSWORD_VALUE = "drupal6DbPasswordValue";
	public String CONFIGURATIONS_DRUPAbL6_DB_DBNAME_VALUE = "drupal6DbdbNameValue";
	public String CONFIGURATIONS_DRUPAL6_DB_CONTEXT_VALUE = "drupal6DbContextValue";

	public String DRUPAL6_GENERATEBUILD_DRUPAL6SERVER = "drupal6BuildGenBuilddrupal6Server";
	public String DRUPAL6_GENERATEBUILD_DRUPAL6SERVER_CLICK = "drupal6BuildGenBuilddrupal6ServerClick";
	public String DRUPAL6_GENERATEBUILD_DRUPAL6DATABASE = "drupal6BuildGenBuilddrupal6Db";
	public String DRUPAL6_GENERATEBUILD_DRUPAL6DATABASE_CLICK = "drupal6BuildGenBuilddrupal6DbClick";
	


	public Drupal6ConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadDrupal6Constants();
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
