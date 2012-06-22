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

<%@ include file="../progress.jsp" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<script src="js/reader.js" ></script>

<%
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
%>

<div class="operation">
    <input id="generate" type="button" value="<s:text name="label.site.report.generate"/>" class="primary btn">
    <input id="configure" type="button" value="<s:text name="label.site.report.configure"/>" class="primary btn">
</div>
 
<div id="site_report" style="height: 92%;">
	
</div>

<script>
	changeStyle("veiwSiteReport");
	
    $(document).ready(function() {
    	checkForSiteReport();
    	
    	$("#generate").click(function() {
    		generateReport();
    	});
    	
    	$("#configure").click(function() {
    		openConfigurePopup();
    	});
    	
    	$('#closeGenTest, #closeGenerateTest').click(function() {
       		closePopup();
       		checkForSiteReport();
        });
    });
    
    function generateReport() {
    	getCurrentCSS();
    	$('#loadingDiv').show();
    	$('#build-output').empty();
    	$('#build-output').html("Generating report...");
        $('#build-outputOuter').show().css("display","block");
        $(".wel_come").show().css("display","block");
    	readerHandlerSubmit("generateReport", '<%= projectCode %>', '<%= FrameworkConstants.REQ_SITE_REPORT %>');
    }
    
    function checkForSiteReport() {
    	$("#site_report").empty();
        popup('checkForSiteReport', '', $('#site_report'));
    }
    
    function openConfigurePopup() {
    	showPopup();
    	popup('siteConfigure', '', $('#popup_div'));
    }
</script>