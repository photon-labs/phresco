package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;


public class PhrescoUiConstants {
	
	private ReadXMLFile readXml;

	public String PROTOCOL = "protocol";
	public String CONTEXT = "context";
    public String HOST = "host";
	public String PORT = "port";
	public String SELENIUM_HOST = "selenium.host";
	public String SERVER_PORT = "selenium.port";
	public String BROWSER = "Browser";
	public String SPEED = "speed";
	public String ELEMENT = "stringCapturehelloworldUrl";

	

 	private static PhrescoUiConstants instance;

	public static PhrescoUiConstants getInstance() {
		if (instance == null)
			instance = new PhrescoUiConstants();
		return instance;
	}

	public PhrescoUiConstants() {
	
			try {
			    readXml = new ReadXMLFile();
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
