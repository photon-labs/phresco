package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class UserInfoConstants {
	private ReadXMLFile readXml;

	public String USERNAME="userName";
	public String PASSWORD ="passWord";
	

    public UserInfoConstants() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadUserInfoConstants();
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
