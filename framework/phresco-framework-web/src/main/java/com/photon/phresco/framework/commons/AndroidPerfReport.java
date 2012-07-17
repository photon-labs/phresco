package com.photon.phresco.framework.commons;

import java.util.List;

import com.photon.phresco.framework.model.TestSuite;

public class AndroidPerfReport {
	private String fileName;
	private List<JmeterReport> deviceReport;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<JmeterReport> getDeviceReport() {
		return deviceReport;
	}
	public void setDeviceReport(List<JmeterReport> deviceReport) {
		this.deviceReport = deviceReport;
	}

}