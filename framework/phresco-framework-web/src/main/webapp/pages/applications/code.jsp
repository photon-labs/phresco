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
	if (StringUtils.isNotEmpty(sonarError)) {
		disabledStr = "disabled";
	}
%>

<div class="operation">
    <input id="validate" type="button" value="Validate" class="btn primary" <%= disabledStr %>>
</div>
<% if (StringUtils.isNotEmpty(sonarError)) { %>
	<div class="alert-message warning sonar">
		<s:label cssClass="sonarLabelWarn" key="sonar.not.started" />
	</div>
<% } %>
 
<div id="sonar_report" class="sonar_report">

</div>
<!-- To show popup for selecting skiptest in code validation whenever the validate button is clicked  -->

<form id="codeForm">
	<input type="hidden" name="skipTest"/>
	<div id="codeConfirm" class="modal confirm">
		<div class="modal-header">
			<h3><s:text name="label.skiptest.confirm"/></h3>
			<a id="close" href="#" class="close">&times;</a>
		</div>
		
		<div class="modal-body">
			<p id="confirmationText">Do you want to skip the test?</p>
		</div>
		
		<div class="modal-footer">
			<input id="no" type="button" value="<s:text name="label.no"/>" class="btn primary"/>
			<input type="button" value="<s:text name="label.yes"/>" class="btn primary" id="yes" />
		</div>
	</div>
</form>
<!--  Validate popup Start -->
<!-- <div class="popup_div" id="code_validate_pop_up"></div> -->
<!--  Validate popup Start -->

<script>
    $(document).ready(function() {
		/** To enable/disable the validate button based on the sonar startup **/
    	<% if (StringUtils.isNotEmpty(sonarError)) { %>
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
        	<% 	
        		if (TechnologyTypes.HTML5_WIDGET.equals(technology) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) || TechnologyTypes.HTML5.equals(technology) || TechnologyTypes.IPHONES.contains(technology)){
        	%>
        		getCodeValidatePopUp();
        	<% 
        		} else {
        	%>	
        			disableScreen();
        			$('#codeConfirm').show();    
        			escBlockPopup();
        	<% 
        		}
        	%>
        });
        
       	$('#closeGenTest, #closeGenerateTest').click(function() {
       		closePopup();
       		sonarReport();
        });
       	
       	$('#yes').click(function() {
       		$('#codeConfirm').hide();
       		$("input[name='skipTest']").val("true");
       		progress();
    		return false;
        });
       	
       	$('#no').click(function() {
       		$('#codeConfirm').hide();    
       		$("input[name='skipTest']").val("false");
       		progress();
    		return false;
        });
       	
    });
    
    function progress(codeTech) {
    	$('#codeConfirm').hide(); 
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