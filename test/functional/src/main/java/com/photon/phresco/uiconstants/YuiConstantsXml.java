package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class YuiConstantsXml {
	
	private ReadXMLFile readXml;
	public String HTML5_WIDGET_PROJECT_CREATED_PROJ = "YuiWidgetCreatedProj";
	public String HTML5_WIDGET_PROJET_NAME = "appInfoYuiProjNameValue";
	public String HTML5_WIDGET_PROJECT_NONE_NAME="appInfoYuiNoneProjNameValue";
	public String HTML5_WIDGET_PROJECT_DESCRIPTION = "appInfoYuiProjDescValue";
	public String HTML5_WIDGET_CONFIG_SERVER_NAME = "YuiConfigServNameValue";
	//public String HTML5_WIDGET_CONFIG_SERVER_NAME_CLICK ="PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_SERVER_NAME_CLICK";
	public String HTML5_WIDGET_CONFIG_SERVER_DESCRIPTION = "YuiConfigservDescValue";
	public String HTML5_WIDGET_CONFIG_SERVER_HOST = "YuiconfigServHostValue";
	public String HTML5_WIDGET_CONFIG_SERVER_PORT = "YuiconfigServPortValue";
	public String HTML5_WIDGET_CONFIG_SERVER_DEPLOY_DIR = "YuiconfigServDeployDir";
	public String HTML5_WIDGET_CONFIG_SERVER_CONTEXT_NAME = "YuiconfigServContextNameValue";
	public String HTML5_WIDGET_CONFIG_WEBSERVICE_NAME = "YuiConfigWebServiceNameValue";
	//public String HTML5_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK = "PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK";
	public String HTML5_WIDGET_CONFIG_WEBSERVICE_DESCRIPTION = "YuiConfigWebserviceDesc";
	public String HTML5_WIDGET_CONFIG_WEBSERVICE_HOST = "YuiconfigWebserviceHostValue";
	public String HTML5_WIDGET_CONFIG_WEBSERVICE_PORT = "YuiconfigWebservicePortValue";
	public String HTML5_WIDGET_CONFIG_WEBSERVICE_CONTEXTNAME = "YuiconfigWebservContextNameValue";
	public String APPINFO_TECHNOLOGY_HTML5_WIDGET = "appInfoTechYuiWidget";
    //public String HTML5_WIDGET_CONFIG_TYPE="PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_TYPE";
    //public String HTML5_WIDGET_CONFIG_TYPE_CLICK="PHOTON_PHRESCO_APPLICATION_PAGE_HTML5_WIDGET_CONFIG_TYPE_CLICK";

    public YuiConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadYuiConstants();
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
