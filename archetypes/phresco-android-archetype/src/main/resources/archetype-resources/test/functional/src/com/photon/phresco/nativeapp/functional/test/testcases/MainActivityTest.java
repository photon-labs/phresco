/*
 * ###
 * PHR_AndroidNative
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
/**
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.nativeapp.functional.test.testcases;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.activity.MainActivity;
import com.photon.phresco.nativeapp.functional.test.core.Data;


@SuppressWarnings("unchecked")
public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	/**
	 * This is suite testcase by this testcase will call other testcases . In
	 * static block we are loading the MainActivity class and from the
	 * constructor will pass the package and activity full class name then in
	 * setUp() created the Solo class object
	 * 
	 */
	public static final String PACKAGE_NAME = "com.photon.phresco.nativeapp";
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.photon.phresco.nativeapp.activity.MainActivity";
	private static Class<MainActivity> mainActivity;
	private Solo soloMain;

	private static final String TAG = "****MainTestCase****";
	private Instrumentation inst;
	private Data data;

	/**
	 * This block will be executed first and it will loads the SplashActivity .
	 */
	static {
		try {
			mainActivity = (Class<MainActivity>) Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		}

		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * In this constructor , we have to send the packagename and activity full
	 * class name.
	 * 
	 * @throws Exception
	 */
	public MainActivityTest() throws TestException {
		super(PACKAGE_NAME, mainActivity);
	}

	/**
	 * this method for create the Solo class object having two super class
	 * methods..
	 * 
	 */
	@Override
	public void setUp() {

		soloMain = new Solo(getInstrumentation(), getActivity());
		inst=getInstrumentation();
		data=new Data();

	}

	/**
	 * With this method we can check the Registration Verification scenario by
	 * using testRegisterScenario().
	 * 
	 */

	public void testMain() throws TestException {
		
		data.parser(inst.getContext());
		soloMain.clickOnButton(0);
		soloMain.sleep(2000);
		
		assertEquals("Hello World!",data.HELLO_WORLD_TEXT);
		soloMain.sleep(2000);

	}


	/**
	 * Once the testcases executed completely. This method will be called and
	 * will close the all activities this is overridden with super class
	 * tearDown()method
	 */
	@Override
	protected void tearDown() throws Exception {

		try {
			soloMain.finishOpenedActivities();
		} catch (Exception e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}
		getActivity().finish();
		super.tearDown();

	}

}
