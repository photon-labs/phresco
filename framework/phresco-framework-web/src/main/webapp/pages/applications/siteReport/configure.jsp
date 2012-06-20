<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>

<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="com.phresco.pom.site.Reports"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>

<%
	List<Reports> reports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_REPORTS);
	List<String> selectedReportNames = (List<String>) request.getAttribute(FrameworkConstants.REQ_SITE_SLECTD_RPT_NMS);
	String strSelectedReportNames = "";
%>

<div class="popup_Modal" id="configure-popup">
	<form action="siteConfigure">
		<div class="modal-header">
			<h3><s:text name="header.site.report.configure"/></h3>
			<a class="close" href="#" id="close">&times;</a>
		</div>

		<div class="modal-body" style="height: 210px;">
			<fieldset class="popup-fieldset" style="border: 1px solid #CCCCCC; height: 97%;">
				<legend class="fieldSetLegend"><s:text name="header.site.report.availableRpts"/></legend>
				<div style="overflow: auto; height: 100%; margin-top: -10px;">
	        		<ul id="availableReports" class="xlarge" style="text-align: left;">
						<%
							if (CollectionUtils.isNotEmpty(reports)) {
								for (Reports report : reports) {
									String checkedStr = "";
									if (CollectionUtils.isNotEmpty(selectedReportNames)) {
										if (selectedReportNames.contains(report.getDisplayName())) {
											checkedStr = "checked";
										}
									}
						%>
						<li class="environment_list">
							<input type="checkbox" name="reports" value="<%= report.getDisplayName() %>" <%= checkedStr %>>&nbsp;<%= report.getDisplayName() %>
						</li>
						<%
								}
							}
						%>
			        </ul>
		        </div>
	        </fieldset>			
		</div>

		<div class="modal-footer">
			<div class="action popup-action">
				<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
				<input type="button" id="actionBtn" class="btn primary" value="<s:text name="label.ok"/>">
			</div>
		</div>
		<%
			if (CollectionUtils.isNotEmpty(selectedReportNames)) {
				for (String selectedReportName : selectedReportNames) {
					strSelectedReportNames = strSelectedReportNames + selectedReportName + ",";
				}
				strSelectedReportNames = strSelectedReportNames.trim().substring(0, strSelectedReportNames.length() - 1);
			}
		%>
		<input type="hidden" name="alreadySelectedRptNames" value="<%= strSelectedReportNames %>">
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		$("#actionBtn").click(function() {
			configureSite();
		});
	});
	
	function configureSite() {
		showParentPage();
		popup('createReportConfig', '', $('#site_report'));
	}
</script>