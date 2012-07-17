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

<%@ page import="java.io.File"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<%@include file="../progress.jsp" %>

<style type="text/css">
	.testSuiteDisplay {
	    height: 98%;
	    position: relative;
	    top: -32px;
	}
	.testSuiteListAdj {
	    margin-bottom: 8px;
	    margin-top: 6px;
	    position: relative;
	     top: -30px;
	 }
</style>

<form action="load" method="post" autocomplete="off" class="marginBottomZero">
   <!--  <div class="frame-header frameHeaderPadding btnTestPadding"> -->
     <div class="operation">
            <input id="testbtn" type="button" value="<s:text name="label.test"/>" class="primary btn env_btn">
        <div class="icon_fun_div printAsPdf">
        	<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
    </div>
</form>

<!-- load test button ends -->
<% 
	Boolean popup = Boolean.FALSE;
	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
   	String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
    String technology =  project.getProjectInfo().getTechnology().getName();
	if (TechnologyTypes.ANDROIDS.contains(project.getProjectInfo().getTechnology().getId())) {
		popup = Boolean.TRUE;
	}
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
   	if(testError != null) {
%>
    <div class="alert-message block-message warning" style="margin: 5px 5px 0;">
		<center><label class="errorMsgLabel"><%= testError %></label></center>
	</div>
	
	<script type="text/javascript">
		enableScreen();
		$("#loadingIconDiv").empty();
	</script>
<% } else {
        File[] files = (File[])request.getAttribute(FrameworkConstants.REQ_JMETER_REPORT_FILES); 
%>
    
    <div class="functional_header testSuiteList testSuiteListAdj"><strong><s:text name="label.test.results"/></strong> 
        <select id="testResults" name="testResults" class="testList"> 
            <% 
            if(files != null) {
                for(File file : files) {
            %>
              <option value="<%= file.getName() %>" ><%= file.getName() %> </option>
            <% 
                }
            }
            %>
        </select>
    </div>
    
    <div id="testResultDisplay" class="testSuiteDisplay">
    </div>
    
	<script type="text/javascript">
		$(document).ready(function() {
	    	loadTestResults();
	    	
	    	$('#testResults').change(function() {
	    		loadTestResults();
	    	});
	    });
	
	    function loadTestResults() {
	        var testResult = $("#testResults").val();
	        $("#testResultDisplay").empty();
     	    var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	 		params = params.concat("testType=");
			params = params.concat('<%= testType%>');
			params = params.concat("&testResultFile=");
			params = params.concat(testResult);
			getCurrentCSS();
	        $('.popupLoadingIcon').show();
			performAction('loadTestResult', params, $('#testResultDisplay'));
			//show print as pdf icon
			$('#pdfPopup').show();
	    }
	</script>
<% } %>

<!-- <div class="popup_div" id="generateJmeter">
</div> -->
	

<script type="text/javascript">
    
	    $(document).ready(function() {
	    	
	    	//Disable test button for load
	    	if(<%= popup %>){
	    		disableControl($("#testbtn"), "btn disabled");	
	    	}
	    	
	        $('#testbtn').click(function() {
	        	$("#popup_div").empty(); // remove perfromance html data and to avoid name conflict with load test
			 	if(<%= popup %>){
					openAndroidPopup();
				} else {
					generateJmeter('<%= testType %>');
				}
	        });
	        $('#openFolder').click(function() {
	             openFolder('<%= projectCode %><%= path %>');
	         });
	         
	         $('#copyPath').click(function() {
	            copyPath('<%= projectCode %><%= path %>');
	         });
	         
	         $('#pdfCreation').click(function() {
	     		showPopup();
	     		$('#popup_div').empty();
	 			var params = "testType=";
	 			params = params.concat('<%= testType %>');
	     		popup('printAsPdfPopup', params, $('#popup_div'));
	     	    escPopup();
	 	    });
	    });
    
       
	    function generateJmeter(testType) {
			showPopup();
			$('#popup_div').empty();
			var params = "testType=";
			params = params.concat(testType);
			popup('generateJmeter', params, $('#popup_div'));
			escPopup();
        }
        
        $('#closeGenerateTest, #closeGenTest').click(function() {
			changeTesting('<%= testType %>');
			enableScreen();
		});
        
		function openAndroidPopup(){
			$('#popup_div').empty();
			showPopup();
			var params = "testType=";
			params = params.concat("load");
			popup('testAndroid', params, $('#popup_div'));
		}
		
    </script>