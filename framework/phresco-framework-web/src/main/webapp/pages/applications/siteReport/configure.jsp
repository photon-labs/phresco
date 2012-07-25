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

<script type="text/javascript" src="js/delete.js" ></script>

<%
    String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	List<Reports> reports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_REPORTS);
	List<Reports> selectedReports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_SLECTD_REPORTS);
%>

<div class="popup_Modal" id="configure-popup">
	<form action="siteConfigure">
		<div class="modal-header">
			<h3><s:text name="header.site.report.configure"/></h3>
			<a class="close" href="#" id="close">&times;</a>
		</div>

		<div class="modal-body" style="height: 228px;">
			<fieldset class="popup-fieldset" style="border: 1px solid #CCCCCC; height: 97%;">
				<legend class="fieldSetLegend"><s:text name="header.site.report.availableRpts"/></legend>
				<div class="tblheader">
                    <table class="zebra-striped">
                        <thead>
                           <tr id=allReport>
                               <th class="report-header1">
                                   <input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto">&nbsp;<s:text name="label.reports"/>
                               </th>
                               <th class="report-header2"></th>
                           </tr>   
                       </thead>
                    </table>
                </div>
				<div class="report_scroll" id="reports-div">
	        		<ul id="availableReports" class="xlarge">
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
									<div class="theme_accordion_container" style="border: none;">
									    <section class="accordion_panel_wid" style="margin-top: -6px;">
									        <div class="accordion_panel_inner" style="padding:0; border:1px solid #cccccc;">
									            <section class="lft_menus_container" style="width:100%;">
									                <span class="siteaccordion <%= CollectionUtils.isEmpty(report.getReportCategories()) ? "closereg_empty" : "closereg" %> reportcolor" style="border: none;">
										                <span id="reportList">
										                  <input type="checkbox" class="check" name="reports" value="<%= report.getArtifactId() %>" <%= checkedStr %>>&nbsp;<%= report.getDisplayName() %>
										                </span>
									                </span>
									                <%
										                List<ReportCategories> reportCategories = report.getReportCategories();
														if (CollectionUtils.isNotEmpty(reportCategories)) {
									                %>
									                <div class="mfbox siteinnertooltiptxt" style="display: none;">
									                    <div class="scrollpanel adv_setting_accordian_bottom">
									                        <section class="scrollpanel_inner">
	                                                               <fieldset class="popup-fieldset fieldset_center_align" style="border: none;">
	                                                                <div class="clearfix">
	                                                                    <div class="xlInput" id="reportcategy">
	                                                                    
	                                                                           <%
	                                                                               String categoryChk = "";
	                                                                               for (ReportCategories reportCategory : reportCategories) {
	                                                                                   String indexCheck = "";
	                                                                                   if(reportCategory.getName().equals("index")){
	                                                                                       indexCheck = "checked";
	                                                                                   }
	                                                                                   if (CollectionUtils.isNotEmpty(selectedReportCategories)) {
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
	                                                                                
	                                                                                   <li class="environment_list" style="margin: 0;">
	                                                                                       <input type="checkbox" class="check" id="check" name="<%= report.getArtifactId() %>" value="<%= reportCategory.getName() %>" <%= indexCheck %> <%= categoryChk %>>&nbsp;<%= reportCategory.getName() %>
	                                                                                   </li>
	                                                                           <%  
	                                                                                   
	                                                                           }       
	                                                                           %>
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

    /** To make accordion as open when only one report is available **/
    
    <% if (CollectionUtils.isNotEmpty(reports) && reports.size() == 1) {%>
           $(".mfbox").css("display", "block");
           $('.siteaccordion').removeClass('closereg_empty').addClass('openreg');
    <% } %>
    
    /** To Make index as default checked **/
    
    var index = 'index';
    var project_info = 'maven-project-info-reports-plugin';
    
    $('input:checkbox[value="' + index + '"]').attr('disabled', true);
    
    /* jquery scroll bar */
	$(".report_scroll").scrollbars();
	
	$(document).ready(function() {
		
		/** Accordian starts **/
		var showContent = 0;
	    
	    $('.siteaccordion').bind('click',function(e){
	        var _tempIndex = $('.siteaccordion').index(this);
	            $('.siteaccordion').removeClass('openreg').addClass('closereg_empty');
	            $('.mfbox').each(function(e){
	                if($(this).css('display')=='block'){
	                    $(this).find('.scrollpanel').slideUp('300');
	                    $(this).slideUp('300');
	                }
	            })
	        if($('.mfbox').eq(_tempIndex).css('display')=='none'){
	            $(this).removeClass('closereg_empty').addClass('openreg');
	            $('.mfbox').eq(_tempIndex).slideDown(300,function(){
	                $('.mfbox').eq(_tempIndex).find('.scrollpanel').slideDown('300');
	            });
	        }
	    });
	    /** Accordian ends **/
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		$("#actionBtn").click(function() {
			$('input:checkbox[value="maven-project-info-reports-plugin"]').prop('checked', true);
		    $('input:checkbox[value="' + index + '"]').removeAttr('disabled', true);
			configureSite();
		});
	});
	
	function configureSite() {
		showParentPage();
		var params = $('form').serialize();
		performAction('createReportConfig', params, $("#tabDiv"));
	}
	
	/*To check all categories under the project_info report*/
	$('.check').click(function() {
		var isChecked = $(this).prop('checked');
		var name = $(this).val();
	    	$('input:checkbox[name="' + name + '"]').each(function() {
	    		if ($(this).is(':disabled')) {
					
				} else {
					$(this).prop('checked', isChecked);
				}
	    	});
    	});
	
	/*To check all report when we click on project_info report*/
	
	$('input:checkbox[value="' + project_info + '"]').click(function(){
		if ($('#reports-div').find("input[type='checkbox']").length == $('#reports-div').find("input[type='checkbox']:checked").length) {
			$('#checkAllAuto').attr('checked', true);
		} else {
			$('#checkAllAuto').attr('checked', false);
		}
	});
	
	var project_info = 'maven-project-info-reports-plugin';
	
	if ($('#reportcategy').find("input[type='checkbox']").length == $('#reportcategy').find("input[type='checkbox']:checked").length) {
		$('input:checkbox[value="' + project_info + '"]').attr('checked', true);
	} else {
		$('input:checkbox[value="' + project_info + '"]').attr('checked', false);
	}
	
	$('#reportcategy').click(function() {
		if ($('#reportcategy').find("input[type='checkbox']").length == $('#reportcategy').find("input[type='checkbox']:checked").length) {
			$('input:checkbox[value="' + project_info + '"]').attr('checked', true);
		} else {
			$('input:checkbox[value="' + project_info + '"]').attr('checked', false);
		}
	});
</script>