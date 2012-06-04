package com.photon.phresco.uiconstants;

import com.photon.phresco.uiconstants.TestProperties;
import java.lang.reflect.Field;

public class PhrescoUiConstants {

	public String PROTOCOL = "server.protocol";
	public String SERVER_PORT = "server.port";
	public String CONTEXT = "server.context";
	public String HOST = "server.host";
	public int PORT = 4444;
	public String BROWSER = "browser.name";
	public String SPEED = "PHOTON_PHRESCO_SPEED";

	// ***************LOGINPAGE*****************
    public String TEXTCAPTURED="PHOTON_PHRESCO_TEXTCAPTURED";
	// ***************LOGINPAGE*****************

	private static PhrescoUiConstants instance;

	public static PhrescoUiConstants getInstance() {
		if (instance == null)
			instance = new PhrescoUiConstants();
		return instance;
	}

	public PhrescoUiConstants() {
		try {
			TestProperties localTestProperties = new TestProperties();
			localTestProperties.loadPhrescoConfigProperties();
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField.set(this,
							localTestProperties.getValue((String) localObject));
			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
	}
}
