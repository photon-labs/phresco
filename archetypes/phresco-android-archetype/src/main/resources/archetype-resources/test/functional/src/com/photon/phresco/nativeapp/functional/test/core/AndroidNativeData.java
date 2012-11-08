package com.photon.phresco.nativeapp.functional.test.core;

import java.io.InputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;

import com.photon.phresco.nativeapp.functional.R;


public class AndroidNativeData {
	private ReadXMLFile readXml;

	public String HELLO_WORLD_TEXT = "text1";
	
	
	public AndroidNativeData() {
		
	}
    public void parser(Context context)
    {
    	try {
    		
    	    Resources res = context.getResources(); 
    	    InputStream inputStream = res.openRawResource(R.raw.android_native_data);
    		
    	    readXml = new ReadXMLFile();
			readXml.loadUserInfoConstants(inputStream);
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
