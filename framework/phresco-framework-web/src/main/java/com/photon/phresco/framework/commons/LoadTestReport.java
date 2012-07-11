package com.photon.phresco.framework.commons;

import java.util.List;

import com.photon.phresco.framework.model.TestResult;
import com.photon.phresco.framework.model.TestSuite;

public class LoadTestReport {
	private String fileName;
	private List<TestResult> testResults;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<TestResult> getTestResults() {
		return testResults;
	}
	public void setTestResults(List<TestResult> testResults) {
		this.testResults = testResults;
	}

}