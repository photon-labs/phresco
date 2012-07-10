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
<%@ page import="com.phresco.pom.site.ReportCategories"%>

<%
	List<Reports> reports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_REPORTS);
	List<Reports> selectedReports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_SLECTD_REPORTS);
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
				<div class="report_scroll" style="overflow: auto; height: 100%; margin-top: -10px;">
	        		<ul id="availableReports" class="xlarge" style="text-align: left;">
						<%
							if (CollectionUtils.isNotEmpty(reports)) {
								for (Reports report : reports) {
									String checkedStr = "";
									List<ReportCategories> selectedReportCategories = null;
									if (CollectionUtils.isNotEmpty(selectedReports)) {
										for (Reports selectedReport : selectedReports) {
											if (selectedReport.getGroupId().equals(report.getGroupId()) && selectedReport.getArtifactId().equals(report.getArtifactId())) {
												selectedReportCategories = selectedReport.getReportCategories();
												checkedStr = "checked";
											}
										}
									}
						%>
									<div class="theme_accordion_container">
									    <section class="accordion_panel_wid">
									        <div class="accordion_panel_inner">
									            <section class="lft_menus_container">
									                <span class="siteaccordion closereg reportcolor" ><span><input type="checkbox" name="reports" value="<%= report.getArtifactId() %>" <%= checkedStr %>>&nbsp;<%= report.getDisplayName() %></span></span>
									                <%
										                List<ReportCategories> reportCategories = report.getReportCategories();
														if (CollectionUtils.isNotEmpty(reportCategories)) {
									                %>
									                <div class="mfbox siteinnertooltiptxt">
									                    <div class="scrollpanel adv_setting_accordian_bottom">
									                        <section class="scrollpanel_inner">
																<fieldset class="popup-fieldset fieldset_center_align">
																	<div class="clearfix">
																		<div class="xlInput" id="reportcategy">
																			<ul class="inputs-list">
																					<%
																						String categoryChk = "";
																						for (ReportCategories reportCategory : reportCategories) {
																							if (CollectionUtils.isNotEmpty(selectedReports)) {
																								for (ReportCategories selectedReportCategory : selectedReportCategories) {
																									if(reportCategory.getName().equals(selectedReportCategory.getName())) {
																										categoryChk = "checked";
																										break;
																									} else {
																										categoryChk = "";
																									}
																								}
																							}
																					%>
																							<li class="environment_list">
																								<input type="checkbox" name="<%= report.getArtifactId() %>" value="<%= reportCategory.getName() %>" <%= categoryChk %>><%= reportCategory.getName() %>
																							</li>
																					<%	
																							
																					}		
																					%>
																			</ul>
																		</div>	
																	</div>
																</fieldset>
									                        </section>
									                    </div>
									                </div>
									                <%
														}
									                %>
									            </section>  
									        </div>
									    </section>
									</div>
									
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
	</form>
</div>

<script type="text/javascript">

	/* JQuery scroll bar */
	$(".report_scroll").scrollbars();
	
	$(document).ready(function() {
		
		/** Accordian starts **/
		var showContent = 0;
	    
	    $('.siteaccordion').bind('click',function(e){
	        var _tempIndex = $('.siteaccordion').index(this);
	            $('.siteaccordion').removeClass('openreg').addClass('closereg');
	            $('.mfbox').each(function(e){
	                if($(this).css('display')=='block'){
	                    $(this).find('.scrollpanel').slideUp('300');
	                    $(this).slideUp('300');
	                }
	            })
	        if($('.mfbox').eq(_tempIndex).css('display')=='none'){
	            $(this).removeClass('closereg').addClass('openreg');
	            $('.mfbox').eq(_tempIndex).slideDown(300,function(){
	                $('.mfbox').eq(_tempIndex).find('.scrollpanel').slideDown('300');
	            });
	        }
	    });
	    
	    $(".theme_accordion_container").css('border','none');
	    $(".mfbox").css('display','none');
	    
	    /** Accordian ends **/
		
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