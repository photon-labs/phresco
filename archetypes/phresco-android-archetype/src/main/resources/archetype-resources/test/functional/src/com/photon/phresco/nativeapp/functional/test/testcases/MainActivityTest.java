package com.photon.phresco.nativeapp.functional.test.testcases;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.activity.MainActivity;

@SuppressWarnings("unchecked")
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static Class<MainActivity> mainActivity;
	private Solo soloObj;
	private TextView mTextView;
	private String resourceString;
	private static final String TARGET_PACKAGE = "com.photon.phresco.nativeapp";
	private static final String LAUNCHER_ACTIVITY = "com.photon.phresco.nativeapp.activity.MainActivity";

	static {
		try {

			mainActivity = (Class<MainActivity>) Class.forName(LAUNCHER_ACTIVITY);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public MainActivityTest() throws Exception {
		super(TARGET_PACKAGE, mainActivity);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		soloObj = new Solo(getInstrumentation(), getActivity());
		mTextView = (TextView) soloObj.getView(R.id.textview);
		resourceString = soloObj.getString(R.string.hello);

	}

	@Override
	public void tearDown() throws Exception {
		try {
			soloObj.finalize();

		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}

	public void testMain() throws InterruptedException {
		assertTrue(soloObj != null);
		assertNotNull(mTextView);
		assertNotNull(resourceString);
		assertEquals(mTextView.getText().toString(), resourceString);

	}

}