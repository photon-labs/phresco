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

<% 
	Project project = (Project) request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String projectCode = (String) request.getAttribute(FrameworkConstants. REQ_PROJECT_CODE); 
   	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
   	String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String techId = project.getProjectInfo().getTechnology().getId();
%>

<style>
.perOpenAndCopyPath {
    float: right;
    padding-right: 3px;
    position: relative;
    top: 2px;
}

.perTabularView {
    float: right;
    position: relative;
    top: -32px;
    width: auto;
    left: -79px;
}
</style>
	
<form action="" method="post" autocomplete="off" style="margin-bottom: 0px; height: 100%;">
	<div class="operation">
        <%
        	if ((Boolean)request.getAttribute(FrameworkConstants.REQ_BUILD_WARNING)) {
        %>
			<div class="alert-message warning display_msg" >
				<s:label cssClass="labelWarn" key="build.required.message"/>
	   		</div>
   		<%
           	}
		%>
	    <input id="testbtn" type="button" value="<s:text name="label.test"/>" class="primary btn env_btn">
	    <div class="perOpenAndCopyPath">
	    	<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder"/></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>&nbsp;
		
	    <strong id="lblType" class="noTestAvail"><s:text name="label.types"/></strong>&nbsp;
    	<select class="noTestAvail" id="testResultsType" name="testResultsType" style="width :100px;"> 
			<option value="server" ><s:text name="label.application.server"/></option>
			<option value="database" ><s:text name="label.database"/></option>
			<option value="webservices" ><s:text name="label.webservices"/></option>
		</select>&nbsp;
		
    	<strong class="noTestAvail" id="testResultFileTitle"><s:text name="label.test.results"/></strong> 
        <select  class="noTestAvail" id="testResultFile" name="testResultFile">
        </select>
        <%
        	if(TechnologyTypes.ANDROIDS.contains(techId)) {
        %>
	        <strong class="noTestAvail" id="testResultDeviceName"><s:text name="label.device"/></strong> 
	        <select  class="noTestAvail" id="testResultDeviceId" name="testResultDeviceId" style="width :100px;">
	        </select>
        <%
        	}
        %>
	</div>
		
	<div class="noTestAvail perTabularView" id="dropdownDiv">
		
		<div class="resultView" style="float: left;" >
			<strong class="noTestAvail" id="dropdownDiv"><s:text name="label.test.result.view"/></strong> 
			<select id="resultView" name="resultView232"> 
				<option value="tabular" >Tabular View</option>
				<option value="graphical" >Graphical View</option>
			</select>
		</div>
	</div>
	
	<div id="graphBasedOn" class="functional_header perBasedOn">
		<div class="perBasedOnLbl" style="padding-left: 81px;">
			&nbsp;&nbsp;
			<strong id="lblType" class="noTestAvail"><s:text name="label.based.on"/></strong>
			<select  name="showGraphFor" id="showGraphFor" onchange="changeGraph(this.options[this.selectedIndex].value)"  style="width :210px;">
				<option value="responseTime" >Avg Response Time</option>
				<option value="throughPut" >Throughput</option>
				<option value="minResponseTime" >Min Response Time</option>
				<option value="maxResponseTime" >Max Response Time</option>
				<option value="all" >All</option>
			</select>
		</div>&nbsp;&nbsp;
	</div>
		
    <div id="testResultDisplay" class="testSuiteDisplay">
    </div>
	    
    <input type="hidden" id="testType" name="testType" value="<%= testType%>">
	    
	<div class="perErrorDis" id="noFiles">
		<div class="alert-message block-message warning" style="margin: 5px 0px 0;">
			<label Class="errorMsgLabel"><s:text name="performancetest.not.executed"/></label>
		</div>
	</div>
</form>

