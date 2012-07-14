package com.photon.phresco.framework.commons;

import java.util.List;

import com.photon.phresco.framework.model.TestSuite;

public class JmeterTypeReport {
	private String type;
	private List<JmeterReport> fileReport;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<JmeterReport> getFileReport() {
		return fileReport;
	}
	public void setFileReport(List<JmeterReport> fileReport) {
		this.fileReport = fileReport;
	}
}