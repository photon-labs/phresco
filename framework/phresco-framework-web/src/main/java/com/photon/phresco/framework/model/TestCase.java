/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
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
package com.photon.phresco.framework.model;

public class TestCase {

	private String name;
	private String testClass;
	private String file;
	private float line;
	private float assertions;
	private String time;
	private TestCaseFailure testCaseFailure;
	private TestCaseError testCaseError;

	public TestCase() {

	}

	public TestCase(String name, String testClass, String file, float line,
			float assertions, String time) {
		this.name = name;
		this.testClass = testClass;
		this.file = file;
		this.line = line;
		this.assertions = assertions;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public float getLine() {
		return line;
	}

	public void setLine(float line) {
		this.line = line;
	}

	public float getAssertions() {
		return assertions;
	}

	public void setAssertions(float assertions) {
		this.assertions = assertions;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public TestCaseFailure getTestCaseFailure() {
		return testCaseFailure;
	}

	public void setTestCaseFailure(TestCaseFailure testCaseFailure) {
		this.testCaseFailure = testCaseFailure;
	}

	public TestCaseError getTestCaseError() {
		return testCaseError;
	}

	public void setTestCaseError(TestCaseError testCaseError) {
		this.testCaseError = testCaseError;
	}

}
