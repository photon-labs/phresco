package com.photon.phresco.framework.commons;

import java.util.List;

import com.photon.phresco.framework.model.TestSuite;

public class SureFireReport {
	//custom report
	private List<AllTestSuite> allTestSuites;
	//detail report
	private List<TestSuite> testSuites;
	public List<AllTestSuite> getAllTestSuites() {
		return allTestSuites;
	}
	public void setAllTestSuites(List<AllTestSuite> allTestSuites) {
		this.allTestSuites = allTestSuites;
	}
	public List<TestSuite> getTestSuites() {
		return testSuites;
	}
	public void setTestSuites(List<TestSuite> testSuites) {
		this.testSuites = testSuites;
	}
}
