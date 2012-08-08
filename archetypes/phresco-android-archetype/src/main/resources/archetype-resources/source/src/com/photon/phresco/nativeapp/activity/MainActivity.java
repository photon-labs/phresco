/*
 * ###
 * PHR_android-native-hw
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.nativeapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.config.EnvConstuctor;

public class MainActivity extends Activity {

	private static Button btn;
//	public static String webserviceURL;
//	private static final String WEBSERVICE_CONFIG_NAME = "res_service";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
//		buildEnvData();
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn=(Button)findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Toast.makeText(getApplicationContext(), getString(R.string.hello), Toast.LENGTH_LONG).show();
				
			}
		});
	}
	
//Below code is used for reading web service URL from Phresco configuration menu
//	private void buildEnvData() {
//		EnvConstuctor envConstuctor = new EnvConstuctor(getResources());
//		webserviceURL = envConstuctor.getWebServiceURL(WEBSERVICE_CONFIG_NAME);
//		
//	}
}