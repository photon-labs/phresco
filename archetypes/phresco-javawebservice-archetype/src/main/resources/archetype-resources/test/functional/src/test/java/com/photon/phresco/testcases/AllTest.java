/*
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.testcases;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTest.class.getName());
		// $JUnit-BEGIN$

		suite.addTestSuite(AWelcomePage.class);

		// $JUnit-END$
		return suite;
	}

}
