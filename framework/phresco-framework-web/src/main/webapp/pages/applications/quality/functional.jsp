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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.framework.model.TestSuite"%>
<%@ page import="com.photon.phresco.framework.model.TestCase"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<%
	Boolean popup = Boolean.FALSE;
    Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
    String projectCode = project.getProjectInfo().getCode();
    String technology =  project.getProjectInfo().getTechnology().getName();
    String techId = project.getProjectInfo().getTechnology().getId();
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
%>
    <form action="functional" method="post" autocomplete="off" class="marginBottomZero" id="form_test">
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
				<div class="icon_fun_div">
					<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
					<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path" /></a>
				</div>
				<ul id="display-inline-block-example">
					<li id="first">
						<input id="testbtn" type="button" value="<s:text name="label.test"/>" class="primary btn env_btn">
					</li>
    
	<script type="text/javascript">
		$(document).ready(function() {
			
			$('#closeGenerateTest, #closeGenTest').click(function() {
				changeTesting("functional", "testGenerated");
				$(".wel_come").show().css("display","none");
				$("#popup_div").css("display","none");
				$("#popup_div").empty();
			});
			
			$('#testbtn').click(function() {
				$("#popup_div").empty();
				showPopup();
			 	<% 
			 		if (TechnologyTypes.IPHONE_NATIVE.equals(techId)) { 
			 	%>
			 		generateTest('testIphone', 'popup_div');
			 	<% 
			 		} else if (TechnologyTypes.ANDROIDS.contains(techId)) {
			 	%>
			 		generateTest('testAndroid', 'popup_div');
			 		
				<% 
			 		} else if (TechnologyTypes.IPHONE_HYBRID.equals(techId)) {
			 	%>
			 		iphone_HybridTest('testIphone', 'Iphone_HybridTest');
				<% 
			 		} else {
			 	%>
			 		generateTest('generateTest', 'popup_div');			 	
			 	<% 
			 		}
			 	
			 	%>

			});
			$('#openFolder').click(function() {
	        	//$('#folderIcon').attr('src','images/icons/close.png');
	            openFolder('<%= projectCode %><%= path %>');
	        });
	        
	        $('#copyPath').click(function() {
	           copyPath('<%= projectCode %><%= path %>');
	        });
		});	
		
		function generateTest(urlAction, container, event) {
			// load testSuite
			if($('#testSuites').find("option:selected").attr("id") != undefined) {
				var results = $('#testSuites').find("option:selected").attr("id").split(",");
				var failures = results[0];
				var errors = results[1];
				var tests = results[2];
				var selectedTestResultFile = results[3];
			}
			var testSuite = $("#testSuites").val();
			//changeTestResultFile
			if(event == "testResultFiles") {
				selectedTestResultFile = $('#testResultFiles').val();
			}
	        var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("testSuite=");
			params = params.concat(testSuite);
			params = params.concat("&testType=");
			params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
			params = params.concat("&failures=");
			params = params.concat(failures);
			params = params.concat("&errs=");
			params = params.concat(errors);
			params = params.concat("&tests=");
			params = params.concat(tests);
			params = params.concat("&selectedTestResultFileName=");
			params = params.concat(selectedTestResultFile);
			showLoadingIcon($('#'+ container)); // Loading Icon
			performAction(urlAction, params, $('#'+ container));
		    escPopup()
		}
		    
		function iphone_HybridTest() {
            $("#popup_div").css("display","none");
			showConsoleProgress('block');
			readerHandlerSubmit('functional', '<%= projectCode %>', '<%= FrameworkConstants.FUNCTIONAL %>', '');
		}
	 </script>
    
	<%
	   String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
       if(testError != null) {
    %>
    	<div class="alert-message block-message warning" style="margin: 5px 0 0 0;">
			<center><label class="errorMsgLabel"><%= testError %></label></center>
		</div>
	<% } else {
	        List<TestSuite> testSuites = (List<TestSuite>) request.getAttribute(FrameworkConstants.REQ_TEST_SUITE);
	        Set<String> testResultFiles = (Set<String>) request.getAttribute(FrameworkConstants.REQ_TEST_RESULT_FILE_NAMES);
	        String selectedTestResultFile = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TEST_RESULT_FILE);
	        List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
	        boolean buttonRow = false;
	%>
        
        <% 
        	if(CollectionUtils.isNotEmpty(projectModules)) {
        		buttonRow = true;
        %>
				<li id="label">
					&nbsp;<strong><s:text name="label.module"/></strong> 
				</li>
				<li>
					<select id="projectModule" name="projectModule"> <!-- class="funcModuleList"  -->
						<% for(String projectModule : projectModules) { %>
					  <option value="<%= projectModule %>" id="<%= projectModule %>" ><%= projectModule %> </option>
					
					<% 
				        }
					%>
					</select>
				</li>
			<% 	}
				if (buttonRow) {
			%>
				</ul>
			<% } %>

        	<div class="alert-message block-message warning hideCtrl" id="errorDiv" style="margin: 5px 0 0 0;">
				<center><label class="errorMsgLabel"></label></center>
			</div>
			
			<% if (buttonRow) { %>
			<ul id="display-inline-block-example">
				<li id="first"></li>
			<% } %>
			
			<li id="label">
				&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="label.test.suite"/></strong> 
			</li>
			<li>
				<select id="testSuite" name="testSuite"> <!--  class="funcList" -->
					<option value="All">All</option>
					<% 
					if(CollectionUtils.isNotEmpty(testSuites)) {
						for(TestSuite testSuiteDisplay : testSuites) {
					%>
					  <option value="<%= testSuiteDisplay.getName() %>" id="<%= testSuiteDisplay.getFailures() %>,<%= testSuiteDisplay.getErrors() %>,<%= testSuiteDisplay.getTests() %>,<%= selectedTestResultFile %>" ><%= testSuiteDisplay.getName() %> </option>
					
					<% 
				        }
					}
					%>
				</select>
			</li>
			<li id="label">
				&nbsp;<strong id="view" class="hideCtrl"><s:text name="label.test.result.view"/></strong> 
			</li>
			<li>
				<select id="resultView" name="resultView" class="techList selectDefaultWidth"> 
					<option value="tabular" >Tabular View</option>
					<option value="graphical" >Graphical View</option>
				</select>
			</li>
			</ul>
		</div>
	</form>
	
	<div id="testSuiteDisplay" class="testSuiteDisplay responsiveTableDisplay" style="height: 87%;">
	</div>
		
        <script type="text/javascript">

			$(document).ready(function() {
				$("#testResultFile, #testSuite, #testSuiteDisplay, #resultView, #testResultLbl, #view").hide();
				
				$('#resultView').change(function() {
					changeView();
				});
				
				// table resize
				var tblheight = (($("#subTabcontainer").height() - $("#form_test").height()));
				$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
			});
			
			loadTestResults();
			
			$('#projectModule').change(function() {
				loadTestResults();
			});

			$('#testSuite').change(function() {
				testReport();
			});

			function loadTestResults() {
				var params = "";
		    	if (!isBlank($('form').serialize())) {
		    		params = $('form').serialize() + "&";
		    	}
				params = params.concat("testType=");
				params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
				
		    	<% if (StringUtils.isNotEmpty(fromPage)) { %>
					params = params.concat("&fromPage=");
					params = params.concat('<%= fromPage %>');
				<% } %>

				performAction('fillTestSuites', params, '', true);
			}

			function testReport() {
				var params = "";
		    	if (!isBlank($('form').serialize())) {
		    		params = $('form').serialize() + "&";
		    	}
				params = params.concat("testType=");
				params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
				performAction('testReport', params, $('#testSuiteDisplay'));	
			}

			function successEvent(pageUrl, data) {
				if(pageUrl == "checkForConfiguration") {
		    		successEnvValidation(data);
		    	} else if(pageUrl == "checkForConfigType") {
		    		successEnvValidation(data);
		    	} else if(pageUrl == "fetchBuildInfoEnvs") {
		    		fillVersions("environments", data.buildInfoEnvs);
		    	} else {
		    		if ((data != undefined || !isBlank(data)) && data != "") {
						if (data.validated != undefined && data.validated) {
							return validationError(data.showError);
						}
						var testSuiteNames = data.testSuiteNames;
						if ((testSuiteNames != undefined || !isBlank(testSuiteNames))) {
							$("#errorDiv").hide();
							$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView, strong").show();
							$("#testResultFile").hide();
							$('#testSuite').empty();
							$('#testSuite').append($("<option></option>").attr("value", "All").text("All"));
							for (i in testSuiteNames) {
								$('#testSuite').append($("<option></option>").attr("value", testSuiteNames[i]).text(testSuiteNames[i]));
							}
							testReport();
						}
					
					}
		    	}
			}

			function validationError(errMsg) {
				$(".errorMsgLabel").html(errMsg);
				$("#errorDiv").show();
				$("#testResultFile, #testSuite, #testSuiteDisplay, #resultView, #testResultLbl, #view").hide();
				enableScreen();
			}
			
			function changeView() {
				var resultView = $('#resultView').val();
				if (resultView == 'graphical') {
					$("#graphicalView").show();
					$("#tabularView").hide();
				} else  {
					$("#graphicalView").hide();
					$("#tabularView").show();
				}
			}
		</script>
    <% } %>
 