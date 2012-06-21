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
	public static final ReportCategories INFO_INDEX = new ReportCategories(SiteMessages.getString("Reports.Reports.project-info.report.index"));

	public static final ReportCategories INFO_MODULE = new ReportCategories(SiteMessages.getString("Reports.Reports.project-info.report.modules"));

	public static final ReportCategories INFO_DEPENDENCIES = new ReportCategories(SiteMessages.getString("Reports.Reports.project-info.report.dependencies"));
}