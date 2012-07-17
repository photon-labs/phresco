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

<%@ include file="progress.jsp" %>
<%@ include file="errorReport.jsp" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.photon.phresco.model.*"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.framework.api.ValidationResult" %>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<script src="js/reader.js" ></script>
<script type="text/javascript" src="js/home-header.js" ></script>
<script type="text/javascript" src="js/phresco/common.js"></script>

<%
	String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String technology = (String)project.getProjectInfo().getTechnology().getId();
	String sonarError = (String)request.getAttribute(FrameworkConstants.REQ_ERROR);
	String disabledStr = "";
	if (!TechnologyTypes.IPHONES.contains(technology) && StringUtils.isNotEmpty(sonarError)) {
		disabledStr = "disabled";
	}
%>
<form action="check" id="check">
	<div class="operation">
	    <input id="validate" type="button" value="Validate" class="btn primary" <%= disabledStr %>>
		&nbsp;&nbsp;<strong id="lblType" class="noTestAvail"><s:text name="label.sonar.report"/></strong>&nbsp;
		<select id="report" name="report">
		<% if (TechnologyTypes.HTML5_WIDGET.equals(technology) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5.equals(technology)|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(technology)) { %>	
			<option value="java" ><s:text name="label.tech.java"/></option>
			<option value="js" ><s:text name="label.tech.javascript"/></option>
			<option value="web" ><s:text name="label.tech.jsp"/></option>
		<% } else { %>
			<option value="source" ><s:text name="label.validateAgainst.source"/></option>
		<% } %>
			<option value="functional" ><s:text name="label.funtional"/></option>
		</select>
	</div>
	<% if (!TechnologyTypes.IPHONES.contains(technology) && StringUtils.isNotEmpty(sonarError)) { %>
		<div class="alert-message warning sonar">
			<s:label cssClass="sonarLabelWarn" key="sonar.not.started" />
		</div>
	<% } %>
</form> 
<div id="sonar_report" class="sonar_report">

</div>

<script>
    $(document).ready(function() {
		/** To enable/disable the validate button based on the sonar startup **/
    	<% if (!TechnologyTypes.IPHONES.contains(technology) && StringUtils.isNotEmpty(sonarError)) { %>
    			$("#validate").removeClass("primary");	
    			$("#validate").addClass("disabled");
    	<% } else { %>
    			$("#validate").addClass("primary");	
				  $("#validate").removeClass("disabled");
    	<% } %>

    	changeStyle("code");
    	sonarReport();
    	enableScreen();
    	
		$('#validate').click(function() {
			getCodeValidatePopUp();
  		});
		
		$('#report').change(function() {
			sonarReport();
  		});
  
		$('#closeGenTest, #closeGenerateTest').click(function() {
 			closePopup();
 			sonarReport();
 			$('#popup_div').empty();
  		});
    });
    
    function progress() {
    	
    	getCurrentCSS();
    	$('#loadingDiv').show();
    	$('#build-output').empty();
    	$('#build-output').html("Validating code...");
		$('#build-outputOuter').show().css("display","block");
		$(".wel_come").show().css("display","block");
		readerHandlerSubmit('progressValidate', '<%= projectCode %>', '<%= FrameworkConstants.REQ_SONAR_PATH %>');
	}
    
    function sonarReport() {
        $("#sonar_report").empty();
        popup('check', '', $('#sonar_report'));
    }
    
    function getCodeValidatePopUp() {
    	$('#popup_div').empty();
		showPopup();
      	popup('getCodeValidatePopUp', '', $('#popup_div'));
    }
    
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
</script>
