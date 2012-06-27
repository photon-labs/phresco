package com.phresco.pom.site;

public class ReportCategories {

	private String name;

	private boolean enabled=false;

	public ReportCategories(String name) {
		super();
		this.name = name;
	}

	public ReportCategories(String name, boolean enabled) {
		super();
		this.name = name;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public static final ReportCategories INFO_INDEX = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.index"));

	public static final ReportCategories INFO_MODULE = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.modules"));

	public static final ReportCategories INFO_DEPENDENCIES = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.dependencies"));
	
	public static final ReportCategories SCM = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.scm"));
	
	public static final ReportCategories CIM = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.cim"));
	
	public static final ReportCategories SUMMARY = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.summary"));
	
	public static final ReportCategories LICENSE = new ReportCategories(SiteMessages.getString("Reports.Report.project-info.report.license"));
}