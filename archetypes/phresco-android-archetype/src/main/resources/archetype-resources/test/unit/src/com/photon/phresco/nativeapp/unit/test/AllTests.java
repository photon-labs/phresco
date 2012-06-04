package com.photon.phresco.nativeapp.unit.test;

import junit.framework.TestSuite;

import com.photon.phresco.nativeapp.unit.test.testcases.A_MainActivityTest;

public class AllTests extends TestSuite {
	public static TestSuite suite() {

		TestSuite suite = new TestSuite(AllTests.class.getName());

		suite.addTestSuite(A_MainActivityTest.class);

		return suite;

	}

	public ClassLoader getLoader() {
		return AllTests.class.getClassLoader();
	}
}
