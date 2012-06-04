/**
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.nativeapp.performance.test.testcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.activity.MainActivity;

@SuppressWarnings("unchecked")
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

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
	private MainActivityVerificationTest mainTestCase;
	
	private final String TAG = "MainTestCase****";

	/**
	 * This block will be executed first and it will loads the SplashActivity .
	 */
	static {
		try {
			mainActivity = (Class<MainActivity>) Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
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
	public MainActivityTest() throws Exception {
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

	}

	/**
	 * This test method will execute the testLoginScenario() .It will verifies
	 * the Login screen verification.
	 * 
	 * @throws TestException
	 */
	@Smoke
	public void testMain() throws TestException {

		try {
			Log.i(TAG, "testMain---------Start");
			// creating object of the testclass LoginVerificationTestCase
			mainTestCase = new MainActivityVerificationTest(soloMain);
			// calling the test method
			mainTestCase.testMain();
			Log.i(TAG, "testMain---------End");

		} catch (TestException e) {
			e.printStackTrace();
		}

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
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();

	}

	
	
	
}
