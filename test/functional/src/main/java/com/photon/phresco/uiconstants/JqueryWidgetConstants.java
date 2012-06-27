package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class JqueryWidgetConstants {
	private ReadXMLFile readXml;
	
	public String JQUERY_WIDGET_PROJECT_CREATED_PROJ = "jqueryWidgetCreatedProj";
	public String JQUERY_WIDGET_PROJET_NAME = "appInfojqueryProjNameValue";
	public String JQUERY_WIDGET_NONEPROJ_NAME="appInfojqueryNoneProjNameValue";
	public String JQUERY_WIDGET_PROJECT_DESCRIPTION = "appInfojqueryProjDescValue";
	public String JQUERY_WIDGET_CONFIG_SERVER_NAME_VALUE ="jqueryConfigServNameValue";
	public String JQUERY_WIDGET_CONFIG_SERVER_DESCRIPTION = "jqueryConfigservDescValue";
	public String JQUERY_WIDGET_CONFIG_SERVER_HOST = "jqueryconfigServHostValue";
	public String JQUERY_WIDGET_CONFIG_SERVER_PORT = "jqueryconfigServPortValue";
	public String JQUERY_WIDGET_CONFIG_SERVER_DEPLOY_DIR = "jqueryconfigServDeployDir";
	public String JQUERY_WIDGET_CONFIG_SERVER_CONTEXT_NAME = "jqueryconfigServContextNameValue";
	public String JQUERY_WIDGET_CONFIG_WEBSERVICE_NAME = "jqueryConfigWebServiceNameValue";
	//public String JQUERY_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK = "PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK";
	public String JQUERY_WIDGET_CONFIG_WEBSERVICE_DESCRIPTION = "jqueryConfigWebserviceDesc";
	public String JQUERY_WIDGET_CONFIG_WEBSERVICE_HOST = "jqueryconfigWebserviceHostValue";
	public String JQUERY_WIDGET_CONFIG_WEBSERVICE_PORT = "jqueryconfigWebservicePortValue";
	public String JQUERY_WIDGET_CONFIG_WEBSERVICE_CONTEXTNAME = "jqueryconfigWebservContextNameValue";
	public String APPINFO_TECHNOLOGY_JQUERY_WIDGET = "appInfoTechjqueryWidget";
    //public String JQUERY_WIDGET_CONFIG_TYPE="PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_TYPE";
    //public String JQUERY_WIDGET_CONFIG_TYPE_CLICK="PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_TYPE_CLICK";
	
	public JqueryWidgetConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadJqueryWidgetConstants();
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
