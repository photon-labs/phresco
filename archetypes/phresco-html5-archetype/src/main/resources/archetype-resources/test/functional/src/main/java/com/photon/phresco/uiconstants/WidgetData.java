package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class WidgetData {

	private ReadXMLFile readXml;
	
	// ***************LOGINPAGE*****************
    public String TEXTCAPTURED="loginText";
	// ***************LOGINPAGE*****************
	
	public WidgetData() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadMobileWidgetData();
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