<script type="text/javascript">
    $(document).ready(function() {
    	
    	isResultFileAvailbale();
    	
		$(".noTestAvail").hide();// When there is no result table, it hides everything except test button
		$("#graphBasedOn").hide();
		// This specifies the recent test type , runned by user
		// make the seelct option selected which is selected in performance pop-up(When user close popup it is hidded)
		var selectJmeterTest = $('input:radio[name=jmeterTestAgainst]:checked').val();
		if (selectJmeterTest != undefined && !isBlank(selectJmeterTest)) {
			$("#testResultsType").val( selectJmeterTest ).attr('selected',true);
       	}
		
		$('#testResultsType').change(function() {
			performanceTestResultsFiles();
		});
		
		$('#testResultFile').change(function() {
			<%
				if(TechnologyTypes.ANDROIDS.contains(techId)) {
			%>
				getDevicesName();
			<%
				} else {
			%>
				performanceTestResults($("#testResultsType").val());
			<%
				}
			%>
		});
		
		$('#testResultDeviceId').change(function() {
			performanceTestResults($("#testResultsType").val());
		});
		
        $('#testbtn').click(function() {
			generateJmeter('<%= testType %>');
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
    		popup('printAsPdfPopup', '', $('#popup_div'));
    	    escPopup();
	    });
        
        $('#closeGenerateTest, #closeGenTest').click(function() {
        	changeTesting('<%= testType %>');
        	$(".wel_come").show().css("display","none");
        	$('#popup_div').css("display","none");
    		$('#popup_div').empty();
    	});
        
    });
   
    var testResultsType = "";
    
	function generateJmeter(testType) {
		 $('#popup_div').empty();
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	getCurrentCSS();
    	showPopup();
		performAction('generateJmeter', params, $('#popup_div'));
  		$(document).keydown(function(e) {
			// ESCAPE key pressed
          	if (e.keyCode == 27) {
              	$(".wel_come").show().css("display","none");
              	$('.popup_div').show().css("display","none");
            }
      	});
		return false;
        
	}
       
	function performanceTestResults(testResultsType) {
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
		performAction('performanceTestResult', params, $('#testResultDisplay'));
		//show print as pdf icon
		$('#pdfPopup').show();
    }
	
    function isResultFileAvailbale() {
       	var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	params = params.concat('<%= FrameworkConstants.REQ_TEST_TYPE %>' + "=");
		params = params.concat('<%= testType%>');
    	performAction('performanceTestResultAvail', params, '', true);
    }
    
    function successIsResultFileAvailable(data) {
       	if (data == false) {
       		$("#noFiles").show();
       		testResultNotAvail();
       		enableScreen();
		} else {
       		testResultAvailShowList();
       		performanceTestResultsFiles();
       	}
    }
    
    function performanceTestResultsFiles() {
        testResultsType = $("#testResultsType").val();
        var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	params = params.concat('<%= FrameworkConstants.REQ_TEST_TYPE %>' + "=");
		params = params.concat('<%= testType%>');
    	performAction('performanceTestResultFiles', params, '', true);
    }
    
    function successPerfTestResultsFiles(data) {
		if (data != null && !isBlank(data)) {
       		$('#testResultFile').show();
       		$('#testResultFile').empty();
			for (i in data) {
            	$('#testResultFile').append($("<option></option>").attr("value",data[i]).text(data[i]));
            }
            $("#noFiles").hide(); // hides no result found error message when there is test result file
            $("#testResultDisplay").show();
			<%
				if(TechnologyTypes.ANDROIDS.contains(techId)) {
			%>
				getDevicesName();
				disableType();
			<%
				} else {
			%>
				performanceTestResults(testResultsType);
			<%
				}
			%>
       	} else {
       		enableScreen();
       		$("#noFiles").show();       		
       		$('.errorMsgLabel').empty();
       		$('.errorMsgLabel').html("Performance test not yet executed for " + testResultsType);
       		testResultAvailShowList();
       	}
    }
    
    function testResultAvailShowList() {
    	$(".noTestAvail").show(); // shows list alone
    	testResultNotAvail();
    }
    
	function testResultNotAvail() {
		// When there is no test result file in any folder hide everything except test btn
   		$('#testResultFile').hide();
   		$('#testResultFileTitle').hide();
   		$("#dropdownDiv").hide();
		$("#graphBasedOn").hide();
		$("#testResultDisplay").empty(); 
		$("#testResultDisplay").hide();
    }
	
	function getDevicesName() {
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	performAction('getDevices', params, '', true);
	}
	
	function successGetDeviceName(data) {
		if (data != null && !isBlank(data)) {
			$('#testResultDeviceId').empty();
			$.each(data,function(key, value){
				$('#testResultDeviceId').append($("<option></option>").attr("value",key).text(value));
			});
			$('#testResultDeviceId').append($("<option></option>").attr("value","").text("All"));
			var testResultsType = $("#testResultsType").val();
			performanceTestResults(testResultsType);
		} else {
			// When there is no result
       		$('.errorMsgLabel').empty();
       		$('.errorMsgLabel').html("Performance test not yet executed for " + testResultsType);
       		testResultAvailShowList();
		}
	}
	
	function disableType() {	// for android technology disable type list box
		$('#lblType').css("display","none");
		$('#testResultsType').css("display","none");
	}
	
	function successEvent(pageUrl, data) {
		if(pageUrl == "performanceTestResultAvail") {
			successIsResultFileAvailable(data);
		} else if(pageUrl == "performanceTestResultFiles") {
			successPerfTestResultsFiles(data);
		} else if(pageUrl == "getDevices") {
			successGetDeviceName(data);
		} else if(pageUrl == "tstResultFiles") {
			successLoadTestFiles(data);
		} else if(pageUrl == "checkForConfigType") {
			successEnvValidation(data);
		} else if(pageUrl == "getPerfTestJSONData") {
			fillJSONData(data);
		}
	}
	
</script>