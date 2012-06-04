package com.photon.phresco.nativeapp.unit.test.testcases;

import junit.framework.TestCase;


public class A_MainActivityTest extends TestCase {

	private static final String HELLO_WORLD = "Hello World!";

	protected void setUp() throws Exception {

	}

	protected void tearDown() throws Exception {

	}

	public void testAssertTrue() {
		assertTrue(HELLO_WORLD.equalsIgnoreCase("Hello World!"));
	}

	public void testAssertFalse() {
		assertFalse(HELLO_WORLD.equalsIgnoreCase("Hello-World!"));
	}
}
