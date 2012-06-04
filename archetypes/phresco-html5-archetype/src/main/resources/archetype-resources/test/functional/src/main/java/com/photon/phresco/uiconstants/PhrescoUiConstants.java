package com.photon.phresco.uiconstants;


import java.lang.reflect.Field;

public class PhrescoUiConstants {
	private ReadXMLFile readXml;

	public String PROTOCOL = "protocol";
	public String SERVER_PORT = "server.port";
	public String CONTEXT = "context";
	public String HOST = "host";
	public String BROWSER = "Browser";
	public String SPEED = "speed";
	
	public String SERVER_HOST ="server.host";
	public String PORT= "port";

	// ***************LOGINPAGE*****************
    public String TEXTCAPTURED="loginText";
	// ***************LOGINPAGE*****************

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