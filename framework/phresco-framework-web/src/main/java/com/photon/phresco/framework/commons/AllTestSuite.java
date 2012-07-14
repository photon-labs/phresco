package com.photon.phresco.framework.commons;

public class AllTestSuite {
	private String testSuiteName;
	private float total;
	private float success;
	private float failure;
	private float error;
	
	public AllTestSuite(String testSuiteName, float total, float success, float failure, float error) {
		this.testSuiteName = testSuiteName;
		this.total = total;
		this.success = success;
		this.failure = failure;
		this.error = error;
	}
	
	public String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getSuccess() {
		return success;
	}

	public void setSuccess(float success) {
		this.success = success;
	}

	public float getFailure() {
		return failure;
	}

	public void setFailure(float failure) {
		this.failure = failure;
	}

	public float getError() {
		return error;
	}

	public void setError(float error) {
		this.error = error;
	}

}
